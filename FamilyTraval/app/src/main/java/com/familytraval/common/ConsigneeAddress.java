package com.familytraval.common;

/**
 * Created by dings on 2016/10/25.
 */

import com.familytraval.callback.Model;

import java.io.Serializable;

/**
        *  收货地址
        */
public class ConsigneeAddress implements Model, Serializable {

    //地址ID
    String addressID;

    String userID;

    //收货人姓名
    String consignee;

    //国家号码
    String country;

    //省份
    String province;

    //城市号码
    String city;

    //区号码
    String district;

    //镇/街道
    String town;

    //详细地址
    String address;

    //电话
    String mobile;

    //是否默认地址
    String isDefault;

    /********* 设计需要, 额外新增字段, 数据库不存在以下字段 *****************/
    String fullAddress;


    //省份
    String provinceLabel;

    //城市号码
    String cityLabel;

    //区号码
    String districtLabel;

    //镇/街道
    String townLabel;


    @Override
    public String[] replaceKeyFromPropertyName() {
        return new String[]{
                "addressID","address_id",
                "fullAddress","full_address",
                "isDefault","is_default",
                "town","twon"
        };
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getProvinceLabel() {
        return provinceLabel;
    }

    public void setProvinceLabel(String provinceLabel) {
        this.provinceLabel = provinceLabel;
    }

    public String getCityLabel() {
        return cityLabel;
    }

    public void setCityLabel(String cityLabel) {
        this.cityLabel = cityLabel;
    }

    public String getDistrictLabel() {
        return districtLabel;
    }

    public void setDistrictLabel(String districtLabel) {
        this.districtLabel = districtLabel;
    }

    public String getTownLabel() {
        return townLabel;
    }

    public void setTownLabel(String townLabel) {
        this.townLabel = townLabel;
    }
}

