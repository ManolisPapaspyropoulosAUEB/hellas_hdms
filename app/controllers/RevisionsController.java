package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;

import javax.persistence.Query;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static play.mvc.Http.Context.Implicit.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

@SuppressWarnings("Duplicates")
/**
 * created by mpapaspyropoulos
 */
public class RevisionsController {
    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    public Result getRevisions() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Long user_id = json.findPath("userId").asLong();
                if (user_id == null || user_id == 0) {
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής.");
                    return ok(result);
                } else {
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    int k = -1;
                    Query q2 = JPA.em().createQuery("SELECT c FROM RevisionsEntity c WHERE c.userId=" + user_id);
                    List<RevisionsEntity> revList = q2.getResultList();
                    List<HashMap<String, Object>> revListmap = new ArrayList<HashMap<String, Object>>();
                    for (RevisionsEntity j : revList) {
                        k++;
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("id", j.getId());
                        pt.put("customerId", j.getCustomerId());
                        pt.put("userId", j.getUserId());
                        pt.put("alarmUnitType", j.getAlarmUnitType());
                        pt.put("areaPhone", j.getAreaPhone());
                        pt.put("customerAuxiliaryPass", j.getCustomerAuxiliaryPass());
                        pt.put("customerConnectionDate", j.getCustomerConnectionDate());
                        pt.put("customerPass", j.getCustomerPass());
                        pt.put("datePublished", j.getDatePublished());
                        pt.put("directTransmissionPhones", j.getDirectTransmissionPhones());
                        pt.put("duressCode", j.getDuressCode());
                        pt.put("afm", j.getAfm());
                        pt.put("format", j.getFormat());
                        pt.put("frequency24HourTest", j.getFrequency24HourTest());
                        pt.put("insuredAreaAddress", j.getInsuredAreaAdress());
                        pt.put("insuredAreaCity", j.getInsuredAreaCity());
                        pt.put("insuredAreaDescription", j.getInsuredAreaDescription());
                        pt.put("insuredAreaFloor", j.getInsuredAreaFloor());
                        pt.put("insuredAreaPostCode", j.getInsuredAreaPostCode());
                        pt.put("insuredAreaType", j.getInsuredAreaType());
                        pt.put("insuredAreaTypeOther", j.getInsuredAreaTypeOther());
                        pt.put("monthlyAlarmList", j.getMonthlyAlarmList());
                        pt.put("operationControllHours", j.getOperationControllHours());
                        pt.put("otherRemarks", j.getOtherRemarks());
                        pt.put("policeStation", j.getPoliceStation());
                        pt.put("subscriberName", j.getSubscriberName());
                        pt.put("weeklyTimeMonitoring", j.getWeeklyTimeMonitoring());
                        pt.put("device", j.getDevice());
                        pt.put("revisions", j.getRevisions());
                        pt.put("revisionDate", j.getRevisionDate());
                        pt.put("creationDate", j.getCreationDate());
                        pt.put("updateDate", j.getUpdateDate());
                        if (j.getVisible() != null) {
                            if (j.getVisible() == 0) {
                                pt.put("visible", false);
                            } else {
                                pt.put("visible", true);
                            }
                        }
                        if (j.getEnabled() != null) {
                            if (j.getEnabled() == 0) {
                                pt.put("enabled", false);
                            } else {
                                pt.put("enabled", true);
                            }
                        }
                        if (j.getDraft() != null) {
                            if (j.getDraft() == 0) {
                                pt.put("draft", false);
                            } else {
                                pt.put("draft", true);
                            }
                        }
                        List<PhoneNoticesRevisionsEntity> phoneNoticiesList = JPA.em()
                                .createQuery("SELECT d FROM PhoneNoticesRevisionsEntity d WHERE d.customerId=" + j.getCustomerId() + " ORDER BY d.id")
                                .getResultList();
                        HashMap<String, Object> p = new HashMap<String, Object>();
                        List<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
                        for (int l = 0; l < phoneNoticiesList.size(); l++) {
                            p.put("id", phoneNoticiesList.get(l).getId());

                            if (phoneNoticiesList.get(l).getEditable().equals(0)) {
                                p.put("editable", false);
                            } else {
                                p.put("editable", true);
                            }
                            p.put("phone", phoneNoticiesList.get(l).getPhone());
                            p.put("name", phoneNoticiesList.get(l).getName());
                            p.put("username", phoneNoticiesList.get(l).getUsername());
                            p.put("revision", phoneNoticiesList.get(l).getRevision());
                            p.put("revisionDate", phoneNoticiesList.get(l).getRevisionDate());
                            p.put("revisionId", phoneNoticiesList.get(l).getRevisionId());
                            p.put("customerId", phoneNoticiesList.get(l).getCustomerId());
                            if (phoneNoticiesList.get(l).getVisible().equals(0)) {
                                p.put("visible", false);
                            } else {
                                p.put("visible", true);
                            }
                            pList.add(p);
                        }
                        List<ZonesRevisionsEntity> zonesList = JPA.em()
                                .createQuery("SELECT z FROM ZonesRevisionsEntity z WHERE z.customerId=" + j.getCustomerId() + " ORDER BY z.id")
                                .getResultList();
                        HashMap<String, Object> z = new HashMap<String, Object>();
                        List<HashMap<String, Object>> zList = new ArrayList<HashMap<String, Object>>();
                        for (int l = 0; l < zonesList.size(); l++) {
                            z.put("id", zonesList.get(l).getId());
                            if (zonesList.get(l).getEditable() == 0) {
                                z.put("editable", false);
                            } else {
                                z.put("editable", true);
                            }
                            if (zonesList.get(l).getVisible() == 0) {
                                z.put("visible", false);
                            } else {
                                z.put("visible", true);
                            }
                            z.put("name", zonesList.get(l).getName());
                            z.put("customerId", zonesList.get(l).getCustomerId());
                            z.put("revision", zonesList.get(l).getRevision());
                            z.put("revisionDate", zonesList.get(l).getRevisionDate());
                            z.put("zoneId", zonesList.get(l).getZoneId());
                            z.put("revisionId", zonesList.get(l).getRevisionId());
                            z.put("idByUser", zonesList.get(l).getIdByUser());
                            zList.add(z);
                        }
                        List<AlarmUsersRevisionsEntity> AlarmUsersList = JPA.em()
                                .createQuery("SELECT a FROM AlarmUsersRevisionsEntity a WHERE a.customerId=" + j.getCustomerId() + " ORDER BY a.id")
                                .getResultList();
                        HashMap<String, Object> a = new HashMap<String, Object>();
                        List<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();
                        for (int l = 0; l < AlarmUsersList.size(); l++) {
                            a.put("id", AlarmUsersList.get(l).getId());
                            a.put("customerId", AlarmUsersList.get(l).getCustomerId());
                            //  a.put("editable",AlarmUsersList.get(l).getEditable());
                            a.put("name", AlarmUsersList.get(l).getName());
                            a.put("username", AlarmUsersList.get(l).getUsername());
                            a.put("revision", AlarmUsersList.get(l).getRevision());
                            a.put("revisionDate", AlarmUsersList.get(l).getRevisionDate());
                            a.put("alarmUserId", AlarmUsersList.get(l).getAlarmUserId());
                            a.put("revisionId", AlarmUsersList.get(l).getRevisionId());
                            if (AlarmUsersList.get(l).getEditable() == 0) {  //TODO SUNEXEIA EDW
                                a.put("editable", false);
                            } else {
                                a.put("editable", true);
                            }
                            if (AlarmUsersList.get(l).getVisible() == 0) {
                                a.put("visible", false);
                            } else {
                                a.put("visible", true);
                            }
                            a.put("name", AlarmUsersList.get(l).getName());
                            a.put("customerId", AlarmUsersList.get(l).getCustomerId());
                            aList.add(a);
                        }
                        pt.put("phoneNotices", pList);
                        pt.put("zones", zList);
                        pt.put("alarmUsers", aList);
                        if (j.getVisible() != null) {
                            if (j.getVisible() == 0) {
                                pt.put("visible", false);
                            } else {
                                pt.put("visible", true);
                            }
                        }
                        revListmap.add(pt);
                    }
                    returnList.put("status", "ok");
                    returnList.put("revisions", revListmap);
                    returnList.put("total", revListmap.size());
                    result.put("status", "ok");
                    result.put("message", "επιτυχως");
                    DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //TODO:DATE :AYTH H METHODOS EYTHINETE GIA TO PWS THA EPISTREPSOUN OI HMEROMHNIES
                    ow.setDateFormat(myDateFormat);
                    try {
                        jsonResult = ow.writeValueAsString(returnList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // ObjectNode result = Json.newObject();
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
    public Result getRevisionsAsJson() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Long user_id = json.findPath("userId").asLong();
                if (user_id == null || user_id == 0) {
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής.");
                    return ok(result);
                } else {
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    int k = -1;
                    Query q2 = JPA.em().createQuery("SELECT c FROM RevisionsEntity c WHERE c.userId=" + user_id);
                    List<RevisionsEntity> revList = q2.getResultList();
                    List<HashMap<String, Object>> revListmap = new ArrayList<HashMap<String, Object>>();
                    for (RevisionsEntity j : revList) {
                        k++;
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("id", j.getId());
                        pt.put("customerId", j.getCustomerId());
                        pt.put("userId", j.getUserId());
                        pt.put("alarmUnitType", j.getAlarmUnitType());
                        pt.put("areaPhone", j.getAreaPhone());
                        pt.put("customerAuxiliaryPass", j.getCustomerAuxiliaryPass());
                        pt.put("customerConnectionDate", j.getCustomerConnectionDate());
                        pt.put("customerPass", j.getCustomerPass());
                        pt.put("datePublished", j.getDatePublished());
                        pt.put("directTransmissionPhones", j.getDirectTransmissionPhones());
                        pt.put("duressCode", j.getDuressCode());
                        pt.put("format", j.getFormat());
                        pt.put("frequency24HourTest", j.getFrequency24HourTest());
                        pt.put("insuredAreaAddress", j.getInsuredAreaAdress());
                        pt.put("insuredAreaCity", j.getInsuredAreaCity());
                        pt.put("insuredAreaDescription", j.getInsuredAreaDescription());
                        pt.put("insuredAreaFloor", j.getInsuredAreaFloor());
                        pt.put("insuredAreaPostCode", j.getInsuredAreaPostCode());
                        pt.put("insuredAreaType", j.getInsuredAreaType());
                        pt.put("insuredAreaTypeOther", j.getInsuredAreaTypeOther());
                        pt.put("monthlyAlarmList", j.getMonthlyAlarmList());
                        pt.put("operationControllHours", j.getOperationControllHours());
                        pt.put("otherRemarks", j.getOtherRemarks());
                        pt.put("policeStation", j.getPoliceStation());
                        pt.put("subscriberName", j.getSubscriberName());
                        pt.put("weeklyTimeMonitoring", j.getWeeklyTimeMonitoring());
                        pt.put("device", j.getDevice());
                        pt.put("revisions", j.getRevisions());
                        pt.put("revisionDate", j.getRevisionDate());
                        pt.put("creationDate", j.getCreationDate());
                        pt.put("updateDate", j.getUpdateDate());
                        pt.put("visible", j.getVisible());
                        pt.put("enabled", j.getEnabled());
                        pt.put("draft", j.getDraft());
                        revListmap.add(pt);
                    }
                    returnList.put("status", "ok");
                    returnList.put("revisions", revListmap);
                    returnList.put("total", revListmap.size());
                    result.put("status", "ok");
                    result.put("message", "επιτυχως");
                    DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //TODO:DATE :AYTH H METHODOS EYTHINETE GIA TO PWS THA EPISTREPSOUN OI HMEROMHNIES
                    ow.setDateFormat(myDateFormat);
                    try {
                        jsonResult = ow.writeValueAsString(returnList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // ObjectNode result = Json.newObject();
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


    @play.db.jpa.Transactional
    //ayth h methodos ekteleitai ka8e fora pou eisagetai mia parametros
    public Result getRevisionsDetails() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Long user_id = json.findPath("user_id").asLong();
                if (user_id == null || user_id == 0) {
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής.");
                    return ok(result);
                } else {
                    Query q = JPA.em().createQuery("SELECT c FROM RevisionsEntity c WHERE c.userId=" + user_id);
                    List<RevisionsEntity> rev = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    List<HashMap<String, Object>> cust = new ArrayList<HashMap<String, Object>>();
                    for (RevisionsEntity j : rev) {
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("alarmUnitType", j.getAlarmUnitType());
                        pt.put("areaPhone", j.getAreaPhone());
                        pt.put("customerAuxiliaryPass", j.getCustomerAuxiliaryPass());
                        pt.put("customerConnectionDate", j.getCustomerConnectionDate());
                        pt.put("custommerPass", j.getCustomerPass());//
                        pt.put("datePublished", j.getDatePublished()); //
                        pt.put("directTransmissionPhones", j.getDirectTransmissionPhones()); //
                        pt.put("enabled", j.getEnabled());
                        pt.put("format", j.getFormat());
                        pt.put("frequency24HourTest", j.getFrequency24HourTest());
                        pt.put("id", j.getId());
                        pt.put("user_id", j.getUserId());
                        pt.put("insuredAreaAddress", j.getInsuredAreaAdress());
                        pt.put("insuredAreaCity", j.getInsuredAreaCity());
                        pt.put("insuredAreaDescription", j.getInsuredAreaDescription());
                        pt.put("insuredAreaFloor", j.getInsuredAreaFloor());
                        pt.put("insuredAreaPostCode", j.getInsuredAreaPostCode());
                        pt.put("insuredAreaTypeOther", j.getInsuredAreaTypeOther());
                        pt.put("monthlyAlarmList", j.getMonthlyAlarmList());
                        pt.put("operationControlHours", j.getOperationControllHours());
                        pt.put("otherRemarks", j.getOtherRemarks());
                        pt.put("policeStation", j.getPoliceStation());
                        pt.put("subscriberName", j.getSubscriberName());
                        pt.put("weeklyTimeMonitoring", j.getWeeklyTimeMonitoring());
                        pt.put("revision", j.getRevisions());
                        pt.put("revisionDate", j.getRevisionDate());
                        pt.put("system", j.getDevice());

                        List<PhoneNoticesRevisionsEntity> phoneNoticiesList = JPA.em()
                                .createQuery("SELECT d FROM PhoneNoticesRevisionsEntity d WHERE d.customerId=" + j.getCustomerId() + " ORDER BY d.id")
                                .getResultList();

                        List<ZonesRevisionsEntity> zonesList = JPA.em()
                                .createQuery("SELECT z FROM ZonesRevisionsEntity z WHERE z.customerId=" + j.getCustomerId() + " ORDER BY z.id")
                                .getResultList();

                        List<AlarmUsersRevisionsEntity> AlarmUsersList = JPA.em()
                                .createQuery("SELECT a FROM AlarmUsersRevisionsEntity a WHERE a.customerId=" + j.getCustomerId() + " ORDER BY a.id")
                                .getResultList();

                        pt.put("phoneNotices", phoneNoticiesList);
                        pt.put("zones", zonesList);
                        pt.put("alarmUsers", AlarmUsersList);
                        pt.put("system", j.getDevice());
                        cust.add(pt);
                    }
                    returnList.put("data", cust);
                    returnList.put("total", total.intValue());
                    DateFormat myDateFormat = new SimpleDateFormat("M/d/Y");
                    ow.setDateFormat(myDateFormat);
                    try {
                        jsonResult = ow.writeValueAsString(returnList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // ObjectNode result = Json.newObject();
                        result.put("status", "error");
                        result.put("message", "Πρόβλημα κατά την ανάγνωση των στοιχείων των Ασθενών");
                        return ok(result);
                    }
                    return ok(jsonResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την διαγραφή του Ασφαλιστικού Ταμείου.");
            return ok(result);
        }
    }


    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result insertRevision() {
        try {
            ObjectMapper ow = new ObjectMapper();
            JsonNode json = request().body().asJson(); //
            ObjectNode result = Json.newObject();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                long id = Long.valueOf(0);
                //krataw thn hmeromhnia opws erxetai apo to json
                String datePublished = json.findPath("datePublished").asText();
                String customerConnectionDate = json.findPath("customerConnectionDate").asText();  //revisionDate
                String revisionDate = json.findPath("revisionDate").asText();  //revisionDate
                //sbhnw apo to json to datePublished kai to customerConnectionDate
                ((ObjectNode) json).remove("datePublished");
                ((ObjectNode) json).remove("customerConnectionDate");
                ((ObjectNode) json).remove("revisionDate");
                String enabled = json.findPath("enabled").asText(); //draft
                String draft = json.findPath("draft").asText();
                if (enabled != null) {
                    if (enabled.equalsIgnoreCase("true")) {
                        ((ObjectNode) json).put("enabled", 1);
                    } else if (enabled.equalsIgnoreCase("false")) {
                        ((ObjectNode) json).put("enabled", 0);
                    }
                }
                if (draft != null) {
                    if (draft.equalsIgnoreCase("true")) {
                        ((ObjectNode) json).put("draft", 1);
                    } else if (draft.equalsIgnoreCase("false")) {
                        ((ObjectNode) json).put("draft", 0);
                    }
                }
                RevisionsEntity pat = Json.fromJson(json, RevisionsEntity.class);         //ftoiaxnw antikeimeno pelath xwris thn hmeromhnia
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");        //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                DateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");        //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                if (datePublished != "" && datePublished != null && (!datePublished.equalsIgnoreCase(""))) {
                    Date datepubl = formatter.parse(datePublished);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setDatePublished(datepubl);                      //kanw set ston pelath thn nea hmeromhnia

                }
                if (customerConnectionDate != "" && customerConnectionDate != null && (!customerConnectionDate.equalsIgnoreCase(""))) {
                    Date dateconn = formatter2.parse(customerConnectionDate);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setCustomerConnectionDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia

                }
                if (revisionDate != "" && revisionDate != null && (!revisionDate.equalsIgnoreCase(""))) {
                    Date daterev = formatter3.parse(revisionDate);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setCustomerConnectionDate(daterev);                      //kanw set ston pelath thn nea hmeromhnia

                }
                JPA.em().persist(pat);                             //persist sthn bash
                Query q = JPA.em().createQuery("SELECT c FROM RevisionsEntity c order by id desc");
                List<CustomersEntity> custList = q.getResultList();
                id = (long) custList.get(0).getId();
                result.put("id", id);
                result.put("status", "ok");
                result.put("message", "Η καταχώρηση έγινε με επιτυχία.");
                return ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την εισαγωγή Πελάτη.");
            return ok(result);
        }
    }


    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result createRevision() {
        try {
            JsonNode json = request().body().asJson(); //
            ObjectNode result = Json.newObject();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                String revisionDate = json.findPath("revisionDate").textValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(revisionDate);
                java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
                ((ObjectNode) json).put("revisionDate", timestamp.getTime());

                String customerConnectionDate = json.findPath("customerConnectionDate").textValue();
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = sdf.parse(customerConnectionDate);
                timestamp = new java.sql.Timestamp(date.getTime());
                ((ObjectNode) json).put("customerConnectionDate", timestamp.getTime());

                String datePublished = json.findPath("datePublished").textValue();
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = sdf.parse(datePublished);
                timestamp = new java.sql.Timestamp(date.getTime());

                ((ObjectNode) json).put("datePublished", timestamp.getTime());
                RevisionsEntity med = Json.fromJson(json, RevisionsEntity.class);

                JPA.em().persist(med);

                result.put("status", "ok");
                result.put("message", "Η καταχώρηση έγινε με επιτυχία.");
                return ok(result);

            }
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την εισαγωγή Πελάτη.");
            return ok(result);
        }
    }


    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result getZoneRevisionByCustomerId() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Long customer_id = json.findPath("customer_id").asLong();
                if (customer_id == null || customer_id == 0) {
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής.");
                    return ok(result);
                } else {
                    Query q = JPA.em().createQuery("SELECT z FROM ZonesRevisionsEntity z WHERE z.customerId=" + customer_id);
                    List<ZonesRevisionsEntity> zList = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    boolean draftFlag;
                    boolean customerEnabledFlag;
                    List<HashMap<String, Object>> zones = new ArrayList<HashMap<String, Object>>();
                    for (ZonesRevisionsEntity j : zList) {
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("editable", j.getEditable());
                        pt.put("name", j.getName());
                        pt.put("id", j.getId());
                        pt.put("customer_id", j.getCustomerId());
                        pt.put("revision", j.getRevision());
                        pt.put("revision_date", j.getRevisionDate());
                        zones.add(pt);
                    }

                    returnList.put("data", zones);
                    returnList.put("total", total.intValue());
                    DateFormat myDateFormat = new SimpleDateFormat("M/d/Y");
                    ow.setDateFormat(myDateFormat);
                    try {
                        jsonResult = ow.writeValueAsString(returnList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        result.put("status", "error");
                        result.put("message", "Πρόβλημα κατά την ανάγνωση των στοιχείων ");
                        return ok(result);
                    }
                    return ok(jsonResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την ανάγνωση των στοιχείων");
            return ok(result);
        }
    }


    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result insertZonesRevisions() {
        try {
            JsonNode json = request().body().asJson(); //
            ObjectNode result = Json.newObject();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else { //ZonesRevisionsEntity
//                ZonesRevisionsEntity zrev = Json.fromJson(json, ZonesRevisionsEntity.class);
                String revisionDate = json.findPath("revisionDate").asText();
                ((ObjectNode) json).remove("revisionDate");
                String editable = json.findPath("editable").asText(); //draft
                if (editable != null) {
                    if (editable.equalsIgnoreCase("true")) {
                        ((ObjectNode) json).put("editable", 1);
                    } else if (editable.equalsIgnoreCase("false")) {
                        ((ObjectNode) json).put("editable", 0);
                    }
                }

                ZonesRevisionsEntity zrev = Json.fromJson(json, ZonesRevisionsEntity.class);
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                if (revisionDate != "" && revisionDate != null && (!revisionDate.equalsIgnoreCase(""))) {
                    Date datepubl = formatter.parse(revisionDate);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    zrev.setRevisionDate(datepubl);                      //kanw set ston pelath thn nea hmeromhnia
                }

                JPA.em().persist(zrev);
                result.put("status", "ok");
                result.put("message", "Η καταχώρηση έγινε με επιτυχία.");
                return ok(result);

            }
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την εισαγωγή .");
            return ok(result);
        }
    }


    @play.db.jpa.Transactional
    public Result getPhoneNoticiesRevisionByCustomerId() throws IOException {
        ObjectNode result = Json.newObject();

        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Long customer_id = json.findPath("customer_id").asLong();
                if (customer_id == null || customer_id == 0) {
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής.");
                    return ok(result);
                } else {
                    Query q = JPA.em().createQuery("SELECT p FROM PhoneNoticesRevisionsEntity p WHERE p.customerId=" + customer_id);
                    List<PhoneNoticesRevisionsEntity> phNot = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    boolean draftFlag;
                    boolean customerEnabledFlag;
                    List<HashMap<String, Object>> cust = new ArrayList<HashMap<String, Object>>();
                    for (PhoneNoticesRevisionsEntity j : phNot) {
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("editable", j.getEditable());
                        pt.put("phone", j.getPhone());
                        pt.put("name", j.getName());
                        pt.put("id", j.getId());
                        pt.put("customer_id", j.getCustomerId());//
                        pt.put("revision", j.getRevision());//
                        pt.put("revision_date", j.getRevisionDate());//
                        cust.add(pt);
                    }

                    returnList.put("data", cust);
                    returnList.put("total", total.intValue());
                    DateFormat myDateFormat = new SimpleDateFormat("M/d/Y");
                    ow.setDateFormat(myDateFormat);
                    try {
                        jsonResult = ow.writeValueAsString(returnList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // ObjectNode result = Json.newObject();
                        result.put("status", "error");
                        result.put("message", "Πρόβλημα κατά την ανάγνωση των στοιχείων.");
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

    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result insertPhoneNoticesRevision() {
        try {  //PhoneNoticesRevisionsEntity
            JsonNode json = request().body().asJson(); //
            ObjectNode result = Json.newObject();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                String revisionDate = json.findPath("revisionDate").asText();
                ((ObjectNode) json).remove("revisionDate");
                String editable = json.findPath("editable").asText(); //draft
                if (editable != null) {
                    if (editable.equalsIgnoreCase("true")) {
                        ((ObjectNode) json).put("editable", 1);
                    } else if (editable.equalsIgnoreCase("false")) {
                        ((ObjectNode) json).put("editable", 0);
                    }
                }
                PhoneNoticesRevisionsEntity zrev = Json.fromJson(json, PhoneNoticesRevisionsEntity.class);
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                if (revisionDate != "" && revisionDate != null && (!revisionDate.equalsIgnoreCase(""))) {
                    Date datepubl = formatter.parse(revisionDate);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    zrev.setRevisionDate(datepubl);                      //kanw set ston pelath thn nea hmeromhnia
                }
                JPA.em().persist(zrev);
                result.put("status", "ok");
                result.put("message", "Η καταχώρηση έγινε με επιτυχία.");
                return ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την εισαγωγή .");
            return ok(result);
        }
    }

    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result getAlarmUserRevisionByCustomerId() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Long customer_id = json.findPath("customer_id").asLong();
                if (customer_id == null || customer_id == 0) {
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής.");
                    return ok(result);
                } else {
                    Query q = JPA.em().createQuery("SELECT a FROM AlarmUsersRevisionsEntity a WHERE a.customerId=" + customer_id);
                    List<AlarmUsersRevisionsEntity> zList = q.getResultList();

                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";

                    Integer total = q.getResultList().size();
                    boolean draftFlag;
                    boolean customerEnabledFlag;

                    List<HashMap<String, Object>> zones = new ArrayList<HashMap<String, Object>>();
                    for (AlarmUsersRevisionsEntity j : zList) {
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("editable", j.getEditable());
                        pt.put("name", j.getName());
                        pt.put("username", j.getUsername());

                        pt.put("id", j.getId());
                        pt.put("customer_id", j.getCustomerId());
                        pt.put("revision", j.getRevision());
                        pt.put("revision_date", j.getRevisionDate());

                        zones.add(pt);
                    }

                    returnList.put("data", zones);
                    returnList.put("total", total.intValue());
                    DateFormat myDateFormat = new SimpleDateFormat("M/d/Y");
                    ow.setDateFormat(myDateFormat);
                    try {
                        jsonResult = ow.writeValueAsString(returnList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // ObjectNode result = Json.newObject();
                        result.put("status", "error");
                        result.put("message", "Πρόβλημα κατά την ανάγνωση των στοιχείων ");
                        return ok(result);
                    }
                    return ok(jsonResult);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την ανάγνωση των στοιχείων");
            return ok(result);
        }
    }


    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result setDataRevisions() {
        try {
            ObjectMapper ow = new ObjectMapper();
            JsonNode json = request().body().asJson(); //
            JsonNode jsonZones = ((ObjectNode) json).get("zonesRevisions"); //
            JsonNode jsonAlarmUsers = ((ObjectNode) json).get("alarmUsersRevisions"); //
            JsonNode jsonPhoneNotices = ((ObjectNode) json).get("phoneNoticesRevisions"); //
            ObjectNode result = Json.newObject();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Long id = Long.valueOf(0);
                //krataw thn hmeromhnia opws erxetai apo to json
                String datePublished = json.findPath("datePublished").asText();
                String customerConnectionDate = json.findPath("customerConnectionDate").asText();
                String updateDate = json.findPath("updateDate").asText();
                String creationDate = json.findPath("creationDate").asText();

                Long userId = json.findPath("userId").asLong();
                //sbhnw apo to json to datePublished kai to customerConnectionDate
                ((ObjectNode) json).remove("datePublished");
                ((ObjectNode) json).remove("customerConnectionDate");
                ((ObjectNode) json).remove("updateDate");
                ((ObjectNode) json).remove("creationDate");

                String enabled = json.findPath("enabled").asText(); //draft
                String draft = json.findPath("draft").asText();
                String visible = json.findPath("visible").asText();
                if (enabled != null) {
                    if (enabled.equalsIgnoreCase("true")) {
                        ((ObjectNode) json).put("enabled", 1);
                    } else if (enabled.equalsIgnoreCase("false")) {
                        ((ObjectNode) json).put("enabled", 0);
                    }
                }
                if (draft != null) {
                    if (draft.equalsIgnoreCase("true")) {
                        ((ObjectNode) json).put("draft", 1);
                    } else if (draft.equalsIgnoreCase("false")) {
                        ((ObjectNode) json).put("draft", 0);
                    }
                }
                if (visible != null) {
                    if (visible.equalsIgnoreCase("true")) {
                        ((ObjectNode) json).put("visible", 1);
                    } else if (visible.equalsIgnoreCase("false")) {
                        ((ObjectNode) json).put("visible", 0);
                    }
                }
                RevisionsEntity pat = Json.fromJson(json, RevisionsEntity.class);    //ftoiaxnw antikeimeno pelath xwris thn hmeromhnia
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa

                if (datePublished != "" && datePublished != null && (!datePublished.equalsIgnoreCase(""))) {
                    Date datepubl = formatter.parse(datePublished.trim());                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setDatePublished(datepubl);                      //kanw set ston pelath thn nea hmeromhnia
                }

                if (customerConnectionDate != "" && customerConnectionDate != null && (!customerConnectionDate.equalsIgnoreCase(""))) {
                    Date dateconn = formatter.parse(customerConnectionDate.trim());                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setCustomerConnectionDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                }

                if (updateDate != "" && updateDate != null && (!updateDate.equalsIgnoreCase(""))) {
                    Date dateconn = formatter.parse(updateDate.trim());                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setUpdateDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                }
                if (creationDate != "" && creationDate != null && (!creationDate.equalsIgnoreCase(""))) {
                    Date dateconn = formatter.parse(creationDate.trim());                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setCustomerConnectionDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                }
                JPA.em().persist(pat);                             //persist sthn bash
                Query q = JPA.em().createQuery("SELECT c FROM RevisionsEntity c order by id desc");
                List<RevisionsEntity> custList = q.getResultList();
                id = Long.valueOf(custList.get(0).getId());

                Query q2 = JPA.em().createQuery("SELECT c FROM RevisionsEntity c WHERE c.userId=" + userId);
                List<RevisionsEntity> custList2 = q.getResultList();

                if (jsonZones != null) {
                    for (int i = 0; i < jsonZones.size(); i++) {
                        //TODO:KANTO SE OLA TA AUTO INCREMENT ID
                        JsonNode z = jsonZones.get(i);
                        ZonesRevisionsEntity zo = Json.fromJson(z, ZonesRevisionsEntity.class);
                        JPA.em().persist(zo);
                    }
                }
                if (jsonAlarmUsers != null) {
                    for (int i = 0; i < jsonAlarmUsers.size(); i++) {
                        JsonNode al = jsonAlarmUsers.get(i);
                        AlarmUsersRevisionsEntity ale = Json.fromJson(al, AlarmUsersRevisionsEntity.class);
                        JPA.em().persist(ale);
                    }
                }
                if (jsonPhoneNotices != null) {
                    for (int i = 0; i < jsonPhoneNotices.size(); i++) {
                        JsonNode phn = jsonPhoneNotices.get(i);
                        PhoneNoticesRevisionsEntity phnent = Json.fromJson(phn, PhoneNoticesRevisionsEntity.class);
                        JPA.em().persist(phnent);
                    }

                }
                result.put("id", id);
                result.put("status", "ok");
                result.put("message", "Η καταχώρηση έγινε με επιτυχία.");
                return ok(result);

            }
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την εισαγωγή Πελάτη.");
            return ok(result);
        }
    }

    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result insertAlarmUsersRevisions() {
        try {
            JsonNode json = request().body().asJson(); //
            ObjectNode result = Json.newObject();

            String revisionDate = json.findPath("revisionDate").asText();
            ((ObjectNode) json).remove("revisionDate");

            String editable = json.findPath("editable").asText(); //draft
            if (editable != null) {
                if (editable.equalsIgnoreCase("true")) {
                    ((ObjectNode) json).put("editable", 1);
                } else if (editable.equalsIgnoreCase("false")) {
                    ((ObjectNode) json).put("editable", 0);
                }
            }
            AlarmUsersRevisionsEntity zrev = Json.fromJson(json, AlarmUsersRevisionsEntity.class);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
            if (revisionDate != "" && revisionDate != null && (!revisionDate.equalsIgnoreCase(""))) {
                Date datepubl = formatter.parse(revisionDate);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                zrev.setRevisionDate(datepubl);                      //kanw set ston pelath thn nea hmeromhnia
            }
            JPA.em().persist(zrev);
            result.put("status", "ok");
            result.put("message", "Η καταχώρηση έγινε με επιτυχία.");
            return ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την εισαγωγή .");
            return ok(result);
        }
    }

}
