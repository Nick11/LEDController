package executor;

import java.awt.Color;
import java.util.ArrayList;
import outputAdapters.OutputAdapter;
import outputAdapters.PanelAndLEDOutputAdapter;
import colorAverager.AbstractTimeColorAverager;
import colorAverager.SimpleTimeColorAverager;
import colorAverager.WeightedTimeColorAverager;
import colorReader.AbstractColorReader;
import colorReader.AreaPixelReader;
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
     * How often should a new color be read and evaluated.
     * if this is set high, the color doesn't change fast enough in fast scenes.
     * if it is set to lof the color might change hectically. 
     */
    private final int periodsBetweenReading;
    /**
     * in ms. determines how often the LEDs color should be updated per reading-period
     * if this is set to high, there might be performance issues, but no visible effects.
     * if this is set to low, the transition between different colors is not smooth anymore.
     * MUST be 1 or bigger.
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
     * @param channelNo
     */
    public RunningMode(AbstractColorReader currentColorReader,OutputAdapter currentOutputAdapter,
				AbstractTimeColorAverager currentColorAverager,
				int periodsBetweenReading, int outColorRefreshRate, int screenNr, boolean isRunning, int channelNo) {
		this.colorReader = currentColorReader;
		this.outputAdapter = currentOutputAdapter;
		this.colorAverager = currentColorAverager;
		this.periodsBetweenReading = periodsBetweenReading;
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

	public int getPeriodsBetweenReading() {
		return periodsBetweenReading;
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
		return false; //TODO
	}

	public static RunningMode getDefault(int channelNr) {
		
		OutputAdapter adapter =  new PanelAndLEDOutputAdapter();
		int noOutRefreshes = 1;
		int outColorRefreshRate = 100;
		int screenNr = 0;
		AbstractColorReader colorReader = new  SolidColorReader(new Color(255,0,200));  //AreaPixelReader(screenNr); //SimplePixelReader(screenNr);//RandomColorReader();
		AbstractTimeColorAverager averager = new SimpleTimeColorAverager(colorReader, adapter, noOutRefreshes, channelNr);
		return new RunningMode(colorReader, adapter,
				averager,
				noOutRefreshes, outColorRefreshRate, screenNr, true, channelNr);
	}
    
}
