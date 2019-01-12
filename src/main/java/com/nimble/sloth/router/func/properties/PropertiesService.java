package com.nimble.sloth.router.func.properties;

import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class PropertiesService {

    private final PropertiesRepository repository;

    public PropertiesService(final PropertiesRepository repository) {
        this.repository = repository;
    }

    public Set<AppProperty> getProperties(final String appId) {
        return repository.getProperties(appId);
    }

    public void updateProperties(final String appId, final UpdateAppPropertiesRequest request) {
        final Set<AppProperty> properties = getProperties(appId);
        final Set<AppProperty> save = request.getSave();
        final Set<String> remove = request.getRemove();
        final Set<String> updatedKeys = getUpdateKeys(save);

        final Set<AppProperty> newProperties = properties
                .stream()
                .filter(prop -> !remove.contains(prop.getKey()))
                .filter(prop -> !updatedKeys.contains(prop.getKey()))
                .collect(toSet());

        newProperties.addAll(save);
        repository.setProperties(appId, newProperties);
    }

    private Set<String> getUpdateKeys(final Set<AppProperty> save) {
        return save
                .stream()
                .map(AppProperty::getKey)
                .collect(toSet());
    }
}
