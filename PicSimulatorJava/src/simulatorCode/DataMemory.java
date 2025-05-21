package simulatorCode;

/**
 * Only RAM-Registers (p.13/14 in DataSheet).
 */
public class DataMemory {
    private int[] ram = new int[256];// Addresses: 0x00-0xFF

    private final Registers registers;

    // Addresses of seperate Registers
    public static final int ADDR_TMR0 = 0x01;
    public static final int ADDR_STATUSB0 = 0x03;
    public static final int ADDR_STATUSB1 = 0x83;
    public static final int ADDR_OPTION = 0x81;
    public static final int ADDR_INTCON = 0x0B;

    // Masks to get specific Bit of Register
    public static final int C_Mask = 0x01;
    public static final int DC_Mask = 0x02;
    public static final int Z_Mask = 0x03;

    // INTCON bit mask
    private static final int GIE_MASK = 0b10000000;
    private static final int T0IE_MASK = 0b00100000;
    private static final int T0IF_MASK = 0b00000100;

    private Port portA = new Port("A", 5);
    private Port portB = new Port("B", 8);

    public DataMemory(Registers registers) {
        this.registers = registers;
    }

    /* writes VALUE at ADDRESS(=Register */
    public void write(int address, int value) {
        switch (address) {
        case 0x05:
            portA.write(value);
            break;
        case 0x06:
            portB.write(value);
            break;
        case 0x85:
            portA.setTris(value);
            break;
        case 0x86:
            portB.setTris(value);
            break;
//        case 0x02:
//        case 0x82:

        default:
            ram[address] = value & 0xFF;// RAM has 8-bit Numbers only (1111 1111)
            break;
        }

    }

    /* reads value at ADDRESS(=Register) */
    public int read(int address) {
        switch (address) {
        case 0x05:
            return portA.read();
        case 0x06:
            return portB.read();
        case 0x85:
            return portA.getTris();
        case 0x86:
            return portB.getTris();
        case 0x02:
        case 0x82:
            return registers.getPC() & 0xFF;// PCL
        default:
            return ram[address];
        }
    }

    // --- PCL --- (Addr: 0x02 & 0x82)
    // -------------------------------------------------

    // --- STATUS --- (Addr: 0x03 & 0x83)
    // ----------------------------------------------
    public void setStatusBit(int mask) {
        int status = read(ADDR_STATUSB0); // only need to read one, bcs we made sure to always map it !!
        write(ADDR_STATUSB0, status | mask); //
        write(ADDR_STATUSB1, status | mask);
    }

    public void clearStatusBit(int mask) {
        int status = read(ADDR_STATUSB0);
        write(ADDR_STATUSB0, status & ~mask);
        write(ADDR_STATUSB1, status & ~mask);
    }

    public void setZeroFlag(boolean value) {
        if (value) {
            setStatusBit(Z_Mask);
        } else {
            clearStatusBit(Z_Mask);
        }
    }

    public void setCarryFlag(boolean value) {
        if (value) {
            setStatusBit(C_Mask);
        } else {
            clearStatusBit(C_Mask);
        }
    }

    public void setDigitatCarryFlag(boolean value) {
        if (value) {
            setStatusBit(DC_Mask);
        } else {
            clearStatusBit(DC_Mask);
        }
    }

    // --- FSR --- (Addr: 0x04 & 0x84)
    // -------------------------------------------------

    // --- PCLATH --- (Addr: 0x0A & 0x8A)
    // ----------------------------------------------

    // ---- OPTION ---(Addr: 0x81)
    public boolean isInternalClock() {// T0CS = 0
        int value = read(ADDR_OPTION);
        return (value & 0b00100000) == 0;
    }

    public boolean isPrescalerAssignedToTMR0() {// PSA = 0
        int value = read(ADDR_OPTION);
        return (value & 0b00001000) == 0;
    }

    public int getPrescalerRate() {// PS2-0
        int value = read(ADDR_OPTION);
        int ps = value & 0b00000111;
        return switch (ps) {
        case 0 -> 2;
        case 1 -> 4;
        case 2 -> 8;
        case 3 -> 16;
        case 4 -> 32;
        case 5 -> 64;
        case 6 -> 128;
        case 7 -> 256;
        default -> 1;
        };
    }

    // --- INTCON --- (Addr: 0x0B & 0x8B)
    public boolean isGlobalInterruptEnabled() {
        return (read(ADDR_INTCON) & GIE_MASK) != 0;
    }

    public boolean isTMR0InterruptEnabled() {
        return (read(ADDR_INTCON) & T0IE_MASK) != 0;
    }

    public boolean isTMR0Overflowed() {
        return (read(ADDR_INTCON) & T0IF_MASK) != 0;
    }

    public void setTMR0OverflowFlag() {// set T0IF=1
        int value = read(ADDR_INTCON);
        write(ADDR_INTCON, value | T0IF_MASK);

    }

    public void clearTMR0OverflowFlag() {//set T0IF=0
        int value = read(ADDR_INTCON);
        write(ADDR_INTCON, value & ~T0IF_MASK);
    }
    // ----------------------------------------------

    // --- General Purspose SRAM --- (Addr: 0x0C-0x4F & 0x8C-0xCF)
    // ---------------------

    // --- undefined/"empty" --- (Addr: 0x50-0x7F & 0xD0-FF)
    // ---------------------------
    // -> these can simply be set to 0 and mapped

}
