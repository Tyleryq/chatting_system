package edu.ncu.chattingsys.commonsapi.model.responseData;

import edu.ncu.chattingsys.commonsapi.model.domain.Message;

public class MessageData {
    private Message message;
    private String senderName;
    private String teamName;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
