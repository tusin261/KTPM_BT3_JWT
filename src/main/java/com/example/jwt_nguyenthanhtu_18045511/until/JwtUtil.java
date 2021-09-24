package com.example.jwt_nguyenthanhtu_18045511.until;

import com.example.jwt_nguyenthanhtu_18045511.authen.UserPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.text.ParseException;
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


    private JWTClaimsSet getClaimsFromToken(String token){
        JWTClaimsSet claimsSet = null;

        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET.getBytes());
            if(signedJWT.verify(verifier)){
                claimsSet = signedJWT.getJWTClaimsSet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claimsSet;
    }

    public UserPrincipal getUserFromToken(String token){
        UserPrincipal userPrincipal = null;

        try {
            JWTClaimsSet claimsSet = getClaimsFromToken(token);
            if(claimsSet !=null && isTokenExpired(claimsSet)){
                JSONObject jsonObject = (JSONObject) claimsSet.getClaim(USER);
                userPrincipal = new ObjectMapper().readValue(jsonObject.toJSONString(), UserPrincipal.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return userPrincipal;
    }

    private Date getExpirationDateFromToken(JWTClaimsSet claims) {
        return claims != null ? claims.getExpirationTime() : new Date();
    }

    //--------------------isTokenExpired-------------------------
    private boolean isTokenExpired(JWTClaimsSet claims) {
        return getExpirationDateFromToken(claims).after(new Date());
    }

}
