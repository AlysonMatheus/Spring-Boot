package br.com.Alyson.security.jwt;

import br.com.Alyson.Exception.InvalidJwtAuthenticationException;
import br.com.Alyson.data.dto.security.TokenDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token. expire-length:3600000}")
    private long validityInMilliseconds = 3600000;// 1Hora

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    // Método executado automaticamente após a injeção de dependências (@PostConstruct)
// Aqui ele prepara a chave secreta e o algoritmo que será usado para assinar os tokens JWT
    @PostConstruct
    protected void init() {
        // Converte a chave secreta para Base64
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());

        // Cria o algoritmo HMAC256 usando a chave secreta
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    // Método responsável por criar e retornar um objeto TokenDTO
// Ele gera tanto o Access Token quanto o Refresh Token
    public TokenDTO createAccessToken(String username, List<String> roles) {
        // Data e hora atual
        Date now = new Date();

        // Data de expiração do token (agora + tempo de validade)
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        // Gera o Access Token
        String accessToken = getAccesToken(username, roles, now, validity);

        // Gera o Refresh Token
        String refreshToken = getRefreshToken(username, roles, now);

        // Retorna os dados do token encapsulados no TokenDTO
        return new TokenDTO(username, true, now, validity, accessToken, refreshToken);
    }

    // Método responsável por gerar o Refresh Token
// Normalmente possui validade maior e menos informações que o Access Token
    private String getRefreshToken(String username, List<String> roles, Date now) {


        Date refreshTokenValidity = new Date(now.getTime() + (validityInMilliseconds * 3));

        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(refreshTokenValidity)
                .withSubject(username)
                .sign(algorithm);
    }

    // Método responsável por gerar o Access Token (JWT)

    private String getAccesToken(String username, List<String> roles, Date now, Date validity) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString();
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm);
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.isEmpty(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring("Bearer".length());
        } else {
            throw new InvalidJwtAuthenticationException("Invalid JWT Token");
        }

    }

    public boolean validateToken(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        try {
            if (decodedJWT.getExpiresAt().before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException(" Expired or Invalid JWT Token");
        }
    }

}
