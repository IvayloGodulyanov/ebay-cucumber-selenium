package com.example.ebay.steps;

import com.example.ebay.hooks.DriverContext;
import com.example.ebay.hooks.SoftAssertionsContext;
import com.example.ebay.pages.EbayItemDetailsPage;
import com.example.ebay.state.ScenarioState;
import io.cucumber.java.en.And;
import org.assertj.core.api.SoftAssertions;

public class EbayItemDetailsSteps {

    private final ScenarioState scenarioState;
    private EbayItemDetailsPage ebayItemDetailsPage;
    private SoftAssertions softly() {
        return SoftAssertionsContext.get();
    }

    public EbayItemDetailsSteps(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
    }

    @And("the title of the item should contain {string}")
    public void theTitleOfTheItemShouldContain(String expectedTitlePart) {
        ebayItemDetailsPage = new EbayItemDetailsPage(DriverContext.getDriver());
        softly().assertThat(ebayItemDetailsPage.getCurrentUrl())
                .as("Item page title should indicate 'eBay/itm' is open")
                .containsIgnoringCase("www.ebay.com/itm");
        softly().assertThat(ebayItemDetailsPage.getItemTitle())
                .as("Item details page title")
                .containsIgnoringCase(expectedTitlePart);
    }

    @And("the item price should be the same as on the first page")
    public void theItemPriceShouldBeTheSameAsOnTheFirstPage() {
        String itemDetailsPagePrice = ebayItemDetailsPage.getItemPriceText();
        String firstPagePrice = scenarioState.getFirstPagePrice();
        softly().assertThat(ebayItemDetailsPage.normalizePriceValue(itemDetailsPagePrice))
                .as("Item price value on details page")
                .isEqualTo(ebayItemDetailsPage.normalizePriceValue(firstPagePrice));
    }

    @And("I click on the {string} link")
    public void iClickOnTheLink(String linkText) {
        softly().assertThat(linkText).isEqualTo("See details");
        ebayItemDetailsPage.clickShippingSeeDetails();
    }

    @And("the {string} popup should be displayed")
    public void thePopupShouldBeDisplayed(String popupTitle) {
        softly().assertThat(ebayItemDetailsPage.getShippingPopupTitle())
                .as("Shipping, returns, and payments popup text")
                .isEqualToIgnoringCase(popupTitle);
    }

    @And("the item can be shipped to {string}")
    public void theItemCanBeShippedTo(String country) {
        ebayItemDetailsPage.searchInCountries(country);
        softly().assertThat(ebayItemDetailsPage.getSelectedShippingOption())
                .as("Bulgaria")
                .containsIgnoringCase(country);
        ebayItemDetailsPage.closePopUp();
    }

    @And("I select quantity {string}")
    public void iSelectQuantity(String quantity) {
        ebayItemDetailsPage = new EbayItemDetailsPage(DriverContext.getDriver());
        ebayItemDetailsPage.selectItemQuantity(quantity);
    }

    @And("I click Add to cart")
    public void iClickAddToCart() {
        ebayItemDetailsPage.clickAddToCart();
        ebayItemDetailsPage.clickSeeInCart();
    }
}
