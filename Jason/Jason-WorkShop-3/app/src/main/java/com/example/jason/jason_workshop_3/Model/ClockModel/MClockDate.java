package com.example.jason.jason_workshop_3.Model.ClockModel;

/**
 * Created by jason on 10/06/2016.
 */
public class MClockDate implements MClockDateImpl {
    public String Day_of_Week;
    public String Day;
    public String Month;
    public String Year;

    public String getDay() {
        return Day;
    }
    public String getMonth() {
        return convertMonth(Month);
    }
    public String getMonthNumber() {
        int MM = Integer.parseInt(Month) + 1;
        return String.valueOf(MM);
    }
    public String getYear() {
        return Year;
    }
    public String getDay_of_Week() {
        return convertDay(Day_of_Week);
    }

    public MClockDate(String day_of_Week, String day, String month, String year) {

        Day_of_Week = day_of_Week;
        Day = day;
        Month = month;
        Year = year;
    }
    @Override
    public String convertMonth(String month) {
        int _month = Integer.parseInt(month) + 1;
        String _MM = "";
        switch (_month){
            case 1: _MM = "Jan";
                break;
            case 2: _MM = "Feb";
                break;
            case 3: _MM = "Mar";
                break;
            case 4: _MM = "Apr";
                break;
            case 5: _MM = "May";
                break;
            case 6: _MM = "Jun";
                break;
            case 7: _MM = "Jul";
                break;
            case 8: _MM = "Aug";
                break;
            case 9: _MM = "Sep";
                break;
            case 10: _MM = "Oct";
                break;
            case 11: _MM = "Nov";
                break;
            case 12: _MM = "Dec";
                break;
        }
        return _MM;
    }

    @Override
    public String convertDay(String day) {
        int _day = Integer.parseInt(day);
        String _dd = "";
        switch (_day){
            case 1: _dd = "Sunday";
                break;
            case 2: _dd = "Monday";
                break;
            case 3: _dd = "TuesDay";
                break;
            case 4: _dd = "Wednesday";
                break;
            case 5: _dd = "Thursday";
                break;
            case 6: _dd = "Friday";
                break;
            case 7: _dd = "Saturday";
                break;
        }
        return _dd;
    }

    public String getDate(){
        return getDay() + "/" + getMonthNumber() + "/" + getYear();
    }
}
