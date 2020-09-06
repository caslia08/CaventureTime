package ac.za.mandela.wrpv301.Items;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface Item {

     String getName();
     String getDesc();
     String getImageURL();
     StringProperty nameProperty();
     int getUses();
     IntegerProperty usesProperty();
     void setItemName(String name);
     void setItemUses(Integer uses);
     void useItem();



    @Override
    public String toString();
}
