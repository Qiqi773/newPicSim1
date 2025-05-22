package simulatorCode;

public class Timer0 {

    private int value = 0;
    private int prescalerCount = 0;
    private final DataMemory memory;

    public Timer0(DataMemory memory) {
        this.memory=memory;
    }
    
    public void tick() {
        if(!memory.isInternalClock()){
            return;
        }
    }

}
