package Controllers;

import DAOPattern.CategoryDAO;
import DAOPattern.ShoppingDAO;
import Models.*;
import Views.ShoppingView;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShoppingController implements IController{

    // Variables ------------------------------------------------------------------------------------------------------------

    private UserDto currentUser;

    private int catSelectedIndex;
    private int grocerySelectedIndex;
    private int shoppingListSelectedIndex;

    // Arraylists
    private ArrayList<CategoryDto> catList; // List of categories
    private ArrayList<FoodDto> groceryCat; // List of grocery items in a single category
    private ArrayList<FoodDto> groceryList; // List of choosen groceries

    // Views
    private ShoppingView shoppingView;

    // Viewmodels (like ListModel)
    // private ComboBoxModel<String> comboModel; Not used
    private ListModel<String> lstSelectionModel; // Model for JList of selected items

    // DAO's
    private ShoppingDAO shoppingDAO; /* Do we need to use more Dao's from same controller ?*/
    private CategoryDAO categoryDAO;


    // Contructor ---------------------------------------------------------------------------------------------------------------------

    public ShoppingController (ShoppingView view, UserDto currentUser) throws SQLException {
        this.currentUser = currentUser;

        shoppingView = view;

        catList = new ArrayList<CategoryDto>();
        groceryCat = new ArrayList<FoodDto>();
        groceryList = new ArrayList<FoodDto>();

        shoppingDAO = new ShoppingDAO();
        categoryDAO = new CategoryDAO();

        lstSelectionModel = new DefaultListModel<>();
        shoppingView.getLstSelection().setModel(lstSelectionModel);

        pieChart();



    }

    // Methods ---------------------------------------------------------------------------------------------------------

    public void initController(){
        // Fill category combobox
        displayCategories();
        // Add top item to grocery combobox
        shoppingView.getCmbBoxGrocery().addItem("Vælg vare");
        // actionlisteners etc

        // category combobox
        shoppingView.getCmbBoxCategory().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                                // Remove duplication of selection
                /* We want the index of the selection and retrive a list of grocery items of that category
                 and display them */

                // Get the index
                catSelectedIndex = shoppingView.getCmbBoxCategory().getSelectedIndex();
                System.out.println(catSelectedIndex);
                // retrieve the grocery items from db
                if (catSelectedIndex != 0){

                    groceryCat = shoppingDAO.findbyCategory(catSelectedIndex);

                // Post items in combobox
                // First empty combobox
                shoppingView.getCmbBoxGrocery().removeAllItems();
                    shoppingView.getCmbBoxGrocery().addItem("Vælg vare");
                for (int i = 0; i < groceryCat.size(); i++) {
                    shoppingView.getCmbBoxGrocery().addItem(groceryCat.get(i).getVareNavn() + " " + groceryCat.get(i).getCo2ePrKg());
                }

                }

            }
        });

        shoppingView.getCmbBoxGrocery().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {

                // Which grocery is selected ? Use the index
                // First ensure there is no duplication

                // Get the selected index

                grocerySelectedIndex = shoppingView.getCmbBoxGrocery().getSelectedIndex();
                System.out.println(grocerySelectedIndex + " Index");


            }
        });
        /* Add button, adds selected grocery item to arraylist and displays it on GUI JList
        but only if all the input is filled out by the user
         */
        shoppingView.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    // validation
                    if (grocerySelectedIndex > 0 && Integer.parseInt(shoppingView.getTxtAmount().getText()) > 0 ) {

                        // Add selected foodDto to arraylist
                        groceryList.add(groceryCat.get(grocerySelectedIndex - 1));
                        // Add the amount to the grocery model that was just added to the groceryList
                        groceryList.get(groceryList.size() - 1).setAmount(Integer.parseInt(shoppingView.getTxtAmount().getText()));


                        // Test groceryList
                        for (int i = 0; i < groceryList.size(); i++) {
                            System.out.println(groceryList.get(i).getVareNavn());
                            System.out.println(groceryList.get(i).getAmount());

                        }
                        // Add selected to JList, based on the list of selected items
                        ((DefaultListModel<String>) lstSelectionModel).addElement(groceryList.get(groceryList.size() - 1).toString());

                        // Display in piechart
                        pieChart();

                    } else {
                        // Message for user
                        JOptionPane.showMessageDialog(new JFrame(), "Vælg en vare og mængde først, tak");

                    }

                } catch (NumberFormatException n){
                    System.out.println("Exception");
                    // Message for user
                    JOptionPane.showMessageDialog(new JFrame(), "Udfyld mængde med et tal, tak");
                }
            }
        });

        // Keep track of selected item in JList
        shoppingView.getLstSelection().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(!listSelectionEvent.getValueIsAdjusting()){ // To avoid double firing
                    System.out.println("Value changed");
                    System.out.println(shoppingView.getLstSelection().getSelectedIndex());
                    shoppingListSelectedIndex = shoppingView.getLstSelection().getSelectedIndex();


                }
            }
        });

        // Remove item from list
        shoppingView.getBtnRemove().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Remove the selected item in the Jlist from the Jlist and the array of choosen items
                // Remove the item from array, do this first !
                groceryList.remove(shoppingListSelectedIndex);
                //System.out.println(groceryList.toString());
                // Remove the item from JList
                ((DefaultListModel<String>) lstSelectionModel).remove(shoppingListSelectedIndex);
                // Update Piechart
                pieChart();

            }
        });

        // Clear the list
        shoppingView.getBtnClear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ((DefaultListModel<String>) lstSelectionModel).removeAllElements();
                // Also clear the arraylist !
                groceryList.clear();
                // Update Piechart
                pieChart();
            }
        });


        // Save the list
        shoppingView.getBtnSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Call the ShoppingDAO with a shoppingListDto and a FoodDto array
                // We need the current userID and the date choosen in the GUI
                // Make sure the user is logged in
                if (currentUser.getBrugerID() == 0){
                    JOptionPane.showMessageDialog(new JFrame(), "Log venligst ind først");
                    return;
                }
                // Make sure that a date has been choosen
                if (shoppingView.getLstSelection().getModel().getSize() != 0 && shoppingView.getDatePicker().getDate() != null){

                    ShoppingListDto shoppingListDto = new ShoppingListDto();
                    shoppingListDto.setShoppingDate(shoppingView.getDatePicker().getDate());
                    shoppingListDto.setUserID(currentUser.getBrugerID());

                    shoppingDAO.saveShopping(groceryList, shoppingListDto);
                    JOptionPane.showMessageDialog(new JFrame(), "Din liste blev gemt");

                    // Also clear the arraylist !
                    groceryList.clear();
                    // Clear the view
                    clearView();
                }
                else
                    JOptionPane.showMessageDialog(new JFrame(), "Vælg dato og tjek at listen ikke er tom, tak");

            }
        });

    }

    // other methods -----------------------------------------------------------------------------------------------------

    // Fetches categories from the database to fill the category combobox
    private void displayCategories(){
            shoppingView.getCmbBoxCategory().addItem("Vælg varekategori");
    catList = categoryDAO.findAll();
        for (int i = 0; i < catList.size(); i++) {
            shoppingView.getCmbBoxCategory().addItem(catList.get(i).getKatID() + " " + catList.get(i).getKatNavn());

        }
    }
    @Override // Implement IController
    public void updateView(boolean userContext) {
        if (userContext){
            clearView();
        }


    }

    private void clearView(){
        shoppingView.getDatePicker().clear();
        shoppingView.getCmbBoxCategory().setSelectedIndex(0);
        shoppingView.getCmbBoxGrocery().setSelectedIndex(0);
        shoppingView.getTxtAmount().setText("Indtast mængde...");
        ((DefaultListModel<String>) lstSelectionModel).removeAllElements();
        pieChart();

    }
    // Set the piechart datamodel
    private void pieChart(){
        // Get the object we need to set
        PiePlot chartPlot = (PiePlot) shoppingView.getPnlChart().getChart().getPlot();
        DefaultPieDataset dataset = (DefaultPieDataset) chartPlot.getDataset();
        // How to set it ?
        // First reset all aggregates
        for (int i = 0; i < catList.size(); i++) {
            catList.get(i).setCo2Aggregate(0);
        // Reset the chart
            dataset.clear();
        }

        // Use the catlist and add the Co2 amounts to the co2 aggregate variable for each item in the choosen list
        for (int i = 0; i < groceryList.size(); i++) {
            // First calculate the Co2
            double Co2e = groceryList.get(i).getCo2ePrKg() * groceryList.get(i).getAmount();
            System.out.println(Co2e);
            // Add to the category
            System.out.println(groceryList.get(i).getVareKat());
            catList.get(groceryList.get(i).getVareKat() -1).addCo2Aggregate(Co2e);

        }
        // Set the datamodel with the categories that are relevant (not 0 Co2), based on the catlist
        for (int i = 0; i < catList.size(); i++) {
            if (catList.get(i).getCo2Aggregate() != 0){
                System.out.println(catList.get(i).getKatNavn());
             dataset.setValue(catList.get(i).getKatNavn(), catList.get(i).getCo2Aggregate());

            }
        }



        //dataset.setValue("Test", 23.3);
        //dataset.setValue("Kategori", 30);
    }
}
