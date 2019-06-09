package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.adapter.PostListAdapter;
import co.com.ceiba.mobile.pruebadeingreso.adapter.UserListAdapter;
import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints;
import co.com.ceiba.mobile.pruebadeingreso.rest.Request;
import co.com.ceiba.mobile.pruebadeingreso.utils.ManageSharedPreferences;

public class PostActivity extends Activity {

    public static User user;

    private RecyclerView recyclerView;
    private PostListAdapter mAdapter;
    private ProgressDialog progressDialog;
    private List<Post> postList;
    private TextView name;
    private TextView phone;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //Elements setup
        recyclerView = findViewById(R.id.recyclerViewPostsResults);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        postList = new ArrayList<>();
        mAdapter = new PostListAdapter(this, postList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        //Info setup
        setupDialog();
        progressDialog.show();
        name.setText(user.getName());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());
        Request.consultPostsInfo(getApplicationContext(),mAdapter,postList,progressDialog,user.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setupDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Posts");
        progressDialog.setMessage("Just a moment ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }

}
