package jCsCodeFromFirstRepo;

//test
public class InstructionLine {

    private int address;
    private int machineCode;
    private String instruction;
    private String comment;
    private boolean breakpoint = false;

    public InstructionLine(String address, String machineCode, String instruction, String comment) {
        this.address = Integer.parseInt(address, 16);
        this.machineCode = Integer.parseInt(machineCode, 16);
        this.instruction = instruction;
        this.comment = comment;
    }

    public int getAddress() {
        return address;
    }

    public int getMachineCode() {
        return machineCode;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return (breakpoint ? "[B]" : " ") + address + " | " + machineCode + " | " + instruction + " | " + comment;
    }

    public boolean hasBreakpoint() {
        return breakpoint;
    }

    public void setBreakpoint(boolean breakpoint) {
        this.breakpoint = breakpoint;
    }
}
