package ru.com.document.application.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.com.document.application.security.CustomUserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class JwtUtil {

    /**
     * Bearer token header key
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * Mandatory 'Bearer' prefix.
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Mandatory 'Bearer' prefix length.
     */
    public static final int TOKEN_PREFIX_LEN = TOKEN_PREFIX.length();

    private final JwtConfig jwtConfig;

    /**
     * Gets claims from given token.
     *
     * @param token - String;
     * @return - Claims.
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                   .setSigningKey(jwtConfig.getJwt().getSecret())
                   .parseClaimsJws(token)
                   .getBody();
    }

    private String userFromClaims(Claims claims) {
        return claims.get(jwtConfig.getJwt().getKeys().getUser(), String.class);
    }

    private List<String> rolesFromClaims(Claims claims) {
        return Arrays.asList(
                claims.get(jwtConfig.getJwt().getKeys().getRoles(), String.class)
                      .split(",")
        );
    }

    private List<GrantedAuthority> getGrantedAuthorityList(Claims claims) {
        return rolesFromClaims(claims)
                .stream()
                .filter(Objects::nonNull)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public CustomUserDetails getCustouserDetails(String token) {
        Claims claims = getClaimsFromToken(token);
        return new CustomUserDetails(
                userFromClaims(claims),
                getGrantedAuthorityList(claims)
        );
    }

}
