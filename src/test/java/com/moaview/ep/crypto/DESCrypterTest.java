package com.moaview.ep.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DESCrypterTest {

	@Test
	void test() {
		// must be multiple of 8
		String srcText = "asdfawejfklawjefkljawklefjlkawjekflawef";

		// create crypter
		final DESCrypter crypter = new DESCrypter();

		// do encrypt
		String encryptedText;
		try {
			encryptedText = crypter.encrypt(srcText);
			
			assertEquals(srcText, crypter.decrypt(encryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
