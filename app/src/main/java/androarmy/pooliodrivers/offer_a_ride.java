package androarmy.pooliodrivers;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class offer_a_ride extends Fragment {

    public final String OFFER_URL="http://192.168.1.13/poolio/offer.php"; //Siddharth's pc
    //public final String OFFER_URL="http://192.168.1.12:8080/poolio/offer.php";// Sumit's pc

    String[] locations ={"SRM Arch Gate","Abode Valley","Estancia","Backgate","Potheri Station","Guduvancheri"};//need to make it dynamic
    List<String> vehicleType = new ArrayList<String>(); //No need for dynamic i suppose
    public static Spinner spinner;
    AutoCompleteTextView actv,actv2;
    String source, destination, type="Premium",mobile,date,time,vname,vnumber;
    int availableSeats, amount=0;
    public int chargeable=1; //false for free ride
    SharedPreferences pref;
    EditText sourceET,destinationET, dateET, timeET,vnameET,vnumberET,availableET,amountET;
    static RadioGroup chargeableRG;
    Button offer_button;
    static int dayCheck;
    LinearLayout chargeLayout;
    static boolean timeCheck;// if timeCheck is false -> don't go to next screen
    ImageView Calenderiv;
    private int mYear, mMonth, mDay, mHour, mMinute;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.activity_offer_a_ride, container, false);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.select_dialog_item,locations);

        actv= (AutoCompleteTextView)v.findViewById(R.id.from);
        actv.setThreshold(1);
        actv.setAdapter(adapter);
        actv.setTextColor(Color.RED);
        actv2= (AutoCompleteTextView)v.findViewById(R.id.to);
        actv2.setThreshold(0);
        actv2.setAdapter(adapter);
        actv2.setTextColor(Color.RED);


        sourceET = (EditText)v.findViewById(R.id.from);
        destinationET=(EditText)v.findViewById(R.id.to);
        dateET=(EditText)v.findViewById(R.id.date);
        timeET=(EditText)v.findViewById(R.id.time);
        vnameET=(EditText)v.findViewById(R.id.vname);
        vnumberET=(EditText)v.findViewById(R.id.vnumber);
        availableET=(EditText)v.findViewById(R.id.passengers);
        Calenderiv = (ImageView)v.findViewById(R.id.calender);
        Calenderiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timeET.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateET.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);




            }
        });


//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        dayCheck = day;
//        timeCheck = false;
//        if(day < 10 && (month+1) > 10)
//            date = "0"+day+"/"+(month+1)+"/"+year;
//        else if(day > 10 && (month+1) < 10)
//            date = day+"/0"+(month+1)+"/"+year;
//        else if(day < 10 && (month+1) < 10)
//            date = "0"+day+"/0"+(month+1)+"/"+year;
//        else
//            date = day+"/"+(month+1)+"/"+year;
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);
//        if(minute < 10 && hour > 10)
//            time = hour+":0"+minute;
//        else if(minute > 10 && hour < 10)
//            time = "0"+hour+":"+minute;
//        else if(minute < 10 && hour < 10)
//            time = "0"+hour+":0"+minute;
//        else
//            time = hour+":"+minute;

        timeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timeET.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });
//        dateET.setText(date);





        offer_button = (Button)v.findViewById(R.id.btn_offer);
        offer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerRides();

            }
        });



        return v;

    }

    void offerRides()
    {
        pref = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        mobile = pref.getString("mobile", null);
        source=sourceET.getText().toString().trim();
        destination=destinationET.getText().toString().trim();
        date = dateET.getText().toString();
        time=timeET.getText().toString()+"";
        vname=vnameET.getText().toString();
        vnumber=vnumberET.getText().toString();
        availableSeats=Integer.parseInt(availableET.getText().toString());


        Log.i("**fff**",mobile+" "+source+" "+destination+" "+type+" "+date+" "+time+" "+vname+" "+vnumber+" "+availableSeats+" "+chargeable+" "+amount);
        offer(mobile,source,destination,type,date,time,vname,vnumber,availableSeats,chargeable,amount);
    }

    //11 parameters
    public void offer(String mobile,String source,String destination,String type,String date,String time, String vname, String vnumber, final int availableSeats, int chargeable,int amount){
        class OfferTheRide extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc=new RegisterUserClass();


            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Saving Your Details","Thanks for offering ride", true, true);
            }
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                if("".equals(s))
                {
                    s="Server error, Please try again after some time!";
                }
                else if("successfully registered".equalsIgnoreCase(s)){
                    //Intent myIntent = new Intent(getContext(), Confirmation.class);//Need to make an activity saying your ride has been offered succesfully and will be displayed soon
                    //startActivity(myIntent);
                    //getActivity().overridePendingTransition(R.anim.next_slide_in, R.anim.next_slide_out);
                    Toast.makeText(getContext(),"Ride offered",Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();


            }


            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("mobile",params[0]);
                data.put("source",params[1]);
                data.put("destination",params[2]);
                data.put("type",params[3]);
                data.put("date",params[4]);
                data.put("time",params[5]);
                data.put("vname",params[6]);
                data.put("vnumber",params[7]);
                data.put("availableSeats",params[8]);
                data.put("chargeable",params[9]);
                data.put("amount",params[10]);
                String result = ruc.sendPostRequest(OFFER_URL,data);
                //Log.i("@doinBackground:", result);
                return  result;

            }
        }
        OfferTheRide otr = new OfferTheRide();
        otr.execute(mobile,source,destination,type,date,time,vname,vnumber,Integer.toString(availableSeats),Integer.toString(chargeable),Integer.toString(amount));
    }


}
