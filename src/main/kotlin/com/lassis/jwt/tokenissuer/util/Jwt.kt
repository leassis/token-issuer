package com.lassis.jwt.tokenissuer.util


import com.lassis.jwt.tokenissuer.config.ConfigProperties
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths


@Component
class Jwt(private val config: ConfigProperties) {

    private val pvtRSAKey = JWK.parseFromPEMEncodedObjects(Files.readString(Paths.get(config.key))) as RSAKey
    private val signer = RSASSASigner(pvtRSAKey)

    val publicJWK: RSAKey = pvtRSAKey.toPublicJWK()

    fun sign(jwtClaimsSet: JWTClaimsSet): SignedJWT {
        val header = JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(pvtRSAKey.keyID)
                .build()

        val signedJWT = SignedJWT(header, jwtClaimsSet)
        signedJWT.sign(signer)
        return signedJWT
    }

}

