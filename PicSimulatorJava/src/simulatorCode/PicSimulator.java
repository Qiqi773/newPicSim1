package simulatorCode;

import java.util.List;

import gui.GUIsim;
import jCsCodeFromFirstRepo.InstructionLine;

public class PicSimulator implements SimulatorInterface {

    private final DataMemory memory = new DataMemory();
    private final InstructionExcutor executor = new InstructionExcutor(memory);
    private DecoderWithBitShift decoder = new DecoderWithBitShift();
    private List<InstructionLine> instructionLines;
    private final ProgramMemory programMemory = new ProgramMemory();
    private boolean isRunning = false;
    // private boolean halted = false;
    // private final GUIsim gui = new GUIsim();

    @Override
    public boolean isRunning() {
        // TODO Auto-generated method stub
        return false;
    }

//    @Override
//    public boolean isHalted() {
//        // TODO Auto-generated method stub
//        return halted;
//    }

    @Override
    public int getW() {
        return memory.getW();
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

        executor.resetHalted();
        isRunning = false;
        // this.halted = false;
        memory.reset();

        runProgram();

    }

    @Override
    public void runNextInstruction() {
        // TODO Auto-generated method stub

    }

    @Override
    public void step() {
        // TODO Auto-generated method stub
        if (executor.isHalted()) {
            return;
        }

        memory.checkAndHandleInterrupt();
        if (memory.interruptTriggered == true) {
            executor.resetHalted();
            return;
        }
        int pc = memory.getPC();
        int machineCode = programMemory.getInstructionAt(pc);

        decoder.decodeAndExecute(machineCode, executor);
        // memory.incrementPC();
    }

    @Override
    public void runProgram() {
        // TODO Auto-generated method stub
        // let decoder (and thus executor) run
        // after every executed code (line) check for buttons/interrupts etc

        isRunning = true;

        for (int i = 0; i < 1024 && isRunning; i++) {
            step();
        }

        isRunning = false;

    }

    @Override
    public int getPC() {
        // TODO Auto-generated method stub
        return memory.getPC();
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

    @Override
    public int getValue(int address) {
        // TODO Auto-generated method stub
        return memory.read(address);
    }

    @Override
    public int getTris(int port) {
        // TODO Auto-generated method stub
        if (port == 0)
            return memory.read(0x85);
        if (port == 1)
            return memory.read(0x86);
        return 0;
    }

}
