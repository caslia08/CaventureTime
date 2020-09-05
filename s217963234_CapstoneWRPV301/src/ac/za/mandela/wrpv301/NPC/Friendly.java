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
            room.setItem(this.item);
            room.addItemIcon();
            room.removeNpcIcon();
            room.setDescription("The "+ name + " seems to have left. Check on the map if anything was left behind");
            return positiveResult;
        } else {
            return negativeResult;

        }
    }

    public void setPositiveResult(String positiveResult) {
        this.positiveResult = positiveResult;
    }

    public void setNegativeResult(String negativeResult) {
        this.negativeResult = negativeResult;
    }
}
