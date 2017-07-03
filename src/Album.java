import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Album extends Construct {
    String homeDirectory = System.getProperty("user.home") + File.separator + "Rating" + File.separator + "Album";
    static final FlowPane pane = new FlowPane();
    public Album(){}
    public Album(String directory, String add, String title){
        super(directory,add,pane,title,800);
        itemAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                about("   Rate your \nfavorite album","The icon resolution 160x200");
            }
        });
        itemAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                add(pane);
            }
        });
        itemLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane.getChildren().clear();
                load(new File(homeDirectory),pane);
            }
        });
        itemSort.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               sort(pane);
            }
        });
        itemSortPlatforfm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AlbumPane[] panes = new AlbumPane[pane.getChildren().size()];
                for (int i=0;i<pane.getChildren().size();i++) {
                    panes[i] = (AlbumPane) pane.getChildren().get(i);
                }
                pane.getChildren().clear();
            }
        });
        itemSearch.setText("Search a album");
        itemSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage mainStage = new Stage();
                Pane paneSearch = new Pane();
                mainStage.setScene(new Scene(paneSearch,200,65));
                TextField field = new TextField();
                Button button = new Button("Search");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        search(field.getText(),pane);
                        mainStage.close();
                    }
                });
                field.setLayoutX(5);
                field.setLayoutY(5);
                field.setPrefSize(190,20);
                button.setLayoutX(132);
                button.setLayoutY(35);
                paneSearch.getChildren().addAll(field,button);
                mainStage.setResizable(false);
                mainStage.show();
            }
        });
    }

    private void add(FlowPane pane){
        TextField[] textFields = new TextField[3];
        Button[] buttons = new Button[2];
        CheckBox[] checkBoxes = new CheckBox[platforms.length];
        Stage stage = new Stage();
        TextArea area = new TextArea("Description");
        TextArea areaS = new TextArea();
        ListView<String> listView = new ListView<>();
        ListView<String> listViewS = new ListView<>();
        GUI(textFields,buttons,checkBoxes,area,"Add album","Add album to the library",stage,platforms,listView,listViewS,areaS);
        buttons[0].setTooltip(new Tooltip("Icon resolution - 160x200"));
        buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooser(chooser,chooserStage,textFields[2]);
            }
        });
        buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (listView.getSelectionModel().getSelectedItem()==null){
                    Player player = new Player("Player","Add Player","Player");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Please, add Player first");
                    alert.setTitle("Warning");
                    alert.showAndWait();
                    stage.close();
                    return;
                } if (textFields[0].getText().isEmpty()||textFields[1].getText().isEmpty()||area.getText().isEmpty()){
                    check("title or rating or description");
                    return;
                } if (textFields[2].getText().isEmpty()){
                    check("icon");
                    return;
                }
                homeDirectory +=File.separator+textFields[0].getText();
                try {
                    if (Files.exists(Paths.get(homeDirectory))){
                        isExists();
                        return;
                    }
                    Files.createDirectory(Paths.get(homeDirectory));
                    Files.copy(Paths.get(textFields[2].getText()), Paths.get(homeDirectory + File.separator + textFields[0].getText()).resolveSibling("icon"), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Failed to create homeDirectory " + textFields[0].getText() + " in /home/" + System.getProperty("homeDirectory.home"));
                    alert.showAndWait();
                }
                editTRD(homeDirectory,area,textFields[1].getText(),textFields[0].getText());
                try (BufferedWriter br = new BufferedWriter(new FileWriter(homeDirectory +File.separator+"player"))) {
                    if (listView.getSelectionModel().getSelectedItem()==null){
                        br.write("unknown");
                    } else {
                        br.write(listView.getSelectionModel().getSelectedItem());
                        setTo("Player",listView.getSelectionModel().getSelectedItem(),"albums",textFields[0].getText(),true);
                    }
                } catch (IOException eT) {
                    eT.printStackTrace();
                    alertError(homeDirectory +File.separator+"player",sCreate);
                }
                try (BufferedWriter br = new BufferedWriter(new FileWriter(homeDirectory +File.separator+"songs"))) {
                    if (areaS.getText().isEmpty()){
                    } else {
                        br.write(areaS.getText());
                        String[] s = areaS.getText().trim().split("\n");
                        for (int i=0;i < s.length;i++){
                            setTo("Music",s[i],"album",textFields[0].getText(),false);
                        }
                    }
                } catch (IOException eD) {
                    eD.printStackTrace();
                    alertError(homeDirectory + File.separator + "songs",sCreate);
                } setIconPath("Player",listView.getSelectionModel().getSelectedItem(),homeDirectory + File.separator + "iconPlayer");
                AlbumPane albumPane = new AlbumPane(textFields[2].getText(),textFields[0].getText(),
                        Float.parseFloat(textFields[1].getText()),area.getText(),
                        listView.getSelectionModel().getSelectedItem(),areaS.getText(),
                        getIconPath(homeDirectory,"iconPlayer"));
                for (int i = 0; i< checkBoxes.length; i++) {
                    if (checkBoxes[i].isSelected()){
                        albumPane.isPlatforms[i]=true;
                        try {
                            Files.createFile(Paths.get(homeDirectory +File.separator+platforms[i]));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                pane.getChildren().add(albumPane);
                stage.close();
                platformsInit(albumPane.isPlatforms,platforms,albumPane.flowPane,"music","jpg");
                homeDirectory = System.getProperty("user.home") + File.separator + "Rating" + File.separator + "Album";
            }
        });
    }

    void load(File directory,FlowPane pane){
        float rating=0f;
        String p = null,iconPlayer = null;
        StringBuilder sb = null,sbt = null,sbs=null;
        File[] files = directory.listFiles();
        AlbumPane albumPane = new AlbumPane();
        for (File file: files) {
            if (file.isDirectory()) {
                load(file,pane);
            }  if (file.getName().equals("icon")) {
                albumPane.setIconPath(file.getAbsolutePath());
            }  if (file.getName().equals("rating")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    while ((text = br.readLine()) != null) {
                        rating = Float.parseFloat(text);
                    }
                    albumPane.setRating(rating);
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            }  if (file.getName().equals("title")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    sbt = new StringBuilder();
                    while ((text = br.readLine()) != null) {
                        sbt.append(text);
                        sbt.append("\n");
                    }
                    albumPane.setTitle(sbt.toString());
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            } if (file.getName().equals("description")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    sb = new StringBuilder();
                    while ((text = br.readLine()) != null) {
                        sb.append(text);
                        sb.append("\n");
                    }
                    albumPane.setDescription(sb.toString());
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            } if (file.getName().equals("player")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    while ((text = br.readLine()) != null) {
                        p=text;
                    }
                    albumPane.setPlayer(p);
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            } if (file.getName().equals("songs")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    sbs = new StringBuilder();
                    while ((text = br.readLine()) != null) {
                        sbs.append(text);
                        sbs.append("\n");
                    }
                    albumPane.setSongs(sbs.toString());
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            } if (file.getName().equals("iconPlayer")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    while ((text = br.readLine()) != null) {
                        iconPlayer=text;
                    }
                    albumPane.setIconPlayer(iconPlayer);
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            }

            for (int i=0;i<platforms.length;i++){
                if (file.getName().equals(platforms[i])){
                    createImage(platforms[i],albumPane.flowPane,"music","jpg");
                }
            }
            if (sbt!=null&sb!=null&rating!=0&p!=null&sbs!=null&iconPlayer!=null) {
                albumPane.createGUI();
                pane.getChildren().add(albumPane);
                sbt=null;rating=0;sb=null;p=null;sbs=null;iconPlayer=null;
            }

        }
    }

    void GUI(TextField[] fields, Button[] buttons, CheckBox[] checkBoxes,TextArea area, String title, String action, Stage stage, String[] platforms,ListView<String> listView,ListView<String> listViewS,TextArea areaS) {
        String[] titles = {"Choose a title icon",action};
        for (int i=0;i<platforms.length;i++){
            checkBoxes[i] = new CheckBox(platforms[i]);
            if (checkBoxes[i].getText().equals(platforms[0])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(35);
            } if (checkBoxes[i].getText().equals(platforms[1])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(35);
            } if (checkBoxes[i].getText().equals(platforms[2])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(55);
            } if (checkBoxes[i].getText().equals(platforms[3])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(55);
            } if (checkBoxes[i].getText().equals(platforms[4])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(75);
            } if (checkBoxes[i].getText().equals(platforms[5])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(75);
            } if (checkBoxes[i].getText().equals(platforms[7])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(95);
            } if (checkBoxes[i].getText().equals(platforms[6])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(95);
            } if (checkBoxes[i].getText().equals(platforms[8])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(115);
            } if (checkBoxes[i].getText().equals(platforms[9])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(115);
            } if (checkBoxes[i].getText().equals(platforms[10])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(135);
            } if (checkBoxes[i].getText().equals(platforms[11])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(135);
            } if (checkBoxes[i].getText().equals(platforms[12])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(155);
            } if (checkBoxes[i].getText().equals(platforms[13])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(155);
            }
        }
        stage.setTitle(title);
        Pane root = new Pane();
        for (int i=0;i<fields.length;i++){
            fields[i] = new TextField();
            fields[i].setPrefSize(200,20);
        }
        fields[0].setText("Enter a title");
        fields[1].setText("Rating");
        fields[1].setPrefSize(50,20);
        fields[0].setLayoutX(5);
        fields[0].setLayoutY(5);
        fields[1].setLayoutX(210);
        fields[1].setLayoutY(5);
        fields[2].setLayoutX(265);
        fields[2].setLayoutY(5);
        for (int i=0;i<buttons.length;i++){
            buttons[i] = new Button(titles[i]);
        }
        buttons[0].setLayoutX(470);
        buttons[0].setLayoutY(5);
        buttons[1].setLayoutX(435);
        buttons[1].setLayoutY(660);
        area.setPrefSize(605,250);
        area.setLayoutX(5);
        area.setLayoutY(400);
        area.setWrapText(true);
        listView.setPrefSize(200,100);
        listView.setLayoutX(405);
        listView.setLayoutY(250);
        listViewS.setPrefSize(200,100);
        listViewS.setLayoutX(5);
        listViewS.setLayoutY(250);
        areaS.setPrefSize(200,100);
        areaS.setLayoutX(205);
        areaS.setLayoutY(250);
        areaS.setWrapText(true);
        String playerDirectory = System.getProperty("user.home") + File.separator + "Rating" + File.separator + "Player";
        String songDirectory = System.getProperty("user.home") + File.separator + "Rating" + File.separator + "Music";
        File[] files = new File(playerDirectory).listFiles();
        File[] filesS = new File(songDirectory).listFiles();
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayListS = new ArrayList<>();
        for (File file:files){
            if (file.isDirectory()){
                arrayList.add(file.getName());
            }
        }
        for (File file:filesS){
            if (file.isDirectory()){
                arrayListS.add(file.getName());
            }
        }
        ObservableList<String> list = FXCollections.observableArrayList(arrayList);
        ObservableList<String> listS = FXCollections.observableArrayList(arrayListS);
        listView.setItems(list);
        listViewS.setItems(listS);
        listViewS.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> c) {
                areaS.appendText(listViewS.getSelectionModel().getSelectedItem()+"\n");
            }
        });
        Label labelS = new Label("Songs:");
        Label labelP = new Label("Player");
        Label labelD = new Label("Description");
        labelS.setLayoutX(185);
        labelS.setLayoutY(220);
        labelP.setLayoutX(470);
        labelP.setLayoutY(220);
        labelD.setLayoutX(270);
        labelD.setLayoutY(370);
        root.getChildren().addAll(fields[0],fields[1],fields[2],area,buttons[0],buttons[1],listView,listViewS,areaS,labelP,labelS,labelD);
        for (int i=0;i<checkBoxes.length;i++) {
            root.getChildren().addAll(checkBoxes[i]);
        }
        stage.setScene(new Scene(root,615,720));
        stage.setResizable(false);
        stage.show();
    }
}