package ac.za.mandela.wrpv301.Room;
import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.NPC.NonPlayableCharacter;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Connection implements Serializable {

    private transient Room[] connRooms;
    private transient StackPane connStackPane;
    private transient Rectangle connRect;
    private transient Rectangle connLock;
    private transient Image iconImage;
    private transient int dir;
    private transient ArrayList<StackPane> connectionArray = new ArrayList<StackPane>();
    private transient double x;
    private transient double y;

    public Connection(Room room1, Room room2, int dir)
    {
      this.connStackPane = new StackPane();
      this.connRooms = new Room[2];
      connRooms[0] = room1;
      connRooms[1] = room2;
      this.dir = dir;
      createRoom();
      connRect.setFill(Color.rgb(145,181,155));
    }

    public Room getFirstRoom () {
        return connRooms[0];
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private void createRoom()
    {
        if(dir == 0 || dir ==1)
        {
            this.connRect = new Rectangle(20,120);
            this.x = ((connRooms[0].getX() + connRooms[1].getX()) /2) +25;
            this.y = ((connRooms[0].getY() + connRooms[1].getY()) /2) -30;
        }
        else {
            this.connRect = new Rectangle(120,20);
            this.x = ((connRooms[0].getX() + connRooms[1].getX()) /2) - 30;
            this.y = ((connRooms[0].getY() + connRooms[1].getY()) /2) + 25;
        }
    }

    public StackPane getConnStackPane() {
        return connStackPane;
    }

    private Rectangle createLock()
    {
        Rectangle lockRect = new Rectangle(20, 20);
        iconImage = new Image(Connection.class.getResourceAsStream("/Images/lock.png"));
        ImagePattern icon = new ImagePattern(iconImage);
        lockRect.setFill(icon);
        return lockRect;
    }

    private void appendLock()
    {
        this.connLock = createLock();
        this.connStackPane.setAlignment(connLock, Pos.CENTER);
        this.connStackPane.getChildren().add(connLock);
    }

    public StackPane createConnection()
    {
        this.connStackPane.getChildren().add(connRect);
        if(connRooms[1].getIsLocked() == true)
        {
            appendLock();
        }
        connectionArray.add(connStackPane);
        return connStackPane;
    }

    public void checkLock(Room r1, Room r2)
    {
        if(this.connRooms[0].equals(r2) && this.connRooms[1].equals(r1))
        {
            this.getConnStackPane().getChildren().remove(connLock);
        }
    }

    public ArrayList<StackPane> getConnectionArray() {
        return connectionArray;
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(connRooms);
        outputStream.writeObject(connStackPane);
        outputStream.writeObject(connRect);
        outputStream.writeObject(connLock);
        outputStream.writeObject(iconImage);
        outputStream.writeInt(dir);
        outputStream.writeObject(connectionArray);
        outputStream.writeObject(x);
        outputStream.writeObject(y);
    }
    private void readObject(ObjectInputStream inputStream)  throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        initValues();
        this.connRooms= (Room[]) inputStream.readObject();
        this.connStackPane= (StackPane) inputStream.readObject();
        this.connRect= (Rectangle) inputStream.readObject();
        this.connLock= (Rectangle) inputStream.readObject();
        this.iconImage=  (Image) inputStream.readObject();
        this.dir = inputStream.readInt();
        this.connectionArray= (ArrayList<StackPane>) inputStream.readObject();
        this.x= inputStream.readDouble();
        this.y= inputStream.readDouble();
    }
    private void initValues(){

        this.connStackPane = new StackPane();
        this.connRooms = new Room[2];
        connRooms[0] = null;
        connRooms[1] = null;
        createRoom();
        connRect.setFill(Color.BLACK);
    }
}
