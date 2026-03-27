# eBay QA Automation - Gradle + Cucumber + Selenium

This project implements the requested BDD scenario with:
- Java
- Gradle
- Cucumber
- Selenium WebDriver
- Chrome

## Scenario Covered
- Open `https://ebay.com/`
- Verify page is opened correctly
- Select category `Toys & Hobbies`
- Search for `Monopoly`
- Verify first result title contains `Monopoly Board Game`
- Verify shipping contains `Bulgaria`
- Verify first result has a price

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
- Selenium Manager handles ChromeDriver automatically.
- eBay UI/content can change, so selectors and strict content checks (especially shipping destination) may need small updates over time.
