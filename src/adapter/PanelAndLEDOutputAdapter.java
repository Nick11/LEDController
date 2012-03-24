package adapter;

import java.awt.Color;

public class PanelAndLEDOutputAdapter implements OutputAdapter {
	private LEDOutputAdapter ledAdapter;
	private PanelOutputAdapter panelAdapter;
	public PanelAndLEDOutputAdapter(){
		ledAdapter = new LEDOutputAdapter();
		panelAdapter = new PanelOutputAdapter();
	}

	@Override
	public void setColor(Color color) {
		ledAdapter.setColor(color);
		this.panelAdapter.setColor(color);

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
