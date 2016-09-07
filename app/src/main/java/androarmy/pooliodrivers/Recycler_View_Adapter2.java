package androarmy.pooliodrivers;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Recycler_View_Adapter2 extends RecyclerView.Adapter<Recycler_View_Adapter2.View_Holder>  {

    public final String STATUS_URL="http://192.168.1.13/poolio/statuschange.php";//Sumit's pc

    List<Data> list = Collections.emptyList();
    Context context;
    private ItemClickListener clickListener;
    public View view;
//    String mobile_number,vehicleType,vehicleName,vehicleNo,gender,seats;

    public Recycler_View_Adapter2(final List<Data> list, Context context)
    {
        this.list = list;
        this.context = context;

    }


    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myride_list_row, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }


    @Override
    public void onBindViewHolder(View_Holder holder, int position) {

//        holder.id.setText(list.get(position).getId());
      //  holder.name.setText(list.get(position).getFirst_name()+" "+ list.get(position).getLast_name());
        holder.id.setText(list.get(position).getid());
        holder.source.append(list.get(position).getSource());
        holder.destination.append(list.get(position).getDestination());
        holder.date.append(list.get(position).getDate());
        holder.time.append(list.get(position).getTime());
        holder.status = list.get(position).getStatus();
        holder.checkStatus();
       // Log.i("holder.status:: ",holder.status);
        //holder.timestamp.setText(list.get(position).getTimestamp()); //timestamp need to be fetched and value need to be converted in app format





    }



    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);


    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public  class View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView id,source,destination,time,timestamp,date,statusTV;
        public CardView cv;
        public Button button_cancel,button_complete;
        public String status;


        public View_Holder(final View view) {
            super(view);
            view.setOnClickListener(this);
            cv=(CardView) view.findViewById(R.id.cv);
            id=(TextView)view.findViewById(R.id.ride_id);
            source=(TextView)view.findViewById(R.id.source);
            destination=(TextView)view.findViewById(R.id.destination);
            date=(TextView)view.findViewById(R.id.date);
            time=(TextView)view.findViewById(R.id.time);
            timestamp=(TextView)view.findViewById(R.id.timestamp);
            button_complete = (Button)view.findViewById(R.id.button_complete);
            button_cancel = (Button)view.findViewById(R.id.button_cancel);
            statusTV=(TextView)view.findViewById(R.id.status_tv);


            button_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(view.getContext(),"completed",Toast.LENGTH_LONG).show();
                    changeStatus("1",id.getText().toString());
                }
            });

            button_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(view.getContext(),"cancelled",Toast.LENGTH_LONG).show();
                    changeStatus("2",id.getText().toString());
                }
            });
        }
        public void checkStatus(){
        Log.i("View_holder:: ",status);
        statusTV.setText(status);
            if(!"0".equals(status))
            {
                button_complete.setVisibility(View.GONE);
                button_cancel.setVisibility(View.GONE);
                statusTV.setVisibility(View.VISIBLE);
                if("1".equals(status))
                {

                    statusTV.setText("found a travel buddy");
                    statusTV.setTextColor(Color.GREEN);
                }
                else if("2".equals(status))
                {
                    statusTV.setText("Cancelled");
                    statusTV.setTextColor(Color.RED);
                }

            }
        }

        @Override
        public void onClick(View v) {
      //  clickListener.onClick(v, getAdapterPosition());
        }

        void changeStatus(final String status, final String r_id)
        {
            class ChangeStatusClass extends AsyncTask<String,Void,String> {
                ProgressDialog loading;
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(context,"Working","Please wait while we connect to server",true,true);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    if("done".equalsIgnoreCase(s))
                    {
                        button_cancel.setVisibility(View.GONE);
                        button_complete.setVisibility(View.GONE);
                        statusTV.setVisibility(View.VISIBLE);

                        if(status.equals("1"))
                        {
                            statusTV.setText("found a travel buddy");
                            statusTV.setTextColor(Color.GREEN);
                        }
                        else if(status.equals("2"))
                        {
                            statusTV.setText("Cancelled");
                            statusTV.setTextColor(Color.RED);
                        }
                    }
                    else{


                    }

                }

                @Override
                protected String doInBackground(String... params) {
                    HashMap<String,String> data = new HashMap<>();
                    data.put("status",params[0]);
                    data.put("id",params[1]);

                    RegisterUserClass ruc = new RegisterUserClass();

                    String result = ruc.sendPostRequest(STATUS_URL,data);

                    return result;
                }
            }
            ChangeStatusClass csc = new ChangeStatusClass();
            csc.execute(status,r_id);

        }
    }

}
