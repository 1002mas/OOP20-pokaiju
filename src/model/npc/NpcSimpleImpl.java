package model.npc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import model.Pair;
import model.gameevents.GameEvent;

public class NpcSimpleImpl implements NpcSimple {

    private final String name;
    private TypeOfNpc typeOfNpc;
    private List<String> sentences;
    private int currentSentence;
    private Set<GameEvent> events;
    private Optional<GameEvent> triggeredEvent;
    private Pair<Integer, Integer> position;
    private boolean isVisible;
    private boolean isEnabled;

    protected NpcSimpleImpl(final String name, final TypeOfNpc typeOfNpc, final List<String> sentences,
            final Pair<Integer, Integer> position, final boolean isVisible, final boolean isEnabled) {
        this.name = name;
        this.typeOfNpc = typeOfNpc;
        this.sentences = sentences;
        this.position = position;
        this.currentSentence = 0;
        this.isVisible = isVisible;
        this.isEnabled = isEnabled;
        this.events = new HashSet<>();
        this.triggeredEvent = Optional.empty();
    }

    public NpcSimpleImpl(final String name, final List<String> sentences, final Pair<Integer, Integer> position,
            final boolean isVisible, final boolean isEnabled) {
        this(name, TypeOfNpc.SIMPLE, sentences, position, isVisible, isEnabled);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Optional<String> interactWith() {
        if (isEnabled) {
            final Optional<String> result = Optional.of(sentences.get(currentSentence));
            final Optional<GameEvent> activeEvent = events.stream().filter(ge -> ge.isActive()).findFirst();
            if (activeEvent.isPresent()) {
                this.triggeredEvent = activeEvent;
                activeEvent.get().activate();
            } else {
                this.triggeredEvent = Optional.empty();
            }
            return result;
        }
        return Optional.empty();
    }

    @Override
    public Optional<GameEvent> getTriggeredEvent() {
        return this.triggeredEvent;
    }

    @Override
    public void setDialogueText(final int textID) {
        if (textID >= 0 && textID < this.sentences.size()) {
            this.currentSentence = textID;
        }
    }

    @Override
    public TypeOfNpc getTypeOfNpc() {
        return this.typeOfNpc;
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    @Override
    public void changeNpcPosition(final Pair<Integer, Integer> newPosition) {
        this.position = newPosition;
    }

    @Override
    public boolean isVisible() {
        return this.isVisible;
    }

    @Override
    public void setVisible(final boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public void setEnabled(final boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public void addGameEvent(final GameEvent gameEvent) {
        this.events.add(gameEvent);
    }

    @Override
    public List<GameEvent> getGameEvents() {
        return Collections.unmodifiableList(new ArrayList<>(this.events));
    }

    @Override
    public int getCurrentSetence() {
        return this.currentSentence;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

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

        final NpcSimpleImpl other = (NpcSimpleImpl) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "NpcSimpleImpl [name=" + name + ", typeOfNpc=" + typeOfNpc + ", sentences=" + sentences
                + ", currentSentence=" + currentSentence + ", events=" + events + ", triggeredEvent=" + triggeredEvent
                + ", position=" + position + ", isVisible=" + isVisible + ", isEnabled=" + isEnabled + "]";
    }

}
