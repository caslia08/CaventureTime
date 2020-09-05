package ac.za.mandela.wrpv301.Room;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;


public class Connection {

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
      connRect.setFill(Color.BLACK);
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
}
