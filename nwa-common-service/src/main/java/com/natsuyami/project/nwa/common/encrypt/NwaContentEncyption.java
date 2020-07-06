package com.natsuyami.project.nwa.common.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class NwaContentEncyption {
  private static final Logger LOGGER = LoggerFactory.getLogger(NwaContentEncyption.class);

  public static String[] generateKeys() {
    try {
      KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

      // Initialize KeyPairGenerator.
      SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
      keyGen.initialize(1024, random);

      // Generate Key Pairs, a private key and a public key.
      KeyPair keyPair = keyGen.generateKeyPair();
      PrivateKey privateKey = keyPair.getPrivate();
      PublicKey publicKey = keyPair.getPublic();

      Base64.Encoder encoder = Base64.getEncoder();
      String[] keys = {encoder.encodeToString(privateKey.getEncoded()), encoder.encodeToString(publicKey.getEncoded())};
      System.out.println("privateKey: " + encoder.encodeToString(privateKey.getEncoded()));
      System.out.println("publicKey: " + encoder.encodeToString(publicKey.getEncoded()));

      return keys;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NoSuchProviderException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static PublicKey getPublicKey(String base64PublicKey){
    LOGGER.info("Initialized getPublicKey method for getting PublicKey");

    PublicKey publicKey = null;
    try{
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      publicKey = keyFactory.generatePublic(keySpec);
      return publicKey;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (InvalidKeySpecException e) {
      e.printStackTrace();
    }
    return publicKey;
  }

  public static PrivateKey getPrivateKey(String base64PrivateKey){
    LOGGER.info("Initialized getPrivateKey method for getting PrivateKey");

    PrivateKey privateKey = null;
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
    KeyFactory keyFactory = null;
    try {
      keyFactory = KeyFactory.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    try {
      privateKey = keyFactory.generatePrivate(keySpec);
    } catch (InvalidKeySpecException e) {
      e.printStackTrace();
    }
    return privateKey;
  }

  public static String encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException,
          InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
    LOGGER.info("Initialized encrypt method to encrypt string data={{}} using rsa", data);
    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
    return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
  }

  public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
    LOGGER.info("Initialized decrypt method to decrypt byte data using rsa");
    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    return new String(cipher.doFinal(data));
  }

  public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
    LOGGER.info("Initialized decrypt method to decrypt string data={{}} using rsa", data);
    return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
  }
}
