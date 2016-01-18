import java.math.BigInteger;
import java.util.Random;

/**
 * Created by jason on 15/01/16.
 */
public class KeyGenerator {

	/*************************************************************************************************
	 ************************************** ATTRIBUTES ***********************************************
	 *************************************************************************************************/

	private static int BIT_LENGTH = 100;
	private static int E_BIT_LENGTH = 20;
	private BigInteger m_indicatEuler; // m
	private BigInteger m_publicExponent; // e
	private BigInteger m_module; // n

	private BigInteger[] m_publicKey;
	private BigInteger[] m_privateKey;

	/*************************************************************************************************
	 ************************************** METHODS **************************************************
	 *************************************************************************************************/

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
		} while (m_firstPrime == m_secondPrime); // the two keys as to be
													// different

		// Step 2: Encryption module n = pq
		m_module = m_firstPrime.multiply(m_secondPrime); // n

		// Step 3: Euler indicat m = (p − 1)(q − 1)
		m_indicatEuler = (m_firstPrime.subtract(BigInteger.ONE)).multiply(m_secondPrime.subtract(BigInteger.ONE)); // m

		// Step 4: Choose e where e and m are coprime
		do {
			m_publicExponent = BigInteger.probablePrime(E_BIT_LENGTH, new Random()); // e
		} while (!m_publicExponent.gcd(m_indicatEuler).equals(BigInteger.valueOf(1))
				&& m_publicExponent.mod(BigInteger.valueOf(2)).equals(BigInteger.ONE));

		BigInteger ret[] = { m_module, m_publicExponent }; // couple n, e
		return ret;
	}

	public BigInteger[] generatePrivateKey() {

		if (m_indicatEuler == null || m_publicExponent == null) {
			System.out.println("You must generate the public key before generate the private one");
		} else {
			// Step 5: Private key modular multiplicative inverse u d ≡ e^(−1)
			// (mod φ(n))

			// FIXME Problème dans la génération de la clef privée
			BigInteger a = m_publicExponent;
			BigInteger b = m_indicatEuler;
			BigInteger u = BigInteger.ONE;
			BigInteger u1 = BigInteger.ZERO;
			BigInteger v = BigInteger.ZERO;
			BigInteger v1 = BigInteger.ONE;
			BigInteger r = a.multiply(u).add(b.multiply(v));
			BigInteger r1 = a.multiply(u1).add(b.multiply(v1));
			while (!r1.equals(BigInteger.ZERO)) {
				BigInteger q = r.divide(r1);
				BigInteger rs = r;
				BigInteger us = u;
				BigInteger vs = v;
				r = r1;
				u = u1;
				v = v1;
				r1 = rs.subtract(q.multiply(r1));
				u1 = us.subtract(q.multiply(u1));
				v1 = vs.subtract(q.multiply(v1));
			}
			BigInteger uRes = u;
			while (uRes.compareTo(BigInteger.ZERO) < 0) {
				BigInteger k = BigInteger.valueOf(-1);
				uRes = u.subtract(k.multiply(b));
				k.subtract(BigInteger.ONE);
			}
			System.out.println(uRes);
			BigInteger m_privateExponent = uRes;
			// BigInteger m_privateExponent =
			// m_publicExponent.modInverse(m_indicatEuler);
			BigInteger ret[] = { m_module, m_privateExponent };
			return ret;
		}
		return null;
	}

	/*************************************************************************************************
	 ************************************** GETTER/SETTER*********************************************
	 *************************************************************************************************/

	public BigInteger[] getPublicKey() {
		return m_publicKey;
	}

	public BigInteger[] getPrivateKey() {
		return m_privateKey;
	}

}
