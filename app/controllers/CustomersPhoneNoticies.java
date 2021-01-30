package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.CustomersEntity;
import models.PhoneNoticesEntity;
import play.db.jpa.JPA;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;

import javax.persistence.Query;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static play.mvc.Http.Context.Implicit.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

public class CustomersPhoneNoticies {
    /**
     * created by mpapaspyropoulos
     */
    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    //ayth h methodos ekteleitai ka8e fora pou eisagetai mia parametros
    public Result getCustomersPhoneNoticiesdetails() throws IOException {
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
                    Query q = JPA.em().createQuery("SELECT c FROM CustomersEntity c  WHERE c.userId=" + user_id); //
                    List<CustomersEntity> custList = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    boolean draftFlag;
                    boolean customerEnabledFlag;
                    ArrayList<HashMap<String, Object>> preliminaryFindings = new ArrayList<>();
                    for (CustomersEntity j : custList) {
                        HashMap<String, Object> pt = new HashMap<String, Object>();

                        List<PhoneNoticesEntity> phoneNoticiesList = JPA.em()
                                .createQuery("SELECT d FROM PhoneNoticesEntity d WHERE d.customerId=" + j.getId() + " ORDER BY d.id")
                                .getResultList();

                        ArrayList<HashMap<String, Object>> temp2 = new ArrayList<>();
                        for (int i = 0; i < phoneNoticiesList.size(); i++) {
                            HashMap<String, Object> pt2 = new HashMap<String, Object>();
                            pt2.put("CustomerId", phoneNoticiesList.get(i).getCustomerId());

                            if (phoneNoticiesList.get(i).getEditable() == 0) {
                                pt2.put("editable", false);
                            } else {
                                pt2.put("editable", true);
                            }
                            pt2.put("id", phoneNoticiesList.get(i).getId());
                            pt2.put("name", phoneNoticiesList.get(i).getName());
                            pt2.put("phone", phoneNoticiesList.get(i).getPhone());
                            pt2.put("system", j.getDevice());
                            temp2.add(pt2);
                        }
                        preliminaryFindings.add(pt);
                    }
                    returnList.put("data", preliminaryFindings);
                    returnList.put("total", total.intValue());
                    DateFormat myDateFormat = new SimpleDateFormat("M/d/Y");
                    ow.setDateFormat(myDateFormat);
                    try {
                        jsonResult = ow.writeValueAsString(returnList);
                    } catch (Exception e) {
                        e.printStackTrace();
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

    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    public Result getPhoneNoticiesByCustomerId() throws IOException {
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
                    Query q = JPA.em().createQuery("SELECT p FROM PhoneNoticesEntity p WHERE p.customerId=" + customer_id);
                    List<PhoneNoticesEntity> phNot = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    boolean draftFlag;
                    boolean customerEnabledFlag;

                    List<HashMap<String, Object>> cust = new ArrayList<HashMap<String, Object>>();
                    for (PhoneNoticesEntity j : phNot) {
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("editable", j.getEditable());
                        pt.put("phone", j.getPhone());
                        pt.put("name", j.getName());
                        pt.put("id", j.getId());
                        pt.put("customer_id", j.getCustomerId());//
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

    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result insertPhoneNotice() {
        try {
            JsonNode json = request().body().asJson();
            ObjectNode result = Json.newObject();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                PhoneNoticesEntity pnot = Json.fromJson(json, PhoneNoticesEntity.class);
                JPA.em().persist(pnot);
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

    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result deletePhoneNotices() {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Long id = json.findPath("id").asLong();
                if (id == null || id == 0) {
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής Διαγραφή.");
                    return ok(result);
                } else {

                    PhoneNoticesEntity c = JPA.em().find(PhoneNoticesEntity.class, id);
                    JPA.em().remove(c);
                    result.put("status", "ok");
                    result.put("message", "Η διαγραφή έγινε με επιτυχία.");
                    return ok(result);
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
    @BodyParser.Of(BodyParser.Json.class)
    public Result updatePhoneNotices() {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                PhoneNoticesEntity phn = Json.fromJson(json, PhoneNoticesEntity.class);
                JPA.em().merge(phn);
                result.put("status", "ok");
                result.put("message", "Η ενημέρωση έγινε με επιτυχία.");
                return ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την ενημέρωση.");
            return ok(result);
        }
    }

}
