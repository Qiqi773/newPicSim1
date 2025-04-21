package jLsCodeCurrent;

public class DataMemory {
    private int[] ram = new int[256];// 0x00-0xFF

    public static final int ADDR_TMR0 = 0x01;
    public static final int ADDR_STATUS = 0x03;
    public static final int ADDR_OPTION = 0x81;
    public static final int C_Mask = 0x01;
    public static final int DC_Mask = 0x02;
    public static final int Z_Mask = 0x04;

    public void write(int address, int value) {
        ram[address] = value & 0xFF;// 8

    }

    public int read(int address) {
        return ram[address];
    }

    public void setStatusBit(int mask) {
        int status = read(ADDR_STATUS);
        write(ADDR_STATUS, status | mask);
    }

    public void clearStatusBit(int mask) {
        int status = read(ADDR_STATUS);
        write(ADDR_STATUS, status & ~mask);
    }

    public void setZeroFlag(boolean value) {
        if (value) {
            setStatusBit(Z_Mask);
        } else {

            clearStatusBit(Z_Mask);

        }
    }
    
    public void setCarryFlag(boolean vlaue) {
        if(vlaue) {
            setStatusBit(C_Mask);
            
        }else {
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

}
