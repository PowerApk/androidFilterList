package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.adapter.UserListAdapter;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.model.VolleySingleton;
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints;
import co.com.ceiba.mobile.pruebadeingreso.rest.Request;
import co.com.ceiba.mobile.pruebadeingreso.utils.ManageSharedPreferences;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private UserListAdapter mAdapter;
    private ProgressDialog progressDialog;
    private List<User> usertList;
    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Elements setup
        recyclerView = findViewById(R.id.recyclerViewSearchResults);
        editTextSearch = findViewById(R.id.editTextSearch);
        usertList = new ArrayList<>();
        mAdapter = new UserListAdapter(this, usertList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        //Filter setup
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAdapter.getFilter().filter(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //Info setup
        setupDialog();
        ManageSharedPreferences.initUsersList(this,progressDialog,mAdapter,usertList);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setupDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Users");
        progressDialog.setMessage("Just a moment ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }
}