package com.lassis.jwt.tokenissuer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class TokenIssuerApplication

fun main(args: Array<String>) {
	runApplication<TokenIssuerApplication>(*args)
}
