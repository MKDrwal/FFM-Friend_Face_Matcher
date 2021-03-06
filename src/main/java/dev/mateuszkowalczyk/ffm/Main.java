package dev.mateuszkowalczyk.ffm;

import dev.mateuszkowalczyk.ffm.view.StageController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        StageController.getInstance().initApp(stage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
