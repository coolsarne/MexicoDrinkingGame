package be.kdg.mexen.view.spel;

import be.kdg.mexen.view.menu.GameMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Simon Scheers
 * 18/02/2021
 */
public class SpelView extends BorderPane {
    private GameMenu gameMenu;

    private Label lblCommunicatie;
    private Button btnNieuweRonde;
    private Button btnVolgendeBeurt;

    //Stackpane voor in center + spelbord
    private StackPane stackPane;
    private ImageView ivSpelbord;

    // Dobbelstenen
    private ImageView ivDobbelsteen1;
    private ImageView ivDobbelsteen2;

    // Speler 1
    private VBox vbSpeler1;
    private ImageView ivSpeler1;
    private Label lblSpeler1;
    private Label lblScoreSpeler1;
    HBox hbGlazenSpeler1;

    // Speler 2
    private VBox vbSpeler2;
    private ImageView ivSpeler2;
    private Label lblSpeler2;
    private Label lblScoreSpeler2;
    HBox hbGlazenSpeler2;

    // Speler 3
    private VBox vbSpeler3;
    private ImageView ivSpeler3;
    private Label lblSpeler3;
    private Label lblScoreSpeler3;
    HBox hbGlazenSpeler3;

    // Info
    private Label lblRonde;
    private Label lblScoreToBeat;
    private Label lblDrank;

    public SpelView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        gameMenu = new GameMenu();
        lblCommunicatie = new Label("Welkom bij mexen!");
        btnNieuweRonde = new Button("Nieuwe Ronde");
        btnVolgendeBeurt = new Button("Volgende Beurt");

        // Stackpane (+spelbord)
        stackPane = new StackPane();
        ivSpelbord = new ImageView(new Image("model/spelbord.png"));

        // Dobbelstenen
        ivDobbelsteen1 = new ImageView();
        ivDobbelsteen2 = new ImageView();

        // Speler 1
        lblSpeler1 = new Label();
        lblScoreSpeler1 = new Label("Score: -");

        // Speler 2
        lblSpeler2 = new Label();
        lblScoreSpeler2 = new Label("Score: -");

        // Speler 3
        lblSpeler3 = new Label();
        lblScoreSpeler3 = new Label("Score: -");

