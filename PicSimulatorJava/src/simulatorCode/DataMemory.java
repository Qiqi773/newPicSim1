package simulatorCode;

import java.util.Arrays;

/**
 * Only RAM-Registers (p.13/14 in DataSheet).
 */
public class DataMemory {
	private int[] ram = new int[256];// Addresses: 0x00-0xFF

	private int W;
	private int pc = 0; // 0-1023
	private int[] callStack = new int[8];
	private int stackPointer; // 3 bit !
	private Timer0 timer0 = new Timer0(this);
	public boolean interruptTriggered = false;

	// Addresses of seperate Registers
	public static final int ADDR_TMR0 = 0x01;
	public static final int ADDR_STATUSB0 = 0x03;
	public static final int ADDR_FSR = 0x04;
	public static final int ADDR_STATUSB1 = 0x83;
	public static final int ADDR_OPTION = 0x81;
	public static final int ADDR_INTCON = 0x0B;

	// Masks to get specific Bit of Register
	public static final int C_Mask = 0x01;
	public static final int DC_Mask = 0x02;
	public static final int Z_Mask = 0x04;

	// INTCON bit mask
	private static final int GIE_MASK = 0b10000000;
	private static final int T0IE_MASK = 0b00100000;
	private static final int T0IF_MASK = 0b00000100;
	private static final int INTE_MASK = 0b00010000;
	private static final int INTF_MASK = 0b00000010;
	private static final int RBIE_MASK = 0b00001000;
	private static final int RBIF_MASK = 0b00000001;

	private Port portA = new Port("A", 5);
	private Port portB = new Port("B", 8);
	private Port lastPortB = new Port("Last", 8);

	// --- EXTRA REGISTERS/Variables
// -----------------------------------------------------
	public Port getPortA() {
		return portA;
	}

	public Port getPortB() {
		return portB;
	}

	public int getRA() {
		return portA.getValue();
	}

	public void setRA(int value) {
		portA.setValue(value);
	}

	public int getRB() {
		return portB.getValue();
	}

	public void setRB(int value) {
		portB.setValue(value);
	}

	public Timer0 getTimer0() {
		return timer0;
	}

	public int[] getRam() {
		return this.ram;
	}

	public void reset() {
		Arrays.fill(ram, 0);
		setW(0);
		setPC(0);
		stackPointer = 0;

		timer0.reset();
		portA.reset();
		portB.reset();

	}

	public void setW(int value) {
		W = value & 0xFF;// get last 8 bits
	}

	public int getW() {
		return W;
	}

	public void setPC(int value) {
		pc = value & 0x3FFF;// so only 0-1023 possible (see pc size)
		ram[0x02] = getPC() & 0xFF; // PCL in Bank 0
		ram[0x82] = getPC() & 0xFF; // PCL in Bank 1
	}

	public int getPC() {
		return pc;
	}

	public void incrementPC() {
		pc = (pc + 1) & 0x3FFF; // see setPC -> overflow = start at 0 again
		ram[0x02] = getPC() & 0xFF; // PCL in Bank 0
		ram[0x82] = getPC() & 0xFF; // PCL in Bank 1
	}

	public int getPclath() {
		return ram[0x0A]; // made sure in both banks is same value, so we can return only one of them
	}

	public void setPclath(int value) {
		ram[0x0A] = value & 0xFF; // bank 0
		ram[0x8A] = value & 0xFF; // bank 1
	}
//-------Stack----------------------------------------------------------------

	public int getStackPointer() {
		return stackPointer;
	}

	public int[] getCallStack() {
		return callStack;
	}

	public void incStackPointer() {
		stackPointer = (stackPointer + 1) & 0x7; // 0x7 bcs of ONLY last 3 BITS
	}

	public void decStackPointer() {
		if (stackPointer != 0) { // so we never go below 0 (wrong RETURN prevented)
			stackPointer--;
		}
	}

	public void writeInstack(int adr) {
		callStack[stackPointer] = adr;
		stackPointer = (stackPointer + 1) & 0x7; // to use only last 3 bit -> overflow = back to 0
	}

	public int readFromStack() {
		if (stackPointer == 0) {
			stackPointer = 7;
		} else {
			stackPointer = (stackPointer - 1);
		}
		return callStack[stackPointer];
	}
	// ------------------------------------------------------------------------------------------------------

