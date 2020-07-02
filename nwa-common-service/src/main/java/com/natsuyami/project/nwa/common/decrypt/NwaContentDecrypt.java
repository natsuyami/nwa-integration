package com.natsuyami.project.nwa.common.decrypt;

import com.natsuyami.project.nwa.common.encrypt.NwaPasswordEncrypt;
import com.natsuyami.project.nwa.common.encrypt.NwaSecretKey;
import java.util.Base64;
import javax.crypto.Cipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NwaContentDecrypt {
  private static final Logger LOGGER = LoggerFactory.getLogger(NwaContentDecrypt.class);

  /**
   *
   * @param strToEncrypt - stored password
   * @param secret - {code} entered as 2nd layer password
   * @param strToDecrypt - stored password
   * @return - decrypt password
   */
  public static String decrypt(String strToEncrypt, String secret, String strToDecrypt) {
    LOGGER.info("Initialized encrypt for base decryption of password and secret");
    String[] encryptions = NwaPasswordEncrypt.originalEncryption(strToEncrypt, secret);
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
