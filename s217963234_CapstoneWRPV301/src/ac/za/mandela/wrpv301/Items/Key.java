package ac.za.mandela.wrpv301.Items;

import ac.za.mandela.wrpv301.Room.Room;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Key implements Item {

    private Image icon;
    private String description;
    String name;
    private StringProperty keyName = new SimpleStringProperty();
    int uses;
    private IntegerProperty keyUses = new SimpleIntegerProperty();
    Room room;

    public Key(String name) {
        this.name = name;
        this.uses = 1;
        this.keyName.set(name);
        this.keyUses.set(uses);
        this.icon = new Image(Key.class.getResourceAsStream("/Images/key.png"));
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDesc() {
        return this.description;
    }

    @Override
    public Image getImage() {
        return icon;
    }

    @Override
    public StringProperty nameProperty() {
        return keyName;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public IntegerProperty usesProperty() {
        return keyUses;
    }

    @Override
    public void setItemName(String name) {
        this.keyName.set(name);

    }

    @Override
    public void setItemUses(Integer uses) {
        this.keyUses.set(uses);
    }

    @Override
    public void useItem() {
        uses--;
        keyUses.set(uses);
    }

    @Override
    public String toString()
    {
        return String.format("%s",  keyName.getValue());
    }
}
