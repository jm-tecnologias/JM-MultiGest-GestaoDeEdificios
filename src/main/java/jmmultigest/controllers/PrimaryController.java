package jmmultigest.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import jmmultigest.App;
import jmmultigest.building.BuildingService;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException, InterruptedException {
        BuildingService er = new  BuildingService();
        App.setRoot("secondary");
    }
}
