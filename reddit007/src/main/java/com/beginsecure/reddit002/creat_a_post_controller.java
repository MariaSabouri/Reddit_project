package com.beginsecure.reddit002;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class creat_a_post_controller implements Initializable {

    @FXML
    private Button submit;
    @FXML
    private Button button_home;
    @FXML
    private Button buttom_logout;
    @FXML
    private TextField tf_titlepost;
    @FXML
    private TextField tf_imageURL;
    @FXML
    private ImageView image_post;
    @FXML
    private TextArea ta_post;
    @FXML
    private DatePicker datepicker;

    public static String Subredditname;
    public static String Username;
    public static String reply_to;
    private static int counter=0;

//    public static void setValueOfUsername(String username){
//        Username=username;
//
//
//    }
    public static void setAndSubredditname(String subredditname){
        Subredditname=subredditname;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(Username);
        buttom_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"sample.fxml","Log in!",null);

            }
        });
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                counter++;
                if (counter>1){
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Your commit has been added!");
                    alert.show();
                }else insertDataToMysql(event,Username,subredditcontroler.reply_to,tf_titlepost.getText(),ta_post.getText(),datepicker.getValue(),tf_imageURL.getText());

            }
        });
        button_home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                subredditcontroler subredditcontroler=new subredditcontroler();
                try {
                    subredditcontroler.start(new Stage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
    public static void insertDataToMysql(ActionEvent event, String username, String reply, String title, String post, LocalDate date, String image){
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        String url = "jdbc:mysql://localhost:3306/javafx";
        try{
            Connection connection=DriverManager.getConnection(url,"root","13821381MariaSabouriDodaran//");
            String sql = "INSERT INTO "+ Subredditname+" (username,reply_to,title,post,date_picker,image) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,reply);
            statement.setString(3,title);
            statement.setString(4,post);
            statement.setString(5, String.valueOf(sqlDate));
            statement.setString(6,image);
            int rowsInserted=statement.executeUpdate();
            if (rowsInserted>0){
                System.out.println("A new row has been inserted successfully!");
            }
            connection.close();
        }catch(SQLException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }



}
