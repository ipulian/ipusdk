package com.ipusoft.localcall.constant;

/**
 * author : GWFan
 * time   : 3/29/21 3:07 PM
 * desc   : 系统通话记录类型
 */

public enum CallLogType {
    /**
     * Call log type for incoming calls.
     */
    INCOMING_TYPE(1),
    /**
     * Call log type for outgoing calls.
     */
    OUTGOING_TYPE(2),
    /**
     * Call log type for missed calls.
     */
    MISSED_TYPE(3),
    /**
     * Call log type for voicemails.
     */
    VOICEMAIL_TYPE(4),
    /**
     * Call log type for calls rejected by direct user action.
     */
    REJECTED_TYPE(5),
    /**
     * Call log type for calls blocked automatically.
     */
    BLOCKED_TYPE(6);

    private final int type;

    CallLogType(int type) {
        this.type = type;
    }

    public int getType(){
        return type;
    }


}
