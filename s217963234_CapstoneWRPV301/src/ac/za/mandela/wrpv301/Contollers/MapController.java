package ac.za.mandela.wrpv301.Contollers;

import ac.za.mandela.wrpv301.Player.Player;
import ac.za.mandela.wrpv301.Room.Connection;
import ac.za.mandela.wrpv301.Room.Room;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;

public class MapController {

    private Player player;
    public Pane pane;
    public Rectangle playerIcon;
    public Group group;
    TranslateTransition tl;
    ArrayList<Room> rooms;
    private ArrayList<StackPane> roomStackPanes = new ArrayList<StackPane>();
    private ArrayList<Connection> pathConnection = new ArrayList<Connection>();

    public MapController(Pane pane, Rectangle playerIcon) {
        this.playerIcon = playerIcon;
        //player.setPlayerIcon(playerIcon);
        playerIcon.setVisible(true);
        this.pane = pane;
        Image icon = new Image(MapController.class.getResourceAsStream("/Images/female.png"));
        playerIcon.setWidth(30);
        playerIcon.setHeight(30);
        playerIcon.setFill(new ImagePattern(icon));
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
    public void move(int direction)
    {
       this.tl = new TranslateTransition();
       this.getDir(direction, tl);
       tl.setDuration(Duration.seconds(1));
       tl.setNode(playerIcon);
       tl.playFromStart();
       this.group = new Group();
       group.getChildren().addAll(playerIcon);
       pane.getChildren().add(playerIcon);
       updateMap();
    }
    private TranslateTransition getDir(int dir, TranslateTransition translateTransition)
    {

        switch (dir) {
            case 0: //NORTH
            {
                translateTransition.setByY(-140);
                translateTransition.setByX(0);
            }
            break;
            case 1://SOUTH
            {
                translateTransition.setByY(140);
                translateTransition.setByX(0);
            }
            break;
            case 2://EAST
            {
                translateTransition.setByY(0);
                translateTransition.setByX(180);
            }
            break;
            case 3://WEST
            {
                translateTransition.setByY(0);
                translateTransition.setByX(-180);
            }
            break;
        }
        return translateTransition;
    }
    public void drawAllRooms()
    {
        //Gets the first room for me and draws and places it.
        Room curRoom = rooms.get(0);
        curRoom.setXY(15,310);
        StackPane newRoom = curRoom.getRoomStackPane();
        newRoom.setLayoutX(curRoom.getX());
        newRoom.setLayoutY(curRoom.getY());
        pane.getChildren().add(newRoom);
        roomStackPanes.add(newRoom);
        pane.getChildren().get(pane.getChildren().size() - 1).toBack();
        for(int i = 0; i < rooms.size(); i++)
        {
            curRoom = rooms.get(i);
            drawDirectedRoom(curRoom);
        }
        updateConnections();
    }

    private void drawDirectedRoom(Room room)
    {
        Room[] exits = room.getExits();

        for(int i =0; i < exits.length; i++)
        {
            Room curRoom = exits[i];
            if(curRoom != null)
                if(curRoom.getDrawn() == false) {
                    determineRoomDirection(curRoom, room.getX(), room.getY(), i);
                    createPlaceConnection(pane, room, curRoom, i);
                }
        }
    }

    private void determineRoomDirection(Room room, double x, double y, int dir)
    {
        StackPane newRoom = room.getRoomStackPane();
        newRoom.setVisible(false);
        switch (dir) {
            case 0: //NORTH
            {
                room.setXY(x, y-140);
                newRoom.setLayoutX(room.getX());
                newRoom.setLayoutY(room.getY());
                pane.getChildren().add(newRoom);
            }
            break;
            case 1://SOUTH
            {
                room.setXY(x, y+140);
                newRoom.setLayoutX(room.getX());
                newRoom.setLayoutY(room.getY());
                pane.getChildren().add(newRoom);
            }
            break;
            case 2://EAST
            {
                room.setXY(x +180 , y);
                newRoom.setLayoutX(room.getX());
                newRoom.setLayoutY(room.getY());
                pane.getChildren().add(newRoom);
            }
            break;
            case 3://WEST
            {
                room.setXY(x -180 , y);
                newRoom.setLayoutX(room.getX());
                newRoom.setLayoutY(room.getY());
                pane.getChildren().add(newRoom);
            }
            break;
        }
        roomStackPanes.add(newRoom);
    }

    private void updateMap()
    {
        updateRooms();
        updateConnections();
    }

    private void updateRooms()
    {
        if(player.getCurrentLocation().getDiscovered() == true)
        {
            player.getCurrentLocation().getRoomStackPane().setVisible(true);
        }
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

}
