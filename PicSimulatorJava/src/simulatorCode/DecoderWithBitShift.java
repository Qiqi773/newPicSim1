package simulatorCode;

import jCsCodeFromFirstRepo.InstructionLine;
import simulatorCode.InstructionExcutor;
import simulatorCode.ProgramMemory;

/**
 * Decodes extracted machine code to get Instruction (calls method).
 */
public class DecoderWithBitShift {

    // Get Machinecode form ProgMemory (int array size 1024)-> where to put
    // ProgMemory...(fill progmemory from FileReader List)
//    InstructionExcutor executor;

//    public DecoderWithBitShift(InstructionExcutor executor) {
//        this.executor = executor;
//
//    }

//
//		int machinecode = 0x3002;	//11 00|00 0000 0010 (EXAMPLE) => later use variable from fetch cycle !!
//		int importantBits;

    public void decodeAndExecute(int machinecode, InstructionExcutor executor) {

        int importantBits = machinecode >>> 11; // first 3 bits

        // switch case for 3 bit instructions
        switch (importantBits)

        {
        case 0b100:
            // CALL
            executor.call(machinecode);
            break;
        case 0b101:
            // GOTO
            executor.goTo(machinecode);
            break;
        }

        int importantBits1 = machinecode >>> 10; // first 4 bits

        // switch case for 4 bit instructions
        switch (importantBits1) {
        case 0b0100:
            // BCF
            executor.BCF(machinecode);
            break;
        case 0b0101:
            // BSF
            executor.BSF(machinecode);
            break;
        case 0b0110:
            // BTFSC
            executor.BTFSC(machinecode);
            break;
        case 0b0111:
            // BTFSS
            executor.BTFSS(machinecode);
            break;
        case 0b1100:
            // MOVLW
            executor.movlw(machinecode);
            break;
        case 0b1101:
            // RETLW
        	executor.retlw(machinecode);
            break;
        }

        int importantBits2 = machinecode >>> 8; // first 6 bits

        // switch case for 6 bit instructions
        switch (importantBits2) {
        case 0b000111:
            executor.addwf(machinecode);
            break;
        case 0b000101:
            // ANDWF
        	executor.andwf(machinecode);
            break;
        case 0b000001:
            int whichClr = machinecode >>> 7; // first 7 bits
            if (whichClr == 0b0000011) {
                // CLRF
                executor.clrf(machinecode);
                
            } else { // (whichClr == 0000010)
                // CLRW
                executor.clrw(machinecode);
            }
            break;
        case 0b001001:
            // COMF
        	executor.comf(machinecode);
            break;
        case 0b000011:
            // DECF
            executor.DECF(machinecode);
            break;
        case 0b001011:
            // DECFSZ
            executor.DECFSZ(machinecode);
            break;
        case 0b001010:
            // INCF
            executor.INCF(machinecode);
            break;
        case 0b001111:
            // INCFSZ
            executor.INCFSZ(machinecode);
            break;
        case 0b000100:
            // IORWF
        	executor.iorwf(machinecode);
            break;
        case 0b001000:
            executor.movf(machinecode);
            break;
        case 0b000000:
            executor.movwf(machinecode);
            break;
        case 0b001101:
            // RLF
            executor.RLF(machinecode);
            break;
        case 0b001100:
            // RRF
            executor.RRF(machinecode);
            break;
        case 0b000010:
            executor.subwf(machinecode);
            break;
        case 0b001110:
            // SWAPF
        	executor.swapf(machinecode);
            break;
        case 0b000110:
            // XORWF
        	executor.xorwf(machinecode);
            break;
        case 0b111001:
            // ANDLW
        	executor.andlw(machinecode);
            break;
        case 0b111000:
            // IORLW
        	executor.iorlw(machinecode);
            break;
        case 0b111010:
            // XORLW
        	executor.xorlw(machinecode);
            break;
        }
        int importantBits3 = machinecode >>> 9; // first 5 bits

        // switch case for 5 bit instructions
        switch (importantBits3) {
        case 0b11111:
            executor.addlw(machinecode);
            break;
        case 0b11110:
            executor.sublw(machinecode);
            break;

        }

        // switch case for whole machinecode
        switch (machinecode) {
        case 0b00000000000000:
            // NOP - has more don#t care cases, not important here, in Testfiles always: [x
            // = 0]
        	executor.nop(machinecode);
            break;
        case 0b00000001100100:
            // CLRWDT
            break;
        case 0b00000000001001:
            // RETFIE
        	executor.retfie(machinecode);
            break;
        case 0b00000000001000:
            executor.returnFromSub(); // return
            break;
        case 0b00000001100011:
            // SLEEP
            executor.sleep(machinecode);
            break;
        }

    }

}
