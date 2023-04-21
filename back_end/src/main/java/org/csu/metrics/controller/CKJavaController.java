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
import org.apache.log4j.BasicConfigurator;
import org.csu.metrics.CK;
import org.csu.metrics.CKNumber;
import org.csu.metrics.CKReport;
import org.csu.metrics.domain.CKBean;

import java.io.File;
import java.util.List;

public class CKJavaController {
    public TableColumn c1;
    public TableColumn c2;
    public TableColumn c3;
    public TableColumn c4;
    public TableColumn c5;
    public TableColumn c6;
    public TableColumn c7;
    public TableColumn c8;
    public TableColumn c9;
    private List<File> ckFile;
    private MainController mainController;

    //    @FXML
//    private TextArea wmc_dis;
//    @FXML
//    private TextArea rfc_dis;
//    @FXML
//    private TextArea cbo_dis;
//    @FXML
//    private TextArea lcom_dis;
//    @FXML
//    private TextArea dit_dis;
//    @FXML
//    private TextArea noc_dis;
    @FXML
    private TableView<CKBean> table;
    @FXML
    private Button openCKBt;
    @FXML
    private Button startCKBt;
    private ObservableList<CKBean> data = FXCollections.observableArrayList();


    public void openCKFile() {
        Stage fileStage = new Stage();
        fileStage.setWidth(400);
        fileStage.setHeight(300);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.java)", "*.java");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Select Java docs...");
        setCkFile(fileChooser.showOpenMultipleDialog(fileStage));
    }

    public void startCK() {
        BasicConfigurator.configure();

        c1.setCellValueFactory(new PropertyValueFactory<>("file"));
        c2.setCellValueFactory(new PropertyValueFactory<>("clazz"));
        c3.setCellValueFactory(new PropertyValueFactory<>("type"));
        c4.setCellValueFactory(new PropertyValueFactory<>("wmc"));
        c5.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        c6.setCellValueFactory(new PropertyValueFactory<>("lcom"));
        c7.setCellValueFactory(new PropertyValueFactory<>("cbo"));
        c8.setCellValueFactory(new PropertyValueFactory<>("dit"));
        c9.setCellValueFactory(new PropertyValueFactory<>("noc"));
        table.setItems(data);
        for (File file : ckFile) {
            CKReport report = new CK().calculate(file.getAbsolutePath());
            for (CKNumber result : report.all()) {
                if (result.isError()) continue;
                data.add(new CKBean(result.getFile(), result.getClassName(), result.getType(), String.valueOf(result.getWmc()),
                        String.valueOf(result.getRfc()), String.valueOf(result.getLcom()), String.valueOf(result.getCbo()),
                        String.valueOf(result.getDit()), String.valueOf(result.getNoc())));
            }
        }
    }

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setCkFile(List<File> ckFile) {
        this.ckFile = ckFile;
    }

    public List<File> getCkFile() {
        return this.ckFile;
    }
}
