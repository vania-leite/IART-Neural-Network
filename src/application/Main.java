package application;

import ai.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

import ai.DataHandler;
import ai.Layer;
import ai.Network;

public class Main extends Application {
	static int NLINES = 1000;

	public int nrHLayers;
	ArrayList<Integer> nodesPerHiddenLayer;
	ArrayList<Layer> hiddenLayers;
	float learningRate;

	public GridPane graphOutput() {
		GridPane gr = new GridPane();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);
		return gr;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(
					getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}

		primaryStage.setTitle("Neural Networks");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label labelRate = new Label("Learning rate:");
		grid.add(labelRate, 0, 1);

		TextField rateText = new TextField();
		grid.add(rateText, 1, 1);

		Label labelNrHidden = new Label("Number of hidden layers:");
		grid.add(labelNrHidden, 0, 2);

		TextField nrHiddenText = new TextField();
		grid.add(nrHiddenText, 1, 2);

		Button btn = new Button("Next step...");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 3, 4);
		Button actiontarget = new Button("Start");
		nodesPerHiddenLayer = new ArrayList<Integer>();
		hiddenLayers = new ArrayList<Layer>();
		nrHLayers = 0;
		// setting action to the button
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// TODO: ADD CODE FOR DELETING THE FIRST BUTTON ("Next step...")
				if (nrHiddenText.getText().matches("[0-9]*")) {
					nrHLayers = Integer.parseInt(nrHiddenText.getText());
				}
				// TODO: ADD ALERT IF IT DOESNT MATCH THE REGEX EXPRESSION
				Label labelNrNodes;
				ArrayList<TextField> NodesText = new ArrayList<TextField>();
				for (int i = 0; i < nrHLayers; i++) {
					labelNrNodes = new Label("Number of nodes on hidden layer "
							+ (i + 1) + ":");
					grid.add(labelNrNodes, 3, i + 1);
					NodesText.add(new TextField());
					grid.add(NodesText.get(i), 4, i + 1);
				}
				// Start (add event handler on button)
				grid.add(actiontarget, 3, 6);
				// TODO make the handle receive the learning rate and number of
				// nodes per layer and be able to execute again with new values
				// ????
				actiontarget.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {

						// TODO: ADD CODE FOR DELETING THE SECOND BUTTON
						// ("Start")
						// get the values for the number of nodes per hidden
						// layer
						for (int i = 0; i < nrHLayers; i++) {
							nodesPerHiddenLayer.add(Integer.parseInt(NodesText
									.get(i).getText()));
						}
						Network testNet = new Network();
						Layer inLayer = new Layer(68, 1);
						Layer outLayer = new Layer(7, 3);
						testNet.replaceInputLayer(inLayer);
						testNet.replaceOutputLayer(outLayer);
						Layer l;
						for (int i = 0; i < nrHLayers; i++) {
							l = new Layer(nodesPerHiddenLayer.get(i), 2);
							hiddenLayers.add(l);
							testNet.addHiddenLayer(hiddenLayers.get(i));
						}
						testNet.createConnections();
						try {
							DataHandler.loadTrainingInputs();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Random rd = new Random();
						int k = 0;
						int printErrorEveryXSet = 100;
						long nSetRuns = 0;
						ArrayList<Double> targetValues;
						ArrayList<Double> inps;
						double error = 0.0;

						do {
							error = 0.0;
							int te = DataHandler.getRandomData();
							inps = DataHandler.coord.get(te).getInput();
							targetValues = DataHandler.coord.get(te).getTargetValues();


							testNet.setInputValues(inps);
							testNet.setTargetValues(targetValues);
							testNet.calculate();
							testNet.updateWeights();
							double curError = testNet.calculateError();
							DataHandler.coord.get(te).setAsUsed();
							DataHandler.coord.get(te).setError(curError);

							k++;
							if (k == NLINES) {

								k = 0;
								// calcular erro
								for (int i = 0; i < NLINES; i++) {
									error += DataHandler.coord.get(i).getError();
									DataHandler.coord.get(i).resetTemps();
								}
								error = error / (2 * NLINES);
								if (nSetRuns % printErrorEveryXSet == 0) {
									System.out.println("Error: " + error);
								}
								nSetRuns++;
							}
						} while (nSetRuns < 1200 || k != 0);
						int rdi;
						for (int j = 0; j < 3; j++) {

							rdi = rd.nextInt(NLINES);
							targetValues = DataHandler.coord.get(rdi).getTargetValues();
							inps = DataHandler.coord.get(rdi).getInput();
							testNet.setInputValues(inps);
							testNet.setTargetValues(targetValues);
							testNet.calculate();
							for (int i = 0; i <7; i++) {
								if (targetValues.get(i) == 1.0) {
									System.out.println("Neuro " + i + " should be on");
								}
							}
							for (int i = 0; i < 7; i++) {
								System.out.println("Output " + i + ": "
										+ outLayer.getNeuron(i).getOutput());
							}
						}
						Label layerColumn, labelNode1, labelLayer1, labelNode2, labelLayer2;
						Label labelError = new Label("Error: " + error);
						grid.add(labelError, 0, 8);
						labelNode1 = new Label("Connect node ");
						grid.add(labelNode1, 0, 9);
						TextField TextNode1 = new TextField();
						grid.add(TextNode1, 1, 9);
						labelLayer1 = new Label(" from layer ");
						grid.add(labelLayer1, 2, 9);
						TextField TextLayer1 = new TextField();
						grid.add(TextLayer1, 3, 9);

						labelNode2 = new Label(" To node ");
						grid.add(labelNode2, 0, 10);
						TextField TextNode2 = new TextField();
						grid.add(TextNode2, 1, 10);
						labelLayer2 = new Label("from layer ");
						grid.add(labelLayer2, 2, 10);
						TextField TextLayer2 = new TextField();
						grid.add(TextLayer2, 3, 10);

						// get output values
						Label labeloutValues = new Label("Output results:");
						grid.add(labeloutValues, 1, 11);
						Label labelResult;

						for (int i = 0; i < 4; i++) {
							labelResult = new Label(""
									+ outLayer.getNeurons().get(i).getOutput());
							grid.add(labelResult, i, 12);
						}
						Button showWeight = new Button("Show connection weight");
						grid.add(showWeight, 0, 15);

						// TODO make the handle receive the nodes and be able to
						// execute again with new values ????
						showWeight.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {
								Label labelWeight = new Label("cenas");
								grid.add(labelWeight, 0, 16);
							}

						});
					}
				});

			}
		});

		Scene scene = new Scene(grid, 800, 600);
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
