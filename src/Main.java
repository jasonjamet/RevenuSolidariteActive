import java.math.BigInteger;
import java.util.Random;

/**
 * Created by jason on 15/01/16.
 */
public class Main {
	public static void main(String[] args) {
		/* Alice */
		KeyGenerator keyGen = new KeyGenerator(false);
		keyGen.generatePair();
		final Random rand = new Random();
		final StringBuilder s = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < 5000; i++) {
			s.append((char) (r.nextInt(26) + 'a'));
		}
		String message = s.toString();// "Bonjour";
		System.out.println(s.length());
		BigInteger[] chain = Encode.encode(message, keyGen.getPublicKeyArray());
		System.out.println("-----------");
		final String decodeString = Decode.decode(chain, keyGen.getPrivateKey());
		System.out.println(decodeString);

		// Exemple de la feuille de tp
		// BigInteger publicKey[] = { BigInteger.valueOf(7),
		// BigInteger.valueOf(5141) };
		// Encode.encode("Bonjour !", publicKey);
		// System.out.println("-----------");
		//
		// BigInteger chain2[] = { BigInteger.valueOf(386),
		// BigInteger.valueOf(737), BigInteger.valueOf(970),
		// BigInteger.valueOf(204), BigInteger.valueOf(1858) };
		// BigInteger privateKey[] = { BigInteger.valueOf(5141),
		// BigInteger.valueOf(4279) };
		// Decode.decode(chain2, privateKey);

	}
}
