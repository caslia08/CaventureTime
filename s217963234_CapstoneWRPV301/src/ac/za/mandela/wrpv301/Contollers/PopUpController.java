package ac.za.mandela.wrpv301.Contollers;

import ac.za.mandela.wrpv301.Items.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpController {

    @FXML
    private AnchorPane popUpAnchor;

    @FXML
    private ImageView itemImageView;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblDescription;

    public void initData(Item item)
    {
        this.lblItemName.setText(item.getName());
        this.lblDescription.setText(item.getDesc());
        this.itemImageView.setImage(item.getImage());
    }

    public Stage getStage()
    {
        Stage stage = (Stage) popUpAnchor.getScene().getWindow();
        return stage;
    }

}
