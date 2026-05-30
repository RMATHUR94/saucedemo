# Page Object Model (POM) Architecture Guide

## 🎯 Overview

This document provides a comprehensive guide to the Page Object Model implementation in the SauceDemo automation project.

---

## 📚 What is Page Object Model?

Page Object Model is a design pattern that creates an abstraction of web application pages. Each page is represented as a class that contains all the page elements (locators) and page methods (actions).

### Key Principles
1. **One class per page** - Each web page has its own Page Object class
2. **Element locators in class** - All By locators are defined as class variables
3. **No test logic in POM** - Page Objects only contain element identification and methods
4. **Reusable methods** - Methods represent user actions on the page

---

## 🏗️ Project Architecture

### Class Hierarchy

```
AbstractComponent (Base Class)
    ↓
    ├── LandingPage
    ├── ProductCatalogue
    ├── CartPage
    ├── CheckoutPage
    └── ConfirmationPage
```

### AbstractComponent - Base Class

The `AbstractComponent` class provides common functionality used by all page objects:

```java
public abstract class AbstractComponent {
    protected WebDriver driver;
    protected static final int WAIT_TIMEOUT = 10;
    
    // Common Web Elements
    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    protected WebElement shoppingCartLink;
    
    @FindBy(css = "div[class='inventory_item_name']")
    protected List<WebElement> productList;
    
    // Common Methods
    public void waitForElementToAppear(By locator) { }
    public void waitForWebElementToAppear(WebElement element) { }
    public void waitAndClick(By locator) { }
    public void scrollByPixels(WebDriver driver, int xPixels, int yPixels) { }
    public CartPage goToCartPage() { }
    public boolean verifyProductDisplay(final String productName) { }
}
```

**Responsibilities**:
- Manages WebDriver instance
- Provides common wait methods
- Contains shared UI elements (cart, header, etc.)
- Provides utility methods (scroll, verify, navigate)

---

## 📄 Page Object Implementation Details

### 1. LandingPage - Login Page

**File**: `src/main/java/Practice/saucedemo/pageobjects/LandingPage.java`

**Purpose**: Represents the login page of SauceDemo application

**Elements**:
```java
@FindBy(id = "user-name")
private WebElement userName;

@FindBy(id = "password")
private WebElement passWord;

@FindBy(id = "login-button")
private WebElement submitButton;

@FindBy(css = "h3[data-test='error']")
private WebElement errorMessage;
```

**Methods**:
```java
public void goTo()  // Navigate to application URL

public ProductCatalogue loginApplication(String email, String password)
// Perform login and return ProductCatalogue page

public String getErrorMsg()
// Get login error message
```

**Usage Example**:
```java
LandingPage landingPage = new LandingPage(driver);
landingPage.goTo();
ProductCatalogue catalogue = landingPage.loginApplication("standard_user", "secret_sauce");
```

---

### 2. ProductCatalogue - Product Inventory Page

**File**: `src/main/java/Practice/saucedemo/pageobjects/ProductCatalogue.java`

**Purpose**: Represents the product listing/inventory page

**Elements**:
```java
@FindBy(xpath = "//button[@id='add-to-cart-sauce-labs-backpack']")
private WebElement productBackpack;

@FindBy(xpath = "//button[@id='add-to-cart-sauce-labs-bike-light']")
private WebElement sauceBike;

@FindBy(xpath = "//button[@id='add-to-cart-test.allthethings()-t-shirt-(red)']")
private WebElement redShirt;

private By productList = By.xpath("//div[@class='inventory_list']");
```

**Methods**:
```java
public void addProductToCart()
// Add multiple products (Backpack, Bike, RedShirt) to cart
```

**Usage Example**:
```java
ProductCatalogue catalogue = landingPage.loginApplication("user", "pass");
catalogue.addProductToCart();
CartPage cart = catalogue.goToCartPage();  // Inherited from AbstractComponent
```

---

### 3. CartPage - Shopping Cart Page

**File**: `src/main/java/Practice/saucedemo/pageobjects/CartPage.java`

**Purpose**: Represents the shopping cart page

**Elements**:
```java
@FindBy(xpath = "//button[@id='checkout']")
private WebElement checkoutButton;
```

**Methods**:
```java
public CheckoutPage clickCheckoutButton()
// Click checkout and navigate to CheckoutPage
```

**Usage Example**:
```java
CartPage cartPage = productCatalogue.goToCartPage();
CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
```

---

### 4. CheckoutPage - Checkout Form Pages

**File**: `src/main/java/Practice/saucedemo/pageobjects/CheckoutPage.java`

**Purpose**: Represents checkout form pages (Step 1 and Step 2)

**Elements**:
```java
@FindBy(id = "first-name")
private WebElement firstName;

@FindBy(id = "last-name")
private WebElement lastName;

@FindBy(id = "postal-code")
private WebElement postalCode;

@FindBy(id = "continue")
private WebElement continueButton;

@FindBy(xpath = "//button[@id='finish']")
private WebElement finishButton;
```

