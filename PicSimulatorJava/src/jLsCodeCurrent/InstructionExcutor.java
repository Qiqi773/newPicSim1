package jLsCodeCurrent;

public class InstructionExcutor {
	private Registers register;
	private DataMemory memory;

	public InstructionExcutor(Registers register, Datamemory memory) {
		this.register = register;
		this.memory = memory;
	}

	public void movlw(int instruction) {
		int literal = instruction & 0xFF;
		register.setW(literal);
		register.incrementPC();
	}
	
	public void movwf(int instruction) {
		int f=instruction&0x007F;
		int w=register.getW();
		
		memory.write(f, w);
		register.incrementPC();
	}
	
	public void addwf(int instruction) {
		int f=instruction&0x007F;
		int d=(instruction>>7)&0x01;
		int w=register.getW();
		int fVal =memory.read(f);
		
		int result=w+fVal;
		
		
	}

}
