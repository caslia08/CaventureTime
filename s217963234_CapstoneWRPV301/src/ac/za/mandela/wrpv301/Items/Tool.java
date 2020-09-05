package ac.za.mandela.wrpv301.Items;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Tool implements Item {

    private String description;
    String name;
    Image icon;
    int uses;
    private StringProperty toolName = new SimpleStringProperty();
    private IntegerProperty toolUses = new SimpleIntegerProperty();

    public Tool(String name) {
        this.name = name;
        this.uses = 1;
        this.toolName.set(name);
        this.toolUses.set(uses);
        this.icon = new Image(Tool.class.getResourceAsStream("/Images/tools.png"));

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

}
