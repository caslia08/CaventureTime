package ac.za.mandela.wrpv301.NPC;

import ac.za.mandela.wrpv301.Items.Item;
import javafx.scene.image.Image;

public class NonPlayableCharacter {
    private transient String name;
    private transient Item item;
    private transient String positiveResult;
    private transient String negativeResult;
    private transient Image icon;

    public NonPlayableCharacter(String name, Item item) {
        this.name = name;
        this.item = item;
    }

    public NonPlayableCharacter() {

    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getPositiveResult() {
        return positiveResult;
    }

    public void setPositiveResult(String positiveResult) {
        this.positiveResult = positiveResult;
    }

    public String getNegativeResult() {
        return negativeResult;
    }

    public void setNegativeResult(String negativeResult) {
        this.negativeResult = negativeResult;
    }
}
