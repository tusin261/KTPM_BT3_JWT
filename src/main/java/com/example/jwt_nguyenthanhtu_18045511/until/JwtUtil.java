package com.example.jwt_nguyenthanhtu_18045511.until;

import com.example.jwt_nguyenthanhtu_18045511.authen.UserPrincipal;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String USER = "Tu";
    private static final String SECRET = "the secrect length must be at least 256 bits";

    public String generateToken(UserPrincipal user){
        String token = null;

        try {
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim(USER, user);
            builder.expirationTime(generateExpirationDate());

            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256),claimsSet);
            JWSSigner signer = new MACSigner(SECRET.getBytes());
            signedJWT.sign(signer);

            token = signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + 864000000);
    }

}
