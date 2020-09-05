package ac.za.mandela.wrpv301.Items;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public interface Item {

    public String getName();
    public String getDesc();
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
