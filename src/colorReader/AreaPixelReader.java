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

import outputAdapters.AreaTester;


public class AreaPixelReader implements AbstractColorReader{
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
	private int noPixelsToAnalyseX = 192;
	/**
	 * the numer of pixels analysed on each Spalte
	 */
	private int noPixelsToAnalyseY = 108;
	/**
	 * x component of the corner of the rectangle to be analysed
	 */
	private int cornerOfAreaX;
	/**
	 * y component of the corner of the rectangle to be analysed
	 */
	private int cornerOfAreaY;
	/**
	 * length of the x side of the rectangle to be analyzed.
	 */
	private int areaSideX;
	/**
	 * length of the y side of the rectangle to be analyzed.
	 */
	private int areaSideY;
	
	public AreaPixelReader(int screenNr) {
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
			
			//TODO: u.s. parameter sollten im constructor übergeben werden
			this.cornerOfAreaX = 200;
			this.cornerOfAreaY = 150;
			this.areaSideX = 400;
			this.areaSideY = 400;
			//testArea();
	}
	private void testArea() {
		AreaTester tester = new AreaTester();
		for(int i=0; i<areaSideX; i++){
			tester.paint(cornerOfAreaX+i, cornerOfAreaY);
		}
		for(int i=0; i<areaSideY; i++){
			tester.paint(cornerOfAreaX, cornerOfAreaY+i);
		}
		for(int i=0; i<areaSideX; i++){
			tester.paint(cornerOfAreaX+i, cornerOfAreaY+areaSideY);
		}
		for(int i=0; i<areaSideY; i++){
			tester.paint(cornerOfAreaX+areaSideX, cornerOfAreaY+i);
		}
	}
	/**
	 * 
	 * @return <code>Color</code> based on the average color of the selected screen.
	 */
	@Override
	public Color getColor(){
		int rgb = 0;
		int destValue = 0;
		int[] xRange = new int[]{ cornerOfAreaX,cornerOfAreaX+areaSideX};
		int[] yRange = new int[]{cornerOfAreaY,cornerOfAreaY+areaSideY};
		
		int[] color = new int[3];;
		
		image = robot.createScreenCapture(new Rectangle(xRange[0], yRange[0], xRange[1], yRange[1])); // screencordinates: upper left corner is 0,0. the Ursprung of the rectangle is the left bottom corner
		int noPixels = noPixelsToAnalyseX*noPixelsToAnalyseY;
		int pixelStepX = areaSideX/noPixelsToAnalyseX;
		int pixelStepY = areaSideY/noPixelsToAnalyseY;
		
		for(int x=0; x<noPixelsToAnalyseX; x++){
			for(int y=0; y< noPixelsToAnalyseY; y++){
				try{
					rgb = image.getRGB(x*pixelStepX+destValue,y*pixelStepY+destValue);
				}catch(ArrayIndexOutOfBoundsException e){
					y=noPixelsToAnalyseY;
					rgb = 0x11111111;
				}
				color[0] = color[0]+((rgb & 0x00ff0000) >> 16);
				color[1] = color[1]+ ((rgb & 0x0000ff00) >> 8);
				color[2] = color[2]+(rgb & 0x000000ff);
				/*if(xRange[0]+x*pixelStepX+destValue>boarderWidth)
					xRange[0] = screenWidth -boarderWidth;
				if(yRange[0]+y*pixelStepY+destValue>boarderWidth)
					yRange[0]=screenHeight-boarderWidth;*/
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
