package model.gameitem;

import model.monster.EvolutionType;
import model.monster.Monster;

public class EvolutionItem extends AbstractGameItem {
    /**
     * 
     * @param nameItem
     * @param description
     */
    public EvolutionItem(final String nameItem, final String description) {
        super(nameItem, description, GameItemTypes.EVOLUTIONTOOL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean use(final Monster m) {
        if (m.getSpecies().getEvolutionType() == EvolutionType.ITEM && m.canEvolveByItem(this)) {
            m.evolve();
            return true;
        }
        return false;

    }

}
