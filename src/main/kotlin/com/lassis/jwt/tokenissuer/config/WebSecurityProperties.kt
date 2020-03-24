package com.lassis.jwt.tokenissuer.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("web.security")
@ConstructorBinding
data class WebSecurityProperties(val openEndpoints: Set<String>)
