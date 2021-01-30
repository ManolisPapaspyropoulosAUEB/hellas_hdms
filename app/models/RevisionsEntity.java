package models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "revisions", schema = "hdms_ibo_db_pr", catalog = "")
public class RevisionsEntity {
    private int id;
    private long customerId;
    private int userId;
    private String alarmUnitType;
    private String areaPhone;
    private String customerAuxiliaryPass;
    private Date customerConnectionDate;
    private String customerPass;
    private Date datePublished;
    private String directTransmissionPhones;
    private Byte draft;
    private String duressCode;
    private Byte enabled;
    private String format;
    private Integer frequency24HourTest;
    private String insuredAreaAdress;
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
    private Integer revisions;
    private Date revisionDate;
    private Date creationDate;
    private Date updateDate;
    private Byte visible;
    private String lastAction;
    private String afm;
    private String fax;
    private String companyName;
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
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
    @Column(name = "insured_area_adress")
    public String getInsuredAreaAdress() {
        return insuredAreaAdress;
    }

    public void setInsuredAreaAdress(String insuredAreaAdress) {
        this.insuredAreaAdress = insuredAreaAdress;
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
    @Column(name = "revisions")
    public Integer getRevisions() {
        return revisions;
    }

    public void setRevisions(Integer revisions) {
        this.revisions = revisions;
    }

    @Basic
    @Column(name = "revision_date")
    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
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
    @Column(name = "visible")
    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
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
    @Column(name = "afm")
    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RevisionsEntity that = (RevisionsEntity) o;
        return id == that.id &&
                customerId == that.customerId &&
                userId == that.userId &&
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
                Objects.equals(insuredAreaAdress, that.insuredAreaAdress) &&
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
                Objects.equals(revisions, that.revisions) &&
                Objects.equals(revisionDate, that.revisionDate) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(visible, that.visible) &&
                Objects.equals(lastAction, that.lastAction) &&
                Objects.equals(afm, that.afm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, userId, alarmUnitType, areaPhone, customerAuxiliaryPass, customerConnectionDate, customerPass, datePublished, directTransmissionPhones, draft, duressCode, enabled, format, frequency24HourTest, insuredAreaAdress, insuredAreaCity, insuredAreaDescription, insuredAreaFloor, insuredAreaPostCode, insuredAreaType, insuredAreaTypeOther, monthlyAlarmList, operationControllHours, otherRemarks, policeStation, subscriberName, weeklyTimeMonitoring, device, revisions, revisionDate, creationDate, updateDate, visible, lastAction, afm);
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
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
