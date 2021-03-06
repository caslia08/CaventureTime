package ac.za.mandela.wrpv301.NPC;

import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.Player.Player;
import ac.za.mandela.wrpv301.Items.Weapon;
import ac.za.mandela.wrpv301.Room.Room;
import javafx.scene.image.Image;

public class Enemy extends NonPlayableCharacter {

    Item weakness;
    int damage;
    Boolean isAlive;
    String positiveResult;
    String negativeResult;

    public Enemy(String name, Item item, Item weakness, int damage) {
        super(name, item);
        this.weakness = weakness;
        this.damage = damage;
    }

    public String getName() { return super.getName(); }

    public void setName(String name) { super.setName(name); }

    public Item getItem() {
        return super.getItem();
    }

    public void setItem(Item item) { super.setItem(item); }

    public Image getIcon()
    {
        return super.getIcon();
    }

    public void setIcon(Image image){
        super.setIcon(image);
    }

    public int getDamage() {
        return damage;
    }

    public String takeDamage(Item item, Player player, Room room) {
        if (weakness.equals(item)) {
            isAlive = false;
            room.setDescription("You defeated the " + this.name + "in here. Check your map to see if there is anything worth while in here.");
            room.removeNpcIcon();
            return item.getName() + " worked! You have defeated " + name;
        } else {
            player.takeDamage(this);
            item.useItem();
            return "The "+item.getName() + " did not work! You have taken damage! ";

        }
    }

    public void setPositiveResult(String positiveResult) {
        this.positiveResult = positiveResult;
    }

    public void setNegativeResult(String negativeResult) {
        this.negativeResult = negativeResult;
    }


}
