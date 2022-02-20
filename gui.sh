#!/bin/bash

WORKING_DIR=$(pwd)
java --module-path $WORKING_DIR/lib/sqlite-jdbc-3.36.0.3.jar:$WORKING_DIR/lib/javafx-sdk-17.0.2/lib \
--add-modules javafx.controls,javafx.fxml \
-Dfile.encoding=UTF-8 \
-classpath $WORKING_DIR\/news-gui/bin:$WORKING_DIR\/lib/javafx-sdk-17.0.2/lib/javafx.web.jar:$WORKING_DIR\/lib/javafx-sdk-17.0.2/lib/javafx.swing.jar:$WORKING_DIR\/lib/javafx-sdk-17.0.2/lib/javafx.media.jar:$WORKING_DIR\/lib/javafx-sdk-17.0.2/lib/javafx.graphics.jar:$WORKING_DIR\/lib/javafx-sdk-17.0.2/lib/javafx.fxml.jar:$WORKING_DIR\/lib/javafx-sdk-17.0.2/lib/javafx.controls.jar:$WORKING_DIR\/lib/javafx-sdk-17.0.2/lib/javafx.base.jar:$WORKING_DIR\/lib/javafx-sdk-17.0.2/lib/javafx-swt.jar:$WORKING_DIR\/lib/sqlite-jdbc-3.36.0.3.jar \
views.Main
