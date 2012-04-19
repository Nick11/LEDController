package colorAverager;

import java.awt.Color;

import outputAdapters.OutputAdapter;
import colorReader.AbstractColorReader;

public class WeightedTimeColorAverager extends AbstractTimeColorAverager {
	
	private float futureRed, futureGreen, futureBlue, currentRed, currentGreen, currentBlue;
	/**
	 * see <code>RunningMode</code> for details concerning the following fields.
	 */
	private int noOutRefreshes;
	private Color outColor;
	private int outColorRefreshRate;
	private int channelNo;
	
	public WeightedTimeColorAverager(AbstractColorReader reader, OutputAdapter outputAdapter, int readColorRefreshRate, int outColorRefreshRate, int channelNo) {
		super(reader, outputAdapter,readColorRefreshRate, outColorRefreshRate, channelNo);
		this.noOutRefreshes = (int) Math.floor((float)(readColorRefreshRate)/(float)(outColorRefreshRate));
		this.outColorRefreshRate = outColorRefreshRate;
		this.channelNo = channelNo;
		Color futureColor = readOneFramesColor();
		this.currentRed = futureColor.getRed();
		this.currentGreen = futureColor.getGreen();
		this.currentBlue = futureColor.getBlue();
	}
	
	@Override
	public void run() {
		Color futureColor = readOneFramesColor();
		
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
           
            outColor = new Color(weight(currentRed,1), weight(currentGreen,1.3f), weight(currentBlue,0.5f));
            setColor(outColor, channelNo);
            try{
                Thread.sleep(outColorRefreshRate);
            }catch (InterruptedException ex){}
        }
        assert Math.round(currentRed) == futureRed;
        assert Math.round(currentGreen) == futureGreen;
        assert Math.round(currentBlue) == futureBlue;
        currentRed = Math.round(currentRed);
        currentGreen = Math.round(currentGreen);
        currentBlue = Math.round(currentBlue); 
	}

	private int weight(float color,  float factor) {
		int weighted = Math.round(((color/255)*(color/255)*factor*2.5f)*255);
				if(weighted >255)
					weighted = 255;
		return weighted;
	}


}
