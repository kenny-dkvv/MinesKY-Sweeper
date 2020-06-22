package panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class LoadingPanel extends JPanel{
	
	JProgressBar bar = new JProgressBar();
	GridBagLayout layout = new GridBagLayout();
	JLabel lbPleaseWait = new JLabel("Please Wait");
	Color baseColor = new Color(232, 137, 71);
	
	public void barConfiguration() 
	{
		bar.setSize(100, 100);
		bar.setValue(0);
		bar.setBorder(BorderFactory.createRaisedBevelBorder());
		bar.setBackground(Color.BLACK);
		bar.setUI(new FancyProgressBar(baseColor));
		bar.setStringPainted(true);
	}
	
	public LoadingPanel() {
		// TODO Auto-generated constructor stub
		setBackground(Color.BLACK);
		setLayout(layout);	    
		GridBagConstraints consBar = new GridBagConstraints();
		consBar.gridx = 0;
		consBar.gridy = 7;
		consBar.gridwidth = 3;
		consBar.gridheight = 2;
		consBar.weightx = 1;
		consBar.weighty = 1;
		consBar.ipadx = Toolkit.getDefaultToolkit().getScreenSize().width*2/3;
		consBar.ipady = 100;
		
	    layout.addLayoutComponent(bar, consBar);
	    barConfiguration();
		add(bar);
		
		JLabel logo = null;
		try {
			BufferedImage img = ImageIO.read(new File("./logo2.png"));
			 img.getScaledInstance(500, 500, BufferedImage.SCALE_SMOOTH);
			 logo = new JLabel(new ImageIcon(img));
		} catch(Exception e) {}
		
		GridBagConstraints consLogo = new GridBagConstraints();
		consLogo.gridx = 0;
		consLogo.gridy = 0;
		consLogo.gridwidth = 3;
		consLogo.gridheight = 2;
		consLogo.weightx = 1;
		consLogo.weighty = 1;
		
	    layout.addLayoutComponent(logo, consLogo);
		add(logo);
		
		lbPleaseWait.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
		lbPleaseWait.setForeground(baseColor);
		GridBagConstraints consLabel = new GridBagConstraints();
		consLabel.gridx = 0;
		consLabel.gridy = 4;
		consLabel.gridwidth = 1;
		consLabel.gridheight = 1;
		consLabel.weightx = 1;
		consLabel.weighty = 1;
		consLabel.anchor = GridBagConstraints.BASELINE;
		
		layout.addLayoutComponent(lbPleaseWait, consLabel);
		add(lbPleaseWait);
	}
	
	public boolean processLoading() 
	{
		while(bar.getValue() < 100) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bar.setValue(bar.getValue() + 1);
		}
		return true;
	}
}

class FancyProgressBar extends BasicProgressBarUI {

	Color baseColor;
    public FancyProgressBar(Color color) {
		// TODO Auto-generated constructor stub
    	baseColor = color;
	}

	@Override
    protected Dimension getPreferredInnerVertical() {
        return new Dimension(20, 146);
    }

    @Override
    protected Dimension getPreferredInnerHorizontal() {
        return new Dimension(146, 20);
    }



    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int iStrokWidth = 3;
        g2d.setStroke(new BasicStroke(iStrokWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(progressBar.getBackground());
        g2d.setBackground(progressBar.getBackground());

        int width = progressBar.getWidth();
        int height = progressBar.getHeight();

        RoundRectangle2D outline = new RoundRectangle2D.Double((iStrokWidth / 2), (iStrokWidth / 2),
                width - iStrokWidth, height - iStrokWidth,
                height, height);

        g2d.draw(outline);

        int iInnerHeight = height - (iStrokWidth * 4);
        int iInnerWidth = width - (iStrokWidth * 4);

        double dProgress = progressBar.getPercentComplete();
        if (dProgress < 0) {
            dProgress = 0;
        } else if (dProgress > 1) {
            dProgress = 1;
        }

        iInnerWidth = (int) Math.round(iInnerWidth * dProgress);

        int x = iStrokWidth * 2;
        int y = iStrokWidth * 2;

        Point2D start = new Point2D.Double(x, y);
        Point2D end = new Point2D.Double(x, y + iInnerHeight);

        float[] dist = {0.0f, 0.25f, 1.0f};
        Color[] colors = {baseColor, baseColor.brighter(), baseColor.darker()};
        LinearGradientPaint p = new LinearGradientPaint(start, end, dist, colors);

        g2d.setPaint(p);

        RoundRectangle2D fill = new RoundRectangle2D.Double(iStrokWidth * 2, iStrokWidth * 2,
                iInnerWidth, iInnerHeight, iInnerHeight, iInnerHeight);

        g2d.fill(fill);
        g2d.dispose();
    }

    @Override
    protected void paintIndeterminate(Graphics g, JComponent c) {
        super.paintIndeterminate(g, c); //To change body of generated methods, choose Tools | Templates.
    }

}
