package com.nimble.sloth.router.func.apps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {
    private String appId;
    private String url;
    private String token;
}
