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
    private final ThreadLocal <User> userThreadLocal = new ThreadLocal <>();

    public User getUserThreadLocal() {
        return userThreadLocal.get();
    }

    public void setUserThreadLocal(User user) {
        userThreadLocal.set(user);
    }

    public void clearUserThreadLocal() {
        userThreadLocal.remove();
    }

    public UserToken authenticate(String email, String password) {
        LOG.info("Start authentication for email: {}", email);
        User user = userDao.getByEmail(email);

        if (BCrypt.checkpw(password, user.getPassword())) {
            for (Map.Entry <UUID, UserToken> entry : userTokenCache.entrySet()) {
                UserToken userToken = entry.getValue();
                if (userToken.getUser().getEmail().equals(email) &&
                    userToken.getExpirationTime().isAfter(LocalDateTime.now())) {
                    LOG.info("Finish authentication for email: {}. Token returned from cache ", email);
                    return userToken;
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

    public void deleteToken(UUID uuid) {
        LOG.info("Start deleting token for uuid {}", uuid);

        userTokenCache.remove(uuid);

        LOG.info("Auth cache. Finish deleting token for uuid {}", uuid);
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
        Iterator <UserToken> iterator = userTokenCache.values().iterator();
        while (iterator.hasNext()) {
            UserToken userToken = iterator.next();
            LocalDateTime expirationTime = userToken.getExpirationTime();
            if (expirationTime.isBefore(LocalDateTime.now())) {
                iterator.remove();
            }
        }
        LOG.info("Auth cache. Finish invalidating token cache");
    }

}
