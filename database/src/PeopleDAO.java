
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PeopleDAO")
public class PeopleDAO {     
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public PeopleDAO() {

    }
	       
    /**
     * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/classproject?"
  			          + "useSSL=false&user=project&password=Project1234");
            System.out.println(connect);
        }
    }
    
    public boolean addNewUser(User newUser) throws SQLException {
    	connect_func();
    	
    	String getAllUsers = "SELECT Username FROM user";
    	statement = (Statement) connect.createStatement();
    	ResultSet usernames = statement.executeQuery(getAllUsers);
    	
    	while(usernames.next()) {
    		if(usernames.getString("Username").equals(newUser.username)) {
    			usernames.close();
    			statement.close();
    			disconnect();
    			System.out.println("Username already exists");
    			return false;
    		}
    	}
    	
		String insert = "INSERT INTO user(Username, Password, FirstName, LastName, Age) VALUES (?, ?, ?, ?, ?)";
    	preparedStatement = (PreparedStatement) connect.prepareStatement(insert);
    	preparedStatement.setString(1, newUser.username);
    	preparedStatement.setString(2, newUser.password);
    	preparedStatement.setString(3, newUser.firstName);
    	preparedStatement.setString(4, newUser.lastName);
    	preparedStatement.setString(5, Integer.toString(newUser.age));
    	int rowInserted = preparedStatement.executeUpdate();
    	preparedStatement.close();
    	disconnect();
    	return rowInserted > 0;
    }
    
    public boolean checkLogin(User loginInfo) throws SQLException {
    	connect_func();
    	
    	String sql = "SELECT * FROM user WHERE Username='" + loginInfo.username + "'";
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	if(!resultSet.next()) {
    		return false;
    	}
    	String databasePassword = resultSet.getString("Password");
    	resultSet.close();
        statement.close();
        disconnect();
    	if(loginInfo.password.equals(databasePassword)) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    public User getUserInfo(User loginInfo) throws SQLException {
    	connect_func();
    	
    	String sql = "SELECT * FROM user WHERE Username='" + loginInfo.username + "'";
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	resultSet.next();
    	User userInfo;
    	if(loginInfo.username.contentEquals("root")) {
    		userInfo = new User(resultSet.getString("Username"), resultSet.getString("Password"));
    		
    	}else {
    		userInfo = new User(resultSet.getString("Username"), resultSet.getString("Password"),
        			resultSet.getString("FirstName"), resultSet.getString("LastName"),
        			Integer.parseInt(resultSet.getString("Age")));
    	}
    	resultSet.close();
        statement.close();     
        disconnect();
    	return userInfo;
    }
    
    public void dropAllTables() throws SQLException{
    	connect_func();
    	
    	String dropSQL = "DROP TABLE user";
    	String createUserTable = "CREATE TABLE user (Username CHAR(50), Password CHAR(20), FirstName CHAR(50), LastName CHAR(50), Age INTEGER, PRIMARY KEY(Username))";
    	String addRootUser = "INSERT INTO user(Username, Password) VALUES ('root','pass1234')";
    	String[] users = {"INSERT INTO user(Username, Password, FirstName, LastName, Age) VALUES ('user1', 'pass1', 'user1First', 'user1Last', 1)",
    			"INSERT INTO user(Username, Password, FirstName, LastName, Age) VALUES ('user2', 'pass2', 'user2First', 'user2Last', 2)",
    			"INSERT INTO user(Username, Password, FirstName, LastName, Age) VALUES ('user3', 'pass3', 'user3First', 'user3Last', 3)",
    			"INSERT INTO user(Username, Password, FirstName, LastName, Age) VALUES ('user4', 'pass4', 'user4First', 'user4Last', 4)",
    			"INSERT INTO user(Username, Password, FirstName, LastName, Age) VALUES ('user5', 'pass5', 'user5First', 'user5Last', 5)",
    			"INSERT INTO user(Username, Password, FirstName, LastName, Age) VALUES ('user6', 'pass6', 'user6First', 'user6Last', 6)",
    			"INSERT INTO user(Username, Password, FirstName, LastName, Age) VALUES ('user7', 'pass7', 'user7First', 'user7Last', 7)",
    			"INSERT INTO user(Username, Password, FirstName, LastName, Age) VALUES ('user8', 'pass8', 'user8First', 'user8Last', 8)",
    			"INSERT INTO user(Username, Password, FirstName, LastName, Age) VALUES ('user9', 'pass9', 'user9First', 'user9Last', 9)",
    			"INSERT INTO user(Username, Password, FirstName, LastName, Age) VALUES ('user10', 'pass10', 'user10First', 'user10Last', 10)"
    			};
    	
    	statement = (Statement) connect.createStatement();
    	statement.execute(dropSQL);
    	statement.execute(createUserTable);
    	statement.execute(addRootUser);
    	
    	for(int i = 0; i < 10; i++) {
    		statement.execute(users[i]);
    	}
    	statement.close();
    	disconnect();
    }
    
    public List<User> getAllUsers() throws SQLException{
    	List<User> listOfUsers = new ArrayList<User>(); 
    	connect_func();
    	
    	String sql = "SELECT * FROM user";
    	statement =  (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
        	if(resultSet.getString("Username").equals("root")) {
        		continue;
        	}else {
        		String username = resultSet.getString("Username");
        		String password = resultSet.getString("Password");
        		String firstName = resultSet.getString("FirstName");
        		String lastName = resultSet.getString("LastName");
        		int age = Integer.parseInt(resultSet.getString("Age"));
        		
        		User newUser = new User(username, password, firstName, lastName, age);
                listOfUsers.add(newUser);
        	}
            
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listOfUsers;
    }
    public void insertVideo(String userName, String link, String videoTitle, String videoDescription, String videoTags, String nameComedian) throws SQLException{
    	connect_func();
    	
    	
    	// This line of code is to get the current date and convert object to string 
    	long millis=System.currentTimeMillis();  
    	java.sql.Date date=new java.sql.Date(millis);  
    	String dateString=date.toString(); 
    	System.out.println(dateString);
    	
    	// This line of code is to see if the url (String link) is already in the youtubevideos table
    	String urlCheck = "SELECT COUNT(*) FROM youtubevideos WHERE URL ='"+ link + "'";
    	statement = (Statement) connect.createStatement();
    	ResultSet urlExist = statement.executeQuery(urlCheck);
    	if (urlExist.next()) {
    		int numUrl = urlExist.getInt(1);
        	System.out.println("Number of url: " + numUrl);
        	if (numUrl > 0) {
        		System.out.println("The URL is already in the table cannot add video");
        		return;
        	}
    	}else {
    		System.out.println("Error checking if the URL exist");
    		return;
    	}
    	
    	// This line of code is to check if the user has posted more than 5 videos in same day
    	String numberVideoPosts = "SELECT COUNT(*) FROM youtubevideos WHERE PostUser ='"+ userName +"' AND PostDate = '"+ dateString +"'";
    	ResultSet videoNum = statement.executeQuery(numberVideoPosts);
    	if (videoNum.next()) {
    		int numberOfVideos = videoNum.getInt(1); // 1 is the column number starts at 1
        	System.out.println("Number of Videos Posted in a day: " + numberOfVideos);
        	if (numberOfVideos < 5) { // if video posts in a day are less than 5 then continue adding video
        		// This line of code is going to extract the comid from the comedian table based on
            	// the nameComedian string which holds the comedians last name which is used to search for 
            	// comid in the comedian table. 
            	String comedianID = "SELECT comid FROM comedians WHERE LastName ='"+ nameComedian +"'";
            	ResultSet comedianName = statement.executeQuery(comedianID);
            	int id = 0;
            	if (comedianName.next()) {
            		id = comedianName.getInt("comid");
                	System.out.println("Comedian id: " + id);
               	}else {
               		System.out.println("Comedian id was not found");
               		return;
               	}
            	// This line of code is to insert video into youtubevideos table
            	String insert = "INSERT INTO youtubevideos(url, Title, VideoDescription, comid, PostUser, PostDate) "
            			+ "VALUES (?, ?, ?, ?, ?, ?)";
            	preparedStatement = (PreparedStatement) connect.prepareStatement(insert);
            	preparedStatement.setString(1, link);
            	preparedStatement.setString(2, videoTitle);
            	preparedStatement.setString(3, videoDescription);
            	preparedStatement.setInt(4, id);
            	preparedStatement.setString(5, userName);
            	preparedStatement.setString(6, dateString);
            	preparedStatement.executeUpdate();
            	preparedStatement.close();
            	// This line of code is to insert into youtubetags table
            	String enter = "INSERT INTO youtubetags(url, Tag) "
            			+ "VALUES (?, ?)";
            	preparedStatement = (PreparedStatement) connect.prepareStatement(enter);
            	preparedStatement.setString(1, link);
            	preparedStatement.setString(2, videoTags);
            	preparedStatement.executeUpdate();
            	preparedStatement.close();
            	statement.close();
            	disconnect();
            	System.out.println("Sucessfully inserted a video and added video tags");
        	}
        	else {
        		System.out.println("The number of videos allowed to post in a day exceeds the limit 5");
        		return; 
        	}
       	}else {
       		System.out.println("Error checking number of videos posted in a day");
       		return; 
       	}
    	
    	
    }
    
    public List<YoutubeVideo> getSearchResults(String userInput) throws SQLException {
    	List<YoutubeVideo> searchResults = new ArrayList<YoutubeVideo>();
    	String sql;
    	if(userInput.contains(" ")) {
    		String[] query = userInput.split(" ");
    		String first = query[0];
    		String second = query[1];
    		sql = "SELECT comid FROM comedians WHERE FirstName='" + first + "' OR LastName='" + second + "'";
    	}else {
    		sql = "SELECT comid FROM comedians WHERE FirstName='" + userInput + "' OR LastName='" + userInput + "'";
    	}
    	
    	connect_func();
    	
    	statement =  (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<String> comids = new ArrayList<String>(); 
        while(resultSet.next()) {
        	comids.add(resultSet.getString("comid"));
        }
        
        for(int i = 0; i < comids.size();i++) {
        	String sql2 = "SELECT * FROM youtubevideos WHERE comid='" + comids.get(i) + "'";
    		ResultSet videoResultSet = statement.executeQuery(sql2);
    		while(videoResultSet.next()) {
    			YoutubeVideo video = new YoutubeVideo(videoResultSet.getString("url"), videoResultSet.getString("Title"), videoResultSet.getString("PostUser"));
    			searchResults.add(video);
    		}
        }
    	resultSet.close();
    	statement.close();         
        disconnect();
    	return searchResults;
    }
    
    public List<Comedian> getAllComediansNotInFavorite(String givenUsername) throws SQLException{
    	List<Comedian> allComedians = new ArrayList<Comedian>();
    	connect_func();
    	
    	String sql = "SELECT * FROM comedians WHERE comid NOT IN (SELECT comid FROM isfavorite WHERE Username='" + givenUsername +"')";
    	statement =  (Statement) connect.createStatement();
        ResultSet comedianResultSet = statement.executeQuery(sql);
        
        while(comedianResultSet.next()) {
        	allComedians.add(new Comedian(Integer.parseInt(comedianResultSet.getString("comid")), comedianResultSet.getString("FirstName"), comedianResultSet.getString("LastName"), 
        			comedianResultSet.getString("Birthday"), comedianResultSet.getString("BirthPlace")));
        }
        comedianResultSet.close();
        statement.close();
        disconnect();
    	return allComedians;
    }
    
    public List<Comedian> getFavoriteList(String givenUsername) throws SQLException{
    	List<Comedian> favoriteList = new ArrayList<Comedian>(); 
    	connect_func();
    	
    	String sql = "SELECT * FROM isfavorite WHERE Username='" + givenUsername + "'";
    	statement =  (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        
        List<String> comids = new ArrayList<String>(); 
        while(resultSet.next()) {
        	comids.add(resultSet.getString("comid"));
        }
        
        for(int i = 0; i < comids.size();i++) {
        	String sql2 = "SELECT * FROM comedians WHERE comid='" + comids.get(i) + "'";
        	ResultSet comedianResultSet = statement.executeQuery(sql2);
        	
        	comedianResultSet.next(); 
    		favoriteList.add(new Comedian(Integer.parseInt(comedianResultSet.getString("comid")), comedianResultSet.getString("FirstName"), comedianResultSet.getString("LastName"), 
    			comedianResultSet.getString("Birthday"), comedianResultSet.getString("BirthPlace")));
        }
        resultSet.close();
    	statement.close();
        disconnect();        
        return favoriteList;
    }
    
    public void deleteFromFavorite(String username, String comid) throws SQLException{
    	
    	String sql = "DELETE FROM isfavorite WHERE Username='" + username + "' AND comid='" + comid + "'";
    	
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.executeUpdate();
    	
    	preparedStatement.close();
    	disconnect();
    }
    
    public void addToFavorite(String username, String comid) throws SQLException{
    	
    	String sql = "INSERT INTO isfavorite (Username, comid) VALUES ('" + username + "', '" + comid + "')";
    	
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.executeUpdate();
        
        preparedStatement.close();
    	disconnect();
    }
    
    public void insertReview(String userName, String remark, String rating) throws SQLException{
    	connect_func();
    	
    	// This line of code is to insert review into reviews table
    	String insert = "INSERT INTO reviews(Remark, Rating, Author, Youtubeid) "
    			+ "VALUES (?, ?, ?, ?)";
    	String youtubeidtemp = "ignore";
    	preparedStatement = (PreparedStatement) connect.prepareStatement(insert);
    	preparedStatement.setString(1, remark);
    	preparedStatement.setString(2, rating);
    	preparedStatement.setString(3, userName);
    	preparedStatement.setString(4, youtubeidtemp);
    	preparedStatement.executeUpdate();
    	preparedStatement.close();
    	disconnect();

    }

}