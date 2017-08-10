package com.sharekeg.streetpal.chatcomponents;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sharekeg.streetpal.R;

import static java.security.AccessController.getContext;

/**
 * Created by Khalid on 7/31/2017.
 */
public class UserGuide {
    public static final int USER_CALLS_HELP = 0,
            USER_FEELS_FOLLOWED = 1,
            USER_FEELS_IN_DANGER = 2,
            USER_HAS_BEEN_HARASSED = 3,
            USER_IS_FOLLOWED_SURROUNDINGS_ARE_SAFE = 4,
            USER_IS_FOLLOWED_SURROUNDINGS_ARE_NOT_SAFE = 5,
            SEND_STRESS_SIGNAL = 1000,
            USER_IS_SAFE = 1001,
            KNOW_MORE = -5;


    ChatMessage chatMessage;

    public ChatMessage guideUserToSafety(int situationId , Context context) {

        switch (situationId) {
            case USER_CALLS_HELP:
                chatMessage = new ChatMessage(context.getResources().getText(R.string.user_guide_question_1).toString(), context.getResources().getText(R.string.user_guide_case_1).toString()
                        , context.getResources().getText(R.string.user_guide_case_2).toString(),context.getResources().getText(R.string.user_guide_neutral_button).toString(), USER_FEELS_FOLLOWED, SEND_STRESS_SIGNAL, KNOW_MORE);
                break;
            case USER_FEELS_FOLLOWED:
                chatMessage = new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_qustion1).toString(), USER_IS_FOLLOWED_SURROUNDINGS_ARE_SAFE, SEND_STRESS_SIGNAL);
                break;
            case USER_FEELS_IN_DANGER:
                //will be implemented soon
                break;
            case USER_HAS_BEEN_HARASSED:
                //go to gide for now

                break;
            case USER_IS_FOLLOWED_SURROUNDINGS_ARE_SAFE:
                chatMessage = new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_qustion2).toString(), USER_IS_SAFE, SEND_STRESS_SIGNAL);
                break;
            case USER_IS_SAFE:
                chatMessage = new ChatMessage(context.getResources().getText(R.string.user_guide_user_safe).toString(),false);
                break;
            case SEND_STRESS_SIGNAL:
                chatMessage = new ChatMessage(context.getResources().getText(R.string.user_guide_user_not_safe).toString(),context.getResources().getText(R.string.user_guide_button_map).toString(),-1);
                break;
            case KNOW_MORE:
                chatMessage = new ChatMessage(context.getResources().getText(R.string.user_guide_button_know_more).toString(),context.getResources().getText(R.string.user_guide_button_go_to_guide).toString(),-5);
                break;

        }

        return chatMessage;
    }
}
