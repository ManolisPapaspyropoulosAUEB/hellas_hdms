package controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.Query;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static play.mvc.Http.Context.Implicit.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;
/**
 * created by mpapaspyropoulos
 */
public class CcController {

    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    public Result getAllDataForCc() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                if (json == null ){
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής.");
                    return ok(result);
                } else {
                    Query q = JPA.em().createQuery("SELECT c FROM CustomersCcEntity c ");
                    List<CustomersCcEntity> custList = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    List<HashMap<String, Object>> cust = new ArrayList<HashMap<String, Object>>();
                    int k=-1;
                    //--------------------------MASTER ------------- (customer details)
                    for(CustomersCcEntity j: custList) {
                        k++;
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("id",j.getId());
                        pt.put("customerId",j.getCustomerId());
                        pt.put("userId",j.getUserId());
                        pt.put("alarmUnitType",j.getAlarmUnitType());
                        if(j.getCancelation()!=null){
                            if(j.getCancelation()==0){
                                pt.put("cancelation",false);
                            }else{
                                pt.put("cancelation",true);
                            }
                        }
                        pt.put("areaPhone",j.getAreaPhone());
                        pt.put("customerAuxiliaryPass",j.getCustomerAuxiliaryPass());
                        pt.put("customerConnectionDate",j.getCustomerConnectionDate());
                        pt.put("customerPass",j.getCustomerPass());
                        pt.put("datePublished",j.getDatePublished());
                        pt.put("directTransmissionPhones",j.getDirectTransmissionPhones());
                        pt.put("draft",j.getDraft());
                        pt.put("duressCode",j.getDuressCode());
                        pt.put("submittedCcDate",j.getSubmittedCcDate());
                        pt.put("creationCcDate",j.getCreationCcDate());
                        pt.put("enabled",j.getEnabled());
                        pt.put("format",j.getFormat());
                        pt.put("frequency24HourTest",j.getFrequency24HourTest());
                        pt.put("insuredAreaAddress",j.getInsuredAreaAddress());
                        pt.put("insuredAreaCity",j.getInsuredAreaCity());
                        pt.put("insuredAreaDescription",j.getInsuredAreaDescription());
                        pt.put("insuredAreaFloor",j.getInsuredAreaFloor());
                        pt.put("insuredAreaPostCode",j.getInsuredAreaPostCode());
                        pt.put("insuredAreaType",j.getInsuredAreaType());
                        pt.put("insuredAreaTypeOther",j.getInsuredAreaTypeOther());
                        pt.put("monthlyAlarmList",j.getMonthlyAlarmList());
                        pt.put("operationControllHours",j.getOperationControllHours());
                        pt.put("otherRemarks",j.getOtherRemarks());
                        pt.put("policeStation",j.getPoliceStation());
                        pt.put("subscriberName",j.getSubscriberName());
                        pt.put("weeklyTimeMonitoring",j.getWeeklyTimeMonitoring());
                        pt.put("lastAction",j.getLastAction());

                        pt.put("device",j.getDevice());
                        pt.put("system",j.getDevice());
                        pt.put("lastUpdateDate",j.getUpdateDate());
                        pt.put("visible",j.getVisible());
                        pt.put("status",j.getStatus());
                        pt.put("revisionId",j.getRevisionId());
                        pt.put("afm",j.getAfm());
                        List<PhoneNoticesCcEntity> phoneNoticiesList = JPA.em()
                                .createQuery("SELECT d FROM PhoneNoticesCcEntity d WHERE d.customerId=" + j.getCustomerId() + " ORDER BY d.id")
                                .getResultList();
                        HashMap<String, Object> p = new HashMap<String, Object>();
                        List<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
                        for(int l=0;l<phoneNoticiesList.size();l++){
                            p.put("id",phoneNoticiesList.get(l).getId());
                            p.put("phoneId",phoneNoticiesList.get(l).getPhoneNoticesId());
                            if(phoneNoticiesList.get(l).getEditable().equals(0)){
                                p.put("editable",false);
                            }else{
                                p.put("editable",true);
                            }
                            p.put("phone",phoneNoticiesList.get(l).getPhone());
                            p.put("name",phoneNoticiesList.get(l).getName());
                            p.put("username",phoneNoticiesList.get(l).getUsername());
                            p.put("customerId",phoneNoticiesList.get(l).getCustomerId());
                            if (phoneNoticiesList.get(l).getVisible().equals(0)) {
                                p.put("visible",false);
                            } else {
                                p.put("visible",true);
                            }
                            pList.add(p);
                        }
                        List<ZonesCcEntity> zonesList = JPA.em()
                                .createQuery("SELECT z FROM ZonesCcEntity z WHERE z.customerId=" + j.getCustomerId() + " ORDER BY z.id")
                                .getResultList();
                        HashMap<String, Object> z = new HashMap<String, Object>();
                        List<HashMap<String, Object>> zList = new ArrayList<HashMap<String, Object>>();
                        for(int l=0;l<zonesList.size();l++){

                            if(zonesList.get(l).getEditable()!=null) {
                                if(zonesList.get(l).getEditable()==0){
                                    z.put("editable",false);
                                }else{
                                    z.put("editable",true);
                                }
                            }
                            if(zonesList.get(l).getVisible()!=null){
                                if(zonesList.get(l).getVisible()==0){
                                    z.put("visible",false);
                                }else{
                                    z.put("visible",true);
                                }
                            }
                            z.put("id",zonesList.get(l).getId());

                            z.put("name",zonesList.get(l).getName());
                            z.put("customerId",zonesList.get(l).getCustomerId());
                            z.put("zoneId",zonesList.get(l).getZoneId());
                            z.put("idByUser",zonesList.get(l).getIdByUser());
                            zList.add(z);
                        }
                        List<AlarmUsersCcEntity> AlarmUsersList = JPA.em()
                                .createQuery("SELECT a FROM AlarmUsersCcEntity a WHERE a.customerId=" + j.getCustomerId() + " ORDER BY a.id")
                                .getResultList();
                        HashMap<String, Object> a = new HashMap<String, Object>();
                        List<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();
                        for(int l=0;l<AlarmUsersList.size();l++){
                            a.put("id",AlarmUsersList.get(l).getId());
                            a.put("alarmUserId",AlarmUsersList.get(l).getAlarmUserId());

                            a.put("customerId",AlarmUsersList.get(l).getCustomerId());
                            a.put("name",AlarmUsersList.get(l).getName());
                            a.put("username",AlarmUsersList.get(l).getUsername());
                            if(AlarmUsersList.get(l).getEditable()==0){  //TODO SUNEXEIA EDW:::::::::::::::::::::::::
                                a.put("editable",false);
                            }else{
                                a.put("editable",true);
                            }
                            if(AlarmUsersList.get(l).getVisible()==0){
                                a.put("visible",false);
                            }else{
                                a.put("visible",true);
                            }
                            a.put("name",AlarmUsersList.get(l).getName());
                            a.put("customerId",AlarmUsersList.get(l).getCustomerId());
                            aList.add(a);
                        }

                        if(j.getVisible()!=null){
                            if(j.getVisible()==0){
                                pt.put("visible",false);
                            }else{
                                pt.put("visible",true);
                            }
                        }
                        if(j.getEnabled()!=null){
                            if(j.getEnabled()==0){
                                pt.put("enabled",false);
                            }else{
                                pt.put("enabled",true);
                            }
                        }
                        if(j.getDraft()!=null){
                            if(j.getDraft()==0){
                                pt.put("draft",false);
                            }else{
                                pt.put("draft",true);
                            }
                        }

                        pt.put("phoneNotices",pList);
                        pt.put("zones",zList);
                        pt.put("alarmUsers",aList);

                        cust.add(pt);
                    }
                    returnList.put("status","ok");
                    returnList.put("customers",cust);
                    returnList.put("total", total.intValue());
                    result.put("status", "ok");
                    result.put("message", "επιτυχως");
                    DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //TODO:DATE :AYTH H METHODOS EYTHINETE GIA TO PWS THA EPISTREPSOUN OI HMEROMHNIES
                    ow.setDateFormat(myDateFormat);
                    try {
                        jsonResult = ow.writeValueAsString(returnList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        result.put("status", "error");
                        result.put("message", "Πρόβλημα κατά την ανάγνωση των στοιχείων");
                        return ok(result);
                    }
                    return ok(jsonResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την διαγραφή .");
            return ok(result);
        }

    }










    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    public Result getAllDataForACc() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                if (json == null ){
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής.");
                    return ok(result);
                } else {
                    Query q = JPA.em().createQuery("SELECT c FROM CustomersCcEntity c where c.status="+"'"+"SUBMITTED"+"'" );
                    List<CustomersCcEntity> custList = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    List<HashMap<String, Object>> cust = new ArrayList<HashMap<String, Object>>();
                    int k=-1;
                    //--------------------------MASTER ------------- (customer details)
                    for(CustomersCcEntity j: custList) {
                        k++;
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("id",j.getId());
                        pt.put("customerId",j.getCustomerId());
                        pt.put("userId",j.getUserId());
                        pt.put("alarmUnitType",j.getAlarmUnitType());
                        if(j.getCancelation()!=null){
                            if(j.getCancelation()==0){
                                pt.put("cancelation",false);
                            }else{
                                pt.put("cancelation",true);
                            }
                        }
                        pt.put("areaPhone",j.getAreaPhone());
                        pt.put("customerAuxiliaryPass",j.getCustomerAuxiliaryPass());
                        pt.put("customerConnectionDate",j.getCustomerConnectionDate());
                        pt.put("customerPass",j.getCustomerPass());
                        pt.put("datePublished",j.getDatePublished());
                        pt.put("directTransmissionPhones",j.getDirectTransmissionPhones());
                        pt.put("draft",j.getDraft());
                        pt.put("duressCode",j.getDuressCode());
                        pt.put("enabled",j.getEnabled());
                        pt.put("format",j.getFormat());
                        pt.put("frequency24HourTest",j.getFrequency24HourTest());
                        pt.put("insuredAreaAddress",j.getInsuredAreaAddress());
                        pt.put("insuredAreaCity",j.getInsuredAreaCity());
                        pt.put("insuredAreaDescription",j.getInsuredAreaDescription());
                        pt.put("insuredAreaFloor",j.getInsuredAreaFloor());
                        pt.put("insuredAreaPostCode",j.getInsuredAreaPostCode());
                        pt.put("insuredAreaType",j.getInsuredAreaType());
                        pt.put("insuredAreaTypeOther",j.getInsuredAreaTypeOther());
                        pt.put("monthlyAlarmList",j.getMonthlyAlarmList());
                        pt.put("operationControllHours",j.getOperationControllHours());
                        pt.put("otherRemarks",j.getOtherRemarks());
                        pt.put("policeStation",j.getPoliceStation());
                        pt.put("subscriberName",j.getSubscriberName());
                        pt.put("weeklyTimeMonitoring",j.getWeeklyTimeMonitoring());
                        pt.put("lastAction",j.getLastAction());
                        pt.put("device",j.getDevice());
                        pt.put("system",j.getDevice());
                        pt.put("updateDate",j.getUpdateDate());
                        pt.put("visible",j.getVisible());
                        pt.put("status",j.getStatus());
                        pt.put("revisionId",j.getRevisionId());
                        pt.put("afm",j.getAfm());
                        List<PhoneNoticesCcEntity> phoneNoticiesList = JPA.em()
                                .createQuery("SELECT d FROM PhoneNoticesCcEntity d WHERE d.customerId=" + j.getCustomerId() + " ORDER BY d.id")
                                .getResultList();
                        HashMap<String, Object> p = new HashMap<String, Object>();
                        List<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
                        for(int l=0;l<phoneNoticiesList.size();l++){
                            p.put("id",phoneNoticiesList.get(l).getId());
                            p.put("phoneId",phoneNoticiesList.get(l).getPhoneNoticesId());
                            if(phoneNoticiesList.get(l).getEditable().equals(0)){
                                p.put("editable",false);
                            }else{
                                p.put("editable",true);
                            }
                            p.put("phone",phoneNoticiesList.get(l).getPhone());
                            p.put("name",phoneNoticiesList.get(l).getName());
                            p.put("username",phoneNoticiesList.get(l).getUsername());
                            p.put("customerId",phoneNoticiesList.get(l).getCustomerId());
                            if (phoneNoticiesList.get(l).getVisible().equals(0)) {
                                p.put("visible",false);
                            } else {
                                p.put("visible",true);
                            }
                            pList.add(p);
                        }
                        List<ZonesCcEntity> zonesList = JPA.em()
                                .createQuery("SELECT z FROM ZonesCcEntity z WHERE z.customerId=" + j.getCustomerId() + " ORDER BY z.id")
                                .getResultList();
                        HashMap<String, Object> z = new HashMap<String, Object>();
                        List<HashMap<String, Object>> zList = new ArrayList<HashMap<String, Object>>();
                        for(int l=0;l<zonesList.size();l++){

                            if(zonesList.get(l).getEditable()!=null) {
                                if(zonesList.get(l).getEditable()==0){
                                    z.put("editable",false);
                                }else{
                                    z.put("editable",true);
                                }
                            }
                            if(zonesList.get(l).getVisible()!=null){
                                if(zonesList.get(l).getVisible()==0){
                                    z.put("visible",false);
                                }else{
                                    z.put("visible",true);
                                }
                            }
                            z.put("id",zonesList.get(l).getId());

                            z.put("name",zonesList.get(l).getName());
                            z.put("customerId",zonesList.get(l).getCustomerId());
                            z.put("zoneId",zonesList.get(l).getZoneId());
                            z.put("idByUser",zonesList.get(l).getIdByUser());
                            zList.add(z);
                        }
                        List<AlarmUsersCcEntity> AlarmUsersList = JPA.em()
                                .createQuery("SELECT a FROM AlarmUsersCcEntity a WHERE a.customerId=" + j.getCustomerId() + " ORDER BY a.id")
                                .getResultList();
                        HashMap<String, Object> a = new HashMap<String, Object>();
                        List<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();
                        for(int l=0;l<AlarmUsersList.size();l++){
                            a.put("id",AlarmUsersList.get(l).getId());
                            a.put("alarmUserId",AlarmUsersList.get(l).getAlarmUserId());

                            a.put("customerId",AlarmUsersList.get(l).getCustomerId());
                            a.put("name",AlarmUsersList.get(l).getName());
                            a.put("username",AlarmUsersList.get(l).getUsername());
                            if(AlarmUsersList.get(l).getEditable()==0){  //TODO SUNEXEIA EDW:::::::::::::::::::::::::
                                a.put("editable",false);
                            }else{
                                a.put("editable",true);
                            }
                            if(AlarmUsersList.get(l).getVisible()==0){
                                a.put("visible",false);
                            }else{
                                a.put("visible",true);
                            }
                            a.put("name",AlarmUsersList.get(l).getName());
                            a.put("customerId",AlarmUsersList.get(l).getCustomerId());
                            aList.add(a);
                        }
                        if(j.getVisible()!=null){
                            if(j.getVisible()==0){
                                pt.put("visible",false);
                            }else{
                                pt.put("visible",true);
                            }
                        }
                        if(j.getEnabled()!=null){
                            if(j.getEnabled()==0){
                                pt.put("enabled",false);
                            }else{
                                pt.put("enabled",true);
                            }
                        }
                        if(j.getDraft()!=null){
                            if(j.getDraft()==0){
                                pt.put("draft",false);
                            }else{
                                pt.put("draft",true);
                            }
                        }
                        pt.put("phoneNotices",pList);
                        pt.put("zones",zList);
                        pt.put("alarmUsers",aList);
                        cust.add(pt);
                    }
                    returnList.put("status","ok");
                    returnList.put("customers",cust);
                    returnList.put("total", total.intValue());
                    result.put("status", "ok");
                    result.put("message", "επιτυχως");
                    DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //TODO:DATE :AYTH H METHODOS EYTHINETE GIA TO PWS THA EPISTREPSOUN OI HMEROMHNIES
                    ow.setDateFormat(myDateFormat);
                    try {
                        jsonResult = ow.writeValueAsString(returnList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        result.put("status", "error");
                        result.put("message", "Πρόβλημα κατά την ανάγνωση των στοιχείων");
                        return ok(result);
                    }
                    return ok(jsonResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την διαγραφή .");
            return ok(result);
        }
    }














    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    public Result getAllCustomersByUserId() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Long user_id = json.findPath("userId").asLong();
                if (user_id == null||user_id==0 ){
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής.");
                    return ok(result);
                } else {
                    Query q = JPA.em().createQuery("SELECT c FROM CustomersCcEntity c where c.userId="+user_id);
                    List<CustomersCcEntity> custList = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    List<HashMap<String, Object>> cust = new ArrayList<HashMap<String, Object>>();
                    int k=-1;
                    //--------------------------MASTER ------------- (customer details)
                    for(CustomersCcEntity j: custList) {
                        k++;
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("id",j.getId());
                        pt.put("customerId",j.getCustomerId());
                        pt.put("userId",j.getUserId());
                        pt.put("alarmUnitType",j.getAlarmUnitType());
                        pt.put("areaPhone",j.getAreaPhone());
                        pt.put("customerAuxiliaryPass",j.getCustomerAuxiliaryPass());
                        pt.put("customerConnectionDate",j.getCustomerConnectionDate());
                        pt.put("customerPass",j.getCustomerPass());
                        pt.put("datePublished",j.getDatePublished());
                        pt.put("directTransmissionPhones",j.getDirectTransmissionPhones());
                        pt.put("draft",j.getDraft());
                        if(j.getCancelation()!=null){
                            if(j.getCancelation()==0){
                                pt.put("cancelation",false);
                            }else{
                                pt.put("cancelation",true);
                            }
                        }
                        pt.put("duressCode",j.getDuressCode());
                        pt.put("enabled",j.getEnabled());
                        pt.put("format",j.getFormat());
                        pt.put("frequency24HourTest",j.getFrequency24HourTest());
                        pt.put("insuredAreaAddress",j.getInsuredAreaAddress());
                        pt.put("insuredAreaCity",j.getInsuredAreaCity());
                        pt.put("insuredAreaDescription",j.getInsuredAreaDescription());
                        pt.put("insuredAreaFloor",j.getInsuredAreaFloor());
                        pt.put("insuredAreaPostCode",j.getInsuredAreaPostCode());
                        pt.put("insuredAreaType",j.getInsuredAreaType());
                        pt.put("insuredAreaTypeOther",j.getInsuredAreaTypeOther());
                        pt.put("monthlyAlarmList",j.getMonthlyAlarmList());
                        pt.put("operationControllHours",j.getOperationControllHours());
                        pt.put("otherRemarks",j.getOtherRemarks());
                        pt.put("policeStation",j.getPoliceStation());
                        pt.put("subscriberName",j.getSubscriberName());
                        pt.put("weeklyTimeMonitoring",j.getWeeklyTimeMonitoring());
                        pt.put("lastAction",j.getLastAction());
                        pt.put("device",j.getDevice());
                        pt.put("system",j.getDevice());
                        pt.put("updateDate",j.getUpdateDate());
                        pt.put("visible",j.getVisible());
                        pt.put("status",j.getStatus());
                        pt.put("revisionId",j.getRevisionId());
                        pt.put("afm",j.getAfm());
                        List<PhoneNoticesCcEntity> phoneNoticiesList = JPA.em()
                                .createQuery("SELECT d FROM PhoneNoticesCcEntity d WHERE d.customerId=" + j.getCustomerId() + " ORDER BY d.id")
                                .getResultList();
                        HashMap<String, Object> p = new HashMap<String, Object>();
                        List<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
                        for(int l=0;l<phoneNoticiesList.size();l++){
                            p.put("id",phoneNoticiesList.get(l).getId());
                            p.put("phoneId",phoneNoticiesList.get(l).getPhoneNoticesId());
                            if(phoneNoticiesList.get(l).getEditable().equals(0)){
                                p.put("editable",false);
                            }else{
                                p.put("editable",true);
                            }
                            p.put("phone",phoneNoticiesList.get(l).getPhone());
                            p.put("name",phoneNoticiesList.get(l).getName());
                            p.put("username",phoneNoticiesList.get(l).getUsername());
                            p.put("customerId",phoneNoticiesList.get(l).getCustomerId());
                            if (phoneNoticiesList.get(l).getVisible().equals(0)) {
                                p.put("visible",false);
                            } else {
                                p.put("visible",true);
                            }
                            pList.add(p);
                        }
                        List<ZonesCcEntity> zonesList = JPA.em()
                                .createQuery("SELECT z FROM ZonesCcEntity z WHERE z.customerId=" + j.getCustomerId() + " ORDER BY z.id")
                                .getResultList();
                        HashMap<String, Object> z = new HashMap<String, Object>();
                        List<HashMap<String, Object>> zList = new ArrayList<HashMap<String, Object>>();
                        for(int l=0;l<zonesList.size();l++){
                            if(zonesList.get(l).getEditable()!=null) {
                                if(zonesList.get(l).getEditable()==0){
                                    z.put("editable",false);
                                }else{
                                    z.put("editable",true);
                                }
                            }
                            if(zonesList.get(l).getVisible()!=null){
                                if(zonesList.get(l).getVisible()==0){
                                    z.put("visible",false);
                                }else{
                                    z.put("visible",true);
                                }
                            }
                            z.put("id",zonesList.get(l).getId());
                            z.put("name",zonesList.get(l).getName());
                            z.put("customerId",zonesList.get(l).getCustomerId());
                            z.put("zoneId",zonesList.get(l).getZoneId());
                            z.put("idByUser",zonesList.get(l).getIdByUser());
                            zList.add(z);
                        }
                        List<AlarmUsersCcEntity> AlarmUsersList = JPA.em()
                                .createQuery("SELECT a FROM AlarmUsersCcEntity a WHERE a.customerId=" + j.getCustomerId() + " ORDER BY a.id")
                                .getResultList();
                        HashMap<String, Object> a = new HashMap<String, Object>();
                        List<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();
                        for(int l=0;l<AlarmUsersList.size();l++){
                            a.put("id",AlarmUsersList.get(l).getId());
                            a.put("alarmUserId",AlarmUsersList.get(l).getAlarmUserId());
                            a.put("customerId",AlarmUsersList.get(l).getCustomerId());
                            a.put("name",AlarmUsersList.get(l).getName());
                            a.put("username",AlarmUsersList.get(l).getUsername());
                            if(AlarmUsersList.get(l).getEditable()==0){  //TODO SUNEXEIA EDW:::::::::::::::::::::::::
                                a.put("editable",false);
                            }else{
                                a.put("editable",true);
                            }
                            if(AlarmUsersList.get(l).getVisible()==0){
                                a.put("visible",false);
                            }else{
                                a.put("visible",true);
                            }
                            a.put("name",AlarmUsersList.get(l).getName());
                            a.put("customerId",AlarmUsersList.get(l).getCustomerId());
                            aList.add(a);
                        }
                        if(j.getVisible()!=null){
                            if(j.getVisible()==0){
                                pt.put("visible",false);
                            }else{
                                pt.put("visible",true);
                            }
                        }
                        if(j.getEnabled()!=null){
                            if(j.getEnabled()==0){
                                pt.put("enabled",false);
                            }else{
                                pt.put("enabled",true);
                            }
                        }
                        if(j.getDraft()!=null){
                            if(j.getDraft()==0){
                                pt.put("draft",false);
                            }else{
                                pt.put("draft",true);
                            }
                        }
                        pt.put("phoneNotices",pList);
                        pt.put("zones",zList);
                        pt.put("alarmUsers",aList);
                        cust.add(pt);
                    }
                    returnList.put("status","ok");
                    returnList.put("customers",cust);
                    returnList.put("total", total.intValue());
                    result.put("status", "ok");
                    result.put("message", "επιτυχως");
                    DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //TODO:DATE :AYTH H METHODOS EYTHINETE GIA TO PWS THA EPISTREPSOUN OI HMEROMHNIES
                    ow.setDateFormat(myDateFormat);
                    try {
                        jsonResult = ow.writeValueAsString(returnList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        result.put("status", "error");
                        result.put("message", "Πρόβλημα κατά την ανάγνωση των στοιχείων");
                        return ok(result);
                    }
                    return ok(jsonResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την ανάκτηση δεδομένων .");
            return ok(result);
        }
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    @JsonInclude(JsonInclude.Include.NON_NULL)//DUSTYXWS DEN XREIASTHKE POU8ENA
    @BodyParser.Of(BodyParser.Json.class)
    //todo:mazepse to se methodous:gia paradeigma estw mia me8odos function() ,tha einai transactional kai 8a epistrefei response ok,if response == ok proxwra,alliws BAD REQUEST
    ////PAIrnei ena json se morfh data:[ { stoixeia customer1 },{stoixeia customer2},{stoixeia customer3}  ]
    //    //kanei loop sayto to array kai gia ka8e object analoga me to lastAction enhmerwnei thn Bash Dedomenwn
    public Result setDatacc() {
        try {
            ObjectMapper ow = new ObjectMapper();
            JsonNode json = request().body().asJson(); //
            System.out.println(json);
            ObjectNode result = Json.newObject();
            JsonNode data = ((ObjectNode) json).get("data");
            if (data != null) {
                for (int j = 0; j < data.size(); j++) {
                    JsonNode pjson = data.get(j);
                    JsonNode jsonZones = ((ObjectNode) pjson).get("zones");
                    JsonNode jsonAlarmUsers = ((ObjectNode) pjson).get("alarmUsers");
                    JsonNode jsonPhoneNotices = ((ObjectNode) pjson).get("phoneNotices");
                    //apo to arxiko json sbhnw ta details jsons (zones,alarms,phoneNotices) opote to  pjson periexei stoixeia pou aforoun ton Customer table
                    ((ObjectNode) pjson).remove("zones");
                    ((ObjectNode) pjson).remove("alarmUsers");
                    ((ObjectNode) pjson).remove("phoneNotices");
                    if (pjson == null) {
                        return badRequest("Expecting Json data");
                    } else {
                        //krataw thn hmeromhnia opws erxetai apo to json
                        String datePublished = pjson.findPath("datePublished").asText();
                        String submittedCcDate = pjson.findPath("submittedCcDate").asText();//
                        String creationCcDate = pjson.findPath("creationCcDate").asText();//creationCcDate
                        String lastAction = pjson.findPath("lastAction").asText();
                        String insuredAreaAddress = pjson.findPath("insuredAreaAddress").asText();
                        System.out.println(pjson.findPath("id").longValue());
                        // long customerId = pjson.findPath("id").asLong();
                        int id = pjson.findPath("id").asInt();
                        long customerId = pjson.findPath("customerId").asLong();
                        long cid = pjson.findPath("id").asLong();
                        //------------------check afm -------------------
                        String customerConnectionDate = pjson.findPath("customerConnectionDate").asText();
                        String updateDate = pjson.findPath("updateDate").asText();
                        Long updateDateCheck = pjson.findPath("updateDate").asLong();
                        String creationDate = pjson.findPath("creationDate").asText();
                        Integer userId = pjson.findPath("userId").asInt();
                        //sbhnw apo to json to datePublished kai to customerConnectionDate
                        ((ObjectNode) pjson).remove("datePublished");
                        ((ObjectNode) pjson).remove("customerConnectionDate");
                        ((ObjectNode) pjson).remove("updateDate");
                        ((ObjectNode) pjson).remove("creationDate");
                        ((ObjectNode) pjson).remove("creationCcDate");
                        ((ObjectNode) pjson).remove("submittedCcDate");
                        String enabled = pjson.findPath("enabled").asText(); //draft
                        String draft = pjson.findPath("draft").asText();
                        String visible = pjson.findPath("visible").asText();
                        if (enabled != null) {
                            if (enabled.equalsIgnoreCase("true")) {
                                ((ObjectNode) pjson).put("enabled", 1);
                            } else if (enabled.equalsIgnoreCase("false")) {
                                ((ObjectNode) pjson).put("enabled", 0);
                            }
                        }
                        if (draft != null) {
                            if (draft.equalsIgnoreCase("true")) {
                                ((ObjectNode) pjson).put("draft", 1);
                            } else if (draft.equalsIgnoreCase("false")) {
                                ((ObjectNode) pjson).put("draft", 0);
                            }
                        }
                        if (visible != null) {
                            if (visible.equalsIgnoreCase("true")) {
                                ((ObjectNode) pjson).put("visible", 1);
                            } else if (visible.equalsIgnoreCase("false")) {
                                ((ObjectNode) pjson).put("visible", 0);
                            }
                        }
                        CustomersCcEntity pat = Json.fromJson(pjson, CustomersCcEntity.class);    //ftoiaxnw antikeimeno pelath xwris thn hmeromhnia
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                        //                            Date datepubl = formatter.parse(datePublished);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                        if ((!datePublished.equalsIgnoreCase("null")) && (!datePublished.equalsIgnoreCase(""))) {
                            Date datepubl = formatter.parse(datePublished);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                            pat.setDatePublished(datepubl);                      //kanw set ston pelath thn nea hmeromhnia
                        }
                        //submittedCcDate
                            if ((!submittedCcDate.equalsIgnoreCase("null")) && (!submittedCcDate.equalsIgnoreCase(""))) {
                                Date dateconn = formatter.parse(submittedCcDate);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                                pat.setSubmittedCcDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                            }
                        //creationCcDate
                            if ((!creationCcDate.equalsIgnoreCase("null")) && (!creationCcDate.equalsIgnoreCase(""))) {
                                Date dateconn = formatter.parse(creationCcDate);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                                pat.setCreationCcDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                           }
                           if ((!customerConnectionDate.equalsIgnoreCase("null")) && (!customerConnectionDate.equalsIgnoreCase(""))) {
                               Date dateconn = formatter2.parse(customerConnectionDate);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                               pat.setCustomerConnectionDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                           }
                           if ((!updateDate.equalsIgnoreCase("null")) && (!updateDate.equalsIgnoreCase(" "))) {
                              Date dateconn = formatter.parse(updateDate);
                              pat.setUpdateDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                           }
                           if (lastAction.equalsIgnoreCase("U")) {
                              Query qCust = JPA.em().createQuery("SELECT c FROM CustomersCcEntity c ");
                              List<CustomersCcEntity> cList = qCust.getResultList();
                              int count = 0;
                              for (CustomersCcEntity it : cList) {
                                if (it.getId() == id) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                result.put("status", "ok");
                                result.put("message", "Προσπαθείτε να ενημερώσετε έναν πελάτη ο οποίος δεν είναι καταχωρημένος");
                            }
                            if(pat.getStatus().equalsIgnoreCase("SUBMITTED")){
                                pat.setSubmittedCcDate(new Date());
                            }
                            pat.setUpdateDate(new Date());
                            JPA.em().merge(pat);
                            result.put("status", "ok");
                            result.put("message", "H ενημέρωση του πελάτη έγινε με επιτιχία.");
                        } else {
                            return badRequest("Παρακαλώ καταχωρήστε ενέργεια (I για insert,U για update,D για delete)-->lastAction()");
                        }
                        if (jsonAlarmUsers != null) {
                            //an einai last action delete kai draft tote kai monon tote antikatesthse ta details me to teleytaio tous revision(max revision)
                                if (pat.getDraft() == 0) {
                                    Query allAlusrsCc = JPA.em().createQuery("SELECT a FROM AlarmUsersCcEntity a where a.customerId=" + customerId);
                                    List<AlarmUsersCcEntity> allAlusrsCclist = allAlusrsCc.getResultList();
                                    for (int p = 0; p < allAlusrsCclist.size(); p++) {
                                        JPA.em().remove(allAlusrsCclist.get(p));
                                    }
                                }
                                for (int i = 0; i < jsonAlarmUsers.size(); i++) {
                                    JsonNode ajson = jsonAlarmUsers.get(i);
                                    ((ObjectNode) ajson).remove("id");
                                    String editable = ajson.findPath("editable").asText();
                                    String vis = ajson.findPath("visible").asText();
                                    ((ObjectNode) ajson).remove("editable");
                                    ((ObjectNode) ajson).remove("visible");
                                    AlarmUsersCcEntity au = Json.fromJson(ajson, AlarmUsersCcEntity.class);
                                    au.setCustomerId(customerId);
                                    if (editable.equalsIgnoreCase("true")) {
                                        au.setEditable((byte) 1);
                                    } else {
                                        au.setEditable((byte) 0);
                                    }
                                    if (vis.equalsIgnoreCase("true")) {
                                        au.setVisible((byte) 1);
                                    } else {
                                        au.setVisible((byte) 0);
                                    }
                                    if (vis.equalsIgnoreCase("true")) {
                                        au.setVisible((byte) 1);
                                    } else {
                                        au.setVisible((byte) 0);
                                    }
                                    JPA.em().persist(au);
                                }
                        }
                        if (jsonZones != null) {
//-----------------***************************************************************************************----------------------------------
                                if (pat.getDraft() == 0) {
                                    Query zCc = JPA.em().createQuery("SELECT a FROM ZonesCcEntity a where a.customerId=" + customerId);
                                    List<ZonesCcEntity> zCcCclist = zCc.getResultList();
                                    for (int p = 0; p < zCcCclist.size(); p++) {
                                        JPA.em().remove(zCcCclist.get(p));
                                    }
                                }
//-----------------***************************************************************************************----------------------------------
                                for (int i = 0; i < jsonZones.size(); i++) {
                                    JsonNode ajson = jsonZones.get(i);
                                    //((ObjectNode) ajson).remove("zoneId");
                                    String editable = ajson.findPath("editable").asText();
                                    String vsi = ajson.findPath("visible").asText();
                                    ((ObjectNode) ajson).remove("editable");
                                    ((ObjectNode) ajson).remove("visible");
                                    ((ObjectNode) ajson).remove("id");
                                    ZonesCcEntity au = Json.fromJson(ajson, ZonesCcEntity.class);
                                    au.setCustomerId(customerId);
                                    if(editable!=null && !editable.equalsIgnoreCase("")){
                                        if (editable.equalsIgnoreCase("true")) {
                                            au.setEditable((byte) 1);
                                        } else {
                                            au.setEditable((byte) 0);
                                        }
                                    }
                                    if(visible!=null && !visible.equalsIgnoreCase("")){
                                        if (vsi.equalsIgnoreCase("true")) {
                                            au.setVisible((byte) 1);
                                        } else {
                                            au.setVisible((byte) 0);
                                        }
                                    }
                                    ObjectMapper mapper = new ObjectMapper();
                                    JsonNode nodecc = mapper.convertValue(au, JsonNode.class);
                                    System.out.println("ZONES CC JSON");
                                    ((ObjectNode) nodecc).remove("id");
                                    JPA.em().persist(au);
                                }
                        }
                        if (jsonPhoneNotices != null) {
                                if (pat.getDraft() == 0) {
                                    Query pCc = JPA.em().createQuery("SELECT a FROM PhoneNoticesCcEntity a where a.customerId=" + customerId);
                                    List<PhoneNoticesCcEntity> pCcCclist = pCc.getResultList();
                                    for (int p = 0; p < pCcCclist.size(); p++) {
                                        JPA.em().remove(pCcCclist.get(p));
                                    }
                                }
                               for (int i = 0; i < jsonPhoneNotices.size(); i++) {
                                    JsonNode ajson = jsonPhoneNotices.get(i);
                                    ((ObjectNode) ajson).remove("id");
                                    String editable = ajson.findPath("editable").asText();
                                    String vis = ajson.findPath("visible").asText();
                                    ((ObjectNode) ajson).remove("editable");
                                    ((ObjectNode) ajson).remove("visible");
                                    PhoneNoticesCcEntity au = Json.fromJson(ajson, PhoneNoticesCcEntity.class);
                                    au.setCustomerId(customerId);
                                    if (editable.equalsIgnoreCase("true")) {
                                        au.setEditable((byte) 1);
                                    } else {
                                        au.setEditable((byte) 0);
                                    }
                                    if (vis.equalsIgnoreCase("true")) {
                                        au.setVisible((byte) 1);
                                    } else {
                                        au.setVisible((byte) 0);
                                    }
                                    JPA.em().persist(au);
                                }//
                            }
                    }
                    jsonZones = null; //
                    jsonAlarmUsers = null; //
                    jsonPhoneNotices = null; //
                }
            }
            String status = result.findPath("status").asText();
            if (status.equalsIgnoreCase("") || status.isEmpty()) {
                result.put("status", "error");
                result.put("message", "Λανθασμένη μορφή εισαγωγής δεδομένων.");
            }
            return ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την εισαγωγή Πελάτη.");
            return ok(result);
        }
    }



    @SuppressWarnings("Duplicates")
    @Transactional
    @JsonInclude(JsonInclude.Include.NON_NULL)//DUSTYXWS DEN XREIASTHKE POU8ENA
    @BodyParser.Of(BodyParser.Json.class)
    //todo:mazepse to se methodous:gia paradeigma estw mia me8odos function() ,tha einai transactional kai 8a epistrefei response ok,if response == ok proxwra,alliws BAD REQUEST
    ////PAIrnei ena json se morfh data:[ { stoixeia customer1 },{stoixeia customer2},{stoixeia customer3}  ]
    //    //kanei loop sayto to array kai gia ka8e object analoga me to lastAction enhmerwnei thn Bash Dedomenwn
    public Result changeStatusToInProgress() {
        ObjectMapper ow = new ObjectMapper();
        JsonNode json = request().body().asJson(); //
        System.out.println(json);
        try {
            ObjectNode result = Json.newObject();
            if (json == null ) {
                return badRequest("Expecting Json data");
            } else {

                //krataw thn hmeromhnia opws erxetai apo to json
                Integer id = json.findPath("id").asInt();
                String status = json.findPath("status").asText();

                if(id!=null && status!=null){
                    CustomersCcEntity cc = JPA.em().find(CustomersCcEntity.class, id); //tameio 1
                    if(status.equalsIgnoreCase("SUBMITTED")){
                        cc.setSubmittedCcDate(new Date());
                    }
                    cc.setStatus(status);
                    result.put("status", "ok");
                    result.put("message", "Η ενημέρωση έγινε με επιτυχία");
                    return ok(result);
                }
                result.put("status", "error");
                result.put("message", "Πρόβλημα κατά την ενημέρωση");
                return ok(result);

            }
        }catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την ενημέρωση");
            return ok(result);
        }
    }




    @SuppressWarnings("Duplicates")
    private AlarmUsersEntity assignmentToAlarmUser(AlarmUsersRevisionsEntity ar) {
        AlarmUsersEntity a = new AlarmUsersEntity();
        a.setCustomerId(ar.getCustomerId());
        a.setEditable(ar.getEditable());
        a.setName(ar.getName());
        a.setUsername(ar.getUsername());
        a.setVisible(ar.getVisible());
        return a;
    }

    @SuppressWarnings("Duplicates")
    private ZonesEntity assignmentToZones(ZonesRevisionsEntity zr) {
        ZonesEntity z = new ZonesEntity();
        z.setEditable(zr.getEditable());
        z.setCustomerId(zr.getCustomerId());
        z.setName(zr.getName());
        z.setIdByUser(zr.getIdByUser());
        z.setVisible(zr.getVisible());
        return z;
    }
    @SuppressWarnings("Duplicates")

    private PhoneNoticesEntity assignmentToPhoneNotices(PhoneNoticesRevisionsEntity pr) {
        PhoneNoticesEntity p = new PhoneNoticesEntity();
        p.setCustomerId(pr.getCustomerId());
        p.setEditable(pr.getEditable());
        p.setName(pr.getName());
        p.setUsername(pr.getUsername());
        p.setVisible(pr.getVisible());
        p.setPhone(pr.getPhone());
        return p;
    }


    @SuppressWarnings("Duplicates")
    private CustomersEntity assignment(RevisionsEntity r) {
        CustomersEntity c = new CustomersEntity();
        c.setId(r.getCustomerId());
        c.setAfm(r.getAfm());
        c.setUserId(r.getUserId());
        c.setAlarmUnitType(r.getAlarmUnitType());
        c.setAreaPhone(r.getAreaPhone());
        c.setCustomerAuxiliaryPass(r.getCustomerAuxiliaryPass());
        c.setCustomerConnectionDate(r.getCustomerConnectionDate());
        c.setCustomerPass(r.getCustomerPass());
        c.setDatePublished(r.getDatePublished());
        c.setDirectTransmissionPhones(r.getDirectTransmissionPhones());
        c.setDraft(r.getDraft());
        c.setDuressCode(r.getDuressCode());
        c.setEnabled(r.getEnabled());
        c.setFormat(r.getFormat());
        c.setFrequency24HourTest(r.getFrequency24HourTest());
        c.setInsuredAreaAddress(r.getInsuredAreaAdress());
        c.setInsuredAreaCity(r.getInsuredAreaCity());
        c.setInsuredAreaDescription(r.getInsuredAreaDescription());
        c.setInsuredAreaFloor(r.getInsuredAreaFloor());
        c.setInsuredAreaPostCode(r.getInsuredAreaPostCode());
        c.setInsuredAreaType(r.getInsuredAreaType());
        c.setInsuredAreaTypeOther(r.getInsuredAreaTypeOther());
        c.setMonthlyAlarmList(r.getMonthlyAlarmList());
        c.setOperationControllHours(r.getOperationControllHours());
        c.setOtherRemarks(r.getOtherRemarks());
        c.setPoliceStation(r.getPoliceStation());
        c.setSubscriberName(r.getSubscriberName());
        c.setWeeklyTimeMonitoring(r.getWeeklyTimeMonitoring());
        c.setDevice(r.getDevice());
        c.setCreationDate(r.getCreationDate());
        c.setUpdateDate(r.getUpdateDate());
        c.setLastAction(r.getLastAction());
        c.setAfm(r.getAfm());
        c.setVisible(r.getVisible());
        return c;
    }

    @SuppressWarnings("Duplicates")
    //PREPEI NA GINEI GET KAI NA EPISTREFEI APLA ENA MHNYMATAKI OTI EINAI OK
    //TODO:ONLY FOR POSTMAN USE
    @Transactional// by customer id
    public void emptyCustomerTables(long customerId) {
        try {
            Query q1 = JPA.em().createQuery("SELECT c FROM ZonesEntity c where customerId=" + customerId);
            List<ZonesEntity> z = q1.getResultList();
            Query q2 = JPA.em().createQuery("SELECT c FROM AlarmUsersEntity  c where customerId=" + customerId);
            List<AlarmUsersEntity> a = q2.getResultList();
            Query q3 = JPA.em().createQuery("SELECT c FROM PhoneNoticesEntity c where customerId=" + customerId);
            List<PhoneNoticesEntity> p = q3.getResultList();
            Query q4 = JPA.em().createQuery("SELECT c FROM CustomersEntity c where id=" + customerId);
            List<CustomersEntity> c = q4.getResultList();

            for (int i = 0; i < z.size(); i++) {
                JPA.em().remove(z.get(i));
            }
            for (int i = 0; i < a.size(); i++) {
                JPA.em().remove(a.get(i));
            }
            for (int i = 0; i < p.size(); i++) {
                JPA.em().remove(p.get(i));
            }
            for (int i = 0; i < c.size(); i++) {
                JPA.em().remove(c.get(i));
            }

            //  result.put("id", id);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }









}






