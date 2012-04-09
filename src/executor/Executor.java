package executor;

import inputAdapters.GUIAdapter;
import colorAverager.AbstractTimeColorAverager;

public class Executor extends Thread {
	private volatile RunningMode desiredRunningMode;
	private RunningMode currentRunningMode;
	
	public Executor(RunningMode runningMode){
		this.currentRunningMode = runningMode;
		this.desiredRunningMode = runningMode;
	}
	
	@Override
	public void run(){
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
		RunningMode runningMode1 = RunningMode.getDefault(0);
		RunningMode runningMode2 = RunningMode.getDefault(1);
		Executor executor1= new Executor(runningMode1);
		Executor executor2= new Executor(runningMode2);
		GUIAdapter adapter = GUIAdapter.getInstance(runningMode1, new Executor[]{executor1, executor2});
		adapter.start();
		runningMode2.getOutputAdapter().startTransmission();
    	executor1.start();
    	executor2.start();
	}
	public synchronized void setDesiredRunningMode(RunningMode desiredRunningMode){
		this.desiredRunningMode = desiredRunningMode;
	}

}
