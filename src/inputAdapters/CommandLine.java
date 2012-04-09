package inputAdapters;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

import outputAdapters.OutputAdapter;
import colorAverager.AbstractTimeColorAverager;
import colorAverager.ManualTimeColorAverager;
import colorAverager.SimpleTimeColorAverager;
import colorReader.AbstractColorReader;
import colorReader.SimplePixelReader;
import colorReader.SolidColorReader;
import executor.Executor;
import executor.RunningMode;


public class CommandLine extends Thread{

	private Scanner scan;
	private boolean running;
	private volatile RunningMode runningMode;
	private Executor executor;
	
	public CommandLine(RunningMode runningMode, Executor executor){
		this.scan = new Scanner(System.in);
		this.runningMode =  runningMode;
		running = true;
		this.executor = executor;
	}
	
	@Override
	public void run(){
		System.out.println("type solid, auto, setcolor or setcolorsequence to change mode");
		String input;
		
		AbstractColorReader reader;
		OutputAdapter adapter;
		AbstractTimeColorAverager averager ;
		int readColorRefreshRate;
		int outColorRefreshRate;
		int screenNo;
		boolean isRunning;
		
		while(running){
			reader = runningMode.getColorReader();
			adapter = runningMode.getOutputAdapter();
			averager = runningMode.getColorAverager();
			readColorRefreshRate = runningMode.getReadColorRefreshRate();
			outColorRefreshRate = runningMode.getOutColorRefreshRate();
			screenNo = runningMode.getScreenNr();
			isRunning = runningMode.isRunning();
			
			input = scan.next();
			if(input.equalsIgnoreCase( "stop")){
				running = false;
				System.out.println("ByeBye");
				isRunning = false;
			}
			
			if(input.equalsIgnoreCase("solid") || input.equalsIgnoreCase("s")){
				reader = new SolidColorReader(new Color(0, 0, 20));
				System.out.println("solid mode.");
			}
			if(input.equalsIgnoreCase("auto")){
				reader = new SimplePixelReader(runningMode.getScreenNr());
				averager = new SimpleTimeColorAverager(reader, adapter, readColorRefreshRate, outColorRefreshRate);
				System.out.println("running in auto mode.");
			}
			if(input.equalsIgnoreCase("setcolor") ||input.equalsIgnoreCase("sc")){
				System.out.println("red(0-255:)");
				int red = scan.nextInt();
				System.out.println("green(0-255:)");
				int green = scan.nextInt();
				System.out.println("blue(0-255:)");
				int blue = scan.nextInt();
				
				reader = new SolidColorReader(new Color(red, green, blue));
				System.out.println("changed color");
			}
			if(input.equalsIgnoreCase("setcolorsequence") ||input.equalsIgnoreCase("scs")){
				int i=1;
				int red,green,blue;
				boolean run = true;
				ArrayList<Color> colors = new ArrayList<Color>();
				while(run){
					System.out.println(i+". color. a to end sequence");
					System.out.println("red(0-255:)");
					String next = scan.next();
					if(next.equals("a"))
						run=false;
					else{
						red = Integer.valueOf(next);
						System.out.println("green(0-255:)");
						green = scan.nextInt();
						System.out.println("blue(0-255:)");
						blue = scan.nextInt();
						colors.add(new Color(red,green,blue));
						i++;
					}
				}
				averager = new ManualTimeColorAverager(runningMode.getOutputAdapter(), 100, 1, colors);
				System.out.println("changed color sequence");
			}
			//very important to modify the averager!
			if(averager.getClass()==(SimpleTimeColorAverager.class)){
				averager = new SimpleTimeColorAverager(reader, adapter, readColorRefreshRate, outColorRefreshRate);
				System.out.println("ajusting averager");
			}
			runningMode = new RunningMode(reader,
					adapter, averager,
					readColorRefreshRate, outColorRefreshRate,
					screenNo, isRunning);
			executor.setDesiredRunningMode(runningMode);
		}
	}
}
/*
	runningMode = new RunningMode(runningMode.getColorReader(),
						runningMode.getOutputAdapter(), runningMode.getColorAverager(),
						runningMode.getReadColorRefreshRate(), runningMode.getOutColorRefreshRate(),
						runningMode.getScreenNr(), runningMode.isRunning());
						
*/
