package DAOPattern;

import Models.CategoryDto;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ConsumptionDAO extends ADao{

    // Variables
    // SQL statement strings
    private static final String SEARCH_PERIOD = ("SELECT brugerID, VareKat, KatNavn, COUNT(*) AS Antal_varer, SUM(`Co2e pr. kg`*mængde / 1000) AS KgCo2e\n" +
            "FROM public.Fødevarer\n" +
            "       INNER JOIN public.indkøbslister_fødevarer\n" +
            "                  ON VareID = fødevareID\n" +
            "       INNER JOIN public.indkøbslister\n" +
            "                  ON indkøbslister_fødevarer.indkøbslisteID = indkøbslister.indkøbslisteID\n" +
            "        INNER JOIN public.Kategori\n" +
            "        ON Fødevarer.VareKat = Kategori.KatID\n" +
            "WHERE brugerID = ? AND indkøbDato BETWEEN ? AND ?\n" +
            "GROUP BY VareKat");

    private static final String SUM_PERIOD = ("SELECT SUM(`Co2e pr. kg` *mængde / 1000) AS TotalCo2\n" +
            "FROM public.Fødevarer\n" +
            "       INNER JOIN public.indkøbslister_fødevarer\n" +
            "                  ON VareID = fødevareID\n" +
            "       INNER JOIN public.indkøbslister\n" +
            "                  ON indkøbslister_fødevarer.indkøbslisteID = indkøbslister.indkøbslisteID\n" +
            "       INNER JOIN public.Kategori\n" +
            "                  ON Fødevarer.VareKat = Kategori.KatID\n" +
            "WHERE brugerID = ? AND indkøbDato BETWEEN ? AND ?");


    // Constructor

    public ConsumptionDAO() throws SQLException {
        super();
    }

    // Methods

    // We want to get all the grocery items the user bought between the dates choosen (both included).
    // We want the total Co2 use for all the groceryitems, do that calculation in
    // We want the Co2 use divided into grocery categories
    public ArrayList<CategoryDto> searchPeriod (LocalDate dateFrom, LocalDate dateTo, Integer userID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SEARCH_PERIOD);
        statement.setInt(1, userID);
        statement.setDate(2, Date.valueOf(dateFrom));
        statement.setDate(3, Date.valueOf(dateTo));
        ResultSet rs = statement.executeQuery();

        ArrayList<CategoryDto> resultList = new ArrayList<>();

        // Insert the result in the arrayList
        while(rs.next()){
            CategoryDto searchPeriodModel = new CategoryDto();
            searchPeriodModel.setKatID(rs.getInt("VareKat"));
            searchPeriodModel.setKatNavn(rs.getString("KatNavn"));
            searchPeriodModel.setCount(rs.getInt("Antal_varer"));
            searchPeriodModel.setCo2Aggregate(rs.getDouble("KgCo2e"));
            resultList.add(searchPeriodModel);
        }
        return resultList;
    }

    // To get the sum of Co2e of a period
    public Double sumPeriod (LocalDate dateFrom, LocalDate dateTo, Integer userID) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(SUM_PERIOD);
        statement.setInt(1, userID);
        statement.setDate(2, Date.valueOf(dateFrom));
        statement.setDate(3, Date.valueOf(dateTo));
        ResultSet rs = statement.executeQuery();

        // Extract the result
        rs.next();
        return rs.getDouble("TotalCo2");

    }

    // Getters and setters


}
