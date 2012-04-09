package inputAdapters;

import java.awt.Color;
import java.util.NoSuchElementException;

import outputAdapters.OutputAdapter;

import colorAverager.AbstractTimeColorAverager;
import colorAverager.SimpleTimeColorAverager;
import colorReader.AbstractColorReader;
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
	private volatile RunningMode mode;
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
	private int readColorRefreshRate;
	private int outColorRefreshRate;
	private int screenNo;
	private boolean isRunning;
	private int channelNo;
	/**
	 * default values for the refresh rates. in ms
	 */
	private final int SLOW = 1500;
	private final int FAST = 10;
	
	private GUIAdapter(RunningMode mode, Executor[] executors){
		super();
		this.mode=mode;
		this.executors=executors;
	}
	/**
	 * Use this method instead of the constructor.
	 * 
	 * @param mode to run in
	 * @param executor the <code>Executor</code> who called this
	 * @return an existing or newly created instance of this.
	 */
	public static GUIAdapter getInstance(RunningMode mode, Executor[] executors){
		if(instance== null){
			instance = new GUIAdapter(mode, executors);
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
		updateParameters(mode);
		red = red*255/100;
		green = green*255/100;
		blue = blue*255/100;
		Color color = new Color(red, green, blue);
		reader = new SolidColorReader(color);
		readColorRefreshRate=FAST;
		channelNo = id;
		setRunningMode();
	}
	/**
	 * transfers an array of colors to the executor
	 * @param colors <code>int[*][3]</code> values between 0 and 100
	 */
	public void setColorSequence(int[][] colors, int id){
		updateParameters(mode);
		Color[] colorArray = new Color[colors.length];
		for(int i=0; i<colors.length; i++){
			for(int j=0; j<colors[0].length; j++){
				assert(colors[i][j]>= MainWindow.MIN && colors[i][j]<= MainWindow.MAX);
				colors[i][j] = colors[i][j]*255/100;
			}
			colorArray[i] = new Color(colors[i][0],colors[i][1],colors[i][2]);
		}
		reader = new SolidColorReader(colorArray);
		readColorRefreshRate=SLOW;
		channelNo = id;
		setRunningMode();
	}
	
	private void updateParameters(RunningMode mode){
		this.outputAdapter = mode.getOutputAdapter();
		this.averager = mode.getColorAverager();
		this.reader = mode.getColorReader();
		this.isRunning = mode.isRunning();
		this.outColorRefreshRate = mode.getOutColorRefreshRate();
		this.readColorRefreshRate = mode.getReadColorRefreshRate();
		this.screenNo = mode.getScreenNr();
		this.channelNo = mode.getChannelNo();
	}
	private void setRunningMode(){
		//very important to modify the averager!
		if(averager.getClass()==(SimpleTimeColorAverager.class)){
			averager = new SimpleTimeColorAverager(reader, outputAdapter, readColorRefreshRate, outColorRefreshRate, channelNo);
		}
		mode = new RunningMode(reader, outputAdapter,averager,
				readColorRefreshRate, outColorRefreshRate, screenNo, isRunning, channelNo);
		executors[channelNo].setDesiredRunningMode(mode);
	}
}
