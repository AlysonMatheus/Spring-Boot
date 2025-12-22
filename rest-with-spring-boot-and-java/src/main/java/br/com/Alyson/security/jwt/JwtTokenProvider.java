package br.com.Alyson.security.jwt;

import br.com.Alyson.data.dto.security.TokenDTO;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-lenght: 3600000")
    private long validityInMilliseconds = 3600000;// 1Hora

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    // Método executado automaticamente após a injeção de dependências (@PostConstruct)
// Aqui ele prepara a chave secreta e o algoritmo que será usado para assinar os tokens JWT
    @PostConstruct
    protected void init() {
        // Converte a chave secreta para Base64 (boa prática para JWT)
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
        // Implementação futura da geração do Refresh Token
        return "";
    }

    // Método responsável por gerar o Access Token (JWT)
// Ele geralmente contém usuário, roles, data de criação e expiração
    private String getAccesToken(String username, List<String> roles, Date now, Date validity) {
        // Implementação futura da geração do Access Token
        return "";
    }



}
