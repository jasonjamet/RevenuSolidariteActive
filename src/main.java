import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Created by jason on 15/01/16.
 */
public class main {
    public static void main(String[] args) {
        /*Alice*/
        KeyGenerator keyGen = new KeyGenerator();
        keyGen.generatePair();
        BigInteger[] chain = Encode.encode("Bonjour !", keyGen.getPublicKey());
        Decode.decode(chain, keyGen.getPrivateKey());



        //Exemple de la feuille de tp
        BigInteger publicKey[] = {BigInteger.valueOf(7), BigInteger.valueOf(5141)};
        Encode.encode("Bonjour !", publicKey);

        System.out.println("-----------");


        BigInteger chain2[] = {BigInteger.valueOf(386), BigInteger.valueOf(737), BigInteger.valueOf(970), BigInteger.valueOf(204), BigInteger.valueOf(1858)};
        BigInteger privateKey[] = {BigInteger.valueOf(5141), BigInteger.valueOf(4279)};
        Decode.decode(chain2, privateKey);


    }
}
