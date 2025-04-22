package jLsCodeCurrent;

import java.util.Stack;

public class Registers {
	private int W;
	private int pc; //0-1023
	private int pclath;
	private Stack<Integer> callStack=new Stack<>();

	public void setW(int value) {
		this.W = value & 0xFF;// 8
	}

	public int getW() {
		return this.W;
	}

	public void setPC(int value) {
		this.pc = value & 0x3FFF;
	}

	public int getPC() {
		return this.pc;
	}

	public void incrementPC() {
		this.pc = (this.pc + 1) & 0x3FFF;
	}

	public int getPclath() {
		return this.pclath;
	}

	public void setPclath(int value) {
		this.pclath = value & 0xFF;

	}
	
	public void pushReturnAddress(int returnAddress) {
	    callStack.push(returnAddress);
	}
	
	public int popReturnAddress() {
	    return callStack.pop();
	    
	}
	
	public boolean isCallStackEmpty() {
	    return callStack.isEmpty();
	}

}
