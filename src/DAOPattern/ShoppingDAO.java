package DAOPattern;

import Models.FoodDto;
import Models.ShoppingListDto;
import java.sql.*;
import java.util.ArrayList;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ShoppingDAO extends ADao {

    // Variables

    // SQL statement strings

    // findbyCategory
    private static final String FIND_CAT = "SELECT VareID, VareNavn, `Co2e pr. kg` , VareKat FROM public.Fødevarer WHERE VareKat=?";

    // saveShopping
    private static final String INSERTSHOPPINGLIST = "INSERT INTO public.indkøbslister (indkøbDato, brugerID) VALUES (?,?)";
    private static final String INSERTGROCERYLIST = "INSERT INTO public.indkøbslister_fødevarer (indkøbslisteID, fødevareID, mængde) VALUES (?,?,?)";

    // Constructor
    public ShoppingDAO() throws SQLException{
        super();
    }

    // Methods

    // Other methods

    public ArrayList<FoodDto> findbyCategory(int category){
        try (PreparedStatement statement = this.connection.prepareStatement(FIND_CAT)){
            statement.setInt(1, category);
            ResultSet rs = statement.executeQuery();
            ArrayList<FoodDto> groceryCat = new ArrayList<FoodDto>();
            while (rs.next()){
                FoodDto model = new FoodDto();
                model.setVareID(rs.getInt("VareID"));
                model.setVareNavn(rs.getString("VareNavn"));
                model.setCo2ePrKg(rs.getDouble("Co2e pr. kg"));
                model.setVareKat(rs.getInt("VareKat"));
                groceryCat.add(model);

            }
        return groceryCat;

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
    // When the user saves the shoppinglist
    public boolean saveShopping (ArrayList<FoodDto> groceryDto, ShoppingListDto listDto) {
        // Insert into indkøbslister and indkøbslister_fødevarer tables when users saves list
        // First insert into shoppinglists, then take shoppinglist id for the grocerylist table
        try (PreparedStatement statement = connection.prepareStatement(INSERTSHOPPINGLIST, RETURN_GENERATED_KEYS)){

            statement.setDate(1, Date.valueOf(listDto.getShoppingDate()));
            statement.setInt(2, listDto.getUserID());

            statement.execute();
            // Get the ID of the row that was inserted
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            Integer listID = rs.getInt(1);
            //System.out.println(rs.getInt(1));
            // Then insert into the grocerylist table
            // We need the shopping list id, the grocery item id and the amount
            // First prepare the statement
            PreparedStatement statement2 = connection.prepareStatement(INSERTGROCERYLIST);
            for (int i = 0; i < groceryDto.size(); i++) {
                statement2.setInt(1, listID);
                statement2.setInt(2, groceryDto.get(i).getVareID());
                statement2.setInt(3, groceryDto.get(i).getAmount());
                statement2.execute();

            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }

        return false;
    }
}
