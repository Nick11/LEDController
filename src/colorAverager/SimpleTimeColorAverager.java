package colorAverager;

import java.awt.Color;
import outputAdapters.OutputAdapter;
import colorReader.ColorReaderInterface;

public class SimpleTimeColorAverager extends AbstractTimeColorAverager {
	
	private int futureRed, futureGreen, futureBlue, nextRed, nextGreen, nextBlue;
	private int stepRed, stepGreen, stepBlue;
	/**
	 * see <code>RunningMode</code> for details concerning the following fields.
	 */
	private int periodsBetweenReading;
	private Color outColor;
	private int channelNo;
	private int outCounter;
	
	public SimpleTimeColorAverager(ColorReaderInterface reader, OutputAdapter outputAdapter, int periodsBetweenReading, int channelNo) {
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
		assert(nextRed>=0 && nextGreen>=0 && nextBlue>= 0);
		assert(nextRed<=255 && nextGreen<=255 && nextBlue<=255);
		try{
			outColor = new Color(nextRed, nextGreen, nextBlue);
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}
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
