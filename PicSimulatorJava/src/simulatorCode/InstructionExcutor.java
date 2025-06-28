package simulatorCode;

/**
 * Run the programm (=testfile). before loaded Testfile (=Programm) runs, have
 * to make a new object to run on.
 */
public class InstructionExcutor {

	private DataMemory memory;
	private static boolean isHalted = false;

	public InstructionExcutor(DataMemory memory) {

		this.memory = memory;
	}

	public void comf(int instruction) {
		int f = instruction & 0x007F;
		int d = (instruction >> 7) & 0x01;

		int value = memory.read(f);
		int result = (~value) & 0xFF;

		if (d == 0) {
			memory.setW(result);
		} else {
			memory.write(f, result);
		}

		memory.setZeroFlag(result == 0);
		memory.incrementPC();
		memory.tickTimer0();

	}

	public void xorlw(int instruction) {
		int k = instruction & 0x00FF;
		int w = memory.getW();

		int result = w ^ k;
		memory.setW(result);

		memory.setZeroFlag(result == 0);
		memory.incrementPC();
		memory.tickTimer0();

	}

	public void xorwf(int instruction) {
		int f = instruction & 0x007F;
		int d = (instruction >> 7) & 0x01;
		int w = memory.getW();

		int value = memory.read(f);
		int result = w ^ value;

		if (d == 0) {
			memory.setW(result);
		} else {
			memory.write(f, result);
		}

		memory.setZeroFlag(result == 0);
		memory.incrementPC();
		memory.tickTimer0();

	}

	public void iorlw(int instruction) {
		int k = instruction & 0x00FF;
		int w = memory.getW();

		int result = w | k;

		memory.setW(result);

		memory.setZeroFlag(result == 0);

		memory.incrementPC();
		memory.tickTimer0();

	}

	public void iorwf(int instruction) {
		int f = instruction & 0x007F;
		int d = (instruction >> 7) & 0x01;

		int w = memory.getW();
		int value = memory.read(f);

		int result = w | value;

		if (d == 0) {
			memory.setW(result);
		} else {
			memory.write(f, result);
		}

		memory.setZeroFlag(result == 0);
		memory.incrementPC();
		memory.tickTimer0();

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

	public void goTo(int instruction) { // with PCLath
		int k = instruction & 0x07FF;

		int pclath = memory.getPclath();
		int pclathHighBits = (pclath >> 3) & 0x03;// only bit 3 and bit 4

		int targetAddress = (pclathHighBits << 11) | k;
		

		memory.setPC(targetAddress);
		memory.tickTimer0();
		memory.tickTimer0();
	}

	public void call(int instruction) { // with PCLath
		//System.out.println(" call: pc before:" + memory.getPC());
		int k = instruction & 0x07FF;

		int pclath = memory.getPclath();
		int pclathHighBits = (pclath >> 3) & 0x03;// only bit 3 and bit 4

		int targetAddress = (pclathHighBits << 11) | k;

		memory.writeInstack(memory.getPC() + 1); // remember next instruction after CALL

		memory.setPC(targetAddress); // (go to) target-address

		memory.tickTimer0();
		memory.tickTimer0();

	}

	public void returnFromSub() { // no PCLath - return from SUBroutine/-program
		//System.out.println("return: jumping to " + memory.readFromStack());
		
		int returnAddress = memory.readFromStack();

		memory.setPC(returnAddress);
		memory.tickTimer0();
		memory.tickTimer0();

	}

	public void andlw(int instruction) {
		int k = instruction & 0x00FF;
		int w = memory.getW();

		int result = w & k;

		memory.setW(result);

		memory.setZeroFlag(result == 0);
		memory.incrementPC();
		memory.tickTimer0();

	}

	public void andwf(int instruction) {
		int f = instruction & 0x7F;
		int d = (instruction >> 7) & 0x01;

		int w = memory.getW();
		int fVal = memory.read(f);

		int result = w & fVal;

		if (d == 0) {
			memory.setW(result);
		} else {
			memory.write(f, result);
		}

		memory.setZeroFlag(result == 0);
		memory.incrementPC();
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
				int newPC = (((pclath & 0x1F) << 8) | result) + 1;
				memory.setPC(newPC);
				return;
			}
		}

