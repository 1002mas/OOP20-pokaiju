package gui;

public enum PanelTypes {
    NEW_GAME_PANEL("new game"), MAP_PANEL("map game"), MENU_PANEL("menu"), LOGIN_PANEL("login panel");

    private final String panelName;

    PanelTypes(String panelName) {
	// TODO Auto-generated constructor stub
	this.panelName = panelName;
    }
}
