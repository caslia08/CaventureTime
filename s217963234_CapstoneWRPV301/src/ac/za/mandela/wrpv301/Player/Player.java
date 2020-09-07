package ac.za.mandela.wrpv301.Player;
import ac.za.mandela.wrpv301.Contollers.MapController;
import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.NPC.Enemy;
import ac.za.mandela.wrpv301.Room.Room;
import javafx.scene.shape.Rectangle;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private transient double health;
    private transient ArrayList<Item> inventory = new ArrayList<>();
    private transient final int maxCapacity = 3;
    private transient Room currentLocation;
    private transient Boolean picked;
    private transient Boolean dropped;
    private transient double x;
    private transient double y;

    public Player() {
        this.health = 100;
        picked  = false;
        dropped = false;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public Boolean getPicked() {
        return picked;
    }

    public void setPicked(Boolean picked) {
        this.picked = picked;
    }

    public Boolean getDropped() {
        return dropped;
    }

    public void setDropped(Boolean dropped) {
        this.dropped = dropped;
    }

    public String pickUp(Item newItem, MapController mapController) {
        if (!(inventory.size() == maxCapacity)) {
            inventory.add(newItem);
            this.getCurrentLocation().setItem(null);
            picked = true;
            mapController.removeItemIcon(this.getCurrentLocation());
            System.out.println(this.currentLocation.getDescription());
            mapController.updateTooltip(this.getCurrentLocation());
            return "You have successfully picked up: " + newItem.getName();
        } else {
            return "You are over encumbered - please select and item to drop if you wish to pick up : " + newItem.getName();
        }
    }


    public String dropItem(int index) {
        Item curItem = inventory.get(index);
        if (curItem != null) {
            inventory.remove(curItem);
            dropped = true;
            this.getCurrentLocation().setItem(curItem);
            //this.currentLocation.addItemIcon();
            //updateTooltip();
            return "You have successfully dropped: " + curItem.getName() + " hope you don't need that later...";
        } else {
            return "Fool! You do not have that item!";
        }
    }

    public void takeDamage(Enemy enemy) {
        this.health -= enemy.getDamage();
    }

    public double getHealth() {
        return health;
    }

    public void setLocation(Room room) {
        currentLocation = room;
    }

    public String viewLocation() {
        return currentLocation.getDescription();
    }

    public Room getCurrentLocation() {
        return currentLocation;
    }

    public Item getItem(String itemName) {
        int index = findItem(itemName);
        if(index != -1)
        {return inventory.get(index);}
        else
            return null;
    }

    public void move(int direction)
    {
        Room nextRoom = this.getCurrentLocation().getRoom(direction);
        this.setLocation(nextRoom);
        nextRoom.setDiscovered(true);

    }

    public int findItem(String itemName) {
        int count = 0;
        for (Item item : inventory) {
            if (item.getName().toLowerCase().equals(itemName))
                return count;
            else
                count++;
        }
        return -1;
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeDouble(health);
        outputStream.writeObject(inventory);
        outputStream.writeObject(currentLocation);
        outputStream.writeBoolean(picked);
        outputStream.writeBoolean(dropped);
        outputStream.writeDouble(x);
        outputStream.writeDouble(y);

    }
    private void readObject(ObjectInputStream inputStream)  throws IOException, ClassNotFoundException {
        initValues();
        inputStream.defaultReadObject();
        this.health = inputStream.readDouble();
        this.inventory = (ArrayList<Item>) inputStream.readObject();
        Object curObj = inputStream.readObject();
        if(curObj instanceof Room)
        {
            this.currentLocation = (Room) curObj;
        }
        else this.currentLocation = null;
        this.picked = inputStream.readBoolean();
        this.dropped = inputStream.readBoolean();
        this.x = inputStream.readDouble();
        this.y = inputStream.readDouble();
    }
    private void initValues(){
        this.health = 100;
        this.inventory = new ArrayList<Item>();
        this.currentLocation = new Room();
        this.picked = false;
        this.dropped = false;
        this.x = 0;
        this.y = 0;
    }


}
