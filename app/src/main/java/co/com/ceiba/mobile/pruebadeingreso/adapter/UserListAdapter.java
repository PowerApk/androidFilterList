package co.com.ceiba.mobile.pruebadeingreso.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.view.PostActivity;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> implements Filterable {

    private Context mCtx;
    private List<User> userList;
    private List<User> userListFiltered;

    public UserListAdapter(Context mCtx, List<User> userList) {
        this.mCtx = mCtx;
        this.userList = userList;
        this.userListFiltered = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final User user = userListFiltered.get(position);
        holder.user_name.setText(user.getName());
        holder.user_email.setText(user.getEmail());
        holder.user_email.setText(user.getPhone());
        holder.btn_view_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostActivity.user = user;
                Intent intent = new Intent(mCtx, PostActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                //String which is used to filter
                String charString = charSequence.toString();
                //Filter logic
                if (charString.isEmpty()) {
                    userListFiltered = userList;
                } else {
                    List<User> filteredList = new ArrayList<>();
                    for (User row : userList) {
                        // Filtering by Name.
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    userListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = userListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userListFiltered = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, user_email, user_phone;
        public Button btn_view_post;
        public MyViewHolder(View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.name);
            user_email = itemView.findViewById(R.id.email);
            user_phone = itemView.findViewById(R.id.phone);
            btn_view_post = itemView.findViewById(R.id.btn_view_post);
        }
    }
}
