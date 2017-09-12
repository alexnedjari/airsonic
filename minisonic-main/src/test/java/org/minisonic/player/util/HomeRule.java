package org.minisonic.player.util;

import org.junit.rules.ExternalResource;
import org.minisonic.player.TestCaseUtils;

public class HomeRule extends ExternalResource {
    @Override
    protected void before() throws Throwable {
        super.before();
        System.setProperty("minisonic.home", TestCaseUtils.minisonicHomePathForTest());

        TestCaseUtils.cleanMinisonicHomeForTest();
    }
}
