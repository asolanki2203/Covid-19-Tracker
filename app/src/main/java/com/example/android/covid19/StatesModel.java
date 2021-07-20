package com.example.android.covid19;

public class StatesModel {

    private String state,cases,todayCases,deaths,todayDeaths,recovered,newRecovered,active;
    int x;
    public StatesModel() {
    }
    //constructor with parameters
    public StatesModel(String state, String cases, String todayCases, String deaths, String todayDeaths, String recovered, String newRecovered, String active ) {

        this.state = state;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.newRecovered = newRecovered;
        this.active = active;
    }
    //getter and setter
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getCases() {
        x = Integer.parseInt(cases);
        x = Math.abs(x);
        cases = String.valueOf(x);
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }


    public String getTodayCases() {
        x = Integer.parseInt(todayCases);
        x = Math.abs(x);
        todayCases = String.valueOf(x);
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }


    public String getDeaths() {
        x = Integer.parseInt(deaths);
        x = Math.abs(x);
        deaths = String.valueOf(x);
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }


    public String getTodayDeaths() {
        x = Integer.parseInt(todayDeaths);
        x = Math.abs(x);
        todayDeaths = String.valueOf(x);
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }


    public String getRecovered() {
        x = Integer.parseInt(recovered);
        x = Math.abs(x);
        recovered = String.valueOf(x);
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }


    public String getNewRecovered() {
        x = Integer.parseInt(newRecovered);
        x = Math.abs(x);
        newRecovered = String.valueOf(x);
        return newRecovered;
    }

    public void setNewRecovered(String newRecovered) {
        this.newRecovered = newRecovered;
    }


    public String getActive(){
        x = Integer.parseInt(active);
        x = Math.abs(x);
        active = String.valueOf(x);
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
