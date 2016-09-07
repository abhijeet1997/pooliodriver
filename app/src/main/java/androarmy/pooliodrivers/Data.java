package androarmy.pooliodrivers;

/**
 * Created by kjaganmohan on 17/07/16.
 */
public class Data {

    public String id; //rideid
    public String first_name;
    public String last_name;
    public String gender;
    public String mobile;
    public String source;
    public String destination;
    public String type;
    public String date;
    public String time;
    public String vehicle_name;
    public String vehicle_number;
    public String seats;
    public String timestamp;
    public String device_id;
    public String status;





    Data(String id , String first_name, String last_name, String mobile, String gender, String source , String destination, String type,
         String date, String time, String vehicle_name, String vehicle_number, String seats,String device_id)//constructor for findrides
    {
        this.id = id;
        this.first_name=first_name;
        this.last_name=last_name;
        this.gender=gender;
        this.mobile = mobile;
        this.source = source;
        this.destination = destination;
        this.type = type;
        this.date = date;
        this.time = time;
        this.vehicle_name = vehicle_name;
        this.vehicle_number = vehicle_number;
        this.seats = seats;
        this.device_id=device_id;

    }

    public Data(String id, String source, String destination, String date, String time,String timestamp,String status) //constructor for myrides
    {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.timestamp=timestamp;
        this.status=status;
    }

    public String getid(){
        return  id;
    }
    public String getFirst_name(){
        return  first_name;
    }
    public String getLast_name(){
        return  last_name;
    }
    public String getGender(){
        return  gender;
    }
    public String getMobile(){
        return  mobile;
    }
    public String getSource(){
        return source;
    }
    public String getDestination(){
        return destination;
    }
    public String getType(){
        return type;
    }
    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }

    public String getVehicle_name(){
        return vehicle_name;
    }
    public String getVehicle_number(){
        return vehicle_number;
    }
    public String getSeats(){
        return seats;
    }
    public String getDevice_id(){
        return device_id;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public String getStatus() {
        return status;
    }



}
