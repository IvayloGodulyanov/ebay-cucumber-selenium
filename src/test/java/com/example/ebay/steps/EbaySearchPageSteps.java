package com.example.ebay.steps;

import com.example.ebay.hooks.DriverContext;
import com.example.ebay.hooks.SoftAssertionsContext;
import com.example.ebay.pages.EbaySearchPage;
import com.example.ebay.state.ScenarioState;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;

public class EbaySearchPageSteps {

    private final ScenarioState scenarioState;
    private EbaySearchPage ebaySearchPage;
    private SoftAssertions softly() {return SoftAssertionsContext.get();}

    public EbaySearchPageSteps(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
    }

    @Given("I open the eBay homepage")
    public void iOpenTheEbayHomepage() {
        ebaySearchPage = new EbaySearchPage(DriverContext.getDriver());
        ebaySearchPage.openHomePage();

        softly().assertThat(ebaySearchPage.getPageTitle())
                .as("Home page title should indicate eBay is open")
                .containsIgnoringCase("ebay");
    }

    @When("I search for {string} in category {string}")
    public void iSearchForInCategory(String query, String category) {
        ebaySearchPage.searchInCategory(query, category);
    }

    @Then("the first result title should contain {string}")
    public void theFirstResultTitleShouldContain(String expectedTitlePart) {
        softly().assertThat(ebaySearchPage.firstResultTitle())
                .as("First search result title")
                .containsIgnoringCase(expectedTitlePart);
    }

    @And("the first result shipping should contain {string}")
    public void theFirstResultShippingShouldContain(String expectedCountry) {
        softly().assertThat(ebaySearchPage.firstResultShippingText())
                .as("First search result shipping text")
                .containsIgnoringCase(expectedCountry);
    }

    @And("the first result should display a price")
    public void theFirstResultShouldDisplayAPrice() {
        String firstPagePrice = ebaySearchPage.firstResultPriceText();
        scenarioState.setFirstPagePrice(firstPagePrice);

        softly().assertThat(firstPagePrice)
                .as("First page price from context")
                .isNotBlank();

        softly().assertThat(firstPagePrice)
                .as("First search result price format")
                .matches("^\\$\\d+(\\.\\d{2})?$");

        double price = Double.parseDouble(firstPagePrice.replace("$", ""));

        softly().assertThat(price)
                .as("Price should be greater than 0")
                .isGreaterThan(0);
    }

    @Then("click on the first item in order to navigate to details page")
    public void clickOnTheFirstItemInOrderToNavigateToDetailsPage() {
        ebaySearchPage.clickFirstResultItem();
    }
}
