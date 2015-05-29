import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class DataHandler {
	
	static String COORD="coord_learn.txt";
	static ArrayList<Data> coord;
	
	public static void loadTrainingInputs() throws IOException{
		coord=new ArrayList<Data>();
		FileInputStream is=new FileInputStream(COORD);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line=null;
		String[] thisLine=null;
		ArrayList<Double> thisInp=new ArrayList<Double>();
		Data thisData;
		while((line=reader.readLine())!=null){
			thisLine=line.split(",");
			for(int i=0;i<68;i++){
				thisInp.add(Double.valueOf(thisLine[i]));
			}
			int capital=Integer.parseInt(thisLine[68]);
			thisData= new Data(capital,thisInp);
			coord.add(thisData);
		}
		
		
	}
	public static int getRandomData(){
		ArrayList<Integer> vlds= new ArrayList<Integer>(); 
		for (int i=0;i<1000;i++) {
			if(!coord.get(i).isUsed()){
				vlds.add(i);
			}
			
		}
		  Random rand = new Random();
		    int randomNum = rand.nextInt(vlds.size());
		    return vlds.get(randomNum);
	}
	
	
}
