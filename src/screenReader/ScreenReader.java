package screenReader;

import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class ScreenReader {
	private Robot robot;
	/**
	 * the selected screens width in pixels
	 */
	private int screenWidth;
	/**
	 * the selected screens height in pixels
	 */
	private int screenHeight;
	
	
	public ScreenReader(int screenNr) {
		GraphicsEnvironment graphicEnvironement = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = graphicEnvironement.getScreenDevices();
		assert(screenNr>=0 && screenNr< screens.length);
		GraphicsDevice analysedScreen = screens[screenNr];
		try{
		robot = new Robot(analysedScreen);
		} catch(AWTException e){
			//TODO
		}
		DisplayMode displayMode = analysedScreen.getDisplayMode();
		this.screenWidth = displayMode.getWidth();
		this.screenHeight = displayMode.getHeight();
	}
	public BufferedImage areaScreenShot(int[] xRange, int[] yRange){
		return robot.createScreenCapture(new Rectangle(xRange[0], yRange[0], xRange[1], yRange[1]));
	}
	public BufferedImage screenShot(){
		return robot.createScreenCapture(new Rectangle(0, 0, screenWidth, screenHeight));
	}
}
