package ac.za.mandela.wrpv301.Adventure;

import ac.za.mandela.wrpv301.Items.Key;
import ac.za.mandela.wrpv301.Items.Tool;
import ac.za.mandela.wrpv301.Items.Weapon;
import ac.za.mandela.wrpv301.NPC.Enemy;
import ac.za.mandela.wrpv301.NPC.Friendly;
import ac.za.mandela.wrpv301.Room.Room;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Adventure implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient Room start;
    private transient ArrayList<Room> worldRooms;

    public Room getStart() {
        return start;
    }

    public Adventure() {
        this.worldRooms = new ArrayList<Room>();
        Weapon shears = new Weapon("Shears", "A universal tool that is primarily used for cutting down demonic sheep to size");
        Weapon sunRay = new Weapon("Sun-Ray", "This tool provides a bean of pure sunlight useful for any load-shedding mishaps if you have solar panels and potentially good for combating trolls ");
        Weapon mallets = new Weapon("Mallets", "This lovely traditional music item can be used to play a xylophone and perhaps you favourite Boney M tune ;)");


        Enemy sheep = new Enemy("Killer Sheep", null, "/Images/sheep.png", shears, 30);
        Enemy troll = new Enemy("Ugly Troll", mallets,"/Images/troll.png", sunRay, 50);
        Enemy boneBoy = new Enemy("Strange Talking Skeleton", null,"/Images/skeleton.png", mallets, 15);

        sheep.setNegativeResult("Oof that was not the right thing to do. You may notice a decrease in your health");
        sheep.setPositiveResult("Without his wool he is has been made. You are now safe to leave! ");

        troll.setNegativeResult("He slams you on the head. You may perish if you do not attack!");
        troll.setPositiveResult("The sun-ray has turned the troll to stone - guess that's one way of getting rock hard abs. You are now free to move on");

        boneBoy.setNegativeResult("You take a blow after being battered in the ribs with ribs. Better act fast - if you can!");
        boneBoy.setPositiveResult("By playing the most annoying pop songs of recent times the reverberations have manage to shatter the bones of your " +
                "captor. It only his chattering skull that remains intact.");

        Tool torch = new Tool("Torch", "The red glowing torch can serve many purposes sometimes the most useful will be the most unusual");
        Tool mirror = new Tool("Mirror", "Magic Mirror makes he/she who possesses this feel like a million beautiful bucks");

        Key key = new Key("Key", "This shiny golden key can prove useful for opening any locked passage ways be careful not to lose it or use it unnecessarily");
        Key key1 = new Key("RustyKey", "This rusted key doesn't look like it opens anything useful");
        Key pickAxe = new Key("Pick-Axe", "Pick-Axe is a generally T-shaped hand tool used for prying and removing obstacles");


        Friendly witch = new Friendly("Friendly wicked witch", pickAxe, "/Images/witch.png", torch);
        Friendly goodTroll = new Friendly("Forlorn Troll", key1, "/Images/sad.png", mirror);

        witch.setPositiveResult("The witch thanks you as she leaves the room to light up as she says the forest is a smoke free zone. " +
                "She has left something behind for you, that will help you in your journey.");
        witch.setNegativeResult("Witch, what a cruel world guess you will struggle to keep moving.");

        goodTroll.setPositiveResult("Upon receiving the mirror and looking at her reflection " +
                "she is ecstatic and decides that it is time to start swiping right again! ");
        goodTroll.setNegativeResult("The troll seems really disappointed... oh well poor thing.");

        this.start = new Room(null, shears, "You are standing outside and have no recollective memory of where you are or how you got here" +
                "\nYou see an entrance of some sort into a forest right in front of you.", false);


        Room room1 = new Room(null, key, "You approach a small clearing with nothing but a lone key lying in close proximity – " +
                "you see a random door standing straight ahead in the clearing, " +
                " that key might prove to be use full. You also see a passage way in the trees to your east.", false);


        Room room2 = new Room(sheep, torch, "As you enter this room you immediately see a sheep upright upon 2 legs looking menacingly at you! What will you do? " +
                "surely you have some kind of weapon... \n to the south you see a door that could get you out of here... but the sheep won’t let you leave", true);

//remove pickaxe
        Room room3 = new Room(witch, null, "As the passage way comes to an end, you see an entrancingly horrendous " +
                "looking witch that reassures you that she is in fact a \"good\" witch she asks if she you have a torch she could borrow to light her cigarette whilst she laments her life's choices including a few choice hexes that were made. ( ಠ ͜ʖಠ)..." + " " +
                "looks like you can go back the way you came or proceed north", false);

//Use Pick-Axe
        Room room4 = new Room(null, null, "The clearing you stumbled upon seems to baren and the only way to proceed seems to be blocked by a pile of rocks", false);

        Room room5 = new Room(goodTroll, mirror, "Upon entering a beautifully decorated clearing you see that in the corner there appears to be a troll bride" +
                " she swiftly tells you that she is very sad due to the fact that she was ditched at the alter by her troll husband to be and warns that his " +
                "good for nothing buns of rock might still be dwelling within this cave system. She looks to you for reassurance of her beauty and " +
                "will not let you pass until you convince her of her beauty. ", true);

        Room room6 = new Room(null, sunRay, "Upon entering this room there seems to be a gift that was left behind from the wedding reception. " +
                "It has the label \"for my arch nemesis – curse you for stealing the love of my life\"… hopefully someone broke the news to him or her", true);
                Room room7 = new Room(troll, null, "You enter the room and see a macho man of a troll - chest puffed out ready to attack, " +
                        "this must be the groom. He looks like he is ready to attack! ..", false);

        Room room8 = new Room(boneBoy, null, "Upon entering the room you see a skeleton in the corner that suddenly comes to life only to mock your life's " +
                "choices and question your own sanity and why you are in fact wondering through a system of forest all alone fighting sheep and stealing wedding gifts" +
                "he reveals that he teleported you here and wiped you memory so that he may take your fleshy body to use as his own .. He begins to start hitting you with inappropriate " +
                "bones in order to render you unconscious  - this could cause some damage if you don't do something quickly! If only you picked up some kind of mallet ", false);


        //N 0
        //S 1
        //E 2
        //W 3
        key.setRoom(room2);
        key1.setRoom(room6);
        pickAxe.setRoom(room5);
        start.setExit(room1, 0);

        room1.setExit(room2, 0);
        room1.setExit(start, 1);
        room1.setExit(room3,2);

        //Sheep
        room2.setExit(room1,1);

        //Witch
        room3.setExit(room1, 3);
        room3.setExit(room4, 0);

        //Boulder room
        room4.setExit(room3,1);
        room4.setExit(room5,2);

        //Has key
        room5.setExit(room4, 3);
        room5.setExit(room6, 1);

        //Bride Troll
        room6.setExit(room7, 1);
        room6.setExit(room5, 0);

        //Groom Troll
        room7.setExit(room6, 0);
        room7.setExit(room8, 3);

        room8.setExit(room7, 2);

        worldRooms.add(start);
        worldRooms.add(room1);
        worldRooms.add(room2);
        worldRooms.add(room3);
        worldRooms.add(room4);
        worldRooms.add(room5);
        worldRooms.add(room6);
        worldRooms.add(room7);
        worldRooms.add(room8);
    }

    public ArrayList<Room> getWorldRooms() {
        return this.worldRooms;
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(start);
        outputStream.writeObject(worldRooms);
    }

    private void readObject(ObjectInputStream inputStream)  throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        initValues();
        this.start = (Room) inputStream.readObject();
        this.worldRooms= (ArrayList<Room>) inputStream.readObject();
    }

    private void initValues(){
       this.start = new Room();
       this.worldRooms = new ArrayList<>();
    }



}
