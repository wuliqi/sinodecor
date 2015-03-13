package cn.app118.model;

import java.util.Date;

public class UserGpsInfo {
    private Integer gpsId;

    private String longitude;//经度

    private String latitude;//纬度

    private Date locationTime;//定位时间

    private Integer userId;//用户标识

    public Integer getGpsId() {
        return gpsId;
    }

    public void setGpsId(Integer gpsId) {
        this.gpsId = gpsId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public Date getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(Date locationTime) {
        this.locationTime = locationTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}