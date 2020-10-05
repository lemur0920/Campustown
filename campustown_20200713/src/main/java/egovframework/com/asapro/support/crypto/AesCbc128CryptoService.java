/**
 * 
 */
package egovframework.com.asapro.support.crypto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cryptography.EgovCryptoService;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

/**
 * AES/CBC 128bit 암/복호화 서비스
 * @author cwson
 * @since 2016. 9. 12.
 */
@Component
public class AesCbc128CryptoService implements EgovCryptoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AesCbc128CryptoService.class);

	private Cipher cipher = null;
	private SecretKeySpec secretKey = null;
	byte[] iv = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	private IvParameterSpec ivspec = new IvParameterSpec(iv);

	public AesCbc128CryptoService() {
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			LOGGER.error("[NoSuchAlgorithmException] Try/Catch... : "+ e.getMessage());
		}
	}

	private String keyValue;

	/**
	 * 암호화
	 * @param encodeData
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String encodeData) throws Exception {
		ivspec = new IvParameterSpec(iv);
		keyValue = EgovProperties.getProperty("crypto.aes.password");
		secretKey = new SecretKeySpec(keyValue.getBytes(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
		String encryptedData = Base64.encodeBase64String(cipher.doFinal(encodeData.getBytes()));

		return encryptedData;
	}

	/**
	 * 복호화
	 * @param decodeData
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String decodeData) throws Exception {
		ivspec = new IvParameterSpec(iv);
		keyValue = EgovProperties.getProperty("crypto.aes.password");
		secretKey = new SecretKeySpec(keyValue.getBytes(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
		String decryptedData = new String(cipher.doFinal(Base64.decodeBase64(decodeData)));

		return new String(decryptedData);
	}
	
	/**
	 * 암호화 - 별도키사용
	 * @param encodeData
	 * @param keyValue
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String encodeData, String keyValue) throws Exception {
		ivspec = new IvParameterSpec(iv);
		keyValue = EgovProperties.getProperty("crypto.aes.password");
		secretKey = new SecretKeySpec(keyValue.getBytes(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
		String encryptedData = Base64.encodeBase64String(cipher.doFinal(encodeData.getBytes()));

		return encryptedData;
	}

	/**
	 * 복호화 - 별도키사용
	 * @param decodeData
	 * @param keyValue
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String decodeData, String keyValue) throws Exception {
		ivspec = new IvParameterSpec(iv);
		keyValue = EgovProperties.getProperty("crypto.aes.password");
		secretKey = new SecretKeySpec(keyValue.getBytes(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
		String decryptedData = new String(cipher.doFinal(Base64.decodeBase64(decodeData)));

		return new String(decryptedData);
	}

	/**
	 * Byte Array to Hex
	 * 
	 * @param buf
	 * @return
	 */
	public String byteArrayToHex(byte[] buf) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		for (int i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10) {
				strbuf.append("0");
			}

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}

	/**
	 * Hex to Byte Array
	 * 
	 * @param s
	 * @return
	 */
	public byte[] hexToByteArray(String s) {
		byte[] retValue = null;

		if (s != null && s.length() != 0) {
			retValue = new byte[s.length() / 2];
			for (int i = 0; i < retValue.length; i++) {
				retValue[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
			}
		}

		return retValue;
	}

	/**
	 * @return the keyValue
	 */
	public String getKeyValue() {
		return keyValue;
	}

	/**
	 * @param keyValue
	 *            the keyValue to set
	 */
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	@Override
	public void setPasswordEncoder(EgovPasswordEncoder passwordEncoder) {
		LOGGER.error("[asaproX] AesCbc128CryptoService - Not implemented yet.");
	}

	@Override
	public void setBlockSize(int blockSize) {
		LOGGER.error("[asaproX] AesCbc128CryptoService - Not implemented yet.");
	}

	@Override
	public byte[] encrypt(byte[] data, String password) {
		LOGGER.error("[asaproX] AesCbc128CryptoService - Not implemented yet.");
		return null;
	}

	@Override
	public BigDecimal encrypt(BigDecimal number, String password) {
		LOGGER.error("[asaproX] AesCbc128CryptoService - Not implemented yet.");
		return null;
	}

	@Override
	public void encrypt(File srcFile, String password, File trgtFile) throws FileNotFoundException, IOException {
		LOGGER.error("[asaproX] AesCbc128CryptoService - Not implemented yet.");
	}

	@Override
	public byte[] decrypt(byte[] encryptedData, String password) {
		LOGGER.error("[asaproX] AesCbc128CryptoService - Not implemented yet.");
		return null;
	}

	@Override
	public BigDecimal decrypt(BigDecimal encryptedNumber, String password) {
		LOGGER.error("[asaproX] AesCbc128CryptoService - Not implemented yet.");
		return null;
	}

	@Override
	public void decrypt(File encryptedFile, String password, File trgtFile) throws FileNotFoundException, IOException {
		LOGGER.error("[asaproX] AesCbc128CryptoService - Not implemented yet.");
	}

}
