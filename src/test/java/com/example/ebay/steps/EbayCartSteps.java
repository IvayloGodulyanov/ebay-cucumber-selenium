package com.example.ebay.steps;

import static org.assertj.core.api.BDDAssertions.within;

import com.example.ebay.hooks.DriverContext;
import com.example.ebay.pages.EbayCartPage;
import com.example.ebay.state.ScenarioState;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import java.math.BigDecimal;
import org.assertj.core.api.SoftAssertions;

public class EbayCartSteps {

    private final ScenarioState scenarioState;
    private EbayCartPage ebayCartPage;
    private SoftAssertions softly() {
        return scenarioState.getSoftly();
    }

    public EbayCartSteps(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
    }

    @Then("I should be on {string}")
    public void iShouldBeOn(String expectedUrlPrefix) {
        ebayCartPage = new EbayCartPage(DriverContext.getDriver());
        softly().assertThat(ebayCartPage.currentPageUrl())
                .as("Cart URL")
                .contains(expectedUrlPrefix);
    }

    @And("in the Qty Drop Down List the quantity is {string}")
    public void inTheQtyDropDownListTheQuantityIs(String expectedQuantity) {
        softly().assertThat(ebayCartPage.getSelectedQuantity())
                .as("Selected cart quantity")
                .isEqualTo(expectedQuantity);
    }

    @And("the price is displayed for {string} items")
    public void thePriceIsDisplayedForItems(String quantity) {
        softly().assertThat(ebayCartPage.getSubtotalLabel())
                .as("Cart subtotal label")
                .containsIgnoringCase(quantity)
                .containsIgnoringCase("Items");

        String firstPagePrice = scenarioState.getFirstPagePrice();

        BigDecimal cartPageSinglePrice = ebayCartPage.singleItemPriceFromTotal(ebayCartPage.getTotalPriceText(), quantity);
        BigDecimal firstPagePriceBD = ebayCartPage.priceAsBigDecimal(firstPagePrice);

        softly().assertThat(cartPageSinglePrice)
                .as("Total price value on cart page")
                .isCloseTo(firstPagePriceBD, within(new BigDecimal("0.01")));
    }
}
