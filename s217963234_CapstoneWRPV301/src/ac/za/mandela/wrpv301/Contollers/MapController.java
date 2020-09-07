package ac.za.mandela.wrpv301.Contollers;

import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.NPC.NonPlayableCharacter;
import ac.za.mandela.wrpv301.Player.Player;
import ac.za.mandela.wrpv301.Room.Connection;
import ac.za.mandela.wrpv301.Room.Room;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.font.TextHitInfo;
import java.util.ArrayList;

public class MapController {

    private Player player;
    public Pane pane;
    public Rectangle playerIcon;
    public Group group;
    ArrayList<Room> rooms;
    private ArrayList<StackPane> roomStackPanes = new ArrayList<StackPane>();
    private ArrayList<Connection> pathConnection = new ArrayList<Connection>();
    public double x;
    public double y;

    public MapController(Pane pane, Rectangle playerIcon) {
        this.playerIcon = playerIcon;
        playerIcon.setVisible(true);
        this.pane = pane;
        Image icon = new Image(MapController.class.getResourceAsStream("/Images/female.png"));
        playerIcon.setWidth(30);
        playerIcon.setHeight(30);
        playerIcon.setFill(new ImagePattern(icon));
        playerIcon.setLayoutX(35);
        playerIcon.setLayoutY(332);
        x= playerIcon.getLayoutX();
        y=playerIcon.getLayoutY();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public void move(int direction) {
        this.getDir(direction);
        this.group = new Group();
        group.getChildren().addAll(playerIcon);
        pane.getChildren().add(playerIcon);
        pane.getChildren().get(pane.getChildren().size() - 1).toFront();
        player.setXY(x, y);
        updateMap();
    }

    private void getDir(int dir) {
        switch (dir) {
            case 0: //NORTH
            {
                y-=140;
            }
            break;
            case 1://SOUTH
            {
                y+=140;
            }
            break;
            case 2://EAST
            {

                x+=180;
            }
            break;
            case 3://WEST
            {

                x-=180;
            }
            break;
        }
        player.setXY(x, y);
        playerIcon.setLayoutX(x);
        playerIcon.setLayoutY(y);
    }

    public void drawAllRooms() {
        //Gets the first room for me and draws and places it.
        Room curRoom = rooms.get(0);
        curRoom.setDiscovered(true);
        curRoom.setXY(15, 310);

        StackPane newRoom = createRoom(curRoom);
        newRoom.setLayoutX(curRoom.getX());
        newRoom.setLayoutY(curRoom.getY());
        pane.getChildren().add(newRoom);
        roomStackPanes.add(newRoom);

        if(!player.getCurrentLocation().equals(curRoom))
            newRoom.setVisible(false);
        //pane.getChildren().get(pane.getChildren().size() - 1).toBack();
        for (int i = 0; i < rooms.size(); i++) {
            curRoom = rooms.get(i);
            drawDirectedRoom(curRoom);
        }
        updateConnections();
        pane.getChildren().get(0).toFront();
    }

    private void drawDirectedRoom(Room room) {
        Room[] exits = room.getExits();
        for (int i = 0; i < exits.length; i++) {
            Room curRoom = exits[i];
            if (curRoom != null)
                if (curRoom.getDrawn() == false) {
                    determineRoomDirection(curRoom, room.getX(), room.getY(), i);
                    createPlaceConnection(pane, room, curRoom, i);
                }
        }
    }

    private void determineRoomDirection(Room room, double x, double y, int dir) {
        StackPane newRoom = createRoom(room);
        newRoom.setVisible(false);
        switch (dir) {
            case 0: //NORTH
            {
                room.setXY(x, y - 140);
                newRoom.setLayoutX(room.getX());
                newRoom.setLayoutY(room.getY());
            }
            break;
            case 1://SOUTH
            {
                room.setXY(x, y + 140);
                newRoom.setLayoutX(room.getX());
                newRoom.setLayoutY(room.getY());
            }
            break;
            case 2://EAST
            {
                room.setXY(x + 180, y);
                newRoom.setLayoutX(room.getX());
                newRoom.setLayoutY(room.getY());
            }
            break;
            case 3://WEST
            {
                room.setXY(x - 180, y);
                newRoom.setLayoutX(room.getX());
                newRoom.setLayoutY(room.getY());
            }
            break;
        }
        pane.getChildren().add(newRoom);
        roomStackPanes.add(newRoom);
    }

    private void updateMap() {
        updateRooms();
        updateConnections();
    }

    private void updateRooms() {
        if (player.getCurrentLocation().getDiscovered() == true) {
            player.getCurrentLocation().getRoomStackPane().setVisible(true);
        }
    }

    public void refreshRooms()
    {
        for (Room room: rooms)
        {
            if(room.getDiscovered() == true) {
                room.getRoomStackPane().setVisible(true);

                for (Connection connection : pathConnection) {
                    Room room1 = connection.getFirstRoom();
                    if (room.equals(room1)) {
                        connection.getConnStackPane().setVisible(true);
                    }
                }
            }
        }
        playerIcon.setLayoutX(player.getX());
        playerIcon.setLayoutY(player.getY());
        x = player.getX();
        y = player.getY();

        pane.getChildren().add(playerIcon);
    }


    private void updateConnections()
    {
        for(Connection connection: pathConnection)
        {
            Room room1 = connection.getFirstRoom();
            if( player.getCurrentLocation().equals(room1))
            {
                connection.getConnStackPane().setVisible(true);
            }
        }
    }

    private void createPlaceConnection(Pane pane, Room room1, Room room2, int dir)
    {
        Connection newConn = new Connection(room1, room2, dir);
        StackPane connection = newConn.createConnection();
        connection.setLayoutX(newConn.getX());
        connection.setLayoutY(newConn.getY());
        connection.setVisible(false);
        pathConnection.add(newConn);
        pane.getChildren().add(connection);
    }

    public ArrayList<Connection> getPathConnection() {
        return pathConnection;
    }

    private StackPane createRoom(Room room)
    {
        Rectangle roomRect = new Rectangle(70,70);
        roomRect.setFill(Color.rgb(145,181,155));
        StackPane roomStackPane = new StackPane();
        roomStackPane.setPrefWidth(65);
        roomStackPane.setPrefHeight(65);

        String tooltipText = room.getDescription();
        roomStackPane.getChildren().add(roomRect);

        Item roomItem = room.getItem();

        if(roomItem != null ) {
            tooltipText+= "\nItems: " + roomItem.getName();
            Rectangle itemRect = createItemIcon(roomItem);
            roomStackPane.setAlignment(itemRect, Pos.TOP_LEFT);
            roomStackPane.getChildren().add(itemRect);
            room.setItemRect(itemRect);
        }
        else
        {
            tooltipText+= "\nItems: None ";
        }

        NonPlayableCharacter npc = room.getNpc();
        if(npc != null ) {
            System.out.println(npc.getName());
            tooltipText+= "\nNPC: " + npc.getName();
            Image icon = npc.getIcon();
            Rectangle npcRect = new Rectangle(35,35);
            npcRect.setFill(new ImagePattern(icon));
            roomStackPane.setAlignment(npcRect, Pos.BOTTOM_RIGHT);
            roomStackPane.getChildren().add(npcRect);
            room.setNpcRect(npcRect);
        }
        else
        {
            tooltipText+= "\nNPC: None ";
        }

        Tooltip roomInfo = new Tooltip(tooltipText);
        roomInfo.setWrapText(true);
        roomInfo.setPrefWidth(250);
        Tooltip.install(roomRect, roomInfo);
        room.setTooltip(roomInfo);

        room.setRoomStackPane(roomStackPane);
        return roomStackPane;
    }

    public Rectangle createItemIcon(Item item)
    {
            Image icon = new Image(item.getImageURL());
            Rectangle itemRect = new Rectangle(20, 20);
            itemRect.setFill(new ImagePattern(icon));
            return itemRect;
    }

    public void addItemIcon(Room room, Item item)
    {
        StackPane curStackPane = room.getRoomStackPane();
        Rectangle itemRect = createItemIcon(item);
        curStackPane.setAlignment(itemRect, Pos.TOP_LEFT);
        curStackPane.getChildren().add(itemRect);
        room.setItemRect(itemRect);
    }
    public void removeItemIcon(Room room)
    {
        StackPane curStackPane = room.getRoomStackPane();
        Rectangle curRect = room.getItemRect();
        curStackPane.getChildren().remove(curRect);
        room.setItemRect(null);
    }
    public void removeNpcIcon(Room room)
    {
        StackPane curStackPane = room.getRoomStackPane();
        Rectangle curRect = room.getNpcRect();
        curStackPane.getChildren().remove(curRect);
        room.setNpcRect(null);

    }
    public void updateTooltip(Room room){
            String tooltipText = room.getDescription();
            if(room.getItem() == null)
            {
                tooltipText+= "\nItems: None ";
            }
            else
            {
                tooltipText+= "Items: " + room.getItem().getName();

            }
            if(room.getNpc() == null)
            {
                tooltipText+= "\nNPC: None ";
            }
            else
            {
                tooltipText+= "\nNPC: " + room.getNpc().getName();

            }
            room.getTooltip().setText(tooltipText);
    }


}
