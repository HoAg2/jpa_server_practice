package com.example.practice.jpa_practice.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

@Slf4j
public class ProfileUtils {

    public static boolean isLocal(Environment environment) {
        return environment.acceptsProfiles(Profiles.of("local"));
    }

    public static boolean isDev(Environment environment) {
        return environment.getActiveProfiles().length == 0 || environment.acceptsProfiles(Profiles.of("dev"));
    }

    public static boolean isTest(Environment environment) {
        return environment.acceptsProfiles(Profiles.of("test"));
    }

    public static boolean isAlpha(Environment environment) {
        return environment.acceptsProfiles(Profiles.of("alpha"));
    }

    public static boolean isBeta(Environment environment) {
        return environment.acceptsProfiles(Profiles.of("beta"));
    }

    public static boolean isReal(Environment environment) {
        return environment.acceptsProfiles(Profiles.of("real"));
    }

}
