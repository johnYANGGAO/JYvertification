package com.johnson.jyvertification.Model.InfoModels;

import java.io.Serializable;

/**
 * Created by johnsmac on 4/17/16.
 */
public class BasicInfo implements Serializable {
    /**
     * "Basic": {
     * "CompanyName": "sample string 1",
     * "Status": 1,
     * "Province": "sample string 2",
     * "City": "sample string 3",
     * "Address": "sample string 4",
     * "LegalPerson": "sample string 5",
     * "LegalMobile": "sample string 6",
     * "LegalTel": "sample string 7",
     * "ContactPerson": "sample string 8",
     * "ContactDept": "sample string 9",
     * "ContactTel": [
     * "sample string 1",
     * "sample string 2"
     * ],
     * "ContactEmail": "sample string 10",
     * "CompanyURL": "sample string 11",
     * "InsertDate": "2016-04-17T09:15:51.2283874+08:00"
     * },
     */

    private String CompanyName, Province, City, Address, LegalPerson, LegalMobile, LegalTel,

    ContactPerson, ContactDept, ContactEmail, CompanyURL, InsertDate;
    private int Status;
    private String[] ContactTel;



    public BasicInfo(){
        super();
    }

    public String[] getContactTel() {
        return ContactTel;
    }

    public void setContactTel(String[] contactTel) {
        ContactTel = contactTel;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getLegalPerson() {
        return LegalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        LegalPerson = legalPerson;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLegalMobile() {
        return LegalMobile;
    }

    public void setLegalMobile(String legalMobile) {
        LegalMobile = legalMobile;
    }

    public String getLegalTel() {
        return LegalTel;
    }

    public void setLegalTel(String legalTel) {
        LegalTel = legalTel;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getContactDept() {
        return ContactDept;
    }

    public void setContactDept(String contactDept) {
        ContactDept = contactDept;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String contactEmail) {
        ContactEmail = contactEmail;
    }

    public String getCompanyURL() {
        return CompanyURL;
    }

    public void setCompanyURL(String companyURL) {
        CompanyURL = companyURL;
    }

    public String getInsertDate() {
        return InsertDate;
    }

    public void setInsertDate(String insertDate) {
        InsertDate = insertDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
