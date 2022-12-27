# Bring It On

## Objective
For this task, please, use the Selenium WebDriver power, framework unit test, and Page Object concepts. 

Automate the following script:

1. Open https://pastebin.com/ or a similar service in any browser.
2. Create 'New Paste' with the following attributes:
   - Code: 
```shell
git config --global user.name  "New Sheriff in Town"
git reset $(git commit-tree HEAD^{tree} -m "Legacy code")
git push origin master --force
```
   - Syntax Highlighting: "Bash"
   - Paste Expiration: "10 Minutes"
   - Paste Name / Title: "how to gain dominance among developers"
3. Save 'paste' and check the following:
   - Browser page title matches 'Paste Name / Title'
   - Syntax is suspended for bash
   - Check that the code matches the one from paragraph 2