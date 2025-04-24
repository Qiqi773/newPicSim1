package simulatorCode;

import java.util.Stack;

public class Registers {
	private int W;
	private int pc; //0-1023
	private int pclath;
	private int[] callStack = new int[8];
	private int stackPointer; //3 bit !

	public void setW(int value) {
		this.W = value & 0xFF;// get last 8 bits 
	}

	public int getW() {
		return this.W; 
	}

	public void setPC(int value) {
		this.pc = value & 0x3FFF;// so only 0-1023 possible (see pc size)
	}

	public int getPC() {
		return this.pc;
	}

	public void incrementPC() {
		this.pc = (this.pc + 1) & 0x3FFF; //see setPC -> overflow = start at 0 again
	}

	public int getPclath() {
		return this.pclath;
	}

	public void setPclath(int value) {
		this.pclath = value & 0xFF;

	}
	
	public void incStackPointer() {
		this.stackPointer = (stackPointer + 1) & 0x7; //0x7 bcs of ONLY last 3 BITS
	}
	
	public void decStackPointer() {
		if (stackPointer != 0) {	//so we never go below 0 (wrong RETURN prevented)
			this.stackPointer--;
		}
	}

	public void writeInstack(int adr) {
		callStack[stackPointer] = adr;
		stackPointer = (stackPointer + 1) & 0x7;	//to use only last 3 bit -> overflow = back to 0
	}
	
	public int readFromStack() {
		if (stackPointer == 0) {
			stackPointer = 7;
		} else {
			stackPointer = (stackPointer - 1);
		}
		return callStack[stackPointer];
	}
	
//	public void pushReturnAddress(int returnAddress) {	
//	    callStack.push(returnAddress);
//	}
//	
//	public int popReturnAddress() {
//	    return callStack.pop();
//	    
//	}
//	
//	public boolean isCallStackEmpty() {
//	    return callStack.isEmpty();
//	}

}
