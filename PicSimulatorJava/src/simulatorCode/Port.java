package simulatorCode;

public class Port {
	private int value;
	private int tris;// 1=input 0=output
	private int latch;

	private final int width;
	private final String name;

	public Port(String name, int width) {
		this.name = name;
		this.width = width;
		this.value = 0;
		this.tris = 0xFF;
		this.latch = 0;
	}

	public boolean getPin(int bitIndex) {
		return (value & (1 << bitIndex)) != 0;
	}

	public void setPin(int bitIndex, boolean isOn) {
		if (isOn) {
			value |= (1 << bitIndex);
		} else {
			value &= ~(1 << bitIndex);
		}
	}

	public void setTris(int mask) {
		this.tris = mask & getMaxBitMask();
	}

	public void write(int data) {
		this.latch = data & getMaxBitMask();
		this.value = (this.value & tris) | (latch & ~tris);
	}

	public int read() {
		return (value & tris) | (latch & ~tris);
	}

	public int getTris() {
		return tris;
	}

	public int getLatch() {
		return latch;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	private int getMaxBitMask() {
		return (1 << width) - 1;
	}

	public void reset() {
		value = 0;
		tris = 0xFF;
		latch = 0;

	}

	public void updateValue(int newValue) {// only update the state of input
		this.value = (this.value & ~tris) | (newValue & tris);// keep output

	}

	public boolean hasChanged(Port last, int bit) {
		return ((this.value ^ last.value) & (1 << bit)) != 0;
	}

	public boolean isInput(int bit) {
		return (tris & (1 << bit)) != 0;
	}

}
