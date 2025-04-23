package utils;

import com.zebrunner.carina.utils.encryptor.EncryptorUtils;

public class Encryptor {
    public static void main(String[] args) {
        String valueToEncrypt = "locked_user";
        String encrypted = EncryptorUtils.encrypt(valueToEncrypt);
        System.out.println("Encrypted value: " + encrypted);
    }
}
