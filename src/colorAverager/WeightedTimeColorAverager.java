package colorAverager;

import java.awt.Color;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import outputAdapters.OutputAdapter;
import colorReader.AbstractColorReader;

public class WeightedTimeColorAverager extends AbstractTimeColorAverager {
	
	private float futureRed, futureGreen, futureBlue, currentRed, currentGreen, currentBlue;
	private float stepRed, stepGreen, stepBlue;
	/**
	 * see <code>RunningMode</code> for details concerning the following fields.
	 */
	private int noOutRefreshes;
	private Color outColor;
	private int outColorRefreshRate;
	private int readColorRefreshRate;
	private int channelNo;
	
	public WeightedTimeColorAverager(AbstractColorReader reader, OutputAdapter outputAdapter, int readColorRefreshRate, int outColorRefreshRate, int channelNo) {
		super(reader, outputAdapter,readColorRefreshRate, outColorRefreshRate, channelNo);
		this.noOutRefreshes = (int) Math.floor((float)(readColorRefreshRate)/(float)(outColorRefreshRate));
		this.outColorRefreshRate = outColorRefreshRate;
		this.readColorRefreshRate = readColorRefreshRate;
		this.channelNo = channelNo;
		Color futureColor = readOneFramesColor();
		this.currentRed = futureColor.getRed();
		this.currentGreen = futureColor.getGreen();
		this.currentBlue = futureColor.getBlue();
	}
	public void startPeriod(){
       	Color futureColor = readOneFramesColor();
		
		futureRed = futureColor.getRed();
        futureGreen = futureColor.getGreen();
        futureBlue = futureColor.getBlue();

        stepRed = (futureRed - currentRed) / noOutRefreshes;
        stepGreen = (futureGreen - currentGreen) / noOutRefreshes;
        stepBlue = (futureBlue - currentBlue) / noOutRefreshes;

		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		final ScheduledFuture<?> timeHandle = scheduler.scheduleAtFixedRate(new Runner(), 0, outColorRefreshRate, TimeUnit.MILLISECONDS);
//		scheduler.schedule(new Runnable() {
//			public void run() {
//		        timeHandle.cancel(false);
//		       
//		      }
//		    }, readColorRefreshRate, TimeUnit.MILLISECONDS);
		
	}
	public void endPeriod(){
//		assert (Math.round(currentRed) == futureRed);
//		assert (Math.round(currentGreen) == futureGreen);
//		assert (Math.round(currentBlue) == futureBlue);
		currentRed = Math.round(currentRed);
	    currentGreen = Math.round(currentGreen);
	    currentBlue = Math.round(currentBlue); 
	}
	
	@Override
	public void run() {
		
		startPeriod();
	}
	private class Runner extends Thread{
		
		public void run(){
        
//        for (int i = 0; i < noOutRefreshes; i++)
//        {
            currentRed += stepRed;
            currentGreen += stepGreen;
            currentBlue += stepBlue;
           
            outColor = new Color(weight(currentRed,1), weight(currentGreen,1.3f), weight(currentBlue,0.5f));
            setColor(outColor, channelNo);
            try{
                Thread.sleep(outColorRefreshRate);
            }catch (InterruptedException ex){}
        }

//	}
	}

	private int weight(float color,  float factor) {
		int weighted = Math.round(((color/255)*(color/255)*factor*8f)*color);
				if(weighted >255)
					weighted = 255;
		return (int)color;// weighted;
	}


}
