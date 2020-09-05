package ac.za.mandela.wrpv301.Items;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Weapon implements Item {
    private transient String name;
    private transient int uses;
    private transient String description = "";
    private transient StringProperty weaponName = new SimpleStringProperty();
    private transient IntegerProperty weaponUses = new SimpleIntegerProperty();
    private transient Image icon;

    public Weapon(String name) {
        this.name = name;
        this.uses = 1;
        this.weaponName.set(name);
        this.weaponUses.set(uses);
        this.icon = new Image(Weapon.class.getResourceAsStream("/Images/sword.png"));

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



}
