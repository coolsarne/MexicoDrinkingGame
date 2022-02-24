package be.kdg.mexen.view.start;

import be.kdg.mexen.view.menu.GameMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/*
 *
 * Mexen
 * Simon Scheers en Arne Cools
 *
 */

public class StartView extends BorderPane {
    TextField tfNaam;
    Button btnVerder;
    private Label lbMexen;
    private VBox vBox;
    private Label lbHighscores;
    private GameMenu menu;
    private Label lblHighscoresTitel;
    private Label lblHighscores2;


    public StartView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        lbMexen = new Label("Mexen");
        tfNaam = new TextField("Naam");
        btnVerder = new Button("Verder");
        vBox = new VBox();
        lbHighscores = new Label();
        lblHighscores2 = new Label();
        menu = new GameMenu();
        lblHighscoresTitel = new Label("Highscores");

    }

    private void layoutNodes() {
        //menu vanboven zetten
        this.setTop(menu);
        // vBox
        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(lbMexen, tfNaam, btnVerder, lbHighscores);
        vBox.setSpacing(10);


        VBox vbHighscores = new VBox();
        vbHighscores.setAlignment(Pos.CENTER);
        vbHighscores.getChildren().addAll(lblHighscoresTitel, lbHighscores, lblHighscores2);
        vbHighscores.setPadding(new Insets(20));

        this.setBottom(vbHighscores);

        // lbMexen
        lbMexen.setPrefSize(250, 50);
        lbMexen.getStyleClass().clear();
        lbMexen.getStyleClass().add("mexenTitel");

        // tfNaam
        tfNaam.setPrefSize(250, 50);
        tfNaam.setMaxSize(250, 50);
        tfNaam.getStyleClass().clear();
        tfNaam.getStyleClass().add("textfield");
        tfNaam.setCursor(Cursor.TEXT);

        // btnVerder
        btnVerder.setPrefSize(250, 75);
        btnVerder.getStyleClass().clear();
        btnVerder.getStyleClass().add("button");
        btnVerder.setCursor(Cursor.HAND);

        // Highscores
        // lblHighscoresTitel
        lblHighscoresTitel.getStyleClass().clear();
        lblHighscoresTitel.getStyleClass().add("lblHighscoresTitel");
    }

    public void setHighscores(String[][] highscores) {
        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            stringBuilder1.append(String.format("%s: %s", highscores[i][0], highscores[i][1]));
            if (i < 2) {
                stringBuilder1.append("  -  ");
            }
        }
        for (int i = 3; i < 5; i++) {
            stringBuilder2.append(String.format("%s: %s", highscores[i][0], highscores[i][1]));
            if (i < 4) {
                stringBuilder2.append("  -  ");
            }
        }
        lbHighscores.setText(stringBuilder1.toString());
        lblHighscores2.setText(stringBuilder2.toString());
    }

    public TextField getTfNaam() {
        return tfNaam;
    }
}
