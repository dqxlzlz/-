package org.csu.metrics.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class LKController {
    public TableColumn c1;
    public TableColumn c2;
    public TableColumn c3;
    public TableColumn c4;
    public TableView table;
    @FXML
    private MainController mainController;


    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void startLk(MouseEvent mouseEvent) {
    }

    public void openLkFile(MouseEvent mouseEvent) {
    }
}
