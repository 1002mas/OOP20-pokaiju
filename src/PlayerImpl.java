import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class PlayerImpl implements Player {
    private String name;
    private String gender;
    private int trainerNumber;
    private PositionPlayer position;
    private int numPok;
    private ArrayList<String> pokaiju;

    public PlayerImpl(String name, String gender, int trainerNumber, int x, int y) {
	this.name = name;
	this.gender = gender;
	this.trainerNumber = trainerNumber;
	this.position = new PositionPlayer(x, y);
	this.pokaiju = new ArrayList<String>();

    }

    @Override
    public PositionPlayer getPosition() {
	// TODO Auto-generated method stub
	return this.position;
    }

    @Override
    public int getNumPok() {
	// TODO Auto-generated method stub
	return this.numPok;
    }
    
    public String allPok() {
        StringBuilder sb = new StringBuilder();
        for (String p : pokaiju) {
            sb.append(p.toString() + ", ");
        }
        return sb.toString();
    }
    
    public String toString() {
        return  this.name + "," + this.trainerNumber + this.gender +"," + allPok();
               
    }
    

}
