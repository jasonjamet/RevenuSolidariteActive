import java.math.BigInteger;
import java.util.Random;

/**
 * Created by jason on 15/01/16.
 */
public class KeyGenerator {

    private static int BIT_LENGTH;
    private static BigInteger m_firstPrime;
    private static BigInteger m_secondPrime;


    private static void init() {
        BIT_LENGTH = 100;
        m_firstPrime = BigInteger.probablePrime(BIT_LENGTH, new Random());
        do {
            m_secondPrime = BigInteger.probablePrime(BIT_LENGTH, new Random());
        }
        while (m_firstPrime == m_secondPrime);

    }

    public static BigInteger[] generatePublicKey() {
        init();

        System.out.println(BigInteger.valueOf(3).gcd(BigInteger.valueOf(5)));

        BigInteger n = m_firstPrime.multiply(m_secondPrime);
        BigInteger m_indicatEuler = (m_firstPrime.subtract(BigInteger.ONE)).multiply(m_secondPrime.subtract(BigInteger.ONE));
        BigInteger m_publicExposant;
        do {
            m_publicExposant = BigInteger.probablePrime(BIT_LENGTH, new Random());
        } while(!m_publicExposant.gcd(m_indicatEuler).equals(BigInteger.ONE));

        BigInteger ret[] = {n, m_publicExposant};
        return ret;
    }
}
