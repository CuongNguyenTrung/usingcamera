package main;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class CamTest extends Application {

    private ComboBox<String> cbo = new ComboBox<>();
    private Webcam webcam = Webcam.getDefault();
    private Button button = new Button("Capture");
    void initCombobox() {
        ObservableList<String> list = FXCollections.observableArrayList("Cam 1", "Cam 2", "Cam 3");
        cbo.getItems().addAll(list);
        cbo.getSelectionModel().select(0);
        cbo.setOnAction(e -> {
            try {
                usingCam(cbo.getValue());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }


    public void initButtonCapture() {
        button.setOnAction(e -> {
            try {
                System.out.println("wat??");
                captureImage();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        // init combobox: setString, setAction
        initCombobox();
        // init button
        initButtonCapture();

        BorderPane borderPane = new BorderPane();
        // Set top
        HBox topPane = new HBox(10);
        topPane.getChildren().addAll(cbo, button);
        borderPane.setTop(topPane);

        // Set center
        SwingNode swingNode = new SwingNode();
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        SwingUtilities.invokeLater(() -> {
            swingNode.setContent(webcamPanel);
        });
        borderPane.setCenter(swingNode);

        Scene scene = new Scene(borderPane, 400, 400);
        primaryStage.setTitle("Camera Test");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Create event close cam
        primaryStage.setOnCloseRequest(e -> {
            webcam.close();
        });
    }

    // Using cam
    public void usingCam(String name) throws IOException {
        webcam.open();
    }

    // Capture image
    public void captureImage() throws IOException {
        ImageIO.write(webcam.getImage(), "PNG", new File("hello-world.png"));
    }



    public static void main(String[] args) {
        launch(args);
    }
}
