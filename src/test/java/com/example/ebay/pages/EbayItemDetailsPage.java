package com.example.ebay.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EbayItemDetailsPage {

    private static final By ITEM_TITLE = By.xpath("//h1[contains(@class,'x-item-title__mainTitle')]"
            + "//span[contains(@class,'ux-textspans') and normalize-space()]");
    private static final By ITEM_PRICE = By.cssSelector(".x-price-primary span");
    private static final By ITEM_US_PRICE = By.cssSelector("[class*='ux-textspans--SECONDARY ux-textspans']");
    private static final By SHIPPING_DETAILS_LINK = By.xpath("//span[normalize-space()='Returns:']"
            + "/following::span[contains(normalize-space(.),'See details')][1]");
    private static final By SHIPPING_POPUP_TITLE = By.xpath("(//span[normalize-space()="
            + "'Shipping, returns, and payments'])[1]");
    private static final By SHIPPING_OPTIONS_DROPDOWN = By.id("shCountry");
    private static final By ITEM_SELECTION_OPTIONS_DROPDOWN = By.xpath("(//span[@class='btn__text'])[1]");
    private static final By SINGLE_ITEM_SELECTION_OPTION = By.xpath("//div[@class='listbox__option' and starts-with(@data-sku-value-name, '1')]");
    private static final By ITEM_QUANTITY_INPUT = By.cssSelector("[id='qtyTextBox']");
    private static final By ADD_TO_CART_BUTTON = By.id("atcBtn_btn_1");
    private static final By SEE_IN_CART_BUTTON = By.xpath("(//span[normalize-space()='See in cart'])[3]");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public EbayItemDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getCurrentUrl() {
        wait.until(ExpectedConditions.titleContains("eBay"));
        return driver.getCurrentUrl();
    }

    public String getItemTitle() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(ITEM_TITLE))
                .getText()
                .trim();
    }

    public String getItemPriceText() {
        // Read the primary price shown on the item details page.
        String price = wait.until(
                ExpectedConditions.visibilityOfElementLocated(ITEM_PRICE)).getText().trim();
        // If primary price is not in USD, use the secondary "(US $...)" price instead.
        if (!price.contains("US")) {
            price = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(ITEM_US_PRICE)).getText().trim();
        }
        return price;
    }

    public void clickShippingSeeDetails() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SHIPPING_DETAILS_LINK))
                .click();
    }

    public String getShippingPopupTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SHIPPING_POPUP_TITLE))
                .getText()
                .trim();
    }

    public String getSelectedShippingOption() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SHIPPING_OPTIONS_DROPDOWN))
                .getText()
                .trim();
    }

    public void closePopUp() {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();
    }

    public void searchInCountries(String country) {
        WebElement categorySelect = wait.until(ExpectedConditions.elementToBeClickable(SHIPPING_OPTIONS_DROPDOWN));
        new Select(categorySelect).selectByVisibleText(country);
    }

    public void selectItemQuantity(String quantity) {
        WebElement quantityInput = wait.until(ExpectedConditions.presenceOfElementLocated(ITEM_QUANTITY_INPUT));
        if (ExpectedConditions.elementToBeClickable(quantityInput).apply(driver) != null) {
            // Input is clickable → type quantity
            quantityInput.click();
            quantityInput.clear();
            quantityInput.sendKeys(quantity);
        } else {
            // Input not clickable → select from dropdown item → type quantity
            WebElement itemSelectionDropdown = wait.until(ExpectedConditions.elementToBeClickable(ITEM_SELECTION_OPTIONS_DROPDOWN));
            itemSelectionDropdown.click();
            WebElement singleItemSelection = wait.until(ExpectedConditions.elementToBeClickable(SINGLE_ITEM_SELECTION_OPTION));
            singleItemSelection.click();
            quantityInput.click();
            quantityInput.clear();
            quantityInput.sendKeys(quantity);
        }
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART_BUTTON))
                .click();
    }

    public void clickSeeInCart() {
        wait.until(ExpectedConditions.elementToBeClickable(SEE_IN_CART_BUTTON))
                .click();
    }

    public String normalizePriceValue(String priceText) {
        return priceText == null ? "" : priceText.replaceAll("[^0-9.]", "");
    }
}
