package com.example.android.covid19;

import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Vaccine extends AppCompatActivity  {

    String URL_PIN = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin";
    String URL_DIS = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict";
    //String URL_PIN1 = "";
    public static ArrayList<ArrayList<VaccineModel>> VaccineList = new ArrayList<ArrayList<VaccineModel>>();
    public static ArrayList<ArrayList<VaccineModel>> VaccineList_Dis = new ArrayList<ArrayList<VaccineModel>>();
    public static  ArrayList<String> StateId = new ArrayList<String>();
    public static ArrayList<String> DistrictId = new ArrayList<String>();
    public static int position111;
    ArrayList<String> Dis_Name = new ArrayList<String>();
    ArrayList<String> Dis_id = new ArrayList<String>();
    String[] Dis_Id_with_Name;
    public static String dis_id11 = null;
    static int ok1=0;
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public TextView mDate_1, mDate_2, mDate_3, mDate_4, mDate_5;
    public String CombinedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);

        LinearLayout pin_lay = (LinearLayout) findViewById(R.id.pin_layout);
        LinearLayout state_lay = (LinearLayout) findViewById(R.id.state_layout);
        LinearLayout date_lay = (LinearLayout) findViewById(R.id.date_layout);
        pin_lay.setVisibility(View.GONE);
        state_lay.setVisibility(View.GONE);
        date_lay.setVisibility(View.INVISIBLE);
        ListView listView = (ListView) findViewById(R.id.list_vaccine);

        //Adding states
        StateId.clear();
        StateId.add("Andaman and Nicobar Islands");
        StateId.add("Andhra Pradesh");
        StateId.add("Arunachal Pradesh");
        StateId.add("Assam");
        StateId.add("Bihar");
        StateId.add("Chandigarh");
        StateId.add("Chhattisgarh");
        StateId.add("Dadra and Nagar Haveli");
        StateId.add("Daman and Diu");
        StateId.add("Delhi");
        StateId.add("Goa");
        StateId.add("Gujarat");
        StateId.add("Haryana");
        StateId.add("Himachal Pradesh");
        StateId.add("Jammu and Kashmir");
        StateId.add("Jharkhand");
        StateId.add("Karnataka");
        StateId.add("Kerala");
        StateId.add("Ladakh");
        StateId.add("Lakshadweep");
        StateId.add("Madhya Pradesh");
        StateId.add("Maharashtra");
        StateId.add("Manipur");
        StateId.add("Meghalaya");
        StateId.add("Mizoram");
        StateId.add("Nagaland");
        StateId.add("Odisha");
        StateId.add("Puducherry");
        StateId.add("Punjab");
        StateId.add("Rajasthan");
        StateId.add("Sikkim");
        StateId.add("Tamil Nadu");
        StateId.add("Telangana");
        StateId.add("Tripura");
        StateId.add("Uttar Pradesh");
        StateId.add("Uttarakhand");
        StateId.add("West Bengal");


        Spinner spinner_state = (Spinner) findViewById(R.id.state_spinner);
        Spinner spinner_district = (Spinner) findViewById(R.id.district_spinner);
        Button Search_by_pin = (Button) findViewById(R.id.pin_btn);
        Button Search_by_district = (Button) findViewById(R.id.district_btn);
        try {
            CombinedDate = Dates();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final ExecutorService executorservice1 = Executors.newSingleThreadExecutor();
        Search_by_pin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText edittext = (EditText) findViewById(R.id.pin_edit);
                String pin =  edittext.getText().toString();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String date1 = df.format(Calendar.getInstance().getTime());

                VaccineList.clear();
                executorservice1.execute(new Runnable() {
                    @Override
                    public void run() {
                        //background
                        VaccineList = QueryUtils.fetchSlotsFromPin(URL_PIN, date1,pin);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //front
//                                Toast toast = Toast.makeText(Vaccine.this, VaccineList.get(0).get(0).getAddress(), Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
                                date_lay.setVisibility(View.VISIBLE);
                                VaccineAdapter adapterV1 = new VaccineAdapter(Vaccine.this, VaccineList, CombinedDate);
                                listView.setAdapter(adapterV1);
                            }
                        });
                    }
                });
            }
        });

        final ExecutorService executorservice2 = Executors.newSingleThreadExecutor();
        final ExecutorService executorservice3 = Executors.newSingleThreadExecutor();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, StateId);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_state.setAdapter(adapter);
        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // String State = parent.getItemAtPosition(position).toString();
                if (position <= 7) {
                    position++;
                } else if (position == 8) {
                    position = 37;
                }
                position111 = position;
                ok1 = 1;
