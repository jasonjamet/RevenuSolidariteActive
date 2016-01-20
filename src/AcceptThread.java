import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class AcceptThread implements Runnable {

	private People owner;
	private boolean run;

	public AcceptThread(final People owner) {
		this.owner = owner;
	}

	public void run() {
		run = true;
		System.out.println(owner.getName() + " is waiting ...");
		while (run) {
			try {
				System.out.println("Someone try to connect to " + owner.getName());
				final Socket socket;
				try {
					socket = owner.getServer().accept();
				} catch (final SocketException e) {
					break;
				}
				final PeopleListener peopleListener = new PeopleListener(owner, socket);
				final PeopleWriter peopleWriter = new PeopleWriter(owner, socket);
				peopleListener.acceptNameAndKey();
				peopleWriter.sendNameAndKey();
				peopleWriter.setPublicKey(peopleListener.getPublicKey());
				owner.addPeople(peopleListener.getName(), peopleListener, peopleWriter);
				final Thread t1 = new Thread(peopleListener);
				t1.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		run = false;
	}
}
