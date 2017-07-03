import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class GamePane extends ConstructPane {
    private String title,description,iconPath,iconPath1,iconPath2,iconPath3,iconPath4;
    FlowPane flowPane;
    private ImageView icon,iconStar;
    private float rating;
    private Game main = new Game();
    private  boolean isNes=false,isSnes=false,isN64=false,isSwitch=false,isNWii=false,isNWiiU=false,
            isPC=false,isXbox=false,isXbox360=false,isXboxOne=false,isPs=false,isPs2=false, isPs3 =false,
            isPs4=false,isGenesis=false;
    boolean[] isPlatforms = {isNes=false,isSnes=false,isN64=false,isNWii=false,isNWiiU=false,
            isSwitch=false,isPC=false,isGenesis=false,isPs=false,isPs2=false,isPs3=false,isPs4=false,
            isXbox=false,isXbox360=false,isXboxOne=false};
    GamePane(){
        flowPane = new FlowPane();
        flowPane.setLayoutX(155);
        flowPane.setLayoutY(0);
        flowPane.setPrefSize(645,100);}

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
        icon = new ImageView(new Image(new File(iconPath).toURI().toString()));
        icon.setLayoutX(5);
        icon.setLayoutY(5);
        getChildren().add(icon);
    }

    public void setIconPath1(String iconPath1) {
        this.iconPath1 = iconPath1;
    }

    public void setIconPath2(String iconPath2) {
        this.iconPath2 = iconPath2;
    }

    public void setIconPath3(String iconPath3) {
        this.iconPath3 = iconPath3;
    }

    public void setIconPath4(String iconPath4) {
        this.iconPath4 = iconPath4;
    }

    GamePane(String iconPath, String iconPath1, String iconPath2, String iconPath3, String iconPath4, String title, String description, float rating){
        icon = new ImageView(new Image(new File(iconPath).toURI().toString()));
        iconStar = new ImageView(new Image( "game" + "/" + "star.png"));
        this.title=title;
        this.description=description;
        this.rating=rating;
        this.iconPath=iconPath;
        this.iconPath1=iconPath1;
        this.iconPath2=iconPath2;
        this.iconPath3=iconPath3;
        this.iconPath4=iconPath4;
        icon.setLayoutX(5);
        icon.setLayoutY(5);
        iconStar.setLayoutX(650);
        iconStar.setLayoutY(70);
        flowPane = new FlowPane();
        flowPane.setLayoutX(155);
        flowPane.setLayoutY(0);
        flowPane.setPrefSize(645,100);
        Label labelT = new Label(title);
        labelT.setFont(javafx.scene.text.Font.font(18));
        labelT.setPrefSize(450,200);
        TextArea area = new TextArea(description);
        area.setWrapText(true);
        area.setPrefSize(800,200);
        area.setEditable(false);
        Label labelR = new Label(String.valueOf(rating));
        labelR.setFont(Font.font("Monospace",FontPosture.ITALIC,24));
        labelR.setPrefSize(100,200);
        labelT.setLayoutX(185);
        labelT.setLayoutY(5);
        labelR.setLayoutX(705);
        labelR.setLayoutY(5);
        area.setLayoutX(0);
        area.setLayoutY(205);
        Button buttonDesc = new Button("Read more");
        Button buttonEdit = new Button("Edit");
        buttonDesc.setLayoutX(700);
        buttonDesc.setLayoutY(410);
        buttonEdit.setLayoutX(5);
        buttonEdit.setLayoutY(410);
        buttonEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                edit();
            }
        });
        buttonDesc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new MorePane(iconPath,iconPath1,iconPath2,iconPath3,iconPath4,title,description,rating);
            }
        });

        getChildren().addAll(icon,labelT,iconStar,labelR,area,buttonDesc,buttonEdit,flowPane);
        main.platformsInit(isPlatforms,main.platforms,flowPane,"game","jpg");
    }
    private void edit(){
        TextField[] textFields = new TextField[7];
        Button[] buttons = new Button[6];
        CheckBox[] checkBoxes = new CheckBox[isPlatforms.length];
        TextArea area = new TextArea();
        Stage stage = new Stage();
        main.GUI(textFields,buttons,checkBoxes,area,"Edit","Edit game in the library",stage,main.platforms);
        textFields[2].setText(iconPath);
        textFields[3].setText(iconPath1);
        textFields[4].setText(iconPath2);
        textFields[5].setText(iconPath3);
        textFields[6].setText(iconPath4);
        textFields[0].setText(title);
        textFields[1].setText(String.valueOf(rating));
        area.setText(description);
        String currentDirectory=main.homeDirectory+File.separator+textFields[0].getText();
        String currentTitle=textFields[0].getText();
        File[] files = new File(currentDirectory).listFiles();
        main.setCheckBoxSelected(files,main.platforms,checkBoxes);
        buttons[0].setTooltip(new Tooltip("Icon resolution - 150x200"));
        buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.chooser(main.chooser,main.chooserStage,textFields[2]);
            }
        });
        buttons[1].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.chooser(main.chooser,main.chooserStage,textFields[3]);
            }
        });
        buttons[2].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.chooser(main.chooser,main.chooserStage,textFields[4]);
            }
        });
        buttons[3].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[3].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.chooser(main.chooser,main.chooserStage,textFields[5]);
            }
        });
        buttons[4].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[4].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.chooser(main.chooser,main.chooserStage,textFields[6]);
            }
        });
        buttons[5].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (textFields[0].getText().isEmpty()||textFields[1].getText().isEmpty()||area.getText().isEmpty()){
                    main.check("title or rating or description");
                    return;
                } if (textFields[2].getText().isEmpty()||textFields[3].getText().isEmpty()||textFields[4].getText().isEmpty()||textFields[5].getText().isEmpty()||textFields[6].getText().isEmpty()){
                    main.check("icons");
                    return;
                }
                main.deleteIfCheckBoxNotSelected(files,main.platforms,checkBoxes,currentDirectory);
                String directory = main.homeDirectory + File.separator + textFields[0].getText();
                if (currentTitle.equals(textFields[0].getText())) {
                    main.change(iconPath, textFields[2].getText(), directory + File.separator + textFields[0].getText(), "icon");
                    main.change(iconPath1, textFields[3].getText(), directory + File.separator + textFields[0].getText(), "icon1");
                    main.change(iconPath2, textFields[4].getText(), directory + File.separator + textFields[0].getText(), "icon2");
                    main.change(iconPath3, textFields[5].getText(), directory + File.separator + textFields[0].getText(), "icon3");
                    main.change(iconPath4, textFields[6].getText(), directory + File.separator + textFields[0].getText(), "icon4");
                    if (!String.valueOf(rating).equals(textFields[1].getText())) {
                        main.editFile(directory + File.separator + "rating",textFields[1].getText());
                    }
                    if (!description.equals(area.getText())) {
                        main.editFile(directory + File.separator + "description",area.getText());
                    }
                } else {
                    try {
                        if (!Files.exists(Paths.get(directory))) {
                            Files.createDirectory(Paths.get(directory));
                        } else {
                            main.isExists();
                            return;
                        }
                        Files.copy(Paths.get(textFields[2].getText()), Paths.get(directory + File.separator + textFields[0].getText()).resolveSibling("icon"), StandardCopyOption.REPLACE_EXISTING);
                        Files.copy(Paths.get(textFields[3].getText()), Paths.get(directory + File.separator + textFields[0].getText()).resolveSibling("icon1"), StandardCopyOption.REPLACE_EXISTING);
                        Files.copy(Paths.get(textFields[4].getText()), Paths.get(directory + File.separator + textFields[0].getText()).resolveSibling("icon2"), StandardCopyOption.REPLACE_EXISTING);
                        Files.copy(Paths.get(textFields[5].getText()), Paths.get(directory + File.separator + textFields[0].getText()).resolveSibling("icon3"), StandardCopyOption.REPLACE_EXISTING);
                        Files.copy(Paths.get(textFields[6].getText()), Paths.get(directory + File.separator + textFields[0].getText()).resolveSibling("icon4"), StandardCopyOption.REPLACE_EXISTING);
                        main.editTRD(directory,area,textFields[1].getText(),textFields[0].getText());
                        File[] files = new File(currentDirectory).listFiles();
                        main.moveFiles(files,main.platforms,directory);
                        Files.delete(Paths.get(iconPath));
                        Files.delete(Paths.get(iconPath1));
                        Files.delete(Paths.get(iconPath2));
                        Files.delete(Paths.get(iconPath3));
                        Files.delete(Paths.get(iconPath4));
                        main.deleteTRD(currentDirectory);
                        Files.delete(Paths.get(currentDirectory));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                main.createFileIfCheckBoxSelected(checkBoxes,directory,main.platforms);
                stage.close();
                Game.pane.getChildren().clear();
                main.load(new File(main.homeDirectory),Game.pane);
            }
        });
    }

    public String getTitle(){
        return title;
    }

     void createGUI(){
        iconStar = new ImageView(new Image( "game" + "/" + "star.png"));
        iconStar.setLayoutX(650);
        iconStar.setLayoutY(70);
        Label labelT = new Label(title);
        labelT.setFont(javafx.scene.text.Font.font(18));
        labelT.setPrefSize(450,200);
        TextArea area = new TextArea(description);
        area.setWrapText(true);
        area.setPrefSize(800,200);
        area.setEditable(false);
        Label labelR = new Label(String.valueOf(rating));
        labelR.setFont(Font.font("Monospace",FontPosture.ITALIC,24));
        labelR.setPrefSize(100,200);
        labelT.setLayoutX(185);
        labelT.setLayoutY(5);
        labelR.setLayoutX(705);
        labelR.setLayoutY(5);
        area.setLayoutX(0);
        area.setLayoutY(205);
        Button buttonDesc = new Button("Read more");
        Button buttonEdit = new Button("Edit");
        buttonDesc.setLayoutX(700);
        buttonDesc.setLayoutY(410);
        buttonEdit.setLayoutX(5);
        buttonEdit.setLayoutY(410);
        buttonEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                edit();
            }
        });
        buttonDesc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new MorePane(iconPath,iconPath1,iconPath2,iconPath3,iconPath4,title,description,rating);
            }
        });

        getChildren().addAll(labelT,iconStar,labelR,area,buttonDesc,buttonEdit,flowPane);
    }
    }