        // Info
        lblRonde = new Label("Ronde: ");
        lblScoreToBeat = new Label("Score to beat: ");
        lblDrank = new Label("Drank: ");
    }

    private void layoutNodes() {
        this.setTop(gameMenu);

        HBox hbMain = new HBox();
        hbMain.setPadding(new Insets(20));

        this.setCenter(hbMain);


//        this.setBackground(new Background(
//                new BackgroundImage(
//                        new Image("/model/gamewallpaper.png"),
//                        BackgroundRepeat.REPEAT,
//                        BackgroundRepeat.REPEAT,
//                        BackgroundPosition.CENTER,
//                        BackgroundSize.DEFAULT
//                )
//        ));

        VBox vbLinks = new VBox();
        VBox vbMidden = new VBox();
        VBox vbRechts = new VBox();

        // Region
        Region hRegion1 = new Region();
        Region hRegion2 = new Region();
        HBox.setHgrow(hRegion1, Priority.ALWAYS);
        HBox.setHgrow(hRegion2, Priority.ALWAYS);

        // Region
        Region vRegion1 = new Region();
        Region vRegion2 = new Region();
        Region vRegion3 = new Region();
        Region vRegion4 = new Region();
        VBox.setVgrow(vRegion1, Priority.ALWAYS);
        VBox.setVgrow(vRegion2, Priority.ALWAYS);
        VBox.setVgrow(vRegion3, Priority.ALWAYS);
        VBox.setVgrow(vRegion4, Priority.ALWAYS);

        // Dobbelstenen
        HBox hbDobbelstenen = new HBox();
        hbDobbelstenen.getChildren().addAll(ivDobbelsteen1, ivDobbelsteen2);
        hbDobbelstenen.setSpacing(20);

        // Speler 1
        vbSpeler1 = new VBox();
        vbSpeler1.setAlignment(Pos.CENTER);
        vbSpeler1.setMinWidth(250);
        hbGlazenSpeler1 = new HBox();
        hbGlazenSpeler1.setAlignment(Pos.CENTER);
        ivSpeler1 = new ImageView();
        vbSpeler1.getChildren().addAll(ivSpeler1, lblSpeler1, lblScoreSpeler1, hbGlazenSpeler1);
        vbSpeler1.setAlignment(Pos.CENTER);
        vbSpeler1.getStyleClass().clear();
        vbSpeler1.getStyleClass().add("vbSpeler");

        // Speler 2
        vbSpeler2 = new VBox();
        vbSpeler2.setAlignment(Pos.CENTER);
        vbSpeler2.setMinWidth(250);
        hbGlazenSpeler2 = new HBox();
        hbGlazenSpeler2.setAlignment(Pos.CENTER);
        ivSpeler2 = new ImageView();
        vbSpeler2.getChildren().addAll(ivSpeler2, lblSpeler2, lblScoreSpeler2, hbGlazenSpeler2);
        vbSpeler2.getStyleClass().add("vbSpeler");

        // Speler 3
        vbSpeler3 = new VBox();
        vbSpeler3.setMinWidth(250);
        vbSpeler3.setAlignment(Pos.CENTER);
        hbGlazenSpeler3 = new HBox();
        hbGlazenSpeler3.setAlignment(Pos.CENTER);
        ivSpeler3 = new ImageView();
        vbSpeler3.getChildren().addAll(ivSpeler3, lblSpeler3, lblScoreSpeler3, hbGlazenSpeler3);
        vbSpeler3.getStyleClass().add("vbSpeler");

        lblCommunicatie.getStyleClass().clear();
        lblCommunicatie.getStyleClass().add("lblCommunicatie");
        lblCommunicatie.setPrefWidth(600);

        // vbInfo
        VBox vbInfo = new VBox();
        vbInfo.setPrefSize(250, 150);
        Label lblInfo = new Label("Info");
        lblInfo.getStyleClass().add("lbInstellingen");
        vbInfo.getChildren().addAll(lblInfo, lblRonde, lblScoreToBeat, lblDrank);
        vbInfo.setAlignment(Pos.CENTER_LEFT);
        vbInfo.setPadding(new Insets(20));

        vbInfo.getStyleClass().clear();
        vbInfo.getStyleClass().add("vbInfo");


        // vbLinks
        vbLinks.getChildren().addAll(vbSpeler1, vRegion1, vbSpeler3);

        // hbButton
        HBox hbButton = new HBox();
        hbButton.getChildren().addAll(btnVolgendeBeurt ,btnNieuweRonde);
        hbButton.setSpacing(20);
        // btnNieuweRonde
        btnNieuweRonde.setPrefSize(250, 75);
        btnNieuweRonde.getStyleClass().clear();
        btnNieuweRonde.getStyleClass().add("button");
        btnNieuweRonde.setDisable(false);

        // btnVolgendeBeurt
        btnVolgendeBeurt.setPrefSize(250,75);
        btnVolgendeBeurt.getStyleClass().clear();
        btnVolgendeBeurt.getStyleClass().add("button");
        btnVolgendeBeurt.setDisable(true);

        //stackpane
        stackPane.getChildren().addAll(ivSpelbord, hbDobbelstenen);

        // vbMidden
        vbMidden.getChildren().addAll(lblCommunicatie, vRegion2, stackPane, vRegion3, hbButton);
        VBox.setVgrow(vbMidden, Priority.ALWAYS);

        // vbRechts
        vbRechts.getChildren().addAll(vbSpeler2, vRegion4, vbInfo);

        vbMidden.setAlignment(Pos.CENTER);
        hbDobbelstenen.setAlignment(Pos.CENTER);


        hbMain.getChildren().addAll(vbLinks, hRegion1, vbMidden, hRegion2, vbRechts);
    }

    public void setNamen(String speler1, String speler2, String speler3) {
        lblSpeler1.setText(speler1);
        lblSpeler2.setText(speler2);
        lblSpeler3.setText(speler3);
    }


    // Set Info
    public void setLblRonde(int ronde) {
        lblRonde.setText("Ronde " + ronde);
    }

    public void setLblScoreToBeat(int scoreToBeat) {
        if (scoreToBeat == 2100) scoreToBeat = 21;
        lblScoreToBeat.setText("Score to beat: " + scoreToBeat);
    }

    public void setLblDrank(String drank) {
        lblDrank.setText("Drank: " + drank);
    }

    // Zet het aantal volle en lege glazen
    public void setGlazen(int glazenSpeler1, int glazenSpeler2, int glazenSpeler3, int spelGlazen) {
        // Speler 1
        hbGlazenSpeler1.getChildren().clear();
        for (int i = 0; i < glazenSpeler1; i++) {
            hbGlazenSpeler1.getChildren().add(new ImageView(new Image("/model/volglas.png")));
        }
        for (int i = 0; i < spelGlazen - glazenSpeler1; i++) {
            hbGlazenSpeler1.getChildren().add(new ImageView(new Image("/model/leegglas.png")));
        }

        // Speler 2
        hbGlazenSpeler2.getChildren().clear();
        for (int i = 0; i < glazenSpeler2; i++) {
            hbGlazenSpeler2.getChildren().add(new ImageView(new Image("/model/volglas.png")));
        }
        for (int i = 0; i < spelGlazen - glazenSpeler2; i++) {
            hbGlazenSpeler2.getChildren().add(new ImageView(new Image("/model/leegglas.png")));
        }

        // Speler 3
        hbGlazenSpeler3.getChildren().clear();
        for (int i = 0; i < glazenSpeler3; i++) {
            hbGlazenSpeler3.getChildren().add(new ImageView(new Image("/model/volglas.png")));
        }
        for (int i = 0; i < spelGlazen - glazenSpeler3; i++) {
            hbGlazenSpeler3.getChildren().add(new ImageView(new Image("/model/leegglas.png")));
        }
    }

    // Plaatst Profielfotos
    public void setProfielFotos(String pfSpeler1, String pfSpeler2, String pfSpeler3) {
        ivSpeler1.setImage(new Image(pfSpeler1));
        ivSpeler2.setImage(new Image(pfSpeler2));
        ivSpeler3.setImage(new Image(pfSpeler3));
    }

    public void setLblCommunicatie(String boodschap) {
        lblCommunicatie.setText(boodschap);
    }

    public Label getLblCommunicatie() {
        return lblCommunicatie;
    }

    public Button getBtnNieuweRonde() {
        return btnNieuweRonde;
    }

    public Button getBtnVolgendeBeurt() {
        return btnVolgendeBeurt;
    }

    public void setDobbelstenen(int dobbelsteen1, int dobbelsteen2) {
        ivDobbelsteen1.setImage(new Image(String.format("/model/dobbelstenen/%d.png", dobbelsteen1)));
        ivDobbelsteen2.setImage(new Image(String.format("/model/dobbelstenen/%d.png", dobbelsteen2)));
    }

    public VBox getVbSpeler2() {
        return vbSpeler2;
    }

    public VBox getVbSpeler1() {
        return vbSpeler1;
    }

    public VBox getVbSpeler3() {
        return vbSpeler3;
    }

    public Label getLblScoreSpeler1() {
        return lblScoreSpeler1;
    }

    public Label getLblScoreSpeler2() {
        return lblScoreSpeler2;
    }

    public Label getLblScoreSpeler3() {
        return lblScoreSpeler3;
    }

    public Label getLblSpeler1() {
        return lblSpeler1;
    }

    public Label getLblSpeler2() {
        return lblSpeler2;
    }

    public Label getLblSpeler3() {
        return lblSpeler3;
    }
}
