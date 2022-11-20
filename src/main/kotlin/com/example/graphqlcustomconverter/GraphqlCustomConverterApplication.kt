package com.example.graphqlcustomconverter

import com.fasterxml.jackson.databind.ObjectMapper
import org.javamoney.moneta.Money
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.FormatterRegistrar
import org.springframework.format.FormatterRegistry
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.support.AnnotatedControllerConfigurer
import org.springframework.stereotype.Controller
import javax.money.MonetaryAmount

@SpringBootApplication
class GraphqlCustomConverterApplication

enum class TransactionType { TRANSFER, CHARGE }

data class Transaction(val value: MonetaryAmount, val transactionType: TransactionType)

@Controller
class TransactionResolver {
    @QueryMapping
    fun processTransaction(@Argument transaction: Transaction): Boolean {
        check(transaction.value is Money)
        return true
    }
}

class CustomFormatterRegistrar : FormatterRegistrar {
    override fun registerFormatters(registry: FormatterRegistry) {
        registry.addConverter(MonetaryAmountConverter())
    }
}

class MonetaryAmountConverter : Converter<Map<String, Any>, MonetaryAmount> {
    override fun convert(input: Map<String, Any>) =
        Money.of(input["amount"]!! as Float, input["currency"] as String)
}

@Configuration
class GraphQlConfig(val objectMapper: ObjectMapper, controllerConfigurer: AnnotatedControllerConfigurer) {
    init {
        controllerConfigurer.addFormatterRegistrar(CustomFormatterRegistrar())
    }
}

fun main(args: Array<String>) {
    runApplication<GraphqlCustomConverterApplication>(*args)
}
