import java.io.UnsupportedEncodingException;

/**
 * Created by jason on 15/01/16.
 */
public class Encode {
    private byte[] strToAscii() {
        String s = new String("Bonjour !");
        try {
            return s.getBytes("ASCII");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
