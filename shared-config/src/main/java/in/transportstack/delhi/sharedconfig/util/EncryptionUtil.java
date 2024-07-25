package in.transportstack.delhi.sharedconfig.util;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {
    @Value("${encryptionUtil.key}")
    private static String encryptionKey;

    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = encryptionKey.getBytes();

    private EncryptionUtil() {
    }

    public static String encrypt(String valueToEnc) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128);
        SecretKey secretKey = new SecretKeySpec(keyValue, ALGORITHM);
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encValue = c.doFinal(valueToEnc.getBytes());
        return Base64.getEncoder().encodeToString(encValue);
    }

    public static String decrypt(String encryptedValue) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128);
        SecretKey secretKey = new SecretKeySpec(keyValue, ALGORITHM);
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decValue = Base64.getDecoder().decode(encryptedValue);
        byte[] decryptedValue = c.doFinal(decValue);
        return new String(decryptedValue);
    }
}
