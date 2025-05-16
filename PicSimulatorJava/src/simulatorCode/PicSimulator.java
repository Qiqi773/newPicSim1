package simulatorCode;

import java.util.List;

import jCsCodeFromFirstRepo.InstructionLine;

public class PicSimulator implements SimulatorInterface {
    private final Registers registers = new Registers();
    private final DataMemory memory = new DataMemory(registers);
    private final InstructionExcutor executor = new InstructionExcutor(registers, memory);
    private DecoderWithBitShift decoder = new DecoderWithBitShift(executor);
    private List<InstructionLine> instructionLines;
    private final ProgramMemory programMemory = new ProgramMemory();

    @Override
    public int getW() {
        return registers.getW();
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }

    @Override
    public void runNextInstruction() {
        // TODO Auto-generated method stub

    }

    @Override
    public void runProgram() {
        // TODO Auto-generated method stub

    }

    @Override
    public int getPC() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getStatus() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setBreakpoint() {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearBreakpoint() {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadInstructions(List<InstructionLine> instructions) {// from GUI to back end .save the data that has
                                                                      // been read in GUI to back end
        // TODO Auto-generated method stub
        programMemory.loadProgram(instructions);

    }

    @Override
    public List<InstructionLine> getInstructionlines() {// get all instruction line objects. form back end to GUI
        // TODO Auto-generated method stub
        return instructionLines;
    }
}
