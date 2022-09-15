package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Dictionary");
		stage.setScene(new Scene(createContent(), 1366, 768));
		stage.show();
	}
	
	private Parent createContent() {
		
		
		TextField inputField = new TextField();
		inputField.setPrefWidth(250);	
		
		Text enterPrompt = new Text("Enter a word to search for: ");
		enterPrompt.setFont(new Font(16));
		
		Button submitBtn = new Button("Submit");
		submitBtn.setFont(new Font(12));
		
		HBox inputRow = new HBox();
		inputRow.setSpacing(10);
		inputRow.setPadding(new Insets(20));
		
		inputRow.getChildren().addAll( enterPrompt, inputField, submitBtn);
		
		VBox outputCol = new VBox();
		outputCol.setPadding(new Insets(20));
		Text word = new Text("");
		word.setFont(new Font(16));
		Text type = new Text("");
		type.setFont(new Font(16));
		Text phonetic = new Text("");
		phonetic.setFont(new Font(16));
		Text definition = new Text("");
		definition.setFont(new Font(16));
		Text synonyms = new Text("");
		synonyms.setFont(new Font(16));
		outputCol.getChildren().addAll(word,type,phonetic,definition, synonyms);
		
		
		VBox root = new VBox();
		
		root.getChildren().addAll(inputRow, outputCol);
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent e) {
				SearchMeaning results = new SearchMeaning(inputField.getText());
				word.setText("Word given: " + results.word);
				type.setText("Type: " +results.type);
				phonetic.setText("Phonetic: " + results.phonetic);
				definition.setText("Definitions: \n\t\t" + results.definitions);
				synonyms.setText("Synonyms: \n" + results.synonyms);
			}
			
		};
		submitBtn.setOnAction(event);
		return root;
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}
