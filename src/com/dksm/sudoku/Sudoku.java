/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dksm.sudoku;

import com.dksm.sudoku.ctrls.CommonUtil;
import com.dksm.sudoku.models.GridPosition;
import com.dksm.sudoku.models.NumPosition;
import com.dksm.sudoku.models.ZonePosition;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 *
 * @author nelson
 */
public class Sudoku extends Application {

    private static Sudoku instance;
    public final static Map<ZonePosition, NumPosition> numPozMap = new HashMap<ZonePosition, NumPosition>();
    public final static Integer[] NUMS = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    static {
        ZonePosition zonePoz = null;
        NumPosition numPoz = null;
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 8; j++) {
                numPoz = new NumPosition(j + 1);
                zonePoz = new ZonePosition(i, j);
                numPoz.setZonePosition(zonePoz);
                numPozMap.put(zonePoz, numPoz);
            }
        }
    }
    private Stage stage;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static Sudoku getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        stage = primaryStage;
        setSence4Stage(zoneGrid());
        stage.show();
    }

    public GridPane zoneGrid() {
        GridPane gridpane = new GridPane();
        gridpane.setPrefSize(400, 300);
        // never size the gridpane larger than its preferred size:
        gridpane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        TextField numText = null;
        //generate num grid
        for (Map.Entry<ZonePosition, NumPosition> entry : numPozMap.entrySet()) {
            ZonePosition zonePosition = entry.getKey();
            NumPosition numPosition = entry.getValue();
            numText = new TextField(numPosition.getNum() + "");
            GridPosition gridPosition = CommonUtil.zonePoz2GridPoz(zonePosition);
            gridpane.add(numText, gridPosition.getCol(), gridPosition.getRow());// column row
            GridPane.setConstraints(numText, gridPosition.getCol(), gridPosition.getRow(), 1, 1,
                    HPos.CENTER,
                    VPos.CENTER,
                    Priority.SOMETIMES,
                    Priority.SOMETIMES,
                    new Insets(1));
            setTextInputAction(numText);
        }
        //add button for validate and view answer
        Button btnAn = new Button("View Answer");
        Button btnVld = new Button("Validate");
        btnAn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent ae) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        btnVld.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent ae) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        return gridpane;
    }

    private void setSence4Stage(Parent par) {
        Scene sce = stage.getScene();
        if (sce == null) {
            stage.setScene(new Scene(par, 400, 300));
        } else {
            sce.setRoot(par);
        }
        stage.setResizable(false);
        stage.sizeToScene();
    }

    private void setTextInputAction(TextField numText) {
        numText.setPromptText("Please type 1..9");
        numText.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {
//                Integer.parseInt(keyEvent.getText())
                Pattern numPtn = Pattern.compile("[1-9]");
                Matcher mtc = numPtn.matcher(keyEvent.getCharacter());
                TextField tf = ((TextField) keyEvent.getSource());
                if (mtc.matches()) {
                    tf.setText(keyEvent.getCharacter());
                } else {
//                    tf.deleteText(0, 1);
//                    tf.setText(tf.getText());
//                    tf.deleteNextChar();f
//                    tf.deletePreviousChar();
                }
            }
        });
        numText.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                ((TextField) mouseEvent.getSource()).selectAll();
            }
        });

    }
}
