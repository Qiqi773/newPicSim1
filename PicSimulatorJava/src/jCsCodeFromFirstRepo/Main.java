package jCsCodeFromFirstRepo;
/*
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;

import java.util.List;

public class Main extends Application {

    private TableView<InstructionLine> table = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PIC16F84 Simulator");

        // 顶部按钮
        Button loadButton = new Button("Load File");

        // 表格列
        TableColumn<InstructionLine, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<InstructionLine, String> machineCodeCol = new TableColumn<>("Machine Code");
        machineCodeCol.setCellValueFactory(new PropertyValueFactory<>("machineCode"));

        TableColumn<InstructionLine, String> instructionCol = new TableColumn<>("Instruction");
        instructionCol.setCellValueFactory(new PropertyValueFactory<>("instruction"));

        TableColumn<InstructionLine, String> commentCol = new TableColumn<>("Comment");
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));

        table.getColumns().addAll(addressCol, machineCodeCol, instructionCol, commentCol);

        // 选择文件的逻辑
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open LST File");
            File file = fileChooser.showOpenDialog(primaryStage);

            if (file != null) {
                List<InstructionLine> lines = InstructionFileReader.readInstructionFile(file.getAbsolutePath());
                table.getItems().setAll(lines);
            }
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(loadButton, table);

        BorderPane root = new BorderPane();
        root.setTop(vbox);

        Scene scene = new Scene(root, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
*/
