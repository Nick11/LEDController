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
	/**
	 * instance of this class
	 */
	private static GUIUpdater instance=null;
	
	private GUIUpdater(JSliderPanel sliderPanel, JRGBPicturePanel picturePanel){
		this.picturePanel = picturePanel;
		this.sliderPanel = sliderPanel;
	}
	/**
	 * returns <code>instance</code>, a existing instance of this class, or creates a new one.
	 * @return an instance of GUIUpdater
	 */
	public static GUIUpdater getInstance(JSliderPanel sliderPanel, JRGBPicturePanel picturePanel){
		if(instance==null){
			instance = new GUIUpdater(sliderPanel, picturePanel);
		}
		return instance;
	}
	/**
	 * returns <code>instance</code>, a existing instance of this class, if one has been initialized.
	 * @return an instance of GUIUpdater
	 */
	public static GUIUpdater getInstance(){
		if(instance==null){
			throw(new NoSuchElementException("No instance of GUIUpdater has yet been created"));
		}
		return instance;
	}
	
	/**
	 * updates <code>JSliderPanel.rgbSliders</code>, <code>JSliderPanel.textFields</code>. 
	 * @param red between 0 and 100
	 * @param green between 0 and 100
	 * @param blue between 0 and 100
	 */
	public void update(int red, int green, int blue){
		assert(red<256 && green<256 && blue<256);
		assert(red>=0&& green>=0 && blue>=0);
		
	}
}
