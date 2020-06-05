
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
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
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
    
    /*public boolean insert(People people) throws SQLException {
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
    }*/ 
    
    public boolean addNewUser(User newUser) throws SQLException {
    	connect_func();
    	String sql = "INSERT INTO user(Username, Password, FirstName, LastName, Age) VALUES (?, ?, ?, ?, ?)";
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
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
    	resultSet.next();
    	String databasePassword = resultSet.getString("Password");
    	resultSet.close();
        statement.close();         
        disconnect();
        System.out.println(databasePassword);
        System.out.println(loginInfo.password);
    	if(loginInfo.password.equals(databasePassword)) {
    		System.out.println("Im in DAO true");
    		return true;
    	}else {
    		System.out.println("Im in DAO false");
    		return false;
    	}
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
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