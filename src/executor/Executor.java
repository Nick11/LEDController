package executor;

import colorAverager.AbstractTimeColorAverager;
import ui.CommandLine;

public class Executor extends Thread {
	private volatile RunningMode desiredRunningMode;
	private RunningMode currentRunningMode;
	private static Executor instance = null;
	
	private Executor(RunningMode runningMode){
		this.currentRunningMode = runningMode;
		this.desiredRunningMode = runningMode;
		
	}
	public static Executor getInstance(RunningMode runningMode){
		if(instance == null){
			instance = new Executor(runningMode);
		}
		return instance;
	}
	
	@Override
	public void run(){
		setUp();
		AbstractTimeColorAverager averager;
		while(currentRunningMode.isRunning()){
			averager = currentRunningMode.getColorAverager();
			averager.run();
			try {
				Thread.sleep(currentRunningMode.getReadColorRefreshRate());
			} catch(InterruptedException e) { }
			// checking if another runningMode has been set
			if( !currentRunningMode.equals(desiredRunningMode)){
				currentRunningMode = desiredRunningMode;
			}
		}
		tearDown();
	}
	
	private void setUp(){
		currentRunningMode.getOutputAdapter().startTransmission();
	}
	private void tearDown(){
		currentRunningMode.getOutputAdapter().endTransmission();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RunningMode runningMode = RunningMode.getDefault();
		Executor executor= Executor.getInstance(runningMode);
		
    	CommandLine cmd = new CommandLine(runningMode);
    	cmd.start();
    	executor.run();

	}
	public synchronized void setDesiredRunningMode(RunningMode desiredRunningMode){
		this.desiredRunningMode = desiredRunningMode;
		System.out.println("changed desiredRunningMode");
	}

}
