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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Random;

public class subredditcontroler extends Application {
    public static int v1;
    public static int v2;
    public static int v3;

    /**
     * When the user wants to comment on a post,
     * we send these variables to the creat_a_post_controller class.
     */
    public static String reply_to;
    public static String title_toReply;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Random rng = new Random();
        String url = "jdbc:mysql://localhost:3306/javafx";
        /**
         * Choosing a wallpaper for the first page, after login
         */

        HBox main_hbox=new HBox();
        main_hbox.setBackground(new Background(
                new BackgroundImage(
                        new Image("https://images.hdqwalls.com/wallpapers/reddit-logo-qhd.jpg"),
                        BackgroundRepeat.REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                )));
        /**
         * Create a menu for subreddits
         */

        MenuButton menuButton=new MenuButton("subreddit");
        MenuItem item1=new MenuItem("rscience");
        MenuItem item2=new MenuItem("rsports");
        MenuItem item3=new MenuItem("rpolitics");
        MenuItem item4=new MenuItem("rfashion");
        menuButton.getItems().addAll(item1,item2,item3,item4);
        menuButton.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
        VBox searchBar = new VBox(menuButton);
        searchBar.setSpacing(40);
        searchBar.setPadding(new Insets(70));
        main_hbox.getChildren().addAll(searchBar);

        /**
         * Create a key to create a post
         */
        Button creatPost=new Button("Creat");

        creatPost.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
        creatPost.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"creat_a_post.fxml","creatpost",creat_a_post_controller.Username);
                v1=rng.nextInt(256);
                v2=rng.nextInt(256);
                v3=rng.nextInt(256);
            }
        });

        menuButton.getItems().forEach(item -> item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main_hbox.setBackground(new Background(
                        new BackgroundImage(
                                new Image("https://wallpapers.com/images/hd/blue-minimalist-reddit-alien-mp6g50evoopcgcnk.jpg"),
                                BackgroundRepeat.REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                        )));

                System.out.println("Selected option: " + item.getText());
                /**
                 * adding creat button
                 */
                searchBar.getChildren().addAll(creatPost);

                /**
                 * Send the selected subreddit to the creat_a_post_controller class
                 */
                creat_a_post_controller.Subredditname=item.getText();
                VBox content = new VBox(10);
                ScrollPane scroller = new ScrollPane(content);
                scroller.setFitToWidth(true);


                /**
                 * Obtaining the required number of vbox from the database
                 */
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
                        /**
                         * Importing Nodes into each vbox
                         */
                        for (int i=1;i<=count;i++){
                            int finalI = i;
                            HBox hBox=new HBox(10);
                            AnchorPane anchorPane = new AnchorPane();
                            String style = String.format("-fx-background: rgb(%d, %d, %d);"+
                                            "-fx-background-color: -fx-background;",
                                    v1,
                                    v2,
                                    v3);
                            anchorPane.setStyle(style);
                            anchorPane.getChildren().add(hBox);


                            Label label = new Label();
                            DBUtils.fillingLAbles(label,"username",finalI);
                            label.setPadding(new Insets(10));
                            label.setFont(new Font(15));
                            Label Lable_reply=new Label();
                            DBUtils.fillingLAbles(Lable_reply,"reply_to",finalI);
                            Lable_reply.setPadding(new Insets(10));
                            Lable_reply.setFont(new Font(15));
                            hBox.getChildren().addAll(label,Lable_reply);
                            VBox vBox=new VBox(5);
                            hBox.getChildren().addAll(vBox);
                            Label tf_Title=new Label();
                            tf_Title.setPadding(new Insets(10));
                            tf_Title.setFont(new Font(15));
                            vBox.getChildren().addAll(tf_Title);
                            DBUtils.fillingLAbles(tf_Title,"title",finalI);
                            Label ta_post=new Label();
                            DBUtils.fillingLAbles(ta_post,"post",finalI);
                            TextArea ta=new TextArea(ta_post.getText());
                            ta.setWrapText(true);
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
                            comment.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");

                            hBox1.setSpacing(10);
                            hBox1.getChildren().addAll(KarmaLike,KarmaDislike);
                            hBox1.getChildren().addAll(karma);
                            hBox1.getChildren().addAll(comment);

                            KarmaLike.setPrefHeight(30);
                            KarmaDislike.setPrefHeight(30);
                            karma.setPrefHeight(30);
                            comment.setPrefHeight(30);

                            /**
                             * It is possible to comment for each post
                             */
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
                                    try(Connection connection = DriverManager.getConnection(url, "root", "13821381MariaSabouriDodaran//")) {
                                        Statement statement = connection.createStatement();
                                        String query3 = "SELECT title FROM " + creat_a_post_controller.Subredditname + " WHERE id = " + finalI;
                                        ResultSet resultSet3 = statement.executeQuery(query3);
                                        if (resultSet3.next()) {
                                            String retrievedValue = resultSet3.getString("username");
                                            if (retrievedValue==null){
                                                title_toReply="";
                                            }
                                            else reply_to= retrievedValue;
                                            System.out.println("title to Reply: " + title_toReply);
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            VBox vBox1=new VBox(30);
                            hBox.getChildren().addAll(vBox1);
                            /**
                             * Ability to create a photo in each comment
                             */

                            ImageView img_post=new ImageView();
                            DBUtils.settingImage(img_post,finalI);
                            vBox1.getChildren().addAll(img_post);
                            img_post.setFitHeight(300);
                            img_post.setFitWidth(400);
                            img_post.setPreserveRatio(true);

                            content.getChildren().add(anchorPane);

                            /**
                             * Creating a radio button for Like and Dislike and updating karma
                             */
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

        /**
         * Create a scene
         */
        Scene scene = new Scene(new BorderPane(main_hbox), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
