package models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "installers_customers", schema = "hdms_ibo_db_pr", catalog = "")
public class InstallersCustomersEntity {
    private String rowId;
    private int userId;
    private String name;
    private String lastNameIns;
    private String emailIns;
    private String doy;
    private String companyName;
    private String installerAfm;
    private String billingAddressOnly;
    private String installerBillingAddressOnly;
    private String installerCollectionPolicy;
    private String username;
    private String selfCollection;
    private String emailInvoice;
    private String faxIns;
    private String installerInsuredAreaAddress;
    private String installerInsuredAreaCity;
    private String installerInsuredAreaFloor;
    private String installerInsuredAreaPostCode;
    private String installerLandlinePhone;
    private String installerMobilePhone;
    private String installerProffesionalDescription;
    private String installerWebsite;


    private Date lastUpdatePwd;
    private Byte active;
    private Integer id;
    private Long customerId;
    private Integer revisionId;
    private String alarmUnitType;
    private String areaPhone;
    private String customerAuxiliaryPass;
    private Timestamp customerConnectionDate;
    private String customerPass;
    private Timestamp datePublished;
    private String directTransmissionPhones;
    private Byte draft;
    private String duressCode;
    private Byte enabled;
    private String format;
    private Integer frequency24HourTest;
    private String insuredAreaAddress;
    private String insuredAreaCity;
    private String insuredAreaDescription;
    private String insuredAreaFloor;
    private String insuredAreaPostCode;
    private String insuredAreaType;
    private String insuredAreaTypeOther;
    private String monthlyAlarmList;
    private String operationControllHours;
    private String otherRemarks;
    private String policeStation;
    private String subscriberName;
    private String weeklyTimeMonitoring;
    private String device;
    private Date creationDate;
    private Date updateDate;
    private String lastAction;
    private Byte visible;
    private String emailCc;
    private String faxCc;
    private String service247Monitoring;
    private String serviceAccess;
    private String serviceCctv;
    private String serviceCommunication;
    private String serviceGlobalSim;
    private String serviceReport;
    private String serviceTest;
    private String sevriceMonitoring;
    private String afm;
    private String status;
    private Byte cancelation;
    private Integer revision;
    private Date creationCcDate;
    private Date submittedCcDate;
    private String role;

    @Id

