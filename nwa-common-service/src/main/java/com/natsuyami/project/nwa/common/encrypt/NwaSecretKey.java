package com.natsuyami.project.nwa.common.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NwaSecretKey {
  private static final Logger LOGGER = LoggerFactory.getLogger(NwaSecretKey.class);

  private SecretKeySpec secretKey;
  private static byte[] key;

  protected SecretKeySpec getSecretKey() {
    return this.secretKey;
  }

  protected void generateKey(String baseEncryption, int intKey) {
    LOGGER.info("generate key for encryption and decryption");

    MessageDigest sha = null;

    String myKey = this.generateSecret(baseEncryption, intKey);
    try {
      key = myKey.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-256");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      this.secretKey = new SecretKeySpec(key, "AES");
    } catch(NoSuchAlgorithmException e) {
      LOGGER.error("Error in Algorithm, cannot encrypt");
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      LOGGER.error("Error in  encoding, UTF-8 not working");
      e.printStackTrace();
    }
  }

  protected static String generateSecret(String hexStr, int intSecret) {
    LOGGER.info("generate secret for encryption and decryption");

    int intOperation = (intSecret * 8) + Integer.parseInt(hexStr, 16);
    String secret = Integer.toHexString(intOperation);
    if (secret.length() > 255) {
      secret = secret.substring(0, 255);
    }

    return NwaEncryptionLayer.commonHash(secret);
  }

}
