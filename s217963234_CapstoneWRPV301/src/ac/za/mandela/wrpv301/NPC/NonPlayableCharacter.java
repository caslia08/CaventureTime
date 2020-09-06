package ac.za.mandela.wrpv301.NPC;

import ac.za.mandela.wrpv301.Items.Item;
import javafx.scene.image.Image;

import java.io.*;

public class NonPlayableCharacter implements Serializable {
    private transient String name;
    private transient Item item;
    private transient String positiveResult;
    private transient String negativeResult;
    private transient String ImageURL = "";


    public NonPlayableCharacter(String name, Item item, String ImageURL) {
        this.name = name;
        this.item = item;
        this.ImageURL = ImageURL;
    }

    public NonPlayableCharacter() {

    }

    public Image getIcon() {
        Image icon = new Image(ImageURL);
        return icon;
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

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeUTF(name);
        outputStream.writeObject(item);
        outputStream.writeUTF(positiveResult);
        outputStream.writeUTF(negativeResult);
        outputStream.writeUTF(ImageURL);
        if(this instanceof Friendly){
            outputStream.writeBoolean(((Friendly) this).isActive);
            outputStream.writeObject(((Friendly) this).itemNeeded);
        }else {
            outputStream.writeObject(((Enemy) this).weakness);
            outputStream.writeInt(((Enemy) this).damage);
            outputStream.writeBoolean(((Enemy) this).isAlive);

        }
    }
    private void readObject(ObjectInputStream inputStream)  throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        initParentValues();
        this.name = inputStream.readUTF();
        this.item = (Item) inputStream.readObject();
        this.positiveResult = inputStream.readUTF();
        this.negativeResult = inputStream.readUTF();
        this.ImageURL = inputStream.readUTF();
        if(this instanceof Friendly){
            ((Friendly) this).isActive =  inputStream.readBoolean();
            ((Friendly) this).itemNeeded = (Item) inputStream.readObject();
        }else {
            ((Enemy) this).weakness = (Item) inputStream.readObject();
            ((Enemy) this).damage =  inputStream.readInt();
            ((Enemy) this).isAlive =  inputStream.readBoolean();
        }
    }
    private void initParentValues(){
        this.name = "";
        this.positiveResult ="";
        this.negativeResult = "";
        this.ImageURL = "";
        if(this instanceof Friendly){
            ((Friendly) this).initValues();
        }else{
            ((Enemy) this).initValues();
        }

    }
}
