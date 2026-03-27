# eBay QA Automation - Gradle + Cucumber + Selenium

This project implements the requested BDD scenario with:
- Java
- Gradle
- Cucumber
- Selenium WebDriver
- Chrome

## E2E Scenario Covered
- Open `https://ebay.com/`
- Search for `Monopoly` in category `Toys & Hobbies`
- Verify first result title contains `Monopoly Board Game`
- Verify first result shipping contains `Free International Shipping`
- Verify first result has a price
- Open first result item details page
- Verify details page title contains `Monopoly`
- Verify details page item price matches search result price
- Open `Shipping, returns, and payments` popup
- Verify item can be shipped to `Bulgaria`
- Select quantity `2`
- Add item to cart
- Verify user is on `https://cart.payments.ebay.com/`
- Verify cart quantity is `2`
- Verify price is displayed for `2` items

## Run from CLI

```bash
./gradlew test -Dheadless=true
```

Generate reports:
- Cucumber HTML: `build/reports/cucumber/cucumber-report.html`
- Test report: `build/reports/tests/test/index.html`

## Run in IntelliJ IDEA / Eclipse
- Import as **Gradle Project**
- Use JDK 21
- Run test class: `com.example.ebay.runner.RunCucumberTest`

## Notes
- Chrome must be installed.
- The sporadic human verification is not automated(should be bypassed manually).
- Selenium Manager handles ChromeDriver automatically.
- eBay UI/content can change, so selectors and strict content checks (especially shipping destination) may need small updates over time.
