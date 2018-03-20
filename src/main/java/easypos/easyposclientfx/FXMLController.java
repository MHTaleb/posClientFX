package easypos.easyposclientfx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class FXMLController implements Initializable {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    void exit(ActionEvent event) {
        
        System.exit(0);
    }

    @FXML
    void login(ActionEvent event) {

        if (username.validate() && password.validate()) {
            System.out.println("login attempt to server");

            try {

                URL url = new URL("http://localhost:8181/logins/get/");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                http.setRequestMethod("POST"); // PUT is another valid option
                http.setDoOutput(true);

                Map<String, String> arguments = new HashMap<>();
                arguments.put("us_username", username.getText());
                arguments.put("us_pwdusr", password.getText());

                StringJoiner sj = new StringJoiner("&");
                for (Map.Entry<String, String> entry : arguments.entrySet()) {
                    sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                            + URLEncoder.encode(entry.getValue(), "UTF-8"));
                }
                byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
                int length = out.length;

                http.setFixedLengthStreamingMode(length);
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                //http.setRequestProperty("Method", "GET");
                http.connect();
                OutputStream os = http.getOutputStream();
                    os.write(out);
                

                BufferedReader br;
                if (200 <= http.getResponseCode() && http.getResponseCode() <= 299) {
                    br = new BufferedReader(new InputStreamReader(http.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
                }
                StringBuilder sb = new StringBuilder();
                int output;
                while ((output = br.read()) != -1) {
                    System.out.println(output);
                    sb.append((char)output);
                }
                
                System.out.println(sb.toString());
                
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<List<LoginResponse>> mapType = new TypeReference<List<LoginResponse>>() {};
                List<LoginResponse> readValue = mapper.readValue(sb.toString(),mapType);
                System.out.println(readValue);
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private RequiredFieldValidator rfv;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        rfv = new RequiredFieldValidator();
        rfv.setMessage("veuillez saisir le nom d'utilisateur");
        username.setValidators(rfv);

        username.focusedProperty().addListener((arg, oldv, newv) -> {
            if (!newv) {
                username.validate();
            }
        });

        rfv = new RequiredFieldValidator();
        rfv.setMessage("veuillez saisir le mot de passe");
        password.setValidators(rfv);
        password.focusedProperty().addListener((arg, oldv, newv) -> {
            if (!newv) {
                password.validate();
            }
        });

    }
}
