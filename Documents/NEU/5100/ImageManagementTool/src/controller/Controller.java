package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import model.Converter;
import model.CustomImage;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Controller {
    @FXML
    private BorderPane borderPane;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private TextField textWidth;
    @FXML
    private TextField textHeight;

    @FXML
    private GridPane gridpane;
    @FXML
    private Label content_header;
    @FXML
    private Label alert;

    private Image image;
    private List<File> files;

    ImageHelper helper = new ImageHelper();//instantiate util object

    //triggered when users click upload button, open window to choose picture(s)
    public void uploadFile() throws IOException {
        alert.setVisible(false);
        FileChooser fileChooser = new FileChooser();//instantiate javaFx fileChooser
        fileChooser.setTitle("");
        //filter to constrain files of which patterns users can choose
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        //to open window for users to choose images and get file(s) users chose.
        files = fileChooser.showOpenMultipleDialog(borderPane.getScene().getWindow());
        if (files != null && files.size()>0) {
            //clear old grid pane content to show new content
            gridpane.getChildren().clear();
            for(int i = 0; i < files.size(); i++){
                File file = files.get(i);
                final ImageView selectedImage = new ImageView();
                Image image1 = new Image(file.toURI().toString());//convert file to image
                selectedImage.setImage(image1);
                selectedImage.setFitHeight(100);//set small version to preview
                selectedImage.setFitWidth(100);
                Label textArea = new Label();
                //get image information of name, height, width.
                String text = "name: " + file.getName() + "\n" + "height: " + image1.getHeight() + "\n" +
                        "width: " + image1.getWidth();
                textArea.setText(text);
                //to calculate the column and row. each row place 5 images
                int col = i % 5;
                int row = (i / 5)*2+1;
                gridpane.add(selectedImage, col, row);//add images in grid pane to preview
                gridpane.add(textArea,col, row+1);//add images information to grid pane
            }
        }else {
            //if user canceled choosing, show message in grid pane
            showMessage("Please choose picture(s)");
        }

    }

    //triggered when users click the convert button
    @FXML
    public void imageTransfer() throws IOException {
        if(files != null && files.size()>0) {
            FileInputStream input;
            String filePath;
            String sourcePath;
            //instantiate javaFx file chooser
            FileChooser chooser = new FileChooser();
            File file = chooser.showSaveDialog(borderPane.getScene().getWindow());//open save window to change file name and choose folder to save in
            for (int i = 0; i < files.size(); i++) {
                chooser.setInitialFileName(file.getName());
                chooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*." + choiceBox.getValue())
                );
                String destinationPath = "";
                File imageFile = files.get(i);//get each selected file
                input = new FileInputStream(imageFile);
                image = new Image(input);//transfer file to image
                sourcePath = files.get(i).getPath();//get path information
                try {
                    filePath = file.getPath();
                    //add a random number at the end to make sure every image have distinct name
                    destinationPath = filePath + helper.getRandom(1000);
                    //get the format user chose and add at the end
                    destinationPath += ("." + choiceBox.getValue());
                   if (textHeight.getText().equals("") && textWidth.getText().equals("")) {//if user did't set width and height
                        int width = (int) image.getWidth();
                        int height = (int) image.getWidth();
                       CustomImage ima = new CustomImage(sourcePath, width, height);
                       convertImages(ima, destinationPath);
                    } else if (textHeight.getText().equals("") || textWidth.getText().equals("") ){//if user only set width or height
                        break;
                    } else {//user set width and height
                        int width = Integer.parseInt(textWidth.getText());
                        int height = Integer.parseInt(textHeight.getText());
                       CustomImage ima = new CustomImage(sourcePath, width, height);//encapsulate information in a CustomImage object
                       convertImages(ima, destinationPath);//start convert image and save
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage(("Picture(s) transfer failed."));//update gird pane content to show failure
                }
            }
            textWidth.clear();
            textHeight.clear();

        }else {
            alert.setText("upload image first");//if user didn't upload any image show message
            alert.setVisible(true);
        }
    }


    private void convertImages(CustomImage ima, String destinationPath){
        //get a converter to convert image
        Converter converter = Converter.getInstance();
        boolean ifSuccess = converter.convertImages(ima, destinationPath);
        if(ifSuccess){
            showMessage("Picture(s) transfer succeed!");//update grid pane content to show success
        }else {
            showMessage(("Picture(s) transfer failed."));//update gird pane content to show failure
        }
    }

    //this function is used as a feedback of image conversion
    private void showMessage(String message) {
        content_header.setVisible(false);
        gridpane.getChildren().clear();//clear old content
        Label label = new Label(message);
        gridpane.add(label, 0, 3);//place new content
    }

}
