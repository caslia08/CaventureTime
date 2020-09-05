package ac.za.mandela.wrpv301.NPC;

import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.Player.Player;
import ac.za.mandela.wrpv301.Room.Room;
import javafx.scene.image.Image;

public class Friendly extends NonPlayableCharacter {
    Boolean isActive;
    Item itemNeeded;

    public Friendly(String name, Item item, Item itemNeeded) {
        super(name, item);
        this.isActive = true;
        this.itemNeeded = itemNeeded;
    }

    public String getName() { return super.getName(); }

    public void setName(String name) { super.setName(name); }

    public Item getItem() {
        return super.getItem();
    }

    public void setItem(Item item) { super.setItem(item); }

    public  Image getIcon()
    {
        return super.getIcon();
    }

    public void setIcon(Image image){
        super.setIcon(image);
    }

    public String interact(Item item, Player player, Room room) {
        if (itemNeeded.equals(item)) {
            isActive = false;
            room.setItem(this.getItem());
            room.addItemIcon();
            room.removeNpcIcon();
            room.setDescription("The "+ this.getName() + " seems to have left. Check on the map if anything was left behind");
            return this.getPositiveResult();
        } else {
            return this.getNegativeResult();

        }
    }

    public void setPositiveResult(String positiveResult) {
        super.setPositiveResult(positiveResult);
    }

    public void setNegativeResult(String negativeResult) {
        super.setPositiveResult(negativeResult);
    }
}
