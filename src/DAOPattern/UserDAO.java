package DAOPattern;

import Models.UserDto;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO extends ADao {

    // Variables

    // SQL statement strings

    // createOne
    private static final String INSERTUSER = "INSERT INTO public.brugere (brugerNavn, brugerPassword) VALUES (?,?)";
    // validate, we need to be able to check the user login

    private static final String FIND_A_USER = "SELECT * FROM public.brugere WHERE brugerNavn=? AND brugerPassword=?";

    // update
    private static final String UPDATE = "UPDATE public.brugere SET brugerBudget = ? WHERE brugerID = ?";

    // Constructor
    public UserDAO()throws SQLException{
        super();
    }


    // Methods

    // create one user
    public int createOne (UserDto dto) throws SQLException {
        // We want to check if the user exists before adding
        if (findAUser(dto).getBrugerID() < 1){
            try (PreparedStatement statement = this.connection.prepareStatement(INSERTUSER, Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, dto.getBrugerNavn());
                statement.setString(2, dto.getBrugerPassword());
                statement.execute();
                // Get the autogenerated ID of the new user
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                Integer userID = rs.getInt(1);
                return userID;
            }catch(SQLException e){
                e.printStackTrace();
                throw new RuntimeException();
            }

        }
        else
            System.out.println("User exists already");
        JOptionPane.showMessageDialog(new JFrame(), "Brugernavnet eksisterer allerede");
            return 0;


        }

    // Find one and return a user Dto
    public UserDto findAUser (UserDto user) throws SQLException {
        ResultSet rs;
        UserDto userReturned = new UserDto();
        try (PreparedStatement statement = connection.prepareStatement(FIND_A_USER)) {
            statement.setString(1, user.getBrugerNavn());
            statement.setString(2, user.getBrugerPassword());
            rs = statement.executeQuery();

            if (rs.next()) {
                userReturned.setBrugerNavn(rs.getString("brugerNavn"));
                userReturned.setBrugerBudget((rs.getInt("brugerBudget")));
                System.out.println(rs.getInt("brugerBudget") + " Is returned by the database for the user budget");
                userReturned.setBrugerID(rs.getInt("brugerID"));
                return userReturned;
            } else
                return userReturned;


        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();

        }
    }


    // Used to set the user budget from the consumptionController

    public UserDto update(UserDto dto) {
        System.out.println("Dao ready to update");
        try(PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setInt(1, dto.getBrugerBudget());
            statement.setInt(2, dto.getBrugerID());
            statement.execute();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }


        return null;
    }

}
