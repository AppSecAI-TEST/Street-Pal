package com.sharekeg.streetpal.safeplace;

/**
 * Created by MMenem on 4/17/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharekeg.streetpal.R;

import java.util.List;

/**
 * Created by MMenem on 28/01/2017.
 */

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.MyHolder> {
    List<Guide> Guide;
    Context context;


    public GuideAdapter(Context context, List<Guide> Posts) {
        this.Guide = Posts;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_guide, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.tvTitle.setText(Guide.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return Guide.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public MyHolder(View itemView) {
            super(itemView);
            tvTitle=(TextView)itemView.findViewById(R.id.tvguide);

        }
    }


}
