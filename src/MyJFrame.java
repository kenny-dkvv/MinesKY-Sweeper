import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import panel.GamePanel;
import panel.LoadingPanel;

public class MyJFrame extends JFrame{

	LoadingPanel lp = new LoadingPanel();
	GamePanel gp = new GamePanel();
	JPanel panel = new JPanel();
	
	private CardLayout cl;
	
	public MyJFrame() {
		// TODO Auto-generated constructor stub
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(screenSize);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setResizable(false);
		cl = new CardLayout();
		add(panel);
		panel.setLayout(cl);
		
		panel.add(lp,"loading");
		panel.add(gp, "game");
		cl.show(panel, "loading");
		setVisible(true);
		if(lp.processLoading()) {
			cl.show(panel, "game");
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyJFrame();
	}

}