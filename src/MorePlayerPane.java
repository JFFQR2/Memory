import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;

public class MorePlayerPane extends Pane {
    private ImageView icon, icon1, icon2, icon3, icon4;

    MorePlayerPane(String iconPath, String icon1Path, String icon2Path, String icon3Path, String icon4Path,
             String title, String description,short yearOfFoundation,String yearOfDisintegration,String country,String composition,String album) {
        icon = new ImageView(new Image(new File(iconPath).toURI().toString()));
        icon1 = new ImageView(new Image(new File(icon1Path).toURI().toString()));
        icon2 = new ImageView(new Image(new File(icon2Path).toURI().toString()));
        icon3 = new ImageView(new Image(new File(icon3Path).toURI().toString()));
        icon4 = new ImageView(new Image(new File(icon4Path).toURI().toString()));
        icon.setLayoutX(0);
        icon.setLayoutY(0);
        Label labelT = new Label(title);
        labelT.setPrefSize(300, 200);
        TextArea area = new TextArea(description);
        area.setWrapText(true);
        area.setPrefSize(1200, 320);
        area.setEditable(false);
        Label labelY = new Label("Years:" + yearOfFoundation + "-" + yearOfDisintegration);
        labelY.setLayoutX(1000);
        labelY.setLayoutY(100);
        labelY.setFont(Font.font("Monospace",FontWeight.BOLD,18));
        Label labelC = new Label("Country: " + country);
        labelC.setLayoutX(1000);
        labelC.setLayoutY(80);
        labelC.setFont(Font.font("Monospace",FontWeight.BOLD,18));
        Label labelCom = new Label("Composition:");
        labelCom.setLayoutX(500);
        labelCom.setLayoutY(5);
        labelCom.setFont(Font.font("Monospace",FontWeight.BOLD,16));
        Label labelComposition = new Label(composition);
        labelComposition.setLayoutX(500);
        labelComposition.setLayoutY(40);
        Label labelA = new Label("Albums:");
        labelA.setLayoutX(780);
        labelA.setLayoutY(5);
        labelA.setFont(Font.font("Monospace",FontWeight.BOLD,16));
        Label labelAlbums = new Label(album);
        labelAlbums.setLayoutX(770);
        labelAlbums.setLayoutY(40);
        labelComposition.setFont(Font.font("Monospace", FontPosture.ITALIC,14));
        icon1.setLayoutX(0);
        icon1.setLayoutY(200);
        icon2.setLayoutX(300);
        icon2.setLayoutY(200);
        icon3.setLayoutX(600);
        icon3.setLayoutY(200);
        icon4.setLayoutX(900);
        icon4.setLayoutY(200);
        labelT.setLayoutX(200);
        labelT.setLayoutY(0);
        labelT.setFont(Font.font("Monospace",FontWeight.BOLD,18));
        area.setLayoutX(0);
        area.setLayoutY(400);
        getChildren().addAll(icon, labelT,labelY,labelC,labelCom,labelComposition, icon1, icon2, icon3, icon4, area,labelA,labelAlbums);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(this,1200,720));
        stage.setResizable(false);
        stage.show();
    }
}