package com.example.ebay.state;

import org.assertj.core.api.SoftAssertions;

public class ScenarioState {

    private String firstPagePrice;
    private SoftAssertions softly;

    public String getFirstPagePrice() {
        return firstPagePrice;
    }

    public void setFirstPagePrice(String firstPagePrice) {
        this.firstPagePrice = firstPagePrice;
    }

    public SoftAssertions getSoftly() {
        return softly;
    }

    public void setSoftly(SoftAssertions softly) {
        this.softly = softly;
    }
}
