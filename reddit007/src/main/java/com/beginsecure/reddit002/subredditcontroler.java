package com.beginsecure.reddit002;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Random;

public class subredditcontroler extends Application {
    public static String reply_to;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Random rng = new Random();
        String url = "jdbc:mysql://localhost:3306/javafx";


        HBox main_hbox=new HBox();
        main_hbox.setBackground(new Background(
                new BackgroundImage(
                        new Image("https://images.hdqwalls.com/wallpapers/reddit-logo-qhd.jpg"),
                        BackgroundRepeat.REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                )));


        MenuButton menuButton=new MenuButton("subreddit");
        MenuItem item1=new MenuItem("rscience");
        MenuItem item2=new MenuItem("rsports");
        MenuItem item3=new MenuItem("rpolitics");
        MenuItem item4=new MenuItem("rfashion");
        menuButton.getItems().addAll(item1,item2,item3,item4);
        VBox searchBar = new VBox(menuButton);
        searchBar.setSpacing(40);
        searchBar.setPadding(new Insets(70));
        main_hbox.getChildren().addAll(searchBar);
        menuButton.getItems().forEach(item -> item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main_hbox.setBackground(new Background(
                        new BackgroundImage(
                                new Image("https://img.freepik.com/free-vector/sky-background-pastel-paper-cut-design-vector_53876-144660.jpg"),
                                BackgroundRepeat.REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                        )));

                System.out.println("Selected option: " + item.getText());
                creat_a_post_controller.Subredditname=item.getText();
                VBox content = new VBox(10);
                ScrollPane scroller = new ScrollPane(content);
                scroller.setFitToWidth(true);
                Button creatPost=new Button("Creat");
                searchBar.getChildren().addAll(creatPost);
                creatPost.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        DBUtils.changeScene(event,"creat_a_post.fxml","creatpost",creat_a_post_controller.Username);
                    }
                });
                try (Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                    Statement statement = connection.createStatement();
                    String query = "SELECT COUNT(id) FROM "+creat_a_post_controller.Subredditname;
                    ResultSet resultSet = statement.executeQuery(query);
                    if (main_hbox.getChildren().size() >1) {
                        main_hbox.getChildren().remove(main_hbox.getChildren().size()-1);
                    }

                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        System.out.println("Number of cells in the column: " + count);
                        for (int i=1;i<=count;i++){
                            int finalI = i;
                            HBox hBox=new HBox(10);
                            AnchorPane anchorPane = new AnchorPane();
                            String style = String.format("-fx-background: rgb(%d, %d, %d);"+
                                            "-fx-background-color: -fx-background;",
                                    rng.nextInt(256),
                                    rng.nextInt(256),
                                    rng.nextInt(256));
                            anchorPane.setStyle(style);
                            anchorPane.getChildren().add(hBox);


                            Label label = new Label();
                            DBUtils.fillingLAbles(label,"username",finalI);
                            Label Lable_reply=new Label();
                            DBUtils.fillingLAbles(Lable_reply,"reply_to",finalI);
                            hBox.getChildren().addAll(label,Lable_reply);
                            VBox vBox=new VBox(5);
                            hBox.getChildren().addAll(vBox);
                            Label tf_Title=new Label();
                            vBox.getChildren().addAll(tf_Title);
                            DBUtils.fillingLAbles(tf_Title,"title",finalI);
                            Label ta_post=new Label();
                            DBUtils.fillingLAbles(ta_post,"post",finalI);
//                            ta_post.setMaxWidth(400);
//                            ta_post.setMinWidth(Region.USE_PREF_SIZE);
                            TextArea ta=new TextArea(ta_post.getText());
//                            ta.setMinWidth(400);
                            vBox.getChildren().addAll(ta);
                            HBox hBox1=new HBox(5);
                            vBox.getChildren().addAll(hBox1);
                            ToggleGroup group=new ToggleGroup();
                            RadioButton KarmaLike=new RadioButton("Like");
                            KarmaLike.setToggleGroup(group);
                            RadioButton KarmaDislike=new RadioButton("Dislike");
                            KarmaDislike.setToggleGroup(group);
                            Label karma=new Label("Karma: ");
                            Button comment=new Button("comment");

                            hBox1.setSpacing(10);
                            hBox1.getChildren().addAll(KarmaLike,KarmaDislike);
                            hBox1.getChildren().addAll(karma);
                            hBox1.getChildren().addAll(comment);

                            KarmaLike.setPrefHeight(30);
                            KarmaDislike.setPrefHeight(30);
                            karma.setPrefHeight(30);
                            comment.setPrefHeight(30);


                            comment.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    DBUtils.changeScene(event,"creat_a_post.fxml","creatpost",creat_a_post_controller.Username);
                                    try(Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                        Statement statement = connection.createStatement();
                                        String query3 = "SELECT username FROM " + creat_a_post_controller.Subredditname + " WHERE id = " + finalI;
                                        ResultSet resultSet3 = statement.executeQuery(query3);
                                        if (resultSet3.next()) {
                                            String retrievedValue = resultSet3.getString("username");
                                            if (retrievedValue==null){
                                                reply_to="";
                                            }
                                            else reply_to= retrievedValue;
                                            System.out.println("reply to: " + reply_to);

                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
//                    creat_a_post_controller.reply_to=
                                }
                            });

                            VBox vBox1=new VBox(30);
                            hBox.getChildren().addAll(vBox1);

                            ImageView img_post=new ImageView();
                            DBUtils.settingImage(img_post,finalI);
                            vBox1.getChildren().addAll(img_post);
                            img_post.setFitHeight(300);
                            img_post.setFitWidth(400);
                            img_post.setPreserveRatio(true);




                            content.getChildren().add(anchorPane);

                            KarmaDislike.setOnAction(new EventHandler<ActionEvent>() {
                                int karmavalue;

                                @Override
                                public void handle(ActionEvent event) {
                                    try (Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                        Statement statement = connection.createStatement();
                                        String query3 = "SELECT Karma FROM " + creat_a_post_controller.Subredditname + " WHERE id = " + finalI;
                                        ResultSet resultSet3 = statement.executeQuery(query3);


                                        if (resultSet3.next()) {
                                            String retrievedValue = resultSet3.getString("Karma");
                                            if (retrievedValue==null){
                                                karmavalue=0;
                                            }
                                            else karmavalue= Integer.parseInt(retrievedValue);
                                            System.out.println("Retrieved value: " + retrievedValue);

                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    karmavalue--;
                                    try (Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                        Statement statement = connection.createStatement();
                                        String query2 = "UPDATE " + creat_a_post_controller.Subredditname + " SET Karma = '" + karmavalue + "' WHERE id = " + finalI;
                                        Statement statement2 = connection.createStatement();
                                        int rowsAffected = statement2.executeUpdate(query2);
                                        karma.setText("Karma: " + String.valueOf(karmavalue));
                                        System.out.println("Rows affected: " + rowsAffected);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    KarmaLike.setOnAction(new EventHandler<ActionEvent>() {
                                        int karmavalue;
                                        @Override
                                        public void handle(ActionEvent event) {
                                            try(Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                                Statement statement = connection.createStatement();
                                                String query3="SELECT Karma FROM "+creat_a_post_controller.Subredditname+" WHERE id = " + finalI;
                                                ResultSet resultSet3 = statement.executeQuery(query3);


                                                if (resultSet3.next()) {
                                                    String retrievedValue = resultSet3.getString("Karma");
                                                    if (retrievedValue==null){
                                                        karmavalue=0;
                                                    }
                                                    else karmavalue= Integer.parseInt(retrievedValue);
                                                    System.out.println("Retrieved value: " + retrievedValue);

                                                }
                                            }catch (SQLException e){
                                                e.printStackTrace();
                                            }

                                            karmavalue+=2;
                                            try(Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                                Statement statement = connection.createStatement();
                                                String query2 = "UPDATE " + creat_a_post_controller.Subredditname + " SET Karma = '" + karmavalue  + "' WHERE id = " + finalI;
                                                Statement statement2 = connection.createStatement();
                                                int rowsAffected = statement2.executeUpdate(query2);
                                                karma.setText("Karma: "+String.valueOf(karmavalue));
                                                System.out.println("Rows affected: " + rowsAffected);
                                            }catch (SQLException e){
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    KarmaDislike.setOnAction(new EventHandler<ActionEvent>() {
                                        int karmavalue;
                                        @Override
                                        public void handle(ActionEvent event) {
                                            try(Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                                Statement statement = connection.createStatement();
                                                String query3="SELECT Karma FROM "+creat_a_post_controller.Subredditname+" WHERE id = " + finalI;
                                                ResultSet resultSet3 = statement.executeQuery(query3);


                                                if (resultSet3.next()) {
                                                    String retrievedValue = resultSet3.getString("Karma");
                                                    if (retrievedValue==null){
                                                        karmavalue=0;
                                                    }
                                                    else karmavalue= Integer.parseInt(retrievedValue);
                                                    System.out.println("Retrieved value: " + retrievedValue);
                                                }
                                            }catch (SQLException e){
                                                e.printStackTrace();
                                            }

                                            karmavalue-=2;
                                            try(Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                                Statement statement = connection.createStatement();
                                                String query2 = "UPDATE " + creat_a_post_controller.Subredditname + " SET Karma = '" + karmavalue  + "' WHERE id = " + finalI;
                                                Statement statement2 = connection.createStatement();
                                                int rowsAffected = statement2.executeUpdate(query2);
                                                karma.setText("Karma: "+String.valueOf(karmavalue));
                                                System.out.println("Rows affected: " + rowsAffected);
                                            }catch (SQLException e){
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            });


                            KarmaLike.setOnAction(new EventHandler<ActionEvent>() {
                                int karmavalue;
                                @Override
                                public void handle(ActionEvent event) {
                                    try(Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                        Statement statement = connection.createStatement();
                                        String query3="SELECT Karma FROM "+creat_a_post_controller.Subredditname+" WHERE id = " + finalI;
                                        ResultSet resultSet3 = statement.executeQuery(query3);


                                        if (resultSet3.next()) {
                                            String retrievedValue = resultSet3.getString("Karma");
                                            if (retrievedValue==null){
                                                karmavalue=0;
                                            }
                                            else karmavalue= Integer.parseInt(retrievedValue);
                                            System.out.println("Retrieved value: " + retrievedValue);

                                        }
                                    }catch (SQLException e){
                                        e.printStackTrace();
                                    }
                                    karmavalue++;
                                    try(Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                        Statement statement = connection.createStatement();
                                        String query2 = "UPDATE " + creat_a_post_controller.Subredditname + " SET Karma = '" + karmavalue + "' WHERE id = " + finalI;
                                        Statement statement2 = connection.createStatement();
                                        int rowsAffected = statement2.executeUpdate(query2);
                                        karma.setText("Karma: "+String.valueOf(karmavalue));
                                        System.out.println("Rows affected: " + rowsAffected);
                                    }catch (SQLException e){
                                        e.printStackTrace();
                                    }
                                    KarmaDislike.setOnAction(new EventHandler<ActionEvent>() {
                                        int karmavalue;
                                        @Override
                                        public void handle(ActionEvent event) {
                                            try(Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                                Statement statement = connection.createStatement();
                                                String query3="SELECT Karma FROM "+creat_a_post_controller.Subredditname+" WHERE id = " + finalI;
                                                ResultSet resultSet3 = statement.executeQuery(query3);


                                                if (resultSet3.next()) {
                                                    String retrievedValue = resultSet3.getString("Karma");
                                                    if (retrievedValue==null){
                                                        karmavalue=0;
                                                    }
                                                    else karmavalue= Integer.parseInt(retrievedValue);
                                                    System.out.println("Retrieved value: " + retrievedValue);

                                                }
                                            }catch (SQLException e){
                                                e.printStackTrace();
                                            }

                                            karmavalue-=2;
                                            try(Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                                Statement statement = connection.createStatement();
                                                String query2 = "UPDATE " + creat_a_post_controller.Subredditname + " SET Karma = '" + karmavalue + "' WHERE id = " + finalI;
                                                Statement statement2 = connection.createStatement();
                                                int rowsAffected = statement2.executeUpdate(query2);
                                                karma.setText("Karma: "+String.valueOf(karmavalue));
                                                System.out.println("Rows affected: " + rowsAffected);
                                            }catch (SQLException e){
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    KarmaLike.setOnAction(new EventHandler<ActionEvent>() {
                                        int karmavalue;

                                        @Override
                                        public void handle(ActionEvent event) {
                                            try (Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                                Statement statement = connection.createStatement();
                                                String query3 = "SELECT Karma FROM " + creat_a_post_controller.Subredditname + " WHERE id = " + finalI;
                                                ResultSet resultSet3 = statement.executeQuery(query3);


                                                if (resultSet3.next()) {
                                                    String retrievedValue = resultSet3.getString("Karma");
                                                    if (retrievedValue==null){
                                                        karmavalue=0;
                                                    }
                                                    else karmavalue= Integer.parseInt(retrievedValue);
                                                    System.out.println("Retrieved value: " + retrievedValue);

                                                }
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            karmavalue+=2;
                                            try (Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                                Statement statement = connection.createStatement();
                                                String query2 = "UPDATE " + creat_a_post_controller.Subredditname + " SET Karma = '" + karmavalue + "' WHERE id = " + finalI;
                                                Statement statement2 = connection.createStatement();
                                                int rowsAffected = statement2.executeUpdate(query2);
                                                karma.setText("Karma: " + String.valueOf(karmavalue));
                                                System.out.println("Rows affected: " + rowsAffected);
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                        main_hbox.getChildren().addAll(scroller);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }));


        Scene scene = new Scene(new BorderPane(main_hbox), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
