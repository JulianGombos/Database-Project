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
            case "/favoritelist":
            	favoriteList(request, response);
            	break;
            case "/deletefavorite":
            	deleteFavorite(request, response);
            	break;
            case "/addfavorite":
            	addFavorite(request, response);
            	break;
            case "/upload":
            	uploadVideo(request, response);
            	break;
            case "/writeReview":
            	writeReview(request, response);
            	break;
            case "/tovideopage":
            	toVideoPage(request, response);
            	break;
            case "/videoaddfavorite":
            	videoAddToFavorite(request, response);
            	break;
            case "/toaddvideo":
            	toAddVideo(request, response);
            	break;
            case "/addcomedian":
            	addComedian(request, response);
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
    private void uploadVideo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	
    	User newUser = (User)session.getAttribute("user");
    	String userName = newUser.username; 
    	String url = request.getParameter("URL");
    	String title = request.getParameter("Title");
    	String description = request.getParameter("Description");
    	String tags = request.getParameter("Tags");
    	String comid = request.getParameter("comid");
    	peopleDAO.insertVideo(userName, url, title, description, tags, comid);
    	response.sendRedirect("UserHomePage.jsp");
    	
    }
    private void writeReview(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	
    	System.out.println(request.getRequestURL());
    	
    	User newUser = (User)session.getAttribute("user");
    	String videoUrl = request.getParameter("url");
    	String userName = newUser.username; 
    	String review = request.getParameter("review");
    	String rating = request.getParameter("rating");
    	peopleDAO.insertReview(userName, review, rating, videoUrl);
    	boolean hasReview = peopleDAO.getHasReview(videoUrl, newUser);
    	String endOfUrl = videoUrl.split("=")[1];
    	List<Review> allReviews = peopleDAO.getAllReviews(videoUrl);
    	YoutubeVideo newVideo = peopleDAO.getVideo(videoUrl);
    	boolean isFavorite = peopleDAO.isFavorite(newUser.username, Integer.toString(newVideo.comid));
    	request.setAttribute("hasReview", hasReview);
    	request.setAttribute("isFavorite", isFavorite);
    	request.setAttribute("cutUrl", endOfUrl);
    	request.setAttribute("fullUrl", videoUrl);
    	request.setAttribute("allReviews", allReviews);
    	request.setAttribute("videoData", newVideo);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("VideoPage.jsp");
    	dispatcher.forward(request, response);
    }
    
    private void search(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String userInput = request.getParameter("search");
    	List<YoutubeVideo> searchResults = peopleDAO.getSearchResults(userInput);
    	request.setAttribute("searchResults", searchResults);
    	request.setAttribute("userInput", userInput);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("SearchResultsPage.jsp");
    	dispatcher.forward(request, response);
    }

    private void listPeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	String userInput = request.getParameter("search");
    	List<YoutubeVideo> searchResults = peopleDAO.getSearchResults(userInput);
    	request.setAttribute("searchResults", searchResults);
    	request.setAttribute("userInput", userInput);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("SearchResultsPage.jsp");
    	dispatcher.forward(request, response);
    }
    
    private void favoriteList(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	User newUser = (User)session.getAttribute("user");
    	List<Comedian> favoriteList = peopleDAO.getFavoriteList(newUser.username);
    	request.setAttribute("favoriteList", favoriteList);
    	List<Comedian> comedians = peopleDAO.getAllComediansNotInFavorite(newUser.username);
    	request.setAttribute("comedians", comedians);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("FavoriteList.jsp");
    	dispatcher.forward(request, response);
    }
    
    private void deleteFavorite(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String givenComid = request.getParameter("id");
        User newUser = (User)session.getAttribute("user");
        peopleDAO.deleteFromFavorite(newUser.username, givenComid);
        response.sendRedirect("favoritelist");
    }
    
    private void addFavorite(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
    	String givenComid = request.getParameter("comid");
        User newUser = (User)session.getAttribute("user");
        peopleDAO.addToFavorite(newUser.username, givenComid);
        response.sendRedirect("favoritelist");
    }
    
    private void toVideoPage(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	String sentUrl = request.getParameter("url");
    	String endOfUrl = sentUrl.split("=")[1];
    	User newUser = (User)session.getAttribute("user");
    	YoutubeVideo newVideo = peopleDAO.getVideo(sentUrl);
    	List<Review> allReviews = peopleDAO.getAllReviews(sentUrl);
    	boolean hasReview = peopleDAO.getHasReview(sentUrl, newUser);
    	boolean isFavorite = peopleDAO.isFavorite(newUser.username, Integer.toString(newVideo.comid));
    	request.setAttribute("isFavorite", isFavorite);
    	request.setAttribute("hasReview", hasReview);
    	request.setAttribute("cutUrl", endOfUrl);
    	request.setAttribute("fullUrl", sentUrl);
    	request.setAttribute("allReviews", allReviews);
    	request.setAttribute("videoData", newVideo);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("VideoPage.jsp");
    	dispatcher.forward(request, response);
    }
    
    private void videoAddToFavorite(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	String sentUrl = request.getParameter("url");
    	User newUser = (User)session.getAttribute("user");
    	YoutubeVideo newVideo = peopleDAO.getVideo(sentUrl);
    	peopleDAO.videoAddToFavorite(newUser.username, sentUrl);
    	boolean hasReview = peopleDAO.getHasReview(sentUrl, newUser);
    	request.setAttribute("hasReview", hasReview);
    	String endOfUrl = sentUrl.split("=")[1];
    	List<Review> allReviews = peopleDAO.getAllReviews(sentUrl);
    	boolean isFavorite = peopleDAO.isFavorite(newUser.username, Integer.toString(newVideo.comid));
    	request.setAttribute("isFavorite", isFavorite);
    	request.setAttribute("cutUrl", endOfUrl);
    	request.setAttribute("fullUrl", sentUrl);
    	request.setAttribute("allReviews", allReviews);
    	request.setAttribute("videoData", newVideo);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("VideoPage.jsp");
    	dispatcher.forward(request, response);
    }
    
    private void toAddVideo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	List<Comedian> comedians = peopleDAO.getAllComedians();
    	request.setAttribute("comedians", comedians);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("AddVideo.jsp");
    	dispatcher.forward(request, response);
    }
    
    private void addComedian(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	Comedian newComedian = new Comedian(Integer.parseInt(request.getParameter("comid")), request.getParameter("firstName"), 
    			request.getParameter("lastName"), request.getParameter("birthday"), request.getParameter("birthPlace"));
    	peopleDAO.insertComedian(newComedian);
    	response.sendRedirect("AddComedian.jsp");
    }
}