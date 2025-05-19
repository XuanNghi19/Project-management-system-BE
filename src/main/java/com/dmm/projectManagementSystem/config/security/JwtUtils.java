package com.dmm.projectManagementSystem.config.security;

import com.dmm.projectManagementSystem.dto.IntrospectResponse;
import com.dmm.projectManagementSystem.enums.ErrorCode;
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
                .claim("role", user.getRole())
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

    public String getIdNumFromToken(String token) {
        try {
            return SignedJWT.parse(token).getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Không trích xuất được idNum từ token");
        }
    }

    // Xác thực tính hợp lệ của token
    public IntrospectResponse introspect(String token) throws JOSEException, ParseException {
        // Tạo một bộ xác thực chữ ký (JWSVerifier) sử dụng secretKey
        JWSVerifier verifier = new MACVerifier(secretKey.getBytes());

        try {
            // Parse token từ chuỗi JWT thành một đối tượng SignedJWT
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Lấy thời gian hết hạn của token từ phần payload (claims)
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            // Kiểm tra xem chữ ký của token có hợp lệ không
            boolean verified = signedJWT.verify(verifier);

            // Xây dựng và trả về kết quả kiểm tra token
            if(expirationTime.after(new Date())) {
                if(verified) {
                    return IntrospectResponse.builder()
                            .valid(true)
                            .build();
                } else {
                    return IntrospectResponse.builder()
                            .valid(false)
                            .errorCode(ErrorCode.TOKEN_INVALID)
                            .errorMessage(ErrorCode.TOKEN_INVALID.name())
                            .build();
                }
            } else {
                return IntrospectResponse.builder()
                        .valid(false)
                        .errorCode(ErrorCode.TOKEN_EXPIRED)
                        .errorMessage(ErrorCode.TOKEN_EXPIRED.name())
                        .build();
            }

        } catch (ParseException ex) {
            return IntrospectResponse.builder()
                    .valid(false)
                    .errorCode(ErrorCode.TOKEN_INVALID)
                    .errorMessage(ErrorCode.TOKEN_INVALID.name())
                    .build();
        }

    }

}
