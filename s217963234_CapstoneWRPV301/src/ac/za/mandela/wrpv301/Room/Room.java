package ac.za.mandela.wrpv301.Room;

import ac.za.mandela.wrpv301.Contollers.MapController;
import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.Items.Key;
import ac.za.mandela.wrpv301.NPC.NonPlayableCharacter;

import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Room{

    final int maxDirections = 4;
    private NonPlayableCharacter npc;
    private Item item;
    public String description;
    private Boolean isLocked;
    private Boolean drawn;
    private Boolean isDiscovered; 
    private StackPane roomStackPane;
    private Rectangle roomRect;
    private Rectangle itemRect;
    private Rectangle npcRect;
    private Tooltip roomInfo;
    private double x, y;

    public Room(NonPlayableCharacter npc, Item item, String description, Boolean isLocked) {
        this.npc = npc;
        this.item = item;
        this.description = description;
        this.isLocked = isLocked;
        this.drawn = false;
        createRoom();
        x =0;
        y=0;
    }

    public void setRoomInfo(String tooltipText) {
        roomInfo.setText(tooltipText);
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

    private void createRoom()
    {
        this.roomRect = new Rectangle(70,70);
        roomRect.setFill(Color.rgb(7,7,5));
        this.roomStackPane = new StackPane();
        roomStackPane.setPrefWidth(65);
        roomStackPane.setPrefHeight(65);
        String tooltipText = "";

        this.roomStackPane.getChildren().add(roomRect);
        if(this.item != null ) {
            tooltipText+= "Items: " + this.item.getName();
           addItemIcon();
        }
        else
        {
            tooltipText+= "Items: None ";
        }

        if(this.npc != null ) {
            tooltipText+= "\nNPC: " + this.npc.getName();
            Image icon = npc.getIcon();
            this.npcRect = new Rectangle(35,35);
            npcRect.setFill(new ImagePattern(icon));
            this.roomStackPane.setAlignment(npcRect, Pos.BOTTOM_RIGHT);
            this.roomStackPane.getChildren().add(npcRect);
        }
        else
        {
            tooltipText+= "\nNPC: None ";
        }

        roomInfo = new Tooltip(tooltipText);
        roomInfo.setWrapText(true);
        Tooltip.install(roomRect, roomInfo);
    }
    public StackPane getRoomStackPane() {
        this.setDrawn(true);
        return roomStackPane;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public void removeItemIcon()
    {
        this.roomStackPane.getChildren().remove(itemRect);
    }

    public void removeNpcIcon()
    {
        this.roomStackPane.getChildren().remove(npcRect);
    }

    public void addItemIcon()
    {
        if(this.item != null ) {
            Image icon = item.getImage();
            this.itemRect = new Rectangle(20, 20);
            itemRect.setFill(new ImagePattern(icon));
            this.roomStackPane.setAlignment(itemRect, Pos.TOP_LEFT);
            this.roomStackPane.getChildren().add(itemRect);
        }
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
}
