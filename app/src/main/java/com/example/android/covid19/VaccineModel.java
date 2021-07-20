package com.example.android.covid19;

public class VaccineModel {
    private String centre_name, address, date, dose1, dose2, age, type;

    public VaccineModel(){}

    public VaccineModel(String centre_name, String address, String date, String dose1, String dose2, String age, String type){
        this.centre_name = centre_name;
        this.address = address;
        this.date = date;
        this.dose1 = dose1;
        this.dose2 = dose2;
        this.age = age;
        this.type = type;
    }

    public String getCentre_name(){
        return centre_name;
    }

    public String getAddress(){
        return address;
    }

    public String getDate(){
        return date;
    }

    public String getDose1(){
        return dose1;
    }

    public String getDose2(){
        return dose2;
    }

    public String getAge(){
        return age;
    }

    public String getType(){
        return type;
    }


}
