# Automated Testing Mentoring with Java: Basic

The repository contains my solutions for practical tasks of the course.

1. Introduction to Test Automation
2. Build Tools
3. Clean Code & Code Quality Gates
   - [AirCompany](aircompany)
4. Unit Testing Frameworks
   - [Calculator](calculator)
5. Selenium WebDriver
   - [I Can Win](selenium-task-1)
   - [Bring It On](selenium-task-2)
   - [Hurt Me Plenty](selenium-task-3)
   - [Hardcore](selenium-task-4)
6. Selenium WebDriver Advanced:
   - Selenium [Support Classes](course-support-classes)
   - Actions, JavaScript Executor,
   - Selenium Server + Selenium Grid
7. TA Frameworks:
   - Layers, Runner, Business Objects
   - [Practical Task](FRAMEWORK.md)
8. Design Patterns in TA Frameworks implementation
9. Automated testing of Web Services: best practices
    - [Practical Task](rest-api-task)
10. BDD TA Frameworks
11. Automated testing of Mobile Applications: best practices
12. [Continuous Integration with Jenkins](jenkins-task)
13. Results Analysis and Reporting in TA. Integration with reporting tools

## How to run all tests and publish site

```shell
mvn clean site site:stage scm-publish:publish-scm
```
