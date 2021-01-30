package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.InstallersEntity;
import models.NotificationsEntity;
import play.db.jpa.JPA;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;

import javax.persistence.Query;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static play.mvc.Controller.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

@SuppressWarnings("Duplicates")
/**
 * created by mpapaspyropoulos
 */
public class NotificationsController {
    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateNotification() {//pernei to ananeomeno json kai to manei merge..
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                String toDate = json.findPath("toDate").asText();
                String status = json.findPath("status").asText();
                ((ObjectNode) json).remove("toDate");
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                NotificationsEntity note = Json.fromJson(json, NotificationsEntity.class);
                if ((!toDate.equalsIgnoreCase("null")) && (!toDate.equalsIgnoreCase("") && (toDate != null))) {
                    Date dateconn = formatter.parse(toDate);
                    note.setToDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                }
                if (status.equalsIgnoreCase("submitted")) {
                    note.setSubmitDate(new Date());
                }
                JPA.em().merge(note);
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

    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result getNotificationsByUserId() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Integer userId = json.findPath("userId").asInt();
                if (userId == null || userId == 0) {
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής.");
                    return ok(result);
                } else {
                    String role = json.findPath("role").asText();
                    //Query q = JPA.em().createQuery("SELECT n FROM NotificationsEntity n where n.userId="+userId );
                    List<NotificationsEntity> nList = new ArrayList<>();
                    String filter = json.findPath("filter").asText();
                    String query = "";
                    if (filter.equalsIgnoreCase("READ")) {
                        query = "SELECT n FROM NotificationsEntity n where n.userId=" + userId + "and n.status=" + "'READ'";
                    } else if (filter.equalsIgnoreCase("UNREAD")) {
                        query = "SELECT n FROM NotificationsEntity n where n.userId=" + userId + "and n.status=" + "'UNREAD'";

                    } else {
                        query = "SELECT n FROM NotificationsEntity n where n.userId=" + userId;
                    }
                    Query q = JPA.em().createQuery(query);
                    nList = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    List<HashMap<String, Object>> notif = new ArrayList<HashMap<String, Object>>();
                    for (NotificationsEntity j : nList) {
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("id", j.getId());
                        pt.put("content", j.getContent());
                        pt.put("title", j.getTitle());
                        pt.put("status", j.getStatus());
                        pt.put("creationDate", j.getCreationDate());
                        pt.put("userId", j.getUserId());
                        notif.add(pt);
                    }
                    returnList.put("data", notif);
                    returnList.put("total", total.intValue());
                    DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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


    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result insertNotification() {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Query inst = JPA.em().createQuery("SELECT i FROM InstallersEntity i  order by id desc");
                List<InstallersEntity> instList = inst.getResultList();
                for (InstallersEntity i : instList) {
                    NotificationsEntity not = Json.fromJson(json, NotificationsEntity.class);
                    not.setCreationDate(new Date());
                    not.setStatus("UNREAD");
                    not.setUserId(i.getUserId());
                    JPA.em().persist(not);
                }
                result.put("status", "ok");
                result.put("message", "Η καταχώρηση έγινε με επιτυχία.");
                return ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την ενημέρωση.");
            return ok(result);
        }
    }


    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result deleteNotification() {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                int id = json.findPath("id").asInt();
                if (id == 0) {
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής Διαγραφή.");
                    return ok(result);
                } else {
                    NotificationsEntity c = JPA.em().find(NotificationsEntity.class, id);
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

}
