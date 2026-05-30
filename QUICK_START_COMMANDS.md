# Quick Start Commands

## 🚀 Build & Compile

```bash
# Clean install (download dependencies, compile)
mvn clean install

# Compile only (skip tests)
mvn -q clean compile

# Force dependency update
mvn -U dependency:resolve
```

## 🧪 Run Tests

```bash
# Run all tests with default browser (Chrome)
mvn clean verify

# Run specific test class
mvn clean verify -Dtest=StandaloneTest

# Run multiple test classes
mvn clean verify -Dtest=StandaloneTest,ErrorValidation

# Run specific test method
mvn clean verify -Dtest=StandaloneTest#completePurchaseFlow
```

## 🌐 Run with Different Browsers

```bash
# Chrome (default)
mvn clean verify -Dbrowser=chrome

# Chrome Headless (CI/CD friendly)
mvn clean verify -Dbrowser=chrome-headless

# Firefox
mvn clean verify -Dbrowser=firefox

# Edge
mvn clean verify -Dbrowser=edge
```

## 📊 Run by Test Profile/Suite

```bash
# Default suite (testng.xml)
mvn clean verify -P testngxmlrun

# Error handling suite
mvn clean verify -P errorhandeling

# Purchase order suite
mvn clean verify -P Purchaseorder
```

## 📜 View Logs

```bash
# Display logs file
type logs\automation.log

# Tail last 200 lines (PowerShell)
Get-Content .\logs\automation.log -Tail 200

# Follow live log output (PowerShell, Ctrl+C to exit)
Get-Content .\logs\automation.log -Tail 200 -Wait
```

## 📈 View Reports

```bash
# Open ExtentReports HTML report (Windows)
start reports\index.html

# Open via file manager
explorer reports\index.html

# File location
C:\Users\CODECLOUDS-RAHUL\IdeaProjects\selenium_sauce\reports\index.html
```

## 🔍 Check Dependencies

```bash
# Show dependency tree
mvn dependency:tree

# Show available updates
mvn versions:display-dependency-updates

# Check for vulnerabilities
mvn org.sonatype.ossindex.maven:ossindex-maven-plugin:audit
```

## 🧹 Clean Up

```bash
# Remove build artifacts
mvn clean

# Remove old logs (keep last file)
Remove-Item logs\*.log -Exclude automation.log

# Remove old reports (keep index.html)
Remove-Item reports\*.png
Remove-Item reports\*.html -Exclude index.html
```

## 📋 Complete Test Run Example

```bash
# Full build, test, report generation
cd C:\Users\CODECLOUDS-RAHUL\IdeaProjects\selenium_sauce
mvn clean verify -Dtest=StandaloneTest -Dbrowser=chrome

# Check results
Get-Content .\logs\automation.log -Tail 50
start reports\index.html
```

## ⚡ Quick Verification (No Tests)

```bash
# Just compile (fast check)
mvn -q clean compile

# If this succeeds, code is syntactically correct
```

## 🎯 Common Scenarios

### Scenario 1: Developer Quick Check
```bash
mvn -q clean compile
```

### Scenario 2: Full Test Run with Reporting
```bash
mvn clean verify
start reports\index.html
```

### Scenario 3: Debug Single Test
```bash
mvn clean verify -Dtest=StandaloneTest#completePurchaseFlow -X
Get-Content .\logs\automation.log -Tail 200 -Wait
```

### Scenario 4: CI/CD Pipeline (Headless)
```bash
mvn clean verify -Dbrowser=chrome-headless -q
```

### Scenario 5: Performance Testing (Parallel)
```bash
mvn clean verify -DparallelRun=true -DnumThreads=4
```

---

**All commands are ready to copy-paste into PowerShell!**

