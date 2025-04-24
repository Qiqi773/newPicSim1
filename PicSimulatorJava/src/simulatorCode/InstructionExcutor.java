package simulatorCode;

public class InstructionExcutor {
    private Registers register;
    private DataMemory memory;

    public InstructionExcutor(Registers register, DataMemory memory) {
        this.register = register;
        this.memory = memory;
    }

    public void movlw(int instruction) {
        int literal = instruction & 0xFF;
        register.setW(literal);
        register.incrementPC();
    }

    public void movwf(int instruction) {
        int f = instruction & 0x007F;
        int w = register.getW();

        memory.write(f, w);
        register.incrementPC();
    }

    public void movf(int instruction) {
        int f = instruction & 0x007F; // address
        int d = (instruction >> 7) & 0x01;

        int value = memory.read(f);// value

        memory.setZeroFlag(value == 0);

        if (d == 0) {
            register.setW(value);
        } else {
            memory.write(f, value);

        }

        register.incrementPC();
    }

    public void clrw(int instruction) {
        register.setW(0);// W=0;

        memory.setZeroFlag(true);

        register.incrementPC();

    }

    public void clrf(int instruction) {
        int f = instruction & 0x007F;
        memory.write(f, 0);
        memory.setZeroFlag(true);
        register.incrementPC();

    }

    public void addwf(int instruction) {
        int f = instruction & 0x007F;
        int d = (instruction >> 7) & 0x01;
        int w = register.getW();
        int fVal = memory.read(f);

        int result = w + fVal;

    }

    public void addlw(int instruction) {
        int k = instruction & 0x00FF;
        int w = register.getW();

        int result = w + k;

        register.setW(result & 0x00FF);
        memory.setZeroFlag((result & 0xFF) == 0);

        boolean dc = ((w & 0x0F) + (k & 0x0F)) > 0x0F;
        memory.setDigitatCarryFlag(dc);

        boolean c = result > 0xFF;
        memory.setCarryFlag(c);

        register.incrementPC();
    }

    public void sublw(int instruction) {
        int k = instruction & 0x00FF;
        int w = register.getW();

        int result = (k - w) & 0xFF;
        register.setW(result);

        memory.setZeroFlag(result == 0);

        memory.setCarryFlag(k >= w);

        memory.setDigitatCarryFlag((k & 0x0F) >= (w & 0x0F));

        register.incrementPC();

    }

    public void subwf(int instruction) {
        int f = instruction & 0x007F;
        int d = (instruction >> 7) & 0x1;

        int w = register.getW();
        int fVal = memory.read(f);
        int result = (fVal - w) & 0xFF;

        if (d == 0) {
            register.setW(result);
        } else {
            memory.write(f, result);
        }

        memory.setZeroFlag(result == 0);

        memory.setCarryFlag(fVal >= w);

        memory.setDigitatCarryFlag((fVal & 0x0F) >= (w & 0x0F));

        register.incrementPC();

    }

    public void goTo(int instruction) {
        int k = instruction & 0x07FF;

        register.setPC(k);
    }

    public void call(int instruction) {
        int targetAddress = instruction & 0x07FF;

        register.writeInstack(register.getPC() + 1); //remember next instruction after CALL

        register.setPC(targetAddress);	//(go to) targetaddr

    }

    public void returnFromSub() {

        int returnAddress = register.readFromStack();
        
        register.setPC(returnAddress);
    }

}