    @Basic
    @Column(name = "row_id")
    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    @Basic
    @Column(name = "USER_ID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "last_name_ins")
    public String getLastNameIns() {
        return lastNameIns;
    }

    public void setLastNameIns(String lastNameIns) {
        this.lastNameIns = lastNameIns;
    }

    @Basic
    @Column(name = "email_ins")
    public String getEmailIns() {
        return emailIns;
    }

    public void setEmailIns(String emailIns) {
        this.emailIns = emailIns;
    }

    @Basic
    @Column(name = "doy")
    public String getDoy() {
        return doy;
    }

    public void setDoy(String doy) {
        this.doy = doy;
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
    @Column(name = "INSTALLER_AFM")
    public String getInstallerAfm() {
        return installerAfm;
    }

    public void setInstallerAfm(String installerAfm) {
        this.installerAfm = installerAfm;
    }

    @Basic
    @Column(name = "BILLING_ADDRESS_ONLY")
    public String getBillingAddressOnly() {
        return billingAddressOnly;
    }

    public void setBillingAddressOnly(String billingAddressOnly) {
        this.billingAddressOnly = billingAddressOnly;
    }

    @Basic
    @Column(name = "INSTALLER_BILLING_ADDRESS_ONLY")
    public String getInstallerBillingAddressOnly() {
        return installerBillingAddressOnly;
    }

    public void setInstallerBillingAddressOnly(String installerBillingAddressOnly) {
        this.installerBillingAddressOnly = installerBillingAddressOnly;
    }

    @Basic
    @Column(name = "INSTALLER_COLLECTION_POLICY")
    public String getInstallerCollectionPolicy() {
        return installerCollectionPolicy;
    }

    public void setInstallerCollectionPolicy(String installerCollectionPolicy) {
        this.installerCollectionPolicy = installerCollectionPolicy;
    }

    @Basic
    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "SELF_COLLECTION")
    public String getSelfCollection() {
        return selfCollection;
    }

    public void setSelfCollection(String selfCollection) {
        this.selfCollection = selfCollection;
    }

    @Basic
    @Column(name = "EMAIL_INVOICE")
    public String getEmailInvoice() {
        return emailInvoice;
    }

    public void setEmailInvoice(String emailInvoice) {
        this.emailInvoice = emailInvoice;
    }

    @Basic
    @Column(name = "fax_ins")
    public String getFaxIns() {
        return faxIns;
    }

    public void setFaxIns(String faxIns) {
        this.faxIns = faxIns;
    }

    @Basic
    @Column(name = "INSTALLER_INSURED_AREA_ADDRESS")
    public String getInstallerInsuredAreaAddress() {
        return installerInsuredAreaAddress;
    }

    public void setInstallerInsuredAreaAddress(String installerInsuredAreaAddress) {
        this.installerInsuredAreaAddress = installerInsuredAreaAddress;
    }

    @Basic
    @Column(name = "INSTALLER_INSURED_AREA_CITY")
    public String getInstallerInsuredAreaCity() {
        return installerInsuredAreaCity;
    }

    public void setInstallerInsuredAreaCity(String installerInsuredAreaCity) {
        this.installerInsuredAreaCity = installerInsuredAreaCity;
    }

    @Basic
    @Column(name = "INSTALLER_INSURED_AREA_FLOOR")
    public String getInstallerInsuredAreaFloor() {
        return installerInsuredAreaFloor;
    }

    public void setInstallerInsuredAreaFloor(String installerInsuredAreaFloor) {
        this.installerInsuredAreaFloor = installerInsuredAreaFloor;
    }

    @Basic
    @Column(name = "INSTALLER_INSURED_AREA_POST_CODE")
    public String getInstallerInsuredAreaPostCode() {
        return installerInsuredAreaPostCode;
    }

    public void setInstallerInsuredAreaPostCode(String installerInsuredAreaPostCode) {
        this.installerInsuredAreaPostCode = installerInsuredAreaPostCode;
    }

    @Basic
    @Column(name = "INSTALLER_LANDLINE_PHONE")
    public String getInstallerLandlinePhone() {
        return installerLandlinePhone;
    }

    public void setInstallerLandlinePhone(String installerLandlinePhone) {
        this.installerLandlinePhone = installerLandlinePhone;
    }

    @Basic
    @Column(name = "INSTALLER_MOBILE_PHONE")
    public String getInstallerMobilePhone() {
        return installerMobilePhone;
    }

    public void setInstallerMobilePhone(String installerMobilePhone) {
        this.installerMobilePhone = installerMobilePhone;
    }

    @Basic
    @Column(name = "INSTALLER_PROFFESIONAL_DESCRIPTION")
    public String getInstallerProffesionalDescription() {
        return installerProffesionalDescription;
    }

    public void setInstallerProffesionalDescription(String installerProffesionalDescription) {
        this.installerProffesionalDescription = installerProffesionalDescription;
    }

    @Basic
    @Column(name = "INSTALLER_WEBSITE")
    public String getInstallerWebsite() {
        return installerWebsite;
    }

    public void setInstallerWebsite(String installerWebsite) {
        this.installerWebsite = installerWebsite;
    }

    @Basic
    @Column(name = "LAST_UPDATE_PWD")
    public Date getLastUpdatePwd() {
        return lastUpdatePwd;
    }

    public void setLastUpdatePwd(Date lastUpdatePwd) {
        this.lastUpdatePwd = lastUpdatePwd;
    }

    @Basic
    @Column(name = "ACTIVE")
    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    @Basic
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CUSTOMER_ID")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "REVISION_ID")
    public Integer getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(Integer revisionId) {
        this.revisionId = revisionId;
    }

    @Basic
    @Column(name = "ALARM_UNIT_TYPE")
    public String getAlarmUnitType() {
        return alarmUnitType;
    }

    public void setAlarmUnitType(String alarmUnitType) {
        this.alarmUnitType = alarmUnitType;
    }

    @Basic
    @Column(name = "AREA_PHONE")
    public String getAreaPhone() {
        return areaPhone;
    }

    public void setAreaPhone(String areaPhone) {
        this.areaPhone = areaPhone;
    }

    @Basic
    @Column(name = "CUSTOMER_AUXILIARY_PASS")
    public String getCustomerAuxiliaryPass() {
        return customerAuxiliaryPass;
    }

    public void setCustomerAuxiliaryPass(String customerAuxiliaryPass) {
        this.customerAuxiliaryPass = customerAuxiliaryPass;
    }

    @Basic
    @Column(name = "CUSTOMER_CONNECTION_DATE")
    public Timestamp getCustomerConnectionDate() {
        return customerConnectionDate;
    }

    public void setCustomerConnectionDate(Timestamp customerConnectionDate) {
        this.customerConnectionDate = customerConnectionDate;
    }

    @Basic
    @Column(name = "CUSTOMER_PASS")
    public String getCustomerPass() {
        return customerPass;
    }

    public void setCustomerPass(String customerPass) {
        this.customerPass = customerPass;
    }

    @Basic
    @Column(name = "DATE_PUBLISHED")
    public Timestamp getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Timestamp datePublished) {
        this.datePublished = datePublished;
    }

