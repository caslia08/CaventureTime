package ac.za.mandela.wrpv301.Adventure;

import ac.za.mandela.wrpv301.Contollers.MapController;
import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.Items.Key;
import ac.za.mandela.wrpv301.Items.Weapon;
import ac.za.mandela.wrpv301.NPC.Enemy;
import ac.za.mandela.wrpv301.NPC.Friendly;
import ac.za.mandela.wrpv301.Player.Player;
import ac.za.mandela.wrpv301.Room.Room;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.ArrayList;

public class AdventureGame implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient Player player;
    private transient Adventure adventure;
    private transient Boolean isGameOver;
    private transient Item curItem;
    private transient Pane mapPane;
    private transient MapController mapController;
    private transient Boolean enemyEncountered;
    private transient Room start;

    public AdventureGame(Pane mapPane, Rectangle playerIcon) {
        this.mapPane = mapPane;
        this.mapController = new MapController(mapPane, playerIcon);
        this.isGameOver = false;
        this. enemyEncountered = false;
    }

    public void startGame() {
        player = new Player();
        adventure = new Adventure();
        this.start = adventure.getStart();
        player.setLocation(start);
        mapController.setPlayer(player);
        mapController.setRooms(adventure.getWorldRooms());
        mapController.drawAllRooms();
    }

    public Boolean getGameOver() {
        return isGameOver;
    }

    public void setGameOver(Boolean gameOver) {
        isGameOver = gameOver;
    }

    public void checkGame() {
        if (player.getHealth() < 0)
            setGameOver(true);
    }

    public Boolean checkWin() {

        ArrayList<Room> rooms = adventure.getWorldRooms();
        Room lastRoom = rooms.get(rooms.size()-1);

        if(player.getCurrentLocation().equals(lastRoom))
        {
            if(lastRoom.getNpc() instanceof Enemy)
            {
                Enemy curEnemy = (Enemy) lastRoom.getNpc();
                if (curEnemy.getAlive() == false)
                    return true;
            }
        }

        return false;
    }
    public void setMapPane(Pane mapPane) {
        this.mapPane = mapPane;
    }

    public void reDrawMap(Rectangle playerIcon)
    {
        this.mapController = new MapController(mapPane, playerIcon);
        mapController.setPlayer(player);
        mapController.setRooms(adventure.getWorldRooms());
        mapController.drawAllRooms();
        mapController.refreshRooms();
    }
    public String look() {
        return player.viewLocation();
    }

    public String processCommand(String input) {
        player.setPicked(false);
        player.setDropped(false);
        curItem = null;
        return processInput(input);
    }

    private String processInput(String input) {
        String[] data = input.split(" ");
        String command = data[0].toLowerCase();
        Room curRoom = player.getCurrentLocation();
        int direction = getDirection(data[0].toLowerCase());

        System.out.println(enemyEncountered);
        if (player.getCurrentLocation().getNpc() instanceof Enemy && enemyEncountered == true)
        {
            Enemy curEnemy = (Enemy) player.getCurrentLocation().getNpc();
            if (curEnemy.getAlive() == true) {
                if (data.length < 2) {
                    player.takeDamage(curEnemy);
                    return "Oh no you took damage do something or you will perish";
                } else {
                    String itemName = data[1].toLowerCase();
                    Item item = player.getItem(itemName);
                    return curEnemy.takeDamage(item, player, curRoom, mapController);
                }
            }
            else
            {
                enemyEncountered =false;
            }
        }
        if (direction == Integer.MIN_VALUE && data.length > 1) {
            switch (command) {
                case "drop": {
                    String itemName = data[1];
                    int index = player.findItem(itemName);
                    curItem = player.getItem(itemName);
                    return player.dropItem(index);
                }
                case "grab": {
                    if (curRoom.getItem() != null) {
                        String itemName = data[1].toLowerCase();
                        if (itemName.equals((curRoom.getItem().getName()).toLowerCase())) {
                            curItem = curRoom.getItem();
                            return player.pickUp(curItem, mapController);
                        } else
                            return "Can't seem to find that item..";
                    } else
                        return "There are no items in this room to collect";
                }
                case "use": {
                    String itemName = data[1].toLowerCase();
                    Item item = player.getItem(itemName);
                    if (item != null) {
                        if (curRoom.containsNPC()) {
                            if (curRoom.getNpc() instanceof Enemy) {
                                Enemy curEnemy = (Enemy) curRoom.getNpc();
                                return curEnemy.takeDamage(item, player, curRoom, mapController);
                            } else
                            {
                                Friendly curFriendly = (Friendly) curRoom.getNpc();
                                return curFriendly.interact(item, curRoom, mapController);
                            }
                        }
                        else {
                            if (item instanceof Key) {
                                return curRoom.unlock((Key) item, mapController);
                            }
                        }
                    }
                    return "Does not seem like you possess that item...";
                }
                case "inspect" : {
                    String itemName = data[1];
                    Item item = player.getItem(itemName);
                    if (item != null){
                        return item.getDesc();
                    }
                }
                default: {
                    System.out.println("Unrecognised command");
                }
            }
        } else if (direction != Integer.MIN_VALUE && player.getCurrentLocation().getRoom(direction) != null) {
            Room nextLocation = player.getCurrentLocation().getRoom(direction);
            if (nextLocation.getIsLocked() == true) {
                return "Locked! Seems like we might need a key";
            } else {
                player.move(direction);
                mapController.move(direction);
                if(player.getCurrentLocation().getNpc() instanceof Enemy)
                    this.enemyEncountered = true;
                //Room nextRoom = player.getCurrentLocation().getRoom(direction);
                //player.setLocation(nextRoom);
                return player.viewLocation();
            }
        } else if (direction != Integer.MIN_VALUE) {
            return "Ouch! Seems like you can't go that way dumbo! ʕ•ᴥ•ʔ";
        }
        return "Invalid Input please try again  ಠ ⌣ ಠ";
    }

    private int getDirection(String input) {
        int direction = Integer.MIN_VALUE;
        switch (input) {
            case "north":
                direction = 0;
                break;
            case "south":
                direction = 1;
                break;
            case "east":
                direction = 2;
                break;
            case "west":
                direction = 3;
                break;
        }
        return direction;
    }

    public double getHealth() {
        return player.getHealth() / 100.00;
    }

    public Player getPlayer() {
        return player;
    }

    public Item pickedUp() {
        if (player.getPicked() == true) {
            return curItem;
        } else return null;

    }

    public Item dropped() {
        if (player.getDropped() == true) {
            return curItem;
        } else return null;
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(player);
        outputStream.writeObject(adventure);
        outputStream.writeBoolean(enemyEncountered);
        outputStream.writeObject(isGameOver);
        outputStream.writeObject(curItem);
    }
    private void readObject(ObjectInputStream inputStream)  throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        initValues();
        this.player = (Player) inputStream.readObject();
        this.adventure = (Adventure) inputStream.readObject();
        this.enemyEncountered = inputStream.readBoolean();
        //TODO look at this and fix

    }
    private void initValues(){
        this.player = new Player();
        this.adventure = new Adventure();
        this.isGameOver = false;
    }

}
