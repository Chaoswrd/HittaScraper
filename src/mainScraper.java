import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class mainScraper {
	
	static BufferedWriter writer = null;
	static String city, url;
	static int searchResults;
	
	public static void main(String[] args) {
		
		System.out.println("Starting...");
		System.out.println("Setting arguments...");
		try{
			city = args[0];
			url = args[1];
			searchResults =  Integer.parseInt(args[2]);
			/*city = "Lund";
			url = "https://www.hitta.se/restauranger+serveringar+lund/företag/";
			searchResults = 205;*/
		}catch(NumberFormatException e){
			System.err.println("!!!The third argument is not how many search results the category includes!!!");
		}
		
		run(city, url);
		System.out.println("Finished!");
	}
	
	public static ArrayList<String> getTitles(String url){
		ArrayList<String> returnList = new ArrayList<String>();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
			System.out.println("Connected to url!");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("!!!Invalid URL!!!");
		}
		Elements hTwos = doc.select("span[class=result-row__item-hover-visualizer]");
		System.out.println("Adding titles to Arraylist...");
		for(Element hTwo: hTwos){
			returnList.add(hTwo.text());
		}
		return returnList;
	}
	
	public static void addTitlesToFile(ArrayList<String> titles){
		
		try{
			System.out.println("Writing to file...");
		    for(String title: titles){
				writer.append(title+"\n");
			}
		}
		catch ( IOException e){
			e.printStackTrace();
		}
		
		
	}
	
	public static void run(String city, String url){
		try {
			System.out.println("Clearing file...");
			writer = new BufferedWriter( new FileWriter("/Users/chaosward/Desktop/HittaResultat-"+city+".txt"));
			System.out.println("!!!!!!!" + System.getProperty("user.dir"));
			System.out.println("Adding city title!");
			writer.append(city+":\n");
			int i;
			System.out.println("Calculating limit...");
			int limit = (int)(2 + Math.floor(searchResults/26));
			for(i = 2; i<limit; i++){
				addTitlesToFile(getTitles("" + url + i));	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
		    try{
		        if ( writer != null)
		        writer.close( );
		    }
		    catch ( IOException e){
		    	e.printStackTrace();
		    }
		}
		
	}

}
