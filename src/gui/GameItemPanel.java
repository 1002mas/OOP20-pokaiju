package gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.PlayerController;
import model.JTableModel;
import model.gameitem.GameItemTypes;
import model.gameitem.*;

public class GameItemPanel extends JPanel {
    private final PlayerController playerController;

    public GameItemPanel(PlayerController playerController) {
	this.playerController = playerController;
	init();
    }

    private void init() {
	this.setLayout(new GridLayout());
	//List<GameItems> listGameItems = this.playerController.getItemList();
	List<GameItems> listGameItems = new ArrayList<>();
	listGameItems.add(new HealingItem("Heal", 1, "heal 100 hp"));
	listGameItems.add(new HealingItem("Heal3123", 12, "heal 10as0 hp"));
	listGameItems.add(new HealingItem("Heal123", 1, "heal 10sda0 hp"));
	JTableModel model = new JTableModel(listGameItems);
	JTable gameItemTable = new JTable(model);
	JScrollPane scrollPane = new JScrollPane(gameItemTable);
	this.add(scrollPane);
    }

}
// codice giusto
/*
 * public class GameItemPanel extends JPanel { private final List<GameItems>
 * gameItemlist;
 * 
 * public GameItemPanel(List<GameItems> list) { this.gameItemlist = list;
 * init(); }
 * 
 * private void init() { this.setLayout(new GridLayout()); String[]
 * itemColumnNames = { "nameItem", "quantity", "description", "type" };
 * JTableModel model = new JTableModel(this.gameItemlist); JTable gameItemLabel
 * = new JTable(model); this.add(gameItemLabel); }
 * 
 * }
 */
