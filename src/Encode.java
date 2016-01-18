import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * Created by jason on 15/01/16.
 */
public class Encode {

    private static byte[] strToAscii(String str) {
        try {
            return str.getBytes("ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BigInteger[] encode(String str, BigInteger[] publicKey) {
        byte[] byteChain = strToAscii(str);
        BigInteger[] encodedChain = new BigInteger[byteChain.length];


        for(int i = 0; i < byteChain.length; i++)  {
            encodedChain[i] = BigInteger.valueOf(byteChain[i]).modPow(publicKey[0], publicKey[1]);
            System.out.println(byteChain[i] + "----" + encodedChain[i]);
        }
        return encodedChain;
    }
}
