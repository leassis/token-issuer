package com.lassis.jwt.tokenissuer.web

import com.lassis.jwt.tokenissuer.config.ConfigProperties
import com.lassis.jwt.tokenissuer.config.Token
import com.lassis.jwt.tokenissuer.constants.SCOPE_CLAIM
import com.lassis.jwt.tokenissuer.pojo.JwtInfo
import com.lassis.jwt.tokenissuer.util.Jwt
import com.nimbusds.jose.util.JSONObjectUtils
import com.nimbusds.jwt.JWTClaimsSet
import net.minidev.json.JSONObject
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.time.Instant
import java.util.*
import javax.servlet.http.HttpServletResponse


@Controller
class JWKController(private val jwt: Jwt, private val configProperties: ConfigProperties) {

    @PostMapping("/authorize")
    fun jwt(@RequestBody jwtInfo: JwtInfo, @AuthenticationPrincipal user: User): ResponseEntity<JSONObject> = getTokenConfig(user.username).let { cfgToken ->
        val claimsSet = JWTClaimsSet.Builder()
                .subject(jwtInfo.sub)
                .issuer(cfgToken.issuer)
                .expirationTime(Date.from(Instant.now().plus(cfgToken.expiration)))
                .claim(SCOPE_CLAIM, cfgToken.scopes)
                .build()

        val signedJWT = jwt.sign(claimsSet)
        val json = JSONObjectUtils.parse("""{"accessToken": "${signedJWT.serialize()}"}""")

        return ResponseEntity.ok(json)
    }


    @GetMapping("/jwks/keys")
    fun keys(response: HttpServletResponse): ResponseEntity<JSONObject> =
            ResponseEntity.ok(JSONObjectUtils.parse("""{"keys": [${jwt.publicJWK.toJSONString()}]}"""))


    private fun getTokenConfig(username: String): Token = configProperties.users.first { u -> u.username == username }.token

}
