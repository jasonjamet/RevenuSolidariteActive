import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;

public class PeopleWriter {

	private People owner;
	private Socket socket;
	private PublicKey key;
	private PrintWriter out;

	public PeopleWriter(final People owner, final Socket socket) {
		this.owner = owner;
		this.socket = socket;
	}

	public void sendNameAndKey() {
		try {
			if (out == null) {
				out = new PrintWriter(socket.getOutputStream(), true);
			}
			System.out.println(owner.getName() + " is sending his(her) name and key");
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(People.NAME_PREFIX + People.DELIMITER + owner.getName());
			out.println(People.KEY_PREFIX + People.DELIMITER + owner.getPublicKey().code());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(final String message) {
		try {
			if (out == null) {
				out = new PrintWriter(socket.getOutputStream(), true);
			}
			System.out.println(owner.getName() + " is sending the message \"" + message + "\"");
			final BigInteger[] code = Encode.encode(message, key.toArray());
			out.println(PeopleListener.BigIntegerArrayToString(code));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			if (out != null) {
				out.close();
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPublicKey(final PublicKey key) {
		this.key = key;
	}
}
