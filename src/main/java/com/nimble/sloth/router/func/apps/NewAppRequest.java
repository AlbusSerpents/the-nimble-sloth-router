package com.nimble.sloth.router.func.apps;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewAppRequest {
    private String appId;
    private String url;
}
