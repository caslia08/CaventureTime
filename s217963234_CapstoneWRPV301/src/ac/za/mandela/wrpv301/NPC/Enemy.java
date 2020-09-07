package ac.za.mandela.wrpv301.NPC;

import ac.za.mandela.wrpv301.Contollers.MapController;
import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.Items.Tool;
import ac.za.mandela.wrpv301.Player.Player;
import ac.za.mandela.wrpv301.Items.Weapon;
import ac.za.mandela.wrpv301.Room.Room;
import javafx.scene.image.Image;

import java.awt.font.TextHitInfo;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Enemy extends NonPlayableCharacter {

     Item weakness;
     int damage;
     Boolean isAlive;

    public Enemy(String name, Item item, String ImageURL,  Item weakness, int damage) {
        super(name, item, ImageURL);
        this.weakness = weakness;
        this.damage = damage;
        this.isAlive = true;
    }

    public int getDamage() {
        return damage;
    }

    public String takeDamage(Item item, Player player, Room room, MapController mapController) {
        if (weakness.equals(item)) {
            isAlive = false;
            room.setDescription("You defeated the " + this.getName() + "in here. Check your map to see if there is anything worth while in here.");
            if(this.getItem() != null)
            {
                room.setItem(this.getItem());
                room.setNpc(null);
                mapController.addItemIcon(room, this.getItem());
            }
            mapController.removeNpcIcon(room);
            item.useItem();
            mapController.updateTooltip(room);
            return item.getName() + " worked! "+ this.getPositiveResult();
        } else {
            player.takeDamage(this);
            return this.getNegativeResult() +"\n." + "The " + item.getName()+ " did not work! You have been hurt!";
        }
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void initValues(){
        this.weakness = new Weapon("", "");
        this.damage = 0;
        this.isAlive = true;
    }

}
