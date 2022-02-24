package be.kdg.mexen.view.einde;

import be.kdg.mexen.view.menu.GameMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Simon Scheers
 * 26/02/2021
 */
public class EindeView extends BorderPane {
    private VBox vBox;

    // Highscores
    private Label lblHighscoresTitel;
    private Label lbHighscores;
    private Label lblHighscores2;

    private GameMenu menu;
    private Button btnNieuwSpel;

    // Podium
    private Label lblNaamSpeler1;
    private ImageView ivSpeler1;
    private ImageView ivSpeler1Podium;

    private Label lblNaamSpeler2;
    private ImageView ivSpeler2;
    private ImageView ivSpeler2Podium;

    private Label lblNaamSpeler3;
    private ImageView ivSpeler3;
    private ImageView ivSpeler3Podium;


    // AantalGlazenOver
    private Label lblAantalGlazenOverTitel;
    private Label lblAantalGlazen;

    // Totale Scores
    private Label lblTotaleScoresTitel;
    private Label lblTotaleScores;

    public EindeView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        menu = new GameMenu();
        vBox = new VBox();
        lblHighscoresTitel = new Label("Highscores");
        lbHighscores = new Label();
        lblHighscores2 = new Label();
        btnNieuwSpel = new Button("Nieuw Spel");

        // AantalGlazenOver
        lblAantalGlazenOverTitel = new Label("Aantal Resterende Glazen");
        lblAantalGlazen = new Label();

        // Podium
        ivSpeler1 = new ImageView();
        lblNaamSpeler1 = new Label();
        ivSpeler1Podium = new ImageView(new Image("/einde/1.png"));

        ivSpeler2 = new ImageView();
        lblNaamSpeler2 = new Label();
        ivSpeler2Podium = new ImageView(new Image("/einde/2.png"));

        ivSpeler3 = new ImageView();
        lblNaamSpeler3 = new Label();
        ivSpeler3Podium = new ImageView(new Image("/einde/3.png"));

        // Totale Scores
        lblTotaleScoresTitel = new Label("Totale Scores");
        lblTotaleScores = new Label();
    }

    private void layoutNodes() {
        //Game Menu in top van borderpane zetten
        this.setTop(menu);

        //Vbox in center van borderpane zetten
        this.setCenter(vBox);
        vBox.setPadding(new Insets(20));

        //startbutton op bottom van borderpane plaatsen
        this.setBottom(btnNieuwSpel);
        BorderPane.setAlignment(btnNieuwSpel, Pos.CENTER);

        // Labels
        lblAantalGlazenOverTitel.getStyleClass().clear();
        lblAantalGlazenOverTitel.getStyleClass().add("lblHighscoresTitel");
        lblTotaleScoresTitel.getStyleClass().clear();
        lblTotaleScoresTitel.getStyleClass().add("lblHighscoresTitel");
        lblHighscoresTitel.getStyleClass().clear();
        lblHighscoresTitel.getStyleClass().add("lblHighscoresTitel");


        // Region
        Region vRegion1 = new Region();
        Region vRegion2 = new Region();
        Region vRegion3 = new Region();
        VBox.setVgrow(vRegion1, Priority.ALWAYS);
        VBox.setVgrow(vRegion2, Priority.ALWAYS);
        VBox.setVgrow(vRegion3, Priority.ALWAYS);

        // btnNieuwSpel
        BorderPane.setMargin(btnNieuwSpel, new Insets(0, 0, 20, 0));
        btnNieuwSpel.setPrefSize(250, 75);
        btnNieuwSpel.getStyleClass().clear();
        btnNieuwSpel.getStyleClass().add("button");

        HBox hbPodium = new HBox();
        VBox vbSpeler1 = new VBox();
        vbSpeler1.setAlignment(Pos.CENTER);
        VBox vbSpeler2 = new VBox();
        vbSpeler2.setAlignment(Pos.CENTER);
        VBox vbSpeler3 = new VBox();
        vbSpeler3.setAlignment(Pos.CENTER);
        vbSpeler1.getChildren().addAll(ivSpeler1Podium, lblNaamSpeler1);
        vbSpeler2.getChildren().addAll(ivSpeler2Podium, lblNaamSpeler2);
        vbSpeler3.getChildren().addAll(ivSpeler3Podium, lblNaamSpeler3);
        hbPodium.getChildren().addAll(vbSpeler1, vbSpeler2, vbSpeler3);
        hbPodium.setAlignment(Pos.CENTER);
        vbSpeler1.setPadding(new Insets(0, 15, 0, 15));
        vbSpeler2.setPadding(new Insets(0, 15, 0, 15));
        vbSpeler3.setPadding(new Insets(0, 15, 0, 15));

        ivSpeler1Podium.setFitWidth(90);
        ivSpeler1Podium.setFitHeight(90);
        ivSpeler2Podium.setFitWidth(90);
        ivSpeler2Podium.setFitHeight(90);
        ivSpeler3Podium.setFitWidth(90);
        ivSpeler3Podium.setFitHeight(90);

        this.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        vBox.getChildren().addAll(hbPodium, lblAantalGlazenOverTitel, lblAantalGlazen, lblTotaleScoresTitel, lblTotaleScores, lblHighscoresTitel, lbHighscores, lblHighscores2);
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

    public void setAantalGlazenOver(String[][] aantalGlazen) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            stringBuilder.append(String.format("%s: %s", aantalGlazen[i][0], aantalGlazen[i][1]));
            if (i < 2) {
                stringBuilder.append("  -  ");
            }
        }
        lblAantalGlazen.setText(stringBuilder.toString());
    }

    public void setTotaleScores(String[][] totaleScores) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            stringBuilder.append(String.format("%s: %s", totaleScores[i][0], totaleScores[i][1]));
            if (i < 2) {
                stringBuilder.append("  -  ");
            }
        }
        lblTotaleScores.setText(stringBuilder.toString());
    }

    public void setPodium(String[][] podium) {
        ivSpeler1.setImage(new Image(podium[0][0]));
        lblNaamSpeler1.setText(podium[0][1]);

        ivSpeler2.setImage(new Image(podium[1][0]));
        lblNaamSpeler2.setText(podium[1][1]);

        ivSpeler3.setImage(new Image(podium[2][0]));
        lblNaamSpeler3.setText(podium[2][1]);
    }

    public Button getBtnNieuwSpel() {
        return btnNieuwSpel;
    }
}

