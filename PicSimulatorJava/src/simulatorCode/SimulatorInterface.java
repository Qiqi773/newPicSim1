package simulatorCode;

import jCsCodeFromFirstRepo.InstructionLine;
import java.util.List;

public interface SimulatorInterface {
    void loadInstructions(List<InstructionLine> instructions);
    List<InstructionLine> getInstructionlines();
    
    void reset();
    
    void runNextInstruction();
    
    void runProgram();
    
    int getW();
    
    int getPC();
    
    int getStatus();
    
    void setBreakpoint();
    
    void clearBreakpoint();

}
