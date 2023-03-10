package FileWriters;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileWritersENC extends FileWriters {

    protected static final String ENCRYPTING_ALGORITHM = "AES";
    protected static final String KEY = "1111111111111111";
    protected static final int KEY_OFFSET = 0;
    protected static final int KEY_LENGTH = 16;

    public FileWritersENC(String filename) {
        setFilename(filename);
    }

    public void writeFile(String filename) {
        try {
            Cipher cipherEncrypted = Cipher.getInstance(ENCRYPTING_ALGORITHM);
            SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), KEY_OFFSET, KEY_LENGTH, ENCRYPTING_ALGORITHM);
            FileInputStream fileInputStream = new FileInputStream(filename);
            cipherEncrypted.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherText = cipherEncrypted.doFinal(fileInputStream.readAllBytes());
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(getFilename());
            fileOutputStream.write(cipherText);
            fileOutputStream.close();
        }
        catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 IOException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {}

    @Override
    public boolean isActive() {
        return false;
    }
}
