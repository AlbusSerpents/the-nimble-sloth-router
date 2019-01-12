package com.nimble.sloth.router.repositories;

import com.nimble.sloth.router.func.apps.AppRecorderRepository;
import com.nimble.sloth.router.func.liveliness.Liveliness;
import com.nimble.sloth.router.func.status.ApplicationStatus;
import com.nimble.sloth.router.func.status.StatusRepository;
import com.nimble.sloth.router.repositories.BaseRedisRepository.RedisKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nimble.sloth.router.func.liveliness.Liveliness.ApplicationStatusOption.NOT_RESPONDING;

@Repository
public class RedisStatusRepository implements StatusRepository, AppRecorderRepository {

    private final BaseRedisRepository base;

    private enum ApplicationStatusKeys implements RedisKey {
        NAME("application:name"), ADDRESS("application:address"), STATUSE("application:statuses");

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
        return base.getRequired(ApplicationStatusKeys.NAME, String.class);
    }

    @Override
    public String getApplicationAddress() {
        return base.getRequired(ApplicationStatusKeys.ADDRESS, String.class);
    }

    @Override
    public void setApplicationStatus(final ApplicationStatus status) {
        base.put(ApplicationStatusKeys.NAME, status.getApplicationName());
        base.put(ApplicationStatusKeys.ADDRESS, status.getApplicationAddress());
    }

    @Override
    public List<Liveliness> statusReport() {
        return base.getRequired(ApplicationStatusKeys.STATUSE, StatuesWrapper.class).getStatuses();
    }

    @Override
    public void updateStatuses(final List<Liveliness> statuses) {
        final StatuesWrapper wrapper = new StatuesWrapper(statuses);
        base.put(ApplicationStatusKeys.STATUSE, wrapper);
    }

    @Override
    public void recordApp(final String appId, final String appAddress) {
        final Liveliness status = new Liveliness(appId, appAddress, NOT_RESPONDING);
        final List<Liveliness> statuses = statusReport();
        statuses.add(status);
        updateStatuses(statuses);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class StatuesWrapper {
        private List<Liveliness> statuses;
    }

}
