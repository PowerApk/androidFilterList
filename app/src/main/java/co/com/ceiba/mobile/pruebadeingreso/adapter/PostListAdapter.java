package co.com.ceiba.mobile.pruebadeingreso.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.Post;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.MyViewHolder> {

    private Context mCtx;
    private List<Post> postList;

    public PostListAdapter(Context mCtx, List<Post> postList) {
        this.mCtx = mCtx;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_list_item, parent, false);

        return new PostListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Post post = postList.get(position);
        holder.post_title.setText(post.getTitle());
        holder.post_body.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView post_title, post_body;
        public MyViewHolder(View itemView) {
            super(itemView);
            post_title = itemView.findViewById(R.id.title);
            post_body = itemView.findViewById(R.id.body);
        }
    }
}
