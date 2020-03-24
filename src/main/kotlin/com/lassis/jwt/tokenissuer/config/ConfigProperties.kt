package com.lassis.jwt.tokenissuer.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConfigurationProperties("configuration")
@ConstructorBinding
data class ConfigProperties(val realm: String, val key: String, val users: Set<User>)

data class User(val username: String, val password: String, val token: Token)

data class Token(val issuer: String, val scopes: Set<String>, val expiration: Duration)

