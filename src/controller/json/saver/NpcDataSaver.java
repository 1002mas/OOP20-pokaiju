package controller.json.saver;

public class NpcDataSaver {

	private String name;
	private int idSentence;
	private boolean isVisible;
	private boolean isEnable;

	public NpcDataSaver(String name, int idSentence, boolean isVisible, boolean isEnable) {

		this.name = name;
		this.isEnable = isEnable;
		this.idSentence = idSentence;
		this.isVisible = isVisible;
	}

	public String getName() {
		return name;
	}

	public int getIdSentence() {
		return idSentence;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public boolean isEnable() {
		return isEnable;
	}

}
