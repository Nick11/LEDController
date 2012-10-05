package outputAdapters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AreaTester {
	private JPanel panel;
	private BufferedImage image;
	public AreaTester(){
		initPanel();
	}

	
	private void initPanel() {
		JFrame frame = new JFrame();
        panel = new JPanel();
        frame.setContentPane(panel);
        image = new BufferedImage(1920,1080,BufferedImage.TYPE_INT_RGB);
        ImageIcon icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);
        panel.add(label);
        frame.setVisible(true);
        frame.setEnabled(true);
        frame.setMinimumSize(new Dimension(1920, 1180));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void paint(int x, int y){
		image.setRGB(x, y, Color.red.getRGB());
		ImageIcon icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);
        panel.add(label);
	}
	


}
