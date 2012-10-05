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
	private static ScheduledExecutorService scheduler;
	
	public Executor(RunningMode runningMode){
		this.currentRunningMode = runningMode;
		this.desiredRunningMode = runningMode;
	}
	
	@Override
	public void run(){
		// checking if another runningMode has been set
		if( !currentRunningMode.equals(desiredRunningMode)){
			currentRunningMode = desiredRunningMode;
		}
		AbstractTimeColorAverager averager;
		averager = currentRunningMode.getColorAverager();
		averager.doNext();
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
		
		scheduler = Executors.newSingleThreadScheduledExecutor();
		// Get a handle, starting now
		final ScheduledFuture<?> timeHandle = scheduler.scheduleAtFixedRate(executor, 0, runningMode.getOutColorRefreshRate(), TimeUnit.MILLISECONDS);    
		
		// Schedule the event, and run
//		scheduler.schedule(new Runnable() {
//					public void run() {
//						runningMode.getColorAverager().startPeriod();
//				        timeHandle.cancel(false);
//				        startExecutor(executor, runningMode);
//				      }
//				    }, runningMode.getReadColorRefreshRate(), TimeUnit.MILLISECONDS);
	}
	private void changeExecutor(){
		scheduler.shutdownNow();
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(this, 0, desiredRunningMode.getOutColorRefreshRate(), TimeUnit.MILLISECONDS);
	}
	
	public synchronized void setDesiredRunningMode(RunningMode desiredRunningMode){
		this.desiredRunningMode = desiredRunningMode;
		this.changeExecutor();
	}
	public RunningMode getCurrentRunningMode(){
		return this.currentRunningMode;
	}

}
