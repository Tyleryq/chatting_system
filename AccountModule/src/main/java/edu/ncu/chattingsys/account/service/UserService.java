package edu.ncu.chattingsys.account.service;

import edu.ncu.chattingsys.account.dao.UserDao;
import edu.ncu.chattingsys.commonsapi.model.domain.Subscription;
import edu.ncu.chattingsys.commonsapi.model.domain.Team;
import edu.ncu.chattingsys.commonsapi.model.domain.User;
import edu.ncu.chattingsys.commonsapi.model.responseData.CommonData;
import edu.ncu.chattingsys.commonsapi.model.responseData.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public boolean checkPwd(Integer uid,String pwd,String account_type){
        if (account_type.equals("person")){
            User user=userDao.findUserById(uid);
            return user != null && user.getPwd().equals(pwd);
        }else {
            String dpwd=userDao.getSubscriptionPwdById(uid);
            return pwd.equals(dpwd);
        }
    }
    public int register(User user){//0:成功,1:已存在该名称,2:稍后再试
        if (userDao.findNameByName(user.getUname())!=null){
            return 1;
        }
        if (userDao.insertUser(user)>0)
            return 0;
        else
            return 2;
    }
    public Integer findUidByName(String name){
        return userDao.findUidByName(name);
    }
    public String findUnameByUid(Integer uid){
        return userDao.findNameByUid(uid);
    }
    public CommonData SubscriptionRegister(@RequestBody Subscription subscription){
        Integer s_id=userDao.getSidByName(subscription.getS_name());
        if (s_id!=null) return CommonData.fail("用户名已存在!");
        userDao.insertSubscription(subscription);
        s_id=userDao.getSidByName(subscription.getS_name());
        if (s_id!=null){
            return CommonData.sucess("注册成功!公众号为:"+s_id);
        }else
            return CommonData.fail("服务器繁忙,稍后再试!");
    }
    public void userResetPwd(Integer uid,String pwd){
        userDao.updateUserPwd(uid, pwd);
    }
    public SearchResult search(Integer id){
        SearchResult searchResult=new SearchResult();
        searchResult.setUser(userDao.findUserById(id));
        Team team=userDao.findTeamByTid(id);
        searchResult.setTeam(team);
        if (team!=null){
            searchResult.setTeamCreatorName(userDao.findNameByUid(team.getCreator_id()));
        }
        searchResult.setSubscription(userDao.findSubscriptionBySid(id));
        return searchResult;
    }
    public String findSnameBySid(Integer sid){
        return userDao.findSnameBySid(sid);
    }
    public void deleteAcount(Integer uid){
        userDao.deleteBlockByUid(uid);
        userDao.deleteFriendshipByUid(uid);
        userDao.deleteSubscribeUserByUid(uid);
        deleteTeamByCreatorId(uid);
        userDao.deleteTeamMemberByUid(uid);
        userDao.deleteUserByUid(uid);
        userDao.deleteZoneByUid(uid);
        userDao.deleteZoneCommentByUid(uid);
        userDao.deleteZoneLikeByUid(uid);
    }

    public void deleteTeamByCreatorId(Integer creatorId){//删除所有创建的群聊,包括群成员
        List<Integer> tids=userDao.getTidsByCreatorId(creatorId);
        for(Integer t:tids)
            userDao.deleteTeamMemberByTid(t);
        userDao.deleteTeamByCreatorId(creatorId);
    }
}
