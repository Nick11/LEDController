package executor;

import java.awt.Color;
import java.util.ArrayList;

import outputAdapters.LEDOutputAdapter;
import outputAdapters.OutputAdapter;
import outputAdapters.PanelAndLEDOutputAdapter;
import outputAdapters.PanelOutputAdapter;

import colorAverager.AbstractTimeColorAverager;
import colorAverager.ManualTimeColorAverager;
import colorAverager.SimpleTimeColorAverager;
import colorReader.AbstractColorReader;
import colorReader.RandomColorReader;
import colorReader.SimplePixelReader;
import colorReader.SolidColorReader;
/**
 * Immutable data object. Changed by the UI and read by the <code>Executor</code>
 * This is introduced for synchronization purpose.
 * Do not clone this object, as it is not updated, but replaced.
 * @author Niclas
 *
 */
public class RunningMode {
	/**
	 * creates a <code>Color</code>, depending on the concrete implementation, based on
	 *  the screen color or a solid color.
	 */
    private final AbstractColorReader colorReader;
    /**
     * manages all actions with the LED or Panel controlling
     */
    private OutputAdapter outputAdapter;
    /**
     * gets a color processes it and forwards it to the OutputAdapter
     */
    private final AbstractTimeColorAverager colorAverager;
    /**
     * in ms. How often should a new color be read and evaluated.
     * if this is set low, the color doesn't change fast enough in fast scenes.
     * if it is set to high the color might change hectically. 
     * MUST be larger than <code>readColorRefreshRate</code>.
     */
    private final int readColorRefreshRate;
    /**
     * in ms. determines how often the LEDs color should be updated.
     * if this is set to high, there might be performance issues, but no visible effects.
     * if this is set to low, the transition between different colors is not smooth anymore.
     * MUST be smaller than <code>outColorRefreshRate</code>.
     */
    private final int outColorRefreshRate;
    /**
     * determines which screen will be analyzed
     */
    private final int screenNo;
    /**
     * false if the program should stop running, true else.
     */
    private final boolean isRunning;
    /**
     * number of the channel of the LEDController hardware to be affected by this instance.
     */
    private final int channelNo;
    /**
     * WARNING: when creating a new instance, the ColorTimeAverager has to be created new.
     * It's the only object depending on the other fields of this class.
     * @param currentColorReader
     * @param currentOutputAdapter
     * @param currentColorAverager
     * @param readColorRefreshRate
     * @param outColorRefreshRate
     * @param screenNr
     * @param isRunning
     */
    public RunningMode(AbstractColorReader currentColorReader,OutputAdapter currentOutputAdapter,
				AbstractTimeColorAverager currentColorAverager,
				int readColorRefreshRate, int outColorRefreshRate, int screenNr, boolean isRunning, int channelNo) {
    	if(readColorRefreshRate<outColorRefreshRate){
    		throw(new IllegalArgumentException("readColorRefreshRatemust be larger than outColorRefreshRate.")); //not good to
    		//throw exceptions in the constructor. but this criteria is crucial.
    	}
		this.colorReader = currentColorReader;
		this.outputAdapter = currentOutputAdapter;
		this.colorAverager = currentColorAverager;
		this.readColorRefreshRate = readColorRefreshRate;
		this.outColorRefreshRate = outColorRefreshRate;
		this.screenNo = screenNr;
		this.isRunning = isRunning;
		this.channelNo = channelNo;
	}

	public AbstractColorReader getColorReader() {
		return colorReader;
	}

	public OutputAdapter getOutputAdapter() {
		return outputAdapter;
	}

	public AbstractTimeColorAverager getColorAverager() {
		return colorAverager;
	}

	public int getReadColorRefreshRate() {
		return readColorRefreshRate;
	}

	public int getOutColorRefreshRate() {
		return outColorRefreshRate;
	}

	public int getScreenNr() {
		return screenNo;
	}

	public boolean isRunning() {
		return isRunning;
	}
	public int getChannelNo(){
		return this.channelNo;
	}
	@Override
	public boolean equals(Object runningMode){
		return false;
	}

	public static RunningMode getDefault() {
		
		OutputAdapter adapter =  new PanelOutputAdapter();
		
		int readColorRefreshRate = 1;
		int outColorRefreshRate = 1;
		int screenNr = 0;
		int channelNr = 0;
		AbstractColorReader colorReader = new  SimplePixelReader(screenNr);//RandomColorReader();// SolidColorReader(new Color(139,90,43));//
		AbstractTimeColorAverager averager = new SimpleTimeColorAverager(colorReader, adapter, readColorRefreshRate, outColorRefreshRate, channelNr);
		return new RunningMode(colorReader, adapter,
				averager,
				readColorRefreshRate, outColorRefreshRate, screenNr, true, channelNr);
	}
    
}
