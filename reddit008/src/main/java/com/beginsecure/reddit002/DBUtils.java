package com.beginsecure.reddit002;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBUtils {
    public static String username;

    public static void changeScene(ActionEvent event, String fxmlfile, String title, String username){
        Parent root=null;
        if (username!=null){
            try {
                FXMLLoader loader=new FXMLLoader(DBUtils.class.getResource(fxmlfile));
                root=loader.load();
//                subredditcontroler subredditcontroler=new subredditcontroler();
//                subredditcontroler=loader.getController();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            try {
                root=FXMLLoader.load(DBUtils.class.getResource(fxmlfile));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }
    public static void signUpUser(ActionEvent event, String username,String password,String email
            ,String name,String lastname,String combo_gender){
        //add operations for the rest of inputs
        Connection connection=null;
        PreparedStatement psInsert=null;
        PreparedStatement psCheckUserExists=null;
        ResultSet resultSet=null;

        try {
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx","root","13821381MariaSabouriDodaran//");
            psCheckUserExists=connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1,username);
            resultSet=psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("User already exist!");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            }else {
                psInsert=connection.prepareStatement("INSERT INTO users (username, password,Name,Last_Name,Email,gender) VALUES " +
                        "(?,?,?,?,?,?)");
//                System.out.println(combo_gender);
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setString(3,name);
                psInsert.setString(4,lastname);
                psInsert.setString(5,email);
                psInsert.setString(6,combo_gender);
                psInsert.executeUpdate();
//                changeScene(event,"creat_a_post.fxml","Welcome",username);
                subredditcontroler subredditcontroler=new subredditcontroler();
                subredditcontroler.start(new Stage());
            }
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet!=null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists!=null){
                try {
                    psCheckUserExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert!=null){
                try {
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void logInUser(ActionEvent event,String username,String password){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
//        creat_a_post_controller.setValueOfUsername(username);
        creat_a_post_controller.Username=username;
        try {
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx","root","13821381MariaSabouriDodaran//");
            preparedStatement=connection.prepareStatement("SELECT password FROM users WHERE username =?");
            preparedStatement.setString(1,username);
            resultSet=preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedPassword=resultSet.getString("password");
                    if (retrievedPassword.equals(password)){
//                        changeScene(event,"creat_a_post.fxml","Welcome!",username);
                        subredditcontroler subredditcontroler=new subredditcontroler();
                        subredditcontroler.start(new Stage());
                    }else {
                        System.out.println("Password did not match!");
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.show();
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet!=null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (preparedStatement!=null){
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void fillingLAbles(Label label, String title,int finalI){
        String url = "jdbc:mysql://localhost:3306/javafx";
        try(Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
            Statement statement = connection.createStatement();
            String query3 = "SELECT "+title+" FROM " + creat_a_post_controller.Subredditname + " WHERE id = " + finalI;
            ResultSet resultSet3 = statement.executeQuery(query3);
            if (resultSet3.next()) {
                String retrievedValue = resultSet3.getString(title);
                if (retrievedValue==null){
                    label.setText(title+": --");
                }
                else label.setText(title+": "+retrievedValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void settingImage(ImageView imageView,int finalI){
        String url = "jdbc:mysql://localhost:3306/javafx";
        try(Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
            Statement statement = connection.createStatement();
            String query3 = "SELECT image FROM " + creat_a_post_controller.Subredditname + " WHERE id = " + finalI;
            ResultSet resultSet3 = statement.executeQuery(query3);
            if (resultSet3.next()) {
                String retrievedValue = resultSet3.getString("image");
                if (!(retrievedValue ==null)){
                    try {
                        Image image=new Image(retrievedValue);
                        imageView.setImage(image);
                    }catch (Exception e){
                        System.out.println("image not found!");
//                        Alert alert=new Alert(Alert.AlertType.ERROR);
//                        alert.setContentText("image not found!");
//                        alert.show();
                        e.printStackTrace();
                    }
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
