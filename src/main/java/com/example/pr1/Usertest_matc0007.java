package com.example.pr1;

public class Usertest_matc0007 {

    public boolean checkUser(String username, String password){

        if(username.equals("matc0007") && password.equals("1234")){
            return true;
        }

        if(username.equals("admin") && password.equals("admin")){
            return true;
        }

        return false;
    }

}