package be.kdg.mexen.view.spel;

import be.kdg.mexen.model.Spel;

import be.kdg.mexen.model.Speler;
import be.kdg.mexen.view.einde.EindePresenter;
import be.kdg.mexen.view.einde.EindeView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

/**
 * Simon Scheers
 * 18/02/2021
 */
public class SpelPresenter {
    private Spel model;
    private SpelView view;
    private Random random = new Random();
    private boolean tweedeWorp = false;
    private boolean derdeWorp = false;
    private boolean probeertMex = false;
    private int index = 0;

    public SpelPresenter(Spel model, SpelView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        view.getBtnNieuweRonde().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (view.getBtnNieuweRonde().getText().equalsIgnoreCase("Nieuwe Ronde")) {
                    index = 0;
                    view.getBtnNieuweRonde().setDisable(true);
                    view.setDobbelstenen(1, 1);
                    view.getLblScoreSpeler1().setText("Score: -");
                    view.getLblScoreSpeler2().setText("Score: -");
                    view.getLblScoreSpeler3().setText("Score: -");
                    // Ronde starten
                    model.startRonde();
                    model.voegToeLogfile(model.getRonde().toString(model.getAantalRondes()));

                    // Printen hoeveel glazen elke speler heeft (optioneel)
                    System.out.println(model.getVolgordeSpelers().get(0).toString() + " " + model.getVolgordeSpelers().get(1).toString() + " " + model.getVolgordeSpelers().get(2).toString() + " ");
                    model.voegToeLogfile(String.format("%s, %s en %s.%n", model.getVolgordeSpelers().get(0).toString(), model.getVolgordeSpelers().get(1).toString(), model.getVolgordeSpelers().get(2).toString()));

                    view.getBtnNieuweRonde().setText("Beëindig Ronde");
                    view.getBtnVolgendeBeurt().setDisable(false);

                    updateView();

                } else if (view.getBtnNieuweRonde().getText().equalsIgnoreCase("Beëindig Ronde")) {
                    //Nadat elke speler gegooid heeft, kijken of er een knock-out ronde gespeeld moet worden
                    model.getRonde().setKnockOutRondeSpelers(model.getVolgordeSpelers());
                    if (!model.getRonde().knockOutRondeNodig(model.getVolgordeSpelers()) || model.getRonde().getKnockOutRondeSpelers().size() == 3) {
                        // Indien er geen knock out ronde gespeeld word, direct de verloren speler berekenen en glazen drinken
                        if (model.getRonde().getKnockOutRondeSpelers().size() != 3) {
                            model.getRonde().drinkGlazenLeeg(model.getRonde().getVerlorenSpeler(model.getVolgordeSpelers()));
                            //knockout ronde wordt alleen gespeeld als 2 spelers dezelfde laagste score hebben, als het er 3 zijn is er geen verliezer van de ronde
                        }
                        updateView();
                        view.getBtnNieuweRonde().setText("Nieuwe Ronde");
                        view.getBtnNieuweRonde().setDisable(false);
                    } else {
                        view.setLblCommunicatie("Knock out ronde!");
                        view.getBtnNieuweRonde().setText("Speel Play-off");
                        view.getBtnNieuweRonde().setDisable(false);
                        view.getBtnVolgendeBeurt().setDisable(true);
                        //indien er wel knock out ronde gespeeld word
                    }

                } else if (view.getBtnNieuweRonde().getText().equalsIgnoreCase("Speel Play-off")) {
                    index = 0;
                    view.setDobbelstenen(1, 1);
                    view.getLblScoreSpeler1().setText("Score: -");
                    view.getLblScoreSpeler2().setText("Score: -");
                    view.getLblScoreSpeler3().setText("Score: -");
                    view.getBtnNieuweRonde().setDisable(true);
                    view.getBtnVolgendeBeurt().setDisable(false);
                    model.voegToeLogfile(String.format("Er wordt een knock out ronde gespeeld%n"));

                } else if (view.getBtnNieuweRonde().getText().equalsIgnoreCase("Beëindig Play-off")) {
                    // Beëindigen van de knockout ronde
                    if (model.getRonde().getKnockOutVerliezer() != null) {
                        model.getRonde().drinkGlazenLeeg(model.getRonde().getKnockOutVerliezer());
                        model.voegToeLogfile(String.format("%s heeft de knock out ronde verloren%n",model.getRonde().getKnockOutVerliezer().getNaam()));
                    }
                    updateView();
                    view.getBtnNieuweRonde().setText("Nieuwe Ronde");
                    view.getBtnNieuweRonde().setDisable(false);
                }
            }
        });

        view.getBtnVolgendeBeurt().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!view.getBtnNieuweRonde().getText().equalsIgnoreCase("Speel Play-off")) {
                    view.getBtnVolgendeBeurt().setDisable(true);
                    try {
                        beurt();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    index++;
                    if (index < 3) {
                        view.getBtnVolgendeBeurt().setDisable(false);
                    } else {
                        view.getBtnNieuweRonde().setDisable(false);
                    }
                } else {
                    view.getBtnVolgendeBeurt().setDisable(true);
                    knockOutBeurt();
                    index++;
                    if (index < 2) {
                        view.getBtnVolgendeBeurt().setDisable(false);
                    } else {
                        view.getBtnNieuweRonde().setText("Beëindig Play-off");
                        view.getBtnNieuweRonde().setDisable(false);
                    }
                }
            }
        });
    }

    private void updateView() {

        view.setProfielFotos(model.getProfielFotoSpeler1(), model.getProfielFotoSpeler2(), model.getProfielFotoSpeler3());
        view.setNamen(model.getNaamSpeler1(), model.getNaamSpeler2(), model.getNaamSpeler3());
        view.setGlazen(model.getGlazenSpeler1(), model.getAantalGlazenSpeler2(), model.getAantalGlazenSpeler3(), model.getAantalSpelGlazen());

        // Info
        view.getVbSpeler1().getStyleClass().clear();
        view.getVbSpeler1().getStyleClass().add("vbSpeler");
        view.getVbSpeler2().getStyleClass().clear();
        view.getVbSpeler2().getStyleClass().add("vbSpeler");
        view.getVbSpeler3().getStyleClass().clear();
        view.getVbSpeler3().getStyleClass().add("vbSpeler");
        try {

            if (model.getRonde().getBeurt().getSpelerAanDeBeurt() != null) {
                switch (model.getRonde().getBeurt().getSpelerAanDeBeurt().getNummer()) {
                    case 1:
                        view.getVbSpeler1().getStyleClass().add("vbActieveSpeler");
                        break;
                    case 2:
                        view.getVbSpeler2().getStyleClass().add("vbActieveSpeler");
                        break;
                    case 3:
                        view.getVbSpeler3().getStyleClass().add("vbActieveSpeler");
                        break;
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }


        try {
            if (model.getRonde().getBeurt().getWorp() != null) {
                view.setLblScoreToBeat(model.getScoreToBeat());
                view.setDobbelstenen(model.getRonde().getBeurt().getWorp().getDobbelsteen1(), model.getRonde().getBeurt().getWorp().getDobbelsteen2());
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        view.setLblRonde(model.getAantalRondes());
        view.setLblDrank(model.getDrank());


        if (model.isGedaan()) {
            // Einde weergeven
            EindeView eindeView = new EindeView();
            EindePresenter eindePresenter = new EindePresenter(model, eindeView);
            Stage eindeStage = new Stage();
            eindeStage.initOwner(view.getScene().getWindow());
            eindeStage.initModality(Modality.NONE);
            eindeStage.setScene(new Scene(eindeView));
            eindeStage.getIcons().add(new Image("/model/dobbelstenen/5.png"));
            eindeStage.setMinHeight(600);
            eindeStage.setMinWidth(400);
            eindeStage.setResizable(false);
            eindeStage.centerOnScreen();
            eindeStage.getScene().getStylesheets().add(getClass().getResource("/stylesheet/style.css").toExternalForm());
            view.getScene().getWindow().hide();
            eindeStage.setTitle("Mexen");
            eindeStage.show();
        }
    }

    public void beurt() throws InterruptedException {
        probeertMex = false;
        //BEGIN BEURT
        System.out.println("score to beat is: " + model.getRonde().getScoreToBeat());
        model.voegToeLogfile(String.format("Score to beat is: %d%n", model.getRonde().getScoreToBeat()));
        model.getRonde().nieuweBeurt(model.getVolgordeSpelers().get(index));
        model.voegToeLogfile(String.format("%s is aan de beurt%n", model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam()));
        view.getLblCommunicatie().setText(model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam() + " is aan de beurt");
        updateView();


        // Werp 2 dobbelstenen en updateView();
        model.getRonde().getBeurt().doeWorp2Dobbelstenen();
        // Animatie
        dobbelAnimatie();
        updateView();

        System.out.printf("Dobbelsteen 1: %d     Dobbelsteen 2: %d%n", model.getRonde().getBeurt().getWorp().getDobbelsteen1(), model.getRonde().getBeurt().getWorp().getDobbelsteen2());
        System.out.println("Geworpen score: " + model.getRonde().getBeurt().getScore());
        model.voegToeLogfile(String.format("Geworpen score: %d%n", model.getRonde().getBeurt().getScore()));
        view.getLblCommunicatie().setText(String.format("%s heeft %d gegooid", model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam(), model.getRonde().getBeurt().getScore()));

        if (!model.getRonde().getBeurt().beurtGedaan()) {
            if (model.getRonde().isMensSpelerAanDeBeurt()) {
                if (!model.getRonde().getBeurt().getWorp().kansOpMex()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setX(960 - 181);
                    alert.setY(360 - 226);
                    alert.setTitle("Nog Een Worp?");
                    String headerText = String.format("%s%n%s", "Geworpen Score: " + model.getRonde().getBeurt().getScore(), "Score To Beat: " + model.getRonde().getScoreToBeat());
                    alert.setHeaderText(headerText);
                    alert.setContentText("Nog een keer gooien?");
                    alert.setGraphic(new ImageView(new Image("/model/dobbelstenen/5.png")));
                    ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Ja");
                    ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Neen");


                    Optional<ButtonType> result = alert.showAndWait();
                    tweedeWorp = result.get() == ButtonType.OK;
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setX(960 - 181);
                    alert.setY(360 - 226);
                    alert.setTitle("Nog Een Worp?");
                    String headerText = String.format("%s%n%s", "Geworpen Score: " + model.getRonde().getBeurt().getScore(), "Score To Beat: " + model.getRonde().getScoreToBeat());
                    alert.setHeaderText(headerText);
                    alert.setContentText("Je hebt kans op mex! Duid aan of je wilt gooien, en met hoeveel dobbelstenen.");
                    alert.setGraphic(new ImageView(new Image("/model/dobbelstenen/5.png")));
                    ButtonType buttonTypeEen = new ButtonType("één");
                    ButtonType buttonTypeTwee = new ButtonType("twee");
                    ButtonType buttonTypeStop = new ButtonType("stop");
                    alert.getButtonTypes().setAll(buttonTypeEen, buttonTypeTwee, buttonTypeStop);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeEen) {
                        probeertMex = true;
                        tweedeWorp = true;
                    } else if (result.get() == buttonTypeTwee) {
                        probeertMex = false;
                        tweedeWorp = true;
                    } else if (result.get() == buttonTypeStop) {
                        probeertMex = false;
                        tweedeWorp = false;
                    }
                }
            } else {
                tweedeWorp = model.getRonde().getBeurt().getSpelerAanDeBeurt().nogEenWorp(model.getRonde().getBeurt().getScore(), model.getRonde().getBeurt().getScoreToBeat());
            }
        }
        if (tweedeWorp) {
            if (!model.getRonde().isMensSpelerAanDeBeurt()) {
                System.out.println(model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam() + " gooit nog een keer");
            }


            // Werp dobbelstenen en updateView();
            if (!model.getRonde().getBeurt().getWorp().kansOpMex() && !probeertMex) {
                model.getRonde().getBeurt().doeWorp2Dobbelstenen();
            } else model.getRonde().getBeurt().doeWorp1Dobbelsteen();
            dobbelAnimatie();
            updateView();

            System.out.println("Geworpen score: " + model.getRonde().getBeurt().getScore());
            view.getLblCommunicatie().setText(String.format("%s heeft %d gegooid", model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam(), model.getRonde().getBeurt().getScore()));
            model.voegToeLogfile(String.format("%s gooit nog een keer, geworpen score: %d%n", model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam(), model.getRonde().getBeurt().getScore()));
        }
        if (!model.getRonde().getBeurt().beurtGedaan() && tweedeWorp) {
            if (model.getRonde().isMensSpelerAanDeBeurt()) {
                if (!model.getRonde().getBeurt().getWorp().kansOpMex() || probeertMex) { //Speler mag maar 1x per beurt met 1 dobbelsteen gooien, als probeertMex true is, heeft hij dit bij de vorige worp geprobeerd
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setX(960 - 181);
                    alert.setY(360 - 226);
                    alert.setTitle("Nog Een Worp?");
                    String headerText = String.format("%s%n%s", "Geworpen Score: " + model.getRonde().getBeurt().getScore(), "Score To Beat: " + model.getRonde().getScoreToBeat());
                    alert.setHeaderText(headerText);
                    alert.setContentText("Nog een keer gooien?");
                    alert.setGraphic(new ImageView(new Image("/model/dobbelstenen/5.png")));
                    ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Ja");
                    ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Neen");


                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        derdeWorp = true;
                        probeertMex = false;
                    } else derdeWorp = false;
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setX(960 - 181);
                    alert.setY(360 - 226);
                    alert.setTitle("Nog Een Worp?");
                    String headerText = String.format("%s%n%s", "Geworpen Score: " + model.getRonde().getBeurt().getScore(), "Score To Beat: " + model.getRonde().getScoreToBeat());
                    alert.setHeaderText(headerText);
                    alert.setContentText("Je hebt kans op mex! Duid aan of je wilt gooien, en met hoeveel dobbelstenen.");
                    alert.setGraphic(new ImageView(new Image("/model/dobbelstenen/5.png")));
                    ButtonType buttonTypeEen = new ButtonType("één");
                    ButtonType buttonTypeTwee = new ButtonType("twee");
                    ButtonType buttonTypeStop = new ButtonType("stop");
                    alert.getButtonTypes().setAll(buttonTypeEen, buttonTypeTwee, buttonTypeStop);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeEen) {
                        probeertMex = true;
                        derdeWorp = true;
                    } else if (result.get() == buttonTypeTwee) {
                        probeertMex = false;
                        derdeWorp = true;
                    } else if (result.get() == buttonTypeStop) {
                        probeertMex = false;
                        derdeWorp = false;
                    }
                }

            } else {
                derdeWorp = model.getRonde().getBeurt().getSpelerAanDeBeurt().nogEenWorp(model.getRonde().getBeurt().getScore(), model.getRonde().getBeurt().getScoreToBeat());
            }
        }
        if (!model.getRonde().getBeurt().beurtGedaan() && derdeWorp && tweedeWorp) {
            if (!model.getRonde().isMensSpelerAanDeBeurt()) {
                System.out.println(model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam() + " gooit nog een keer");
            }

            // Werp dobbelstenen en updateView();
            if (!probeertMex) {
                model.getRonde().getBeurt().doeWorp2Dobbelstenen();
            } else model.getRonde().getBeurt().doeWorp1Dobbelsteen();
            dobbelAnimatie();
            updateView();


            System.out.println("Geworpen score: " + model.getRonde().getBeurt().getScore());
            view.getLblCommunicatie().setText(String.format("%s heeft %d gegooid", model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam(), model.getRonde().getBeurt().getScore()));
            model.voegToeLogfile(String.format("%s gooit nog een keer, geworpen score: %d%n", model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam(), model.getRonde().getBeurt().getScore()));
        }

        model.getRonde().getBeurt().scoreBijhouden(model.getVolgordeSpelers().get(index));
        model.getRonde().berekenBeurtWaarden(model.getVolgordeSpelers().get(index));

        if (model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam().equalsIgnoreCase(view.getLblSpeler1().getText())) {
            view.getLblScoreSpeler1().setText("Score: " + model.getRonde().getBeurt().getSpelerAanDeBeurt().getLaatsteScore());
        }
        if (model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam().equalsIgnoreCase(view.getLblSpeler2().getText())) {
            view.getLblScoreSpeler2().setText("Score: " + model.getRonde().getBeurt().getSpelerAanDeBeurt().getLaatsteScore());
        }
        if (model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam().equalsIgnoreCase(view.getLblSpeler3().getText())) {
            view.getLblScoreSpeler3().setText("Score: " + model.getRonde().getBeurt().getSpelerAanDeBeurt().getLaatsteScore());
        }

        updateView();
        //BEURT EINDE

    }

    public void knockOutBeurt() {
        Speler spelerAanDeBeurt = model.getRonde().getKnockOutRondeSpelers().get(index);
        model.getRonde().knockOutBeurt(spelerAanDeBeurt);
        model.getRonde().getBeurt().doeWorp2Dobbelstenen();
        dobbelAnimatie();
        updateView();
        view.getLblCommunicatie().setText(String.format("%s heeft %d gegooid", model.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam(), model.getRonde().getBeurt().getScore()));
        model.getRonde().getBeurt().scoreBijhouden(spelerAanDeBeurt);
        model.getRonde().berekenKnockOutWaarden(spelerAanDeBeurt);
    }

    public void dobbelAnimatie() {
        // Animatie

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!probeertMex) {
                    view.setDobbelstenen(random.nextInt(6) + 1, random.nextInt(6) + 1);
                } else {
                    // eerste cijfer in case staat voor positie vd dobbelsteen, tweede cijfers staat voor de waarde
                    switch (model.getRonde().getBeurt().getMexDobbelsteenVorigeWorp()) {
                        case 11:
                            view.setDobbelstenen(1, random.nextInt(6) + 1);
                            break;
                        case 12:
                            view.setDobbelstenen(2, random.nextInt(6) + 1);
                            break;
                        case 21:
                            view.setDobbelstenen((random.nextInt(6) + 1), 1);
                            break;
                        case 22:
                            view.setDobbelstenen((random.nextInt(6) + 1), 2);
                            break;
                    }
                }

            }
        }));
        timeline.setCycleCount(9);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.setDobbelstenen(model.getRonde().getBeurt().getWorp().getDobbelsteen1(), model.getRonde().getBeurt().getWorp().getDobbelsteen2());
            }
        });
        timeline.play();
    }
}


