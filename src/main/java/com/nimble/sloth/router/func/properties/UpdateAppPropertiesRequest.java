package com.nimble.sloth.router.func.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
public class UpdateAppPropertiesRequest {
    @NotNull
    private Set<AppProperty> save;
    @NotNull
    private Set<String> remove;
}
