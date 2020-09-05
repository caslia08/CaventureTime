package ac.za.mandela.wrpv301.Player;
import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.NPC.Enemy;
import ac.za.mandela.wrpv301.Room.Room;
import java.util.ArrayList;

public class Player {

    double Health;
    public ArrayList<Item> inventory = new ArrayList<>();
    int maxCapacity = 3;
    Room currentLocation;
    private Boolean picked;
    private Boolean dropped;

    public Player() {
        this.Health = 100;
        picked  = false;
        dropped = false;
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

    public String pickUp(Item newItem) {
        if (!(inventory.size() == maxCapacity)) {
            inventory.add(newItem);
            this.getCurrentLocation().setItem(null);
            picked = true;
            currentLocation.removeItemIcon();
            updateTooltip();
            return "You have successfully picked up: " + newItem.getName();
        } else {
            return "You are over encumbered - please select and item to drop if you wish to pick up : " + newItem.getName();
        }
    }

    public void updateTooltip()
    {
        String tooltipText = "";
        if(this.currentLocation.getItem() == null)
        {
            tooltipText+= "Items: None ";
        }
        else
        {
            tooltipText+= "Items: " + this.currentLocation.getItem().getName();

        }
        if(this.currentLocation.getNpc() == null)
        {
            tooltipText+= "\nNPC: None ";
        }
        else
        {
            tooltipText+= "\nNPC: " + this.currentLocation.getNpc().getName();

        }
         this.currentLocation.setRoomInfo(tooltipText);
    }

    public String dropItem(int index) {
        Item curItem = inventory.get(index);
        if (curItem != null) {
            inventory.remove(curItem);
            dropped = true;
            this.getCurrentLocation().setItem(curItem);
            this.currentLocation.addItemIcon();
            updateTooltip();
            return "You have successfully dropped: " + curItem.getName() + " hope you don't need that later...";
        } else {
            return "Fool! You do not have that item!";
        }
    }

    public void takeDamage(Enemy enemy) {
        this.Health -= enemy.getDamage();
    }

    public double getHealth() {
        return Health;
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

}
