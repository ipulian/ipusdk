package com.ipusoft.context.bean;

/**
 * author : GWFan
 * time   : 9/26/21 4:14 PM
 * desc   : 会员
 */

public class Member extends Customer {
    //微信头像
    private String avatarUrl;
    //微信昵称
    private String nickName;
    //距离上次签课天数
    private String lastClassSigning;
    //会员状态/天数
    private String effectEnd;
    //签课次数
    private String signTimes;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLastClassSigning() {
        return lastClassSigning;
    }

    public void setLastClassSigning(String lastClassSigning) {
        this.lastClassSigning = lastClassSigning;
    }

    public String getEffectEnd() {
        return effectEnd;
    }

    public void setEffectEnd(String effectEnd) {
        this.effectEnd = effectEnd;
    }

    public String getSignTimes() {
        return signTimes;
    }

    public void setSignTimes(String signTimes) {
        this.signTimes = signTimes;
    }
}
