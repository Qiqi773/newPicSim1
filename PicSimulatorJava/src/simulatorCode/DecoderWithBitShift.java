package simulatorCode;

public class DecoderWithBitShift {

	public static void main(String[] args) {

		int machinecode = 0x3002;	//11 00|00 0000 0010 (EXAMPLE) => later use variable from fetch cycle !!
		int importantBits;

		importantBits = machinecode >>> 11; //first 3 bits
		
		//switch case for 3 bit instructions
		switch(importantBits) {
			case 0b100:
				InstructionExcutor.call(machinecode); //WHY STATIC ???
				break;
			case 0b101:
				//GOTO
				break;
		}

		importantBits = machinecode >>> 10; //first 4 bits
		
		//switch case for 4 bit instructions
		switch(importantBits) {
			case 0b0100:
				//BCF
				break;
			case 0b0101:
				//BSF
				break;
			case 0b0110:
				//BTFSC
				break;
			case 0b0111:
				//BTFSS
				break;
			case 0b1100:
				//MOVLW
				break;
			case 0b1101:
				//RETLW
				break;
		}
		
		importantBits = machinecode >>> 8; //first 6 bits
		
		//switch case for 6 bit instructions
		switch(importantBits)
		{
		case 0b000111:
			//ADDWF
			break;
		case 0b000101:
			//ANDWF
			break;
		case 0b000001:
			int whichClr = machinecode >>> 7;	//first 7 bits
			if (whichClr == 0000011) {
				//CLRF
			} else {	// (whichClr == 0000010)
				//CLRW
			}
			break;
		case 0b001001:
			//COMF
			break;
		case 0b000011:
			//DECF
			break;
		case 0b001011:
			//DECFSZ
			break;
		case 0b001010:
			//INCF
			break;
		case 0b001111:
			//INCFSZ
			break;
		case 0b000100:
			//IORWF
			break;
		case 0b001000:
			//MOVF
			break;
		case 0b000000:
			//MOVWF
			break;
		case 0b001101:
			//RLF
			break;
		case 0b001100:
			//RRF
			break;
		case 0b000010:
			//SUBWF
			break;
		case 0b001110:
			//SWAPF
			break;
		case 0b000110:
			//XORWF
			break;
		case 0b111001:
			//ANDLW
			break;
		case 0b111000:
			//IORLW
			break;
		case 0b111010:
			//XORLW
			break;
		}
		importantBits = machinecode >>> 9; //first 5 bits
		
		//switch case for 5 bit instructions
		switch(importantBits) {
			case 0b11111:
				//ADDLW
				break;
			case 0b11110:
				//SUBLW
				break;
			
		}

		//switch case for whole machinecode 
		switch(machinecode) {
			case 0b00000000000000:
				//NOP - has more don#t care cases, not important here, in Testfiles always: [x = 0]
				break;
			case 0b00000001100100:
				//CLRWDT
				break;
			case 0b00000000001001:
				//RETFIE
				break;
			case 0b00000000001000:
				//RETURN
				break;
			case 0b00000001100011:
				//SLEEP
				break;
		}
		
	}

}
