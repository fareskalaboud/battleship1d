package battleships1d;

/**
 *
 * @author faresalaboud
 *
 */
public class AppManager {
    private static GameFrame frame;

    public static void setUpUI() {
        frame = new GameFrame("Battleships 1D");
    }

    public static void main(String args[]) {
        setUpUI();
        frame.setVisible(true);

    }
}