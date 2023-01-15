package FileWriters;

import javax.crypto.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    public SecretKey encrypt(FileWriters fileWriters) {
        try {
            Cipher cipher_encrypted = Cipher.getInstance("AES");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecretKey key = keyGenerator.generateKey();
            cipher_encrypted.init(Cipher.ENCRYPT_MODE, key);
            FileInputStream fileInputStream = new FileInputStream(fileWriters.getFilename());
            byte[] cipherText = cipher_encrypted.doFinal(fileInputStream.readAllBytes());
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(fileWriters.getFilename());
            fileOutputStream.write(cipherText);
            fileOutputStream.close();

            return key;
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
