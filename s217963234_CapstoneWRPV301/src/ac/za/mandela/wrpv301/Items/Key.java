package ac.za.mandela.wrpv301.Items;

import ac.za.mandela.wrpv301.Room.Room;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Key implements Item, Serializable {
    private transient final Image icon = new Image(Tool.class.getResourceAsStream("/Images/key.png"));
    private transient String description;
    private transient String name;
    private transient StringProperty keyName = new SimpleStringProperty();
    private transient int uses;
    private transient IntegerProperty keyUses = new SimpleIntegerProperty();
    private transient Room room;

    public Key(String name, String description) {
        this.name = name;
        this.description = description;
        this.uses = 1;
        this.keyName.set(name);
        this.keyUses.set(uses);
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

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeUTF(name);
        outputStream.writeUTF(description);
        outputStream.writeInt(uses);
        outputStream.writeObject(room);
        outputStream.writeInt(keyUses.getValue());
        outputStream.writeUTF(keyName.getValue());
    }
    private void readObject(ObjectInputStream inputStream)  throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        initValues();
        this.name = inputStream.readUTF();
        this.description = inputStream.readUTF();
        this.uses = inputStream.readInt();
        this.room = (Room) inputStream.readObject();
        this.keyUses.setValue(inputStream.readInt());
        this.keyName.set(inputStream.readUTF());
    }
    private void initValues(){
        this.name = "";
        this.description = "";
        this.uses = 1;
        this.keyName = new SimpleStringProperty();
        this.keyUses = new SimpleIntegerProperty();
    }
}
