package ac.za.mandela.wrpv301.NPC;

import ac.za.mandela.wrpv301.Contollers.MapController;
import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.Room.Room;


public class Friendly extends NonPlayableCharacter {
    Boolean isActive;
    Item itemNeeded;

    public Friendly(String name, Item item, String ImageURL,  Item itemNeeded) {
        super(name, item, ImageURL);
        this.isActive = true;
        this.itemNeeded = itemNeeded;
    }

    public String interact(Item item, Room room, MapController mapController) {
        if (itemNeeded.equals(item)) {
            isActive = false;
            room.setItem(this.getItem());
            room.setNpc(null);
            mapController.addItemIcon(room, this.getItem());
            mapController.removeNpcIcon(room);
            mapController.updateTooltip(room);
            item.useItem();
            room.setDescription("The "+ this.getName() + " seems to have left. Check on the map if anything was left behind");
            return this.getPositiveResult();
        } else {
            return this.getNegativeResult();

        }
    }

    public void initValues(){
        this.itemNeeded = null;
        this.isActive = true;
    }
}
