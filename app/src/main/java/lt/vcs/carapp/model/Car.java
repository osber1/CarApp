package lt.vcs.carapp.model;

import java.util.ArrayList;
import java.util.List;

public class Car {
    public int id;
    public int mileage;
    public String techFrom;
    public String techTo;
    public int techPeriod;
    public String insuranceCompany;
    public int insurancePrice;
    public int insurancePeriod;
    public String insuranceFrom;
    public String insuranceTo;
    public String autoName;
    public String autoAbout;

    private List<Car> cars = new ArrayList<>();

    public Car() {
    }

    @Override
    public String toString() {
        return autoName;
    }

    public List<Car> getCars() {
        return cars;
    }

    public int getId() {
        return id;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getTechFrom() {
        return techFrom;
    }

    public void setTechFrom(String techFrom) {
        this.techFrom = techFrom;
    }

    public String getTechTo() {
        return techTo;
    }

    public void setTechTo(String techTo) {
        this.techTo = techTo;
    }

    public int getTechPeriod() {
        return techPeriod;
    }

    public void setTechPeriod(int techPeriod) {
        this.techPeriod = techPeriod;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public int getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(int insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public int getInsurancePeriod() {
        return insurancePeriod;
    }

    public void setInsurancePeriod(int insurancePeriod) {
        this.insurancePeriod = insurancePeriod;
    }

    public String getInsuranceFrom() {
        return insuranceFrom;
    }

    public void setInsuranceFrom(String insuranceFrom) {
        this.insuranceFrom = insuranceFrom;
    }

    public String getInsuranceTo() {
        return insuranceTo;
    }

    public void setInsuranceTo(String insuranceTo) {
        this.insuranceTo = insuranceTo;
    }

    public String getAutoName() {
        return autoName;
    }

    public void setAutoName(String autoName) {
        this.autoName = autoName;
    }

    public String getAutoAbout() {
        return autoAbout;
    }

    public void setAutoAbout(String autoAbout) {
        this.autoAbout = autoAbout;
    }
}
