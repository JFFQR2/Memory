
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;

public class Movie extends Construct {
    String homeDirectory = System.getProperty("user.home") + File.separator + "Rating" + File.separator +
            "Movie";
    static final FlowPane pane = new FlowPane();
    final String[] platforms = {"Drama","Science-fiction","Thriller","Horror","Fantasy",
            "Action","Adventure","Detective fiction","Comedy","Documentary","Educational",
            "Game show","Instructional", "Music television","Reality","Sports","Talk show",
            "Television documentary","Serial","Other"};
    public Movie(){}
    public Movie(String directory, String add, String title){
        super(directory,add,pane,title,800);
        itemAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                about("   Rate your \nfavorite Movie","The icon resolution 160x200\nThe icon1,2,3,4 resolution 300x200");
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
                MoviePane[] panes = new MoviePane[pane.getChildren().size()];
                for (int i=0;i<pane.getChildren().size();i++) {
                    panes[i] = (MoviePane) pane.getChildren().get(i);
                }
                pane.getChildren().clear();
            }
        });
        itemSearch.setText("Search a movie");
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
        TextField[] textFields = new TextField[8];
        Button[] buttons = new Button[6];
        CheckBox[] checkBoxes = new CheckBox[platforms.length];
        TextArea area = new TextArea("Description");
        Stage stage = new Stage();
        GUI(textFields,buttons,checkBoxes,area,"Add movie","Add  movie to the library",stage,platforms);
        buttons[0].setTooltip(new Tooltip("Icon resolution - 160x200"));
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
                } if (textFields[7].getText().isEmpty()){
                    check("year");
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
                    alert.setHeaderText("Failed to create homeDirectory " + textFields[0].getText() + " in /home/" + System.getProperty("homeDirectory.home"));
                    alert.showAndWait();
                }
                editTRD(homeDirectory,area,textFields[1].getText(),textFields[0].getText());
                editFile(homeDirectory + File.separator + "year",textFields[7].getText());
                MoviePane moviePane = new MoviePane(textFields[2].getText(),textFields[3].getText(),
                        textFields[4].getText(),textFields[5].getText(),textFields[6].getText(),
                        textFields[0].getText(),area.getText(),Float.parseFloat(textFields[1].getText()),
                        Short.parseShort(textFields[7].getText()));
                for (int i = 0; i< checkBoxes.length; i++) {
                    if (checkBoxes[i].isSelected()){
                        moviePane.isPlatforms[i]=true;
                        try {
                            Files.createFile(Paths.get(homeDirectory +File.separator+platforms[i]));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                pane.getChildren().add(moviePane);
                stage.close();
                platformsInit(moviePane.isPlatforms,platforms,moviePane.flowPane,"movie","png");
                homeDirectory = System.getProperty("user.home") + File.separator + "Rating" + File.separator + "Movie";
            }
        });
    }

    void load(File directory,FlowPane pane){
        float rating=0f;
        short year=0;
        StringBuilder sb = null,sbt = null;
        File[] files = directory.listFiles();
        MoviePane moviePane = new MoviePane();
        for (File file: files) {
            if (file.isDirectory()) {
                load(file,pane);
            }  if (file.getName().equals("icon")) {
                moviePane.setIconPath(file.getAbsolutePath());
            }  if (file.getName().equals("icon1")) {
                moviePane.setIconPath1(file.getAbsolutePath());
            }  if (file.getName().equals("icon2")) {
                moviePane.setIconPath2(file.getAbsolutePath());
            }  if (file.getName().equals("icon3")) {
                moviePane.setIconPath3(file.getAbsolutePath());
            }  if (file.getName().equals("icon4")) {
                moviePane.setIconPath4(file.getAbsolutePath());
            } if (file.getName().equals("description")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    sb = new StringBuilder();
                    while ((text = br.readLine()) != null) {
                        sb.append(text);
                        sb.append("\n");
                    }
                    moviePane.setDescription(sb.toString());
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
                    moviePane.setRating(rating);
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
                    moviePane.setTitle(sbt.toString());
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            }  if (file.getName().equals("year")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                    String text = null;
                    while ((text = br.readLine()) != null) {
                        year = Short.parseShort(text);
                    }
                    moviePane.setYear(year);
                } catch (IOException exp) {
                    exp.printStackTrace();
                    alertError(file.getName(),sRead);
                }
            }

            for (int i=0;i<platforms.length;i++){
                if (file.getName().equals(platforms[i])){
                    createImage(platforms[i],moviePane.flowPane,"movie","png");

                }
            }
            if (sb!=null&sbt!=null&rating!=0&year!=0) {
                moviePane.createGUI();
                pane.getChildren().add(moviePane);
                sbt=null;sb=null;rating=0;year=0;
            }

        }
    }
    void GUI(TextField[] fields, Button[] buttons, CheckBox[] checkBoxes,TextArea area, String title, String action, Stage stage, String[] platforms) {
        String[] titles = {"Choose a title icon","Choose a icon 1","Choose a icon 2","Choose a icon 3",
                "Choose a icon 4",action};
        for (int i=0;i<platforms.length;i++){
            checkBoxes[i] = new CheckBox(platforms[i]);
            if (checkBoxes[i].getText().equals(platforms[0])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(155);
            } if (checkBoxes[i].getText().equals(platforms[1])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(155);
            } if (checkBoxes[i].getText().equals(platforms[2])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(175);
            } if (checkBoxes[i].getText().equals(platforms[3])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(175);
            } if (checkBoxes[i].getText().equals(platforms[4])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(195);
            } if (checkBoxes[i].getText().equals(platforms[5])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(195);
            } if (checkBoxes[i].getText().equals(platforms[7])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(215);
            } if (checkBoxes[i].getText().equals(platforms[6])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(215);
            } if (checkBoxes[i].getText().equals(platforms[8])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(235);
            } if (checkBoxes[i].getText().equals(platforms[9])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(235);
            } if (checkBoxes[i].getText().equals(platforms[10])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(255);
            } if (checkBoxes[i].getText().equals(platforms[11])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(255);
            } if (checkBoxes[i].getText().equals(platforms[12])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(275);
            } if (checkBoxes[i].getText().equals(platforms[13])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(275);
            } if (checkBoxes[i].getText().equals(platforms[14])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(295);
            } if (checkBoxes[i].getText().equals(platforms[15])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(295);
            } if (checkBoxes[i].getText().equals(platforms[16])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(315);
            } if (checkBoxes[i].getText().equals(platforms[17])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(315);
            } if (checkBoxes[i].getText().equals(platforms[18])) {
                checkBoxes[i].setLayoutX(5);
                checkBoxes[i].setLayoutY(335);
            } if (checkBoxes[i].getText().equals(platforms[19])) {
                checkBoxes[i].setLayoutX(175);
                checkBoxes[i].setLayoutY(335);
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
        fields[3].setLayoutX(5);
        fields[3].setLayoutY(35);
        fields[4].setLayoutX(5);
        fields[4].setLayoutY(65);
        fields[5].setLayoutX(5);
        fields[5].setLayoutY(95);
        fields[6].setLayoutX(5);
        fields[6].setLayoutY(125);
        fields[7].setPrefSize(270,20);
        fields[7].setText("Year");
        fields[7].setLayoutX(340);
        fields[7].setLayoutY(35);
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
        area.setPrefSize(605,250);
        area.setLayoutX(5);
        area.setLayoutY(400);
        area.setWrapText(true);
        root.getChildren().addAll(fields[0],fields[1],fields[2],fields[3],fields[4],
                fields[5],fields[6],fields[7],area,buttons[0],buttons[1],buttons[2],buttons[3],
                buttons[4],buttons[5]);
        for (int i=0;i<checkBoxes.length;i++) {
            root.getChildren().addAll(checkBoxes[i]);
        }
        stage.setScene(new Scene(root,615,720));
        stage.setResizable(false);
        stage.show();
    }
}