package colorReader;

import java.awt.Color;
import java.awt.Rectangle;

import screenReader.EnergyImage;
import screenReader.ScreenReader;

public class EnergyPixelReader implements ColorReaderInterface{

	private ScreenReader screenReader;
	private EnergyImage image;
	
	public EnergyPixelReader(int screenNr){
		this.screenReader= new ScreenReader(screenNr);
	}
	
	/**
	 * 
	 * @return <code>Color</code> based on the average color of the selected screen.
	 */
	@Override
	public Color getColor(){
		float threshold = 3f;
		
		int destValue = 0;
		int[] xRange = new int[]{ 0,1920};
		int[] yRange = new int[]{0,1080};
		
		
		image = new EnergyImage(screenReader.screenShot());//robot.createScreenCapture(new Rectangle(xRange[0], yRange[0], xRange[1], yRange[1]));
		//int noPixels = 1;//noPixelsToAnalyseX*noPixelsToAnalyseY;
		int noPixelsToAnalyseX = 10;
		int noPixelsToAnalyseY = 10;
		int pixelStepX = (xRange[1]-xRange[0])/noPixelsToAnalyseX;
		int pixelStepY = (yRange[1]-yRange[0])/noPixelsToAnalyseY;
		Rectangle rec;
		int weight;
		int overallWeight = 1;
		long[] color = new long[3];
		int[] colorTemp = new int[3];
		
		for(int x=0; x<noPixelsToAnalyseX; x++){
			for(int y=0; y< noPixelsToAnalyseY; y++){
				rec = image.areaSize(xRange[0]+x*pixelStepX+destValue,yRange[0]+y*pixelStepY+destValue, threshold);
				//rgb = image.getRGB(xRange[0]+x*pixelStepX+destValue,yRange[0]+y*pixelStepY+destValue);
				colorTemp = analyzeArea(image, rec, 10,10);
				weight = rec.height*rec.width;
				if(colorIsToDark(colorTemp, 0.1f)){
					weight = 0;
					System.out.println("To dark");
				}
				overallWeight = overallWeight + weight;
				color[0] = color[0]+colorTemp[0]*weight;
				color[1] = color[1]+colorTemp[1]*weight;
				color[2] = color[2]+colorTemp[2]*weight;
			}
		}
		return new Color(color[0]/(overallWeight*255f), color[1]/(overallWeight*255f), color[2]/(overallWeight*255f));
		
	}

	private boolean colorIsToDark(int[] color, float threshold) {
		return ( (color[0]/255f+color[1]/255f+color[2]/255f) < threshold );
	}

	private int[] analyzeArea(EnergyImage image2, Rectangle rec, int xAnalyze, int yAnalyze) {
		int rgb = 0;
		int[] color = new int[3];
		int xStep = rec.width/xAnalyze;
		int yStep = rec.height/yAnalyze;
		for(int x=0; x< xAnalyze; x++){
			for(int y=0; y< yAnalyze; y++){
				rgb = image.getRGB(rec.x+x*xStep, rec.y+y*yStep);
				color[0] = color[0]+((rgb & 0x00ff0000) >> 16);
				color[1] = color[1]+ ((rgb & 0x0000ff00) >> 8);
				color[2] = color[2]+(rgb & 0x000000ff);
			}
		}
		int noPixels = xAnalyze*yAnalyze;
		color[0] = color[0]/noPixels;
		color[1] = color[1]/noPixels;
		color[2] = color[2]/noPixels;
		return color;
	}
}
