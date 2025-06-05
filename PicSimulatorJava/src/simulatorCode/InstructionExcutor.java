 package simulatorCode;

/**
 * Run the programm (=testfile). before loaded Testfile (=Programm) runs, have
 * to make a new object to run on.
 */
public class InstructionExcutor {

    private DataMemory memory;

    public InstructionExcutor(DataMemory memory) {

        this.memory = memory;
    }

    public void movlw(int instruction) {
        int literal = instruction & 0xFF;
        memory.setW(literal);
        memory.incrementPC();
        memory.tickTimer0();
    }

    public void movwf(int instruction) {
        int f = instruction & 0x007F;
        int w = memory.getW();

        memory.write(f, w);
        memory.incrementPC();
        memory.tickTimer0();
    }

    public void movf(int instruction) {
        int f = instruction & 0x007F; // address
        int d = (instruction >> 7) & 0x01;

        int value = memory.read(f);// value

        memory.setZeroFlag(value == 0); // false

        if (d == 0) {
            memory.setW(value);
        } else {
            memory.write(f, value);

        }

        memory.incrementPC();
        memory.tickTimer0();
    }

    public void clrw(int instruction) {
        memory.setW(0);// W=0;

        memory.setZeroFlag(true);

        memory.incrementPC();
        memory.tickTimer0();

    }

    public void clrf(int instruction) {
        int f = instruction & 0x007F;
        memory.write(f, 0);
        memory.setZeroFlag(true);
        memory.incrementPC();
        memory.tickTimer0();

    }

    public void addlw(int instruction) {
        int k = instruction & 0x00FF;
        int w = memory.getW();

        int result = w + k;

        memory.setW(result & 0x00FF);
        memory.setZeroFlag((result & 0xFF) == 0);

        boolean dc = ((w & 0x0F) + (k & 0x0F)) > 0x0F;
        memory.setDigitatCarryFlag(dc);

        boolean c = result > 0xFF;
        memory.setCarryFlag(c);

        memory.incrementPC();
        memory.tickTimer0();
    }

    public void sublw(int instruction) {
        int k = instruction & 0x00FF;
        int w = memory.getW();

        int result = (k - w) & 0xFF;
        memory.setW(result);

        memory.setZeroFlag(result == 0);

        memory.setCarryFlag(k >= w);

        memory.setDigitatCarryFlag((k & 0x0F) >= (w & 0x0F));

        memory.incrementPC();
        memory.tickTimer0();

    }

    public void subwf(int instruction) {
        int f = instruction & 0x007F;
        int d = (instruction >> 7) & 0x1;

        int w = memory.getW();
        int fVal = memory.read(f);
        int result = (fVal - w) & 0xFF;

        if (d == 0) {
            memory.setW(result);
        } else {
            memory.write(f, result);
        }

        memory.setZeroFlag(result == 0);

        memory.setCarryFlag(fVal >= w);

        memory.setDigitatCarryFlag((fVal & 0x0F) >= (w & 0x0F));

        memory.incrementPC();
        memory.tickTimer0();

    }

    public void goTo(int instruction) { // no PCLath
        int k = instruction & 0x07FF;

        memory.setPC(k);
        memory.tickTimer0();
    }

    public void call(int instruction) { // no PCLath
        int targetAddress = instruction & 0x07FF;

        memory.writeInstack(memory.getPC() + 1); // remember next instruction after CALL

        memory.setPC(targetAddress); // (go to) targetaddr
        memory.tickTimer0();

    }

    public void returnFromSub() { // no PCLath - return from SUBroutine/-program

        int returnAddress = memory.readFromStack();

        memory.setPC(returnAddress);
        memory.tickTimer0();
    }

    public void addwf(int instruction) {
        int f = instruction & 0x7F;
        int d = (instruction >> 7) & 0x01;

        int w = memory.getW();
        int fVal = memory.read(f);

        int result = w + fVal;

        memory.setZeroFlag((result & 0xFF) == 0);

        memory.setCarryFlag(result >= 0xFF);

        memory.setDigitatCarryFlag(((w & 0x0F) + (fVal & 0x0F)) > 0x0F);

        result = result & 0xFF;

        if (d == 0) {
            memory.setW(result);
        } else {
            memory.write(f, result);

            if (f == 0x02) {// pcl
                int pclath = memory.read(0x0A);
                int newPC = ((pclath & 0x1F) << 8) | result;
                memory.setPC(newPC);
                return;
            }
        }

        memory.incrementPC();
        memory.tickTimer0();

    }

}
