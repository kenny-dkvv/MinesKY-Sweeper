package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GamePanel extends JPanel
{
	//Difficulty
	JPanel difficultyPanel = new JPanel(new BorderLayout());
	JPanel configPanel = new JPanel(new GridLayout(3, 2, 5, 5));
	int width = 10;
	int height = 10;
	int minesCountPercentage = 10;
	
	//title
	JPanel titlePanel = new JPanel();
	JLabel title = new JLabel();
	JLabel panelLeft = new JLabel();
	JLabel mineCount = new JLabel();
	
	//game
	JPanel minePanel = new JPanel();
	GridLayout gridMine = new GridLayout();
	MineSquare[][] mineField = new MineSquare[100][100];
	Vector<MineSquare> mines = new Vector<MineSquare>();
	int panelCount = 0;
	
	int[] openDirX = new int[] {-1, 0, 1, 1, 1, 0, -1, -1};
	int[] openDirY = new int[] {-1, -1, -1, 0, 1, 1, 1, 0};
	Random random = new Random();
	
	static Image mineImg;
	static ImageIcon mineIcon;
	static Image charmanderImg;
	static ImageIcon charmanderIcon;
	static Image flagImg;
	static ImageIcon flagIcon;
	
	void updateInfo() {
		panelLeft.setText(String.format("Unopened: %d", panelCount));
		mineCount.setText(String.format("Mines: %d", mines.size()));
	}
	
	void initTitlePanel() {
		Font fontBig = new Font("Serif", Font.BOLD, 50);
		Font fontSmall = new Font("Serif", Font.BOLD, 30);
		
		title = new JLabel("MinesKY-Sweeper");
		updateInfo();
		
		panelLeft.setFont(fontSmall);
		panelLeft.setForeground(Color.WHITE);
		
		title.setFont(fontBig);
		title.setForeground(Color.WHITE);
		
		mineCount.setFont(fontSmall);
		mineCount.setForeground(Color.WHITE);
		
		
		titlePanel.setBackground(Color.BLACK);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		
		JPanel test1 = new JPanel();
		test1.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		test1.setBackground(Color.BLACK);
		JPanel test2 = new JPanel();
		test2.setBackground(Color.BLACK);
		JPanel test3 = new JPanel();
		test3.setBackground(Color.BLACK);
		test3.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		
		test1.add(panelLeft);
		test2.add(title);
		test3.add(mineCount);
		
		titlePanel.setLayout(new GridLayout(1, 3));
		titlePanel.add(test1);
		titlePanel.add(test2);
		titlePanel.add(test3);
	}
	
	void openSpace(int x, int y) {
		
		MineSquare self = mineField[y][x];
		int total = 0;
		for(int i = 0; i < 8;i++) {
			int nextX = x + openDirX[i];
			int nextY = y + openDirY[i];
			
			if(nextX < 0)continue;
			if(nextY < 0)continue;
			if(nextX >= width)continue;
			if(nextY >= height)continue;
			MineSquare temp = mineField[nextY][nextX];
			if(temp.isBomb)total += 1;
		}
		
		self.minesNearby = total;
		self.setActivated(true);
		panelCount -= 1;
		
		if(total == 0) {
			for(int i = 0; i < 8;i++) {
				int nextX = x + openDirX[i];
				int nextY = y + openDirY[i];
				if(nextX < 0)continue;
				if(nextY < 0)continue;
				if(nextX >= width)continue;
				if(nextY >= height)continue;
				
				MineSquare temp = mineField[nextY][nextX];
				if(!temp.activated)openSpace(nextX, nextY);
			}
		}
	}
	
	void setMinePanel() 
	{
		gridMine.setColumns(width);
		gridMine.setRows(height);
		panelCount = height * width;
		minePanel.removeAll();
		minePanel.revalidate();
		minePanel.repaint();
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width;j++) {
				mineField[i][j] = new MineSquare();
				mineField[i][j].setBackground(Color.BLACK);
				final MineSquare temp = mineField[i][j];
				final int x = j;
				final int y = i;
				
				temp.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, false));
				
				temp.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						if(temp.activated == false) {
							if(SwingUtilities.isRightMouseButton(e)) {
								if(temp.getIcon() == charmanderIcon) {
									temp.setIcon(flagIcon);
								}
								else temp.setIcon(charmanderIcon);
							}
							else {
								temp.setActivated(true);
								temp.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, false));
								
								if(temp.isBomb) {
									for (MineSquare mine : mines) {
										mine.setActivated(true);
									}
									JOptionPane.showMessageDialog(null, "You Lose !", "Game Over", JOptionPane.INFORMATION_MESSAGE, mineIcon);
									setMinePanel();
								}
								else if(SwingUtilities.isLeftMouseButton(e))
								{
									openSpace(x, y);
									updateInfo();
									if(panelCount == mines.size()) {
										JOptionPane.showMessageDialog(null, "You Win :D !", "Game Over", JOptionPane.INFORMATION_MESSAGE, charmanderIcon);
										setMinePanel();
									}
								}
							}
						}
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						temp.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, false));
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						if(temp.activated == false)
							temp.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, false));
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						
					}
				});
				
				minePanel.add(mineField[i][j]);
			}
		}
		
		int mineCount = width*height*minesCountPercentage/100;
		
		mines.clear();
		
		while(mines.size() < mineCount) {
			for(int i = 0; i < mineCount;i++) 
			{
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				mineField[y][x].isBomb = true;
				mines.add(mineField[y][x]);
			}
			
			LinkedHashSet<MineSquare> minesSet = new LinkedHashSet<MineSquare>(mines);
			mines.clear();
			mines.addAll(minesSet);
		}
		
		while(mines.size() > mineCount) {
			MineSquare temp = mines.remove(0);
			temp.isBomb = false;
		}
		
		updateInfo();
		
	}
	
	void initMinePanel() {
		minePanel.setLayout(gridMine);
		minePanel.setBackground(Color.BLACK);
		try {
			mineImg = ImageIO.read(new File("./mine.png"));
			charmanderImg = ImageIO.read(new File("./charmander_face.png"));
			flagImg = ImageIO.read(new File("./flag.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mineIcon = new ImageIcon(mineImg);
		flagIcon = new ImageIcon(flagImg);
		charmanderIcon = new ImageIcon(charmanderImg);
		setMinePanel();
		
	}
	
	void initDifficultyPanel() 
	{
		int hPadding = 50; 
		difficultyPanel.setBorder(BorderFactory.createEmptyBorder(0, hPadding, 0, hPadding));
		difficultyPanel.setBackground(Color.BLACK);
		
		JLabel title = new JLabel("Setting");
		title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		Font fontBig = new Font("Serif", Font.BOLD, 30);
		Font fontSmall = new Font("Serif", Font.PLAIN, 20);
		title.setFont(fontBig);
		title.setForeground(Color.white);
		
		JLabel paddingBottom = new JLabel("");
		try {
			BufferedImage img = ImageIO.read(new File("./img-setting.png"));
			 img.getScaledInstance(500, 500, BufferedImage.SCALE_SMOOTH);
			 paddingBottom = new JLabel(new ImageIcon(img));
		} catch(Exception e) {}
		
		paddingBottom.setBackground(Color.BLACK);
		paddingBottom.setOpaque(true);
		paddingBottom.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		
		JPanel northPanel = new JPanel();
		northPanel.add(title);
		northPanel.setBackground(Color.BLACK);
		
		difficultyPanel.add(northPanel, BorderLayout.NORTH);
		difficultyPanel.add(paddingBottom, BorderLayout.SOUTH);
		difficultyPanel.add(configPanel);
		
		SpinnerModel widthModel =
		        new SpinnerNumberModel(width, width - 0, width + 10, 1);
		SpinnerModel heightModel =
		        new SpinnerNumberModel(height, height - 0, height + 10, 1);
		SpinnerModel bombModel =
		        new SpinnerNumberModel(minesCountPercentage, minesCountPercentage - 0, minesCountPercentage + 40, 10);
		JSpinner widthSpinner = new JSpinner(widthModel);
		JSpinner heightSpinner = new JSpinner(heightModel);
		JSpinner bombSpinner = new JSpinner(bombModel);
		
		widthSpinner.setPreferredSize(new Dimension(150, 30));
		heightSpinner.setPreferredSize(new Dimension(150, 30));
		bombSpinner.setPreferredSize(new Dimension(150, 30));
		
		widthSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				width = (int) widthSpinner.getValue();
				setMinePanel();
			}
		});
		
		heightSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				height = (int) heightSpinner.getValue();
				setMinePanel();
			}
		});
		
		bombSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				minesCountPercentage = (int)bombSpinner.getValue();
				setMinePanel();
			}
		});
		
		JPanel test1 = new JPanel();
		JLabel widthLabel = new JLabel("Width [10- 20]:");
		widthLabel.setFont(fontSmall);
		widthLabel.setForeground(Color.WHITE);
		
		test1.add(widthLabel);
		JPanel test2 = new JPanel();
		test2.add(widthSpinner);
		JPanel test3 = new JPanel();
		JLabel heightLabel = new JLabel("Height [10- 20]:");
		heightLabel.setFont(fontSmall);
		heightLabel.setForeground(Color.WHITE);
		
		test3.add(heightLabel);
		JPanel test4 = new JPanel();
		test4.add(heightSpinner);
		JPanel test5 = new JPanel();
		JLabel bombLabel = new JLabel("Bomb [10- 50]%:");
		bombLabel.setFont(fontSmall);
		bombLabel.setForeground(Color.WHITE);
		
		test5.add(bombLabel);
		JPanel test6 = new JPanel();
		test6.add(bombSpinner);
		
		configPanel.setBackground(Color.BLACK);
		test1.setBackground(Color.BLACK);
		test2.setBackground(Color.BLACK);
		test3.setBackground(Color.BLACK);
		test4.setBackground(Color.BLACK);
		test5.setBackground(Color.BLACK);
		test6.setBackground(Color.BLACK);
		
		configPanel.add(test1);
		configPanel.add(test2);
		configPanel.add(test3);
		configPanel.add(test4);
		configPanel.add(test5);
		configPanel.add(test6);
		
	}
	 public GamePanel() {
		// TODO Auto-generated constructor stub
		 setLayout(new BorderLayout());
		 add(difficultyPanel, BorderLayout.EAST);
		 initDifficultyPanel();
		 add(minePanel, BorderLayout.CENTER);
		 initMinePanel();
		 add(titlePanel, BorderLayout.NORTH);	
		 initTitlePanel();
	}
}
