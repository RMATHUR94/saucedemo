# SauceDemo Selenium Automation Project

## 📋 Project Overview

This is a comprehensive Selenium WebDriver automation testing project for the [SauceDemo](https://www.saucedemo.com/) e-commerce website. The project is built using **Page Object Model (POM)** design pattern and demonstrates best practices in test automation.

### Key Features
- ✅ **Page Object Model Architecture** - Clean separation of concerns
- ✅ **Data-Driven Testing** - JSON-based test data provider
- ✅ **Cross-Browser Support** - Chrome, Firefox, and Edge browsers
- ✅ **Comprehensive Reporting** - ExtentReports HTML reports
- ✅ **Test Retry Mechanism** - Automatic retry for flaky tests
- ✅ **Screenshot on Failure** - Visual debugging support
- ✅ **Maven Build Integration** - Easy CI/CD integration

---

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 11+ | Programming Language |
| Selenium | 4.20.0 | Browser Automation |
| TestNG | 7.7.1 | Test Framework |
| Maven | Latest | Build & Dependency Management |
| WebDriverManager | 5.8.0 | Driver Management |
| Jackson | 2.17.2 | JSON Data Processing |
| ExtentReports | 5.1.1 | HTML Reporting |
| Commons IO | 2.13.0 | File Operations |

---

## 📁 Project Structure

```
saucedemo/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── Practice/
│   │           └── saucedemo/
│   │               ├── pageobjects/          # Page Object Models
│   │               │   ├── LandingPage.java
│   │               │   ├── ProductCatalogue.java
│   │               │   ├── CartPage.java
│   │               │   ├── CheckoutPage.java
│   │               │   └── ConfirmationPage.java
│   │               ├── AbstractComponents/   # Base Classes
│   │               │   └── AbstractComponent.java
│   │               └── resources/            # Configuration & Utilities
│   │                   ├── GlobalData.properties
│   │                   └── ExtentReporterNG.java
│   └── test/
│       └── java/
│           └── Practice/
│               └── saucedemo/
│                   ├── tests/               # Test Classes
│                   │   ├── StandaloneTest.java
│                   │   ├── ErrorValidation.java
│                   │   └── StandaloneTest2.java
│                   ├── testComponents/      # Base Test & Utilities
│                   │   ├── BaseTest.java
│                   │   ├── Listeners.java
│                   │   └── Retry.java
│                   └── data/                # Test Data
│                       └── PurchaseOrder.json
├── testSuites/                              # TestNG XML Configurations
│   ├── testng.xml
│   ├── errorhandling.xml
│   └── Purchase.xml
├── reports/                                 # Test Reports
│   └── index.html
├── pom.xml                                  # Maven Configuration
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 11 or higher
- Maven 3.6 or higher
- Git
- Chrome/Firefox browsers installed

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd saucedemo
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Verify installation**
   ```bash
   mvn --version
   java -version
   ```

---

## ▶️ Running Tests

### Run All Tests
```bash
mvn clean verify
```

### Run Specific Test Suite
```bash
# Run Purchase test suite
mvn clean verify -P Purchaseorder

# Run Error Handling suite
mvn clean verify -P errorhandeling

# Run Default suite
mvn clean verify -P testngxmlrun
```

### Run Tests with Specific Browser
```bash
# Chrome (default)
mvn clean verify -Dbrowser=chrome

# Firefox
mvn clean verify -Dbrowser=firefox

# Chrome Headless Mode
mvn clean verify -Dbrowser=chrome-headless

# Edge
mvn clean verify -Dbrowser=edge
```

### Run Specific Test Class
```bash
mvn clean verify -Dtest=StandaloneTest
```

### Run Specific Test Method
```bash
mvn clean verify -Dtest=StandaloneTest#completePurchaseFlow
```

---

## 📊 Test Report Generation

After running tests, an HTML report is generated at:
```
reports/index.html
```

Open this file in a web browser to view detailed test results including:
- Test execution timeline
- Pass/Fail statistics
- Screenshots of failures
- System information

---

## 🏗️ Page Object Model Architecture

The project follows the Page Object Model (POM) design pattern which provides:

### Benefits
1. **Maintainability** - Element locators centralized in page classes
2. **Reusability** - Page methods can be reused across multiple tests
3. **Readability** - Tests read like business workflows
4. **Scalability** - Easy to add new pages and methods

### Page Objects

#### 1. LandingPage
```java
public class LandingPage extends AbstractComponent
- goTo()                         // Navigate to application
- loginApplication(email, pwd)   // Perform login
- getErrorMsg()                  // Get login error message
```

#### 2. ProductCatalogue
```java
public class ProductCatalogue extends AbstractComponent
- addProductToCart()             // Add multiple products to cart
- goToCartPage()                 // Navigate to cart (inherited)
```

#### 3. CartPage
```java
public class CartPage extends AbstractComponent
- clickCheckoutButton()          // Proceed to checkout
- verifyProductDisplay(name)     // Verify product in cart (inherited)
```

#### 4. CheckoutPage
```java
public class CheckoutPage extends AbstractComponent
- fillCheckoutForm(...)          // Enter customer information
- completeCheckout()             // Finish order placement
```

#### 5. ConfirmationPage
```java
public class ConfirmationPage extends AbstractComponent
- getConfirmationMessage()       // Get order confirmation text
```

#### 6. AbstractComponent
```java
public abstract class AbstractComponent
- waitForElementToAppear(By)     // Wait for element visibility
- waitForWebElementToAppear()    // Wait for WebElement visibility
- waitAndClick(By)               // Wait and click element
- scrollByPixels(x, y)           // Scroll page
- goToCartPage()                 // Navigate to cart
- verifyProductDisplay(name)     // Verify product display
```

---

## 🧪 Test Classes

### StandaloneTest
**Purpose**: Complete end-to-end purchase flow test

**Test Method**: `completePurchaseFlow()`
- Logs in with multiple user credentials (data-driven)
- Adds products to cart
- Verifies cart contents
- Completes checkout
- Validates order confirmation

**Data Provider**: Uses `PurchaseOrder.json` for test data

### ErrorValidation
**Purpose**: Error handling and validation scenarios

**Test Methods**:
- `invalidLoginCredentialsTest()` - Validates error messages for wrong credentials
- `productValidationTest()` - Verifies products are added to cart correctly

**Retry Mechanism**: Automatically retries failed tests once

---

## 🔧 Configuration

### GlobalData.properties
Located at: `src/main/java/Practice/saucedemo/resources/GlobalData.properties`

```properties
# Default browser (can be overridden via Maven parameter)
browser=chrome
```

### TestNG XML Configuration
Located at: `testSuites/testng.xml`

Define test suites, classes, and groups for organized test execution.

### Maven Profiles
Located in: `pom.xml`

Available profiles:
- `testngxmlrun` - Run default TestNG suite
- `errorhandeling` - Run error handling tests
- `Purchaseorder` - Run purchase workflow tests

---

## 🎯 Test Data

### PurchaseOrder.json
Located at: `src/test/java/Practice/saucedemo/data/PurchaseOrder.json`

Contains test data for data-driven tests:
```json
[
  {
    "email": "standard_user",
    "password": "secret_sauce"
  },
  {
    "email": "visual_user",
    "password": "secret_sauce"
  }
]
```

---

## 📈 Logging & Reporting

### ExtentReports Integration
- **Report Location**: `reports/index.html`
- **Features**:
  - Detailed test execution timeline
  - Pass/Fail/Skip status with counts
  - Screenshot capture on failures
  - System information and logs
  - Timeline view for parallel execution

### TestNG Listeners
- **Listeners.java** - Captures screenshots on test failures and logs test events

---

## 🐛 Debugging & Troubleshooting

### Common Issues

#### Issue: Elements not found
**Solution**: 
- Verify element locators using browser developer tools
- Check if element is in view (scroll if needed)
- Ensure wait times are sufficient

#### Issue: Tests flake intermittently
**Solution**:
- Retry mechanism is configured in Retry.java
- Increase wait times in AbstractComponent if needed
- Check for AJAX calls and dynamic elements

#### Issue: WebDriver initialization fails
**Solution**:
- Verify browser is installed
- WebDriverManager automatically downloads drivers
- Check System.PATH for browser executable

#### Issue: JSON data not read
**Solution**:
- Verify PurchaseOrder.json path
- Check JSON format is valid
- Ensure file has read permissions

---

## 📝 Best Practices Implemented

1. **Page Object Model** - Clean architecture with page objects
2. **DRY Principle** - Reusable components in AbstractComponent
3. **Explicit Waits** - Proper synchronization with WebDriverWait
4. **Data-Driven Testing** - JSON-based parameterization
5. **Reporting** - Comprehensive HTML reports with screenshots
6. **Error Handling** - Proper exception handling and logging
7. **Configuration Management** - Properties-based configuration
8. **Browser Management** - WebDriverManager for easy driver setup

---

## 🔄 CI/CD Integration

### Jenkins Integration Example
```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn clean verify -Dbrowser=chrome'
            }
        }
    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'
            publishHTML([
                reportDir: 'reports',
                reportFiles: 'index.html',
                reportName: 'Extent Report'
            ])
        }
    }
}
```

---

## 📚 Additional Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Guide](https://testng.org/doc/documentation-main.html)
- [Page Object Model](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/)
- [ExtentReports](https://www.extentreports.com/)

---

## 👥 Contributors

- QA Automation Team

---

## 📄 License

This project is licensed under the MIT License - see LICENSE file for details.

---

## 📞 Support

For issues, questions, or suggestions, please create an issue in the repository.

---

**Last Updated**: May 28, 2026

