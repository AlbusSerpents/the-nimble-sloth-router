package com.nimble.sloth.router.repositories;

import com.nimble.sloth.router.func.apps.AppRecorderRepository;
import com.nimble.sloth.router.func.liveliness.AppLiveliness;
import com.nimble.sloth.router.func.liveliness.LivelinessRepository;
import com.nimble.sloth.router.repositories.BaseRedisRepository.RedisKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nimble.sloth.router.func.liveliness.AppLiveliness.LivelinessStatus.NOT_RESPONDING;

@Repository
public class RedisLivelinessRepository implements LivelinessRepository, AppRecorderRepository {

    private final BaseRedisRepository base;

    public RedisLivelinessRepository(final BaseRedisRepository base) {
        this.base = base;
    }

    private enum Keys implements RedisKey {
        LIVELINESS("application:statuses");

        Keys(final String key) {
            this.key = key;
        }

        private final String key;

        @Override
        public String getKey() {
            return key;
        }

    }

    @Override
    public List<AppLiveliness> livelinessReport() {
        return base.getRequired(Keys.LIVELINESS, StatuesWrapper.class).getStatuses();
    }

    @Override
    public void updateLiveliness(final List<AppLiveliness> statuses) {
        final StatuesWrapper wrapper = new StatuesWrapper(statuses);
        base.put(Keys.LIVELINESS, wrapper);
    }

    @Override
    public void recordApp(final String appId, final String appAddress) {
        final AppLiveliness status = new AppLiveliness(appId, appAddress, NOT_RESPONDING);
        final List<AppLiveliness> statuses = livelinessReport();
        statuses.add(status);
        updateLiveliness(statuses);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class StatuesWrapper {
        private List<AppLiveliness> statuses;
    }

}
