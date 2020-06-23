import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.xdevapi.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PeopleDAO peopleDAO;
    private HttpSession session = null;
 
    public void init() {
        peopleDAO = new PeopleDAO();
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        try {
            switch (action) {
            case "/login":
            	userLogin(request, response);
                break;
            case "/insert":
            	addUser(request, response);
            	break;
            case "/drop":
            	dropTables(request, response);
            	break;
            case "/list":
            	listAllUsers(request, response);
            	break;
            case "/search":
            	search(request, response);
            	break;
            default:          	
            	userLogin(request, response);           	
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void userLogin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User loginInfo = new User(username, password);
        if(peopleDAO.checkLogin(loginInfo) == true) {
        	session=request.getSession();
        	User userInfo = peopleDAO.getUserInfo(loginInfo);
        	//session.setMaxInactiveInterval(5); Time in seconds before session goes inactive
        	session.setAttribute("user", userInfo);
        	if(userInfo.username.equals("root")) {
        		boolean isRoot = true;
        		session.setAttribute("isRoot", isRoot);
        	}else {
        		boolean isRoot = false;
        		session.setAttribute("isRoot", isRoot);
        	}
        	response.sendRedirect("UserHomePage.jsp");
        }else {
        	System.out.println("Incorrect login info");
        	response.sendRedirect("LoginForm.jsp");
        }
    }
    
    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String confirmPassword = request.getParameter("confirmpassword");
    	String firstName = request.getParameter("firstname");
    	String lastName = request.getParameter("lastname");
    	int age = Integer.parseInt(request.getParameter("age"));
    	User newUser = new User(username, password, firstName, lastName, age);
    	if(password.equals(confirmPassword)) {
    		if(peopleDAO.addNewUser(newUser)) {
        		response.sendRedirect("LoginForm.jsp");
        	}else {
        		System.out.println("Error occured while adding user");
        		response.sendRedirect("AddUser.jsp");
        	}
    	}else {
    		System.out.println("Password mismatch");
    		response.sendRedirect("AddUser.jsp");
    	}
    }
    
    private void dropTables(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	peopleDAO.dropAllTables();
    	response.sendRedirect("LoginForm.jsp");
    }
    
    private void listAllUsers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	List<User> userList = peopleDAO.getAllUsers();
    	request.setAttribute("userList", userList);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("RegisteredUsers.jsp");       
        dispatcher.forward(request, response);
    }
    
    private void search(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	String userInput = request.getParameter("search");
    	List<YoutubeVideo> searchResults = peopleDAO.getSearchResults(userInput);
    	request.setAttribute("searchResults", searchResults);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("SearchResultsPage.jsp");
    	dispatcher.forward(request, response);
    }
    
}

    
/*
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PeopleDAO peopleDAO;
 
    public void init() {
        peopleDAO = new PeopleDAO(); 
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        try {
            switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
            	insertPeople(request, response);
                break;
            case "/delete":
            	deletePeople(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
            	updatePeople(request, response);
                break;
            default:          	
            	listPeople(request, response);           	
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void listPeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<People> listPeople = peopleDAO.listAllPeople();
        request.setAttribute("listPeople", listPeople);       
        RequestDispatcher dispatcher = request.getRequestDispatcher("PeopleList.jsp");       
        dispatcher.forward(request, response);
    }
 
    // to insert a people
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("InsertPeopleForm.jsp");
        dispatcher.forward(request, response);
    }
 
    // to present an update form to update an  existing Student
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        People existingPeople = peopleDAO.getPeople(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("EditPeopleForm.jsp");
        request.setAttribute("people", existingPeople);
        dispatcher.forward(request, response); // The forward() method works at server side, and It sends the same request and response objects to another servlet.
 
    }
 
    // after the data of a people are inserted, this method will be called to insert the new people into the DB
    // 
    private void insertPeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String status = request.getParameter("status");
        People newPeople = new People(name, address, status);
        peopleDAO.insert(newPeople);
        response.sendRedirect("list");  // The sendRedirect() method works at client side and sends a new request
    }
 
    private void updatePeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        System.out.println(id);
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String status = request.getParameter("status");
        
        System.out.println(name);
        
        People people = new People(id,name, address, status);
        peopleDAO.update(people);
        response.sendRedirect("list");
    }
 
    private void deletePeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        //People people = new People(id);
        peopleDAO.delete(id);
        response.sendRedirect("list"); 
    }

}
*/
