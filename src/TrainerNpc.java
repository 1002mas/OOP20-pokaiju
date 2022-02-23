import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TrainerNpc implements Npc {

	private String name;
	private String typeOfNpc;
	private ArrayList<Monster> monstersOwned;
	private ArrayList<String> speechPhrases;
	private boolean defeated;
	
	
	public TrainerNpc(String name, ArrayList<Monster> monsterOwned, String speechFileName) {
		this.name =  name;
		this.typeOfNpc = "Trainer Npc";
		this.monstersOwned = monsterOwned;
		this.speechPhrases = setPhrases(speechFileName);
	}
	
	@Override
	public String interaction(String playerAnswer) {	//Chiamata dal player restituisce la risposta del npc
														/*Se player interagisce per la prima volta non manda alcuna risposta (da un menù di gioco)
														 * altrimenti può mandare una richiesta di battaglia o annullare l'apertura l'interazione (mandando un "exit")*/
		String result = "";								
		if(!isDefeated()) {	
			switch(playerAnswer) {
			case "Combatti":
				int isTheWinner = battle();			//che sia int o boolean dovremmo restituire il vincitore 
				if(isTheWinner!=1) {
					result="Bel combattimento!";		//estendibile con frasi rand
				}
				else {
					result="Hai ancora da migliorare";	//estendibile con frasi rand
				}
				
			break;
			
			case "":
				Random rand = new Random(); 
			    int max = speechPhrases.size() -1;
			    int index = rand.nextInt(max);
				result = speechPhrases.get(index);				
			break;
			
			case "Exit":
				result = "Arrivederci";
			break;
			
			}
		}
		else {
			result = "Cos'è vuoi battermi di nuovo?";	//estendibile con frasi rand
		}
		return result;
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
		return this.monstersOwned;
	}
	
	public boolean isDefeated() {
		return this.defeated;
	}
	
	public void setDefeated() {
		this.defeated = true;
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

}
