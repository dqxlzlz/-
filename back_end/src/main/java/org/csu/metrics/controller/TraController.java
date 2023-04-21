package org.csu.metrics.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.csu.metrics.domain.TrBean;
import org.csu.metrics.util.cyComplexity.antlr4.CyclomaticComplexityVisitor;
import org.csu.metrics.util.cyComplexity.grammar.JavaLexer;
import org.csu.metrics.util.cyComplexity.grammar.JavaParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TraController {

    public TableColumn c1;
    public TableColumn c2;
    public TableColumn c3;
    private MainController mainController;

    private File traFile;

    @FXML
    private Button openTrBt;

    @FXML
    private Button startTrBt;

    @FXML
    private TableView<TrBean> table;
    private ObservableList<TrBean> data = FXCollections.observableArrayList();

    public void openTrFile() {
        Stage fileStage = new Stage();
        fileStage.setWidth(400);
        fileStage.setHeight(300);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a source code doc...");
        setTraFile(fileChooser.showOpenDialog(fileStage));
    }

    public void startTr() throws IOException {
        File traFile = getTraFile();

        if (traFile != null) {
            long fileLength = traFile.length();
            LineNumberReader lnr = new LineNumberReader(new FileReader(traFile));
            lnr.skip(fileLength);
            int lines = lnr.getLineNumber();
            lnr.close();
            c1.setCellValueFactory(new PropertyValueFactory<>("loc"));
            c2.setCellValueFactory(new PropertyValueFactory<>("cp"));
            c3.setCellValueFactory(new PropertyValueFactory<>("cc"));
            table.setItems(data);

            //打印代码行数
//            loc_dis.setText(String.valueOf(lines));

            //处理平均值圈复杂度。方法的复杂度会打印在控制台
            JavaLexer lexer = new JavaLexer(new ANTLRFileStream(traFile.getAbsolutePath()));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaParser parser = new JavaParser(tokens);
            ParserRuleContext tree = parser.compilationUnit();

            CyclomaticComplexityVisitor mv = new CyclomaticComplexityVisitor();
            mv.visit(tree);
//            cc_dis.setText(String.valueOf(mv.getAvgCC()));

            //注释百分比
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(traFile), StandardCharsets.UTF_8));
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("//")) {
                    count++;
                } else if (line.startsWith("/**")) {
                    count++;
                    while (!br.readLine().startsWith("*/")) {
                        count++;
                    }
                    count++;
                }
            }
//            cp_dis.setText((((float)count / (float)lines)) * 100 + "%");
            data.add(
                    new TrBean(
                            String.valueOf(lines),
                            String.valueOf(mv.getAvgCC()),
                    String.valueOf(((float) count / (float) lines) * 100) + "%")
            );
        } else {
            System.out.println("Please select a File!!!");
        }
    }

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public File getTraFile() {
        return traFile;
    }

    public void setTraFile(File traFile) {
        this.traFile = traFile;
    }
}
