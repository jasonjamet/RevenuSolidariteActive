import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PeopleListener implements Runnable {

	private People owner;
	private Socket socket;
	private PublicKey key;
	private String name;
	private BufferedReader in;
	private boolean run;

	public PeopleListener(final People owner, final Socket socket) {
		this.owner = owner;
		this.socket = socket;
	}

	public void run() {
		run = true;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(owner.getName() + " is waiting for message from " + name + " ...");
			String buffer = in.readLine();
			while (run) {
				final BigInteger[] code = PeopleListener.StringToBigIntegerArray(buffer);
				final String message = Decode.decode(code, owner.getPrivateKey());
				System.out.println(owner.getName() + " receive the message : \"" + message + "\"");
				buffer = in.readLine();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void acceptNameAndKey() {
		System.out.println(owner.getName() + " is waiting for name and key");
		try {
			if (in == null) {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}
			String buffer = in.readLine();
			boolean next = false;
			while (!next) {
				if (buffer != null && buffer.startsWith(People.NAME_PREFIX)) {
					final String[] parts = buffer.split(People.DELIMITER);
					if (parts.length == 2) {
						name = parts[1];
						next = true;
					}
				}
				if (!next) {
					buffer = in.readLine();
				}
			}
			System.out.println(owner.getName() + " starts to talk with " + name + " !");

			next = false;
			buffer = in.readLine();
			while (!next) {
				if (buffer != null && buffer.startsWith(People.KEY_PREFIX)) {
					final String[] parts = buffer.split(People.DELIMITER);
					if (parts.length == 3) {
						key = new PublicKey(new BigInteger(parts[1]), new BigInteger(parts[2]));
						next = true;
					}
				}
				if (!next) {
					buffer = in.readLine();
				}
			}
			System.out.println(name + "'s public key is " + key.toString());

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			if (in != null) {
				in.close();
			}
			socket.close();
			run = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String BigIntegerArrayToString(final BigInteger[] bigs) {
		final StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (final BigInteger big : bigs) {
			if (first) {
				first = false;
			} else {
				sb.append(" ");
			}
			sb.append(big);
		}
		return sb.toString();
	}

	public static BigInteger[] StringToBigIntegerArray(final String array) {
		final String[] parts = array.split(" ");
		final List<BigInteger> bigs = new ArrayList<BigInteger>();
		for (String part : parts) {
			bigs.add(new BigInteger(part));
		}
		return bigs.toArray(new BigInteger[0]);
	}

	public String getName() {
		return this.name;
	}

	public PublicKey getPublicKey() {
		return key;
	}
}
