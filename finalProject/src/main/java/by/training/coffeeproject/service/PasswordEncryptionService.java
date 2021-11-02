package by.training.coffeeproject.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PasswordEncryptionService {
	private static final Logger LOG = LogManager.getLogger(PasswordEncryptionService.class);

	private PasswordEncryptionService() {
	}

	private static PasswordEncryptionService instance = new PasswordEncryptionService();

	public static PasswordEncryptionService getInstance() {
		return instance;
	}

	private final byte[] salt = { -85, 124, -25, -44, 4, -115, 39, -28 };

	/**
	 * from String password to String encrypted password
	 * 
	 * @param password
	 * @return
	 * @throws ServiceException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public String getEncryptedPassword(String password) throws ServiceException {
		LOG.debug("Start getEncryptedPassword ");
//		LOG.debug("get password " + password);
//		LOG.debug ("password to char Array " + Arrays.toString(toPass(password)));
		// PBKDF2 with SHA-1 as the hashing algorithm.
		final String algorithm = "PBKDF2WithHmacSHA1";
		// SHA-1 generates 160 bit hashes
		final int derivedKeyLength = 160;
		final int iterations = 10000;
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);

		SecretKeyFactory f;
		try {
			f = SecretKeyFactory.getInstance(algorithm);
			byte[] answ = f.generateSecret(spec).getEncoded();
			return new String(answ, "UTF-8");

//		LOG.debug("get answ in char " +  Arrays.toString(toPass(new String(answ))));
//		LOG.debug("get answ in byte " +  Arrays.toString(answ));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | UnsupportedEncodingException e) {
			LOG.warn("can't encrypt password");
			throw new ServiceException("error_encrypt_password");
		}

	}

//	public byte[] generateSalt() throws NoSuchAlgorithmException {
//		// VERY important to use SecureRandom instead of just Random
//		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//
//		// Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
//		byte[] salt = new byte[8];
//		random.nextBytes(salt);
//
//		return salt;
//	}
//	// to encrypt passwords for db
//	public static void main(String args[]) throws ServiceException {
//		String enctyptedPass = PasswordEncryptionService.getInstance().getEncryptedPassword("789asd789");
//		System.out.println(enctyptedPass);
//	}
//	// method for logging
//	private int [] toPass (String pass) {
//		char [] mas = pass.toCharArray();
//		int [] intMas = new int [mas.length];
//		for (int i = 0; i < mas.length; i++) {
//			intMas [i] = mas [i];
//		}
//		return intMas;
//	}
}
