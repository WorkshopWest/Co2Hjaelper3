package Models;

import java.time.LocalDate;

public class ShoppingListDto {

    // Variables
    private int shoppingListID;
    private LocalDate shoppingDate;
    private int userID;


    // Constructor
    // Empty
    public ShoppingListDto() {

    }


    // Getters and setters


    public int getShoppingListID() {
        return shoppingListID;
    }

    public void setShoppingListID(int shoppingListID) {
        this.shoppingListID = shoppingListID;
    }

    public LocalDate getShoppingDate() {
        return shoppingDate;
    }

    public void setShoppingDate(LocalDate shoppingDate) {
        this.shoppingDate = shoppingDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
