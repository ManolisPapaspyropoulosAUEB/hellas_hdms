package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.InstallersEntity;
import models.NotesEntity;
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
/**
 * created by mpapaspyropoulos
 */
@SuppressWarnings("Duplicates")

public class NotesController {

    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateNote() {//pernei to ananeomeno json kai to manei merge..
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                String todoDate = json.findPath("todoDate").asText();
                ((ObjectNode) json).remove("todoDate");
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                NotesEntity note = Json.fromJson(json, NotesEntity.class);
                if ((!todoDate.equalsIgnoreCase("null")) && (!todoDate.equalsIgnoreCase("") && (todoDate != null))) {
                    Date dateconn = formatter.parse(todoDate);
                    note.setTodoDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                }
                note.setSubmitDate(new Date());
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
    public Result getNotes() throws IOException {
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
                    Query q = JPA.em().createQuery("SELECT n FROM NotesEntity n where n.userId=" + userId);
                    List<NotesEntity> nList = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    List<HashMap<String, Object>> notes = new ArrayList<HashMap<String, Object>>();
                    for (NotesEntity j : nList) {
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        // pt.put("editable", j.getEditable());
                        pt.put("id", j.getId());
                        pt.put("content", j.getContent());
                        pt.put("title", j.getTitle());
                        pt.put("status", j.getStatus());
                        pt.put("submitDate", j.getSubmitDate());
                        pt.put("todoDate", j.getTodoDate());
                        pt.put("userId", j.getUserId());
                        notes.add(pt);
                    }
                    returnList.put("data", notes);
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
    public Result insertNote() {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();

            if (json == null) {
                return badRequest("Expecting Json data");
            } else {

                Integer userId = json.findPath("userId").asInt();
                if (userId == 0) {  //elegxos an einai null,,compare Int an einai null to kaneis UInteger kai an einai null mpenei automata to 0
                    result.put("status", "error");
                    result.put("message", "Απροσδιοριστος χρήστης.");
                    return ok(result);
                }

                String todoDate = json.findPath("todoDate").asText();
                ((ObjectNode) json).remove("todoDate");
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                NotesEntity note = Json.fromJson(json, NotesEntity.class);
                if ((todoDate != null) && (!todoDate.equalsIgnoreCase("null")) && (!todoDate.equalsIgnoreCase(""))) {
                    Date dateconn = formatter.parse(todoDate);
                    note.setTodoDate(dateconn);                      //kanw set  thn nea hmeromhnia
                }
                note.setSubmitDate(new Date());
                JPA.em().persist(note);
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
    public Result deleteNote() {


        System.out.println("hey");

        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {


                System.out.println(json);

                int id = json.findPath("id").asInt();
                if (id == 0) {
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής Διαγραφή.");
                    return ok(result);
                } else {
                    NotesEntity c = JPA.em().find(NotesEntity.class, id);
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
