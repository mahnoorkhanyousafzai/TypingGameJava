/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package javafxController;

import Models.ParagraphModelClass;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 *
 * @author DELL
 */
public class JavaFXTest extends Application {
    private String[] languages = {"english", "french", "spanish", "german", "italian"};
    private String selectedLanguage;
    private int timerDuration;
  ParagraphModelClass p = new ParagraphModelClass();

    private Timer timer;
    private Label timerLabel;
    private Label paragraphLabel;
       private Label CheckingLabel;
    private TextField inputField;
    private Button startButton;

    private Random random; 
    @Override
    public void start(Stage primaryStage) {
  
 selectedLanguage = "english";
        timerDuration = 30;

        timer = new Timer();
        timerLabel = new Label();
        paragraphLabel = new Label();
        CheckingLabel = new Label();
 
        inputField = new TextField();
        startButton = new Button("Start");

        random = new Random();
paragraphLabel.setWrapText(true);
paragraphLabel.setFont(new Font(20));
        startButton.setOnAction(event -> startGame());
        
        

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(timerLabel, paragraphLabel,CheckingLabel, inputField, startButton);
    ScrollPane scrollPane = new ScrollPane(root);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
        Scene scene = new Scene(scrollPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Typing Test");
        primaryStage.show();
    }

    private void startGame() {
        timerLabel.setText("Time: " + timerDuration + "s");
      
        String paragraph = p.createPara(selectedLanguage.toLowerCase());
        paragraphLabel.setText(paragraph);

        inputField.setText("");
        inputField.setEditable(true);
        inputField.requestFocus();

        inputField.setOnKeyTyped(event -> checkInput());

       

        
        
        timer.schedule(new TimerTask() {
            int remainingTime = timerDuration;

            @Override
            public void run() {
                remainingTime--;
                if (remainingTime >= 0) {
                    Platform.runLater(() -> timerLabel.setText("Time: " + remainingTime + "s"));
                } else {
                    Platform.runLater(() -> {
                        timerLabel.setText("Time's up!");
                        inputField.setEditable(false);
                        inputField.setOnKeyTyped(null);
                        generateNewParagraph();
                    });
                }
            }
        }, 8, 2000);
    }

  

private void checkInput() {
    String input = inputField.getText();
    String paragraph = paragraphLabel.getText();
    int inputLength = input.length();

    FlowPane styledTextContainer = new FlowPane(Orientation.VERTICAL);
    styledTextContainer.setPrefWidth(800);
    styledTextContainer.setPrefHeight(500);
    styledTextContainer.setHgap(30); 
    styledTextContainer.setPadding(new Insets(0, 0, 0, 5));

    for (int i = 0; i < paragraph.length(); i++) {
        char c = paragraph.charAt(i);

        Text characterText = new Text(Character.toString(c));
        if (i < inputLength) {
            if (input.charAt(i) == c) {
                // Set the text color to green for correct input
                characterText.setFill(Color.GREEN);
            } else if (i < input.length()) {
                // Set the text color to red for incorrect input
                characterText.setFill(Color.RED);
            } else if (input.charAt(i) != c && input.contains(Character.toString(c))) {
                // Set the text color to orange for redundant characters
                characterText.setFill(Color.ORANGE);
            } else {
                // Set the text color to black for omitted characters
                characterText.setFill(Color.BLACK);
            }
        } else {
            // Set the text color to gray for remaining text
            characterText.setFill(Color.GRAY);
        }

        styledTextContainer.getChildren().add(characterText);
    }

    CheckingLabel.setGraphic(styledTextContainer);
}



  private void generateNewParagraph() {
    paragraphLabel.setText("Generating new paragraph...");
    timer.cancel(); // Cancel the current timer

    TimerTask generateTask = new TimerTask() {
        @Override
        public void run() {
            String newParagraph = p.createPara(selectedLanguage);
            Platform.runLater(() -> {
                CheckingLabel.setText("");
                paragraphLabel.setText(newParagraph);
                startGame(); // Start the game with the new paragraph
            });
        }
    };

    timer = new Timer(); // Create a new timer
    timer.schedule(generateTask, 3000);
}


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
