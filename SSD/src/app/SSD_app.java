package app;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SSD_app {
	static TA ta;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ta = null;

		// check for saved data
		if (new File("data.ser").exists()) {

			// if file exists, load state
			try {
				FileInputStream fileIn = new FileInputStream("data.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				ta = (TA) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();

			} catch (ClassNotFoundException c) {
				System.out
						.println("saved state not found \n building new object");
				c.printStackTrace();

			}

		} else
			// if file doesn't exist, create new object
			ta = new TA();

		// run program
		ta.startCreateProgramChoice();

	}

	public static void saveState() {

		// save state
		try {
			FileOutputStream fileOut = new FileOutputStream("data.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(ta);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

}
