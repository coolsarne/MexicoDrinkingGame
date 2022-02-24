package be.kdg.mexen.view.menu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;

/**
 * Arne Cools
 * 05/03/2021
 */
public class GameMenu extends MenuBar {
    private Menu mnBestand;
    private Menu mnHelp;
    private MenuItem miAfsluiten;
    private MenuItem miAbout;
    private MenuItem miSpelregels;

    public GameMenu() {
        initialiseNodes();
        layoutNodes();
        addEventHandlers();
    }

    private void initialiseNodes() {
        mnBestand = new Menu("Bestand");
        mnHelp = new Menu("Help");
        miAfsluiten = new MenuItem("Afsluiten");
        miAbout = new MenuItem("About");
        miSpelregels = new MenuItem("Spelregels");
    }

    private void layoutNodes() {
        mnBestand.getItems().add(miAfsluiten);
        mnHelp.getItems().addAll(miSpelregels, miAbout);
        this.getMenus().addAll(mnBestand, mnHelp);
    }

    public MenuItem getMiAfsluiten() {
        return miAfsluiten;
    }

    public MenuItem getMiAbout() {
        return miAbout;
    }

    public MenuItem getMiSpelregels() {
        return miSpelregels;
    }

    private void addEventHandlers() {
        getMiAfsluiten().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        getMiAbout().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("About");
                alert.setHeaderText("MEX1");
                alert.setContentText("Dit spel is gemaakt door Arne Cools en Simon Scheers (INF105A)");
                alert.setGraphic(new ImageView(new Image("/model/dobbelstenen/5.png")));
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("/model/dobbelstenen/5.png"));
                alert.showAndWait();
            }
        });

        getMiSpelregels().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Spelregels");
                alert.setHeaderText("Mexen Spelregels");
                alert.setContentText(String.format("Het spel wordt gespeeld met twee dobbelstenen die tegelijkertijd gegooid worden. De eerste speler bepaalt hoe vaak er gegooid mag worden, met een maximum van drie keer. Wanneer de eerste spelerbijvoorbeeld na twee keer gooien tevreden is met zijn score, dan mogen de overige spelers ook maar 2 keer gooien. Het laatste aantal gegooide ogen telt, niet de hoogste score. Nadat iedereen gegooid heeft wordt er gekeken wie er het laagste aantal punten heeft. Deze persoon moet het glas ‘ad fundum’ leegdrinken. Deze persoon mag de volgende ronde beginnen met werpen.%n%nDe waarden die kunnen gegooid worden:%n1. Bij twee ongelijke cijfers telt het hoogste cijfer als tiental: een 4 en een 6 is dus 64.%n2. Twee gelijke cijfers telt als honderdtal: een 5 en nog een 5 is dus 500.%n3. Een 1 en een 2 is een mex; dat is de hoogst mogelijke waarde. Als iemand een mex heeft gegooid, dan betekent dit dat de verliezer van deze ronde een extra ‘ad fundum’ moet drinken.%n4. Alle mogelijke waarden van de ogen (links is minst, rechts is meest):%n\t31, 32, 41, 42, 43, 51, 52, 53, 54, 61, 62, 63, 64, 65, 11, 22, 33, 44, 55, 66, 21 (=mex)%n%nWanneer een speler in een beurt een 1 of een 2 gooit, mag hij deze laten liggen en met de andere dobbelsteen proberen mexte vormen. Dit geldt gewoon als een worp.Een speler mag de 1 of 2 maar éénkeer laten liggen. Een eventuele derde worp moet dus weer met twee dobbelstenen gegooid worden.%nAls er 2 spelers zijn die beide de laagste waarde hebben, dan komt er een 'play off'. Beide spelers mogen 1x gooien, wie het laagst gooit is de verliezer van de ronde."));

                alert.setGraphic(new ImageView(new Image("/model/dobbelstenen/5.png")));
                alert.getDialogPane().setPrefWidth(600);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("/model/dobbelstenen/5.png"));
                alert.showAndWait();
            }
        });
    }
}
