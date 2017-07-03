
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class AlbumPane extends ConstructPane {
    private  boolean isBlues=false,isJazz=false,isCountry=false,isElectronic=false,isPop=false,
            isHipHop=false,isRock=false,isLatin=false,isRandBandSoul=false,isAsian=false,isFolk=false,
            isEasyListening=false, isComedy =false, isExclusions=false;
    boolean[] isPlatforms = {isBlues=false,isJazz=false,isCountry=false,isElectronic=false,isPop=false,
            isHipHop=false,isRock=false,isLatin=false,isRandBandSoul=false,isAsian=false,isFolk=false,
            isEasyListening=false,isComedy=false,isExclusions=false};
    private String title,iconPath,description,player,songs,iconPlayer;
    FlowPane flowPane;
    private Album main = new Album();
    private ImageView icon,iconStar;
    private float rating;
    AlbumPane(){
        flowPane = new FlowPane();
        flowPane.setLayoutX(0);
        flowPane.setLayoutY(205);
        flowPane.setPrefSize(800,100);}

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setSongs(String songs) {
        this.songs = songs;
    }

    public void setTitle(String title) {

        this.title = title;

    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
        icon = new ImageView(new Image(new File(iconPath).toURI().toString()));
        icon.setLayoutX(0);
        icon.setLayoutY(5);
        getChildren().add(icon);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIconPlayer(String iconPlayer) {
        this.iconPlayer = iconPlayer;
    }

    AlbumPane(String iconPath, String title, float rating, String description, String player,
              String songs, String iconPlayer){
        icon = new ImageView(new Image(new File(iconPath).toURI().toString()));
        iconStar = new ImageView(new Image( "game" + "/" + "star.png"));
        this.title=title;
        this.rating=rating;
        this.iconPath=iconPath;
        this.player=player;
        this.iconPlayer=iconPlayer;
        icon.setLayoutX(0);
        icon.setLayoutY(5);
        iconStar.setLayoutX(650);
        iconStar.setLayoutY(70);
        flowPane = new FlowPane();
        flowPane.setLayoutX(0);
        flowPane.setLayoutY(205);
        flowPane.setPrefSize(800,100);
        Label labelT = new Label(title);
        TextArea area = new TextArea(description);
        area.setWrapText(true);
        area.setPrefSize(800,100);
        area.setEditable(false);
        area.setLayoutX(0);
        area.setLayoutY(305);
        labelT.setFont(javafx.scene.text.Font.font(18));
        labelT.setPrefSize(200,200);
        Label labelR = new Label(String.valueOf(rating));
        labelR.setFont(Font.font("Monospace",FontPosture.ITALIC,24));
        labelR.setPrefSize(100,200);
        labelT.setLayoutX(470);
        labelT.setLayoutY(5);
        labelR.setLayoutX(705);
        labelR.setLayoutY(5);
        ObservableList<String> list = FXCollections.observableArrayList(songs.trim().split("\n"));
        ListView<String> listView = new ListView<>(list);
        listView.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> c) {
                Music music = new Music("Music","Add music","Music");
                Music.pane.getChildren().clear();
                music.load(new File(music.homeDirectory),Music.pane);
                music.search(listView.getSelectionModel().getSelectedItem(),Music.pane);
            }
        });
        ImageView iconP = new ImageView(new Image(new File(iconPlayer).toURI().toString()));
        iconP.setLayoutX(162);
        iconP.setLayoutY(5);
        iconP.setPickOnBounds(true);
        iconP.setOnMouseClicked((MouseEvent e) -> {
            Player p = new Player("Player","Add player","Player");
            Player.pane.getChildren().clear();
            p.load(new File(p.homeDirectory),Player.pane);
            p.search(player,Player.pane);
        });
        listView.setPrefSize(130,180);
        listView.setLayoutX(325);
        listView.setLayoutY(25);
        Label labelS = new Label("Songs:");
        labelS.setLayoutX(350);
        labelS.setLayoutY(0);
        Button buttonEdit = new Button("Edit");
        buttonEdit.setLayoutX(5);
        buttonEdit.setLayoutY(410);
        buttonEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                edit();
            }
        });

        getChildren().addAll(icon,labelT,iconStar,labelR,buttonEdit,flowPane,area,labelS,listView,iconP);
        main.platformsInit(isPlatforms,main.platforms,flowPane,"music","jpg");
    }

    private void edit(){
        TextField[] textFields = new TextField[3];
        Button[] buttons = new Button[2];
        CheckBox[] checkBoxes = new CheckBox[isPlatforms.length];
        TextArea area = new TextArea();
        TextArea areaS = new TextArea(songs);
        Stage stage = new Stage();
        ListView<String> listView = new ListView<>();
        ListView<String> listViewS = new ListView<>();
        listViewS.getSelectionModel().select(player);
        main.GUI(textFields,buttons,checkBoxes,area,"Edit","Edit album in the library",stage,main.platforms,listView,listViewS,areaS);
        textFields[2].setText(iconPath);
        textFields[0].setText(title);
        textFields[1].setText(String.valueOf(rating));
        String currentDirectory=main.homeDirectory+File.separator+textFields[0].getText();
        String currentTitle=textFields[0].getText();
        area.setText(description);
        File[] files = new File(currentDirectory).listFiles();
        main.setCheckBoxSelected(files,main.platforms,checkBoxes);
        buttons[0].setTooltip(new Tooltip("Icon resolution - 160x200"));
        buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.chooser(main.chooser,main.chooserStage,textFields[2]);
            }
        });
        buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (textFields[0].getText().isEmpty()||textFields[1].getText().isEmpty()||area.getText().isEmpty()){
                    main.check("title or rating or description");
                    return;
                } if (textFields[2].getText().isEmpty()){
                    main.check("icon");
                    return;
                }
                main.deleteIfCheckBoxNotSelected(files,main.platforms,checkBoxes,currentDirectory);
                String directory = main.homeDirectory + File.separator + textFields[0].getText();
                if (currentTitle.equals(textFields[0].getText())) {
                    main.change(iconPath, textFields[2].getText(), directory + File.separator + textFields[0].getText(), "icon");
                    if (!String.valueOf(rating).equals(textFields[1].getText())) {
                        main.editFile(directory + File.separator + "rating",textFields[1].getText());
                    } if (!description.equals(area.getText())) {
                        main.editFile(directory + File.separator + "description",area.getText());
                    } if (!songs.equals(areaS.getText())) {
                        main.editFile(directory + File.separator + "songs",areaS.getText());
                    } if (!player.equals(listView.getSelectionModel().getSelectedItem())) {
                        main.editFile(directory + File.separator + "player",listView.getSelectionModel().getSelectedItem());
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
                        main.editTRD(directory,area,textFields[1].getText(),textFields[0].getText());
                        try (BufferedWriter br = new BufferedWriter(new FileWriter(directory +File.separator+"songs"))) {
                            if (areaS.getText().isEmpty()){
                                areaS.setText("unknown");
                            }
                            br.write(areaS.getText());
                        } catch (IOException eD) {
                            eD.printStackTrace();
                            main.alertError(directory + File.separator + "songs",main.sCreate);
                        } try (BufferedWriter br = new BufferedWriter(new FileWriter(directory +File.separator+"player"))) {
                           if (listView.getSelectionModel().getSelectedItem()==null){
                               br.write("unknown");
                           } else {
                               br.write(listView.getSelectionModel().getSelectedItem());
                           }
                        } catch (IOException eD) {
                            eD.printStackTrace();
                            main.alertError(directory + File.separator + "player",main.sCreate);
                        }
                        File[] files = new File(currentDirectory).listFiles();
                        main.moveFiles(files,main.platforms,directory);
                        main.deleteTRD(currentDirectory);
                        Files.delete(Paths.get(currentDirectory + File.separator + "songs"));
                        Files.delete(Paths.get(currentDirectory + File.separator + "player"));
                        Files.delete(Paths.get(iconPath));
                        Files.delete(Paths.get(currentDirectory));
                    } catch (IOException e) {
                        e.printStackTrace();
                        main.alertError(currentDirectory,main.sDelete);
                    }
                }
                main.createFileIfCheckBoxSelected(checkBoxes,directory,main.platforms);
                stage.close();
                Album.pane.getChildren().clear();
                main.load(new File(main.homeDirectory),Album.pane);
            }
        });
    }

    public String getTitle(){
        return title;
    }

    void createGUI(){
        iconStar = new ImageView(new Image("game" + "/" + "star.png"));
        iconStar.setLayoutX(650);
        iconStar.setLayoutY(70);
        Label labelT = new Label(title);
        TextArea area = new TextArea(description);
        area.setWrapText(true);
        area.setPrefSize(800,100);
        area.setEditable(false);
        area.setLayoutX(0);
        area.setLayoutY(305);
        labelT.setFont(javafx.scene.text.Font.font(18));
        labelT.setPrefSize(200,200);
        Label labelR = new Label(String.valueOf(rating));
        labelR.setFont(Font.font("Monospace",FontPosture.ITALIC,24));
        labelR.setPrefSize(100,200);
        labelT.setLayoutX(470);
        labelT.setLayoutY(5);
        labelR.setLayoutX(705);
        labelR.setLayoutY(5);
        ObservableList<String> list = FXCollections.observableArrayList(songs.trim().split("\n"));
        ListView<String> listView = new ListView<>(list);
        listView.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> c) {
                Music music = new Music("Music","Add music","Music");
                Music.pane.getChildren().clear();
                music.load(new File(music.homeDirectory),Music.pane);
                music.search(listView.getSelectionModel().getSelectedItem(),Music.pane);
            }
        });
        ImageView iconP = new ImageView(new Image(new File(iconPlayer).toURI().toString()));
        iconP.setLayoutX(162);
        iconP.setLayoutY(5);
        iconP.setPickOnBounds(true);
        iconP.setOnMouseClicked((MouseEvent e) -> {
            Player p = new Player("Player","Add player","Player");
            Player.pane.getChildren().clear();
            p.load(new File(p.homeDirectory),Player.pane);
            p.search(player,Player.pane);
        });
        listView.setPrefSize(130,180);
        listView.setLayoutX(325);
        listView.setLayoutY(25);
        Label labelS = new Label("Songs:");
        labelS.setLayoutX(350);
        labelS.setLayoutY(0);
        Button buttonEdit = new Button("Edit");
        buttonEdit.setLayoutX(5);
        buttonEdit.setLayoutY(410);
        buttonEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                edit();
            }
        });

        getChildren().addAll(labelT,iconStar,labelR,buttonEdit,flowPane,area,labelS,listView,iconP);
    }
}