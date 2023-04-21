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
import org.csu.metrics.Main;
import org.csu.metrics.domain.CKBean;
import org.csu.metrics.domain.XMLElement;
import org.csu.metrics.metric.MetricCompute;
import org.csu.metrics.util.cyComplexity.visulization.ChartPaint;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class CKController {
    public TableColumn c1;
    public TableColumn c2;
    public TableColumn c3;
    public TableColumn c4;
    public TableColumn c5;
    public TableColumn c6;
    public TableColumn c7;
    public TableColumn c8;
    public TableColumn c9;
    private File ckFile;
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
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Select a XML doc...");
        setCkFile(fileChooser.showOpenDialog(fileStage));
    }

    public void startCK() {
        System.out.println("in method startCK");
        File ckFile = this.getCkFile();

        if (ckFile != null) {
            SAXReader reader = new SAXReader();
            try {
                Document doc = reader.read(ckFile);
                List<Node> classList = doc.selectNodes("//packagedElement[@type='Class'] | //packagedElement[@type='Interface']");
                List<Node> interfaceList = doc.selectNodes("//packagedElement[@type='Interface']");

                for (Node n : classList) {
                    Element e = (Element)n;
                    int dit = 0;
                    Element gene = (Element)e.element("generalization");
                    String className = e.attributeValue("name");
                    String classId = e.attributeValue("id");
                    String xpath_operation = "//packagedElement[@id='" + classId +"']/ownedOperation";
                    String xpath_general = "//generalization[@general='" + classId + "']";
                    String xpath_interface = "//packagedElement[@supplier='" + classId + "']";
                    String xpath_ass = "//ownedEnd[@type='" + classId + "']";
                    String xpath_cli = "//packagedElement[@client='" + classId +"']";
                    String xpath_sup = "//packagedElement[@supplier='" + classId +"']";

                    //WMC计算（方法复杂度默认为1）
                    int opNum = doc.selectNodes(xpath_operation).size();

                    //DIT计算
                    while (gene != null) {
                        String generalId = gene.attributeValue("general");
                        Element temp = (Element) doc.selectSingleNode("//packagedElement[@id='" + generalId + "']");
                        gene = temp.element("generalization");
                        dit++;
                    }

                    //NOC计算，如果是类，直接计算子类数，如果是接口，则还需要加上接口实现类
                    int noc = doc.selectNodes(xpath_general).size();
                    int noc_interface = doc.selectNodes(xpath_interface).size();
                    noc = noc + noc_interface;

                    //计算CBO值
                    int cbo = doc.selectNodes(xpath_ass).size() + doc.selectNodes(xpath_sup).size() + doc.selectNodes(xpath_cli).size();
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
                    data.add(new CKBean(ckFile.getName(), className, "class", String.valueOf(opNum), String.valueOf(opNum), "NULL", String.valueOf(cbo), String.valueOf(dit), String.valueOf(noc)));
//                    table.setItems(data);
//                    wmc_dis.appendText(className + " : " + opNum + "\n");
//                    rfc_dis.appendText(className + " : " + opNum + "\n");
//                    dit_dis.appendText(className + " : " + dit + "\n");
//                    noc_dis.appendText(className + " : " + noc + "\n");
//                    cbo_dis.appendText(className + " : " + cbo + "\n");
                }
                //设置LCOM值 TO-DO...
//                lcom_dis.appendText("NULL");

                System.out.println(classList.size());
                System.out.println(interfaceList.size());
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeXMLMetric() throws DocumentException {
        System.out.println("in method executeXMLMetric");
        MetricCompute metricCompute = new MetricCompute();
        List<XMLElement> classes = metricCompute.metricAnalyze(Objects.requireNonNull(Main.class.getResource("/codeToBeMetric/sample.xml")));
        ChartPaint.paint(classes, "software_metric");
    }

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setCkFile(File ckFile) { this.ckFile = ckFile; }
    public File getCkFile() { return this.ckFile; }
}
