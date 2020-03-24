package com.lassis.jwt.tokenissuer.security.config

import com.lassis.jwt.tokenissuer.config.WebSecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
internal class WebSecurityConfig(
        private val authenticationEntryPoint: SimpleAuthenticationEntryPoint,
        private val webSecurityProperties: WebSecurityProperties,
        private val configProperties: com.lassis.jwt.tokenissuer.config.ConfigProperties
) : WebSecurityConfigurerAdapter() {

    private val defaultRole = "ROLE_issuer"

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                //.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers(*webSecurityProperties.openEndpoints.toTypedArray()).permitAll()
                .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
    }

    override fun configure(auth: AuthenticationManagerBuilder) = auth.inMemoryAuthentication().let { inMemory ->
        configProperties.users.forEach {
            inMemory.withUser(it.username)
                    .password(it.password)
                    .authorities(defaultRole)
        }
    }


    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}

