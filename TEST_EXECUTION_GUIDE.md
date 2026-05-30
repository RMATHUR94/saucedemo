# Test Execution Guide

## 📋 Quick Start

This guide provides step-by-step instructions to execute tests in the SauceDemo automation project.

---

## 🔧 Prerequisites

- **Java JDK 11+**: [Download](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- **Maven 3.6+**: [Download](https://maven.apache.org/download.cgi)
- **Git**: [Download](https://git-scm.com/)
- **Browser**: Chrome, Firefox, or Edge installed

### Verify Installation
```bash
java -version
mvn --version
git --version
```

---

## 📥 Project Setup

### 1. Clone Repository
```bash
git clone <repository-url>
cd saucedemo
```

### 2. Install Dependencies
```bash
mvn clean install
```

This will:
- Download all required dependencies
- Compile source code
- Build the project

### 3. Verify Setup
```bash
mvn clean verify -DskipTests -X
```

---

## ▶️ Running Tests

### 1. Run All Tests
```bash
mvn clean verify
```

**What happens**:
- Cleans previous build
- Compiles code
- Runs all test classes
- Generates HTML report

---

### 2. Run Tests by Profile

#### Run Default Suite
```bash
mvn clean verify -P testngxmlrun
```

Executes: `testSuites/testng.xml`

#### Run Error Handling Tests
```bash
mvn clean verify -P errorhandeling
```

Executes: `testSuites/errorhandling.xml`

#### Run Purchase Tests
```bash
mvn clean verify -P Purchaseorder
```

Executes: `testSuites/Purchase.xml`

---

### 3. Run Tests by Browser

#### Chrome (Default)
```bash
mvn clean verify -Dbrowser=chrome
```

#### Firefox
```bash
mvn clean verify -Dbrowser=firefox
```

#### Edge
```bash
mvn clean verify -Dbrowser=edge
```

#### Chrome Headless Mode (CI/CD)
```bash
mvn clean verify -Dbrowser=chrome-headless
```

---

### 4. Run Specific Test Class

```bash
# Run StandaloneTest
mvn clean verify -Dtest=StandaloneTest

# Run ErrorValidation
mvn clean verify -Dtest=ErrorValidation

# Run multiple classes
mvn clean verify -Dtest=StandaloneTest,ErrorValidation
```

---

### 5. Run Specific Test Method

```bash
mvn clean verify -Dtest=StandaloneTest#completePurchaseFlow
```

**Syntax**: `-Dtest=ClassName#methodName`

---

### 6. Parallel Execution

Run tests in parallel using multiple threads:

```bash
# Run with 3 threads
mvn clean verify -DparallelRun=true -DnumThreads=3
```

**Note**: Requires TestNG configuration in XML file

---

### 7. Advanced Maven Commands

#### Skip Compilation
```bash
mvn verify -DskipCompile=true
```

#### Generate Reports Only
```bash
mvn clean verify -Dtest=Dummy -P testreport
```

#### Run with Debug Output
```bash
mvn clean verify -X
```

#### Run with Verbose Output
```bash
mvn clean verify -e
```

---

## 📊 Understanding Test Output

### Console Output Example
```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running Practice.saucedemo.tests.StandaloneTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 45.123 s
Running Practice.saucedemo.tests.ErrorValidation
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 23.456 s

Results:
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
```

### Report Files Generated

After test execution, the following files are created:

| Location | Contents |
|----------|----------|
| `reports/index.html` | ExtentReports HTML report |
| `target/surefire-reports/` | TestNG XML reports |
| `target/surefire-reports/*.txt` | Test summary files |

---

## 🌐 Accessing Test Reports

### Open ExtentReports
```bash
# Windows
start reports/index.html

# Mac
open reports/index.html

# Linux
xdg-open reports/index.html
```

### Report Contents
- ✅ Test execution summary
- ✅ Pass/Fail statistics
- ✅ Screenshots on failure
- ✅ Execution timeline
- ✅ System information
- ✅ Detailed test logs

---

## 🎯 Common Test Scenarios

### Scenario 1: Smoke Testing
Run critical path tests only:
```bash
mvn clean verify -Dgroups=smoke
```

### Scenario 2: Integration Testing
Run related test cases:
```bash
mvn clean verify -Dgroups=purchase,errorhandling
```

### Scenario 3: Regression Testing
Run all tests with detailed reporting:
```bash
mvn clean verify -P testngxmlrun -Dtest=*
```

### Scenario 4: CI/CD Pipeline
Headless execution with minimal output:
```bash
mvn clean verify -Dbrowser=chrome-headless -q
```

---

## 🐛 Debugging Failed Tests

### Step 1: Check Console Output
```bash
mvn clean verify -e
```

The `-e` flag shows full error stack traces.

### Step 2: Review Screenshot
Failed tests capture screenshots in:
```
reports/<TestName>.png
```

### Step 3: Check HTML Report
Open `reports/index.html` and review:
- Exception stack trace
- Screenshot at failure point
- Test execution timeline
- Browser logs

### Step 4: Enable Debug Logging
Add to test code:
```java
System.out.println("Debug: Element locator = " + locator);
```

### Step 5: Rerun Single Test
```bash
mvn clean verify -Dtest=TestClass#testMethod
```

---

## 🔧 Troubleshooting

### Issue: "Unable to find Chrome driver"
**Solution**:
```bash
# WebDriverManager will auto-download, or manually:
mvn dependency:resolve
mvn dependency:download-sources
```

### Issue: "Element not found"
**Solution**:
- Increase wait time in `AbstractComponent`
- Verify element locator is correct
- Check if element is dynamic/loaded via AJAX
- Add explicit waits in test

### Issue: "Tests timeout"
**Solution**:
- Increase timeout in `CommonAbstractComponent.WAIT_TIMEOUT`
- Check internet connection
- Reduce number of parallel threads

### Issue: "Port already in use"
**Solution**:
```bash
# Kill process using port
lsof -ti :8080 | xargs kill -9  # Mac/Linux
netstat -ano | findstr :8080    # Windows
```

---

## 📈 Performance Tips

### Speed Up Test Execution

1. **Use Chrome Headless**
   ```bash
   mvn clean verify -Dbrowser=chrome-headless
   ```

2. **Run in Parallel**
   ```bash
   mvn clean verify -DparallelRun=true -DnumThreads=4
   ```

3. **Skip Browser Maximization**
   Update `BaseTest.initializeDriver()`:
   ```java
   // Remove or comment out:
   // driver.manage().window().maximize();
   ```

4. **Reduce Wait Times** (for stable environments)
   ```java
   private static final int WAIT_TIMEOUT = 5; // from 10
   ```

---

## 📋 TestNG.xml Configuration

Located at: `testSuites/testng.xml`

### Example Configuration
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="SauceDemo Test Suite" parallel="false">
    <listeners>
        <listener class-name="Practice.saucedemo.testComponents.Listeners"/>
    </listeners>
    
    <test name="Purchase Flow Tests">
        <classes>
            <class name="Practice.saucedemo.tests.StandaloneTest"/>
        </classes>
    </test>
    
    <test name="Error Validation Tests">
        <classes>
            <class name="Practice.saucedemo.tests.ErrorValidation"/>
        </classes>
    </test>
</suite>
```

### Parallel Execution
```xml
<suite name="SauceDemo" parallel="methods" thread-count="4">
    <!-- tests -->
</suite>
```

---

## 🔄 Continuous Integration

### Jenkins Integration
```groovy
pipeline {
    agent any
    
    stages {
        stage('Setup') {
            steps {
                sh 'mvn clean install'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn clean verify -Dbrowser=chrome-headless'
            }
        }
        
        stage('Report') {
            steps {
                publishHTML([
                    reportDir: 'reports',
                    reportFiles: 'index.html',
                    reportName: 'Test Report'
                ])
            }
        }
    }
    
    post {
        always {
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'reports/**'
        }
    }
}
```

### GitHub Actions
```yaml
name: Automation Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v2
    - name: Set up Java
      uses: actions/setup-java@v2
      with:
        java-version: '11'
    - name: Run tests
      run: mvn clean verify -Dbrowser=chrome-headless
    - name: Upload reports
      uses: actions/upload-artifact@v2
      with:
        name: test-reports
        path: reports/
```

---

## 📚 Useful Maven Commands

| Command | Purpose |
|---------|---------|
| `mvn clean` | Delete target directory |
| `mvn compile` | Compile source code |
| `mvn test` | Run unit tests only |
| `mvn verify` | Run unit tests + integration tests |
| `mvn install` | Install to local repository |
| `mvn package` | Create JAR/WAR file |
| `mvn dependency:tree` | Display dependency tree |
| `mvn help:active-profiles` | Show active profiles |

---

## ✅ Checklist Before Running Tests

- [ ] Java installed and PATH set correctly
- [ ] Maven installed and PATH set correctly
- [ ] Repository cloned successfully
- [ ] Dependencies installed (`mvn clean install`)
- [ ] Browser installed (Chrome/Firefox/Edge)
- [ ] Internet connection available
- [ ] No other tests running on same ports
- [ ] Reports directory has write permissions

---

## 📞 Getting Help

- **Check logs**: `target/surefire-reports/`
- **Review report**: `reports/index.html`
- **Read documentation**: README.md, POM_ARCHITECTURE.md
- **Enable debug**: `-X` flag in Maven command
- **Stack trace**: Use `-e` flag in Maven command

---

**Version**: 1.0  
**Last Updated**: May 28, 2026

