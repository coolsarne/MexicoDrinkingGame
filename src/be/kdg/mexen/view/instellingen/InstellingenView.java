package be.kdg.mexen.view.instellingen;

import be.kdg.mexen.view.menu.GameMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Simon Scheers
 * 18/02/2021
 */
public class InstellingenView extends BorderPane {
    private VBox vBoxMain;
    private Label lbVraagAantalGlazen;
    private TextField tfInputAantalglazen;
    private Label lbVraagDrank;
    private HBox hBoxDrankKeuze;
    private Label lbVraagProfielFoto;
    private HBox hBoxProfielfotoKeuze;
    private Button btnStart;
    private RadioButton rdbCola;
    private RadioButton rdbBier;
    private RadioButton rdbSterkeDrank;
    private RadioButton rdbJongen;
    private RadioButton rdbMeisje;
    private ImageView drankBier;
    private ImageView drankCola;
    private ImageView drankElixirDanvers;
    private ImageView profielfotoJongen;
    private ImageView profielfotoMeisje;
    private GameMenu gameMenu;

    public InstellingenView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        gameMenu = new GameMenu();
        vBoxMain = new VBox();
        lbVraagAantalGlazen = new Label("Aantal glazen (max. 8)");
        tfInputAantalglazen = new TextField();
        lbVraagDrank = new Label("Drank");
        hBoxDrankKeuze = new HBox();
        lbVraagProfielFoto = new Label("Profielfoto");
        hBoxProfielfotoKeuze = new HBox();
        btnStart = new Button("Start");
        rdbCola = new RadioButton();
        rdbBier = new RadioButton();
        rdbSterkeDrank = new RadioButton();
        rdbJongen = new RadioButton();
        rdbMeisje = new RadioButton();
        drankBier = new ImageView(new Image("/instellingen/drank/stella.png"));
        drankCola = new ImageView(new Image("/instellingen/drank/coke.png"));
        drankElixirDanvers = new ImageView(new Image("/instellingen/drank/elixirdanvers.png"));
        profielfotoJongen = new ImageView(new Image("/instellingen/profielfoto/jongen.png"));
        profielfotoMeisje = new ImageView(new Image("/instellingen/profielfoto/meisje.png"));
    }

    private void layoutNodes() {
        //Game Menu in top van borderpane zetten
        this.setTop(gameMenu);

        //Vbox in center van borderpane zetten
        this.setCenter(vBoxMain);

        //startbutton op bottom van borderpane plaatsen
        this.setBottom(btnStart);
        BorderPane.setAlignment(btnStart, Pos.CENTER);

        // Region
        Region vRegion1 = new Region();
        Region vRegion2 = new Region();
        VBox.setVgrow(vRegion1, Priority.ALWAYS);
        VBox.setVgrow(vRegion2, Priority.ALWAYS);

        // Alles toevoegen aan vbox
        vBoxMain.getChildren().addAll(lbVraagAantalGlazen, tfInputAantalglazen, vRegion1, lbVraagDrank, hBoxDrankKeuze, vRegion2, lbVraagProfielFoto, hBoxProfielfotoKeuze);

        // Radiobuttons toevoegen aan hbox voor drankkeuze
        ToggleGroup toggleGroupDrankKeuze = new ToggleGroup();
        hBoxDrankKeuze.getChildren().addAll(rdbCola, rdbBier, rdbSterkeDrank);

        // rdbCola
        rdbCola.setToggleGroup(toggleGroupDrankKeuze);

        // rdbBier
        rdbBier.setToggleGroup(toggleGroupDrankKeuze);

        // rdbSterkeDrank
        rdbSterkeDrank.setToggleGroup(toggleGroupDrankKeuze);

        // Radiobuttons toevoegen aan hbox voor profielfoto keuze
        ToggleGroup toggleGroupProfielfotoKeuze = new ToggleGroup();
        hBoxProfielfotoKeuze.getChildren().addAll(rdbJongen, rdbMeisje);
        rdbJongen.setToggleGroup(toggleGroupProfielfotoKeuze);
        rdbMeisje.setToggleGroup(toggleGroupProfielfotoKeuze);

        //Styling main vbox
        vBoxMain.setPadding(new Insets(20));

        //Styling textfield
        tfInputAantalglazen.setPrefSize(120, 40);
        tfInputAantalglazen.setMaxSize(120, 40);
        tfInputAantalglazen.getStyleClass().clear();
        tfInputAantalglazen.getStyleClass().add("textfield");

        //Styling drankkeuze
        hBoxDrankKeuze.setAlignment(Pos.CENTER);
        rdbCola.setGraphic(drankCola);
        rdbBier.setGraphic(drankBier);
        rdbSterkeDrank.setGraphic(drankElixirDanvers);
        HBox.setMargin(rdbCola, new Insets(0,10,0,0));
        HBox.setMargin(rdbBier, new Insets(0,10,0,10));
        HBox.setMargin(rdbSterkeDrank, new Insets(0,0,0,10));

        // rdbCola
        rdbCola.getStyleClass().remove("radio-button");
        rdbCola.getStyleClass().add("toggle-button");

        // rdbBier
        rdbBier.getStyleClass().remove("radio-button");
        rdbBier.getStyleClass().add("toggle-button");

        // rdbSterkeDrank
        rdbSterkeDrank.getStyleClass().remove("radio-button");
        rdbSterkeDrank.getStyleClass().add("toggle-button");

        //Styling profielfotos
        hBoxProfielfotoKeuze.setAlignment(Pos.CENTER);

        // rdbMeisje
        rdbMeisje.getStyleClass().remove("radio-button");
        rdbMeisje.getStyleClass().add("toggle-button");
        rdbMeisje.setGraphic(profielfotoMeisje);
        HBox.setMargin(rdbMeisje, new Insets(0,0,0,10));

        // rdbJongen
        rdbJongen.getStyleClass().remove("radio-button");
        rdbJongen.getStyleClass().add("toggle-button");
        rdbJongen.setGraphic(profielfotoJongen);
        HBox.setMargin(rdbJongen, new Insets(0,10,0,0));

        // lbVraagAantalGlazen
        lbVraagAantalGlazen.getStyleClass().clear();
        lbVraagAantalGlazen.getStyleClass().add("lbInstellingen");

        // lbVraagDrank
        lbVraagDrank.getStyleClass().clear();
        lbVraagDrank.getStyleClass().add("lbInstellingen");

        // lbVraagProfielFoto
        lbVraagProfielFoto.getStyleClass().clear();
        lbVraagProfielFoto.getStyleClass().add("lbInstellingen");

        // btnStart
        BorderPane.setMargin(btnStart, new Insets(0, 0, 20, 0));
        btnStart.setPrefSize(250, 75);
        btnStart.getStyleClass().clear();
        btnStart.getStyleClass().add("button");


        // Standaard select radion button
        rdbCola.setSelected(true);
        rdbJongen.setSelected(true);
    }

    public Button getBtnStart() {
        return btnStart;
    }

    public TextField getTfInputAantalglazen() {
        return tfInputAantalglazen;
    }

    public RadioButton getRdbCola() {
        return rdbCola;
    }

    public RadioButton getRdbBier() {
        return rdbBier;
    }

    public RadioButton getRdbSterkeDrank() {
        return rdbSterkeDrank;
    }

    public RadioButton getRdbJongen() {
        return rdbJongen;
    }

    public RadioButton getRdbMeisje() {
        return rdbMeisje;
    }

    public Label getLbVraagAantalGlazen() {
        return lbVraagAantalGlazen;
    }
}
