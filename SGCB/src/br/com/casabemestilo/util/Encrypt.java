package br.com.casabemestilo.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {

	public Encrypt() {
		
	}
	
	public String md5(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException{		
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    BigInteger hash = new BigInteger(1, md.digest(senha.getBytes("ISO-8859-1")));
	    String senhaMD5 = hash.toString(16);
	    return senhaMD5;
	}
}
