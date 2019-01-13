package com.nimble.sloth.router.func.liveliness;

import com.nimble.sloth.router.func.liveliness.AppLiveliness.LivelinessStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.nimble.sloth.router.func.liveliness.AppLiveliness.LivelinessStatus.*;

@SuppressWarnings("WeakerAccess")
@Service
public class LivelinessService {
    private static final String STATUS_SUFFIX = "/status";
    private static final Log LOG = LogFactory.getLog(LivelinessService.class);

    private final LivelinessRepository repository;

    public LivelinessService(final LivelinessRepository repository) {
        this.repository = repository;
    }

    public List<AppLiveliness> livelinessReport() {
        return repository.livelinessReport();
    }

    public void updateLivelinessStatus(final List<AppLiveliness> apps) {
        repository.updateLiveliness(apps);
    }

    public AppLiveliness checkLiveliness(final LivelinessRequest request) {
        final String appId = request.getAppId();
        final String fullUrl = makeUrl(request);
        final LivelinessStatus applicationStatus = callServer(fullUrl);
        return new AppLiveliness(appId, request.getUrl(), applicationStatus);
    }

    private String makeUrl(final LivelinessRequest request) {
        return request.getUrl() + STATUS_SUFFIX;
    }

    private LivelinessStatus callServer(final String fullUrl) {
        final RestTemplate restTemplate = new RestTemplate();
        try {
            final ResponseEntity<Object> result = restTemplate.getForEntity(fullUrl, Object.class);
            return result.getStatusCode().is2xxSuccessful() ? OK : DOWN;
        } catch (Exception e) {
            LOG.error(e);
            return NOT_RESPONDING;
        }
    }

}
