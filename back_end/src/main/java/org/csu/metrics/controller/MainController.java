package org.csu.metrics.controller;

import javafx.fxml.FXML;

public class MainController {

    @FXML
    TraController traController;

    @FXML
    CKController ckController;

    @FXML
    LKController lkController;

    @FXML
    private void init() {
        traController.injectMainController(this);
        ckController.injectMainController(this);
        lkController.injectMainController(this);
    }
}
