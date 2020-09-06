package ac.za.mandela.wrpv301.Room;

import ac.za.mandela.wrpv301.Contollers.MapController;
import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.Items.Key;
import ac.za.mandela.wrpv301.NPC.NonPlayableCharacter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {

    final int maxDirections = 4;
    private transient NonPlayableCharacter npc;
    private transient Item item;
    private transient String description;
    private transient Boolean isLocked;
    private transient Boolean drawn;
    private transient Boolean isDiscovered;
    private transient StackPane roomStackPane;
    private transient Rectangle itemRect;
    private transient Rectangle npcRect;
    private transient Tooltip tooltip;

    private double x, y;

    public Room(NonPlayableCharacter npc, Item item, String description, Boolean isLocked) {
        this.npc = npc;
        this.item = item;
        this.description = description;
        this.isLocked = isLocked;
        this.drawn = false;
        this.isDiscovered = false;
        x =0;
        y=0;
    }

    public Room() {

    }

    public Boolean getDiscovered() {
        return isDiscovered;
    }

    public void setDiscovered(Boolean discovered) {
        this.isDiscovered = discovered;
    }

    Room[] exits = new Room[maxDirections];

    public Item getItem() {
        return item;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public Rectangle getItemRect() {
        return itemRect;
    }

    public void setItemRect(Rectangle itemRect) {
        this.itemRect = itemRect;
    }

    public Rectangle getNpcRect() {
        return npcRect;
    }

    public void setNpcRect(Rectangle npcRect) {
        this.npcRect = npcRect;
    }

    public void setRoomStackPane(StackPane roomStackPane) {
        this.roomStackPane = roomStackPane;
    }

    public StackPane getRoomStackPane() {
        this.setDrawn(true);
        return roomStackPane;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public void setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public Boolean getDrawn() {
        return drawn;
    }

    public void setDrawn(Boolean drawn) {
        this.drawn = drawn;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String unlock(Key key, MapController mapController)
    {
        Room room = key.getRoom();
        if(this.isAdjacent(room) == false)
            return "No adjacent rooms to be unlocked";
        else {
            ArrayList<Connection> pathConn = mapController.getPathConnection();
            for(Connection connection: pathConn)
            {
                connection.checkLock(room, this);
            }

            key.useItem();
            room.setIsLocked(false);
            return "The "+ key.getName() +" worked! The room has been unlocked!";
        }
    }

    public void setExit(Room room, int index)
    {
        exits[index] = room;
    }

    //In case I want to add a npc after player has left or make it disappear  *magic noises*
    public void setNpc(NonPlayableCharacter npc) {
        this.npc = npc;
    }

    //Add or remove item after it has been selected
    public void setItem(Item item) {
        this.item = item;
    }

    //Change room description after having exited the room

    public String getDescription() {
        return description;
    }

    public Room getRoom(int direction)
    {
        return exits[direction];
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean containsNPC()
    {
        if(npc!= null)
            return true;
        else return false;
    }

    public NonPlayableCharacter getNpc() {
        return this.npc;
    }

    public Room[] getExits() {
        return exits;
    }

    private Boolean isAdjacent(Room room)
    {
        for(int i=0; i< this.exits.length; i++)
        {
            if(this.exits[i] == room)
                return true;
        }
        return false;
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(npc);
        outputStream.writeObject(item);
        outputStream.writeUTF(description);
        outputStream.writeBoolean(isLocked);
        //outputStream.writeBoolean(drawn);
        outputStream.writeBoolean(isDiscovered);
        outputStream.writeDouble(x);
        outputStream.writeDouble(y);

    }
    private void readObject(ObjectInputStream inputStream)  throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        initValues();
        Object curObj = inputStream.readObject();
        if(curObj instanceof NonPlayableCharacter)
        {
            this.npc = (NonPlayableCharacter) curObj;
        }
        else this.npc = null;
        this.item= (Item) inputStream.readObject();
        this.description= inputStream.readUTF();
        this.isLocked= inputStream.readBoolean();
        //.drawn= inputStream.readBoolean();
        this.isDiscovered= inputStream.readBoolean();
        this.x= inputStream.readDouble();
        this.y= inputStream.readDouble();
    }
    private void initValues(){
        this.npc = new NonPlayableCharacter();
        this.description = "";
        this.isLocked = false;
        this.drawn = false;
        x =0;
        y=0;
    }


}
