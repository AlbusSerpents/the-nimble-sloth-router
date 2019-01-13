package com.nimble.sloth.router.func.liveliness;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppLiveliness {
    private String applicationName;
    private String applicationAddress;
    private LivelinessStatus applicationStatus;

    public enum LivelinessStatus {
        OK, NOT_RESPONDING, DOWN
    }
}
