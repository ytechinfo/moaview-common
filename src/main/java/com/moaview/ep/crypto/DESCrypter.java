package com.moaview.ep.crypto;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.moaview.ep.config.EpConfig;
import com.moaview.ep.exception.EncryptDecryptException;

public class DESCrypter {

	private static final String CRYPT_ALGORITHM = EpConfig.getInstance().getProperty("crypt.algorithm", "DESede");
	private static final String PADDING = EpConfig.getInstance().getProperty("crypt.padding", "DESede/CBC/PKCS5Padding");
	private static final String CHAR_ENCODING = EpConfig.getInstance().getCharSet();

	private static final byte[] MY_KEY = EpConfig.getInstance().getProperty("crypt.key", "pub0moaview0epcrypt0asdf").getBytes();// 24-byte
	private static final byte[] MY_IV = EpConfig.getInstance().getProperty("crypt.iv", "pubmoaep").getBytes(); // 8-byte

	/**
	 * Encrypt text to encrypted-text
	 * 
	 * @param text
	 * @return
	 * @throws EncryptDecryptException
	 */
	public String encrypt(String text) throws EncryptDecryptException {

		if (text == null) {
			return null;
		}

		final SecretKeySpec secretKeySpec = new SecretKeySpec(MY_KEY, CRYPT_ALGORITHM);

		final IvParameterSpec iv = new IvParameterSpec(MY_IV);

		Cipher cipher;
		try {
			cipher = Cipher.getInstance(PADDING);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

			final byte[] encrypted = cipher.doFinal(text.getBytes(CHAR_ENCODING));

			return encodeToString(encrypted);

		} catch (Exception e) {
			throw new EncryptDecryptException(e);
		}

	}
	
	/**
	 * url 특수 문자 변경.
	 * 
	 * @method : encodeToString
	 * @param encrypted
	 * @return
	 */
	private String encodeToString(byte[] encrypted) {
		String reval = Base64.getEncoder().encodeToString(encrypted);
		reval = reval.replaceAll("\\+", "*");
		reval = reval.replaceAll("\\/", "!");
		reval = reval.replaceAll("\\=", "~");
		return reval;
	}

	/**
	 * Decrypt encrypted-text
	 * 
	 * @param text
	 * @return
	 * @throws EncryptDecryptException
	 */
	public String decrypt(String text) throws EncryptDecryptException {

		if (text == null) {
			return null;
		}

		final SecretKeySpec secretKeySpec = new SecretKeySpec(MY_KEY, CRYPT_ALGORITHM);
		final IvParameterSpec iv = new IvParameterSpec(MY_IV);

		try {
			final Cipher cipher = Cipher.getInstance(PADDING);

			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

			final byte[] decrypted = cipher.doFinal(decodeToByteArray(text));

			return new String(decrypted, CHAR_ENCODING);
		} catch (Exception e) {
			throw new EncryptDecryptException(e);
		}
	}
	
	/**
	 * url 특수 문자 decode
	 * 
	 * @method : decodeToByteArray
	 * @param text
	 * @return
	 */
	private byte[] decodeToByteArray(String text) {
		text = text.replaceAll("\\*", "+");
		text = text.replaceAll("\\!", "!/");
		text = text.replaceAll("\\~", "=");
		
		return Base64.getDecoder().decode(text);
	}
}