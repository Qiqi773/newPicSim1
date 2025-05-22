package simulatorCode;

import java.util.List;

import jCsCodeFromFirstRepo.InstructionLine;

public class ProgramMemory {
    private final int[] progmemory = new int[1024];

    public void loadProgram(List<InstructionLine> instructions) {
        for (InstructionLine line : instructions) {
            int address = line.getAddress();
            int machinecode = line.getMachineCode();
            progmemory[address] = machinecode;
        }
    }

    // get the machine code from the current PC position
    public int getInstructionAt(int address) {
        if (address >= 0 && address < progmemory.length) {
            return progmemory[address];
        } else {
            return 0;
        }
    }

    public void reset() {
        for (int i = 0; i < progmemory.length; i++) {
            progmemory[i] = 0;
        }
    }

}
