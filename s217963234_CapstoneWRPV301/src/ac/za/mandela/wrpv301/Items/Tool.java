package ac.za.mandela.wrpv301.Items;

import ac.za.mandela.wrpv301.Room.Room;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Tool implements Item, Serializable {

    private transient final String ImageURL = "/Images/tools.png";
    private transient String description;
    private transient String name;
    private transient int uses;
    private transient StringProperty toolName = new SimpleStringProperty();
    private transient IntegerProperty toolUses = new SimpleIntegerProperty();

    public Tool(String name, String description) {
        this.name = name;
        this.uses = 1;
        this.toolName.set(name);
        this.toolUses.set(uses);
        this.description = description;
    }

    @Override
    public String getDesc() {
        return this.description;
    }

    @Override
    public String getImageURL() {
        return ImageURL;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public StringProperty nameProperty() {
        return toolName;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public IntegerProperty usesProperty() {
        return toolUses;
    }

    @Override
    public void setItemName(String name) {
        this.toolName.set(name);
    }

    @Override
    public void setItemUses(Integer uses) {
        this.toolUses.set(uses);
    }

    @Override
    public void useItem() {
        uses--;
        toolUses.set(uses);
    }


    @Override
    public String toString()
    {
        return String.format("%s",  toolName.getValue());
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeUTF(name);
        outputStream.writeUTF(description);
        outputStream.writeInt(uses);
        outputStream.writeInt(toolUses.getValue());
        outputStream.writeUTF(toolName.getValue());
    }
    private void readObject(ObjectInputStream inputStream)  throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        initValues();
        this.name = inputStream.readUTF();
        this.description = inputStream.readUTF();
        this.uses = inputStream.readInt();
        //this.icon = (Image) inputStream.readObject();
        this.toolUses.setValue(inputStream.readInt());
        this.toolName.set(inputStream.readUTF());
    }
    private void initValues(){
        this.name = "";
        this.description = "";
        this.uses = 1;
        this.toolName = new SimpleStringProperty();
        this.toolUses = new SimpleIntegerProperty();
        //this.icon = new Image(Tool.class.getResourceAsStream("/Images/tools.png"));
    }

}
