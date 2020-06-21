package panel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.plaf.metal.MetalButtonUI;

public class MineSquare extends JButton{
	
	boolean activated = false;
	boolean isBomb = false;
	int minesNearby = 0;
	
	static Font font = new Font("Arial", Font.PLAIN, 40);
	static Color disabledBackground = new Color(20, 20, 20);
	static Color total1 = new Color(0, 0, 255);
	static Color total2 = new Color(0, 255, 0);
	static Color total3 = new Color(255, 0, 0);
	static Color total4 = new Color(255, 0 , 255);
	static Color total5 = new Color(128, 0, 0);
	static Color total6 = new Color(64, 224, 208);
	static Color total7 = new Color(0, 0, 0);
	static Color total8 = new Color(255, 226, 111);
	
	public MineSquare() {
		// TODO Auto-generated constructor stub	
		setFont(font);
		setFocusPainted(false);
		setIcon(new ImageIcon(GamePanel.charmanderImg));
	}
	
	public MineSquare(ImageIcon mineIcon) {
		// TODO Auto-generated constructor stub
		super(mineIcon);
	}

	public void setActivated(boolean bool) {
		activated = bool;
		setEnabled(false);
		
		if(!isBomb) {
			setIcon(null);
			setBackground(disabledBackground);
			setOpaque(true);
			switch(minesNearby) {
			case 1:
				setUI(new MetalButtonUI() {
					protected Color getDisabledTextColor() {
				        return MineSquare.total1;
				    }
				});
				setText("1");
				break;
			case 2:
				setUI(new MetalButtonUI() {
					protected Color getDisabledTextColor() {
				        return MineSquare.total2;
				    }
				});
				setText("2");
				break;
			case 3:
				setUI(new MetalButtonUI() {
					protected Color getDisabledTextColor() {
				        return MineSquare.total3;
				    }
				});
				setText("3");
				break;
			case 4:
				setUI(new MetalButtonUI() {
					protected Color getDisabledTextColor() {
				        return MineSquare.total4;
				    }
				});
				setText("4");
				break;
			case 5:
				setUI(new MetalButtonUI() {
					protected Color getDisabledTextColor() {
				        return MineSquare.total5;
				    }
				});
				setText("5");
				break;
			case 6:
				setUI(new MetalButtonUI() {
					protected Color getDisabledTextColor() {
				        return MineSquare.total6;
				    }
				});
				setText("6");
				break;
			case 7:
				setUI(new MetalButtonUI() {
					protected Color getDisabledTextColor() {
				        return MineSquare.total7;
				    }
				});
				setText("7");
				break;
			case 8:
				setUI(new MetalButtonUI() {
					protected Color getDisabledTextColor() {
				        return MineSquare.total8;
				    }
				});
				setText("8");
				break;
			}
			revalidate();
		}
		else {
			setBackground(Color.RED);
			setDisabledIcon(new ImageIcon(GamePanel.mineImg));
		}
	}

}
