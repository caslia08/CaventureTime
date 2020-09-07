package ac.za.mandela.wrpv301.Contollers;

import ac.za.mandela.wrpv301.Adventure.AdventureGame;
import ac.za.mandela.wrpv301.Items.Item;
import ac.za.mandela.wrpv301.Player.Player;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
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
    public ImageView itemImageView;
    public Label lblItemName;
    public Text txtDesc;
    public Boolean restarted;
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
        this.restarted = false;
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
                reset();
                newGame = (AdventureGame) inputStream.readObject();
                player = newGame.getPlayer();
                txtMain.setText(newGame.look());
                setUpInventory();
                updateHealth();
                mapPane.getChildren().clear();
                newGame.setMapPane(mapPane);
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
                System.out.println("Save Successful");
            } catch (Exception exc)
            {exc.printStackTrace();}
        }
    }

    @FXML
    void controlsClicked(ActionEvent event) {
        Stage controlStage = controlStage(stage);
        controlStage.show();
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
                if(restarted) {
                    reset();

                }else {
                    this.newGame = new AdventureGame(mapPane, playerIcon);
                    newGame.startGame();
                    player = newGame.getPlayer();
                    setUpInventory();
                }

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
        checkGame();

    }

    private void checkGame(){
        newGame.checkGame();

        if(newGame.getGameOver() == true)
        {
            String text = "YOU HAVE DIED!!!! :( \nType \\\"start\\\" to begin a new adventure\" \nTry not to die this time ;)";
            txtMain.setText(text);
            player= new Player();
            newGame = null;
            restarted = true;
        }
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

        lbxItems.setOnMouseClicked(event -> {
            Item curItem = lbxItems.getSelectionModel().getSelectedItem();
            Stage itemStage = itemStage(stage, curItem);
            itemStage.show();
        });
    }

    private void restart()
    {
            mapPane.getChildren().clear();
            this.newGame = new AdventureGame(mapPane, playerIcon);
            newGame.startGame();
            player = newGame.getPlayer();
            setUpInventory();
    }

    private void reset()
    {
        mapPane.getChildren().clear();
        newGame =null;
        player = null;
    }

    private Stage itemStage(Stage owner, Item item)
    {
        initData(item);

        Stage stage = new Stage();
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #91b59b");
        HBox hBoxMainContainer = new HBox();
        VBox imageInfo = new VBox();
        VBox itemInfo = new VBox();

        Label lblHeading = new Label("Item Description");
        lblHeading.setStyle("-fx-font-size: 24px");
        itemImageView.setFitHeight(250);
        itemImageView.setFitWidth(250);
        itemImageView.setPreserveRatio(true);
        lblItemName.setStyle("-fx-font-size: 30px");
        txtDesc.setWrappingWidth(250);

        itemInfo.setPrefWidth(250);
        imageInfo.getChildren().addAll(itemImageView, lblItemName);
        imageInfo.setAlignment(Pos.CENTER);
        itemInfo.getChildren().addAll(lblHeading, txtDesc);
        itemInfo.setAlignment(Pos.CENTER);


        hBoxMainContainer.getChildren().addAll(imageInfo, itemInfo);
        root.getChildren().add(hBoxMainContainer);

        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);
        stage.setTitle("Item Descriptions ");
        stage.setWidth(600);
        stage.setHeight(350);
        return stage;
    }

    private Stage controlStage(Stage owner)
    {
        Stage stage = new Stage();
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #91b59b");
        HBox hBoxMainContainer = new HBox();
        Pane controls = new Pane();
        Pane info = new Pane();
        Label lblTitle = new Label("Control List");
        lblTitle.setStyle("-fx-font-size: 30px");

        Label lblControls = new Label(" grab <item name>" +
                "\n use <item name>" +
                "\n drop <item name>" +
                "\n inspect <item name>" +
                "\n look " +
                "\n north " +
                "\n south " +
                "\n east " +
                "\n west");
        controls.getChildren().add(lblControls);


        Label lblInfo = new Label("grabs and item and adds it to inventory" +
                "\n uses item, includes using keys, weapons and tools" +
                "\n drop item if necessary" +
                "\n let you look at the item closer and get the description" +
                "\n displays current room description " +
                "\n moves player north " +
                "\n moves player south " +
                "\n moves player east " +
                "\n moves player west");

        lblInfo.setStyle("-fx-font-size: 20px");
        lblControls.setStyle("-fx-font-size: 20px");

        info.getChildren().addAll(lblInfo);
        hBoxMainContainer.setAlignment(Pos.CENTER);
        hBoxMainContainer.getChildren().addAll(lblControls, info);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(lblTitle, hBoxMainContainer);
        root.getChildren().addAll(vBox);
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);
        stage.setTitle("Game Controls");
        stage.setWidth(900);
        stage.setHeight(350);
        return stage;
    }
    public void initData(Item item)
    {
        lblItemName = new Label(item.getName());
        txtDesc = new Text(item.getDesc());
        itemImageView = new ImageView(new Image(item.getImageURL()));
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
        if(itemTemp != null) {
            observableItems.remove(itemTemp);
            player.getInventory().remove(itemTemp);
        }
    }





}
