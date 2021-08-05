package edu.ncu.chattingsys.zone.service;

import edu.ncu.chattingsys.commonsapi.model.domain.Message;

public interface IMQSend {
    public void send(Message message);
}
