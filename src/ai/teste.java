package ai;
import java.io.IOException;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Random;

public class teste {

	static int NLINES = 98;

	public static void main(String[] args) throws IOException {

		Network testNet = new Network();
		Layer inLayer = new Layer(68, 1);
		Layer hLayer1 = new Layer(10, 2);
		Layer outLayer = new Layer(7, 3);

		testNet.replaceInputLayer(inLayer);
		testNet.replaceOutputLayer(outLayer);
		testNet.addHiddenLayer(hLayer1);
		testNet.createConnections();

		testNet.printNet();

		DataHandler.loadTrainingInputs(NLINES);

		System.out.println(DataHandler.coord.size());
		Random rd = new Random();
		int k = 0;
		int printErrorEveryXSet = 1000;
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
		} while (nSetRuns < 10000 || k != 0);
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
			for (int i = 0; i <7; i++) {
				System.out.println("Output " + i + ": "
						+ outLayer.getNeuron(i).getOutput());
			}
		}

		return;

	}

}
