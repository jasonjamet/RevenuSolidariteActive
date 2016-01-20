import java.math.BigInteger;

public class PublicKey {

	private final BigInteger n;
	private final BigInteger e;

	public PublicKey(final BigInteger n, final BigInteger e) {
		this.n = n;
		this.e = e;
	}

	public BigInteger[] toArray() {
		return new BigInteger[] { n, e };
	}

	public String code() {
		return n + People.DELIMITER + e;
	}

	public static PublicKey decode(final String code) {
		final String[] parts = code.split(People.DELIMITER);
		if (parts.length == 2) {
			final BigInteger n = new BigInteger(parts[0]);
			final BigInteger e = new BigInteger(parts[1]);
			return new PublicKey(n, e);
		}
		return null;
	}

	public String toString() {
		return n + " " + e;
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getE() {
		return e;
	}
}
