# Automated testing of web services

## Task
1.	Create a test to verify an http status code:
      o	Send the http request by using the GET method.
      o	The URL is https://jsonplaceholder.typicode.com/users
      o	Validation: status code of the obtained response is 200 OK
2.	Create a test to verify an http response header:
      o	Send the http request by using the GET method.
      o	The URL is https://jsonplaceholder.typicode.com/users
      o	Validation: - the content-type header exists in the obtained response
      - the value of the content-type header is application/json; charset=utf-8
3.	Create a test to verify an http response body:
      o	Send the http request by using the GET method:
      o	The URL is https://jsonplaceholder.typicode.com/users
      o	Validation: the content of the response body is the array of 10 users

## Acceptance criteria
1.	Tests should be created using either Rest Assured or Spring Rest Template.
2.	Tests have to include validations that are given.
3.	Implemented tests should be readable.
4.	Tests must be implemented so that they could be launched in parallel.
5.	Naming and Code Conventions should be followed.
6.	As for tests of the bonus task, they should be created to test CRUD operations of the given resource.
