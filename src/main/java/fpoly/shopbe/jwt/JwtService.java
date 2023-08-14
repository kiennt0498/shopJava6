package fpoly.shopbe.jwt;

import fpoly.shopbe.domain.CustomUserDetails;
import fpoly.shopbe.exception.AccountException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service

public class JwtService {
    @Value(value = "${jwt.secret}")
    private String secret;

    public String accountToken(CustomUserDetails details){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .claim("username",details.getUsername())
                .claim("role", details.getAuthorities())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.ES256, secret)
                .compact();
    }

    public String usernameForToken(String token) {

            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secret) // Sử dụng cùng khóa bí mật để giải mã
                    .parseClaimsJws(token); // Giải mã token

            Claims claims = claimsJws.getBody(); // Truy cập thông tin claims trong token
            return claims.get("username", String.class); // Lấy thông tin username


    }

    public Boolean validateToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(secret) // Sử dụng cùng khóa bí mật để giải mã
                    .parseClaimsJws(token);

            return true;
        }catch (ExpiredJwtException e){
            throw new AccountException(e.getMessage());
        }catch (MalformedJwtException e){
            throw new AccountException(e.getMessage());
        }catch (UnsupportedJwtException e){
            throw new AccountException(e.getMessage());
        }catch (IllegalArgumentException e){
            throw new AccountException(e.getMessage());
        }
    }
}
