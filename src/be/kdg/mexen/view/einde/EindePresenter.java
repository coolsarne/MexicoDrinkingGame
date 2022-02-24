package be.kdg.mexen.view.einde;

import be.kdg.mexen.model.Spel;
import be.kdg.mexen.model.Speler;
import be.kdg.mexen.view.instellingen.InstellingenPresenter;
import be.kdg.mexen.view.instellingen.InstellingenView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.swing.text.Highlighter;
import java.io.*;
import java.util.*;

/**
 * Simon Scheers
 * 26/02/2021
 */
public class EindePresenter {
    public static class Highscores {
        private String naam;
        private int score;

        public Highscores(String naam, int score) {
            this.naam = naam;
            this.score = score;
        }

        public static void compare(List<Highscores> list) {
            Collections.sort(list, new Comparator<Highscores>() {
                @Override
                public int compare(Highscores o1, Highscores o2) {
                    return o2.getScore() - o1.getScore();
                }
            });
        }

        public String getNaam() {
            return this.naam;
        }

        public int getScore() {
            return this.score;
        }
    }

    private Spel model;
    private EindeView view;

    public EindePresenter(Spel model, EindeView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        view.getBtnNieuwSpel().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String naam = model.getNaamSpeler1();

                model = new Spel();
                model.setNaamMensspeler(naam);

                InstellingenView instellingenView = new InstellingenView();
                InstellingenPresenter instellingenPresenter = new InstellingenPresenter(model, instellingenView);
                view.getScene().setRoot(instellingenView);
            }
        });
    }

    private void updateView() {
        // Log wegschrijven naar logfile.txt
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(new File("resources/logfile.txt"))
        ))) {
            dos.writeUTF(model.getLogFile().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Aantal Glazen
        String[][] aantalGlazen = new String[3][2];
        model.sorteerSpelersAantalGlazen();

        aantalGlazen[0][0] = model.getVolgordeSpelers().get(0).getNaam();
        aantalGlazen[0][1] = String.valueOf(model.getVolgordeSpelers().get(0).getAantalGlazen());

        aantalGlazen[1][0] = model.getVolgordeSpelers().get(1).getNaam();
        aantalGlazen[1][1] = String.valueOf(model.getVolgordeSpelers().get(1).getAantalGlazen());

        aantalGlazen[2][0] = model.getVolgordeSpelers().get(2).getNaam();
        aantalGlazen[2][1] = String.valueOf(model.getVolgordeSpelers().get(2).getAantalGlazen());

        view.setAantalGlazenOver(aantalGlazen);

        // Totale Score
        String[][] totaleScores = new String[3][2];
        model.sorteerTotaleScores();

        totaleScores[0][0] = model.getVolgordeSpelers().get(2).getNaam();
        totaleScores[0][1] = String.valueOf(model.getVolgordeSpelers().get(2).getTotaleScore());

        totaleScores[1][0] = model.getVolgordeSpelers().get(1).getNaam();
        totaleScores[1][1] = String.valueOf(model.getVolgordeSpelers().get(1).getTotaleScore());

        totaleScores[2][0] = model.getVolgordeSpelers().get(0).getNaam();
        totaleScores[2][1] = String.valueOf(model.getVolgordeSpelers().get(0).getTotaleScore());

        view.setTotaleScores(totaleScores);

        // Highscores updaten
        List<Highscores> list = new LinkedList<>();

        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(
                new FileInputStream(new File("resources/highscores.bin"))
        ))) {
            int teller = 0;
            while (dis.available() > 0) {
                String naam = dis.readUTF();
                int score = Integer.parseInt(dis.readUTF());
                list.add(new Highscores(naam, score));
                teller++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        list.add(new Highscores(model.getNaamSpeler1(), model.getTotaleScoreSpeler1()));
        list.add(new Highscores(model.getNaamSpeler2(), model.getTotaleScoreSpeler2()));
        list.add(new Highscores(model.getNaamSpeler3(), model.getTotaleScoreSpeler3()));

        Highscores.compare(list);


        // Highscores in bestand opslaan

        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(new File("resources/highscores.bin"))
        ))) {
            for (int i = 0; i < 5; i++) {
                dos.writeUTF(list.get(i).getNaam());
                dos.writeUTF(String.format("%d", list.get(i).getScore()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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


        // Set Podium
        String[][] podium = new String[3][2];
        model.sorteerPodium();

        podium[0][0] = model.getVolgordeSpelers().get(0).getProfielfoto();

        podium[0][1] = model.getVolgordeSpelers().get(0).getNaam();

        podium[1][0] = model.getVolgordeSpelers().get(1).getProfielfoto();

        podium[1][1] = model.getVolgordeSpelers().get(1).getNaam();

        podium[2][0] = model.getVolgordeSpelers().get(2).getProfielfoto();

        podium[2][1] = model.getVolgordeSpelers().get(2).getNaam();

        view.setPodium(podium);
    }
}
