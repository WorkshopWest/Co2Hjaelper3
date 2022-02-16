package Other;

import Controllers.*;
import DB.H2db;
import DB.SingleConnection;
import Views.*;
import Models.UserDto;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Application {

    // Variables
    private UserDto currentUser;

    // Constructor
    public Application(){
        currentUser = new UserDto();

        try {
            setupApplication();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods
    private void setupApplication() throws SQLException {

        // Setup MVC komponenter
        // Setup views
        ShoppingView shoppingView = new ShoppingView();
        HomeView homeView = new HomeView();
        LoginView loginView = new LoginView();
        ConsumptionView consumptionView = new ConsumptionView();

        // Add the JPanels of the individual views to the homeView
        homeView.getJLayeredPane().add(shoppingView.getPanelMain(), "Indkøb");
        homeView.getJLayeredPane().add(loginView.getLoginPanel(), "Bruger Login");
        homeView.getJLayeredPane().add(consumptionView.getConsumptionPanel(), "Forbrug");

        // Setup DAO's
        // So far choosing to let the controllers make their own DAOs

        // Setup Controllers
        ShoppingController shoppingController = new ShoppingController(shoppingView, currentUser);
        shoppingController.initController();
        HomeController homeController = new HomeController(homeView, currentUser);
        homeController.initController();
        LoginController loginController = new LoginController(loginView, currentUser);
        loginController.initController();
        ConsumptionController consumptionController = new ConsumptionController(consumptionView, currentUser);
        consumptionController.initController();

        // To implement the updates to the views when the user changes view the homecontroller
        // needs to know about the other controllers
        ArrayList<IController> controllers = new ArrayList<>();
        controllers.add(loginController);
        controllers.add(shoppingController);
        controllers.add(consumptionController);
        homeController.setControllers(controllers);


        // subscribe the homecontroller to the logincontroller to get notified when the user changes
        loginController.subscribe(homeController);
        //loginController.subscribe(consumptionController); // This may be handled by homecontroller via currentcontroller in stead

        // Setup frame for homeView (GUI window)
        JFrame frame = new JFrame("Co2Hjælper");

        frame.setContentPane(homeView.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("WindowClosingDemo.windowClosing");
                //System.exit(0);
                try {
                    SingleConnection.getSingleton().getSingleConnection().close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Test H2 database connection

        /*H2db H2 = new H2db();
        Connection conn = H2.getConnection();
        System.out.println(conn);
        */
    }


}
