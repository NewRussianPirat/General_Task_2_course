package FileWriters;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Encryption {

    protected String ENCRYPTING_ALGORITHM = "AES";
    protected String KEY = "1111111111111111";
    protected int KEY_OFFSET = 0;
    protected int KEY_LENGTH = 16;
    protected String ENC_TYPE = ".enc";
    protected String MODULE_NAME = "outputFiles";

    public void encrypt(String filename) {
        try {
            Cipher cipherEncrypted = Cipher.getInstance(ENCRYPTING_ALGORITHM);
            SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), KEY_OFFSET, KEY_LENGTH, ENCRYPTING_ALGORITHM);
            FileInputStream fileInputStream = new FileInputStream(filename);
            cipherEncrypted.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherText = cipherEncrypted.doFinal(fileInputStream.readAllBytes());
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(
                    MODULE_NAME +
                            filename.substring(filename.lastIndexOf('/')) +
                            ENC_TYPE
            );
            fileOutputStream.write(cipherText);
            fileOutputStream.close();
        }
        catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 IOException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
