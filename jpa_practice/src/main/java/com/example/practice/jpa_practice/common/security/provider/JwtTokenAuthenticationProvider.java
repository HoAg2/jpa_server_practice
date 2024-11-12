package com.example.practice.jpa_practice.common.security.provider;

import com.example.practice.jpa_practice.common.exception.ErrorException;
import com.example.practice.jpa_practice.common.security.CustomUser;
import com.example.practice.jpa_practice.common.util.StringUtils;
import com.example.practice.jpa_practice.common.web.ResponseType;
import com.example.practice.jpa_practice.config.prop.JwtTokenProp;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenProp jwtTokenProp;

    private static final String ID_KEY = "I";
    private static final String ROLE_KEY = "R";
    private static final String SERVICE_KEY = "V";

    public Authentication createAuthentication(String token) {

        Claims claims = parse(token);
        String id = (String)claims.get(ID_KEY);
        String role = (String)claims.get(ROLE_KEY);
        String serviceId = (String)claims.get(SERVICE_KEY);

        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(String.valueOf(role)));
        CustomUser principal = new CustomUser(id, token, List.of(serviceId), authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtTokenProp.getHeaderString());
        if (bearerToken != null && bearerToken.startsWith(StringUtils.concat(jwtTokenProp.getTokenType(), " "))) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        if (token == null) throw new ErrorException(ResponseType.INVALID_ACCESS);
        parse(token);
        return true;
    }

    private Claims parse(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtTokenProp.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();

        } catch (SecurityException | IllegalArgumentException | MalformedJwtException | UnsupportedJwtException |
                 ExpiredJwtException e) {
            throw new ErrorException(ResponseType.INVALID_ACCESS);
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }



}
