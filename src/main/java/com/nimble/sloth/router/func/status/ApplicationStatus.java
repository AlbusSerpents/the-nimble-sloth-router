package com.nimble.sloth.router.func.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatus {

    @NotBlank
    private String applicationName;
    @NotBlank
    private String applicationAddress;

    @JsonProperty(access = READ_ONLY)
    private String applicationStatus;
}
