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
    String[] result = new String[3];

    if (password.isEmpty() && secret.isEmpty()) {
      return result;
    }

    String hexPass = NwaEncryptionLayer.concatRandomHexa(NwaEncryptionLayer.convertToHex(password)); // get some random hexa from string
    String hexSecret = NwaEncryptionLayer.concatRandomHexa(NwaEncryptionLayer.convertToHex(secret)); // get some random hexa from string
    int hexToIntPass = Integer.parseInt(hexPass, 16);
    int hexToIntSecret = Integer.parseInt(hexSecret, 16);
    int hexOperation = hexToIntPass - hexToIntSecret;

    if (hexOperation < 1) {
      hexOperation = (hexToIntSecret/hexToIntPass);
    }
    result[0] = Integer.toHexString(hexOperation * 8); //use for generate secret
    result[1] = String.valueOf(hexToIntSecret); //use for generate secret
    result[2] = NwaEncryptionLayer.hashString(result[0], NwaEncryptionLayer.generateSalt()); // password with the salt separated by .
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
    String[] encryptions = originalEncryption(strToEncrypt, secret);
    NwaSecretKey nwaSecretKey =  new NwaSecretKey();

    try {
      nwaSecretKey.generateKey(encryptions[0], Integer.parseInt(encryptions[1]));
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, nwaSecretKey.getSecretKey());
      return Base64.getEncoder().encodeToString(cipher.doFinal(encryptions[2].getBytes("UTF-8")));
    } catch (Exception e) {
      LOGGER.error("Unavailable to encypt the strToEncrypt={}", encryptions[2]);
      e.printStackTrace();
    }
    return null;
  }

  /**
   *
   * @param strToEncrypt - stored password
   * @param secret - {code} entered as 2nd layer password
   * @param strToDecrypt - stored password
   * @return - decrypt password
   */
  public static String decrypt(String strToEncrypt, String secret, String strToDecrypt) {
    LOGGER.info("Initialized encrypt for base decryption of password and secret");
    String[] encryptions = originalEncryption(strToEncrypt, secret);
    NwaSecretKey nwaSecretKey =  new NwaSecretKey();

    try {
      nwaSecretKey.generateKey(encryptions[0], Integer.parseInt(encryptions[1]));
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
