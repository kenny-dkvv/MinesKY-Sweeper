package Frame;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import panel.GamePanel;
import panel.ImageRepo;
import panel.LoadingPanel;

public class MyJFrame extends JFrame{

	LoadingPanel lp = new LoadingPanel();
	GamePanel gp = new GamePanel();
	JPanel panel = new JPanel();
	private CardLayout cl;
	
	public static MyJFrame frame;
	
	public MyJFrame() {
		// TODO Auto-generated constructor stub
		
		setIconImage(ImageRepo.charmanderImgBig);
		setTitle("MinesKY-Sweeper");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(screenSize);
		setUndecorated(true);
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
		setFocusable(true);
		
		if(lp.processLoading()) {
			cl.show(panel, "game");
		}
		addKeyListener(new KeyListener() {
			 
			 @Override
			 public void keyTyped(KeyEvent e) {
				 // TODO Auto-generated method stub
			 }
			 
			 @Override
			 public void keyReleased(KeyEvent e) {
				 // TODO Auto-generated method stub
//				 System.out.println("released"); 
				 if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					 gp.setMinePanel();
//					 System.out.println("test space");
				 }
			 }
			 
			 @Override
			 public void keyPressed(KeyEvent e) {
				 // TODO Auto-generated method stub
				 
			 }
		 });
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImageRepo.genImage();
		frame = new MyJFrame();
	}

}
