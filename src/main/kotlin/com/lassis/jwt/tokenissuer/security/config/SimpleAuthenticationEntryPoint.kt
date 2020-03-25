package com.lassis.jwt.tokenissuer.security.config

import com.lassis.jwt.tokenissuer.config.ConfigProperties
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class SimpleAuthenticationEntryPoint(private val config: ConfigProperties) : BasicAuthenticationEntryPoint() {

    override fun afterPropertiesSet() = run { realmName = config.realm }
}
