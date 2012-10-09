package colorReader;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.Random;


public class SimplePixelReader implements AbstractColorReader{
	private Robot robot;
	private BufferedImage image;
	/**
	 * the selected screens width in pixels
	 */
	private int screenWidth;
	/**
	 * the selected screens height in pixels
	 */
	private int screenHeight;
	/**
	 * the numer of pixels analysed on each Zeile
	 */
	private int noPixelsToAnalyseX = 1920;
	/**
	 * the numer of pixels analysed on each Spalte
	 */
	private int noPixelsToAnalyseY = 1080;
	
	public SimplePixelReader(int screenNr) {
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
	/**
	 * 
	 * @return <code>Color</code> based on the average color of the selected screen.
	 */
	@Override
	public Color getColor(){
		int rgb = 0;
		int destValue = 0;
		int[] xRange = new int[]{ 0,screenWidth};
		int[] yRange = new int[]{0,screenHeight};
		
		int[] color = new int[3];;
		
		image = robot.createScreenCapture(new Rectangle(xRange[0], yRange[0], xRange[1], yRange[1]));
		int noPixels = noPixelsToAnalyseX*noPixelsToAnalyseY;
		int pixelStepX = (xRange[1]-xRange[0])/noPixelsToAnalyseX;
		int pixelStepY = (yRange[1]-yRange[0])/noPixelsToAnalyseY;
		
		for(int x=0; x<noPixelsToAnalyseX; x++){
			for(int y=0; y< noPixelsToAnalyseY; y++){
				rgb = image.getRGB(xRange[0]+x*pixelStepX+destValue,yRange[0]+y*pixelStepY+destValue);
				
				color[0] = color[0]+((rgb & 0x00ff0000) >> 16);
				color[1] = color[1]+ ((rgb & 0x0000ff00) >> 8);
				color[2] = color[2]+(rgb & 0x000000ff);
			}
		}
		return new Color(color[0]/noPixels, color[1]/noPixels, color[2]/noPixels);
		
	}
	//getters and setters
	public int getNoPixelsToAnalyseX() {
		return noPixelsToAnalyseX;
	}

	public void setNoPixelsToAnalyseX(int noPixelsToAnalyseX) {
		this.noPixelsToAnalyseX = noPixelsToAnalyseX;
	}

	public int getNoPixelsToAnalyseY() {
		return noPixelsToAnalyseY;
	}

	public void setNoPixelsToAnalyseY(int noPixelsToAnalyseY) {
		this.noPixelsToAnalyseY = noPixelsToAnalyseY;
	}
}
