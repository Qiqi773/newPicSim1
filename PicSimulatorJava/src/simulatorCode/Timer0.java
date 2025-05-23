package simulatorCode;

public class Timer0 {

    private int value = 0;
    private int prescalerCount = 0;
    private final DataMemory memory;

    public Timer0(DataMemory memory) {
        this.memory = memory;
    }

    public void tick() {
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
        value = value + 1;
        memory.write(0x01, value);
        if (value == 0x00) {
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
