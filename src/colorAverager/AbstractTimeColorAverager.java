package colorAverager;

import java.awt.Color;
import java.util.TimerTask;

import colorReader.AbstractColorReader;
import colorReader.SimplePixelReader;

import adapter.OutputAdapter;
/**
 * When called, this class calculates a averaged color based on the input color from PixelReader.
 * @author Niclas
 *
 */
public abstract class AbstractTimeColorAverager extends TimerTask{
	private AbstractColorReader reader;
	private OutputAdapter outputAdapter;

	public AbstractTimeColorAverager(AbstractColorReader reader, OutputAdapter outputAdapter, int readColorRefreshRate, int outColorRefreshRate){
		this.reader=reader;
		this.outputAdapter = outputAdapter;
	}
	@Override
	abstract public void run();
	
	public Color readOneFramesColor(){
		assert(reader!=null);
		return reader.getColor();
	}
	public void setColor(Color color){
		outputAdapter.setColor(color);
	}
}