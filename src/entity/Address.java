package entity;

public class Address {
    private String street; //Поле не может быть null
    private String zipCode; //Поле может быть null

    public Address(String street, String zipCode) {
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean validate() {
        return  street != null;
    }
    @Override
    public String toString() {
        return "street - "+street +"; zipCode - " + zipCode;
    }
}
