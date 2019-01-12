package com.nimble.sloth.router.func.properties;

import java.util.Set;

public interface PropertiesRepository {
    Set<AppProperty> getProperties(final String appId);

    void setProperties(final String appId, final Set<AppProperty> properties);
}
