package com.natsuyami.project.nwa.common.encrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NwaEncryptionLayer {
  private static final Logger LOGGER = LoggerFactory.getLogger(NwaEncryptionLayer.class);
  private static final Random RANDOM = new SecureRandom();

  protected static String convertToHex(String str) {
    LOGGER.info("Initialized convertToHex for string to hex conversion");

    char[] strArray = str.toCharArray();
    String hexa = "";

    for (int a=0; a < strArray.length; a++) {
      hexa += Integer.toHexString(strArray[a]);
    }

    return hexa;
  }

  protected static String concatRandomHexa(String hexa) {
    LOGGER.info("Initialized concatRandomHexa for random generated hex, based from given hexadecimal");

    int strLength = hexa.length();
    int strHalfLength = (strLength/2) - 1;
    String firstHexa = hexa.substring(0, 2);
    String secHexa = hexa.substring(strHalfLength, strHalfLength + 2);
    String thirdHexa = hexa.substring(strLength - 2, strLength - 1);

    return firstHexa.concat(secHexa.concat(thirdHexa));
  }

  public static String hashString(String str, String salt) {
    LOGGER.info("Initialized hashString to hash and put a salt to a string");

    try {
      KeySpec spec = new PBEKeySpec(str.toCharArray(), salt.getBytes(), 65536, 128);
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      byte[] hash = factory.generateSecret(spec).getEncoded();

      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }
      return hexString.toString() + "." + salt.toString();
    } catch(Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  protected static String commonHash(String str) {
    LOGGER.info("Initialized commonHash generate basic hash");

    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(
          str.getBytes(StandardCharsets.UTF_8));

      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if (hex.length() == 1)
          hexString.append('0');
        hexString.append(hex);
      }
      return hexString.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String generateSalt() {
    byte[] salt = new byte[16];
    RANDOM.nextBytes(salt);

    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < salt.length; i++) {
      String hex = Integer.toHexString(0xff & salt[i]);
      if (hex.length() == 1)
        hexString.append('0');
      hexString.append(hex);
    }

    return hexString.toString();
  }
}
