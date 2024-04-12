# Reddit

Making a basic reddit app using java
# Description

Reddit (/ˈrɛdɪt/) is an American social news aggregation, content rating, and forum social network. 
Registered users (commonly referred to as "Redditors") submit content to the site such as links, text posts, images, 
and videos, which are then voted up or down by other members. Posts are organized by subject into user-created boards 
called "communities" or "subreddits". Submissions with more upvotes appear towards the top of their subreddit and, if they 
receive enough upvotes, ultimately on the site's front page.

# Getting Started

The project includes 6 classes:

- HelloApplication: In this class, Run is taken from the project.

- DBUtils: Functions are defined in this class. These functions are the interface between the code and the database. 
- The reason for creating a separate class is to repeat similar processes in the database.

- - changeScene: This function is responsible for changing the scene. During the event, the scene changes.

- - signUpUser: This function takes the information of the new user and stores it in the database. 
- If the person has entered an item incompletely, an error will appear for him. Also, if the entered username has already been saved, he will receive an error.

- - logInUser: When the user wants to login, this function checks if there is such an input password and username or not. 
- Otherwise, an error will appear.

- - fillingLAbles and settingImage: Each subreddit contains separate sections in which the user can exchange. 
- This function takes the information from the database of that subreddit and adds it to the nodes of each Vbox.

- controller: This is the class related to sample .fxml. For this purpose, it implements Initializable. 
- When the user clicks login, it is transferred to the DButils class with the help of the setOnAction function and the 
- correctness of the given entries is checked.

Likewise, if he clicks SignUp, he will be transferred to the Debutils class and the signup page will appear.

- signupController: This class is related to signup .fxml.

- subredditcontroler: This class extends the Application class. In the scene of this class, a menu button appears first. 
- In this menu, the subreddits defined in the database appear. By clicking on an item, we inform the creat_a_post_controller 
- class that what subreddit is selected. 
- Now, in the database, we check how many posts have been created; In a for loop, Vboxes are created. Then we write the 
- data of each Vbox on it.

- - - keys:

- - - - comment: If the user clicks on it, the username corresponding to that post is found from the database and sent to 
- the reply_to variable of the creat_a_post_controller class. Then this class creates a new page so that we can print our 
- comment. do.

- - - - Like and Dislike: I put these keys in radiobutton format. If the user chooses Like, from the database, 
- the karma of that post will increase by one unit; now, if we change our mind and choose Dislike, the karma will decrease 
- by 2 units; now, if we change our mind again and choose LIke, 2 units are added to its karma and...

- creat_a_post_controller: This class controls creat_a_post.fxml.

# help
- https://www.chatgot.io/
- https://www.youtube.com/@WittCode
- https://www.youtube.com/watch?v=7rKqYW7Hd50&t=604s

## Author

Maria Sbouri Dodaran

## Acknowledgments

Inspiration, code snippets, etc.
* [HW](https://github.com/orgs/Advanced-Programming-1402/repositories)
