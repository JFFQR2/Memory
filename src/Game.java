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
import java.util.Arrays;
import java.util.Comparator;

public class Game extends Construct {
    String homeDirectory = System.getProperty("user.home") + File.separator + "Rating" + File.separator +
            "Games";
    static final FlowPane pane = new FlowPane();
    final String[] platforms = {"Nes","Snes","Nintendo 64","Nintendo Switch","Nintendo Wii","Nintendo Wii U",
            "PC","Sega Genesis","PlayStation One","PlayStation 2","PlayStation 3","PlayStation 4","Xbox",
            "Xbox 360","Xbox One"};
    public Game(){}
    public Game(String directory, String add, String title){
        super(directory,add,pane,title,800);
        //about("Rate your \n  favorite game","The icon resolution 150x200\nThe icon1,2,3,4 resolution 300x200");
        itemAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                about("Rate your \n  favorite game","The icon resolution 150x200\nThe icon1,2,3,4 resolution 300x200");
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
                GamePane[] panes = new GamePane[pane.getChildren().size()];
                for (int i=0;i<pane.getChildren().size();i++) {
                    panes[i] = (GamePane) pane.getChildren().get(i);
                }
                pane.getChildren().clear();
            }
        });
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
        TextField[] textFields = new TextField[7];
        Button[] buttons = new Button[6];
        CheckBox[] checkBoxes = new CheckBox[platforms.length];
        TextArea area = new TextArea("Description");
        Stage stage = new Stage();
        GUI(textFields,buttons,checkBoxes,area,"Add game","Add  game to the library",stage,platforms);
        buttons[0].setTooltip(new Tooltip("Icon resolution - 150x200"));
        buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooser(chooser,chooserStage,textFields[2]);
            }
        });
        buttons[1].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooser(chooser,chooserStage,textFields[3]);
            }
        });
        buttons[2].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooser(chooser,chooserStage,textFields[4]);
            }
        });
        buttons[3].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[3].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooser(chooser,chooserStage,textFields[5]);
            }
        });
        buttons[4].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[4].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooser(chooser,chooserStage,textFields[6]);
            }
        });
        buttons[5].setTooltip(new Tooltip("Icon resolution - 300x200"));
        buttons[5].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                homeDirectory +=File.separator+textFields[0].getText();
                if (textFields[0].getText().isEmpty()||textFields[1].getText().isEmpty()||area.getText().isEmpty()){
                    check("title or rating or description");
                    return;
                } if (textFields[2].getText().isEmpty()||textFields[3].getText().isEmpty()||textFields[4].getText().isEmpty()||textFields[5].getText().isEmpty()||textFields[6].getText().isEmpty()){
                    check("icon");
                    return;
                }
                try {
                    if (Files.exists(Paths.get(homeDirectory))){
                        isExists();
                        return;
                    }
                    Files.createDirectory(Paths.get(homeDirectory));
                    Files.copy(Paths.get(textFields[2].getText()), Paths.get(homeDirectory + File.separator + textFields[0].getText()).resolveSibling("icon"), StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(Paths.get(textFields[3].getText()), Paths.get(homeDirectory + File.separator + textFields[0].getText()).resolveSibling("icon1"), StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(Paths.get(textFields[4].getText()), Paths.get(homeDirectory + File.separator + textFields[0].getText()).resolveSibling("icon2"), StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(Paths.get(textFields[5].getText()), Paths.get(homeDirectory + File.separator + textFields[0].getText()).resolveSibling("icon3"), StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(Paths.get(textFields[6].getText()), Paths.get(homeDirectory + File.separator + textFields[0].getText()).resolveSibling("icon4"), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Failed to create homeDirectory " + textFields[0].getText() + " in /home/" + System.getProperty("user.home"));
                    alert.showAndWait();
                }
                editTRD(homeDirectory,area,textFields[1].getText(),textFields[0].getText());
                GamePane gamePane = new GamePane(textFields[2].getText(),textFields[3].getText(),textFields[4].getText(),textFields[5].getText(),textFields[6].getText(),textFields[0].getText(),area.getText(),Float.parseFloat(textFields[1].getText()));
                for (int i = 0; i< checkBoxes.length; i++) {
                    if (checkBoxes[i].isSelected()){
                        gamePane.isPlatforms[i]=true;
                        try {
                            Files.createFile(Paths.get(homeDirectory +File.separator+platforms[i]));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                platformsInit(gamePane.isPlatforms,platforms,gamePane.flowPane,"game","jpg");
                pane.getChildren().add(gamePane);
                stage.close();
                homeDirectory = System.getProperty("user.home") + File.separator + "Rating" + File.separator + "Games";
            }
        });
    }

    void load(File directory,FlowPane pane){
        float rating=0f;
        StringBuilder sb = null,sbt = null;
        File[] files = directory.listFiles();
        GamePane gamePane = new GamePane();
        for (File file: files) {
            if (file.isDirectory()) {
                load(file,pane);
            }  if (file.getName().equals("icon")) {
                gamePane.setIconPath(file.getAbsolutePath());
            }  if (file.getName().equals("icon1")) {
                gamePane.setIconPath1(file.getAbsolutePath());
            }  if (file.getName().equals("icon2")) {
                gamePane.setIconPath2(file.getAbsolutePath());
            }  if (file.getName().equals("icon3")) {
                gamePane.setIconPath3(file.getAbsolutePath());
            }  if (file.getName().equals("icon4")) {
                gamePane.setIconPath4(file.getAbsolutePath());
            } if (file.getName().equals("description")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    sb = new StringBuilder();
                    while ((text = br.readLine()) != null) {
                        sb.append(text);
                        sb.append("\n");
                    }
                    gamePane.setDescription(sb.toString());
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            }  if (file.getName().equals("rating")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    while ((text = br.readLine()) != null) {
                        rating = Float.parseFloat(text);
                    }
                    gamePane.setRating(rating);
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
                    gamePane.setTitle(sbt.toString());
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            }

            for (int i=0;i<platforms.length;i++){
                if (file.getName().equals(platforms[i])){
                    createImage(platforms[i],gamePane.flowPane,"game","png");

                }
            }
            if (sb!=null&sbt!=null&rating!=0) {
                gamePane.createGUI();
                pane.getChildren().add(gamePane);
                sbt=null;sb=null;rating=0;
            }

        }
    }
}