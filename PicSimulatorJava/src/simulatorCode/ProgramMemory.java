package simulatorCode;

import java.util.List;

import jCsCodeFromFirstRepo.InstructionLine;

public class ProgramMemory {
    private final int[] promemory = new int[1024];

    public void loadProgram(List<InstructionLine> instructions) {
        for (InstructionLine line : instructions) {
            int address = line.getAddress();
            int code = line.getMachineCode();
            promemory[address] = code;
        }
    }

    public int read(int address) {
        return promemory[address];

    }

}
