package models;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "customers_cc", schema = "hdms_ibo_db_pr", catalog = "")
public class CustomersCcEntity {
    private int id;//
    private long customerId;//
    private Integer userId;//
    private String alarmUnitType;//
    private String areaPhone;//
    private String customerAuxiliaryPass;//
    private Date customerConnectionDate;//
    private String customerPass;
    private Date datePublished;
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
    private String afm;
    private String status;
    private int revisionId;
    private Byte cancelation;
    private Integer revision;
    private Date creationCcDate;
    private Date submittedCcDate;
    private String role;


    private String companyName;
    private String fax;
    private String email;
    private String service247Monitoring;
    private String serviceAccess;
    private String serviceCctv;
    private String serviceCommunication;
    private String serviceGlobalSim;
    private String serviceReport;
    private String serviceTest;
    private String sevriceMonitoring;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "customer_id")
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "alarm_unit_type")
    public String getAlarmUnitType() {
        return alarmUnitType;
    }

    public void setAlarmUnitType(String alarmUnitType) {
        this.alarmUnitType = alarmUnitType;
    }

    @Basic
    @Column(name = "area_phone")
    public String getAreaPhone() {
        return areaPhone;
    }

    public void setAreaPhone(String areaPhone) {
        this.areaPhone = areaPhone;
    }

    @Basic
    @Column(name = "customer_auxiliary_pass")
    public String getCustomerAuxiliaryPass() {
        return customerAuxiliaryPass;
    }

    public void setCustomerAuxiliaryPass(String customerAuxiliaryPass) {
        this.customerAuxiliaryPass = customerAuxiliaryPass;
    }

    @Basic
    @Column(name = "customer_connection_date")
    public Date getCustomerConnectionDate() {
        return customerConnectionDate;
    }

    public void setCustomerConnectionDate(Date customerConnectionDate) {
        this.customerConnectionDate = customerConnectionDate;
    }

    @Basic
    @Column(name = "customer_pass")
    public String getCustomerPass() {
        return customerPass;
    }

    public void setCustomerPass(String customerPass) {
        this.customerPass = customerPass;
    }

    @Basic
    @Column(name = "date_published")
    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    @Basic
    @Column(name = "direct_transmission_phones")
    public String getDirectTransmissionPhones() {
        return directTransmissionPhones;
    }

    public void setDirectTransmissionPhones(String directTransmissionPhones) {
        this.directTransmissionPhones = directTransmissionPhones;
    }

    @Basic
    @Column(name = "draft")
    public Byte getDraft() {
        return draft;
    }

    public void setDraft(Byte draft) {
        this.draft = draft;
    }

    @Basic
    @Column(name = "duress_code")
    public String getDuressCode() {
        return duressCode;
    }

    public void setDuressCode(String duressCode) {
        this.duressCode = duressCode;
    }

    @Basic
    @Column(name = "enabled")
    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "format")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Basic
    @Column(name = "frequency_24_hour_test")
    public Integer getFrequency24HourTest() {
        return frequency24HourTest;
    }

    public void setFrequency24HourTest(Integer frequency24HourTest) {
        this.frequency24HourTest = frequency24HourTest;
    }

    @Basic
    @Column(name = "insured_area_address")
    public String getInsuredAreaAddress() {
        return insuredAreaAddress;
    }

    public void setInsuredAreaAddress(String insuredAreaAddress) {
        this.insuredAreaAddress = insuredAreaAddress;
    }

    @Basic
    @Column(name = "insured_area_city")
    public String getInsuredAreaCity() {
        return insuredAreaCity;
    }

    public void setInsuredAreaCity(String insuredAreaCity) {
        this.insuredAreaCity = insuredAreaCity;
    }

    @Basic
    @Column(name = "insured_area_description")
    public String getInsuredAreaDescription() {
        return insuredAreaDescription;
    }

    public void setInsuredAreaDescription(String insuredAreaDescription) {
        this.insuredAreaDescription = insuredAreaDescription;
    }

    @Basic
    @Column(name = "insured_area_floor")
    public String getInsuredAreaFloor() {
        return insuredAreaFloor;
    }

    public void setInsuredAreaFloor(String insuredAreaFloor) {
        this.insuredAreaFloor = insuredAreaFloor;
    }

    @Basic
    @Column(name = "insured_area_post_code")
    public String getInsuredAreaPostCode() {
        return insuredAreaPostCode;
    }

    public void setInsuredAreaPostCode(String insuredAreaPostCode) {
        this.insuredAreaPostCode = insuredAreaPostCode;
    }

    @Basic
    @Column(name = "insured_area_type")
    public String getInsuredAreaType() {
        return insuredAreaType;
    }

    public void setInsuredAreaType(String insuredAreaType) {
        this.insuredAreaType = insuredAreaType;
    }

    @Basic
    @Column(name = "insured_area_type_other")
    public String getInsuredAreaTypeOther() {
        return insuredAreaTypeOther;
    }

    public void setInsuredAreaTypeOther(String insuredAreaTypeOther) {
        this.insuredAreaTypeOther = insuredAreaTypeOther;
    }

    @Basic
    @Column(name = "monthly_alarm_list")
    public String getMonthlyAlarmList() {
        return monthlyAlarmList;
    }

    public void setMonthlyAlarmList(String monthlyAlarmList) {
        this.monthlyAlarmList = monthlyAlarmList;
    }

    @Basic
    @Column(name = "operation_controll_hours")
    public String getOperationControllHours() {
        return operationControllHours;
    }

    public void setOperationControllHours(String operationControllHours) {
        this.operationControllHours = operationControllHours;
    }

    @Basic
    @Column(name = "other_remarks")
    public String getOtherRemarks() {
        return otherRemarks;
    }

    public void setOtherRemarks(String otherRemarks) {
        this.otherRemarks = otherRemarks;
    }

    @Basic
    @Column(name = "police_station")
    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    @Basic
    @Column(name = "subscriber_name")
    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    @Basic
    @Column(name = "weekly_time_monitoring")
    public String getWeeklyTimeMonitoring() {
        return weeklyTimeMonitoring;
    }

    public void setWeeklyTimeMonitoring(String weeklyTimeMonitoring) {
        this.weeklyTimeMonitoring = weeklyTimeMonitoring;
    }

    @Basic
    @Column(name = "device")
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

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "update_date")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "last_action")
    public String getLastAction() {
        return lastAction;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    @Basic
    @Column(name = "visible")
    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "afm")
    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomersCcEntity that = (CustomersCcEntity) o;
        return id == that.id &&
                customerId == that.customerId &&
                Objects.equals(userId, that.userId) &&
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
                Objects.equals(afm, that.afm) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, userId, alarmUnitType, areaPhone, customerAuxiliaryPass, customerConnectionDate, customerPass, datePublished, directTransmissionPhones, draft, duressCode, enabled, format, frequency24HourTest, insuredAreaAddress, insuredAreaCity, insuredAreaDescription, insuredAreaFloor, insuredAreaPostCode, insuredAreaType, insuredAreaTypeOther, monthlyAlarmList, operationControllHours, otherRemarks, policeStation, subscriberName, weeklyTimeMonitoring, device, creationDate, updateDate, lastAction, visible, afm, status);
    }

    @Basic
    @Column(name = "revision_id")
    public int getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(int revisionId) {
        this.revisionId = revisionId;
    }

    @Basic
    @Column(name = "cancelation")
    public Byte getCancelation() {
        return cancelation;
    }

    public void setCancelation(Byte cancelation) {
        this.cancelation = cancelation;
    }

    @Basic
    @Column(name = "revision")
    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    @Basic
    @Column(name = "creation_cc_date")
    public Date getCreationCcDate() {
        return creationCcDate;
    }

    public void setCreationCcDate(Date creationCcDate) {
        this.creationCcDate = creationCcDate;
    }

    @Basic
    @Column(name = "submitted_cc_date")
    public Date getSubmittedCcDate() {
        return submittedCcDate;
    }

    public void setSubmittedCcDate(Date submittedCcDate) {
        this.submittedCcDate = submittedCcDate;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
    @Column(name = "fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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
}
