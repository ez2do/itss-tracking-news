#!/bin/bash
java --module-path ./lib/sqlite-jdbc-3.36.0.3.jar:./lib/javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml -jar news.jar
