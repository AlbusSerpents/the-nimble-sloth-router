package com.nimble.sloth.router.func.liveliness;

import java.util.List;

public interface LivelinessRepository {

    List<AppLiveliness> livelinessReport();

    void updateLiveliness(final List<AppLiveliness> statuses);

}
