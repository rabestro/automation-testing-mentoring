import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Should return a student")
    request {
        method GET()
        url "/students/1"
    }
    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(id: 1, name: "Mark", grade: 10)
    }
}