**Methods**:
```java
public void fillCheckoutForm(String firstName, String lastName, String zipCode)
// Fill step 1 form with customer info

public ConfirmationPage completeCheckout()
// Click finish and navigate to ConfirmationPage
```

**Usage Example**:
```java
CheckoutPage checkout = cartPage.clickCheckoutButton();
checkout.fillCheckoutForm("John", "Doe", "12345");
ConfirmationPage confirmation = checkout.completeCheckout();
```

---

### 5. ConfirmationPage - Order Confirmation Page

**File**: `src/main/java/Practice/saucedemo/pageobjects/ConfirmationPage.java`

**Purpose**: Represents the order confirmation page

**Elements**:
```java
@FindBy(css = ".complete-header")
private WebElement confirmationMessage;
```

**Methods**:
```java
public String getConfirmationMessage()
// Get the confirmation message text
```

**Usage Example**:
```java
ConfirmationPage confirmation = checkout.completeCheckout();
String message = confirmation.getConfirmationMessage();
assertEquals(message, "Thank you for your order!");
```

---

## 🎯 Best Practices for Page Objects

### 1. Use Descriptive Names
```java
// ✅ Good
public void fillCheckoutForm(String firstName, String lastName, String zipCode)

// ❌ Bad
public void fillForm()
```

### 2. Return Page Objects for Navigation
```java
// ✅ Good - Enables Method Chaining
public ProductCatalogue loginApplication(String email, String password) {
    // ... actions ...
    return new ProductCatalogue(driver);
}

// ❌ Bad - Breaks Fluent Interface
public void loginApplication(String email, String password) {
    // ... actions ...
}
```

### 3. Use PageFactory for Element Initialization
```java
// ✅ Always initialize PageFactory
public LandingPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    PageFactory.initElements(driver, this);
}
```

### 4. Extract Locators as Variables
```java
// ✅ Good - Centralized Locators
@FindBy(id = "user-name")
private WebElement userName;

// ❌ Bad - Scattered Locators
WebElement userName = driver.findElement(By.id("user-name"));
```

### 5. Use Explicit Waits
```java
// ✅ Good - Explicit Wait
public void waitForElementToAppear(By locator) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
}

// ❌ Bad - Implicit Wait Only
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```

### 6. Keep Methods Simple and Focused
```java
// ✅ Good - Single Responsibility
public void addProductToCart() {
    waitForElementToAppear(productList);
    productBackpack.click();
    sauceBike.click();
}

// ❌ Bad - Multiple Responsibilities
public void addProductAndCheckout() {
    productBackpack.click();
    checkoutButton.click();
    // ... too much logic ...
}
```

---

## 🔀 Method Chaining Pattern

One of the benefits of POM is the ability to chain method calls for cleaner test code:

```java
// Instead of this:
ProductCatalogue catalogue = landingPage.loginApplication("user", "pass");
catalogue.addProductToCart();
CartPage cart = catalogue.goToCartPage();
CheckoutPage checkout = cart.clickCheckoutButton();
checkout.fillCheckoutForm("John", "Doe", "12345");
ConfirmationPage confirmation = checkout.completeCheckout();
String message = confirmation.getConfirmationMessage();

// You can write:
String message = landingPage
    .loginApplication("user", "pass")
    .addProductToCart();
    .goToCartPage()
    .clickCheckoutButton()
    .fillCheckoutForm("John", "Doe", "12345")
    .completeCheckout()
    .getConfirmationMessage();
```

---

## 🧩 Adding New Pages

To add a new page to the POM:

### Step 1: Create Page Class
```java
package Practice.saucedemo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Practice.saucedemo.AbstractComponents.AbstractComponent;

public class NewPage extends AbstractComponent {
    
    WebDriver driver;
    
    public NewPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    // Define elements with @FindBy
    @FindBy(id = "element-id")
    private WebElement element;
    
    // Define page methods
    public void performAction() {
        // Implementation
    }
}
```

### Step 2: Update Related Pages
Link the new page from pages that navigate to it:
```java
public NewPage navigateToNewPage() {
    someButton.click();
    return new NewPage(driver);
}
```

### Step 3: Use in Tests
```java
NewPage newPage = previousPage.navigateToNewPage();
newPage.performAction();
```

---

## 📊 POM vs Non-POM Comparison

| Aspect | POM | Non-POM |
|--------|-----|---------|
| Maintainability | ✅ High | ❌ Low |
| Reusability | ✅ High | ❌ Low |
| Scalability | ✅ Easy | ❌ Difficult |
| Readability | ✅ High | ❌ Low |
| Code Changes | ✅ Localized | ❌ Scattered |
| Duplicate Code | ✅ Minimal | ❌ Much |
| Test Speed | ✅ Fast | ❌ Slow |

---

## 🎓 Learning Resources

- Use existing page objects as templates
- Refer to `AbstractComponent` for common methods
- Review test classes to see POM usage
- Maintain consistent naming conventions
- Document new pages and methods

---

**Version**: 1.0  
**Last Updated**: May 28, 2026

