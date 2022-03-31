package model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.gameitem.GameItems;

public class JTableModel extends AbstractTableModel {
    private final List<GameItems> gameItemList;
    private final String[] columnNames = new String[] { "nameItem", "quantity", "description", "type" };

    public JTableModel(List<GameItems> gameItemList) {
	super();
	this.gameItemList = gameItemList;
    }

    @Override
    public int getRowCount() {
	// TODO Auto-generated method stub
	return this.gameItemList.size();
    }
    
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
	// TODO Auto-generated method stub
	return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	// TODO Auto-generated method stub
	GameItems item = this.gameItemList.get(rowIndex);
	if (columnIndex == 0) {
	    return item.getNameItem();
	} else if (columnIndex == 1) {
	    return item.getNumber();
	} else if (columnIndex == 2) {
	    return item.getDescription();
	} else if (columnIndex == 3) {
	    return item.getType();
	}
	return null;
    }

}
