package com.natsuyami.project.nwa.common.encrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NwaEncryptionLayer {
  private static final Logger LOGGER = LoggerFactory.getLogger(NwaEncryptionLayer.class);
  private static final Random RANDOM = new SecureRandom();

  public static String shuffleString(String input) {
    LOGGER.info("Initialized shuffleString to shuffle the characters in the string input={{}}", input);

    List<Character> characters = new ArrayList<Character>();
    for (char c:input.toCharArray()) {
      characters.add(c);
    }
    StringBuilder output = new StringBuilder(input.length());
    while (characters.size()!=0) {
      int randPicker = (int) (Math.random()*characters.size());
      output.append(characters.remove(randPicker));
    }
    return output.toString();
  }

  public static String hashString(String str, String salt) {
    LOGGER.info("Initialized hashString to hash and put a salt to a string str={{}}", str);

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
      return hexString.toString();
    } catch(Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  protected static String commonHash(String str) {
    LOGGER.info("Initialized commonHash generate basic hash str={{}}", str);

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
    LOGGER.info("Initialized generateSalt, generating salt");

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
