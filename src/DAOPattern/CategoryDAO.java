package DAOPattern;

import Models.CategoryDto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO extends ADao{

    // Variabler
    // SQL strings

    // Streng til findAll
    private static final String GET_ALL = "SELECT KatID, KatNavn FROM public.Kategori";

    // Constructor
    public CategoryDAO()throws SQLException{
        super();

    }

    // Methods

    // Implemented methods

    public ArrayList<CategoryDto> findAll() {
        try (PreparedStatement statement = this.connection.prepareStatement(GET_ALL);){
            ResultSet rs = statement.executeQuery();
            // This can be handled by another class
            ArrayList<CategoryDto> catList = new ArrayList<CategoryDto>();
            while (rs.next()){
                CategoryDto catDto = new CategoryDto();
                catDto.setKatID(rs.getInt("KatID"));
                catDto.setKatNavn(rs.getString("KatNavn"));
                catList.add(catDto);

            }
            return catList;


        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
