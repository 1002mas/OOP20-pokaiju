package gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;

import model.JTableModel;
import model.gameitem.GameItemTypes;
import model.gameitem.GameItems;
import model.gameitem.SimpleItem;

public class GameItemPanel extends JPanel {

    public GameItemPanel() {
  	init();
      }

    private void init() {
	this.setLayout(new GridLayout());
	String[] itemColumnNames = { "nameItem", "quantity", "description", "type" };
	List<GameItems> listGameItemTry = new ArrayList<>();
	listGameItemTry.add(new SimpleItem("Heal", 1, "heal 100 hp", GameItemTypes.HEAL));
	listGameItemTry.add(new SimpleItem("Heal3123", 12, "heal 10as0 hp", GameItemTypes.HEAL));
	listGameItemTry.add(new SimpleItem("Heal123", 1, "heal 10sda0 hp", GameItemTypes.HEAL));
	JTableModel model = new JTableModel(listGameItemTry);
	JTable gameItemLabel = new JTable(model);
	this.add(gameItemLabel);
    }

}
// codice giusto
/*package gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;

import model.JTableModel;
import model.gameitem.GameItemTypes;
import model.gameitem.GameItems;
import model.gameitem.SimpleItem;

public class GameItemPanel extends JPanel {
    private final List<GameItems> gameItemlist;

    public GameItemPanel(List<GameItems> list) {
	this.gameItemlist = list;
	init();
    }

    private void init() {
	this.setLayout(new GridLayout());
	String[] itemColumnNames = { "nameItem", "quantity", "description", "type" };
	JTableModel model = new JTableModel(this.gameItemlist);
	JTable gameItemLabel = new JTable(model);
	this.add(gameItemLabel);
    }

}
*/
