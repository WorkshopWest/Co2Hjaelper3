package Controllers;

import Models.UserDto;
import Views.HomeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class HomeController implements ActionListener, ItemListener, ISubscribe, IController { // Works so far...might be confusing if you need more actionlisteners

    // Variables
    private HomeView homeView;
    private UserDto currentUser; // How to make this update a gui object when it changes ?
    private IController currentController;
    private ArrayList<IController> controllers = new ArrayList<>();

    // Constructor
    public HomeController(HomeView view, UserDto currentUser){
        homeView = view;
        this.currentUser = currentUser;
        currentController = this; // When the homecontroller is instantiated, it is the currentcontroller

    }

    public void initController(){

    // Actions
        homeView.getLoginMenuItem().addActionListener(this);
        homeView.getIndkoebMenuItem().addActionListener(this);
        homeView.getForbrugMenuItem().addActionListener(this);
        homeView.getHjemMenuItem().addActionListener(this);
    }

    // Other methods

    // Implementation of actionlistener and itemlistener methods
    @Override
    public void actionPerformed(ActionEvent actionEvent) { // This can be shortened

        System.out.println(actionEvent.getActionCommand()); // Gets the texts it looks like
        String selectedMenuItem = actionEvent.getActionCommand();
        System.out.println(selectedMenuItem);
        JLayeredPane pane = homeView.getJLayeredPane();
        // Based on the selected menuItem, switch Jpanels in the layeredpane
        CardLayout cardLayout = (CardLayout) homeView.getJLayeredPane().getLayout(); // get the cardlayout
        System.out.println(cardLayout);
        cardLayout.show(pane, selectedMenuItem); // The variable we are aiming for is Card Name


        /* Since the homecontroller knows which card (view) is currently choosen
         it can ask the controller of the current view to do any updates
         such as needed if the user changed or new shopping was added or clearing the view
         it requires an interface for the controllers
        */
        // First change the current controller
        // Match the selected view with a controller from the arraylist of controllers
        // What to match on ?

        switch(selectedMenuItem) {
            case "Home":
                currentController = this;
                System.out.println(currentController.getClass());
                break;
            case "Bruger Login":
                currentController = controllers.get(0);
                System.out.println(currentController.getClass());
                break;
            case "Indkøb":
                currentController = controllers.get(1);
                System.out.println(currentController.getClass());
                break;
            case "Forbrug":
                currentController = controllers.get(2);
                System.out.println(currentController.getClass());
                break;
            default:
                System.out.println("No matching controller found");
        }

        currentController.updateView(false);
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

    }

    @Override
    public void update() { // Implementing Isubscribe
    // Update the user label when the logincontroller changes the user, this is the update method of the observer

        homeView.getLblUser().setText("Logget ind som : " + currentUser.getBrugerNavn() + " ");

        // We could also ask all controllers to update their views immediately
        for (int i = 0; i < controllers.size(); i++) {
            controllers.get(i).updateView(true);

        }

    }

    @Override // Implement IController
    public void updateView(boolean userContext) {
        System.out.println("Welcome home");
    }

    // Getters and setters

    public ArrayList<IController> getControllers() {
        return controllers;
    }

    public void setControllers(ArrayList<IController> controllers) {
        this.controllers = controllers;
    }
}
