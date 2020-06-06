
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
  			          + "user=project&password=Project1234");
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
    
}
    
/**
 * Servlet implementation class Connect
 */
/*
@WebServlet("/PeopleDAO")
public class PeopleDAO {     
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public PeopleDAO() {

    }
	     
    protected void connect_func() throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/classproject?"
  			          + "user=project&password=Project1234");
            System.out.println(connect);
        }
    }
    
    public List<People> listAllPeople() throws SQLException {
        List<People> listPeople = new ArrayList<People>();        
        String sql = "SELECT * FROM student";      
        connect_func();      
        statement =  (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            String status = resultSet.getString("status");
             
            People people = new People(id,name, address, status);
            listPeople.add(people);
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listPeople;
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
         
    public boolean insert(People people) throws SQLException {
    	connect_func();         
		String sql = "insert into  student(Name, Address, Status) values (?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, people.name);
		preparedStatement.setString(2, people.address);
		preparedStatement.setString(3, people.status);
//		preparedStatement.executeUpdate();
		
        boolean rowInserted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowInserted;
    }     
     
    public boolean delete(int peopleid) throws SQLException {
        String sql = "DELETE FROM student WHERE id = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, peopleid);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowDeleted;     
    }
     
    public boolean update(People people) throws SQLException {
        String sql = "update student set Name=?, Address =?,Status = ? where id = ?";
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, people.name);
        preparedStatement.setString(2, people.address);
        preparedStatement.setString(3, people.status);
        preparedStatement.setInt(4, people.id);
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowUpdated;     
    }
	
    public People getPeople(int id) throws SQLException {
    	People people = null;
        String sql = "SELECT * FROM student WHERE id = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, id);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            String status = resultSet.getString("status");
             
            people = new People(id, name, address, status);
        }
         
        resultSet.close();
        statement.close();
         
        return people;
    }
}
*/