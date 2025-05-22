package jCsCodeFromFirstRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InstructionFileReader {

    public static List<InstructionLine> readInstructionFile(String filePath) {
        List<InstructionLine> instructions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // 跳过空行
                }
                // 将一行分成四部分：地址、机器码、指令、注释
                String[] parts = line.split("\\s+", 4);

                if (parts.length < 2 || !parts[1].matches("^[0-9A-Fa-f]{4}$")) {
                    continue;
                }

                String address = parts.length > 0 ? parts[0] : "";
                String machineCode = parts.length > 1 ? parts[1] : "";
                String instruction = parts.length > 2 ? parts[2] : "";
                String comment = parts.length > 3 ? parts[3] : "";

                InstructionLine instructionLine = new InstructionLine(address, machineCode, instruction, comment);
                instructions.add(instructionLine);
            }

        } catch (IOException e) {
            System.out.println("fail:  " + e.getMessage());
        }

        return instructions;
    }

    public static void main(String[] args) {
        List<InstructionLine> test = new ArrayList<InstructionLine>();

        test = InstructionFileReader.readInstructionFile("../testfiles/TestProg_PicSim_20230413/TPicSim1.LST");

        System.out.println("Address: " + test.get(0).getAddress());

    }

}
