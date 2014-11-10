package battleships1d;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameFrame extends JFrame {

	private JPanel mainPanel;

	public GameFrame(String title) {
		this.setUpUI();
	}

	public void setUpUI() {
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.add(mainPanel);
		this.setSize(700, 650);
	}
}
