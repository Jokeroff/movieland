package com.lebediev.movieland.service.authentication;

import com.lebediev.movieland.dao.UserDao;
import com.lebediev.movieland.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class AuthService {
    private final static Logger LOG = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserDao userDao;
    private volatile Map<UUID, UserToken> userTokenCache = new HashMap<>();


    public UserToken getToken(String email, String password) {
        LOG.info("Auth cache. Start getting token for email: {}", email);
        UUID uuid = getUserUUID(email, password);
        for (Map.Entry entry : userTokenCache.entrySet()) {
            if (entry.getKey() == uuid) {
                LOG.info("Auth cache. Token for email {} retrieved from cache", email);
                return (UserToken) entry.getValue();
            }
        }
        return checkPassword(email, password);
    }

    public void deleteToken(UUID uuid) {
        LOG.info("Auth cache. Start deleting token for uuid {}", uuid);
        for (Map.Entry entry : userTokenCache.entrySet()) {
            userTokenCache.remove(entry.getKey());
        }
        LOG.info("Auth cache. Finish deleting token for uuid {}", uuid);
    }

    private UUID getUserUUID(String email, String password) {
        String source = email + password;
        byte[] bytes = source.getBytes();
        return UUID.nameUUIDFromBytes(bytes);
    }

    private UserToken checkPassword(String email, String password) {
        LOG.info("Auth cache. Start checking password for email: {}", email);
        User user = userDao.getUserByEmail(email);
        if (BCrypt.checkpw(password, user.getPassword())) {
            UUID uuid = getUserUUID(email, password);
            UserToken userToken = new UserToken(uuid, LocalDateTime.now().plusHours(2), user);
            userTokenCache.put(uuid, userToken);
            LOG.info("Auth cache. Finish checking password for email: {}. New token created", email);
            return userToken;
        }
        throw new IllegalArgumentException("Incorrect password for email: " + email);
    }

    @PostConstruct
    @Scheduled(fixedRateString = "${token.cache.fixedRate.in.milliseconds}", initialDelayString = "${token.cache.fixedDelay.in.milliseconds}")
    private void invalidateCache() {
        LOG.info("Auth cache. Start invalidating token cache");
        for (Map.Entry entry : userTokenCache.entrySet()) {
            UserToken userToken = (UserToken) entry.getValue();
            if (userToken.getExpirationTime().isAfter(LocalDateTime.now())) {
                userTokenCache.remove(entry.getKey());
            }
        }
        LOG.info("Auth cache. Finish invalidating token cache");
    }
}
