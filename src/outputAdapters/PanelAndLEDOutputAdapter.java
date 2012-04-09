package outputAdapters;

import java.awt.Color;

public class PanelAndLEDOutputAdapter implements OutputAdapter {
	private LEDOutputAdapter ledAdapter;
	private PanelOutputAdapter panelAdapter;
	public PanelAndLEDOutputAdapter(){
		ledAdapter = new LEDOutputAdapter();
		panelAdapter = new PanelOutputAdapter();
	}

	@Override
	public void setColor(Color color, int channelNo) {
		ledAdapter.setColor(color, channelNo);
		this.panelAdapter.setColor(color, channelNo);

	}

	@Override
	public void startTransmission() {
		this.ledAdapter.startTransmission();
		this.panelAdapter.startTransmission();

	}

	@Override
	public void endTransmission() {
		this.ledAdapter.endTransmission();
		this.panelAdapter.endTransmission();
	}

}
