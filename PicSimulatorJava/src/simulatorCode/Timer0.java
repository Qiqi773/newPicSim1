package simulatorCode;

public class Timer0 {

	private int value = 0;
	private int prescalerCount = 0;
	private final DataMemory memory;
	private int tickCount = 0;

	public Timer0(DataMemory memory) {
		this.memory = memory;
	}

	public int getTickCount() {
		return tickCount;
	}

	//
	public void tick() {
		tickCount++;
		if (memory.isInternalClock()) {
			prescalerCount++;
			if (prescalerCount > memory.getPrescalerRate()) {
				prescalerCount = 0;
				incrementTMR0();
			}
		} else {
			incrementTMR0();
		}

	}

	private void incrementTMR0() {
		value = (value + 1) & 0xFF;
		System.out.println("timer 0 value:" + value);
		memory.write(0x01, value);
		if (value == 0x00) {
			//System.out.println("overflowed!");
			memory.setTMR0OverflowFlag();
		}
	}

	public void reset() {
		value = 0;
		prescalerCount = 0;
		memory.write(0x01, 0);
		memory.clearTMR0OverflowFlag();
	}

}
