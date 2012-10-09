package colorAverager;

import java.awt.Color;
import outputAdapters.OutputAdapter;
import colorReader.AbstractColorReader;

public class SimpleTimeColorAverager extends AbstractTimeColorAverager {
	
	private float futureRed, futureGreen, futureBlue, nextRed, nextGreen, nextBlue;
	private float stepRed, stepGreen, stepBlue;
	/**
	 * see <code>RunningMode</code> for details concerning the following fields.
	 */
	private int periodsBetweenReading;
	private Color outColor;
	private int channelNo;
	private int outCounter;
	
	public SimpleTimeColorAverager(AbstractColorReader reader, OutputAdapter outputAdapter, int periodsBetweenReading, int channelNo) {
		super(reader, outputAdapter, periodsBetweenReading, channelNo);
		this.channelNo = channelNo;
		this.outCounter = 0;
		this.periodsBetweenReading = periodsBetweenReading;
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
		outCounter=(outCounter+1)%periodsBetweenReading ;
	}
	private void outputColor(){
		outColor = new Color(nextRed, nextGreen, nextBlue);
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
        
        stepRed = (futureRed - nextRed) / periodsBetweenReading;
        stepGreen = (futureGreen - nextGreen) / periodsBetweenReading;
        stepBlue = (futureBlue - nextBlue) / periodsBetweenReading;
	}

}
