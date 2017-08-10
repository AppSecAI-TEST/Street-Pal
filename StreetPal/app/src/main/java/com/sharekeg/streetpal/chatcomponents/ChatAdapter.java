package com.sharekeg.streetpal.chatcomponents;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.sharekeg.streetpal.R;

import java.util.List;

/**
 * Created by MMenem on 8/2/2017.
 */


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.chatHolder> {
    List<ChatMessage> ChatMessage;
    Context context;
    private View v;


    public ChatAdapter(Context context, List<ChatMessage> ChatMessage) {
        this.ChatMessage = ChatMessage;
        this.context = context;
    }


    @Override
    public chatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_message_rec, parent, false);
        return new chatHolder(v);
    }

    @Override
    public void onBindViewHolder(chatHolder holder, final int position) {
        holder.tvMessageRec.setText(ChatMessage.get(position).getMessageText());
//        Log.i("Message_adapter",ChatMessage.get(position).toString());
        if (ChatMessage.get(position).isUserMessage()) {
            holder.streetPalLogo.setVisibility(View.GONE);
            holder.userChatLogo.setVisibility(View.VISIBLE);
            holder.tvMessageRec.setGravity(RelativeLayout.ALIGN_PARENT_RIGHT);
            //  holder.tvMessageRec.setBackgroundColor(Color.parseColor("#ffffff"));
            //  holder.tvMessageRec.setBackgroundColor();
            //  holder.tvMessageRec.setGravity(RelativeLayout.ALIGN_PARENT_END);
//           holder.tvMessageRec.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.ALIGN_PARENT_RIGHT));


        }

    }

    @Override
    public int getItemCount() {
        return ChatMessage.size();
    }

    public class chatHolder extends RecyclerView.ViewHolder {

        TextView tvMessageRec;
        ImageView streetPalLogo, userChatLogo;

        public chatHolder(View itemView) {
            super(itemView);
            tvMessageRec = (TextView) itemView.findViewById(R.id.tvmessage_send);
            streetPalLogo = (ImageView) itemView.findViewById(R.id.logo_street_pal);
            //  userChatLogo = (ImageView) itemView.findViewById(R.id.logo_chat_user);

        }
    }

}
