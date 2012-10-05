package colorAverager;

import java.awt.Color;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import outputAdapters.OutputAdapter;
import colorReader.AbstractColorReader;

public class WeightedTimeColorAverager extends AbstractTimeColorAverager {
	
	private float futureRed, futureGreen, futureBlue, nextRed, nextGreen, nextBlue;
	private float stepRed, stepGreen, stepBlue;
	/**
	 * see <code>RunningMode</code> for details concerning the following fields.
	 */
	private int noOutRefreshes;
	private Color outColor;
	private int channelNo;
	private int outCounter;
	
	public WeightedTimeColorAverager(AbstractColorReader reader, OutputAdapter outputAdapter, int noOutRefreshes, int channelNo) {
		super(reader, outputAdapter, noOutRefreshes, channelNo);
		this.channelNo = channelNo;
		this.outCounter = 0;
		this.noOutRefreshes = noOutRefreshes;
		Color futureColor = readOneFramesColor();
		this.nextRed = futureColor.getRed();
		this.nextGreen = futureColor.getGreen();
		this.nextBlue = futureColor.getBlue();
		this.stepRed = 0;
		this.stepGreen = 0;
		this.stepBlue = 0;
	}
	
	public void doNext(){
		outputColor();
		calculateNextColor();
		if(outCounter==0){
			endPeriod();
		}
		outCounter=(outCounter+1)%noOutRefreshes ;
	}
	private void outputColor(){
		outColor = new Color(weight(nextRed,1), weight(nextGreen,1.3f), weight(nextBlue,0.5f));
		setColor(outColor, channelNo);
	}
	private void calculateNextColor(){
		nextRed += stepRed;
        nextGreen += stepGreen;
        nextBlue += stepBlue;
	}
	protected void endPeriod(){
//		assert (Math.round(currentRed) == futureRed);
//		assert (Math.round(currentGreen) == futureGreen);
//		assert (Math.round(currentBlue) == futureBlue);
		
		nextRed = Math.round(nextRed);
	    nextGreen = Math.round(nextGreen);
	    nextBlue = Math.round(nextBlue); 
	    
		Color futureColor = readOneFramesColor();
		futureRed = futureColor.getRed();
        futureGreen = futureColor.getGreen();
        futureBlue = futureColor.getBlue();
        
        stepRed = (futureRed - nextRed) / noOutRefreshes;
        stepGreen = (futureGreen - nextGreen) / noOutRefreshes;
        stepBlue = (futureBlue - nextBlue) / noOutRefreshes;
	}

	private int weight(float color,  float factor) {
		int weighted = Math.round(((color/255)*(color/255)*factor*8f)*color);
				if(weighted >255)
					weighted = 255;
		return (int)color;// weighted;
	}


}
