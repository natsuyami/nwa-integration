package com.natsuyami.project.nwa.common.test.service;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

import com.natsuyami.project.nwa.common.encrypt.NwaContentEncyption;
import com.natsuyami.project.nwa.common.encrypt.NwaEncryptionLayer;
import com.natsuyami.project.nwa.common.encrypt.NwaPasswordEncrypt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;

@RunWith(SpringRunner.class)
public class NwaEncryptServiceTest {

  @Test
  public void encryptPasswordSuccess() {
    String password = "TestingPassword";
    String secret = "12093489";

    String encrypted = NwaPasswordEncrypt.encrypt(password, secret);
    String decrypted = NwaPasswordEncrypt.decrypt(password, secret, encrypted);
    String[] random = NwaPasswordEncrypt.originalEncryption(password, secret);
    String hash = NwaEncryptionLayer.hashString(random[0], decrypted.split("\\.")[1]);

    assertThat(hash, equalTo(decrypted));
  }

  @Test
  public void encryptPasswordNotSame() {
    String password = "TestingPassword";
    String passwordTwo = "TestingPasswordTwo";
    String secret = "12093489";

    String encrypted = NwaPasswordEncrypt.encrypt(password, secret);
    String decrypted = NwaPasswordEncrypt.decrypt(password, secret, encrypted);
    String[] random = NwaPasswordEncrypt.originalEncryption(passwordTwo, secret);
    String hash = NwaEncryptionLayer.hashString(random[0], decrypted.split("\\.")[1]);

    assertThat(hash, not(equalTo(decrypted)));
  }
  @Test
  public void encryptSecretNotSame() {
    String password = "TestingPassword";
    String secretTwo = "111233314";
    String secret = "12093489";

    String encrypted = NwaPasswordEncrypt.encrypt(password, secret);
    String decrypted = NwaPasswordEncrypt.decrypt(password, secret, encrypted);
    String[] random = NwaPasswordEncrypt.originalEncryption(password, secretTwo);
    String hash = NwaEncryptionLayer.hashString(random[0], decrypted.split("\\.")[1]);

    assertThat(hash, not(equalTo(decrypted)));
  }

  @Test
  public void encryptContentTest() throws Exception {
    String testOne = "test-data";
    String testTwo = "testing of @#$# data 123";

    String[] keys = NwaContentEncyption.generateKeys();
    String encryptedOne = NwaContentEncyption.encrypt(testOne, keys[1]);
    String encryptedTwo = NwaContentEncyption.encrypt(testTwo, keys[1]);

    assertThat(testOne, equalTo(NwaContentEncyption.decrypt(encryptedOne,
            keys[0])));
    assertThat(testOne, not(equalTo(NwaContentEncyption.decrypt(encryptedTwo,
            keys[0]))));

  }
}
