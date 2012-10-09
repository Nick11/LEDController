package colorAverager;

import java.awt.Color;
import outputAdapters.OutputAdapter;
import colorReader.AbstractColorReader;

public class WeightedTimeColorAverager extends AbstractTimeColorAverager {
	
	private float futureRed, futureGreen, futureBlue, nextRed, nextGreen, nextBlue;
	private float stepRed, stepGreen, stepBlue;
	/**
	 * see <code>RunningMode</code> for details concerning the following fields.
	 */
	private int periodsBetweenReading;
	private Color outColor;
	private int channelNo;
	private int outCounter;
	
	public WeightedTimeColorAverager(AbstractColorReader reader, OutputAdapter outputAdapter, int periodsBetweenReading, int channelNo) {
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
		outColor = weight(nextRed, nextGreen, nextBlue);
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

	private Color weight(float red, float green, float blue) {
		
		double redNorm = red/255d;
		double greenNorm = green/255d;
		double blueNorm = blue/255d;
		double average = (redNorm+greenNorm+blueNorm)/3;
		double max = Math.max(Math.max(redNorm,greenNorm), blueNorm);
		double b = 0.9;
		double a2 = -(b/average)-(b/(average-1));
		double a1 = 1-a2;
		redNorm = redNorm/max;
		greenNorm = greenNorm/max;
		blueNorm = blueNorm / max;
		double blueConstant = -0.2;
		double blueFactor = 1;
		double redWeight = a1*redNorm+a2*redNorm*redNorm;
		double greenWeight = a1*greenNorm+a2*greenNorm*greenNorm;
		double blueWeight = a1*blueNorm+a2*blueNorm*blueNorm*blueFactor+blueConstant;
		
		int redRounded = (int) (redWeight*255>255? 255 : redWeight*255);
		int greenRounded = (int) (greenWeight*255>255? 255 : greenWeight*255);
		int blueRounded = (int) (blueWeight*255>255? 255 : blueWeight*255);
				
		
		return new Color(redRounded, greenRounded, blueRounded);
	}


}
