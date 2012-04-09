package ui;

import java.awt.Color;
import java.util.NoSuchElementException;

/**
 * synchronizes the slider's, textField's and the picture's color value
 * @author Niclas
 *
 */

public class GUIUpdater{
	/**
	 * the <code>JRGBPicturePanel</code> to be updated. 
	 */
	private JRGBPicturePanel picturePanel;
	/**
	 * the <code>JSliderPanel</code> to be updated. 
	 */
	private JSliderPanel sliderPanel;
	
	public GUIUpdater(){
		
	}
	
	/**
	 * updates <code>JSliderPanel.rgbSliders</code>, <code>JSliderPanel.textFields</code>. 
	 * @param red between 0 and 100
	 * @param green between 0 and 100
	 * @param blue between 0 and 100
	 */
	public void update(int red, int green, int blue){
		assert(red<=MainWindow.MAX && green<=MainWindow.MAX && blue<=MainWindow.MAX);
		assert(red>=MainWindow.MIN  && green>=MainWindow.MIN  && blue>=MainWindow.MIN );
		sliderPanel.updateSliders(red, green, blue);
		sliderPanel.updateTextFields(red, green, blue);
		picturePanel.updateLeftCornerImage(red,green,blue);
	}
	public void setParameter(JSliderPanel sliderPanel, JRGBPicturePanel picturePanel){
		this.picturePanel = picturePanel;
		this.sliderPanel = sliderPanel;
	}
	/**
	 * gets the current color from <code>sliderPanel.textFields</code>.
	 * @return array with values between 0 and 100.
	 */
	public int[] getCurrentColor() {
		int[] colors = sliderPanel.getTextFieldValues();
		for(int i=0; i<colors.length;i++){
			assert(colors[i]>=MainWindow.MIN && colors[i]<= MainWindow.MAX);
		}
		return colors;
		
	}
}
