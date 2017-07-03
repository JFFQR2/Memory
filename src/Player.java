import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Player extends Construct{
    String homeDirectory = System.getProperty("user.home") + File.separator + "Rating" + File.separator + "Player";
    static final FlowPane pane = new FlowPane();
    public Player(){}
    public Player(String directory, String add, String title){
        super(directory,add,pane,title,1100);
        itemAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                about("Add a Player","The icon resolution 160x200\nThe icon1,2,3,4 resolution 300x200");
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
                PlayerPane[] panes = new PlayerPane[pane.getChildren().size()];
                for (int i=0;i<pane.getChildren().size();i++) {
                    panes[i] = (PlayerPane) pane.getChildren().get(i);
                }
                pane.getChildren().clear();
            }
        });
        itemSearch.setText("Search a Player");
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
        TextField[] textFields = new TextField[9];
        Button[] buttons = new Button[6];
        CheckBox[] checkBoxes = new CheckBox[platforms.length];
        TextArea area = new TextArea("Description");
        TextArea areaComposition = new TextArea("Composition");
        Stage stage = new Stage();
        ListView<String> listView = new ListView<>();
        TextArea areaA = new TextArea();
        GUI(textFields,buttons,checkBoxes,area,areaComposition,"Add Player","Add  Player to the library",stage,platforms,listView,areaA);
        buttons[0].setTooltip(new Tooltip("Icon resolution - 160x200"));
        buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooser(chooser,chooserStage,textFields[1]);
            }
        });
        buttons[1].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooser(chooser,chooserStage,textFields[2]);
            }
        });
        buttons[2].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooser(chooser,chooserStage,textFields[3]);
            }
        });
        buttons[3].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[3].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooser(chooser,chooserStage,textFields[4]);
            }
        });
        buttons[4].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[4].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooser(chooser,chooserStage,textFields[5]);
            }
        });
        buttons[5].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[5].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                homeDirectory +=File.separator+textFields[0].getText();
                if (textFields[0].getText().isEmpty()||areaComposition.getText().isEmpty()||area.getText().isEmpty()){
                    check("title or description or composition");
                    return;
                } if (textFields[1].getText().isEmpty()||textFields[2].getText().isEmpty()||textFields[3].getText().isEmpty()||textFields[4].getText().isEmpty()||textFields[5].getText().isEmpty()){
                    check("icons");
                    return;
                } if (textFields[6].getText().isEmpty()||textFields[7].getText().isEmpty()||textFields[8].getText().isEmpty()){
                    check("country or one of years");
                    return;
                }
                try {
                    if (Files.exists(Paths.get(homeDirectory))){
                        isExists();
                        return;
                    }
                    Files.createDirectory(Paths.get(homeDirectory));
                    Files.copy(Paths.get(textFields[1].getText()), Paths.get(homeDirectory + File.separator + textFields[0].getText()).resolveSibling("icon"), StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(Paths.get(textFields[2].getText()), Paths.get(homeDirectory + File.separator + textFields[0].getText()).resolveSibling("icon1"), StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(Paths.get(textFields[3].getText()), Paths.get(homeDirectory + File.separator + textFields[0].getText()).resolveSibling("icon2"), StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(Paths.get(textFields[4].getText()), Paths.get(homeDirectory + File.separator + textFields[0].getText()).resolveSibling("icon3"), StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(Paths.get(textFields[5].getText()), Paths.get(homeDirectory + File.separator + textFields[0].getText()).resolveSibling("icon4"), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Failed to create homeDirectory " + textFields[0].getText() + " in /home/" + System.getProperty("homeDirectory.home"));
                    alert.showAndWait();
                }
                try(BufferedWriter br  = new BufferedWriter(new FileWriter(homeDirectory + File.separator + "albums"))){
                    if (areaA.getText().isEmpty()){
                        br.write("unknown");
                    } else {
                        br.write(areaA.getText());
                        String[] s = areaA.getText().trim().split("\n");
                        for (int i=0;i < s.length;i++){
                            setTo("Album",s[i],"player",textFields[0].getText(),false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                editFile(homeDirectory + File.separator + "title",textFields[0].getText());
                editFile(homeDirectory + File.separator + "yearF",textFields[6].getText());
                editFile(homeDirectory + File.separator + "yearD",textFields[7].getText());
                editFile(homeDirectory + File.separator + "country",textFields[8].getText());
                editFile(homeDirectory + File.separator + "description",area.getText());
                editFile(homeDirectory + File.separator + "composition",areaComposition.getText());
                PlayerPane playerPane = new PlayerPane(textFields[0].getText(),area.getText(),textFields[1].getText(),textFields[2].getText(),
                        textFields[3].getText(),textFields[4].getText(),textFields[5].getText(),areaComposition.getText(),
                        textFields[8].getText(),Short.parseShort(textFields[6].getText()),textFields[7].getText(),listView.getSelectionModel().getSelectedItem());
                for (int i = 0; i< checkBoxes.length; i++) {
                    if (checkBoxes[i].isSelected()){
                        playerPane.isPlatforms[i]=true;
                        try {
                            Files.createFile(Paths.get(homeDirectory +File.separator+platforms[i]));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                pane.getChildren().add(playerPane);
                stage.close();
                platformsInit(playerPane.isPlatforms,platforms,playerPane.flowPane,"music","jpg");
                homeDirectory = System.getProperty("user.home") + File.separator + "Rating" + File.separator + "Player";
            }
        });
    }

    void load(File directory,FlowPane pane){
        short yearOfFoundation=0;
        StringBuilder sb = null,sbt = null,sbc = null,sba = null;
        String yearD = null,country = null;
        File[] files = directory.listFiles();
        PlayerPane playerPane = new PlayerPane();
        for (File file: files) {
            if (file.isDirectory()) {
                load(file,pane);
            }  if (file.getName().equals("icon")) {
                playerPane.setIconPath(file.getAbsolutePath());
            }  if (file.getName().equals("icon1")) {
                playerPane.setIconPath1(file.getAbsolutePath());
            }  if (file.getName().equals("icon2")) {
                playerPane.setIconPath2(file.getAbsolutePath());
            }  if (file.getName().equals("icon3")) {
                playerPane.setIconPath3(file.getAbsolutePath());
            }  if (file.getName().equals("icon4")) {
                playerPane.setIconPath4(file.getAbsolutePath());
            } if (file.getName().equals("description")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    sb = new StringBuilder();
                    while ((text = br.readLine()) != null) {
                        sb.append(text);
                        sb.append("\n");
                    }
                    playerPane.setDescription(sb.toString());
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            }  if (file.getName().equals("yearF")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    while ((text = br.readLine()) != null) {
                        yearOfFoundation = Short.parseShort(text);
                    }
                    playerPane.setYearOfFoundation(yearOfFoundation);
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
                    playerPane.setTitle(sbt.toString());
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            }  if (file.getName().equals("yearD")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    while ((text = br.readLine()) != null) {
                        yearD=text;
                    }
                    playerPane.setYearOfDisintegration(yearD);
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            } if (file.getName().equals("composition")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    sbc = new StringBuilder();
                    while ((text = br.readLine()) != null) {
                        sbc.append(text);
                        sbc.append("\n");
                    }
                    playerPane.setComposition(sbc.toString());
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            } if (file.getName().equals("country")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    while ((text = br.readLine()) != null) {
                        country=text;
                    }
                    playerPane.setCountry(country);
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            } if (file.getName().equals("albums")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    sba = new StringBuilder();
                    while ((text = br.readLine()) != null) {
                        sba.append(text);
                        sba.append("\n");
                    }
                    playerPane.setAlbum(sba.toString());
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            }

            for (int i=0;i<platforms.length;i++){
                if (file.getName().equals(platforms[i])){
                    createImage(platforms[i],playerPane.flowPane,"music","jpg");

                }
            }
            if (sb!=null&sbt!=null&yearOfFoundation!=0&sbc!=null&yearD!=null&country!=null&sba!=null) {
                playerPane.createGUI();
                pane.getChildren().add(playerPane);
                sbt=null;sb=null;sbc=null;country=null;yearD=null;sba=null;
            }

        }
    }

     void GUI(TextField[] fields, Button[] buttons, CheckBox[] checkBoxes,
              TextArea area,TextArea areaComposit, String title, String action,
              Stage stage, String[] platforms,ListView<String> listView,TextArea areaA) {
        String[] titles = {"Choose a title icon","Choose a icon 1","Choose a icon 2","Choose a icon 3",
                "Choose a icon 4",action};
        for (int i=0;i<platforms.length;i++){
            checkBoxes[i] = new CheckBox(platforms[i]);
            if (checkBoxes[i].getText().equals(platforms[0])) {
                checkBoxes[i].setLayoutX(340);
                checkBoxes[i].setLayoutY(35);
            } if (checkBoxes[i].getText().equals(platforms[1])) {
                checkBoxes[i].setLayoutX(470);
                checkBoxes[i].setLayoutY(35);
            } if (checkBoxes[i].getText().equals(platforms[2])) {
                checkBoxes[i].setLayoutX(340);
                checkBoxes[i].setLayoutY(55);
            } if (checkBoxes[i].getText().equals(platforms[3])) {
                checkBoxes[i].setLayoutX(470);
                checkBoxes[i].setLayoutY(55);
            } if (checkBoxes[i].getText().equals(platforms[4])) {
                checkBoxes[i].setLayoutX(340);
                checkBoxes[i].setLayoutY(75);
            } if (checkBoxes[i].getText().equals(platforms[5])) {
                checkBoxes[i].setLayoutX(470);
                checkBoxes[i].setLayoutY(75);
            } if (checkBoxes[i].getText().equals(platforms[7])) {
                checkBoxes[i].setLayoutX(340);
                checkBoxes[i].setLayoutY(95);
            } if (checkBoxes[i].getText().equals(platforms[6])) {
                checkBoxes[i].setLayoutX(470);
                checkBoxes[i].setLayoutY(95);
            } if (checkBoxes[i].getText().equals(platforms[8])) {
                checkBoxes[i].setLayoutX(340);
                checkBoxes[i].setLayoutY(115);
            } if (checkBoxes[i].getText().equals(platforms[9])) {
                checkBoxes[i].setLayoutX(470);
                checkBoxes[i].setLayoutY(115);
            } if (checkBoxes[i].getText().equals(platforms[10])) {
                checkBoxes[i].setLayoutX(340);
                checkBoxes[i].setLayoutY(135);
            } if (checkBoxes[i].getText().equals(platforms[11])) {
                checkBoxes[i].setLayoutX(470);
                checkBoxes[i].setLayoutY(135);
            } if (checkBoxes[i].getText().equals(platforms[12])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(155);
            } if (checkBoxes[i].getText().equals(platforms[13])) {
                checkBoxes[i].setLayoutX(85);
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
        fields[0].setLayoutX(5);
        fields[0].setLayoutY(5);
        fields[1].setLayoutX(210);
        fields[1].setLayoutY(5);
        fields[1].setPrefSize(250,20);
        fields[2].setLayoutX(5);
        fields[2].setLayoutY(35);
        fields[3].setLayoutX(5);
        fields[3].setLayoutY(65);
        fields[4].setLayoutX(5);
        fields[4].setLayoutY(95);
        fields[5].setLayoutX(5);
        fields[5].setLayoutY(125);
        fields[6].setText("Foundation");
        fields[7].setText("Disintegration");
        fields[8].setText("Country");
        fields[6].setLayoutX(410);
        fields[6].setLayoutY(335);
        fields[7].setLayoutX(410);
        fields[7].setLayoutY(365);
        fields[8].setLayoutX(5);
        fields[8].setLayoutY(660);
        for (int i=0;i<buttons.length;i++){
            buttons[i] = new Button(titles[i]);
        }
        buttons[0].setLayoutX(470);
        buttons[0].setLayoutY(5);
        buttons[1].setLayoutX(210);
        buttons[1].setLayoutY(35);
        buttons[2].setLayoutX(210);
        buttons[2].setLayoutY(65);
        buttons[3].setLayoutX(210);
        buttons[3].setLayoutY(95);
        buttons[4].setLayoutX(210);
        buttons[4].setLayoutY(125);
        buttons[5].setLayoutX(430);
        buttons[5].setLayoutY(660);
        area.setPrefSize(400,475);
        area.setLayoutX(5);
        area.setLayoutY(180);
        area.setWrapText(true);
        areaComposit.setPrefSize(200,150);
        areaComposit.setLayoutX(410);
        areaComposit.setLayoutY(180);
        listView.setPrefSize(200,100);
        listView.setLayoutX(410);
        listView.setLayoutY(395);
        areaA.setWrapText(true);
        areaA.setPrefSize(200,155);
        areaA.setLayoutX(410);
        areaA.setLayoutY(500);
        String albumDirectory = System.getProperty("user.home") + File.separator + "Rating" + File.separator + "Album";
         File[] files = new File(albumDirectory).listFiles();
         ArrayList<String> arrayList = new ArrayList<>();
         if (files!=null) {
             for (File file : files) {
                 if (file.isDirectory()) {
                     arrayList.add(file.getName());
                 }
             }
         }
         ObservableList<String> list = FXCollections.observableArrayList(arrayList);
         listView.setItems(list);
         listView.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>() {
             @Override
             public void onChanged(Change<? extends Integer> c) {
                 areaA.appendText(listView.getSelectionModel().getSelectedItem()+"\n");
             }
         });

         root.getChildren().addAll(fields[0],fields[1],fields[2],buttons[0],fields[3],
                buttons[1],fields[4],buttons[2],fields[5],buttons[3],fields[6],buttons[4],
                area,buttons[5],fields[7],fields[8],areaComposit,listView,areaA);
        for (int i=0;i<checkBoxes.length;i++) {
            root.getChildren().addAll(checkBoxes[i]);
        }
        stage.setScene(new Scene(root,615,720));
        stage.setResizable(false);
        stage.show();
    }
}