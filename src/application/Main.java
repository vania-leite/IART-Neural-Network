package application;

import ai.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
	public static int NLINES,outNodes,inNodes,nrEpoch;
	String chosenFile;
	public int nrHLayers;
	public double lrate,momentum;
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
		
		Label labelmomentum = new Label("Momentum:");
		grid.add(labelmomentum, 0, 3);
		TextField textMomentum = new TextField();
		grid.add(textMomentum, 1, 3);
		
		Label labelNlines = new Label("Number of lines:");
		grid.add(labelNlines, 0, 4);
		TextField textNLines = new TextField();
		grid.add(textNLines, 1, 4);
		
		Label labelFile = new Label("Text file:");
		grid.add(labelFile, 0, 5);
		TextField textFile = new TextField();
		grid.add(textFile, 1, 5);
		
		Label labelInpt = new Label("Number of input nodes:");
		grid.add(labelInpt, 0, 6);
		TextField textInpt = new TextField();
		grid.add(textInpt, 1, 6);
		
		Label labelOut = new Label("Number of output nodes:");
		grid.add(labelOut, 0, 7);
		TextField textOut = new TextField();
		grid.add(textOut, 1, 7);
		
		Label labelEPOCH = new Label("EPOCHS:");
		grid.add(labelEPOCH, 0,8);
		TextField textEPOCH = new TextField();
		grid.add(textEPOCH, 1, 8);
				
		

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
				NLINES=Integer.parseInt(textNLines.getText());
				chosenFile=textFile.getText();
				if (nrHiddenText.getText().matches("[0-9]*")) {
					nrHLayers = Integer.parseInt(nrHiddenText.getText());
				}
				lrate=Double.parseDouble(rateText.getText());
				momentum=Double.parseDouble(textMomentum.getText());
				inNodes=Integer.parseInt(textInpt.getText());
				outNodes=Integer.parseInt(textOut.getText());
				nrEpoch=Integer.parseInt(textEPOCH.getText());
				grid.getChildren().remove(hbBtn);
				grid.getChildren().remove(labelRate);
				grid.getChildren().remove(rateText);
				grid.getChildren().remove(labelNrHidden);
				grid.getChildren().remove(nrHiddenText);
				grid.getChildren().remove(labelmomentum);
				grid.getChildren().remove(textMomentum);
				grid.getChildren().remove(labelNlines);
				grid.getChildren().remove(textNLines);
				grid.getChildren().remove(labelFile);
				grid.getChildren().remove(textFile);
				grid.getChildren().remove(labelInpt);
				grid.getChildren().remove(textInpt);
				grid.getChildren().remove(labelOut);
				grid.getChildren().remove(textOut);
				grid.getChildren().remove(labelEPOCH);
				grid.getChildren().remove(textEPOCH);
				
				// TODO: ADD ALERT IF IT DOESNT MATCH THE REGEX EXPRESSION and review other inputs
				
				
				ArrayList<TextField> NodesText = new ArrayList<TextField>();
				ArrayList<Label> NodesLabel=new ArrayList<Label>();
				for (int i = 0; i < nrHLayers; i++) {
					NodesLabel.add(new Label("Nodes on hidden layer "
							+ (i + 1) + ":"));
					grid.add(NodesLabel.get(i), 3, i + 1);
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
						
						
						// get the values for the number of nodes per hidden
						// layer
						for (int i = 0; i < nrHLayers; i++) {
							nodesPerHiddenLayer.add(Integer.parseInt(NodesText
									.get(i).getText()));
							grid.getChildren().remove(NodesLabel.get(i));
							grid.getChildren().remove(NodesText.get(i));
							
						}
						grid.getChildren().remove(actiontarget);
						
						Network testNet = new Network();
						testNet.setETA(lrate);
						testNet.setM(momentum);
						DataHandler.setFile(chosenFile);
						Layer inLayer = new Layer(inNodes, 1);
						Layer outLayer = new Layer(outNodes, 3);
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
							DataHandler.loadTrainingInputs(1000);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Random rd = new Random();
						int k = 0;
						int printErrorEveryXSet = nrEpoch/10;
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
						} while (nSetRuns < nrEpoch || k != 0);
						int rdi;
						for (int j = 0; j < 3; j++) {

							rdi = rd.nextInt(NLINES);
							targetValues = DataHandler.coord.get(rdi).getTargetValues();
							inps = DataHandler.coord.get(rdi).getInput();
							testNet.setInputValues(inps);
							testNet.setTargetValues(targetValues);
							testNet.calculate();
							for (int i = 0; i <outNodes; i++) {
								if (targetValues.get(i) == 1.0) {
									System.out.println("Neuro " + i + " should be on");
								}
							}
							for (int i = 0; i < outNodes; i++) {
								System.out.println("Output " + i + ": "
										+ outLayer.getNeuron(i).getOutput());
							}
						}
						Label labelNode1, labelLayer1, labelNode2, labelLayer2;
						Label labelError = new Label("Error: " + error);
						grid.add(labelError, 0, 4);
						labelNode1 = new Label("Connect node ");
						grid.add(labelNode1, 0, 5);
						TextField TextNode1 = new TextField();
						grid.add(TextNode1, 1, 5);
						labelLayer1 = new Label(" from layer ");
						grid.add(labelLayer1, 2, 5);
						TextField TextLayer1 = new TextField();
						grid.add(TextLayer1, 3, 5);

						labelNode2 = new Label(" To node ");
						grid.add(labelNode2, 0, 6);
						TextField TextNode2 = new TextField();
						grid.add(TextNode2, 1, 6);
						labelLayer2 = new Label("from layer ");
						grid.add(labelLayer2, 2, 6);
						TextField TextLayer2 = new TextField();
						grid.add(TextLayer2, 3, 6);
						// get output values
						Label labeloutValues = new Label("Output results:");
						grid.add(labeloutValues, 7, 9);
						Label labelResult;
						
						double outRound;
						ScrollPane sp = new ScrollPane();
						VBox content= new VBox();
						for (int i = 0; i < outNodes; i++) {
							outRound=(double)Math.round(outLayer.getNeurons().get(i).getOutput() * 100000) / 100000;
							labelResult = new Label(+(i+1)+" = "
									+ outRound);
							content.getChildren().add(labelResult);
							
						}
						sp.setContent(content);
						sp.fitToWidthProperty();
						grid.add(sp, 7, 8);
						Button showWeight = new Button("Show connection weight");
						grid.add(showWeight, 0, 7);
						Label labelWeight = new Label("...");
						grid.add(labelWeight, 0, 8);
						// TODO make the handle receive the nodes and be able to
						// execute again with new values ????
						showWeight.setOnAction(new EventHandler<ActionEvent>() {
							
							public void handle(ActionEvent event) {
								
								int l1 = Integer.parseInt(TextLayer1.getText());
								int n1 = Integer.parseInt(TextNode1.getText());
								int l2 = Integer.parseInt(TextLayer2.getText());
								int n2 = Integer.parseInt(TextNode2.getText());
								double weiConnection = testNet
										.getConnectionWeight(l1, n1, l2, n2);
								double oriOut=testNet.getOutValue(l1,n1);
								double destOut=testNet.getOutValue(l2, n2);
								StringProperty propWei=new SimpleStringProperty();
								propWei.setValue(" Connection weight: "+weiConnection+
										"\n\n\n Origin node output: "+oriOut+
										"\n\n\n Destination node output: "+destOut);
								if ((weiConnection != -1 || weiConnection != -2)) {
									labelWeight.textProperty().bind(propWei);
									
								}

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
