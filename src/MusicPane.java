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

public class MusicPane extends ConstructPane {
    private  boolean isBlues=false,isJazz=false,isCountry=false,isElectronic=false,isPop=false,
            isHipHop=false,isRock=false,isLatin=false,isRandBandSoul=false,isAsian=false,isFolk=false,
            isEasyListening=false, isComedy =false, isExclusions=false;
    boolean[] isPlatforms = {isBlues=false,isJazz=false,isCountry=false,isElectronic=false,isPop=false,
            isHipHop=false,isRock=false,isLatin=false,isRandBandSoul=false,isAsian=false,isFolk=false,
            isEasyListening=false,isComedy=false,isExclusions=false};
    private String title,iconPath,player,album,iconPlayer;
    FlowPane flowPane;
    private Music main = new Music();
    private ImageView icon,iconStar;
    private float rating;
    MusicPane(){
        flowPane = new FlowPane();
        flowPane.setLayoutX(0);
        flowPane.setLayoutY(210);
        flowPane.setPrefSize(800,200);}

    public void setRating(float rating) {
        this.rating = rating;
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

    public void setIconPlayer(String iconPlayer) {
        this.iconPlayer = iconPlayer;
    }

    MusicPane(String iconPath, String title, float rating, String player,
              String album, String iconPlayer){
        icon = new ImageView(new Image(new File(iconPath).toURI().toString()));
        iconStar = new ImageView(new Image( "game" + "/" + "star.png"));
        this.title=title;
        this.rating=rating;
        this.iconPath=iconPath;
        this.player=player;
        this.iconPlayer=iconPlayer;
        this.album=album;
        icon.setLayoutX(5);
        icon.setLayoutY(5);
        iconStar.setLayoutX(650);
        iconStar.setLayoutY(70);
        flowPane = new FlowPane();
        flowPane.setLayoutX(0);
        flowPane.setLayoutY(210);
        flowPane.setPrefSize(800,200);
        Label labelT = new Label(title);
        labelT.setFont(javafx.scene.text.Font.font(18));
        labelT.setPrefSize(450,200);
        Label labelR = new Label(String.valueOf(rating));
        labelR.setFont(Font.font("Monospace",FontPosture.ITALIC,24));
        labelR.setPrefSize(100,200);
        labelT.setLayoutX(470);
        labelT.setLayoutY(5);
        labelR.setLayoutX(705);
        labelR.setLayoutY(5);
        ObservableList<String> list = FXCollections.observableArrayList(album);
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

            ImageView iconP = new ImageView(new Image(new File(iconPlayer).toURI().toString()));
            iconP.setLayoutX(162);
            iconP.setLayoutY(5);
            iconP.setPickOnBounds(true);
            iconP.setOnMouseClicked((MouseEvent e) -> {
                Player p = new Player("Player", "Add player", "Player");
                Player.pane.getChildren().clear();
                p.load(new File(p.homeDirectory), Player.pane);
                p.search(player,Player.pane);
            });
        listView.setPrefSize(130,180);
        listView.setLayoutX(325);
        listView.setLayoutY(25);
        Label labelS = new Label("Album");
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

        getChildren().addAll(icon,labelT,iconStar,labelR,buttonEdit,flowPane,labelS,listView,iconP);
        main.platformsInit(isPlatforms,main.platforms,flowPane,"music","jpg");
    }

    private void edit(){
        TextField[] textFields = new TextField[3];
        Button[] buttons = new Button[2];
        CheckBox[] checkBoxes = new CheckBox[isPlatforms.length];
        TextArea areaA = new TextArea(album);
        Stage stage = new Stage();
        ListView<String> listView = new ListView<>();
        ListView<String> listViewS = new ListView<>();
        listViewS.getSelectionModel().select(player);
        main.GUI(textFields,buttons,checkBoxes,"Edit","Edit album in the library",stage,main.platforms,listView,listViewS,areaA);
        textFields[2].setText(iconPath);
        textFields[0].setText(title);
        textFields[1].setText(String.valueOf(rating));
        String currentDirectory=main.homeDirectory+File.separator+textFields[0].getText();
        String currentTitle=textFields[0].getText();
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
                main.deleteIfCheckBoxNotSelected(files,main.platforms,checkBoxes,currentDirectory);
                String directory = main.homeDirectory + File.separator + textFields[0].getText();
                if (currentTitle.equals(textFields[0].getText())) {
                    main.change(iconPath, textFields[2].getText(), directory + File.separator + textFields[0].getText(), "icon");
                    if (!String.valueOf(rating).equals(textFields[1].getText())) {
                        main.editFile(directory + File.separator + "rating",textFields[1].getText());
                    }  if (!album.equals(areaA.getText())) {
                        main.editFile(directory + File.separator + "album",areaA.getText());
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
                        main.editFile(directory + File.separator + "rating",textFields[1].getText());
                        main.editFile(directory + File.separator + "title",textFields[0].getText());
                        try (BufferedWriter br = new BufferedWriter(new FileWriter(directory +File.separator+"album"))) {
                            if (areaA.getText().isEmpty()){
                                areaA.setText("unknown");
                            }
                            br.write(areaA.getText());
                        } catch (IOException eD) {
                            eD.printStackTrace();
                            main.alertError(directory + File.separator + "album",main.sCreate);
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
                        Files.delete(Paths.get(currentDirectory + File.separator + "title"));
                        Files.delete(Paths.get(currentDirectory + File.separator + "rating"));
                        Files.delete(Paths.get(currentDirectory + File.separator + "album"));
                        Files.delete(Paths.get(currentDirectory + File.separator + "player"));
                        Files.delete(Paths.get(iconPath));
                        Files.delete(Paths.get(currentDirectory));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                main.createFileIfCheckBoxSelected(checkBoxes,directory,main.platforms);
                stage.close();
                Music.pane.getChildren().clear();
                main.load(new File(main.homeDirectory),Music.pane);
            }
        });
    }

    public String getTitle(){
        return title;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    void createGUI(){
        iconStar = new ImageView(new Image( "game" + "/" + "star.png"));
        iconStar.setLayoutX(650);
        iconStar.setLayoutY(70);
        Label labelT = new Label(title);
        labelT.setFont(javafx.scene.text.Font.font(18));
        labelT.setPrefSize(450,200);
        Label labelR = new Label(String.valueOf(rating));
        labelR.setFont(Font.font("Monospace",FontPosture.ITALIC,24));
        labelR.setPrefSize(100,200);
        labelT.setLayoutX(470);
        labelT.setLayoutY(5);
        labelR.setLayoutX(705);
        labelR.setLayoutY(5);
        ObservableList<String> list = FXCollections.observableArrayList(album);
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
        Label labelS = new Label("Album");
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

        getChildren().addAll(labelT,iconStar,labelR,buttonEdit,flowPane,labelS,listView,iconP);
    }
}