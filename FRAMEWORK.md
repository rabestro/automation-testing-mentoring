# Automation Framework

## Practical Task
Develop an automation framework for the [Hardcore task](selenium-task-4) in WebDriver.
The final framework should include the following:

1. [x] A WebDriver manager for managing browser connectors
2. [x] Page Object/Page Factory for page abstractions
3. [x] Models for business objects of the required elements
4. [x] Property files with test data for at least two different environments
5. [x] XML suites for smoke tests and other tests
6. [x] A screenshot with the date and time is taken if the test fails.
7. [x] The framework should include an option for running with Jenkins and browser parameterization, test suite, and environment.
8. [x] Test results should be displayed on the job chart, and the screenshots should be archived as artefacts.

## How to run test script

First, you need to update all the dependencies for the test script. To do this, run the following command in the console:
```shell
mvn --update-snapshots clean install
```
In case if you want to install only `selenium-pages` and skip all it's tests:
```shell
mvn clean install --projects selenium-pages --update-snapshots -DskipTests
```
After the successful execution of the command, you can run tests in a specific subproject. To run the tests of this module, run the following command:
```shell
mvn test --projects selenium-task-4
```


## Solution description

### WebDriver manager

```shell
mvn test --projects selenium-task-4 -Denvironment=dev
```
### Suites for smoke tests and other tests

### Run Smoke Tests

```shell
mvn test -Dgroups="SmokeTest"
```

```shell
mvn test --projects selenium-pages -Dinclude=SmokeTestSuit
```

### Run Unit Tests

```shell
mvn test -Dgroups=fast --projects selenium-pages
```

```shell
mvn test --projects selenium-pages -Dinclude=UnitTestSuit
```
