package ac.za.mandela.wrpv301.Adventure;

import ac.za.mandela.wrpv301.Items.Key;
import ac.za.mandela.wrpv301.Items.Tool;
import ac.za.mandela.wrpv301.Items.Weapon;
import ac.za.mandela.wrpv301.NPC.Enemy;
import ac.za.mandela.wrpv301.NPC.Friendly;
import ac.za.mandela.wrpv301.Room.Room;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Adventure {

    private Room start;
    private ArrayList<Room> worldRooms;

    public Room getStart() {
        return start;
    }

    public Adventure() {

        this.worldRooms = new ArrayList<Room>();
        Weapon shears = new Weapon("Shears");
        Weapon sunBeam = new Weapon("Sun Gun");
        Weapon mallets = new Weapon("Xylophone Mallets");

        Enemy sheep = new Enemy("Killer Sheep", null, shears, 30);
        Enemy troll = new Enemy("Ugly Troll", mallets, sunBeam, 50);
        Enemy boneBoy = new Enemy("Strange Talking Skeleton", null, mallets, 15);

        sheep.setNegativeResult("Oof that was not the right thing to do. You may notice a decrease in your health");
        sheep.setPositiveResult("Without his wool he is has been made");

        troll.setNegativeResult("Oof");
        troll.setPositiveResult("Yea boi");

        boneBoy.setNegativeResult("Oof");
        boneBoy.setPositiveResult("Yea boi");


        Tool torch = new Tool("torch");
        Key key = new Key("Key");
        Key pickAxe = new Key("Pick-Axe");


        Friendly witch = new Friendly("Friendly wicked witch", pickAxe, torch);
        Friendly goodTroll = new Friendly("Forlorn Troll", null, null);

        witch.setPositiveResult("Witch,\"Many thanks! I think I should grab my broom and lament elsewhere, here's a gift I am leaving behind \"");
        witch.setNegativeResult("Witch,\"What a cruel world - I guess you will struggle to keep moving... \"");


        sheep.setIcon(new Image(Adventure.class.getResourceAsStream("/Images/sheep.png")));
        troll.setIcon(new Image(Adventure.class.getResourceAsStream("/Images/troll.png")));
        goodTroll.setIcon(new Image(Adventure.class.getResourceAsStream("/Images/sad.png")));
        witch.setIcon(new Image(Adventure.class.getResourceAsStream("/Images/witch.png")));
        boneBoy.setIcon(new Image(Adventure.class.getResourceAsStream("/Images/skeleton.png")));





        this.start = new Room(null, shears, "You are standing outside along a stream, you see what looks like shears near you - maybe you should grab that?" +
                "\nYou see an entrance to a cave in front of you.", false);


        //TODO change desc
        Room room1 = new Room(null, key, "You are in a very poorly lit but you see a shingy gold key lying on the floor maybe it will" +
                " prove to be use full", false);


        Room room2 = new Room(sheep, torch, "As you enter this cavern you immediately see a sheep upright upon 2 legs looking menacingly at you! What will you do? " +
                "surely you have some kind of weapon... \n to the the north you see a door that could get you out of here...", true);

        //remove pickaxe
        Room room3 = new Room(witch, null, "As you enter the room you see an entrancingly horrendous looking witch that reassures you that she is in fact a \"good\" witch " +
                "she asks if she you have a torch she could borrow to light her cigarette whilst she laments her life's choices including a few choice hexes that were made. ( ಠ ͜ʖಠ)..." + " " +
                "looks like you can go back the way you came or proceed north", false);

        //Use Pick-Axe
        Room room4 = new Room(null, null, "The room you are now in seems desolate and reminiscent of nothing good in the world.. you see a pile of rocks " +
                "blocking what seems to be another exit to the east and a dark shadowy entrance to the east of you...", false);

        Room room5 = new Room(null, key, "You enter a room that appears to be completely barren except for all but a key... the only exits are to the west (seems to be " +
                "the same way you got in) and the south of you", true);

        Room room6 = new Room(goodTroll, sunBeam, "Upon entering the dark room you see that in the corner there appears to be a troll bride" +
                "she swiftly tells you that she is very sad due to the fact that she was ditched at the alter by her troll husband to be and warns that he's" +
                "good for nothing buns of rock might still be dwelling within this cave system. She points out one of the gifts left by a guest and suggests that it might be useful to you", false);
        //TODO Add a key to room 6
        Room room7 = new Room(troll, null, "You enter the room and see a macho man of a troll - chest puffed out ready to attack what do you do? ..", false);


        //Todo Add treasure to this room once the enemy is defeated.
        Room room8 = new Room(boneBoy, null, "Upon entering the room you see a skeleton in the corner that suddenly comes to life only to mock your life's " +
                "choices and question your own sanity and why you are in fact wondering through a system of caves all alone.. He begins to start hitting you with inappropriate " +
                "bones - this could cause some damage if you don't do something quickly! If only you picked up some kind of mallet ", false);



        //N 0
        //S 1
        //E 2
        //W 3
        key.setRoom(room2);
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
}
