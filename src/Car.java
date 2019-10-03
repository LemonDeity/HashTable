public class Car {
    VIN vin;
    String Make;
    int year;
    String Country;
    public Car(String num, String make, int y, String country){
        vin = new VIN(num);
        Make = make;
        year = y;
        Country = country;
    }

    public String toString(){
        return "VIN: "+vin+" Make: "+Make+" Year: "+year+" Country: "+Country;
    }
}
