package lam.tutorials.libraryapp.service;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lam.tutorials.libraryapp.database.UserDAO;
import lam.tutorials.libraryapp.entity.User;

public class UserService {

    public static boolean checkEmail(String email, Context context) {
        List<User> listUser = new ArrayList<>();
        UserDAO userDAO = new UserDAO(context);
        listUser = userDAO.getAllUsers();
        int flag = 0;
        for(User user: listUser) {
            if(user.getEmail().equals(email)) {
                flag = 1;
            }
        }
        if(flag == 0) {
            return false;
        }else{
            return true;
        }
    }

    public static boolean checkCode(String code, Context context) {
        List<User> listUser = new ArrayList<>();
        UserDAO userDAO = new UserDAO(context);
        listUser = userDAO.getAllUserByCode(code);
        if(listUser.size() == 0) {
            return false;
        }else{
            return true;
        }
    }

    public static boolean checkAccount(String code, String pass, Context context) {
        List<User> listUser = new ArrayList<>();
        UserDAO userDAO = new UserDAO(context);
        listUser = userDAO.getAllUserByAccount(code,pass);
        if(listUser.size() == 0) {
            return false;
        }else{
            return true;
        }
    }

    public static boolean isStudent(String code, String pass, Context context) {
        List<User> listUser = new ArrayList<>();
        UserDAO userDAO = new UserDAO(context);
        listUser = userDAO.getAllUserByAccount(code,pass);
        Log.d("isStudent",listUser.get(0).getRole());
        if(listUser.get(0).getRole().equals("Student")) {
            Log.d("isStudent","true");
            return true;
        }else{
            Log.d("isStudent","false");
            return false;
        }
    }

    public static String getNameById(int id_user, Context context) {
        UserDAO userDAO = new UserDAO(context);
        User user = userDAO.getUserByID(id_user);
        return user.getFullname();
    }


}
