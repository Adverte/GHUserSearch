package com.example.officepcdell.githubusersearch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.officepcdell.githubusersearch.R;

public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.ViewHolder> {

    private String[] mDataset;
    /**
     * class for receiving each element in list item
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public static class ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_recycler_item);
        }
    }

    /**
     *  Constructor
     * @param dataset
     */
    public RecyclerAdapter(String[] dataset) {
        mDataset = dataset;
    }

    /**
     * create new views
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        // тут можно программно менять layouts attributes (size, margins, paddings и др.)
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // set content for each view item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
