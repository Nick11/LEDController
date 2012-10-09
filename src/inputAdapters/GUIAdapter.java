package inputAdapters;

import java.awt.Color;
import java.util.NoSuchElementException;
import outputAdapters.OutputAdapter;
import colorAverager.AbstractTimeColorAverager;
import colorAverager.SimpleTimeColorAverager;
import colorAverager.WeightedTimeColorAverager;
import colorReader.AbstractColorReader;
import colorReader.SimplePixelReader;
import colorReader.SolidColorReader;
import ui.MainWindow;
import executor.Executor;
import executor.RunningMode;

/**
 * reads the input from the GUI and hands it over to the executor.
 * starts the GUI.
 * Singleton
 * @author Niclas
 *
 */
public class GUIAdapter extends Thread{
	/**
	 * immutable object containing the information about the mode to run in.  
	 */
	private volatile RunningMode[] modes;
	/**
	 * array of <code>Executor</code>. One for each channel.
	 */
	private Executor[] executors;
	/**
	 * instance of this class
	 */
	private static GUIAdapter instance=null;
	/**
	 * parameters of <code>mode</code>.
	 */
	private AbstractColorReader reader;
	private OutputAdapter outputAdapter;
	private AbstractTimeColorAverager averager ;
	private int periodsBetweenReading;
	private int outColorRefreshRate;
	private int screenNo;
	private boolean isRunning;
	private int channelNo;
	
	private GUIAdapter(RunningMode[] modes, Executor[] executors){
		super();
		this.modes=modes;
		this.executors=executors;
	}
	/**
	 * Use this method instead of the constructor.
	 * 
	 * @param mode to run in
	 * @param executor the <code>Executor</code> who called this
	 * @return an existing or newly created instance of this.
	 */
	public static GUIAdapter getInstance(RunningMode[] modes, Executor[] executors){
		if(instance== null){
			instance = new GUIAdapter(modes, executors);
		}
		return instance;
	}
	/**
	 * returns only an instance, if one has been created before. Throws a <code>NoSuchElementException</code> if not.
	 * @return
	 */
	public static GUIAdapter getInstance(){
		if(instance!=null)
			return instance;
		else
			throw(new NoSuchElementException("No GUIAdapter has been created yet."));
		
	}
	
	@Override
	public void run(){
		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		
	}
	/**
	 * transfers a single color to the executor
	 * @param red between 0 and 100
	 * @param green between 0 and 100
	 * @param blue between 0 and 100
	 */
	public void setSingleColor(int red, int green, int blue, int id){
		assert(red<=MainWindow.MAX && green<=MainWindow.MAX && blue<=MainWindow.MAX);
		assert(red>=MainWindow.MIN  && green>=MainWindow.MIN  && blue>=MainWindow.MIN );
		updateParameters(modes[id]);
		Color color = new Color(red, green, blue);
		reader = new SolidColorReader(color);
		//readColorRefreshRate=FAST;
		channelNo = id;
		setRunningMode();
	}
	/**
	 * transfers an array of colors to the executor
	 * @param colors <code>int[*][3]</code> values between 0 and 100
	 */
	public void setColorSequence(int[][] colors, int id){
		updateParameters(modes[id]);
		Color[] colorArray = new Color[colors.length];
		for(int i=0; i<colors.length; i++){
			for(int j=0; j<colors[0].length; j++){
				assert(colors[i][j]>= MainWindow.MIN && colors[i][j]<= MainWindow.MAX);
				colors[i][j] = colors[i][j]*255/100;
			}
			colorArray[i] = new Color(colors[i][0],colors[i][1],colors[i][2]);
		}
		reader = new SolidColorReader(colorArray);
		//readColorRefreshRate=SLOW;
		channelNo = id;
		setRunningMode();
	}
	/**
	 * signals the <code>executor</code> to switch to auto-mode
	 */
	public void setAutoMode(int id){
		updateParameters(modes[id]);
		reader = new SimplePixelReader(0);
		channelNo = id;
		setRunningMode();
	}
	public void setPeriodsBetweenReading(int periods, int id){
		updateParameters(modes[id]);
		this.periodsBetweenReading = periods;
		channelNo = id;
		setRunningMode();
	}
	
