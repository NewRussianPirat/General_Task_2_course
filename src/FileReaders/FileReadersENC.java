package FileReaders;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class FileReadersENC extends FileReaders {

    public FileReadersENC() { super(); }
    public FileReadersENC(String filename1) {
        super(filename1);
    }

    @Override
    protected ArrayList<String> getInfo() {
        return null;
    }

    @Override
    protected FileReaders unpacking() {
        return this;
    }

    @Override
    protected FileReaders decrypting() {
        try {
            if (!this.isEncrypted()) {
                return this;
            }
            Cipher cipherDecrypted = Cipher.getInstance("AES");
            SecretKeySpec key = new SecretKeySpec("1111111111111111".getBytes(), 0, 16, "AES");
            FileInputStream fileInputStream = new FileInputStream(this.getFilename());
            cipherDecrypted.init(Cipher.DECRYPT_MODE, key);
            byte[] cipherDecryptedText = cipherDecrypted.doFinal(fileInputStream.readAllBytes());
            fileInputStream.close();
            String filename = "intermediateFiles\\" + this.getFilename().substring(
                    Math.max(0, FileReaders.filename.lastIndexOf('\\')),
                    FileReaders.filename.lastIndexOf('.')
            );
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.close();
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            fileOutputStream.write(cipherDecryptedText);
            fileOutputStream.close();

            return createFileReader(filename);

        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 IOException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean isPacked() {
        return false;
    }

    @Override
    protected boolean isEncrypted() {
        return true;
    }
}
