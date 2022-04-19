package model.battle;

import java.util.Objects;

import model.monster.MonsterType;

public class MovesImpl implements Moves {
    private final String name;
    private final int base;
    private final MonsterType type;
    private final int maxPP;

    public MovesImpl(final String name, final int base, final MonsterType type, final int pp) {
        this.name = name;
        this.base = base;
        this.type = type;
        this.maxPP = pp;

    }

    /***
     * {@inheritDoc}.
     */
    public String getName() {
        return name;
    }

    /***
     * {@inheritDoc}.
     */
    public int getBase() {
        return base;
    }

    /***
     * {@inheritDoc}.
     */
    public MonsterType getType() {
        return type;
    }

    /***
     * {@inheritDoc}.
     */
    public int getPP() {
        return maxPP;
    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final MovesImpl other = (MovesImpl) obj;
        return Objects.equals(name, other.name);
    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public int getDamage(final MonsterType type) {
        return (int) this.type.damageTo(type);
    }

}
