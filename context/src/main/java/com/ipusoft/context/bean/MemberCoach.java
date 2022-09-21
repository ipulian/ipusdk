package com.ipusoft.context.bean;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 2022/3/29 14:55
 * desc   :
 */
public class MemberCoach implements Serializable {
    private Long coachId;
    private String coachName;
    private Integer type;//(0:主教练 1:副教练)
    private String deptName;

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
