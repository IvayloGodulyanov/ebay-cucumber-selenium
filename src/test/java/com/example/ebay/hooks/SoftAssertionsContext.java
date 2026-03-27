package com.example.ebay.hooks;

import org.assertj.core.api.SoftAssertions;

public final class SoftAssertionsContext {
    private static final ThreadLocal<SoftAssertions> SOFT_ASSERTIONS = new ThreadLocal<>();

    private SoftAssertionsContext() {
    }

    public static SoftAssertions get() {
        return SOFT_ASSERTIONS.get();
    }

    public static void set(SoftAssertions softly) {
        SOFT_ASSERTIONS.set(softly);
    }

    public static void clear() {
        SOFT_ASSERTIONS.remove();
    }
}
