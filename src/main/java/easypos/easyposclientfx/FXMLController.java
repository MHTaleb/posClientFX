package easypos.easyposclientfx;


import utils.rest.Crypto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import utils.rest.RequestHandler;

public class FXMLController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField passwordTextField;

    @FXML
    private PasswordField password;

    @FXML
    private ImageView showPasswordButton;

    private boolean clicked = false;
    private boolean done = false;

    @FXML
    void showPassword(MouseEvent event) {

        clicked = !clicked;
        if (clicked) {
            InputStream resourceAsStream = null;
            try {

                resourceAsStream = this.getClass().getResourceAsStream("/icons/showHide.png");
                showPasswordButton.setImage(new Image(resourceAsStream));
            } catch (Exception e) {
                System.out.println("error 1 ");
            }

        } else {
            InputStream resourceAsStream = null;
            try {

                resourceAsStream = this.getClass().getResourceAsStream("/icons/showhideNC.png");
                showPasswordButton.setImage(new Image(resourceAsStream));
            } catch (Exception e) {
                System.out.println("error 1 ");
            }
        }

        password.setVisible(!clicked);
        passwordTextField.setVisible(clicked);

    }

    @FXML
    void exit(ActionEvent event) {

        System.exit(0);
    }

    @FXML
    void login(ActionEvent event) {

        if (validate(username).getValue() && validate(password).getValue()) {
            System.out.println("login attempt to server");

            try {
                final String requestUrl = "http://localhost:8181/logins/";

                String serverResponse = new RequestHandler(requestUrl, RequestHandler.RequestType.GET)
                        .putProperty("us_username", username.getText())
                        .putProperty("us_pwdusr", Crypto.getSha(password.getText()))
                        .Request();

                System.out.println(serverResponse);// reponse json

                // mapping en jackson
                // preparation du type de reponse attendu
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<List<LoginResponse>> mapType = new TypeReference<List<LoginResponse>>() {
                };

                // mapping du json vers le type
                List<LoginResponse> readValue = mapper.readValue(serverResponse, mapType);
                if (!readValue.isEmpty()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/principal.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.DECORATED);
                    final Stage parent = MainApp.getParent();
                    stage.initOwner(parent);
                    MainApp.hide();
                    stage.setTitle("ABC");
                    stage.setScene(new Scene(root1));
                    stage.show();
                    stage.setOnHiding(new EventHandler<WindowEvent>() {

                        @Override
                        public void handle(WindowEvent event) {
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    MainApp.show();
                                }
                            });
                        }
                    });
                }

                //partie traitement de la reponse
                System.out.println(readValue);
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            init();
        } catch (URISyntaxException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private ImageView conforama;

    private void init() throws URISyntaxException, IOException {
        // TODO
        password.textProperty().bindBidirectional(passwordTextField.textProperty());
        passwordTextField.setVisible(false);
        
        
                    
         
        //File file = new File(getClass().getResource("/icons/conforamaLogo.svg").getPath());
        InputStream fileis = getClass().getResourceAsStream("/icons/conforamaLogo.svg");
        Image image = new Image(fileis);       
        conforama = new ImageView(image);
        // BufferedImage rasterize = SVGImageReader.rasterize(fileis);
        //conforama.setImage(SwingFXUtils.toFXImage(rasterize, null));
//        BufferedImageTranscoder trans = new BufferedImageTranscoder();
//
//        // In my case I have added a file "svglogo.svg" in my project source folder within the default package.
//        // Use any SVG file you like! I had success with http://en.wikipedia.org/wiki/File:SVG_logo.svg
//        try (InputStream file = getClass().getResourceAsStream("/icons/conforamaLogo.svg")) {
//            TranscoderInput transIn = new TranscoderInput(file);
//            try {
//                
//                trans.transcode(transIn, null);
//                // Use WritableImage if you want to further modify the image (by using a PixelWriter)
//                Image img = SwingFXUtils.toFXImage(trans.getBufferedImage(), null);
//                
//                conforama.setImage(img);
//            } catch (TranscoderException ex) {
//                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        catch (IOException io) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, io);
//        }
//        
        
    }

    private BooleanBinding validate(TextField field) {
        return field.textProperty().isEmpty().not();
    }

}
