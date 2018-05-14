package backend;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/*Valor_Armazenado = HEX(HASH_SHA1(senha_texto_plano + SALT))
HEX = representação hexadecimal.
HASH_SHA1 = função hash SHA-1.
+ = concatenação de string.
senha_texto_plano = senha em texto plano (string).
SALT = valor aleatório composto de 10 caracteres do conjunto [A-Z][a-z][0-9] (string)*/

public abstract class PasswordAuthentication
{
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String generatePasswordHash (String password, Integer salt) throws NoSuchAlgorithmException, UnsupportedEncodingException{         
        password = password + String.format("%09d", salt);
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");           
            return bytesToHex(md5.digest(password.getBytes("UTF-8")));          
        } catch (NoSuchAlgorithmException ex) {
        	// adicionar no logger
        	throw ex;
        } catch (UnsupportedEncodingException ex) {
            // adicionar no logger
            throw ex;
        }
    }
    
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}