	/* writes VALUE at ADDRESS(=Register */
	public void write(int address, int value) {
		if (address == 0x00) {
			int fsr = ram[ADDR_FSR] & 0x7F;
			write(fsr, value);
			return;
		}
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
		case 0x02:
		case 0x82:
			setPC(value);

		default:
			ram[address] = value & 0xFF;// RAM has 8-bit Numbers only (1111 1111)
			break;
		}

	}

	/* reads value at ADDRESS(=Register) */
	public int read(int address) {
		if (address == 0x00) {
			int fsr = ram[ADDR_FSR] & 0x7F;
			return read(fsr);

		}

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
			return getPC() & 0xFF;// PCL
		default:
			return ram[address];
		}
	}

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

	public boolean isZeroFlagSet() {
		int status = read(ADDR_STATUSB0);
		return (status & Z_Mask) != 0;

	}

	public boolean isCarryFlagSet() {
		int status = read(ADDR_STATUSB0);
		return (status & C_Mask) != 0;
	}

	public int getCarryFlag() {
		return isCarryFlagSet() ? 1 : 0;
	}

	public int getZeroFlag() {
		return isZeroFlagSet() ? 1 : 0;
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

	public boolean isDCSet() {
		int status = read(ADDR_STATUSB0);
		return (status & DC_Mask) != 0;

	}

	public int getDC() {
		return isDCSet() ? 1 : 0;
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
		default -> 1; // only for short version needed, not possible to get
		};
	}

	// --- INTCON --- (Addr: 0x0B & 0x8B)
	public boolean isGlobalInterruptEnabled() {
		return (read(ADDR_INTCON) & GIE_MASK) != 0;
	}

	public void disableGlobalInterrupt() {
		int val = read(ADDR_INTCON);
		write(ADDR_INTCON, val & ~GIE_MASK);// clear GIE
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

	public void clearTMR0OverflowFlag() {// set T0IF=0
		int value = read(ADDR_INTCON);
		write(ADDR_INTCON, value & ~T0IF_MASK);
	}

	public boolean isExternalInterruptEnabled() {
		return (read(ADDR_INTCON) & INTE_MASK) != 0;
	}

	public boolean isExternalInterruptFlagSet() {// INTF=1
		return (read(ADDR_INTCON) & INTF_MASK) != 0;
	}

	public void setExternalInterruptFlag() {// set INTF=1
		int val = read(ADDR_INTCON);
		write(ADDR_INTCON, val | INTF_MASK);
	}

	public void clearExternalInterruptFlag() {// INTF=0
		int val = read(ADDR_INTCON);
		write(ADDR_INTCON, val & ~INTF_MASK);

	}

	public boolean isPortBInterruptEnabled() {
		return (read(ADDR_INTCON) & RBIE_MASK) != 0;
	}

	public boolean isPortBInterruptFlagSet() {
		return (read(ADDR_INTCON) & RBIF_MASK) != 0;
	}

	public void setPortBInterruptFlag() {
		int val = read(ADDR_INTCON);
		write(ADDR_INTCON, val | RBIF_MASK);
	}

	public void clearPortBInterruptFlag() {
		int val = read(ADDR_INTCON);
		write(ADDR_INTCON, val & ~RBIF_MASK);

	}

	public void clearAllInterruptFlags() {
		clearPortBInterruptFlag();
		clearExternalInterruptFlag();
		clearTMR0OverflowFlag();
		// .....
	}
// ----------------------------------------------

	public void tickTimer0() {
		timer0.tick();
	}

//----------------------------------------------------
	public void updateExternalInputs() {
		lastPortB.updateValue(portB.getValue());
		int current = portB.getValue();

		int bitToFlip = 4 + (int) (Math.random() * 4);
		int newValue = current ^ (1 << bitToFlip);

		portB.updateValue(newValue);
	}

// ---------------------------------------------------
	public void checkAndHandleInterrupt() {

		// Timer0 Interrupt
		if (isGlobalInterruptEnabled() && isTMR0InterruptEnabled() && isTMR0Overflowed()) {
			interruptTriggered = true;
			writeInstack(getPC());
			setPC(0x004);
			clearTMR0OverflowFlag();
			disableGlobalInterrupt();

		}

		// RB0 Interrupt
		if (isGlobalInterruptEnabled() && isExternalInterruptEnabled() && isExternalInterruptFlagSet()) {
			interruptTriggered = true;
			writeInstack(getPC());
			setPC(0x004);
			clearExternalInterruptFlag();
			disableGlobalInterrupt();

		}

		// RB4-RB7 Interrupt
		if (isGlobalInterruptEnabled() && isPortBInterruptEnabled()) {
			for (int i = 4; i <= 7; i++) {
				if (portB.isInput(i) && portB.hasChanged(lastPortB, i)) {
					interruptTriggered = true;
					setPortBInterruptFlag();
					writeInstack(getPC());
					setPC(0x004);
					clearPortBInterruptFlag();
					disableGlobalInterrupt();
					break;
				}
			}
		}

	}

	// --- General Purspose SRAM --- (Addr: 0x0C-0x4F & 0x8C-0xCF)
	// ---------------------

	// --- undefined/"empty" --- (Addr: 0x50-0x7F & 0xD0-FF)
	// ---------------------------
	// -> these can simply be set to 0 and mapped

}
