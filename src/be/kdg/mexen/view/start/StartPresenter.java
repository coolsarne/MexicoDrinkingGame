package be.kdg.mexen.view.start;

import be.kdg.mexen.model.Spel;
import be.kdg.mexen.view.instellingen.InstellingenPresenter;
import be.kdg.mexen.view.instellingen.InstellingenView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

import java.io.*;

/**
 * Simon Scheers
 * 18/02/2021
 */
public class StartPresenter {
    private Spel model;
    private StartView view;

    public StartPresenter(Spel model, StartView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        view.btnVerder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    model.setNaamMensspeler(view.getTfNaam().getText());
                    if (view.getTfNaam().getText().isEmpty()){
                        throw new IllegalArgumentException("Naamveld mag niet leeg zijn");
                    }
                } catch (IllegalArgumentException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Unable to start game:");
                    alert.setContentText("Gelieve je naam in te vullen");
                    alert.showAndWait();
                    throw e;
                }


                InstellingenView instellingenView = new InstellingenView();
                InstellingenPresenter instellingenPresenter = new InstellingenPresenter(model, instellingenView);
                view.getScene().setRoot(instellingenView);
            }
        });

    }

    private void updateView() {

        // Highscores in bestand opslaan
/*
        String[][] highscores1 = new String[5][2];
        highscores1[0][0] = "Dirk";
        highscores1[0][1] = "3207";

        highscores1[1][0] = "Kurt";
        highscores1[1][1] = "2880";

        highscores1[2][0] = "Dirk";
        highscores1[2][1] = "1800";

        highscores1[3][0] = "Birgit";
        highscores1[3][1] = "1700";

        highscores1[4][0] = "Willem";
        highscores1[4][1] = "1000";

        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(new File("resources/highscores.bin"))
        ))) {
            for (int i = 0; i < 5; i++) {
                dos.writeUTF(highscores1[i][0]);
                dos.writeUTF(highscores1[i][1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        
        // Highscores inladen
        String[][] highscores = new String[5][2];

        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(
                new FileInputStream(new File("resources/highscores.bin"))
        ))) {
            int teller = 0;
            while (dis.available() > 0) {
                String naam = dis.readUTF();
                String score = dis.readUTF();
                highscores[teller][0] = naam;
                highscores[teller][1] = score;
                teller++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Waarde doorgeven aan de view
        view.setHighscores(highscores);
    }
}