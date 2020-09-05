package ac.za.mandela.wrpv301.NPC;

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

public class Enemy extends NonPlayableCharacter implements Serializable {

    private transient Item weakness;
    private transient int damage;
    private transient Boolean isAlive;

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
            room.setDescription("You defeated the " + this.getName() + "in here. Check your map to see if there is anything worth while in here.");
            room.removeNpcIcon();
            return item.getName() + " worked! You have defeated " + this.getName();
        } else {
            player.takeDamage(this);
            item.useItem();
            return "The "+item.getName() + " did not work! You have taken damage! ";

        }
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(weakness);
        outputStream.writeInt(damage);
        outputStream.writeBoolean(isAlive);
    }
    private void readObject(ObjectInputStream inputStream)  throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        initValues();
        this.weakness =(Item)inputStream.readObject();
        this.damage =inputStream.readInt();
        this.isAlive =inputStream.readBoolean();

    }
    private void initValues(){
        this.weakness = null;
        this.damage = 0;
        this.isAlive = true;
    }

}
