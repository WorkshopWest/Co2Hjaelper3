package Models;

public class FoodDto {

    // Variables
    private int VareID;
    private String VareNavn;
    private double Co2ePrKg;
    private int VareKat;
    private int Amount; // not always used



    // Default constructor, if we need to create an empty object
    public FoodDto (){
        this.VareID = 0;
        this.VareNavn = "";
        this.Co2ePrKg = 0.0;
        this.VareKat = 0;
        this.Amount = 0;
    }

    // Overloaded constructor
    public FoodDto(int vareID, String vareNavn, double co2ePrKg, int vareKat) {
        VareID = vareID;
        VareNavn = vareNavn;
        Co2ePrKg = co2ePrKg;
        VareKat = vareKat;
        Amount = 0;
    }

    // Metoder


    @Override
    public String toString() {
        return VareNavn + " " + Amount + " gram" + " " + Co2ePrKg + " Co2/kg";
    }

    // Getters and setters
    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getVareID() {
        return VareID;
    }

    public void setVareID(int vareID) {
        VareID = vareID;
    }

    public String getVareNavn() {
        return VareNavn;
    }

    public void setVareNavn(String vareNavn) {
        VareNavn = vareNavn;
    }

    public double getCo2ePrKg() {
        return Co2ePrKg;
    }

    public void setCo2ePrKg(double co2ePrKg) {
        Co2ePrKg = co2ePrKg;
    }

    public int getVareKat() {
        return VareKat;
    }

    public void setVareKat(int vareKat) {
        VareKat = vareKat;
    }
}