//                Toast toast = Toast.makeText(parent.getContext(), State+position, Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();

                executorservice2.execute(new Runnable() {
                    @Override
                    public void run() {
                        //background
                        DistrictId.clear();
                        DistrictId = QueryUtils.fetchDistrictID(position111);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Dis_Name.clear();
                                Dis_id.clear();
                                for (int i = 0; i < DistrictId.size(); i++) {
                                    Dis_Id_with_Name = DistrictId.get(i).split("/");
                                    Dis_Name.add(Dis_Id_with_Name[0]);
                                    Dis_id.add(Dis_Id_with_Name[1]);
                                }


                                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, Dis_Name);
                                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_district.setAdapter(adapter2);
                                spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        dis_id11 = Dis_id.get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        dis_id11 = null;
                                    }
                                });



                            }
                        });
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dis_id11 = null;
            }
        });

        Search_by_district.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
                String date2 = df1.format(Calendar.getInstance().getTime());
                VaccineList_Dis.clear();
                if(dis_id11!=null) {
                    executorservice3.execute(new Runnable() {
                        @Override
                        public void run() {
                            //background
                            VaccineList_Dis = QueryUtils.fetchSlotsFromDistrict(URL_DIS, date2, dis_id11);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    Toast toast = Toast.makeText(Vaccine.this, VaccineList_Dis.get(0).get(0).getAddress(), Toast.LENGTH_LONG);
//                                    toast.setGravity(Gravity.CENTER, 0, 0);
//                                    toast.show();
                                    date_lay.setVisibility(View.VISIBLE);
                                    VaccineAdapter adapterV = new VaccineAdapter(Vaccine.this, VaccineList_Dis, CombinedDate);
                                    listView.setAdapter(adapterV);
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    public void OpenPincode(View view){
        LinearLayout pin_lay = (LinearLayout) findViewById(R.id.pin_layout);
        LinearLayout state_lay = (LinearLayout) findViewById(R.id.state_layout);

        pin_lay.setVisibility(View.VISIBLE);
        state_lay.setVisibility(View.GONE);
    }

    public void OpenState(View view){
        LinearLayout pin_lay = (LinearLayout) findViewById(R.id.pin_layout);
        LinearLayout state_lay = (LinearLayout) findViewById(R.id.state_layout);

        pin_lay.setVisibility(View.GONE);
        state_lay.setVisibility(View.VISIBLE);
    }

    public String Dates() throws ParseException{

        mDate_1 = (TextView) findViewById(R.id.Date1);
        mDate_2 = (TextView) findViewById(R.id.Date2);
        mDate_3 = (TextView) findViewById(R.id.Date3);
        mDate_4 = (TextView) findViewById(R.id.Date4);
        mDate_5 = (TextView) findViewById(R.id.Date5);

        long ONE_DAY_MILLI_SECONDS = 24 * 60 * 60 * 1000;

        SimpleDateFormat Df = new SimpleDateFormat(DATE_FORMAT);
        String presentDate = Df.format(Calendar.getInstance().getTime());
        mDate_1.setText(presentDate);
       // SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = Df.parse(presentDate);

        long next1DayMilliSeconds = date.getTime() + ONE_DAY_MILLI_SECONDS;
        Date next1Date = new Date(next1DayMilliSeconds);
        String next1DateStr = Df.format(next1Date);
        mDate_2.setText(next1DateStr);

        long next2DayMilliSeconds = next1Date.getTime() + ONE_DAY_MILLI_SECONDS;
        Date next2Date = new Date(next2DayMilliSeconds);
        String next2DateStr = Df.format(next2Date);
        mDate_3.setText(next2DateStr);

        long next3DayMilliSeconds = next2Date.getTime() + ONE_DAY_MILLI_SECONDS;
        Date next3Date = new Date(next3DayMilliSeconds);
        String next3DateStr = Df.format(next3Date);
        mDate_4.setText(next3DateStr);

        long next4DayMilliSeconds = next3Date.getTime() + ONE_DAY_MILLI_SECONDS;
        Date next4Date = new Date(next4DayMilliSeconds);
        String next4DateStr = Df.format(next4Date);
        mDate_5.setText(next4DateStr);

        return (presentDate+"/"+next1DateStr+"/"+next2DateStr+"/"+next3DateStr+"/"+next4DateStr);

    }
}