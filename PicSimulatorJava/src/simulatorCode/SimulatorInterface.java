package simulatorCode;

import jCsCodeFromFirstRepo.InstructionLine;
import java.util.List;

public interface SimulatorInterface {
    void loadInstructions(List<InstructionLine> instructions);

    List<InstructionLine> getInstructionlines();

    boolean isRunning();

    //boolean isHalted();

    void reset();

    void runNextInstruction();

    void step();

    void runProgram();

    int getW();

    int getPC();

    int getStatus();

    void setBreakpoint();

    void clearBreakpoint();

    int getValue(int address);

    int getTris(int port);
    
    void caHInterrupt();

	DataMemory getMemory();

}
