package com.natsuyami.project.nwa.common.test.service;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

import com.natsuyami.project.nwa.common.encrypt.NwaContentEncyption;
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

    String[] hashVal = NwaPasswordEncrypt.originalEncryption(password, secret);
    String encrypted = NwaPasswordEncrypt.encrypt(hashVal[0], hashVal[1]);
    String decrypted = NwaPasswordEncrypt.decrypt(hashVal[1], encrypted);

    assertThat(hashVal[0], equalTo(decrypted));
  }

  @Test
  public void encryptPasswordNotSame() {
    String password = "TestingPassword";
    String passwordTwo = "TestingPasswordTwo";
    String secret = "12093489";

    String[] hashVal = NwaPasswordEncrypt.originalEncryption(password, secret);
    String encrypted = NwaPasswordEncrypt.encrypt(hashVal[0], hashVal[1]);
    String decrypted = NwaPasswordEncrypt.decrypt(hashVal[1], encrypted);
    String[] hashValTwo = NwaPasswordEncrypt.originalEncryption(passwordTwo, secret);
    String[] hashValThree = NwaPasswordEncrypt.originalEncryption(password, secret);

    assertThat(hashValThree[0], not(equalTo(decrypted)));
    assertThat(hashValTwo[0], not(equalTo(decrypted)));
  }

  @Test
  public void encryptSecretNotSame() {
    String password = "TestingPassword";
    String fakeSecret = "111233314";
    String secret = "12093489";

    String[] hashVal = NwaPasswordEncrypt.originalEncryption(password, secret);
    String[] hashValTwo = NwaPasswordEncrypt.originalEncryption(password, fakeSecret);
    String encrypted = NwaPasswordEncrypt.encrypt(hashVal[0], hashVal[1]);
    String decrypt = NwaPasswordEncrypt.decrypt(hashValTwo[1], encrypted);

    assertThat("", equalTo(decrypt));
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
