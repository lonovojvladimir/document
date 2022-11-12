package ru.com.document;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.document.application.security.jwt.JwtConfig;
import ru.com.document.application.security.jwt.JwtUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class BaseControllerTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    private JwtConfig jwtConfig;

    @LocalServerPort
    private int randomServerPort;

    protected abstract String getApiUrl();

    protected String url() {
        return url("");
    }

    protected String url(String url) {
        return "http://localhost:" + randomServerPort + "/api/v1/wps/" + getApiUrl() + url;
    }

    protected <DTO> HttpEntity<DTO> getHttpEntity(DTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(JwtUtil.AUTHORIZATION, generateToken());
        return new HttpEntity<>(dto, headers);
    }

    private String generateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put(jwtConfig.getJwt().getKeys().getUser(), "test");
        claims.put(jwtConfig.getJwt().getKeys().getRoles(), "WPS-USER,WPS-ADMIN");
        claims.put(jwtConfig.getJwt().getKeys().getName(), "test");
        return Jwts.builder().setClaims(claims)
                .setSubject(jwtConfig.getJwt().getSubject())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getJwt().getTtl()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getJwt().getSecret()).compact();
    }
}
