package com.natsuyami.project.nwa.common.test.service;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

import com.natsuyami.project.nwa.common.encrypt.NwaEncryptionLayer;
import com.natsuyami.project.nwa.common.encrypt.NwaPasswordEncrypt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

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

}
