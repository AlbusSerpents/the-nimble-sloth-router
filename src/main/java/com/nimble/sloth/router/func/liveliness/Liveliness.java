package com.nimble.sloth.router.func.liveliness;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Liveliness {
    private String applicationName;
    private String applicationAddress;
    private ApplicationStatusOption applicationStatus;

    public enum ApplicationStatusOption {
        OK, NOT_RESPONDING, DOWN
    }
}
