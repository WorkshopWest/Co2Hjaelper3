package Controllers;


import DAOPattern.UserDAO;
import Models.UserDto;
import Views.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController implements IController {


    // Variables
    private LoginView loginView;

    private UserDAO userDAO;

    private UserDto user;

    // Who is logged in ?
    private UserDto currentUser;

    private ArrayList<ISubscribe> subscribers;

    // Constructor
    public LoginController(LoginView view, UserDto currentUser) throws SQLException {
        loginView = view;
        userDAO = new UserDAO();
        user = new UserDto();
        this.currentUser = currentUser;
        this.currentUser.setBrugerNavn("");
        this.currentUser.setBrugerPassword("");
        //this.currentUser.setBrugerID(2);
        subscribers = new ArrayList<>();


    }

    // Methods
    public void initController(){

    // Actions

    // New user button
    loginView.getBtnNewUser().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            // Get the data from the fields
            setUserDto();

            if (!user.getBrugerPassword().equals("") && !user.getBrugerNavn().equals("")){

                // Call the UserDao to create a new user, using the model
                try {
                    userDAO.createOne(user); // This returns an integer containing the ID of the new user
                    JOptionPane.showMessageDialog(new JFrame(), "Du blev oprettet som bruger, med brugernavn " + user.getBrugerNavn());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            else
                JOptionPane.showMessageDialog(new JFrame(), "Udfyld alle felter, tak.");
        }
    });

    // Login button
        loginView.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Get the data from the fields
                setUserDto();
                // Search the userdatabase table
                try {
                    UserDto userFound = userDAO.findAUser(user);
                    if (userFound.getBrugerID() > 0) {

                        // Dont change the object to user, set the variables of the current object that currentUser refers to, as it is used by other classes
                        currentUser.setBrugerID(userFound.getBrugerID());
                        currentUser.setBrugerNavn(userFound.getBrugerNavn());

                        currentUser.setBrugerBudget(userFound.getBrugerBudget());
                        System.out.println(currentUser.getBrugerBudget() + " Is the current users budget ");

                        System.out.println("Logged in as user " + currentUser.getBrugerNavn() + " with id " + currentUser.getBrugerID());

                        // inform subscribers about the change of user
                        informSubscribers();


                    }
                    else {
                        System.out.println("User not found");
                        JOptionPane.showMessageDialog(new JFrame(), "Brugeren eksisterer ikke");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    // Other methods

    // Method for observer, we want to inform homeview when the currentuser changes
    public void subscribe(ISubscribe subscriber){
        subscribers.add(subscriber);
    }

    private void informSubscribers(){
        for (int i = 0; i < subscribers.size(); i++) {
            subscribers.get(i).update();
        }
    }

    // Getters and setters


    private UserDto getUser() {
        return user;
    }

    private void setUserDto() {
        String userName = loginView.getTxtUserName().getText();
        // Have to use new String for some reason, to avoid an array, toString does not work
        String passWord = new String(loginView.getPswEnter().getPassword());

        user.setBrugerNavn(userName);
        user.setBrugerPassword(passWord);

    }

    public LoginView getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    private UserDto getCurrentUser() {
        return currentUser;
    }

    @Override // Implements IController
    public void updateView(boolean userContext) {

        if (userContext){
            loginView.getTxtUserName().setText("");
            loginView.getPswEnter().setText("");

        }
        else{

        }

    }
}
