package colorAverager;

import java.awt.Color;
import java.util.TimerTask;

import outputAdapters.OutputAdapter;

import colorReader.ColorReaderInterface;
import colorReader.SimplePixelReader;

/**
 * When called, this class calculates a averaged color based on the input color from PixelReader.
 * @author Niclas
 *
 */
public abstract class AbstractTimeColorAverager{
	private ColorReaderInterface reader;
	private OutputAdapter outputAdapter;
	private int channelNo;

	public AbstractTimeColorAverager(ColorReaderInterface reader, OutputAdapter outputAdapter,int noOutRefreshes, int channelNo){
		this.reader=reader;
		this.outputAdapter = outputAdapter;
		this.channelNo= channelNo;
	}
	/**
	 * starts reading and averaging the colors. Writes them to a <code>OutputAdapter</code>.
	 */
	abstract public void doNext();
	
	public Color readOneFramesColor(){
		assert(reader!=null);
		return reader.getColor();
	}
	/**
	 * writes <code>color</code> to channel <code>cannelNo</code>.
	 * @param color
	 * @param channelNo
	 */
	public void setColor(Color color, int channelNo){
		outputAdapter.setColor(color, channelNo);
	}
}