		memory.incrementPC();
		memory.tickTimer0();

	}

	public void BSF(int instruction) {
		int f = instruction & 0x007F;
		int b = (instruction >> 7) & 0x07;

		int value = memory.read(f);

		value |= (1 << b);

		memory.write(f, value);
		memory.incrementPC();
		memory.tickTimer0();
	}

	public void BCF(int instruction) {
		int f = instruction & 0x007F;
		int b = (instruction >> 7) & 0x07;// b-decimal

		int value = memory.read(f);
		value &= ~(1 << b);
		memory.write(f, value);
		memory.incrementPC();

		memory.tickTimer0();
	}

	public void BTFSC(int instruction) {
		int f = instruction & 0x007F;
		int b = (instruction >> 7) & 0x07;

		int value = memory.read(f);
		if ((value & (1 << b)) == 0) {
			memory.setPC(memory.getPC() + 2);
			memory.tickTimer0();
		} else {
			memory.incrementPC();

		}
		memory.tickTimer0();
	}

	public void BTFSS(int instruction) {
		int f = instruction & 0x007F;
		int b = (instruction >> 7) & 0x07;

		int value = memory.read(f);
		if (((value >> b) & 1) == 1) {
			memory.setPC(memory.getPC() + 2);
			memory.tickTimer0();
		} else {
			memory.incrementPC();
		}
		memory.tickTimer0();
	}

	public void INCFSZ(int instruction) {
		int f = instruction & 0x007F;
		int d = (instruction >> 7) & 0x1;

		int value = memory.read(f);
		int result = (value + 1) & 0xFF;
		if (d == 0) {
			memory.setW(result);
		} else {
			memory.write(f, result);
		}

		if (result == 0) {
			memory.setPC(memory.getPC() + 2);
			memory.tickTimer0();
		} else {
			memory.incrementPC();
		}

		memory.tickTimer0();
	}

	public void INCF(int instruction) {
		int f = instruction & 0x007F;
		int d = (instruction >> 7) & 0x1;

		int value = memory.read(f);
		int result = (value + 1) & 0xFF;

		if (d == 0) {
			memory.setW(result);
		} else {
			memory.write(f, result);
		}

		memory.incrementPC();

		memory.tickTimer0();
	}

	public void DECFSZ(int instruction) {
		int f = instruction & 0x007F;
		int d = (instruction >> 7) & 0x1;

		int value = memory.read(f);
		int result = (value - 1) & 0xFF;

		if (d == 0) {
			memory.setW(result);
		} else {
			memory.write(f, result);
		}
		if (result == 0) {
			memory.setPC(memory.getPC() + 2);
			memory.tickTimer0();
		} else {
			memory.incrementPC();
		}

		memory.tickTimer0();
	}

	public void DECF(int instruction) {
		int f = instruction & 0x007F;
		int d = (instruction >> 7) & 0x1;

		int value = memory.read(f);
		int result = (value - 1) & 0xFF;

		if (d == 0) {
			memory.setW(result);
		} else {
			memory.write(f, result);
		}

		memory.incrementPC();

		memory.tickTimer0();

	}

	public void RLF(int instruction) {
		int f = instruction & 0x007F;
		int d = (instruction >> 7) & 0x1;

		int value = memory.read(f);
		int carryIn = memory.getCarryFlag();
		int bit7 = (value >> 7) & 1;

		int result = ((value << 1) & 0xFE) | carryIn;

		memory.setCarryFlag(bit7 == 1);

		if (d == 1) {
			memory.setW(result);
		} else {
			memory.write(f, result);
		}
		memory.incrementPC();
		memory.tickTimer0();
	}

	public void RRF(int instruction) {
		int f = instruction & 0x007F;
		int d = (instruction >> 7) & 0x1;

		int value = memory.read(f);
		int carryIn = memory.getCarryFlag();
		int bit0 = value & 0x01;

		int result = (value >> 1) | (carryIn << 7);

		memory.setCarryFlag(bit0 == 1);

		if (d == 1) {
			memory.setW(result);
		} else {
			memory.write(f, result);
		}
		memory.incrementPC();
		memory.tickTimer0();
	}

	public static boolean isHalted() {
		return isHalted;
	}

	public static void setHalted(boolean value) {
		isHalted = value;
	}

	public static void resetHalted() {
		isHalted = false;
	}

	public void sleep(int instruction) {
		int status = memory.read(DataMemory.ADDR_STATUSB0);
		status |= (1 << 4);
		status &= ~(1 << 3);
		memory.write(DataMemory.ADDR_STATUSB0, status);

		isHalted = true;
		memory.incrementPC();
		memory.tickTimer0();

	}

	public void nop(int instruction) {
		memory.incrementPC();
		memory.tickTimer0();
	}

	public void swapf(int instruction) {
		int f = instruction & 0x007F;
		int d = (instruction >> 7) & 0x01;

		int value = memory.read(f);
		int result = ((value & 0x0F) << 4) | ((value & 0x0F) >> 4);

		if (d == 0) {
			memory.setW(result);
		} else {
			memory.write(f, result);
		}

		memory.incrementPC();
		memory.tickTimer0();

	}

	public void retfie(int instruction) {
		int returnAddress = memory.readFromStack();
		memory.setPC(returnAddress);

		int intcon = memory.read(DataMemory.ADDR_INTCON);
		intcon |= 0x80;
		memory.write(DataMemory.ADDR_INTCON, intcon);

		memory.tickTimer0();
		memory.tickTimer0();

	}

	public void retlw(int instruction) {
		int k = instruction & 0x00FF;
		memory.setW(k);
		int returnAddress = memory.readFromStack();

		memory.setPC(returnAddress);
		memory.tickTimer0();
		memory.tickTimer0();
	}

}
