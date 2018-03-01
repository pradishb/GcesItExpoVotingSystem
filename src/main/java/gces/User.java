package gces;



/**

 * Created by pradish on 2/17/2018.

 */



public class User {

    private int barCode;

    private String name;

    private String contactNo;



    public User(){

        barCode = 0;

        name = "Not defined";

        contactNo = "0000000000";

    }



    public User(int i, String n, String c){

        barCode = i;

        name = n;

        contactNo = c;

    }



    public int getBarCode(){

        return barCode;

    }



    public void setBarCode(int b){

        barCode = b;

    }



    public String getName(){

        return name;

    }



    public void setName(String n){

        name = n;

    }



    public String getContactNo(){

        return contactNo;

    }



    public void setContactNo(String c){

        contactNo = c;

    }

}