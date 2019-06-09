package co.com.ceiba.mobile.pruebadeingreso.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.adapter.UserListAdapter;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.rest.Request;
import co.com.ceiba.mobile.pruebadeingreso.view.MainActivity;

public class ManageSharedPreferences {

    public static final String PREFERENCES = "AppInfo";
    public static final String USER_LIST = "UserList";
    private static final Type listType = new TypeToken<ArrayList<User>>(){}.getType();
    private static Context mCtx;
    private static List<User> objectUserList;
    private static String stringUserList;

    public static void initUsersList(Context context, ProgressDialog progressDialog, UserListAdapter mAdapter, List<User> usertList){
        mCtx = context;
        progressDialog.show();
        //shared preferences for LocalInfo
        SharedPreferences userListLocal = context.getSharedPreferences(PREFERENCES, 0); // 0 -> private mode
        objectUserList = new Gson().fromJson(userListLocal.getString(USER_LIST,"[]"), listType);
        //Validation if data is stored local or need request it
        if(objectUserList.size()==0){
            Request.consultUsersInfo(context,mAdapter,usertList,progressDialog);
        }else{
            usertList.clear();
            usertList.addAll(objectUserList);
            mAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }
    }

    public static void setUsers(ArrayList<User> usersList){
        //Update Shared preferences for LocalInfo
        objectUserList = usersList;
        SharedPreferences userListLocal = mCtx.getSharedPreferences(PREFERENCES, 0); // 0 -> private mode
        SharedPreferences.Editor editor = userListLocal.edit();
        Gson gson = new Gson();
        stringUserList = gson.toJson(objectUserList);
        editor.putString(USER_LIST,stringUserList);
        editor.commit();
    }
}
