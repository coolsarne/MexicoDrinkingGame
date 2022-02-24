package be.kdg.mexen;

import be.kdg.mexen.model.Spel;
import be.kdg.mexen.view.start.StartPresenter;
import be.kdg.mexen.view.start.StartView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Simon Scheers
 * 18/02/2021
 */
public class MainMexen extends Application {
    @Override
    public void start(Stage primaryStage) {
        Spel model = new Spel();
        StartView startView = new StartView();
        StartPresenter startPresenter = new StartPresenter(model, startView);

        Scene start = new Scene(startView);
        start.getStylesheets().add(getClass().getResource("/stylesheet/style.css").toExternalForm());

        primaryStage.setScene(start);
        primaryStage.setTitle("Mexen");
        primaryStage.getIcons().add(new Image("/model/dobbelstenen/5.png"));

        primaryStage.setHeight(600);
        primaryStage.setWidth(400);

        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