	public void setOutColorRefreshRate(int outColorRefreshRate, int id){
		updateParameters(modes[id]);
		this.outColorRefreshRate = outColorRefreshRate;
		channelNo = id;
		setRunningMode();
	}
	
	private void updateParameters(RunningMode mode){
		this.outputAdapter = mode.getOutputAdapter();
		this.averager = mode.getColorAverager();
		this.reader = mode.getColorReader();
		this.isRunning = mode.isRunning();
		this.outColorRefreshRate = mode.getOutColorRefreshRate();
		this.periodsBetweenReading = mode.getPeriodsBetweenReading();
		this.screenNo = mode.getScreenNr();
		this.channelNo = mode.getChannelNo();
	}
	private void setRunningMode(){
		//very important to modify the averager!
		if(averager.getClass()==(WeightedTimeColorAverager.class)){
			averager = new WeightedTimeColorAverager(reader, outputAdapter, periodsBetweenReading, channelNo);
		}
		if(averager.getClass()==(SimpleTimeColorAverager.class)){
			averager = new SimpleTimeColorAverager(reader, outputAdapter, periodsBetweenReading, channelNo);
		}
		modes[channelNo] = new RunningMode(reader, outputAdapter,averager,
				periodsBetweenReading, outColorRefreshRate, screenNo, isRunning, channelNo);
		executors[channelNo].setDesiredRunningMode(modes[channelNo]);
	}
	
	//getters
	public AbstractColorReader[] getColorReader(){
		AbstractColorReader[] colorReader= new AbstractColorReader[Executor.NOCHANNELS];
		RunningMode runningMode;
		for(int i=0; i<Executor.NOCHANNELS; i++){
			runningMode = executors[i].getCurrentRunningMode();
			colorReader[i] = runningMode.getColorReader();
		}
		return colorReader;
	}
	public OutputAdapter[] getOutputAdapter(){
		OutputAdapter[] outputAdapter= new OutputAdapter[Executor.NOCHANNELS];
		RunningMode runningMode;
		for(int i=0; i<Executor.NOCHANNELS; i++){
			runningMode = executors[i].getCurrentRunningMode();
			outputAdapter[i] = runningMode.getOutputAdapter();
		}
		return outputAdapter;
	}
	public AbstractTimeColorAverager[] getColorAverager(){
		AbstractTimeColorAverager[] colorAverager= new AbstractTimeColorAverager[Executor.NOCHANNELS];
		RunningMode runningMode;
		for(int i=0; i<Executor.NOCHANNELS; i++){
			runningMode = executors[i].getCurrentRunningMode();
			colorAverager[i] = runningMode.getColorAverager();
		}
		return colorAverager;
	}
	
	
	public int[] getPeriodsBetweenReading(){
		int[] periodsBetweenReadings= new int[Executor.NOCHANNELS];
		RunningMode runningMode;
		for(int i=0; i<Executor.NOCHANNELS; i++){
			runningMode = executors[i].getCurrentRunningMode();
			periodsBetweenReadings[i] = runningMode.getPeriodsBetweenReading();
		}
		return periodsBetweenReadings;
	}
	
	public int[] getOutColorRefreshRate(){
		int[] outColorRefreshRates= new int[Executor.NOCHANNELS];
		RunningMode runningMode;
		for(int i=0; i<Executor.NOCHANNELS; i++){
			runningMode = executors[i].getCurrentRunningMode();
			outColorRefreshRates[i] = runningMode.getOutColorRefreshRate();
		}
		return outColorRefreshRates;
	}
	public int[] getScreenNr(){
		int[] screenNumber= new int[Executor.NOCHANNELS];
		RunningMode runningMode;
		for(int i=0; i<Executor.NOCHANNELS; i++){
			runningMode = executors[i].getCurrentRunningMode();
			screenNumber[i] = runningMode.getScreenNr();
		}
		return screenNumber;
	}
	public boolean[] areRunning(){
		boolean[] areRunning= new boolean[Executor.NOCHANNELS];
		RunningMode runningMode;
		for(int i=0; i<Executor.NOCHANNELS; i++){
			runningMode = executors[i].getCurrentRunningMode();
			areRunning[i] = runningMode.isRunning();
		}
		return areRunning;
	}
}
