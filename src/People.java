import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class People {

	public static final String NAME_PREFIX = "NAME";
	public static final String KEY_PREFIX = "KEY";
	public static final String DELIMITER = "œ";

	private String name;
	private Integer port;
	private KeyGenerator keyGen;
	private ServerSocket server;
	private AcceptThread accept;
	private Thread acceptThread;
	private Map<String, PeopleListener> listeners;
	private Map<String, PeopleWriter> writers;

	public enum PeopleEnum {
		Alice("Alice", 9876), Bob("Bob", 8765);

		private final String name;
		private final Integer port;

		PeopleEnum(final String name, final Integer port) {
			this.name = name;
			this.port = port;
		}

		public String getName() {
			return this.name;
		}

		public Integer getPort() {
			return this.port;
		}

		public People toPeople() {
			return new People(this.name, this.port);
		}
	}

	public People(final String name, final Integer port) {
		this.name = name;
		this.port = port;
		this.keyGen = new KeyGenerator();
		this.listeners = new HashMap<String, PeopleListener>();
		this.writers = new HashMap<String, PeopleWriter>();
		try {
			server = new ServerSocket(this.port);
		} catch (final IOException e) {
			throw new TechnicalException("Port " + this.getPort() + " is already use by someone else ...");
		}
		accept = new AcceptThread(this);
		acceptThread = new Thread(accept);
		acceptThread.start();
	}

	public boolean connectToOtherPeople(final String adress, final Integer port) {
		final Socket socket;
		try {
			socket = new Socket(adress, port);
			final PeopleListener peopleListener = new PeopleListener(this, socket);
			final PeopleWriter peopleWriter = new PeopleWriter(this, socket);
			peopleWriter.sendNameAndKey();
			peopleListener.acceptNameAndKey();
			peopleWriter.setPublicKey(peopleListener.getPublicKey());
			final Thread t = new Thread(peopleListener);
			t.start();
			addPeople(name, peopleListener, peopleWriter);
		} catch (final IOException e) {
			return false;
		}
		return true;
	}

	public void stop() {
		accept.stop();
		for (final PeopleListener people : listeners.values()) {
			people.stop();
		}
		try {
			server.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void addPeople(final String name, final PeopleListener peopleListener, final PeopleWriter peopleWriter) {
		listeners.put(name, peopleListener);
		writers.put(name, peopleWriter);
	}

	public void sendMessage(final String name, final String message) {
		final PeopleWriter people = writers.get(name);
		if (people != null) {
			people.sendMessage(message);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public PublicKey getPublicKey() {
		return keyGen.getPublicKey();
	}

	public BigInteger[] getPrivateKey() {
		return keyGen.getPrivateKey();
	}

	public ServerSocket getServer() {
		return server;
	}

	@SuppressWarnings("unused")
	private static void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		final People Alice = PeopleEnum.Alice.toPeople();

		final People Bob = PeopleEnum.Bob.toPeople();

		Alice.connectToOtherPeople("localhost", PeopleEnum.Bob.port);

		Bob.sendMessage(PeopleEnum.Alice.name, "Salut Alice !");
		Alice.sendMessage(PeopleEnum.Bob.name, "Bonjour Bob !");
		Bob.sendMessage(PeopleEnum.Alice.name, "Comment sa va ?");
		Alice.sendMessage(PeopleEnum.Bob.name, "Sa va et toi ?");
		Bob.sendMessage(PeopleEnum.Alice.name, "Sa va ! a+");
		Alice.sendMessage(PeopleEnum.Bob.name, "a+");
		//Alice.stop();
		//Bob.stop();
	}
}
