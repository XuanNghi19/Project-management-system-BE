package com.dmm.projectManagementSystem.config.security;

import com.dmm.projectManagementSystem.dto.IntrospectResponse;
import com.dmm.projectManagementSystem.model.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    // tạo JWT token gồm 3 phần: header, payload, signature
    public String generateToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getIdNum())
                .issueTime(new Date(System.currentTimeMillis()))
                .expirationTime(new Date(System.currentTimeMillis() + expiration))
                .claim("id", user.getId())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    // Xác thực tính hợp lệ của token
    public IntrospectResponse introspect(String token) throws JOSEException, ParseException {
        // Tạo một bộ xác thực chữ ký (JWSVerifier) sử dụng secretKey
        JWSVerifier verifier = new MACVerifier(secretKey.getBytes());

        // Parse token từ chuỗi JWT thành một đối tượng SignedJWT
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Lấy thời gian hết hạn của token từ phần payload (claims)
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        // Kiểm tra xem chữ ký của token có hợp lệ không
        boolean verified = signedJWT.verify(verifier);

        // Xây dựng và trả về kết quả kiểm tra token
        return IntrospectResponse.builder()
                .valid(verified && expirationTime.after(new Date())) // Token hợp lệ nếu chữ ký đúng và chưa hết hạn
                .build();
    }

}
