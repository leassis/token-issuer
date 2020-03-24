package com.lassis.jwt.tokenissuer.util


import com.lassis.jwt.tokenissuer.config.ConfigProperties
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component


@Component
class Jwt(config: ConfigProperties, resourceLoader: ResourceLoader) {

    private val pvtRSAKey = JWK.parseFromPEMEncodedObjects(read(resourceLoader.getResource(config.key))) as RSAKey

    private val signer = RSASSASigner(pvtRSAKey)

    val publicJWK: RSAKey = pvtRSAKey.toPublicJWK()

    private fun read(resource: Resource): String = resource.file.readText(Charsets.UTF_8)

    fun sign(jwtClaimsSet: JWTClaimsSet): SignedJWT {
        val header = JWSHeader.Builder(JWSAlgorithm.RS256)
            .keyID(pvtRSAKey.keyID)
            .build()

        val signedJWT = SignedJWT(header, jwtClaimsSet)
        signedJWT.sign(signer)
        return signedJWT
    }

}

