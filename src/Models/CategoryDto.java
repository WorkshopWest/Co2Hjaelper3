package Models;

public class CategoryDto {


    // Variables
    private int katID;
    private String katNavn;
    // These are not strictly related to a category object, could be moved to another Dto
    // or put in an inheriting class
    private double Co2Aggregate; // used for returned calculation
    private int count; // Another calculated field for use with query



    // Default constructor
    public CategoryDto(){

        this.katID = 0;
        this.katNavn = "";
    }

    // Overloaded constructor
    public CategoryDto(int katID, String katNavn) {
        this.katID = katID;
        this.katNavn = katNavn;
    }

    // Maybe override toString method

    // Getters and setters
    public int getKatID() {
        return katID;
    }

    public void setKatID(int katID) {
        this.katID = katID;
    }

    public String getKatNavn() {
        return katNavn;
    }

    public void setKatNavn(String katNavn) {
        this.katNavn = katNavn;
    }
    public double getCo2Aggregate() {
        return Co2Aggregate;
    }

    public void setCo2Aggregate(double co2Aggregate) {
        Co2Aggregate = co2Aggregate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCo2Aggregate(double co2){
        Co2Aggregate += co2;
    }
}
