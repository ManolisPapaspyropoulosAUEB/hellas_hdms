package models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "installers", schema = "hdms_ibo_db_pr", catalog = "")
public class InstallersEntity {
    private int userId;
    private String name;
    private String installerAfm;
    private String billingAddressOnly;
    private String installerBillingAddressOnly;
    private String installerCollectionPolicy;
    private String username;
   /* private String pwd;*/
    private String email;
    private String selfCollection;
    private String emailInvoice;
    private String fax;
    private String installerInsuredAreaAddress;
    private String installerInsuredAreaCity;
    private String installerInsuredAreaFloor;
    private String installerInsuredAreaPostCode;
    private String installerLandlinePhone;
    private String installerMobilePhone;
    private String installerProffesionalDescription;
    private String installerWebsite;
    private String lastName;
    private Date lastUpdatePwd;
    private Byte active;
    private String companyName;
    private String doy;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "installer_afm")
    public String getInstallerAfm() {
        return installerAfm;
    }

    public void setInstallerAfm(String installerAfm) {
        this.installerAfm = installerAfm;
    }

    @Basic
    @Column(name = "billing_address_only")
    public String getBillingAddressOnly() {
        return billingAddressOnly;
    }

    public void setBillingAddressOnly(String billingAddressOnly) {
        this.billingAddressOnly = billingAddressOnly;
    }

    @Basic
    @Column(name = "installer_billing_address_only")
    public String getInstallerBillingAddressOnly() {
        return installerBillingAddressOnly;
    }

  /*  @Basic
    @Column(name = "PWD")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }*/

    public void setInstallerBillingAddressOnly(String installerBillingAddressOnly) {
        this.installerBillingAddressOnly = installerBillingAddressOnly;
    }

    @Basic
    @Column(name = "installer_collection_policy")
    public String getInstallerCollectionPolicy() {
        return installerCollectionPolicy;
    }

    public void setInstallerCollectionPolicy(String installerCollectionPolicy) {
        this.installerCollectionPolicy = installerCollectionPolicy;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "self_collection")
    public String getSelfCollection() {
        return selfCollection;
    }

    public void setSelfCollection(String selfCollection) {
        this.selfCollection = selfCollection;
    }

    @Basic
    @Column(name = "email_invoice")
    public String getEmailInvoice() {
        return emailInvoice;
    }

    public void setEmailInvoice(String emailInvoice) {
        this.emailInvoice = emailInvoice;
    }

    @Basic
    @Column(name = "fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "installer_insured_area_address")
    public String getInstallerInsuredAreaAddress() {
        return installerInsuredAreaAddress;
    }

    public void setInstallerInsuredAreaAddress(String installerInsuredAreaAddress) {
        this.installerInsuredAreaAddress = installerInsuredAreaAddress;
    }

    @Basic
    @Column(name = "installer_insured_area_city")
    public String getInstallerInsuredAreaCity() {
        return installerInsuredAreaCity;
    }

    public void setInstallerInsuredAreaCity(String installerInsuredAreaCity) {
        this.installerInsuredAreaCity = installerInsuredAreaCity;
    }

    @Basic
    @Column(name = "installer_insured_area_floor")
    public String getInstallerInsuredAreaFloor() {
        return installerInsuredAreaFloor;
    }

    public void setInstallerInsuredAreaFloor(String installerInsuredAreaFloor) {
        this.installerInsuredAreaFloor = installerInsuredAreaFloor;
    }

    @Basic
    @Column(name = "installer_insured_area_post_code")
    public String getInstallerInsuredAreaPostCode() {
        return installerInsuredAreaPostCode;
    }

    public void setInstallerInsuredAreaPostCode(String installerInsuredAreaPostCode) {
        this.installerInsuredAreaPostCode = installerInsuredAreaPostCode;
    }

    @Basic
    @Column(name = "installer_landline_phone")
    public String getInstallerLandlinePhone() {
        return installerLandlinePhone;
    }

    public void setInstallerLandlinePhone(String installerLandlinePhone) {
        this.installerLandlinePhone = installerLandlinePhone;
    }

    @Basic
    @Column(name = "installer_mobile_phone")
    public String getInstallerMobilePhone() {
        return installerMobilePhone;
    }

    public void setInstallerMobilePhone(String installerMobilePhone) {
        this.installerMobilePhone = installerMobilePhone;
    }

    @Basic
    @Column(name = "installer_proffesional_description")
    public String getInstallerProffesionalDescription() {
        return installerProffesionalDescription;
    }

    public void setInstallerProffesionalDescription(String installerProffesionalDescription) {
        this.installerProffesionalDescription = installerProffesionalDescription;
    }

    @Basic
    @Column(name = "installer_website")
    public String getInstallerWebsite() {
        return installerWebsite;
    }

    public void setInstallerWebsite(String installerWebsite) {
        this.installerWebsite = installerWebsite;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "last_update_pwd")
    public Date getLastUpdatePwd() {
        return lastUpdatePwd;
    }

    public void setLastUpdatePwd(Date lastUpdatePwd) {
        this.lastUpdatePwd = lastUpdatePwd;
    }

    @Basic
    @Column(name = "active")
    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstallersEntity that = (InstallersEntity) o;
        return userId == that.userId &&
                Objects.equals(name, that.name) &&
                Objects.equals(installerAfm, that.installerAfm) &&
                Objects.equals(billingAddressOnly, that.billingAddressOnly) &&
                Objects.equals(installerBillingAddressOnly, that.installerBillingAddressOnly) &&
                Objects.equals(installerCollectionPolicy, that.installerCollectionPolicy) &&
                Objects.equals(username, that.username) &&
                //Objects.equals(pwd, that.pwd) &&
                Objects.equals(email, that.email) &&
                Objects.equals(selfCollection, that.selfCollection) &&
                Objects.equals(emailInvoice, that.emailInvoice) &&
                Objects.equals(fax, that.fax) &&
                Objects.equals(installerInsuredAreaAddress, that.installerInsuredAreaAddress) &&
                Objects.equals(installerInsuredAreaCity, that.installerInsuredAreaCity) &&
                Objects.equals(installerInsuredAreaFloor, that.installerInsuredAreaFloor) &&
                Objects.equals(installerInsuredAreaPostCode, that.installerInsuredAreaPostCode) &&
                Objects.equals(installerLandlinePhone, that.installerLandlinePhone) &&
                Objects.equals(installerMobilePhone, that.installerMobilePhone) &&
                Objects.equals(installerProffesionalDescription, that.installerProffesionalDescription) &&
                Objects.equals(installerWebsite, that.installerWebsite) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(lastUpdatePwd, that.lastUpdatePwd) &&
                Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, installerAfm, billingAddressOnly, installerBillingAddressOnly, installerCollectionPolicy, username, email, selfCollection, emailInvoice, fax, installerInsuredAreaAddress, installerInsuredAreaCity, installerInsuredAreaFloor, installerInsuredAreaPostCode, installerLandlinePhone, installerMobilePhone, installerProffesionalDescription, installerWebsite, lastName, lastUpdatePwd, active);
    }

    @Basic
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "doy")
    public String getDoy() {
        return doy;
    }

    public void setDoy(String doy) {
        this.doy = doy;
    }
}
