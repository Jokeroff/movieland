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
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class AuthService {
    private final static Logger LOG = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserDao userDao;
    private final Map <UUID, UserToken> userTokenCache = new ConcurrentHashMap <>();

    public UserToken authenticate(String email, String password) {
        LOG.info("Start authentication for email: {}", email);
        User user = userDao.getUserByEmail(email);

        if (BCrypt.checkpw(password, user.getPassword())) {
            for (Map.Entry <UUID, UserToken> entry : userTokenCache.entrySet()) {
                if (entry.getValue().getUser().getEmail().equals(email) &&
                    entry.getValue().getExpirationTime().isAfter(LocalDateTime.now())) {
                    LOG.info("Finish authentication for email: {}. Token returned from cache ", email);
                    return entry.getValue();
                }
            }

            UUID uuid = getUserUUID(email, password);
            UserToken userToken = new UserToken(uuid, LocalDateTime.now().plusHours(2), user);
            userTokenCache.put(uuid, userToken);

            LOG.info("Finish authentication for email: {}. New token created", email);
            return userToken;
        }
        LOG.info("Authentication failed for email: {}. Incorrect password", email);
        throw new SecurityException("Authentication failed. Incorrect password for email: " + email);
    }

    public UserToken authorize(UUID uuid) {
        LOG.info("Start authorization for uuid: {}", uuid);

        if (userTokenCache.containsKey(uuid)) {
            UserToken userToken = userTokenCache.get(uuid);
            if (userToken.getExpirationTime().isAfter(LocalDateTime.now())) {

                LOG.info("Finish authorization for uuid: {}", uuid);
                return userToken;
            }
        }
        LOG.warn("Authorization failed for uuid: {}", uuid);
        throw new SecurityException("Session expired. You need login to proceed ");
    }

    public boolean deleteToken(UUID uuid) {
        LOG.info("Start deleting token for uuid {}", uuid);
        Iterator <Map.Entry <UUID, UserToken>> iterator = userTokenCache.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getKey().equals(uuid)) {
                iterator.remove();
                LOG.info("Auth cache. Finish deleting token for uuid {}", uuid);
                return true;
            }
        }
        LOG.warn("Token for uuid {} was not found in cache", uuid);
        return false;
    }

    private UUID getUserUUID(String email, String password) {
        String source = email + password + LocalDateTime.now();
        byte[] bytes = source.getBytes();
        return UUID.nameUUIDFromBytes(bytes);
    }


    @PostConstruct
    @Scheduled(fixedRateString = "${token.cache.fixedDelay.in.milliseconds}", initialDelayString = "${token.cache.fixedDelay.in.milliseconds}")
    private void invalidateCache() {
        LOG.info("Auth cache. Start invalidating token cache");
        Iterator <Map.Entry <UUID, UserToken>> iterator = userTokenCache.entrySet().iterator();
        while (iterator.hasNext()) {
            LocalDateTime expirationTime = iterator.next().getValue().getExpirationTime();
            if (expirationTime.isAfter(LocalDateTime.now())) {
                iterator.remove();
            }
        }
        LOG.info("Auth cache. Finish invalidating token cache");
    }

}
