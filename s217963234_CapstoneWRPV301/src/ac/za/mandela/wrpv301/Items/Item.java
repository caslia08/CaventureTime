package ac.za.mandela.wrpv301.Items;

import ac.za.mandela.wrpv301.Adventure.Adventure;
import ac.za.mandela.wrpv301.NPC.Enemy;
import ac.za.mandela.wrpv301.NPC.Friendly;
import ac.za.mandela.wrpv301.Room.Room;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public interface Item {

    public String getName();
    public String getDesc();
    public void setDesc(String desc);
    public Image getImage();
    public StringProperty nameProperty();
    public int getUses();
    public IntegerProperty usesProperty();
    public void setItemName(String name);
    public void setItemUses(Integer uses);
    public void useItem();



    @Override
    public String toString();
}
