package com.natsuyami.project.nwa.common.encrypt;

import java.util.Base64;
import javax.crypto.Cipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NwaPasswordEncrypt {
  private static final Logger LOGGER = LoggerFactory.getLogger(NwaPasswordEncrypt.class);

  /**
   *
   * @param password - password to one way hash with many layers of algo
   * @param secret - {code} to one way hash as 2nd level
   * @return list of encrypted values used for last layer of encryption of password and secret
   */
  public static String[] originalEncryption(String password, String secret) {
    LOGGER.info("Initialized originalEncryption for additional layer for password and secret");
    String[] result = new String[2];
    StringBuilder secretReverse =new StringBuilder();

    if (password.isEmpty() && secret.isEmpty()) {
      return result;
    }

    secretReverse.append(secret);
    secretReverse.reverse();

    secret= secretReverse.toString();
    password = NwaEncryptionLayer.shuffleString(password);

    result[0] = NwaEncryptionLayer.hashString(password, NwaEncryptionLayer.generateSalt()); // password with the salt separated by .
    result[1] = secret; //use for generate secret
    return result;
  }

  /**
   *
   * @param strToEncrypt - {password} string to be encrypted and to be hashed
   * @param secret - {code} 2nd layer password for encryption
   * @return - encrypted password
   */
  public static String encrypt(String strToEncrypt, String secret) {
    LOGGER.info("Initialized encrypt for base encryption of password and secret");
    NwaSecretKey nwaSecretKey =  new NwaSecretKey();

    try {
      nwaSecretKey.generateKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, nwaSecretKey.getSecretKey());
      return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    } catch (Exception e) {
      LOGGER.error("Unavailable to encypt the strToEncrypt={}", strToEncrypt);
      e.printStackTrace();
    }
    return null;
  }

  /**
   *
   * @param secret - {code} entered as 2nd layer password
   * @param strToDecrypt - stored password
   * @return - decrypt password
   */
  public static String decrypt(String secret, String strToDecrypt) {
    LOGGER.info("Initialized encrypt for base decryption of password and secret");

    NwaSecretKey nwaSecretKey =  new NwaSecretKey();
    try {
      nwaSecretKey.generateKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, nwaSecretKey.getSecretKey());
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      LOGGER.error("Unavailable to decrpyt the strToEncrypt={}", strToDecrypt);
      e.printStackTrace();
    }
    return "";
  }
}
