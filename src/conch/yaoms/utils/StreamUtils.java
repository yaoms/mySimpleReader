package conch.yaoms.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

	public static byte[] getBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(2046);
		byte[] b = new byte[256];
		int len = 0;
		while ((len = inputStream.read(b)) != -1) {
			baos.write(b, 0, len);
		}
		return baos.toByteArray();
	}

}
