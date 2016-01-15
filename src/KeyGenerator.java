import java.math.BigInteger;
import java.util.Random;

/**
 * Created by jason on 15/01/16.
 */
public class KeyGenerator {

    /*************************************************************************************************
     * ****************************************ATTRIBUTES*********************************************
     * ***********************************************************************************************
     */

    private static int BIT_LENGTH = 100;
    private BigInteger m_indicatEuler; // m
    private BigInteger m_publicExponent; // e
    private BigInteger m_module; // n


    private BigInteger[] m_publicKey;
    private BigInteger[] m_privateKey;


    /*************************************************************************************************
     * *******************************************METHODS*********************************************
     * ***********************************************************************************************
     */


    public void generatePair() {
        m_publicKey = this.generatePublicKey();
        m_privateKey = this.generatePrivateKey();
    }

    public BigInteger[] generatePublicKey() {

        // Step 1: Two distincts prime numbers p and q
        BigInteger m_firstPrime = BigInteger.probablePrime(BIT_LENGTH, new Random()); // p
        BigInteger m_secondPrime; // q
        do {
            m_secondPrime = BigInteger.probablePrime(BIT_LENGTH, new Random()); // q
        }
        while (m_firstPrime == m_secondPrime); // the two keys as to be different


        //Step 2: Encryption module n = pq
        m_module = m_firstPrime.multiply(m_secondPrime); // n

        //Step 3: Euler indicat m = (p − 1)(q − 1)
        BigInteger m_indicatEuler = (m_firstPrime.subtract(BigInteger.ONE)).multiply(m_secondPrime.subtract(BigInteger.ONE)); // m

        //Step 4: Choose e where e and m are coprime
        do {
            m_publicExponent = BigInteger.probablePrime(BIT_LENGTH, new Random()); // e
        } while(!m_publicExponent.gcd(m_indicatEuler).equals(BigInteger.ONE));

        BigInteger ret[] = {m_module, m_publicExponent}; // couple n, e
        return ret;
    }

    public BigInteger[] generatePrivateKey() {

        if(m_indicatEuler == null || m_publicExponent == null) {
            System.out.println("You must generate the public key before generate the private one");
        } else {
            //Step 5: Private key  modular multiplicative inverse u d ≡ e^(−1) (mod φ(n))
            BigInteger m_privateExponent = m_publicExponent.modInverse(m_indicatEuler);
            BigInteger ret[] = {m_module, m_privateExponent};
            return ret;
        }
        return null;
    }

    /*************************************************************************************************
     * *************************************GETTER/SETTER*********************************************
     * ***********************************************************************************************
     */

    public BigInteger[] getPublicKey() {
        return m_publicKey;
    }

    public BigInteger[] getPrivateKey() {
        return m_privateKey;
    }

}
