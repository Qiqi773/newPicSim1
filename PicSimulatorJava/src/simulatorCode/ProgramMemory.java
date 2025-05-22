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

    public int read(int address) {
        return progmemory[address];

    }

}
