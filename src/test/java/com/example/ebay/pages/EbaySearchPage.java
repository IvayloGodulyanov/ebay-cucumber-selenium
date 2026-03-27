package com.example.ebay.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EbaySearchPage {
    private static final By CATEGORY_SELECT = By.id("gh-cat");
    private static final By SEARCH_INPUT = By.id("gh-ac");
    private static final By SEARCH_BUTTON = By.cssSelector("[class*='search-button__label']");
    private static final By FIRST_CARD_IMAGE = By.xpath("(//*[@class='s-card__image'])[3]");
    private static final By FIRST_REAL_CARD = By.xpath("(//*[contains(@class, 'su-card-container__content')])[3]");
    private static final By FIRST_CARD_TITLE = By.xpath("(//*[contains(@class, 'primary default')])[3]");
    private static final By FIRST_SHIPPING = By.xpath("(//*[@class='su-styled-text secondary large'])[4]");
    private static final By FIRST_CARD_PRICE = By.xpath("(//*[contains(@class,'s-card__price')])[3]");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public EbaySearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openHomePage() {
        driver.get("https://www.ebay.com/");
        wait.until(ExpectedConditions.titleContains("eBay"));
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void searchInCategory(String query, String category) {
        // Select category first (e.g. "Toys & Hobbies") to scope the search.
        WebElement categorySelect = wait.until(ExpectedConditions.elementToBeClickable(CATEGORY_SELECT));
        new Select(categorySelect).selectByVisibleText(category);
        // Then type the query (e.g. "Monopoly") and submit search.
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        searchInput.clear();
        searchInput.sendKeys(query);
        wait.until(ExpectedConditions.elementToBeClickable(SEARCH_BUTTON))
                .click();
        wait.until(ExpectedConditions.presenceOfElementLocated(FIRST_CARD_IMAGE));
    }

    public String firstResultTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_REAL_CARD))
                .findElement(FIRST_CARD_TITLE)
                .getText()
                .trim();
    }

    public String firstResultShippingText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_REAL_CARD))
                .findElement(FIRST_SHIPPING)
                .getText()
                .trim();
    }

    public String firstResultPriceText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_REAL_CARD))
                .findElement(FIRST_CARD_PRICE)
                .getText()
                .trim();
    }

    public void clickFirstResultItem() {
        WebElement firstResultImage = wait.until(ExpectedConditions.elementToBeClickable(FIRST_CARD_IMAGE));
        WebElement firstResultAnchor = firstResultImage.findElement(By.xpath("./ancestor::a[1]"));
        // Keep navigation in the same tab
        ((JavascriptExecutor) driver).executeScript
                ("arguments[0].setAttribute('target','_self');", firstResultAnchor);
        firstResultAnchor.click();
    }
}