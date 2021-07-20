package com.example.android.covid19;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class QueryUtils {
    public static final String LOG_TAG = IndiaCases.class.getName();
    public static List<StatesModel> stateModelsList1 = new ArrayList<>();
    public static StatesModel statesModel1;
    public static VaccineModel vaccineModel1;
    public static String URL_STATE_ID="https://cdn-api.co-vin.in/api/v2/admin/location/states";
    public static String URL_District="https://cdn-api.co-vin.in/api/v2/admin/location/districts/";
    private QueryUtils() {
    }

    //url building func
    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch(MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        return url;
    }

    //make http request , returns json response
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url==null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG, "Error response code" + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }finally{
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }


      //Convert the {@link InputStream} into a String which contains the whole JSON response from the server.

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return  output.toString();
    }


     //Return a list of WorldCases  that has been built up fromn parsing a JSON response.

    public static ArrayList<String> extractIndiaCasesFromJson(String IndiaCasesJson) {

        if(TextUtils.isEmpty(IndiaCasesJson)){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<String> IndiaCases = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject JsonResponse = new JSONObject(IndiaCasesJson);

            String cases = JsonResponse.getString("totalCases");
            String recovered = JsonResponse.getString("recovered");
            String previousDayTests = JsonResponse.getString("previousDayTests");
            String active = JsonResponse.getString("activeCases");
            String todayCases = JsonResponse.getString("activeCasesNew");
            String deaths = JsonResponse.getString("deaths");
            String todayDeaths = JsonResponse.getString("deathsNew");
          //  String affectedCountries = JsonResponse.getString("affectedCountries");
            String todayRecovered  = JsonResponse.getString("recoveredNew");

            IndiaCases.add(cases);
            IndiaCases.add(recovered);
            IndiaCases.add(previousDayTests);
            IndiaCases.add(active);
            IndiaCases.add(todayCases);
            IndiaCases.add(deaths);
            IndiaCases.add(todayDeaths);
         //   WorldCases.add(affectedCountries);
            IndiaCases.add(todayRecovered);

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return IndiaCases;
    }

    /**
     * Query the USGS dataset and return a list of WorldCases.
     */
    public static ArrayList<String> fetchIndiaCasesData(String requestUrl){
        // Create URL object
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }

        ArrayList<String> IndiaCasesData = extractIndiaCasesFromJson(jsonResponse);

        return IndiaCasesData;
    }

    //Return a list of WorldCases  that has been built up fromn parsing a JSON response.
    // for states
    public static List<StatesModel> extractStateDataFromJson(String StateCasesJson) {

        if(TextUtils.isEmpty(StateCasesJson)){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
     //   ArrayList<String> StateCases = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject JsonResponse = new JSONObject(StateCasesJson);
            JSONArray StateArray = JsonResponse.getJSONArray("regionData");
            JSONObject stateVal;

            String name, active, newCases, totalCases, recovered, newRecovered, deaths, newDeaths;
            for(int i=0;i<StateArray.length();i++){
                stateVal = StateArray.getJSONObject(i);

                name = stateVal.getString("region");
                active = stateVal.getString("activeCases");
                newCases = stateVal.getString("newInfected");
                totalCases = stateVal.getString("totalInfected");
                recovered = stateVal.getString("recovered");
                newRecovered = stateVal.getString("newRecovered");
                deaths = stateVal.getString("deceased");
                newDeaths = stateVal.getString("newDeceased");

                statesModel1 = new StatesModel(name, totalCases, newCases, deaths, newDeaths, recovered, newRecovered, active);
                stateModelsList1.add(statesModel1);
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return stateModelsList1;
    }

    /**
     * Query the USGS dataset and return a list of WorldCases.
     */
    public static List<StatesModel> fetchStateCasesData(String requestUrl){
        // Create URL object
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }

        List<StatesModel> IndiaCasesData = extractStateDataFromJson(jsonResponse);

        return IndiaCasesData;
    }


    public static ArrayList<ArrayList<VaccineModel> > extractSlotsFromPin(String SlotAvailableJson) {

        if(TextUtils.isEmpty(SlotAvailableJson)){
            return null;
        }

        ArrayList<ArrayList<VaccineModel>> VaccineDataList
                = new ArrayList<ArrayList<VaccineModel>>();


        try {


            JSONObject JsonResponse = new JSONObject(SlotAvailableJson);
            JSONArray CentreArray = JsonResponse.getJSONArray("centers");
            JSONObject centreVal;
            JSONArray SessionArray;
            String centreName, address, date, age, type, dose1, dose2;

            for(int i=0;i<CentreArray.length();i++){
                VaccineDataList.add(new ArrayList<VaccineModel>());
                centreVal = CentreArray.getJSONObject(i);

                centreName = centreVal.getString("name");
                address = centreVal.getString("address");

                SessionArray = centreVal.getJSONArray("sessions");
                JSONObject SessionVal;

                for(int j=0;j<SessionArray.length();j++){
                    SessionVal = SessionArray.getJSONObject(j);

                    date = SessionVal.getString("date");
                    age = SessionVal.getString("min_age_limit");
                    type = SessionVal.getString("vaccine");
                    dose1 = SessionVal.getString("available_capacity_dose1");
                    dose2 = SessionVal.getString("available_capacity_dose2");

                    vaccineModel1 = new VaccineModel(centreName, address, date, dose1, dose2,age, type);
                    VaccineDataList.get(i).add(j,vaccineModel1);
                }

            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return VaccineDataList;
    }

    public static ArrayList<ArrayList<VaccineModel>> fetchSlotsFromPin(String requestUrl, String DDate, String Pin){
        // Create URL object

        Uri baseUri = Uri.parse(requestUrl);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("pincode", Pin);
        uriBuilder.appendQueryParameter("date", DDate);
        String URL_PIN2 = uriBuilder.toString();
        URL url = createUrl(URL_PIN2);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }

        ArrayList<ArrayList<VaccineModel>> VaccineData = extractSlotsFromPin(jsonResponse);
        String x = String.valueOf(VaccineData.size());

        Log.e(LOG_TAG, "Size="+x);
        return VaccineData;
    }

    public static ArrayList<String> extractDistrictIdFormJson(String DistrictIdJson) {

        if(TextUtils.isEmpty(DistrictIdJson)){
            return null;
        }


        ArrayList<String> DistrictId = new ArrayList<String>();

        try {


            JSONObject JsonResponse = new JSONObject(DistrictIdJson);
            JSONArray DistrictArray = JsonResponse.getJSONArray("districts");
            JSONObject DistrictVal;

            String Dis_Name, Dis_Id;

            for(int i=0;i<DistrictArray.length();i++){
               // VaccineDataList.add(new ArrayList<VaccineModel>());
                DistrictVal = DistrictArray.getJSONObject(i);

                Dis_Name = DistrictVal.getString("district_name");
                Dis_Id = DistrictVal.getString("district_id");

                DistrictId.add(Dis_Name+"/"+Dis_Id);

            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return DistrictId;
    }

    public static ArrayList<String> fetchDistrictID(int Id){
        // Create URL object
        String District_Url = URL_District+Id;
        URL url = createUrl(District_Url);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }

        ArrayList<String> District_Id_data = extractDistrictIdFormJson(jsonResponse);

        return District_Id_data;
    }

    public static ArrayList<ArrayList<VaccineModel>> fetchSlotsFromDistrict(String requestUrl, String DDate, String Id){
        // Create URL object
        Uri baseUri = Uri.parse(requestUrl);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("district_id", Id);
        uriBuilder.appendQueryParameter("date", DDate);
        String URL_PIN2 = uriBuilder.toString();
        URL url = createUrl(URL_PIN2);
        Log.e(LOG_TAG, URL_PIN2);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }

        ArrayList<ArrayList<VaccineModel>> District_Id_data = extractSlotsFromPin(jsonResponse);
        String x = String.valueOf(District_Id_data.size());

        Log.e(LOG_TAG, "Size="+x);
        return District_Id_data;
    }

}
