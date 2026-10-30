package dev.brandon.backend.github;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;

@Service
public class GitHubAuthService {
    private String privateKeyPath;
    private String clientId;


    public GitHubAuthService(@Value("${github.app.private-key-path}") String privateKeyPath, @Value("${github.app.client-id}") String clientId) {
        this.privateKeyPath = privateKeyPath;
        this.clientId = clientId;

    }


    public PrivateKey readPrivateKey() throws IOException, GeneralSecurityException {
        String pemContents = Files.readString(Path.of(privateKeyPath));

        String encodedKey = pemContents.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] keyBytes = Base64.getDecoder().decode(encodedKey);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        return privateKey;
    }

    public String generateAppJWT() throws IOException, GeneralSecurityException {
        PrivateKey privateKey = readPrivateKey();

        Algorithm algorithm = Algorithm.RSA256(null, (RSAPrivateKey) privateKey);

        Instant now = Instant.now();

        Instant issuedAt = now.minusSeconds(60);
        Instant expiresAt = now.plusSeconds(540);

        return JWT.create()
                .withIssuer(clientId)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }


}