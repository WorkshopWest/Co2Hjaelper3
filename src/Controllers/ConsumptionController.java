package Controllers;

import DAOPattern.ConsumptionDAO;
import DAOPattern.UserDAO;
import Models.CategoryDto;
import Models.UserDto;
import Views.ConsumptionView;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class ConsumptionController implements IController{

    // Variables
    private ConsumptionView consumptionView;
    private UserDto currentUser;
    private ConsumptionDAO consumptionDAO;
    private ArrayList<CategoryDto> calculatedCats = new ArrayList<>();
    private DefaultTableModel tableModel;
    private LocalDate currentDate;
    private DecimalFormat formatNumbers;
    private Double dailyBudget;
    private Double yearSoFarBudget;

    // Constructor
    public ConsumptionController(ConsumptionView view, UserDto currentUser){
        consumptionView = view;
        this.currentUser = currentUser;
        currentDate = LocalDate.now();
        System.out.println(currentDate + " is the current date");
        formatNumbers = new DecimalFormat("###.###");
        dailyBudget = 0.0;
        yearSoFarBudget = 0.0;

        try {
            consumptionDAO = new ConsumptionDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    // Methods
    public void initController(){

    setTable();
    // Actions


    // Search button
    consumptionView.getBtnSubmit().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.out.println("Heard your click");
            // Check if logged in
            if (currentUser.getBrugerID() == 0){
                JOptionPane.showMessageDialog(new JFrame(), "Log venligst ind først");
                return;
            }
            // Call the ConsumtionDAO with the dates from the search fields and the user ID
            // First check if both dates are choosen
            LocalDate dateFrom = consumptionView.getDpFrom().getDate();
            LocalDate dateTo = consumptionView.getDpTo().getDate();
            if(dateFrom != null && dateTo != null){
                System.out.println("Great we have the dates");
                // We want to return some data as well

                try {
                    calculatedCats = consumptionDAO.searchPeriod(dateFrom, dateTo, currentUser.getBrugerID());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // We also want a total, we can get the database to calculate it or do it here
                Double totalCo2 = 0.0;

                for (int i = 0; i < calculatedCats.size(); i++) {
                    totalCo2 += calculatedCats.get(i).getCo2Aggregate();

                }
                System.out.println(totalCo2);
                System.out.printf("%.2f %n", totalCo2);

                // Calculate a budget for the search period
                // How many days ?
                long days = DAYS.between(dateFrom, dateTo) + 1;
                System.out.println(days + " days");
                double periodBudget = days * dailyBudget;
                System.out.println(periodBudget + " is the period budget");

                // Set the view
                // First clear the table
                tableModel.setRowCount(0);
                for (int i = 0; i < calculatedCats.size(); i++) {
                    Object[] dataStrings = {calculatedCats.get(i).getKatNavn(), formatNumbers.format(calculatedCats.get(i).getCo2Aggregate()), calculatedCats.get(i).getCount()};
                    fillTable(dataStrings);
                }

                consumptionView.getTxtTotal().setText(formatNumbers.format(totalCo2) + "Kg.Co2e");
                if (totalCo2 > periodBudget){
                    consumptionView.getTxtTotal().setForeground(Color.red);

                }
                else {
                    consumptionView.getTxtTotal().setForeground(Color.green.darker());
                }
                consumptionView.getTxtBudget().setText(String.valueOf(formatNumbers.format(periodBudget)) + " Kg.Co2e");
            }
            else {
                //JOptionPane.showMessageDialog(new JFrame(), "Vælg datoer, tak");
            }
        }
    });

    // Budget button
    consumptionView.getBtnBudget().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            // Check if logged in
            if (currentUser.getBrugerID() == 0){
                JOptionPane.showMessageDialog(new JFrame(), "Log venligst ind først");
                return;
            }
            Integer budget = null;
            while (budget == null){
                try { budget = Integer.parseInt(JOptionPane.showInputDialog("Indtast dit ønskede årsbudget"));
                    System.out.println(budget); // Test
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(new JFrame(), "Indtast et helt tak, tak. ");
                }

            }
            currentUser.setBrugerBudget(budget);
            System.out.println(currentUser.getBrugerBudget());
            // Update the users budget in the database
            System.out.println("Ready to update the database with budget");
            try {
                UserDAO dao = new UserDAO();
                dao.update(currentUser);
                consumptionView.getTxtYearBudget().setText(budget.toString() + " Kg.Co2e");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            setDailyBudget();

        }
    });


    }

    // Set the table with categories
    public void setTable(){

        String rowData[][]={};
        String columnNames[]={"Kategori", "Kg. Co2e", "Antal varer"};

        tableModel = new DefaultTableModel(rowData, columnNames);

        consumptionView.getTblDistribution().setModel(tableModel);
        consumptionView.getTblDistribution().getTableHeader().setFont(new Font("Cantarell", 1, 16));


    }
    // Put data in the table model
    public void fillTable(Object[] rowToAdd){
        // First reset the model

        tableModel.addRow(rowToAdd);

    }

    // Implement IController
    @Override
    public void updateView(boolean userContext) {
        // Reset the yearsofar budget
        yearSoFarBudget = 0.0;


        // We want to get the budget and the consumption for each month to populate the barchart
        // We need to know the current year
        Integer currentYear = currentDate.getYear();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "Maj", "Juni", "Juli", "Aug", "Sep", "Okt", "Nov", "Dec"};
        Double sum = 0.0;
        // Create some date objects for the call to the dao
        for (int i = 1; i < 13; i++) {

            YearMonth monthOfYear = YearMonth.of(currentYear, i);
            LocalDate firstOfMonth = monthOfYear.atDay(1); // First day of the month
            LocalDate lastOfMonth = monthOfYear.atEndOfMonth();
            //System.out.println(firstOfMonth + " is the  first day of month number " + i);
            //System.out.println(lastOfMonth + " is the last day of month number " + i);

            // Call the dao for monthly totals
            try {
                sum = consumptionDAO.sumPeriod(firstOfMonth, lastOfMonth, currentUser.getBrugerID());
                System.out.println(sum + " is the total sum of consumption for month number " + i);

            } catch (SQLException e) {
                e.printStackTrace();

            }
            // Set the consumption for the year so far
            System.out.println(yearSoFarBudget + " yearsofar before");
            yearSoFarBudget += sum;
            System.out.println(yearSoFarBudget + " yearsofar after");

            // Display the totals in the barChart

            CategoryPlot plot = (CategoryPlot) consumptionView.getChartPanel1().getChart().getPlot();
            DefaultCategoryDataset dset = (DefaultCategoryDataset) plot.getDataset();

            // Removes all data in the first iteration only
            if(i == 1)
                dset.clear();
            // Adds a row
            dset.setValue(sum, "KgCo2e", months[i -1]);

        }



        // Display the yearly budget in the textfield
        if (!(currentUser.getBrugerBudget() == 0)) {
            System.out.println(currentUser.getBrugerBudget() + " Is the current users budget");
            consumptionView.getTxtYearBudget().setText(currentUser.getBrugerBudget().toString() + " Kg.Co2e");
        }
        setDailyBudget();

        // Set the expected consumption for the year, we need to know what the daily average is so far
        // How many days into the year ?
        System.out.println(yearSoFarBudget + " is the spent Co2 so far for the year");
        YearMonth monthOfYear = YearMonth.of(currentYear, 1);
        LocalDate firstOfMonth = monthOfYear.atDay(1); // First day of the month
        long daysPassed = DAYS.between(firstOfMonth, LocalDate.now()) + 1;
        System.out.println(daysPassed + " number of days passed");
        // Daily average
        Double average = yearSoFarBudget / daysPassed;
        System.out.println(average + " average per day");
        // Expected consumption
        Double expectedCo2 = average * 365;
        System.out.println(expectedCo2 + " is the expected consumption");
        // Display in the textbox
        consumptionView.getTxtProjection().setText(formatNumbers.format(expectedCo2) + " Kg.Co2e");
        if (currentUser.getBrugerBudget() >= yearSoFarBudget){
            consumptionView.getTxtProjection().setForeground(Color.green.darker());
        }
        else
            consumptionView.getTxtProjection().setForeground(Color.red);


        if (userContext){
            // if the user was changed we also want to do this
            clearSearch();

        }

        if (!userContext){

            consumptionView.getBtnSubmit().doClick();
        }

    }

   private void clearSearch(){
        consumptionView.getDpFrom().clear();
        consumptionView.getDpTo().clear();
        tableModel.setRowCount(0);
        consumptionView.getTxtTotal().setText("");
        consumptionView.getTxtBudget().setText("");


   }


    // Getters and setters

    private void setDailyBudget() {
        double budgetDouble = currentUser.getBrugerBudget();
        dailyBudget = budgetDouble / 365;

    }

}
