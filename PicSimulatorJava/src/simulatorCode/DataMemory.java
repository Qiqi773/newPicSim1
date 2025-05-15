package simulatorCode;

public class DataMemory {
    private int[] ram = new int[256];// Addresses: 0x00-0xFF 

    //Addresses of seperate Registers
    public static final int ADDR_TMR0 = 0x01;
    public static final int ADDR_STATUSB0 = 0x03;
    public static final int ADDR_STATUSB1 = 0x83;
    public static final int ADDR_OPTION = 0x81;
    
    //Masks to get specific Bit of Register
    public static final int C_Mask = 0x01;
    public static final int DC_Mask = 0x02;
    public static final int Z_Mask = 0x03;

    /*writes VALUE at ADDRESS(=Register*/
    public void write(int address, int value) {
        ram[address] = value & 0xFF;// RAM has 8-bit Numbers only (1111 1111)

    }
    
    /*reads value at ADDRESS(=Register)*/
    public int read(int address) {
        return ram[address];
    }

    // --- STATUS --- (Addr: 0x03 & 0x83) ----------------------------------------------
    public void setStatusBit(int mask) {
        int status = read(ADDR_STATUSB0);		//only need to read one, bcs we made sure to always map it !!
        write(ADDR_STATUSB0, status | mask);	//write in both status Registers
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
        if(value) {
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
