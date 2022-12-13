package Model;

public class Car {

    private int carId;
    private int modelYear;
    private  String vehicule;
    private String carPic;

    public Car(){

    }


    public Car(int carId, int modelYear, String vehicule, String carPic) {
        this.carId = carId;
        this.modelYear = modelYear;
        this.vehicule = vehicule;
        this.carPic = carPic;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public String getVehicule() {
        return vehicule;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }
    public String getCarPic() {
        return carPic;
    }

    public void setCarPic(String carPic) {
        this.carPic = carPic;
    }

}
