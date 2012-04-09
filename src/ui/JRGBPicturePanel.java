package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * contains the picture of the sRGB triangle 
 * @author Niclas
 *
 */
public class JRGBPicturePanel extends JPanel implements MouseListener {
	/**
	 * picture of the RGB colors
	 */
	private BufferedImage image;
	/**
	 * path of <code>image</code>
	 */
	String imagePath= "../pictures/rgb.png";
	/**
	 * containing the <code>image</code>
	 */
	private JLabel jPictureLabel=null;
	/**
	 * showing the color under the cursor
	 */
	private JLabel jColorLabel = null;
	
	public JRGBPicturePanel(int width, int height){
		super();
		this.setSize(new Dimension(width, height));
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		loadImage();
		this.add(getJPictureLabel());
	}
	/**
	 * loads the image located at <code>imagePath</code> ad stores it as <code>image</code>.
	 * @return
	 */
	private boolean loadImage() {
		BufferedImage img = null;
		boolean success = true;
		try {
		    img = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			success = false;
			System.out.println("Loading image failed.");
		}
		if(img != null){
			image.getGraphics().drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, img.getWidth(), img.getHeight(),null);
		}
		return success;
	}
	private JLabel getJPictureLabel(){
		if(jPictureLabel == null){
			ImageIcon icon = new ImageIcon(image);
			jPictureLabel = new JLabel(icon);
			jPictureLabel.setBounds(0, 0, 100, 100);
			jPictureLabel.addMouseListener(this);
		}
		return jPictureLabel;
	}
	
	private void updateImage(Color color){
		for(int x=image.getWidth()-20; x<image.getWidth();x++){
			for(int y=image.getHeight()-20;y<image.getHeight(); y++){
				image.setRGB(x, y, color.getRGB());
			}
		}
		getJPictureLabel().repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point point = e.getPoint();
		Color color = new Color(image.getRGB(point.x, point.y));
		System.out.println(color);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		getJPictureLabel().addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				Point point = e.getPoint();
				Color color = new Color(image.getRGB(point.x, point.y));
				updateImage(color);
				
			}
			
		});
	}
	@Override
	public void mouseExited(MouseEvent e) {
		getJPictureLabel().removeMouseMotionListener(getJPictureLabel().getMouseMotionListeners()[0]);
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	
}
