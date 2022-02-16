package Models;

public class UserDto {
    // Variables
    private int brugerID; // Auto generated in db
    private String brugerNavn;
    private String brugerPassword;
    private Integer brugerBudget;

    // Constructors

    public UserDto(){
        brugerNavn = "";
        brugerPassword = "";
        brugerID = 0;
    }

    public UserDto(String brugerNavn, String brugerPassword) {

        this.brugerNavn = brugerNavn;
        this.brugerPassword = brugerPassword;
    }
    // Methods

    // Getters and setters

    public Integer getBrugerBudget() {
        return brugerBudget;
    }

    public void setBrugerBudget(Integer brugerBudget) {
        this.brugerBudget = brugerBudget;
    }

    public int getBrugerID() {
        return brugerID;
    }

    public void setBrugerID(int brugerID) { // Perhaps would be nice to restrict this more

        this.brugerID = brugerID;
    }

    public String getBrugerNavn() {
        return brugerNavn;
    }

    public void setBrugerNavn(String brugerNavn) {
        this.brugerNavn = brugerNavn;
    }

    public String getBrugerPassword() {
        return brugerPassword;
    }

    public void setBrugerPassword(String brugerPassword) {
        this.brugerPassword = brugerPassword;
    }
}
