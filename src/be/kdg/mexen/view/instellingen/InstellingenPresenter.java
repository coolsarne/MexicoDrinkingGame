package be.kdg.mexen.view.instellingen;

import be.kdg.mexen.model.Drank;
import be.kdg.mexen.model.Spel;
import be.kdg.mexen.view.spel.SpelPresenter;
import be.kdg.mexen.view.spel.SpelView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

/**
 * Simon Scheers
 * 18/02/2021
 */
public class InstellingenPresenter {
    private Spel model;
    private InstellingenView view;

    public InstellingenPresenter(Spel model, InstellingenView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        view.getBtnStart().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Keuze aantal glazen ophalen
                int aantalGlazen;
                try {
                    aantalGlazen = Integer.parseInt(view.getTfInputAantalglazen().getText());
                    if (aantalGlazen > 8) {
                        aantalGlazen = 8;
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Unable to start game:");
                    alert.setContentText("Gelieve een geldig aantal glazen in te vullen");
                    alert.showAndWait();
                    throw new NumberFormatException("Aantal glazen mag niet 0 zijn");
                }

                // Drank ophalen
                String type;
                Drank drank;
                if (view.getRdbBier().isSelected()) {
                    type = Drank.BIER.getNaam();
                    drank = Drank.BIER;
                } else if (view.getRdbSterkeDrank().isSelected()) {
                    type = Drank.STERKEDRANK.getNaam();
                    drank = Drank.STERKEDRANK;
                } else {
                    type = Drank.COLA.getNaam();
                    drank = Drank.COLA;
                }

                // Profielfoto ophalen
                String profielFoto;
                if (view.getRdbMeisje().isSelected()) {
                    profielFoto = "/instellingen/profielfoto/meisje.png";
                } else {
                    profielFoto = "/instellingen/profielfoto/jongen.png";
                }

                // File write
                try (DataOutputStream dos = new DataOutputStream(
                        new BufferedOutputStream(new FileOutputStream(new File("resources/instellingen.txt")))))
                {
                    dos.writeInt(aantalGlazen);
                    dos.writeUTF(type);
                    dos.writeUTF(profielFoto);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                model.setInstellingen(aantalGlazen, drank, profielFoto);

                // Spelers aanmaken
                model.maakSpelersAan();
                model.voegToeLogfile(model.toString());

                //Spelview weergeven
                SpelView spelview = new SpelView();
                SpelPresenter spelPresenter = new SpelPresenter(model, spelview);
                Stage spelStage = new Stage();
                spelStage.initOwner(view.getScene().getWindow());
                spelStage.initModality(Modality.NONE);
                spelStage.setScene(new Scene(spelview));
                spelStage.getIcons().add(new Image("/model/dobbelstenen/5.png"));
                spelStage.setMaximized(true);
                spelStage.setMinHeight(750);
                spelStage.setMinWidth(1100);
                spelview.getScene().getStylesheets().add(getClass().getResource("/stylesheet/style.css").toExternalForm());
                view.getScene().getWindow().hide();

                spelStage.show();

//                SpelView spelView = new SpelView();
//                SpelPresenter spelPresenter = new SpelPresenter(model, spelView);
//                view.getScene().setRoot(spelView);
//                spelView.getScene().getWindow().sizeToScene();
//                spelView.getScene().getWindow().centerOnScreen();
            }
        });
    }

    private void updateView() {
        view.getTfInputAantalglazen().setText("6");
        view.getRdbCola().setSelected(true);
        view.getRdbJongen().setSelected(true);

        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(
                new FileInputStream(new File("resources/instellingen.txt"))
        ))) {
            view.getTfInputAantalglazen().setText(String.valueOf(dis.readInt()));
            switch (dis.readUTF()) {
                case "Cola":
                    view.getRdbCola().setSelected(true);
                    break;
                case "Bier":
                    view.getRdbBier().setSelected(true);
                    break;
                case "Sterkedrank":
                    view.getRdbSterkeDrank().setSelected(true);
                    break;
            }
            switch (dis.readUTF()) {
                case "/instellingen/profielfoto/jongen.png":
                    view.getRdbJongen().setSelected(true);
                    break;
                case "/instellingen/profielfoto/meisje.png":
                    view.getRdbMeisje().setSelected(true);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
