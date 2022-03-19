package model.GameItem;

import model.monster.Monster;

public interface GameItems {
    String getNameItem();

    GameItemTypes getType();

    boolean use(Monster m);

    int getNumber();

    void setNumber(int i);
}
