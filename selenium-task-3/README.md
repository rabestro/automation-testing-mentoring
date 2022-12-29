# Hurt Me Plenty

For this task, please, use the Selenium WebDriver power, framework unit test, and Page Object concepts. Automate the following script:

1. Open https://cloud.google.com/.
2. Click the search button at the top of the portal page and enter "Google Cloud Platform Pricing Calculator" into the search field.
3. Click on the search button to start searching.
4. Click "Google Cloud Platform Pricing Calculator" in the search results and go to the calculator page.
5. Click COMPUTE ENGINE at the top of the page.
6. Fill out the form with the following data:
   * Number of instances: 4
   * What are these instances for?: leave blank
   * Operating System / Software: Free: Debian, CentOS, CoreOS, Ubuntu, or another User-Provided OS
   * VM Class: Regular
   * Instance type: n1-standard-8 (vCPUs: 8, RAM: 30 GB)
   * Select “Add GPUs”
     * Number of GPUs: 1
     * GPU type: NVIDIA Tesla V100
   * Local SSD: 2x375 Gb
   * Datacenter location: Frankfurt (europe-west3)
   * Committed usage: 1 Year

7. Click Add to Estimate.
8. Check that the data is correct in the following fields: VM Class, Instance type, Region, local SSD, commitment term.
9. Check that the monthly rent matches the amount received when the test is done manually.