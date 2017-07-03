import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PlayerPane extends ConstructPane{
    private ImageView icon;
    FlowPane flowPane;
    private short yearOfFoundation;
    private String country,composition,title,description,iconPath,iconPath1,iconPath2,
            iconPath3,iconPath4,yearOfDisintegration,album;
    private  boolean isBlues=false,isJazz=false,isCountry=false,isElectronic=false,isPop=false,
            isHipHop=false,isRock=false,isLatin=false,isRandBandSoul=false,isAsian=false,isFolk=false,
            isEasyListening=false, isComedy =false, isExclusions=false;
    boolean[] isPlatforms = {isBlues=false,isJazz=false,isCountry=false,isElectronic=false,isPop=false,
            isHipHop=false,isRock=false,isLatin=false,isRandBandSoul=false,isAsian=false,isFolk=false,
            isEasyListening=false,isComedy=false,isExclusions=false};
    private Player main = new Player();
    PlayerPane(){
        flowPane = new FlowPane();
        flowPane.setLayoutX(495);
        flowPane.setLayoutY(0);
        flowPane.setPrefSize(600,200);}

    public void setAlbum(String album) {
        this.album = album;
    }

    PlayerPane(String title, String description, String iconPath, String iconPath1, String iconPath2,
               String iconPath3, String iconPath4, String composition, String country,
               short yearOfFoundation, String yearOfDisintegration, String album){
        this.title=title;
        this.description=description;
        this.iconPath=iconPath;
        this.iconPath1=iconPath1;
        this.iconPath2=iconPath2;
        this.iconPath3=iconPath3;
        this.iconPath4=iconPath4;
        this.composition=composition;
        this.country=country;
        this.yearOfFoundation=yearOfFoundation;
        this.yearOfDisintegration=yearOfDisintegration;
        this.album=album;
        flowPane = new FlowPane();
        flowPane.setLayoutX(495);
        flowPane.setLayoutY(0);
        flowPane.setPrefSize(645,100);
        icon = new ImageView(new Image(new File(iconPath).toURI().toString()));
        icon.setLayoutX(5);
        icon.setLayoutY(5);
        Label labelT = new Label(title);
        labelT.setFont(javafx.scene.text.Font.font(18));
        labelT.setPrefSize(160,200);
        TextArea area = new TextArea(description);
        area.setWrapText(true);
        area.setPrefSize(1100,200);
        area.setEditable(false);
        labelT.setLayoutX(325);
        labelT.setLayoutY(5);
        area.setLayoutX(0);
        area.setLayoutY(205);
        ObservableList<String> list = FXCollections.observableArrayList(album.trim().split("\n"));
        ListView<String> listView = new ListView<>(list);
        listView.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> c) {
                Album album = new Album("Album","Add album","Album");
                Album.pane.getChildren().clear();
                album.load(new File(album.homeDirectory),Album.pane);
                album.search(listView.getSelectionModel().getSelectedItem(),Album.pane);
            }
        });
        listView.setLayoutX(170);
        listView.setLayoutY(25);
        listView.setPrefSize(140,180);
        Label labelP = new Label("Albums:");
        labelP.setLayoutX(180);
        labelP.setLayoutY(0);
        Button buttonDesc = new Button("Read more");
        Button buttonEdit = new Button("Edit");
        buttonDesc.setLayoutX(1000);
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
                new MorePlayerPane(iconPath,iconPath1,iconPath2,iconPath3,iconPath4,title,description,yearOfFoundation,
                        yearOfDisintegration,country,composition,album);
            }
        });

        getChildren().addAll(icon,labelT,area,buttonDesc,buttonEdit,flowPane,listView,labelP);
        main.platformsInit(isPlatforms,main.platforms,flowPane,"music","jpg");
    }
    public void setYearOfFoundation(short yearOfFoundation) {
        this.yearOfFoundation = yearOfFoundation;
    }

    public void setYearOfDisintegration(String yearOfDisintegration) {
        this.yearOfDisintegration = yearOfDisintegration;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setComposition(String composition) {
        this.composition = composition;
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

    public String getTitle() {
        return title;
    }

    private void edit(){
        TextField[] textFields = new TextField[9];
        Button[] buttons = new Button[6];
        CheckBox[] checkBoxes = new CheckBox[isPlatforms.length];
        TextArea area = new TextArea();
        TextArea areaComposition = new TextArea(album);
        Stage stage = new Stage();
        ListView<String> listView = new ListView<>();
        TextArea areaA = new TextArea();
        main.GUI(textFields,buttons,checkBoxes,area,areaComposition,"Edit","Edit Player in the library",stage,main.platforms,listView,areaA);
        textFields[1].setText(iconPath);
        textFields[2].setText(iconPath1);
        textFields[3].setText(iconPath2);
        textFields[4].setText(iconPath3);
        textFields[5].setText(iconPath4);
        textFields[6].setText(String.valueOf(yearOfFoundation));
        textFields[7].setText(yearOfDisintegration);
        textFields[0].setText(title);
        area.setText(description);
        areaComposition.setText(composition);
        textFields[8].setText(country);
        String currentDirectory=main.homeDirectory+File.separator+textFields[0].getText();
        String currentTitle=textFields[0].getText();
        File[] files = new File(currentDirectory).listFiles();
        main.setCheckBoxSelected(files,main.platforms,checkBoxes);
        buttons[0].setTooltip(new Tooltip("Icon resolution - 160x200"));
        buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.chooser(main.chooser,main.chooserStage,textFields[1]);
            }
        });
        buttons[1].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.chooser(main.chooser,main.chooserStage,textFields[2]);
            }
        });
        buttons[2].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.chooser(main.chooser,main.chooserStage,textFields[3]);
            }
        });
        buttons[3].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[3].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.chooser(main.chooser,main.chooserStage,textFields[4]);
            }
        });
        buttons[4].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[4].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.chooser(main.chooser,main.chooserStage,textFields[5]);
            }
        });
        buttons[5].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (textFields[0].getText().isEmpty()||textFields[1].getText().isEmpty()||area.getText().isEmpty()){
                    main.check("title or rating or description");
                    return;
                } if (textFields[1].getText().isEmpty()||textFields[2].getText().isEmpty()||textFields[3].getText().isEmpty()||textFields[4].getText().isEmpty()||textFields[5].getText().isEmpty()){
                    main.check("icons");
                    return;
                } if (textFields[6].getText().isEmpty()||textFields[7].getText().isEmpty()){
                    main.check("foundation year or disintegration");
                } if (textFields[8].getText().isEmpty()||areaComposition.getText().isEmpty()){
                    main.check("coutry or composition");
                }
                main.deleteIfCheckBoxNotSelected(files,main.platforms,checkBoxes,currentDirectory);
                String directory = main.homeDirectory + File.separator + textFields[0].getText();
                if (currentTitle.equals(textFields[0].getText())) {
                    main.change(iconPath, textFields[1].getText(), directory + File.separator + textFields[0].getText(), "icon");
                    main.change(iconPath1, textFields[2].getText(), directory + File.separator + textFields[0].getText(), "icon1");
                    main.change(iconPath2, textFields[3].getText(), directory + File.separator + textFields[0].getText(), "icon2");
                    main.change(iconPath3, textFields[4].getText(), directory + File.separator + textFields[0].getText(), "icon3");
                    main.change(iconPath4, textFields[5].getText(), directory + File.separator + textFields[0].getText(), "icon4");
                    if (!String.valueOf(yearOfFoundation).equals(textFields[6].getText())) {
                        main.editFile(directory + File.separator + "yearF",textFields[6].getText());
                    }
                    if (!yearOfDisintegration.equals(textFields[7].getText())) {
                        main.editFile(directory + File.separator + "yearD",textFields[7].getText());
                    }
                    if (!description.equals(area.getText())) {
                        main.editFile(directory + File.separator + "description",area.getText());
                    }
                    if (!composition.equals(areaComposition.getText())) {
                        main.editFile(directory + File.separator + "composition",areaComposition.getText());
                    }
                    if (!country.equals(textFields[8].getText())) {
                        main.editFile(directory + File.separator + "country",textFields[8].getText());
                    }
                    if (!album.equals(areaA.getText())) {
                        main.editFile(directory + File.separator + "albums",areaA.getText());
                    }
                } else {
                    try {
                        if (!Files.exists(Paths.get(directory))) {
                            Files.createDirectory(Paths.get(directory));
                        } else {
                            main.isExists();
                            return;
                        }
                        Files.copy(Paths.get(textFields[1].getText()), Paths.get(directory + File.separator + textFields[0].getText()).resolveSibling("icon"), StandardCopyOption.REPLACE_EXISTING);
                        Files.copy(Paths.get(textFields[2].getText()), Paths.get(directory + File.separator + textFields[0].getText()).resolveSibling("icon1"), StandardCopyOption.REPLACE_EXISTING);
                        Files.copy(Paths.get(textFields[3].getText()), Paths.get(directory + File.separator + textFields[0].getText()).resolveSibling("icon2"), StandardCopyOption.REPLACE_EXISTING);
                        Files.copy(Paths.get(textFields[4].getText()), Paths.get(directory + File.separator + textFields[0].getText()).resolveSibling("icon3"), StandardCopyOption.REPLACE_EXISTING);
                        Files.copy(Paths.get(textFields[5].getText()), Paths.get(directory + File.separator + textFields[0].getText()).resolveSibling("icon4"), StandardCopyOption.REPLACE_EXISTING);
                        main.editFile(directory + File.separator + "title",textFields[0].getText());
                        main.editFile(directory + File.separator + "yearF",textFields[6].getText());
                        main.editFile(directory + File.separator + "yearD",textFields[7].getText());
                        main.editFile(directory + File.separator + "country",textFields[8].getText());
                        main.editFile(directory + File.separator + "description",area.getText());
                        main.editFile(directory + File.separator + "composition",areaComposition.getText());
                        main.editFile(directory + File.separator + "albums",areaA.getText());
                        File[] files = new File(currentDirectory).listFiles();
                        main.moveFiles(files,main.platforms,directory);
                        Files.delete(Paths.get(iconPath));
                        Files.delete(Paths.get(iconPath1));
                        Files.delete(Paths.get(iconPath2));
                        Files.delete(Paths.get(iconPath3));
                        Files.delete(Paths.get(iconPath4));
                        Files.delete(Paths.get(currentDirectory + File.separator + "title"));
                        Files.delete(Paths.get(currentDirectory + File.separator + "yearF"));
                        Files.delete(Paths.get(currentDirectory + File.separator + "yearD"));
                        Files.delete(Paths.get(currentDirectory + File.separator + "description"));
                        Files.delete(Paths.get(currentDirectory + File.separator + "composition"));
                        Files.delete(Paths.get(currentDirectory + File.separator + "country"));
                        Files.delete(Paths.get(currentDirectory + File.separator + "albums"));
                        Files.delete(Paths.get(currentDirectory));
                    } catch (IOException e) {
                        e.printStackTrace();
                        main.alertError(currentDirectory,main.sDelete);
                    }
                }
                main.createFileIfCheckBoxSelected(checkBoxes,directory,main.platforms);
                stage.close();
                Player.pane.getChildren().clear();
                main.load(new File(main.homeDirectory),Player.pane);
            }
        });
    }

    void createGUI(){
        Label labelT = new Label(title);
        labelT.setFont(javafx.scene.text.Font.font(18));
        labelT.setPrefSize(160,200);
        TextArea area = new TextArea(description);
        area.setWrapText(true);
        area.setPrefSize(1100,200);
        area.setEditable(false);
        labelT.setLayoutX(325);
        labelT.setLayoutY(5);
        area.setLayoutX(0);
        area.setLayoutY(205);
        ObservableList<String> list = FXCollections.observableArrayList(album.trim().split("\n"));
        ListView<String> listView = new ListView<>(list);
        listView.setPrefSize(140,180);
        listView.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> c) {
                Album album = new Album("Album","Add album","Album");
                Album.pane.getChildren().clear();
                album.load(new File(album.homeDirectory),Album.pane);
                album.search(listView.getSelectionModel().getSelectedItem(),Album.pane);
            }
        });
        listView.setLayoutX(170);
        listView.setLayoutY(25);
        Label labelP = new Label("Albums:");
        labelP.setLayoutX(180);
        labelP.setLayoutY(0);
        Button buttonDesc = new Button("Read more");
        Button buttonEdit = new Button("Edit");
        buttonDesc.setLayoutX(1000);
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
                new MorePlayerPane(iconPath,iconPath1,iconPath2,iconPath3,iconPath4,title,description,
                        yearOfFoundation,yearOfDisintegration,country,composition,album);
            }
        });

        getChildren().addAll(labelT,area,buttonDesc,buttonEdit,flowPane,listView,labelP);
    }
}