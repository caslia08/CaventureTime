package ac.za.mandela.wrpv301.Contollers;


//TODO FIx scrolling
import ac.za.mandela.wrpv301.Adventure.AdventureGame;
import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.Player.Player;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private AdventureGame newGame;
    private Player player;
    public MenuItem saveGame;
    public MenuItem loadGame;
    public MenuItem exitGame;
    public MenuItem controls;
    public ProgressBar playerHealth;
    public TextArea txtMain;
    public TextField txtInput;
    public ListView<Item> lbxItems;
    private ObservableList<Item> observableItems;
    public Pane mapPane;
    public Text mainLabel;
    public AnchorPane mainAnchorPane;
    public VBox vboxContainer;
    public Rectangle playerIcon;
    public Pane panePopup;
    public ImageView itemImageView;
    public Label lblItemName;
    public Label lblDescription;

    public Stage stage;

    @FXML
    void exitGameClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            stage = (Stage) vboxContainer.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BackgroundImage mainBI= new BackgroundImage(new Image(GameController.class.getResourceAsStream("/Images/Background.jpg")),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        vboxContainer.setBackground(new Background(mainBI));


    }

    @FXML
    void loadGameClicked(ActionEvent event) {
        stage = (Stage) vboxContainer.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jungle", "Adventure"));

        File chosenFile = fileChooser.showOpenDialog(stage);

        if(chosenFile != null)
        {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(chosenFile))) {
                newGame = (AdventureGame) inputStream.readObject();
                player = newGame.getPlayer();
                txtMain.setText(newGame.look());
                setUpInventory();
                newGame.reDrawMap(playerIcon);
                System.out.println("Load worked");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    @FXML
    void saveGameClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jungle", "Adventure"));
        Stage stage = (Stage) vboxContainer.getScene().getWindow();
        File newFile = fileChooser.showSaveDialog(stage);
        if(newFile != null)
        {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(newFile))) {
                outputStream.writeObject(newGame);
            } catch (Exception exc)
            {exc.printStackTrace();}
        }
    }

    @FXML
    void controlsClicked(ActionEvent event) {
        //New window with controls
    }

    public void onEnter(ActionEvent actionEvent){
        String text = txtMain.getText();
        String input = txtInput.getText().toLowerCase();

        if (input.equals("help")) {
            txtMain.setText("In order to choose a direction in which you want to go simply type  \"north\" , \"south\", \"east\"" +
                    "\"west\". \n\nIn the top right corner you will see your Inventory which contains tools and weapons " +
                    "throughout out the game you will encounter enemies and other strange characters that you might require you to utilise either " +
                    "your tools or weapons to defend yourself. Type  \"use\" followed by the item name as it appears in your inventory in order to make use " +
                    "of either item type. \n To get rid of an item or pick it you will type \"drop\" or \"grab\" followed by the item name respectively. "+
                    "\n\nType \"start\" to begin your adventure");

        } else if (input.equals("clear")) {
            txtMain.clear();
        } else if (input.equals("start")) {
            if(this.newGame == null) {
                this.newGame = new AdventureGame(mapPane, playerIcon);
                newGame.startGame();
                player = newGame.getPlayer();
                setUpInventory();
                txtMain.setText(newGame.look());
            }
            else {
                text = text + "\n\n" + "¯\\_( ͡° ͜ʖ ͡°)_/¯ Uhm I we already have something going... You may save and exit if you like";
                txtMain.setText(text);
            }
        } else if (input.equals("look")) {
            text = text + "\n" + newGame.look();
            txtMain.setText(text);
        } else if(newGame != null) {
            txtMain.setText(newGame.look());
           // text = text + "\n\n" + newGame.processCommand(txtInput.getText());
            text = newGame.processCommand(txtInput.getText());

            txtMain.setText(text);

        }
        else {
            text = text + "\n\n" + "Please start a game before you tell me what to do\n ̿̿ ̿̿ ̿̿ ̿'̿'\\̵͇̿̿\\з= ( ▀ ͜͞ʖ▀) =ε/̵͇̿̿/’̿’̿ ̿ ̿̿ ̿̿ ̿̿ ";
            txtMain.setText(text);
        }
        txtMain.setScrollTop(Double.MAX_VALUE);
        txtInput.clear();
        updateHealth();
        updateInventory();

    }

    private void updateHealth()
    {
        playerHealth.setProgress(player.getHealth()/100);
    }

    public void setUpInventory()
    {
        Callback<Item, Observable[]> extractor = item -> {
            return new Observable[]{item.nameProperty()};
        };
        observableItems = FXCollections.observableArrayList(extractor);
        observableItems.addAll(player.getInventory());
        lbxItems.setItems(observableItems);

        //TODO add pop up for visual
        lbxItems.setOnMouseClicked(event -> {
            Item curItem = lbxItems.getSelectionModel().getSelectedItem();
            initData(curItem);
            stage = (Stage) panePopup.getScene().getWindow();
            stage.show();
        });
    }

    public void initData(Item item)
    {
        lblItemName = new Label(item.getName());
        lblDescription = new Label(item.getDesc());
        itemImageView = new ImageView(item.getImage());
    }


    public void updateInventory()
    {
        if(newGame.pickedUp() != null) {
            observableItems.add(newGame.pickedUp());
        }

        if(newGame.dropped() != null) {
            observableItems.remove(newGame.dropped());
            System.out.println("dropped");
        }

        Item itemTemp = null;
        for(Item item:  observableItems)
        {
            if(item.getUses() == 0)
            {
                itemTemp = item;
            }
        }
        if(itemTemp != null)
            observableItems.remove(itemTemp);
    }



}
