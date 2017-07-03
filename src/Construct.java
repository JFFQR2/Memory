import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;

public class Construct extends Stage {
    public final String sCreate = "create";
    public final String sRead = "read";
    public final String sDelete = "delete";
    public final String sMove = "move";
    protected final String[] platforms = {"Blues","Jazz","Country","Electronic","Pop","HipHop","Rock","Latin",
            "R & B and Soul","Asian","Folk","EasyListening","Comedy","Exclusions"};
    FileChooser chooser;
    Stage chooserStage;
    protected final String home = System.getProperty("user.home") + File.separator + "Rating";
    protected MenuItem itemAdd,itemLoad,itemAbout,itemSort,itemSortPlatforfm,itemSearch;
    protected Menu menuAdd,menuEdit,menuAbout,
            menuSort,menuSearch;
    protected Construct(){}
    protected Construct(String directory, String add, FlowPane pane,String title,int width){
    MenuBar menuBar = new MenuBar();
        menuAdd = new Menu("Add");
        menuEdit = new Menu("Load");
        menuAbout = new Menu("Info");
        menuSort = new Menu("Sort");
        menuSearch = new Menu("Search");
    directoryCreate(directory);
        itemAdd = new MenuItem(add);
        itemLoad = new MenuItem("Load a library");
        itemAbout = new MenuItem("Info");
        itemSort = new MenuItem("For Name");
        itemSortPlatforfm = new MenuItem("For Platforms");
        itemSearch = new MenuItem("Search a game");
        menuAdd.getItems().add(itemAdd);
        menuEdit.getItems().add(itemLoad);
        menuSort.getItems().addAll(itemSort,itemSortPlatforfm);
        menuSearch.getItems().add(itemSearch);
        menuAbout.getItems().add(itemAbout);
        ScrollPane scrollPane = new ScrollPane();
        BorderPane root = new BorderPane();
        scrollPane.setContent(pane);
        root.setTop(menuBar);
        root.setCenter(scrollPane);
        this.setTitle(title);
        this.setScene(new Scene(root, width, 720));
        menuBar.getMenus().addAll(menuAdd,menuEdit,menuSort,menuSearch,menuAbout);
        this.setResizable(false);
        this.show();
}

    protected void directoryCreate(String directory){
        Path directoryPath = Paths.get(home + File.separator + directory);
         if (!Files.exists(directoryPath)){
            try {
                Files.createDirectory(directoryPath);
            } catch (IOException e1) {
                e1.printStackTrace();
                alertError(directoryPath.toString(),sCreate);
            }
        }
    }

    protected void about(String head,String note) {
        Pane root = new Pane();
        Label label = new Label(head);
        label.setFont(Font.font("Monospace", FontWeight.BOLD,14));
        TextArea area = new TextArea("Note:\n" + note);
        area.setEditable(false);
        label.setLayoutX(65);
        label.setLayoutY(5);
        area.setLayoutX(5);
        area.setLayoutY(50);
        area.setPrefSize(290,60);
        root.getChildren().addAll(label,area);
        Stage stage = new Stage();
        stage.setTitle("Info");
        stage.setScene(new Scene(root,300,120));
        stage.setResizable(false);
        stage.show();
    }

    protected void editTRD(String homeDirectory,TextArea area,String rating,String title){
        try (BufferedWriter br = new BufferedWriter(new FileWriter(homeDirectory +File.separator+"description"))) {
            if (area.getText().isEmpty()){
                area.setText("Description");
            }
            br.write(area.getText());
        } catch (IOException eD) {
            eD.printStackTrace();
            alertError(homeDirectory + File.separator + "description",sCreate);
        }
        try (BufferedWriter br = new BufferedWriter(new FileWriter(homeDirectory +File.separator+"rating"))) {
            br.write(rating);
        } catch (IOException eR) {
            eR.printStackTrace();
            alertError(homeDirectory + File.separator + "rating",sCreate);
        }
        try (BufferedWriter br = new BufferedWriter(new FileWriter(homeDirectory +File.separator+"title"))) {
            br.write(title);
        } catch (IOException eT) {
            eT.printStackTrace();
            alertError(homeDirectory +File.separator+"title",sCreate);
        }
    }

