package com.jassi.loktra;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jassi on 03/06/2016.
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {



    Context context;
    ArrayList<String> authorName, commitDate, commitMessage;

    MyAdapter(Context context, ArrayList<String> authorName, ArrayList<String> commitDate, ArrayList<String> commitMessage) {

        this.context = context;
        this.authorName = authorName;
        this.commitMessage = commitMessage;
        this.commitDate = commitDate;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.single_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.authorName.setText("Author Name -  " + authorName.get(position));
        holder.commitDate.setText("Commit Date - " + commitDate.get(position));
        holder.commitMessage.setText("Commit Message - " + commitMessage.get(position));

    }

    @Override
    public int getItemCount() {
        return authorName.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView authorName, commitDate, commitMessage;

        public MyViewHolder(View itemView) {
            super(itemView);
            authorName = (TextView) itemView.findViewById(R.id.txtVwAuthorName);
            commitDate = (TextView) itemView.findViewById(R.id.txtVwCommitDate);
            commitMessage = (TextView) itemView.findViewById(R.id.txtVwCommitMessage);


        }
    }


}