    @Basic
    @Column(name = "DIRECT_TRANSMISSION_PHONES")
    public String getDirectTransmissionPhones() {
        return directTransmissionPhones;
    }

    public void setDirectTransmissionPhones(String directTransmissionPhones) {
        this.directTransmissionPhones = directTransmissionPhones;
    }

    @Basic
    @Column(name = "DRAFT")
    public Byte getDraft() {
        return draft;
    }

    public void setDraft(Byte draft) {
        this.draft = draft;
    }

    @Basic
    @Column(name = "DURESS_CODE")
    public String getDuressCode() {
        return duressCode;
    }

    public void setDuressCode(String duressCode) {
        this.duressCode = duressCode;
    }

    @Basic
    @Column(name = "ENABLED")
    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "FORMAT")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Basic
    @Column(name = "FREQUENCY_24_HOUR_TEST")
    public Integer getFrequency24HourTest() {
        return frequency24HourTest;
    }

    public void setFrequency24HourTest(Integer frequency24HourTest) {
        this.frequency24HourTest = frequency24HourTest;
    }

    @Basic
    @Column(name = "INSURED_AREA_ADDRESS")
    public String getInsuredAreaAddress() {
        return insuredAreaAddress;
    }

    public void setInsuredAreaAddress(String insuredAreaAddress) {
        this.insuredAreaAddress = insuredAreaAddress;
    }

    @Basic
    @Column(name = "INSURED_AREA_CITY")
    public String getInsuredAreaCity() {
        return insuredAreaCity;
    }

    public void setInsuredAreaCity(String insuredAreaCity) {
        this.insuredAreaCity = insuredAreaCity;
    }

    @Basic
    @Column(name = "INSURED_AREA_DESCRIPTION")
    public String getInsuredAreaDescription() {
        return insuredAreaDescription;
    }

    public void setInsuredAreaDescription(String insuredAreaDescription) {
        this.insuredAreaDescription = insuredAreaDescription;
    }

    @Basic
    @Column(name = "INSURED_AREA_FLOOR")
    public String getInsuredAreaFloor() {
        return insuredAreaFloor;
    }

    public void setInsuredAreaFloor(String insuredAreaFloor) {
        this.insuredAreaFloor = insuredAreaFloor;
    }

    @Basic
    @Column(name = "INSURED_AREA_POST_CODE")
    public String getInsuredAreaPostCode() {
        return insuredAreaPostCode;
    }

    public void setInsuredAreaPostCode(String insuredAreaPostCode) {
        this.insuredAreaPostCode = insuredAreaPostCode;
    }

    @Basic
    @Column(name = "INSURED_AREA_TYPE")
    public String getInsuredAreaType() {
        return insuredAreaType;
    }

    public void setInsuredAreaType(String insuredAreaType) {
        this.insuredAreaType = insuredAreaType;
    }

    @Basic
    @Column(name = "INSURED_AREA_TYPE_OTHER")
    public String getInsuredAreaTypeOther() {
        return insuredAreaTypeOther;
    }

    public void setInsuredAreaTypeOther(String insuredAreaTypeOther) {
        this.insuredAreaTypeOther = insuredAreaTypeOther;
    }

    @Basic
    @Column(name = "MONTHLY_ALARM_LIST")
    public String getMonthlyAlarmList() {
        return monthlyAlarmList;
    }

    public void setMonthlyAlarmList(String monthlyAlarmList) {
        this.monthlyAlarmList = monthlyAlarmList;
    }

    @Basic
    @Column(name = "OPERATION_CONTROLL_HOURS")
    public String getOperationControllHours() {
        return operationControllHours;
    }

    public void setOperationControllHours(String operationControllHours) {
        this.operationControllHours = operationControllHours;
    }

    @Basic
    @Column(name = "OTHER_REMARKS")
    public String getOtherRemarks() {
        return otherRemarks;
    }

    public void setOtherRemarks(String otherRemarks) {
        this.otherRemarks = otherRemarks;
    }

    @Basic
    @Column(name = "POLICE_STATION")
    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    @Basic
    @Column(name = "SUBSCRIBER_NAME")
    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    @Basic
    @Column(name = "WEEKLY_TIME_MONITORING")
    public String getWeeklyTimeMonitoring() {
        return weeklyTimeMonitoring;
    }

    public void setWeeklyTimeMonitoring(String weeklyTimeMonitoring) {
        this.weeklyTimeMonitoring = weeklyTimeMonitoring;
    }

    @Basic
    @Column(name = "DEVICE")
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Basic
    @Column(name = "creation_date")
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "update_date")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "LAST_ACTION")
    public String getLastAction() {
        return lastAction;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    @Basic
    @Column(name = "VISIBLE")
    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "email_cc")
    public String getEmailCc() {
        return emailCc;
    }

    public void setEmailCc(String emailCc) {
        this.emailCc = emailCc;
    }

    @Basic
    @Column(name = "fax_cc")
    public String getFaxCc() {
        return faxCc;
    }

    public void setFaxCc(String faxCc) {
        this.faxCc = faxCc;
    }

    @Basic
    @Column(name = "service247_monitoring")
    public String getService247Monitoring() {
        return service247Monitoring;
    }

    public void setService247Monitoring(String service247Monitoring) {
        this.service247Monitoring = service247Monitoring;
    }

    @Basic
    @Column(name = "service_access")
    public String getServiceAccess() {
        return serviceAccess;
    }

    public void setServiceAccess(String serviceAccess) {
        this.serviceAccess = serviceAccess;
    }

    @Basic
    @Column(name = "service_cctv")
    public String getServiceCctv() {
        return serviceCctv;
    }

    public void setServiceCctv(String serviceCctv) {
        this.serviceCctv = serviceCctv;
    }

    @Basic
    @Column(name = "service_communication")
    public String getServiceCommunication() {
        return serviceCommunication;
    }

    public void setServiceCommunication(String serviceCommunication) {
        this.serviceCommunication = serviceCommunication;
    }

    @Basic
    @Column(name = "service_global_sim")
    public String getServiceGlobalSim() {
        return serviceGlobalSim;
    }

    public void setServiceGlobalSim(String serviceGlobalSim) {
        this.serviceGlobalSim = serviceGlobalSim;
    }

    @Basic
    @Column(name = "service_report")
    public String getServiceReport() {
        return serviceReport;
    }

    public void setServiceReport(String serviceReport) {
        this.serviceReport = serviceReport;
    }

    @Basic
    @Column(name = "service_test")
    public String getServiceTest() {
        return serviceTest;
    }

    public void setServiceTest(String serviceTest) {
        this.serviceTest = serviceTest;
    }

    @Basic
    @Column(name = "sevrice_monitoring")
    public String getSevriceMonitoring() {
        return sevriceMonitoring;
    }

    public void setSevriceMonitoring(String sevriceMonitoring) {
        this.sevriceMonitoring = sevriceMonitoring;
    }

    @Basic
    @Column(name = "AFM")
    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "CANCELATION")
    public Byte getCancelation() {
        return cancelation;
    }

    public void setCancelation(Byte cancelation) {
        this.cancelation = cancelation;
    }

    @Basic
    @Column(name = "REVISION")
    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    @Basic
    @Column(name = "CREATION_CC_DATE")
    public Date getCreationCcDate() {
        return creationCcDate;
    }

    public void setCreationCcDate(Date creationCcDate) {
        this.creationCcDate = creationCcDate;
    }

    @Basic
    @Column(name = "SUBMITTED_CC_DATE")
    public Date getSubmittedCcDate() {
        return submittedCcDate;
    }

    public void setSubmittedCcDate(Date submittedCcDate) {
        this.submittedCcDate = submittedCcDate;
    }

    @Basic
    @Column(name = "ROLE")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstallersCustomersEntity that = (InstallersCustomersEntity) o;
        return userId == that.userId &&
                Objects.equals(rowId, that.rowId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(lastNameIns, that.lastNameIns) &&
                Objects.equals(emailIns, that.emailIns) &&
                Objects.equals(doy, that.doy) &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(installerAfm, that.installerAfm) &&
                Objects.equals(billingAddressOnly, that.billingAddressOnly) &&
                Objects.equals(installerBillingAddressOnly, that.installerBillingAddressOnly) &&
                Objects.equals(installerCollectionPolicy, that.installerCollectionPolicy) &&
                Objects.equals(username, that.username) &&
                Objects.equals(selfCollection, that.selfCollection) &&
                Objects.equals(emailInvoice, that.emailInvoice) &&
                Objects.equals(faxIns, that.faxIns) &&
                Objects.equals(installerInsuredAreaAddress, that.installerInsuredAreaAddress) &&
                Objects.equals(installerInsuredAreaCity, that.installerInsuredAreaCity) &&
                Objects.equals(installerInsuredAreaFloor, that.installerInsuredAreaFloor) &&
                Objects.equals(installerInsuredAreaPostCode, that.installerInsuredAreaPostCode) &&
                Objects.equals(installerLandlinePhone, that.installerLandlinePhone) &&
                Objects.equals(installerMobilePhone, that.installerMobilePhone) &&
                Objects.equals(installerProffesionalDescription, that.installerProffesionalDescription) &&
                Objects.equals(installerWebsite, that.installerWebsite) &&
                Objects.equals(lastUpdatePwd, that.lastUpdatePwd) &&
                Objects.equals(active, that.active) &&
                Objects.equals(id, that.id) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(revisionId, that.revisionId) &&
                Objects.equals(alarmUnitType, that.alarmUnitType) &&
                Objects.equals(areaPhone, that.areaPhone) &&
                Objects.equals(customerAuxiliaryPass, that.customerAuxiliaryPass) &&
                Objects.equals(customerConnectionDate, that.customerConnectionDate) &&
                Objects.equals(customerPass, that.customerPass) &&
                Objects.equals(datePublished, that.datePublished) &&
                Objects.equals(directTransmissionPhones, that.directTransmissionPhones) &&
                Objects.equals(draft, that.draft) &&
                Objects.equals(duressCode, that.duressCode) &&
                Objects.equals(enabled, that.enabled) &&
                Objects.equals(format, that.format) &&
                Objects.equals(frequency24HourTest, that.frequency24HourTest) &&
                Objects.equals(insuredAreaAddress, that.insuredAreaAddress) &&
                Objects.equals(insuredAreaCity, that.insuredAreaCity) &&
                Objects.equals(insuredAreaDescription, that.insuredAreaDescription) &&
                Objects.equals(insuredAreaFloor, that.insuredAreaFloor) &&
                Objects.equals(insuredAreaPostCode, that.insuredAreaPostCode) &&
                Objects.equals(insuredAreaType, that.insuredAreaType) &&
                Objects.equals(insuredAreaTypeOther, that.insuredAreaTypeOther) &&
                Objects.equals(monthlyAlarmList, that.monthlyAlarmList) &&
                Objects.equals(operationControllHours, that.operationControllHours) &&
                Objects.equals(otherRemarks, that.otherRemarks) &&
                Objects.equals(policeStation, that.policeStation) &&
                Objects.equals(subscriberName, that.subscriberName) &&
                Objects.equals(weeklyTimeMonitoring, that.weeklyTimeMonitoring) &&
                Objects.equals(device, that.device) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(lastAction, that.lastAction) &&
                Objects.equals(visible, that.visible) &&
                Objects.equals(emailCc, that.emailCc) &&
                Objects.equals(faxCc, that.faxCc) &&
                Objects.equals(service247Monitoring, that.service247Monitoring) &&
                Objects.equals(serviceAccess, that.serviceAccess) &&
                Objects.equals(serviceCctv, that.serviceCctv) &&
                Objects.equals(serviceCommunication, that.serviceCommunication) &&
                Objects.equals(serviceGlobalSim, that.serviceGlobalSim) &&
                Objects.equals(serviceReport, that.serviceReport) &&
                Objects.equals(serviceTest, that.serviceTest) &&
                Objects.equals(sevriceMonitoring, that.sevriceMonitoring) &&
                Objects.equals(afm, that.afm) &&
                Objects.equals(status, that.status) &&
                Objects.equals(cancelation, that.cancelation) &&
                Objects.equals(revision, that.revision) &&
                Objects.equals(creationCcDate, that.creationCcDate) &&
                Objects.equals(submittedCcDate, that.submittedCcDate) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowId, userId, name, lastNameIns, emailIns, doy, companyName, installerAfm, billingAddressOnly, installerBillingAddressOnly, installerCollectionPolicy, username, selfCollection, emailInvoice, faxIns, installerInsuredAreaAddress, installerInsuredAreaCity, installerInsuredAreaFloor, installerInsuredAreaPostCode, installerLandlinePhone, installerMobilePhone, installerProffesionalDescription, installerWebsite, lastUpdatePwd, active, id, customerId, revisionId, alarmUnitType, areaPhone, customerAuxiliaryPass, customerConnectionDate, customerPass, datePublished, directTransmissionPhones, draft, duressCode, enabled, format, frequency24HourTest, insuredAreaAddress, insuredAreaCity, insuredAreaDescription, insuredAreaFloor, insuredAreaPostCode, insuredAreaType, insuredAreaTypeOther, monthlyAlarmList, operationControllHours, otherRemarks, policeStation, subscriberName, weeklyTimeMonitoring, device, creationDate, updateDate, lastAction, visible, emailCc, faxCc, service247Monitoring, serviceAccess, serviceCctv, serviceCommunication, serviceGlobalSim, serviceReport, serviceTest, sevriceMonitoring, afm, status, cancelation, revision, creationCcDate, submittedCcDate, role);
    }
}
