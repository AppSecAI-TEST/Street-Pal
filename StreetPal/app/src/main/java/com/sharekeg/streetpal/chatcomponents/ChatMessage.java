package com.sharekeg.streetpal.chatcomponents;

/**
 * Created by Khalid on 7/29/2017.
 */
public class ChatMessage {
    private String messageText, positiveButtonText, negativeButtonText, neutralButtonText;
    private int optionsCount, positiveButtonId, negativeButtonId, neutralButtonId;
    private boolean isUserMessage;

    public ChatMessage(String messageText, String positiveButtonText, String negativeButtonText, String neutralButtonText, int positiveButtonId, int negativeButtonId, int neutralButtonId) {
        this.messageText = messageText;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
        this.neutralButtonText = neutralButtonText;
        this.optionsCount = 3;
        this.positiveButtonId = positiveButtonId;
        this.negativeButtonId = negativeButtonId;
        this.neutralButtonId = neutralButtonId;
        this.isUserMessage = false;
    }

    public ChatMessage(String messageText, int positiveButtonId, int negativeButtonId) {
        this.messageText = messageText;
        this.optionsCount = 2;
        this.positiveButtonId = positiveButtonId;
        this.negativeButtonId = negativeButtonId;
        this.isUserMessage = false;
    }

    public ChatMessage(String messageText, boolean isUserMessage) {
        this.messageText = messageText;
        this.optionsCount = 0;
        this.isUserMessage = isUserMessage;

    }

    public ChatMessage(String messageText, String positiveButtonText, int positiveButtonId) {
        this.messageText = messageText;
        this.positiveButtonText = positiveButtonText;
        this.positiveButtonId = positiveButtonId;
        this.optionsCount = 1;
        this.isUserMessage = false;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
    }

    public String getNegativeButtonText() {
        return negativeButtonText;
    }

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }

    public String getNeutralButtonText() {
        return neutralButtonText;
    }

    public void setNeutralButtonText(String neutralButtonText) {
        this.neutralButtonText = neutralButtonText;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public int getOptionsCount() {
        return optionsCount;
    }

    public void setOptionsCount(int optionsCount) {
        this.optionsCount = optionsCount;
    }

    public int getPositiveButtonId() {
        return positiveButtonId;
    }

    public void setPositiveButtonId(int positiveButtonId) {
        this.positiveButtonId = positiveButtonId;
    }

    public int getNegativeButtonId() {
        return negativeButtonId;
    }

    public void setNegativeButtonId(int negativeButtonId) {
        this.negativeButtonId = negativeButtonId;
    }

    public int getNeutralButtonId() {
        return neutralButtonId;
    }

    public void setNeutralButtonId(int neutralButtonId) {
        this.neutralButtonId = neutralButtonId;
    }


    public boolean isUserMessage() {
        return isUserMessage;
    }

    public void setUserMessage(boolean userMessage) {
        this.isUserMessage = userMessage;
    }
    public String toString() {
        return messageText;
    }
}
