package battleships1d;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GameFrame extends JPanel {

	private JPanel mainPanel;
	
	public GameFrame(String title) {
		this.setName(title);
		this.setUpUI();
	}

	public void setUpUI() {
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setSize(720, 620);
	}
}
