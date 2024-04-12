package com.beginsecure.reddit002;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class signupController implements Initializable {
    @FXML
    private Button button_signup;
    @FXML
    private Button button_login;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_lastname;
    @FXML
    private ComboBox combo_gender;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo_gender.getItems().addAll("Others","Woman","Man");

        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_username.getText().trim().isEmpty()&&!tf_password.getText().trim().isEmpty()){
                    DBUtils.signUpUser(event,tf_username.getText(),tf_password.getText(),tf_email.getText(),tf_name.getText(),tf_lastname.getText(), (String) combo_gender.getValue());
                }else {
                    System.out.println("Please fill in all information");
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();
                }
                System.out.println("sign up");
            }

        });
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"sample.fxml","Log in!",null);

            }
        });


    }
}
