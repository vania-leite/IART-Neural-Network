import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;


public class File {
	
	String COORD="coord_learn.txt";
	ArrayList<Data> coord=new ArrayList<Data>();
	
	public File() throws IOException{
		FileInputStream is=new FileInputStream(COORD);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line=null;
		String[] thisLine=null;
		float[] thisInp=new float[68];
		Data thisData;
		while((line=reader.readLine())!=null){
			thisLine=line.split(",");
			for(int i=0;i<68;i++){
				thisInp[i]=Float.valueOf(thisLine[i]);
			}
			int capital=Integer.parseInt(thisLine[68]);
			thisData= new Data(capital,thisInp);
			coord.add(thisData);
		}
		
		
	}
	
	
}
