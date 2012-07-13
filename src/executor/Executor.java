package executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import inputAdapters.GUIAdapter;
import colorAverager.AbstractTimeColorAverager;

public class Executor extends Thread {
	private volatile RunningMode desiredRunningMode;
	private RunningMode currentRunningMode;
	public final static int NOCHANNELS = 2;
	private ScheduledFuture<?> timeHandle;
	
	public Executor(RunningMode runningMode){
		this.currentRunningMode = runningMode;
		this.desiredRunningMode = runningMode;
	}
	
	@Override
	public void run(){
		AbstractTimeColorAverager averager;
		//while(currentRunningMode.isRunning()){
			averager = currentRunningMode.getColorAverager();
			averager.run();
//			try {
//				Thread.sleep(currentRunningMode.getReadColorRefreshRate());
//			} catch(InterruptedException e) { }
			// checking if another runningMode has been set
			averager.endPeriod();
			if( !currentRunningMode.equals(desiredRunningMode)){
				currentRunningMode = desiredRunningMode;
			}
		//}
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
		GUIAdapter adapter = GUIAdapter.getInstance(new RunningMode[]{runningMode1, runningMode2}, new Executor[]{executor1, executor2});
		adapter.start();
		runningMode2.getOutputAdapter().startTransmission();
		
		startExecutor(executor1, runningMode1);
		
		
//    	executor1.start();
//    	executor2.start();
	}
	
	private static void startExecutor(Executor executor, RunningMode runningMode){
		// Get the scheduler
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		// Get a handle, starting now
		final ScheduledFuture<?> timeHandle = scheduler.scheduleAtFixedRate(executor, 0, runningMode.getReadColorRefreshRate(), TimeUnit.MILLISECONDS);    
		// Schedule the event, and run
//		scheduler.schedule(new Runnable() {
//					public void run() {
//						runningMode.getColorAverager().startPeriod();
//				        timeHandle.cancel(false);
//				        startExecutor(executor, runningMode);
//				      }
//				    }, runningMode.getReadColorRefreshRate(), TimeUnit.MILLISECONDS);
	}
	public synchronized void setDesiredRunningMode(RunningMode desiredRunningMode){
		this.desiredRunningMode = desiredRunningMode;
	}
	public RunningMode getCurrentRunningMode(){
		return this.currentRunningMode;
	}

}
