package com.demo.security.jwt;

import com.demo.security.config.CustomerUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    // Lấy giá trị từ file application.properties
    @Value("${com.security.secret}")
    private String JWT_SECRET_KEY; //mã bí mật
    @Value("${com.security.expiration}")
    private String JWT_EXPIRATION; // thời hạn của jwt

    // Tạo jwt từ thông tin của User
    public  String generateToken(CustomerUserDetails customerUserDetails){
//        Date now = new Date(); //tạo giá trị ngày bắt đầu
//        Date expirationDate = new Date(now.getTime()+JWT_EXPIRATION); // tạo giá trị ngày hết hạn
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDateTime = now.plusDays(Long.parseLong(JWT_EXPIRATION));
        // bắt đầu mã hoá chuỗi jwt
        return  Jwts.builder()
                .setSubject(customerUserDetails.getUsername())
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())) // set ngay bắt đầu
                .setExpiration(Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant())) // set ngày hết hạn token
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY) //chữ ký là mã hoá 512 và key chính là JWT_SECRET ở application.properties
                .compact();
    }
    // Dịch ngược - Lấy thông tin User từ JWT
    public String getUsernameFromJwt    (String token){
        Claims claims= Jwts.parser() //phân tích
                .setSigningKey(JWT_SECRET_KEY)// lấy JWT_SECRET_KEY để mở khoá
                .parseClaimsJws(token)
                .getBody(); //lấy ra thông tin của User

        //trả về thông tin user
        return claims.getSubject();
    }

    //Validate thông tin của JWT
    public Boolean validateToken(String authToken){
            try {
                Jwts.parser().setSigningKey(JWT_SECRET_KEY)
                        .parseClaimsJws(authToken); //phân tích token JWT đã cung cấp
                return true;
            }catch (MalformedJwtException ex) {
                log.error("Invalid JWT Token"); //ko hợp lệ
            }catch (ExpiredJwtException | UnsupportedJwtException e) {
                log.error("Expired or unsupported JWT Token"); //đã hết hạn or ko hỗ trợ
            } catch (IllegalArgumentException ex) {
                log.error("JWT claims String is empty"); // rỗng
            }
            return false;
    }
}
