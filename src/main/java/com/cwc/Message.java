package com.cwc;

/**
 * @author bwh
 * @date 2019/9/17/017 - 17:30
 * @Description
 */
public class Message {
    private String toUserName;
    private String fromUserName;
    private String createTime;
    private String msgType;
    private String content;
    private String msgId;
    private String event;
    private String latitude;

    public String getLocation_X() {
        return location_X;
    }

    public void setLocation_X(String location_X) {
        this.location_X = location_X;
    }

    public String getLocation_Y() {
        return location_Y;
    }

    public void setLocation_Y(String location_Y) {
        this.location_Y = location_Y;
    }

    public String getScale() {
        return scale;
    }

    @Override
    public String toString() {
        return "Message{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", msgType='" + msgType + '\'' +
                ", content='" + content + '\'' +
                ", msgId='" + msgId + '\'' +
                ", event='" + event + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", precision='" + precision + '\'' +
                ", location_X='" + location_X + '\'' +
                ", location_Y='" + location_Y + '\'' +
                ", scale='" + scale + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private String longitude;
    private String precision;
    private String location_X;
    private String location_Y;
    private String scale;
    private String label;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
