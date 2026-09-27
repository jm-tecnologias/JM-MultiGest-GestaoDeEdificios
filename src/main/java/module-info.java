module jmmultigest {
    requires javafx.controls;
    requires javafx.fxml;

    opens jmmultigest.controllers to javafx.fxml;
    exports jmmultigest;
    exports jmmultigest.utils;
    exports jmmultigest.apartments;
    exports jmmultigest.building;
    exports jmmultigest.building.address;
    exports jmmultigest.building.contact;
    exports jmmultigest.client;
    exports jmmultigest.controllers;
    exports jmmultigest.department;
    exports jmmultigest.employee;
    exports jmmultigest.historic;
    exports jmmultigest.auth;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires javafx.graphicsEmpty; 
}