    public void chooser(FileChooser chooser, Stage chooserStage, TextField field){
        if (chooser == null)
            chooser = new FileChooser();
        if (chooserStage == null) {
            chooserStage = new Stage();
        }
        File selectedDirectory = chooser.showOpenDialog(chooserStage);
        if (selectedDirectory != null) {
            field.setText(selectedDirectory.getAbsolutePath());
        }
    }

     void alertError(final String path,final String action){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Failed to " + action + " file " + path);
    }

    void deleteTRD(String directory){
        try {
            Files.delete(Paths.get(directory + File.separator + "title"));
        } catch (IOException e) {
            e.printStackTrace();
            alertError(directory + File.separator + "title",sDelete);
        }
        try {
            Files.delete(Paths.get(directory + File.separator + "description"));
        } catch (IOException e) {
            e.printStackTrace();
            alertError(directory + File.separator + "description",sDelete);
        }
        try {
            Files.delete(Paths.get(directory + File.separator + "rating"));
        } catch (IOException e) {
            e.printStackTrace();
            alertError(directory + File.separator + "rating",sDelete);
        }
    }

     void change(final String oldPath, final String newPath, final String directory, final String name){
        if (!(oldPath.equals(newPath))){
            try {
                Files.copy(Paths.get(newPath),Paths.get(directory).resolveSibling(name), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Failed to copy file " + newPath);
            }
        }
    }

    void GUI (TextField[] fields,Button[] buttons,CheckBox[] checkBoxes,TextArea area,String title,String action,Stage stage,String[] platforms){
        String[] titles = {"Choose a title icon","Choose a icon 1","Choose a icon 2","Choose a icon 3",
                "Choose a icon 4",action};
        for (int i=0;i<platforms.length;i++){
            checkBoxes[i] = new CheckBox(platforms[i]);
            if (checkBoxes[i].getText().equals(platforms[0])) {
                checkBoxes[i].setLayoutX(340);
                checkBoxes[i].setLayoutY(35);
            } if (checkBoxes[i].getText().equals(platforms[1])) {
                checkBoxes[i].setLayoutX(400);
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
                checkBoxes[i].setLayoutX(65);
                checkBoxes[i].setLayoutY(155);
            } if (checkBoxes[i].getText().equals(platforms[14])) {
                checkBoxes[i].setLayoutX(155);
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
        fields[3].setLayoutX(5);
        fields[3].setLayoutY(35);
        fields[4].setLayoutX(5);
        fields[4].setLayoutY(65);
        fields[5].setLayoutX(5);
        fields[5].setLayoutY(95);
        fields[6].setLayoutX(5);
        fields[6].setLayoutY(125);
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
        buttons[5].setLayoutX(435);
        buttons[5].setLayoutY(660);
        area.setPrefSize(605,475);
        area.setLayoutX(5);
        area.setLayoutY(180);
        area.setWrapText(true);
        root.getChildren().addAll(fields[0],fields[1],fields[2],buttons[0],fields[3],
                buttons[1],fields[4],buttons[2],fields[5],buttons[3],fields[6],buttons[4],area,buttons[5]);
        for (int i=0;i<checkBoxes.length;i++) {
            root.getChildren().addAll(checkBoxes[i]);
        }
        stage.setScene(new Scene(root,615,720));
        stage.setResizable(false);
        stage.show();
    }

    void setCheckBoxSelected(File[] files,String[] platforms,CheckBox[] checkBoxes){
        for (File file:files) {
            for (int i = 0; i< platforms.length; i++){
                if (file.getName().equals(checkBoxes[i].getText())) {
                    checkBoxes[i].setSelected(true);
                }
            }
        }
    }

    void deleteIfCheckBoxNotSelected(File[] files,String[] platforms,CheckBox[] checkBoxes,String currentDirectory){
        for (File file:files) {
            for (int i = 0; i< platforms.length; i++){
                if (!checkBoxes[i].isSelected()){
                    try {
                        Files.deleteIfExists(Paths.get(currentDirectory+File.separator+checkBoxes[i].getText()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        alertError(currentDirectory+File.separator+checkBoxes[i].getText(),sDelete);
                    }
                }
            }
        }
    }

    void moveFiles(File[] files,String[] platforms,String directory){
        for (File file:files) {
            for (int i = 0; i< platforms.length; i++){
                if (file.getName().equals(platforms[i])) {
                    try {
                        Files.move(Paths.get(file.getAbsolutePath()),Paths.get(directory+File.separator+ platforms[i]));
                    } catch (IOException e) {
                        e.printStackTrace();
                        alertError(directory+File.separator+ platforms[i],sMove);
                    }
                }
            }
        }
    }

    void createFileIfCheckBoxSelected(CheckBox[] checkBoxes,String directory,String[] platforms){
        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isSelected()) {
                try {
                    if (!Files.exists(Paths.get(directory + File.separator + platforms[i]))) {
                        Files.createFile(Paths.get(directory + File.separator + platforms[i]));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    alertError(directory + File.separator + platforms[i],sCreate);
                }
            }
        }
    }
    void createImage(String name,FlowPane flowPane,String path,String format){
        ImageView imageView = new ImageView(new Image(path+"/"+name+"." + format));
        flowPane.getChildren().add(imageView);
    }
    public void platformsInit(boolean[] isPlatforms,String[] platforms,FlowPane flowPane,String path,String format){
        for (int i=0;i<isPlatforms.length;i++){
            if (isPlatforms[i]){
                createImage(platforms[i],flowPane,path,format);
            }
        }
    }

    public void editFile(String homeDirectory,String value){
        try (BufferedWriter br = new BufferedWriter(new FileWriter(homeDirectory))) {
            if (value!=null)
            br.write(value);
            else br.write("unknown");
        } catch (IOException eT) {
            eT.printStackTrace();
            alertError(homeDirectory,sCreate);
        }
    }

    protected void setIconPath(String mainDirectory,String name,String directory) {
        File[] files = new File(System.getProperty("user.home") + File.separator + "Rating" + File.separator + mainDirectory).listFiles();
        for (File file : files) {
            if (file.getName().equals(name)) {
                setIconPath(mainDirectory + File.separator + file.getName(), name,directory);
            }
            if (file.getName().equals("icon")) {
                try (BufferedWriter br  = new BufferedWriter(new FileWriter(directory))){
                    br.write(file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    alertError(directory,sCreate);
                }
            }
        }
    }

    protected String getIconPath(String mainDirectory,String name) {
        String path = null;
        File[] files = new File(mainDirectory).listFiles();
        for (File file : files) {
            if (file.getName().equals(name)) {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
                    String text = null;
                    while ((text=br.readLine())!=null){
                        path = text;
                    }
                    return path;
                } catch (IOException e) {
                    e.printStackTrace();
                    alertError(mainDirectory, sCreate);
                }
            }
        }
        return path;
    }
        protected void setTo(String directory, String name, String search, String write,boolean addToEnd){
        File[] files = new File(System.getProperty("user.home") + File.separator + "Rating" + File.separator + directory).listFiles();
        for (File file:files){
            if (file.getName().equals(name)){
                setTo(directory+File.separator+file.getName(),name,search,write,addToEnd);
            }
            if (file.getName().equals(search)){
                try (BufferedWriter br  = new BufferedWriter(new FileWriter(file.getAbsolutePath(),addToEnd))){
                    br.write(write + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                    alertError(file.getAbsolutePath(),sCreate);
                }
            }
        }
    }
    protected void search(String search, FlowPane pane){
        ConstructPane[] panes = new ConstructPane[pane.getChildren().size()];
        for (int i = 0; i< pane.getChildren().size(); i++) {
            panes[i] = (ConstructPane) pane.getChildren().get(i);
        }
        pane.getChildren().clear();
        for (int i=0;i<panes.length;i++){
            if (panes[i].getTitle().contains(search)) {
                pane.getChildren().add(panes[i]);
            }
        }
    }
    protected void sort(FlowPane pane){
        ConstructPane[] panes = new ConstructPane[pane.getChildren().size()];
        for (int i=0;i<pane.getChildren().size();i++) {
            panes[i] = (ConstructPane) pane.getChildren().get(i);
        }
        Arrays.sort(panes, new Comparator<ConstructPane>() {
            @Override
            public int compare(ConstructPane o1, ConstructPane o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        pane.getChildren().clear();
        pane.getChildren().addAll(panes);
    }

    protected void check(String name){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Field is Empty!");
        alert.setHeaderText("Please, fill " + name + " fields");
        alert.showAndWait();
    }

    protected void isExists(){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exists!!");
            alert.setHeaderText("File is exists! Please, enter a other title");
            alert.showAndWait();
    }
    }