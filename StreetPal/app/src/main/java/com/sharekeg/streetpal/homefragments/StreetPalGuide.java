package com.sharekeg.streetpal.homefragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sharekeg.streetpal.R;
import com.sharekeg.streetpal.chatcomponents.ChatAdapter;
import com.sharekeg.streetpal.chatcomponents.ChatMessage;
import com.sharekeg.streetpal.chatcomponents.UserGuide;
import com.sharekeg.streetpal.safeplace.SafePlaceActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by MMenem on 8/2/2017.
 */

public class StreetPalGuide extends Fragment implements View.OnClickListener {


    private TextView firstChoice, secondChoice, thirdChoice;

    UserGuide userGuide;
    ChatAdapter adapter;
    List<ChatMessage> chatMessages = new ArrayList<>();
    int positiveButtonID, negativeButtonID, neutralButtonID;
    public LinearLayoutManager mLinearLayoutManager;

    RecyclerView guideChatList;

    public StreetPalGuide() {
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View streetPalGuideView = inflater.inflate(R.layout.fragment_street_pal_guide, container, false);
        //Initialize the recycle view
        guideChatList = (RecyclerView) streetPalGuideView.findViewById(R.id.chatList);
        guideChatList.setHasFixedSize(true);
        ColorDrawable cd = new ColorDrawable(getActivity().getResources().getColor(
                R.color.black));

        getActivity().getWindow().setBackgroundDrawable(cd);

        //find the TextViews (Choices)

        firstChoice = (TextView) streetPalGuideView.findViewById(R.id.user_first_choice);
        secondChoice = (TextView) streetPalGuideView.findViewById(R.id.user_second_choice);
        thirdChoice = (TextView) streetPalGuideView.findViewById(R.id.user_third_choice);
        firstChoice.setOnClickListener(this);
        secondChoice.setOnClickListener(this);
        thirdChoice.setOnClickListener(this);


        guideChatList.setLayoutManager(mLinearLayoutManager);
//        mLinearLayoutManager.setStackFromEnd(true);

        userGuide = new UserGuide();
        ChatMessage chatMessage = userGuide.guideUserToSafety(UserGuide.USER_CALLS_HELP, getContext());
        chatMessages.add(chatMessage);
        manageOptionsDisplay(chatMessage);
        setButtonsIDs(chatMessage);
        Log.i("First_message", chatMessage.toString());
        // Initialize the adapter for the messages

        adapter = new ChatAdapter(getActivity(), chatMessages);

        guideChatList.setAdapter(adapter);


        return streetPalGuideView;
    }

    @Override
    public void onClick(View v) {
        if (v == firstChoice) {
            Log.i("Button_ID_selected", String.valueOf(positiveButtonID));
            if (positiveButtonID == -1) {
                //navigate to whatever you want
                showNearstSafePlace();

                Toast.makeText(getContext(), "Call for help", Toast.LENGTH_SHORT).show();
            } else if (positiveButtonID == -2) {

                getActivity().onBackPressed();
                // Toast.makeText(getContext(), "Close", Toast.LENGTH_SHORT).show();
            } else if (positiveButtonID == -5) {

                startGuideFragment();

                // Toast.makeText(getContext(), "Guide", Toast.LENGTH_SHORT).show();
            } else {
                ChatMessage userMessage = new ChatMessage(firstChoice.getText().toString(), true);
                Log.i("Following_message", userMessage.toString());
                displayNewMessage(userMessage);
                ChatMessage newChatMessage = userGuide.guideUserToSafety(positiveButtonID, getContext());
                Log.i("Following_message", newChatMessage.toString());
                manageOptionsDisplay(newChatMessage);
                setButtonsIDs(newChatMessage);
                displayNewMessage(newChatMessage);
            }

        } else if (v == secondChoice) {
            Log.i("Button_ID_selected", String.valueOf(negativeButtonID));
            ChatMessage userMessage = new ChatMessage(secondChoice.getText().toString(), true);
            Log.i("Following_message", userMessage.toString());
            displayNewMessage(userMessage);

            ChatMessage newChatMessage = userGuide.guideUserToSafety(negativeButtonID, getContext());
            Log.i("Following_message", newChatMessage.toString());
            manageOptionsDisplay(newChatMessage);
            setButtonsIDs(newChatMessage);
            displayNewMessage(newChatMessage);
        } else if (v == thirdChoice) {
            Log.i("Button_ID_selected", String.valueOf(neutralButtonID));
            ChatMessage userMessage = new ChatMessage(thirdChoice.getText().toString(), true);
            Log.i("Following_message", userMessage.toString());
            displayNewMessage(userMessage);

            ChatMessage newChatMessage = userGuide.guideUserToSafety(neutralButtonID, getContext());
            Log.i("Following_message", newChatMessage.toString());
            manageOptionsDisplay(newChatMessage);
            setButtonsIDs(newChatMessage);
            displayNewMessage(newChatMessage);
        }
    }

    private void startGuideFragment() {

        GuideTab guide = new GuideTab();
        this.getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.rlFragments, guide)
                .commit();
    }

    private void showNearstSafePlace() {
        Intent startSafePlaceActivity = new Intent(getActivity(), SafePlaceActivity.class);
        startActivity(startSafePlaceActivity);
    }

    private void manageOptionsDisplay(ChatMessage chatMessage) {
        switch (chatMessage.getOptionsCount()) {
            case 3:
                firstChoice.setText(chatMessage.getPositiveButtonText());
                secondChoice.setText(chatMessage.getNegativeButtonText());
                thirdChoice.setText(chatMessage.getNeutralButtonText());
                break;
            case 2:
                firstChoice.setText("Yes");
                secondChoice.setText("No");
                // firstChoice.setGravity(Gravity.CENTER);
                thirdChoice.setVisibility(View.GONE);
                break;
            case 1:
                firstChoice.setText(chatMessage.getPositiveButtonText());
                secondChoice.setVisibility(View.GONE);
                thirdChoice.setVisibility(View.GONE);
                break;
            case 0:
                firstChoice.setText("Thanks");
                secondChoice.setVisibility(View.GONE);
                thirdChoice.setVisibility(View.GONE);
                break;

        }
    }

    private void setButtonsIDs(ChatMessage chatMessage) {
        switch (chatMessage.getOptionsCount()) {
            case 3:
                positiveButtonID = chatMessage.getPositiveButtonId();
                negativeButtonID = chatMessage.getNegativeButtonId();
                neutralButtonID = chatMessage.getNeutralButtonId();
                break;
            case 2:
                positiveButtonID = chatMessage.getPositiveButtonId();
                negativeButtonID = chatMessage.getNegativeButtonId();
                break;
            case 1:
                positiveButtonID = chatMessage.getPositiveButtonId();
                break;
            case 0:
                positiveButtonID = -2;
                break;
        }
    }

    private void displayNewMessage(ChatMessage message) {
        chatMessages.add(message);
        adapter.notifyDataSetChanged();
    }

}
