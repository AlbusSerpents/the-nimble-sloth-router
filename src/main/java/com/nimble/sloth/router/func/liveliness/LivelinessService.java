package com.nimble.sloth.router.func.liveliness;

import com.nimble.sloth.router.func.liveliness.AppLiveliness.LivelinessStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.nimble.sloth.router.func.liveliness.AppLiveliness.LivelinessStatus.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SuppressWarnings("WeakerAccess")
@Service
public class LivelinessService {
    private static final String STATUS_SUFFIX = "status";
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
        final String url = request.getUrl();
        return url.endsWith("/") ?
                String.format("%s%s", url, STATUS_SUFFIX) :
                String.format("%s/%s", url, STATUS_SUFFIX);
    }

    private LivelinessStatus callServer(final String fullUrl) {
        final RestTemplate restTemplate = new RestTemplate();
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(APPLICATION_JSON);
            final HttpEntity<Object> entity = new HttpEntity<>(null, headers);

            final ResponseEntity<Object> result = restTemplate.exchange(fullUrl, GET, entity, Object.class);
            return result.getStatusCode().is2xxSuccessful() ? OK : DOWN;
        } catch (Exception e) {
            LOG.error(e);
            return NOT_RESPONDING;
        }
    }

}
