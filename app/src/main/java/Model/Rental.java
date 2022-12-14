package Model;

public class Rental {

    private int rentalcarId;
    private int rentalid;
    private  String rentedto;
    private String startDate;
    private String endDate;

    public Rental() {
    }

    public Rental(int rentalcarId, int rentalid, String rentedto, String startDate,String endDate) {
        this.rentalcarId = rentalcarId;
        this.rentalid = rentalid;
        this.rentedto = rentedto;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getRentalcarId() {
        return rentalcarId;
    }

    public void setRentalcarId(int rentalcarId) {
        this.rentalcarId = rentalcarId;
    }

    public int getRentalid() {
        return rentalid;
    }

    public void setRentalid(int rentalid) {
        this.rentalid = rentalid;
    }

    public String getRentedto() {
        return rentedto;
    }

    public void setRentedto(String rentedto) {
        this.rentedto = rentedto;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
