package screenReader;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class EnergyImage{

	private BufferedImage image;
	private float[][] energy;
	
	public EnergyImage(BufferedImage image){
		this.image = image;
		this.energy = new float[image.getWidth()][image.getHeight()];
		initializeEnergy(image);
	}
	
	private void initializeEnergy(BufferedImage image) {
		for(int i=0; i<image.getWidth(); i++){
			for( int j=0; j<image.getHeight(); j++){
				energy[i][j] = -1;
			}
		}
	}
	/**
	 * How big is the area around the specified pixel with energy lower than threshold
	 * @param x
	 * @param y
	 * @param threashold
	 */
	public Rectangle areaSize(int xs, int ys, float threshold){
		int x0, y0, width, height;
		int xt = xs;
		int yt = ys;
				
		//go left until energy gets to high
		while(xt>0 && energyBetweenPixels(xt, yt, xt-1, yt)<threshold){
			xt = xt-1;
		}
		x0 = xt;
		
		//go right until energy gets to high
		xt = xs;
		while(xt+1<image.getWidth() && energyBetweenPixels(xt, yt, xt+1, yt)<threshold){
			xt = xt+1;
		}
		width = xt-x0;
		
		//go up until energy gets to high
		xt = xs;
		while(yt>0 && energyBetweenPixels(xt, yt, xt, yt-1)<threshold){
			yt = yt-1;
		}
		y0 = yt;
		
		//go down until energy gets to high
		yt = ys;
		while(yt+1<image.getHeight() && energyBetweenPixels(xt, yt, xt, yt+1)<threshold){
			yt = yt+1;
		}
		height = yt-y0;
				
		Rectangle area = new Rectangle(x0, y0, width, height);
		//System.out.println(area);
		return area;
	}
	
	private float energyBetweenPixels(int x1, int y1, int x2, int y2){
		assert(x1>=0 && x1<image.getWidth() && y1>=0 && y1<image.getHeight());
		assert(x2>=0 && x2<image.getWidth() && y2>=0 && y2<image.getHeight());
		
		float[] diff = new float[3];
	
		int rgb1 = image.getRGB(x1, y1);
		int rgb2 = image.getRGB(x2, y2);
		
		diff[0] = (float) (rgb1 & 0xFF)-(rgb2 & 0xFF);
		diff[0] = diff[0]*diff[0];
		
		diff[1] = (float) ((rgb1 >> 8) & 0xFF)-((rgb2 >> 8) & 0xFF);
		diff[1] = diff[1]*diff[1];
		
		diff[2] = (float) ((rgb1 >> 16) & 0xFF)-((rgb2 >> 16) & 0xFF);
		diff[2] = diff[2]*diff[2];
			
		float energy = (float) Math.sqrt(diff[0]+diff[1]+diff[2]);
		return energy;
	}
	
	public float getEnergy(int x, int y){
		assert (energy[x][y] != -1);
		return energy[x][y];
	}
	
	public int getRGB(int x, int y){
		return image.getRGB(x, y);
	}
}
	
