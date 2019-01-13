package com.nimble.sloth.router.func.liveliness;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class LivelinessRequest {
    private final String appId;
    private final String url;
}
