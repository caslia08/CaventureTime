package ac.za.mandela.wrpv301.NPC;

import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.Player.Player;
import javafx.scene.image.Image;

public class NonPlayableCharacter {
    String name;
    Item item;
    String positiveResult;
    String negativeResult;
    Image icon;

    public NonPlayableCharacter(String name, Item item) {
        this.name = name;
        this.item = item;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
