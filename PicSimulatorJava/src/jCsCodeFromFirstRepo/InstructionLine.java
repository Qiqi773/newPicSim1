package jCsCodeFromFirstRepo;
//test
public class InstructionLine {

    private String address;
    private String machineCode;
    private String instruction;
    private String comment;

    public InstructionLine(String address, String machineCode, String instruction, String comment) {
        this.address = address;
        this.machineCode = machineCode;
        this.instruction = instruction;
        this.comment = comment;
    }

    public String getAddress() {
        return address;
    }

    public String getMachineCode() {
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
        return address + " | " + machineCode + " | " + instruction + " | " + comment;
    }
}
