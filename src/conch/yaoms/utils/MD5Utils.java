package conch.yaoms.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	private static MessageDigest md5sumDigest;
	
	static {
		try {
			md5sumDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String md5sum(byte[] data) {
		md5sumDigest.reset();
		md5sumDigest.update(data);
		byte[] md5sum = md5sumDigest.digest();
		StringBuffer md5buffer = new StringBuffer();
		for (byte b : md5sum) {
			md5buffer.append(String.format("%02x", b));
		}
		return md5buffer.toString();
	}

}
