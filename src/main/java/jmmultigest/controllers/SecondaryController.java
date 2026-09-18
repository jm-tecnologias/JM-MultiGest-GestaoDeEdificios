package jmmultigest.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import jmmultigest.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}