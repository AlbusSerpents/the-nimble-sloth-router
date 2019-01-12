package com.nimble.sloth.router.repositories;

import com.nimble.sloth.router.func.status.ApplicationStatus;
import com.nimble.sloth.router.func.status.StatusRepository;
import com.nimble.sloth.router.repositories.BaseRedisRepository.RedisKey;
import org.springframework.stereotype.Repository;

@Repository
public class RedisStatusRepository implements StatusRepository {

    private final BaseRedisRepository base;

    private enum ApplicationStatusKeys implements RedisKey {
        NAME("application:name"), ADDRESS("application:address");

        ApplicationStatusKeys(final String key) {
            this.key = key;
        }

        private final String key;

        @Override
        public String getKey() {
            return key;
        }
    }

    public RedisStatusRepository(final BaseRedisRepository base) {
        this.base = base;
    }

    @Override
    public String getApplicationName() {
        return base.getRequired(ApplicationStatusKeys.NAME);
    }

    @Override
    public String getApplicationAddress() {
        return base.getRequired(ApplicationStatusKeys.ADDRESS);
    }

    @Override
    public void setApplicationStatus(final ApplicationStatus status) {
        base.put(ApplicationStatusKeys.NAME, status.getApplicationName());
        base.put(ApplicationStatusKeys.ADDRESS, status.getApplicationAddress());
    }
}
