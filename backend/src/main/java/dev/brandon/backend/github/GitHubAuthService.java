package dev.brandon.backend.github;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
public class GitHubAuthService {
    private String privateKeyPath;


    public GitHubAuthService(@Value("${github.app.private-key-path}") String privateKeyPath) throws GeneralSecurityException, IOException {
        this.privateKeyPath = privateKeyPath;
        readPrivateKey();
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


}