package colorAverager;

import java.awt.Color;

import outputAdapters.OutputAdapter;
import colorReader.AbstractColorReader;

public class SimpleTimeColorAverager extends AbstractTimeColorAverager {
	
	private float futureRed, futureGreen, futureBlue, currentRed, currentGreen, currentBlue;
	/**
	 * see <code>RunningMode</code> for details concerning the following fields.
	 */
	private int noOutRefreshes;
	private Color outColor;
	private int outColorRefreshRate;
	private int channelNo;
	
	public SimpleTimeColorAverager(AbstractColorReader reader, OutputAdapter outputAdapter, int readColorRefreshRate, int outColorRefreshRate, int channelNo) {
		super(reader, outputAdapter,readColorRefreshRate, outColorRefreshRate, channelNo);
		this.currentRed = 0;
		this.currentGreen = 0;
		this.currentBlue = 0;
		this.noOutRefreshes = (int) Math.floor((float)(readColorRefreshRate)/(float)(outColorRefreshRate));
		this.outColorRefreshRate = outColorRefreshRate;
		this.channelNo = channelNo;
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
           
            outColor = new Color(Math.round(currentRed), Math.round(currentGreen), Math.round(currentBlue));
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


	

}
