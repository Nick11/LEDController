package colorAverager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import adapter.OutputAdapter;

public class ManualTimeColorAverager extends AbstractTimeColorAverager {
	private ArrayList<Color> colors;
	private float futureRed, futureGreen, futureBlue, currentRed, currentGreen, currentBlue;
	private int noOutRefreshes;
	private Color outColor;
	private int outColorRefreshRate;
	

	private int colorNo =0;

	public ManualTimeColorAverager(OutputAdapter outputAdapter, int readColorRefreshRate, int outColorRefreshRate, ArrayList<Color> colors) {
		super(null, outputAdapter, readColorRefreshRate, outColorRefreshRate);
		this.colors = colors;
		this.currentRed = 0;
		this.currentGreen = 0;
		this.currentBlue = 0;
		this.noOutRefreshes = (int) Math.floor((float)(readColorRefreshRate)/(float)(outColorRefreshRate));
		this.outColorRefreshRate = outColorRefreshRate;
		
	}

	@Override
	public void run() {
		
		Color futureColor = colors.get(colorNo);
		colorNo=(colorNo+1)%colors.size();
		
		futureRed = futureColor.getRed();
        futureGreen = futureColor.getGreen();
        futureBlue = futureColor.getBlue();

        float stepRed = (futureRed - currentRed) / noOutRefreshes;
        float stepGreen = (futureGreen - currentGreen) / noOutRefreshes;
        float stepBlue = (futureBlue - currentBlue) / noOutRefreshes;
        for (int i = 0; i < noOutRefreshes; i++)
        {
            currentRed += stepRed;
            currentGreen += stepGreen;
            currentBlue += stepBlue;
           
            outColor = new Color(Math.round(currentRed), Math.round(currentGreen), Math.round(currentBlue));
            setColor(outColor);
            try{
                Thread.sleep(outColorRefreshRate);
            }catch (InterruptedException ex){ }
        }
        assert Math.round(currentRed) == futureRed;
        assert Math.round(currentGreen) == futureGreen;
        assert Math.round(currentBlue) == futureBlue;
        currentRed = Math.round(currentRed);
        currentGreen = Math.round(currentGreen);
        currentBlue = Math.round(currentBlue); 

	}
	public int getOutColorRefreshRate() {
		return outColorRefreshRate;
	}

}
