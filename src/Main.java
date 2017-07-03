import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main extends Application {
    String settings = System.getProperty("user.home") + File.separator + "Rating" + File.separator + "Settings";
    @Override
    public void start(Stage primaryStage) throws Exception{
        createMainDirectory();
        BorderPane root = new BorderPane();
        Button buttonGame,buttonMusic,buttonMovie,buttonPlayer,buttonAlbum,buttonAbout,buttonMemo,buttonBackARest;
        buttonGame = new Button("Games");
        buttonMusic = new Button("Music   ");
        buttonMovie = new Button("Movies");
        buttonPlayer = new Button("Players");
        buttonAlbum = new Button("Albums");
        buttonAbout = new Button("About  ");
        buttonMemo = new Button("Memo               ");
        buttonMemo.setContentDisplay(ContentDisplay.RIGHT);
        buttonBackARest = new Button("Backup/Restore");
        buttonBackARest.setContentDisplay(ContentDisplay.RIGHT);
        buttonGame.setGraphic(new ImageView(new Image("IconsMain" + "/" + "Games.png")));
        buttonPlayer.setGraphic(new ImageView(new Image("IconsMain" + "/" + "Player.jpg")));
        buttonAlbum.setGraphic(new ImageView(new Image( "IconsMain" + "/" + "Album.jpg")));
        buttonMusic.setGraphic(new ImageView(new Image("IconsMain" + "/" + "Music.jpg")));
        buttonMovie.setGraphic(new ImageView(new Image("IconsMain" + "/" + "Movie.png")));
        buttonAbout.setGraphic(new ImageView(new Image("IconsMain" + "/" + "About.jpg")));
        buttonMemo.setGraphic(new ImageView(new Image("IconsMain" + "/" + "Memo.png")));
        buttonBackARest.setGraphic(new ImageView(new Image("IconsMain" + "/" + "Backup and Restore.png")));
        buttonGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Game game = new Game("Games","Add a game","Game");
            }
        });
        buttonMusic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Music music = new Music("Music","Add music","Music");
            }
        });
        buttonAlbum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Album album = new Album("Album","Add album","Album");
            }
        });
        buttonPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Player player = new Player("Player","Add Player","Player");
            }
        });
        buttonMovie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Movie movie = new Movie("Movie","Add a movie","Movie");
            }
        });
        buttonAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              about();
            }
        });
        buttonMemo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                Pane Pane = new Pane();
                Pane.setPrefSize(170,400);
                Label labelL = new Label("Linux");
                labelL.setFont(Font.font(16));
                labelL.setGraphic(new ImageView(new Image("IconsMain" + "/" + "Tux.png")));
                Label labelW = new Label();
                labelW.setFont(Font.font(16));
                labelW.setGraphic(new ImageView(new Image("IconsMain" + "/" + "Windows.png")));
                Label labelWindows = new Label("Don't use in the Title:\n\n(~) (#) (%) " +
                        "(*)\n(&) ({}) (\\) (:) \n(<>) (?) (/) (+)\n(|) (\"\")");
                Label labelLinux = new Label("Don't use in the Title:\n(/)");
                labelW.setLayoutX(5);
                labelW.setLayoutY(5);
                labelWindows.setLayoutX(5);
                labelWindows.setLayoutY(65);
                labelL.setLayoutX(5);
                labelL.setLayoutY(170);
                labelLinux.setLayoutX(5);
                labelLinux.setLayoutY(280);
                Pane.getChildren().addAll(labelW,labelWindows,labelL,labelLinux);
                stage.setResizable(false);
                stage.setScene(new Scene(Pane,170,340));
                stage.show();
            }
        });
        buttonBackARest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String backupS = "Backup",restoreS = "Restore";
                Construct construct = new Construct();
                FileChooser fileChooser = new FileChooser();
                Stage chooserStage = new Stage();
                Stage stage = new Stage();
                stage.setTitle("Backup and Restore");
                Pane pane = new Pane();
                Label labelB = new Label(backupS);
                labelB.setFont(Font.font(16));
                Label labelInst = new Label("Please, choose a out folder");
                TextField field = new TextField();
                field.setPrefSize(320,20);
                Button buttonB = new Button("Choose");
                buttonB.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        construct.chooser(fileChooser,chooserStage,field);
                    }
                });
                Button buttonBackup = new Button(backupS);
                buttonBackup.setFont(Font.font("Monospace",FontPosture.ITALIC,14));
                buttonBackup.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (field.getText().isEmpty()){
                            chooseWarning(backupS);
                        } else {
                            Stage stageP = new Stage();
                            progress(stageP);
                            buttonBackup.setDisable(true);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    backup(field.getText());
                                }
                            }).start();
                            stageP.close();
                            buttonBackup.setDisable(false);
                            complete(backupS);
                        }
                    }
                });
                labelB.setLayoutX(170);
                labelB.setLayoutY(0);
                labelInst.setLayoutX(5);
                labelInst.setLayoutY(25);
                field.setLayoutX(5);
                field.setLayoutY(50);
                buttonB.setLayoutX(335);
                buttonB.setLayoutY(50);
                buttonBackup.setLayoutX(165);
                buttonBackup.setLayoutY(100);
                Label labelR = new Label(restoreS);
                labelR.setFont(Font.font(16));
                Label labelInstRes = new Label("Please, choose a backup file");
                TextField fieldR = new TextField();
                fieldR.setPrefSize(320,20);
                Button buttonR = new Button("Choose");
                buttonR.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        construct.chooser(fileChooser,chooserStage,fieldR);
                    }
                });
                Button buttonRestore = new Button(restoreS);
                buttonRestore.setFont(Font.font("Monospace",FontPosture.ITALIC,14));
                labelR.setLayoutX(170);
                labelR.setLayoutY(155);
                labelInstRes.setLayoutX(5);
                labelInstRes.setLayoutY(180);
                fieldR.setLayoutX(5);
                fieldR.setLayoutY(205);
                buttonR.setLayoutX(335);
                buttonR.setLayoutY(205);
                buttonRestore.setLayoutX(165);
                buttonRestore.setLayoutY(255);
                buttonRestore.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (fieldR.getText().isEmpty()){
                           chooseWarning(restoreS);
                        } else {
                            Stage stageR = new Stage();
                            progress(stageR);
                            buttonRestore.setDisable(true);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    restore(fieldR.getText());
                                }
                            }).start();
                            stageR.close();
                            buttonRestore.setDisable(false);
                            complete(restoreS);
                        }
                    }
                });
                pane.getChildren().addAll(labelB,labelInst,field,buttonB,buttonBackup,labelR,labelInstRes,fieldR,buttonR,buttonRestore);
                stage.setResizable(false);
                stage.setScene(new Scene(pane,400,300));
                stage.show();
            }
        });
        FlowPane flowPane = new FlowPane();
        FlowPane flowPaneS = new FlowPane();
        flowPaneS.setPrefSize(170,400);
        flowPane.setPrefSize(300,400);
        flowPane.getChildren().addAll(buttonGame,buttonMusic,buttonAlbum,buttonPlayer,buttonAbout
                ,buttonMovie);
        flowPaneS.getChildren().addAll(buttonMemo,buttonBackARest);
        root.setLeft(flowPane);
        root.setRight(flowPaneS);
        primaryStage.setTitle("Memory");
        primaryStage.setScene(new Scene(root, 900, 460));
        primaryStage.setResizable(false);
        primaryStage.show();
        load();
    }

    private void about() {
        Pane root = new Pane();
        Label label = new Label("Memory");
        label.setFont(Font.font("Monospace", FontWeight.BOLD,16));
        Label labelM = new Label();
        labelM.setGraphic(new ImageView(new Image( "IconsMain" + "/" + "Memory.jpg")));
        labelM.setLayoutX(25);
        labelM.setLayoutY(25);
        Label label0 = new Label("0.1");
        label0.setFont(Font.font("Monospace", FontPosture.ITALIC,16));
        Label label1 = new Label("Created by Quite River 21.06.17");
        label1.setFont(Font.font(14));
        label.setLayoutX(115);
        label.setLayoutY(5);
        label0.setLayoutX(140);
        label0.setLayoutY(180);
        label1.setLayoutX(35);
        label1.setLayoutY(210);
        root.getChildren().addAll(label,labelM,label0,label1);
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(new Scene(root,300,230));
        stage.setResizable(false);
        stage.show();
        load();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void instruction(){
        Stage stage = new Stage();
        CheckBox checkBox = new CheckBox("Not show again");
        Pane Pane = new Pane();
        Pane.setPrefSize(400,400);
        Label labelL = new Label("Linux");
        labelL.setFont(Font.font(16));
        labelL.setGraphic(new ImageView(new Image("IconsMain" + "/" + "Tux.png")));
        Label labelW = new Label();
        labelW.setFont(Font.font(16));
        labelW.setGraphic(new ImageView(new Image("IconsMain" + "/" + "Windows.png")));
        Label labelWindows = new Label("Don't use in the Title:\n\n(~) (#) (%) " +
                "(*)\n(&) ({}) (\\) (:) \n(<>) (?) (/) (+)\n(|) (\"\")");
        Label labelLinux = new Label("Don't use in the Title:\n(/)");
        labelW.setLayoutX(5);
        labelW.setLayoutY(5);
        labelWindows.setLayoutX(5);
        labelWindows.setLayoutY(65);
        labelL.setLayoutX(5);
        labelL.setLayoutY(170);
        labelLinux.setLayoutX(5);
        labelLinux.setLayoutY(280);
        checkBox.setLayoutX(5);
        checkBox.setLayoutY(330);
        Button button = new Button("Close");
        button.setLayoutX(340);
        button.setLayoutY(360);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (checkBox.isSelected()){
                    try(BufferedWriter br = new BufferedWriter(new FileWriter(settings))) {
                    br.write("true");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                stage.close();
            }
        });
        Pane.getChildren().addAll(labelW,labelWindows,labelL,labelLinux,checkBox,button);
        stage.setResizable(false);
        stage.setScene(new Scene(Pane,400,400));
        stage.show();
    }

    private void load() {
        if (!Files.exists(Paths.get(settings))) {
            try (BufferedWriter br = new BufferedWriter(new FileWriter(settings))) {
                br.write("false");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String is = null;
        try (BufferedReader br = new BufferedReader(new FileReader(settings))) {
            String text = null;
            while ((text = br.readLine()) != null) {
                is = text;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            if (!is.equals("true")) {
                instruction();
            }
    }

    public void createMainDirectory(){
        Path home = Paths.get(System.getProperty("user.home") + File.separator + "Rating");
        if (!Files.exists(home)){
            try {
                Files.createDirectory(home);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Failed to create main directory " + home);
                alert.showAndWait();
                System.exit(1);
            }
        }
    }

    private void backup(String path) {
        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(path))){
            File file = new File(System.getProperty("user.home") + File.separator + "Rating");
            if (file.isFile())
                zipFile(file,"",zip);
            else if (file.isDirectory())
                zipFolder(zip,file,"");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zipFolder(ZipOutputStream zipOutputStream,File inputFolder, String parentName){
        String name = parentName +inputFolder.getName()+File.separator;
        ZipEntry zipEntry = new ZipEntry(name);
        zipEntry.setMethod(ZipEntry.DEFLATED);
        try {
            zipOutputStream.putNextEntry(zipEntry);
            File[] contents = inputFolder.listFiles();
            for (File f : contents){
                if (f.isFile())
                    zipFile(f,name,zipOutputStream);
                else if(f.isDirectory())
                    zipFolder(zipOutputStream,f, name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zipFile(File inputFile,String parentName,ZipOutputStream zipOutputStream){
        ZipEntry zipEntry = new ZipEntry(parentName+inputFile.getName());
        zipEntry.setMethod(ZipEntry.DEFLATED);
        try {
            zipOutputStream.putNextEntry(zipEntry);
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buf)) > 0) {
                zipOutputStream.write(buf, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void restore(String path) {
        String dest = System.getProperty("user.home");
        byte[] buffer = new byte[2048];
        try (ZipInputStream zipInput = new ZipInputStream(new FileInputStream(path))){
            ZipEntry entry = zipInput.getNextEntry();
            while (entry != null) {
                String entryName = entry.getName();
                File file = new File(dest + File.separator + entryName);
                if (entry.isDirectory()) {
                    File newDir = new File(file.getAbsolutePath());
                    if (!newDir.exists()) {
                        boolean success = newDir.mkdirs();
                        if (!success) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("ERROR");
                                    alert.setHeaderText("Failed to create " + newDir.getAbsolutePath());
                                    alert.showAndWait();
                                }
                            });
                        }
                    }
                } else {
                    try (FileOutputStream outputStream = new FileOutputStream(file)) {
                        int count = 0;
                        while ((count = zipInput.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, count);
                        }
                    }
                }
                zipInput.closeEntry();
                entry = zipInput.getNextEntry();
            }
            zipInput.closeEntry();
            zipInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseWarning(String action){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Empty");
        alert.setHeaderText("Please, choose a " + action + " file");
        alert.show();
    }

    private void complete(String action){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Complete");
        alert.setHeaderText(action + " complete!");
        alert.show();
    }

    private void progress(Stage stage){
        stage.setResizable(false);
        stage.setTitle("Waiting");
        Pane pane = new Pane();
        ImageView imageView = new ImageView(new Image("IconsMain" + "/" + "ProgressBar.gif"));
        imageView.setLayoutX(15);
        imageView.setLayoutY(0);
        pane.getChildren().add(imageView);
        stage.setScene(new Scene(pane,130,100));
        stage.show();
    }
}