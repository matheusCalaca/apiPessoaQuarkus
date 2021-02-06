package br.com.matheusCalaca.user.JWT;

import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.HttpHeaders;

import br.com.matheusCalaca.user.model.RoleEnum;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;


public class UteisToken implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Gera o token para o usuario logado
     *
     * @param cpf
     * @param roles    (regra de acesso)
     * @param duration (duração do token)
     * @param issuer   (Chave privada)
     * @return
     * @throws Exception
     */
    public static String generateToken(String cpf, List<RoleEnum> roles, Long duration, String issuer) throws Exception {
        final String privateKeyLocation = "/privatekey.pem";
        PrivateKey privateKey = readPrivateKey(privateKeyLocation);

        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        long currentTimeInSecs = currentTimeInSecs();

        Set<String> groups = new HashSet<>();
        for (RoleEnum role : roles) groups.add(role.getName());

        claimsBuilder.issuer(issuer);
        claimsBuilder.subject(cpf);
        claimsBuilder.issuedAt(currentTimeInSecs);
        claimsBuilder.expiresAt(currentTimeInSecs + duration);
        claimsBuilder.groups(groups);

        return claimsBuilder.jws().signatureKeyId(privateKeyLocation).sign(privateKey);

    }

    public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
        try (InputStream contentIS = UteisToken.class.getResourceAsStream(pemResName)) {
            byte[] tmp = new byte[4096];
            int length = contentIS.read(tmp);
            return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
        }
    }

    public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    public static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    public static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    public static int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }

    public static String getTokenHeader(HttpHeaders tokenHeader) {
        String authorization = tokenHeader.getRequestHeader("Authorization").get(0);
        if (authorization == null) {
            throw new IllegalArgumentException("Não localizado token");
        }
        return authorization.split("Bearer")[1];
    }

    public static String getCPFByToken(String token) {

        try {
            String json = new String(Base64.getUrlDecoder().decode(token.split("\\.")[1]), StandardCharsets.UTF_8);
            return JwtClaims.parse(json).getSubject();
        } catch (MalformedClaimException e) {
            throw new RuntimeException("Error ao extrair token FALHA: " + e.getMessage());
        } catch (InvalidJwtException e) {
            throw new RuntimeException("Error ao extrair token FALHA: " + e.getMessage());
        }

    }

}
