package edu.ncu.chattingsys.commonsapi.model.responseData;

import edu.ncu.chattingsys.commonsapi.model.domain.Zone;

import java.util.List;

public class ZoneResponseData {
    private Zone zone;
    private String uname;
    private List<String> likes;//点赞人昵称
    private List<ZoneComment> zoneComments;
    private Integer likeNum;

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public List<ZoneComment> getZoneComments() {
        return zoneComments;
    }

    public void setZoneComments(List<ZoneComment> zoneComments) {
        this.zoneComments = zoneComments;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }
}
