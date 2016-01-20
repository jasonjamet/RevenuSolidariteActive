import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * Created by jason on 15/01/16.
 */
public class Decode {

	private static String asciiTostr(byte[] chainDecodedArray) {
		try {
			return new String(chainDecodedArray, "ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decode(BigInteger[] chainEncodedArray, BigInteger[] privateKey) {
		String chainDecoded = "";
		for (int i = 0; i < chainEncodedArray.length; i++) {
			chainDecoded += new String(chainEncodedArray[i].modPow(privateKey[1], privateKey[0]).toByteArray());
			// System.out.println(chainEncodedArray[i] + " " + new
			// String(chainEncodedArray[i].modPow(privateKey[1],
			// privateKey[0]).toByteArray()));
		}
		return chainDecoded;
	}
}
