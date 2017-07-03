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

public class MorePane extends Pane {
        private ImageView icon, icon1, icon2, icon3, icon4,iconStar;

        MorePane(String iconPath, String icon1Path, String icon2Path, String icon3Path, String icon4Path,
               String title, String description, float rating) {
            icon = new ImageView(new Image(new File(iconPath).toURI().toString()));
            icon1 = new ImageView(new Image(new File(icon1Path).toURI().toString()));
            icon2 = new ImageView(new Image(new File(icon2Path).toURI().toString()));
            icon3 = new ImageView(new Image(new File(icon3Path).toURI().toString()));
            icon4 = new ImageView(new Image(new File(icon4Path).toURI().toString()));
            iconStar = new ImageView(new Image( "game" + "/" + "star.png"));
            icon.setLayoutX(0);
            icon.setLayoutY(0);
            Label labelT = new Label(title);
            labelT.setPrefSize(800, 200);
            TextArea area = new TextArea(description);
            area.setWrapText(true);
            area.setPrefSize(1200, 320);
            area.setEditable(false);
            Label labelR = new Label(String.valueOf(rating));
            labelR.setPrefSize(50, 200);
            iconStar.setLayoutX(1050);
            iconStar.setLayoutY(65);
            icon1.setLayoutX(0);
            icon1.setLayoutY(200);
            icon2.setLayoutX(300);
            icon2.setLayoutY(200);
            icon3.setLayoutX(600);
            icon3.setLayoutY(200);
            icon4.setLayoutX(900);
            icon4.setLayoutY(200);
            labelT.setLayoutX(300);
            labelT.setLayoutY(0);
            labelR.setLayoutX(1120);
            labelR.setLayoutY(0);
            labelR.setFont(Font.font("Monospace", FontWeight.BOLD, 20));
            labelT.setFont(Font.font("Monospace", FontWeight.BOLD, 18));
            area.setLayoutX(0);
            area.setLayoutY(400);
            getChildren().addAll(icon, labelT, iconStar, labelR, icon1, icon2, icon3, icon4, area);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(this, 1200, 720));
            stage.setResizable(false);
            stage.show();
        }

    MorePane(String iconPath, String icon1Path, String icon2Path, String icon3Path, String icon4Path,
             String title, String description, float rating,short year) {
        icon = new ImageView(new Image(new File(iconPath).toURI().toString()));
        icon1 = new ImageView(new Image(new File(icon1Path).toURI().toString()));
        icon2 = new ImageView(new Image(new File(icon2Path).toURI().toString()));
        icon3 = new ImageView(new Image(new File(icon3Path).toURI().toString()));
        icon4 = new ImageView(new Image(new File(icon4Path).toURI().toString()));
        iconStar = new ImageView(new Image( "game" + "/" + "star.png"));
        icon.setLayoutX(0);
        icon.setLayoutY(0);
        Label labelT = new Label(title);
        labelT.setPrefSize(500, 200);
        TextArea area = new TextArea(description);
        area.setWrapText(true);
        area.setPrefSize(1200, 320);
        area.setEditable(false);
        Label labelR = new Label(String.valueOf(rating));
        labelR.setPrefSize(50, 200);
        iconStar.setLayoutX(1050);
        iconStar.setLayoutY(65);
        icon1.setLayoutX(0);
        icon1.setLayoutY(200);
        icon2.setLayoutX(300);
        icon2.setLayoutY(200);
        icon3.setLayoutX(600);
        icon3.setLayoutY(200);
        icon4.setLayoutX(900);
        icon4.setLayoutY(200);
        labelT.setLayoutX(300);
        labelT.setLayoutY(0);
        labelR.setLayoutX(1120);
        labelR.setLayoutY(0);
        labelR.setFont(Font.font("Monospace", FontWeight.BOLD,20));
        labelT.setFont(Font.font("Monospace",FontWeight.BOLD,18));
        area.setLayoutX(0);
        area.setLayoutY(400);
        Label labelY = new Label("Year:");
        labelY.setFont(Font.font("Monospace", FontWeight.BOLD,16));
        labelY.setLayoutX(700);
        labelY.setLayoutY(90);
        Label labelYear = new Label(String.valueOf(year));
        labelYear.setLayoutX(700);
        labelYear.setLayoutY(110);
        labelYear.setFont(Font.font("Monospace", FontPosture.ITALIC,14));
        getChildren().addAll(icon, labelT,iconStar, labelR, icon1, icon2, icon3, icon4, area,labelY,labelYear);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(this,1200,720));
        stage.setResizable(false);
        stage.show();
    }
    }