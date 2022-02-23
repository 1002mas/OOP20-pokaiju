import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SimpleNpc implements Npc {

	private String name;
	private String typeOfNpc;
	private ArrayList<Monster> monstersOwned;
	private ArrayList<String> speechPhrases;
	
	
	public SimpleNpc(String name, ArrayList<Monster> monsterOwned, String speechFileName) {
	
		this.name =  name;
		this.typeOfNpc = "Simple Npc";
		this.monstersOwned = monsterOwned;
		this.speechPhrases = setPhrases(speechFileName);
	}
	


	@Override
	public int battle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTypeOfNpc() {
	
		return this.typeOfNpc;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public ArrayList<Monster> getMonstersOwned() {
	
		return monstersOwned;
	}

	
	public ArrayList<String> setPhrases(String speechFileName){		
		ArrayList<String> fileLines = new ArrayList<String>();
		try (final BufferedReader r = new BufferedReader(new FileReader(speechFileName))) {
			 		String line;			 		
			 		while((line=r.readLine()) != null) {
			 			fileLines.add(line);
	   					}
	    		    } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
		return fileLines;		
	}



	@Override
	public String interaction(String playerAnswer) {
			Random rand = new Random(); 
		    int max = speechPhrases.size() -1;
		    int index = rand.nextInt(max);
			return speechPhrases.get(index);
	}
	
/*	
	public static void main(String[] args) {
	System.out.println("eccome");
	SimpleNpc s = new SimpleNpc("Saro", "Frocio", null, "SpeechSimpleNpc.txt");
	System.out.println(s.getName());
	System.out.println(s.getTypeOfNpc());
	System.out.println(s.interaction("Spacchiu voi"));
	System.out.println(s.interaction(""));

	}
*/

}
