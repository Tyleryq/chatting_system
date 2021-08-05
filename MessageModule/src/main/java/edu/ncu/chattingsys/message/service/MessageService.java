package edu.ncu.chattingsys.message.service;

import edu.ncu.chattingsys.message.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageDao messageDao;
    public String getNameByUid(Integer uid){
        return messageDao.getNameByUid(uid);
    }
    public List<Integer> getMembersByTid(Integer tid,Integer uid){
        return messageDao.getMembersByTid(tid,uid);
    }

    public Integer getCreatorByTid(Integer tid){
        return messageDao.getCreatorByTid(tid);
    }

    public String getSNameBySid(Integer sid){
        return messageDao.getSNameBySid(sid);
    }

    public String getTNameByTid(Integer tid){
        return messageDao.getTNameByTid(tid);
    }
}
