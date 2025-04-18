package jLsCodeCurrent;

public class DataMemory {
	private int[] ram=new int[256];//0x00-0xFF
	
	public static final int ADDR_TMR0 =0x01;
	public static final int ADDR_STATUS=0x03;
	public static final int ADDR_OPTION=0x81;
	
	
	
	public void write(int address,int value) {
		ram[address]=value& 0xFF;//8
		
	}
	
	public int read(int address) {
		return ram[address];
	}
	
	

}
