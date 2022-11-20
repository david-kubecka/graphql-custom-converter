package com.example.graphqlcustomconverter

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.HttpGraphQlTester

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureHttpGraphQlTester
class GraphqlCustomConverterApplicationTests(@Autowired val graphQlTester: HttpGraphQlTester) {

    @Test
    fun `transaction value is properly converted`() {
        val query = """query { processTransaction(transaction: {value: {currency: "EUR", amount: 1.0}}) }"""

        graphQlTester
            .document(query)
            .executeAndVerify()
    }
}
