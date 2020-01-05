package dev.mateuszkowalczyk.ffm.view;

import dev.mateuszkowalczyk.ffm.utils.Property;
import dev.mateuszkowalczyk.ffm.utils.PropertiesLoader;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class StageController {
    private static final StageController instance = new StageController();
    private ResourceLoader resourceLoader = ResourceLoader.getInstance();
    private PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
    private Stage stage;

    private StageController() {}

    public static StageController getInstance() {
        return instance;
    }

    public void initApp(Stage stage) throws IOException {
        this.stage = stage;
        this.stage.setTitle("Friend Face Matcher");

        URL sceneUrl = null;
        if (this.propertiesLoader.get(Property.PATH_TO_DIRECTORY) != null) {
            sceneUrl = this.resourceLoader.getResource("welcomePage.fxml");
        } else {
            sceneUrl = this.resourceLoader.getResource("welcomePage.fxml");
        }
        Scene scene = new Scene(FXMLLoader.load(sceneUrl));
        this.stage.setScene(scene);
        this.stage.show();
    }

}