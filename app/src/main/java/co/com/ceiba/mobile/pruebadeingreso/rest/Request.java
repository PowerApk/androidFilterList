package co.com.ceiba.mobile.pruebadeingreso.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.adapter.PostListAdapter;
import co.com.ceiba.mobile.pruebadeingreso.adapter.UserListAdapter;
import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.model.VolleySingleton;
import co.com.ceiba.mobile.pruebadeingreso.utils.ManageSharedPreferences;

public class Request {

    public static void consultUsersInfo(final Context context, final UserListAdapter mAdapter, final List<User> usersList, final ProgressDialog progressDialog){

        StringRequest stringRequest =new StringRequest(com.android.volley.Request.Method.GET, Endpoints.URL_BASE + Endpoints.GET_USERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    //Transform response in to List<Model>
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<User>>() {}.getType();
                    ArrayList<User> userList = gson.fromJson(jsonArray.toString(), listType);
                    //Uptade RecyclerView Info
                    usersList.clear();
                    usersList.addAll(userList);
                    mAdapter.notifyDataSetChanged();
                    //Uptade local Info
                    ManageSharedPreferences.setUsers(userList);
                    progressDialog.dismiss();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Check network conexion ...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        VolleySingleton.getmInstance(context).addToRequestque(stringRequest);

    }

    public static void consultPostsInfo(final Context context, final PostListAdapter mAdapter, final List<Post> postsList, final ProgressDialog progressDialog, String userid){

        StringRequest stringRequest =new StringRequest(com.android.volley.Request.Method.GET, Endpoints.URL_BASE + Endpoints.GET_POST_USER+userid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    //Transform response in to List<Model>
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Post>>() {}.getType();
                    ArrayList<Post> postList = gson.fromJson(jsonArray.toString(), listType);
                    //Uptade RecyclerView Info
                    postsList.clear();
                    postsList.addAll(postList);
                    mAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Check network conexion ...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        VolleySingleton.getmInstance(context).addToRequestque(stringRequest);

    }
}
