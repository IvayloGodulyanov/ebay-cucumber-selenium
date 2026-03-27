package com.example.ebay.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public class EbayCartPage {

    private static final By CART_QUANTITY_FIELD = By.cssSelector("[class='textbox__control']");
    private static final By CART_SUBTOTAL_LABEL = By.xpath("//span[contains(text(),'Items')]");
    private static final By CART_TOTAL_PRICE = By.cssSelector("[data-test-id='ITEM_TOTAL'] "
            + ".text-display-span span span");
    private static final By CART_TOTAL_US_PRICE = By.xpath("//span[@class='text-display-span']"
            + "//span[starts-with(text(),'(US $')]");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public EbayCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String currentPageUrl() {
        wait.until(ExpectedConditions.titleContains("Cart"));
        return driver.getCurrentUrl();
    }

    public String getSelectedQuantity() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CART_QUANTITY_FIELD))
                .getAttribute("value")
                .trim();
    }

    public String getSubtotalLabel() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CART_SUBTOTAL_LABEL))
                .getText()
                .trim();
    }

    public String getTotalPriceText() {
        // Read the primary price shown on the cart page.
        String price = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(CART_TOTAL_PRICE)).getText().trim();
        // If primary price is not in USD, use the secondary "(US $...)" price instead.
        if (!price.contains("US")) {
                price = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(CART_TOTAL_US_PRICE)).getText().trim();
            }
            return price;
        }

    public String normalizePriceValue(String priceText) {
        return priceText == null ? "" : priceText.replaceAll("[^0-9.]", "");
    }

    public BigDecimal priceAsBigDecimal(String priceText) {
        return new BigDecimal(normalizePriceValue(priceText));
    }

    public BigDecimal singleItemPriceFromTotal(String totalPriceText, String quantity) {
        BigDecimal totalPrice = priceAsBigDecimal(totalPriceText);
        BigDecimal quantityValue = new BigDecimal(Integer.parseInt(quantity));
        return totalPrice.divide(quantityValue, 2, RoundingMode.HALF_UP);
    }
}