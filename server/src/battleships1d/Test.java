package battleships1d;

/**
 * Created by Thomas on 21/10/2014.
 */
public class Test {
	/**
	 * THE MAIN Class to run the Server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		UserManager.init();
		ServerManager.start();
	}
}
