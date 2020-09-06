package ac.za.mandela.wrpv301.Items;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Weapon implements Item, Serializable {
    private transient String name;
    private transient int uses;
    private transient String description = "";
    private transient StringProperty weaponName = new SimpleStringProperty();
    private transient IntegerProperty weaponUses = new SimpleIntegerProperty();
    private transient final Image icon = new Image(Tool.class.getResourceAsStream("/Images/sword.png"));

    //private transient String url = "/Images/sword.png";

    public Weapon(String name, String description) {
        this.name = name;
        this.description = description;
        this.uses = 1;
        this.weaponName.set(name);
        this.weaponUses.set(uses);

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
        return weaponName;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public IntegerProperty usesProperty() {
        return weaponUses;
    }

    @Override
    public void setItemName(String name) {
        this.weaponName.set(name);
    }
    @Override
    public void setItemUses(Integer uses) {
        this.weaponUses.setValue(uses);
    }
    @Override
    public String toString()
    {
        return String.format("%s",  weaponName.getValue());
    }

    @Override
    public void useItem() {
        uses--;
        weaponUses.set(uses);
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeUTF(name);
        outputStream.writeUTF(description);
        outputStream.writeInt(uses);
        outputStream.writeInt(weaponUses.getValue());
        outputStream.writeUTF(weaponName.getValue());
    }
    private void readObject(ObjectInputStream inputStream)  throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        initValues();
        this.name = inputStream.readUTF();
        this.description = inputStream.readUTF();
        this.uses = inputStream.readInt();
        this.weaponUses.setValue(inputStream.readInt());
        this.weaponName.set(inputStream.readUTF());
    }
    private void initValues(){
        this.name = "";
        this.description = "";
        this.uses = 1;
        this.weaponName = new SimpleStringProperty();
        this.weaponUses = new SimpleIntegerProperty();
    }



}
