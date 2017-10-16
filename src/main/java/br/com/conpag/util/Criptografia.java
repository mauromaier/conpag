package br.com.conpag.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Singleton;

import org.apache.commons.codec.binary.Base64;

@Singleton
public class Criptografia {
	
private static String CHAVE = "tvnw63ufg9gh5392";
	
    private Cipher cipher = null;
    private SecretKey key;
    private static Base64 coder;

    private void setUp() throws Exception {
    	key = new SecretKeySpec( CHAVE.getBytes(), "AES");
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
        coder = new Base64();
    }

	public Criptografia() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public String encrypt(String input)  throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(input.getBytes());
		return  new String( coder.encode(cipherText) );
	}

	public String decrypt(String codedText) throws Exception {
		byte[] encypted = coder.decode(codedText.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(encypted);  
        return new String(decrypted);
	}
	
	

}
