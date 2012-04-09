package outputAdapters;

import java.awt.Color;

public interface OutputAdapter {

	public void setColor(Color color, int channelNo);

	public void startTransmission();

	public void endTransmission();
	
}
