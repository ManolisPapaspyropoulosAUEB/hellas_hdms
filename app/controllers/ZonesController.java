package controllers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ZonesEntity;
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

/**
 * created by mpapaspyropoulos
 */

@SuppressWarnings("Duplicates")

public class ZonesController {

    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result createZone() {
        try {
            JsonNode json = request().body().asJson(); //
            ObjectNode result = Json.newObject();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                ZonesEntity med = Json.fromJson(json, ZonesEntity.class);
                JPA.em().persist(med);
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
    public Result deleteZone() {
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
                    ZonesEntity c = JPA.em().find(ZonesEntity.class, id);
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


    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateCustomer() {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                ZonesEntity z = Json.fromJson(json, ZonesEntity.class);
                JPA.em().merge(z);
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
    //ayth h methodos ekteleitai ka8e fora pou eisagetai mia parametros
    public Result getZonesByCustomerId() throws IOException {
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
                    Query q = JPA.em().createQuery("SELECT z FROM ZonesEntity z WHERE z.customerId=" + customer_id);
                    List<ZonesEntity> zList = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    boolean draftFlag;
                    boolean customerEnabledFlag;
                    List<HashMap<String, Object>> zones = new ArrayList<HashMap<String, Object>>();
                    for (ZonesEntity j : zList) {
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("editable", j.getEditable());
                        pt.put("name", j.getName());
                        pt.put("idByUser", j.getIdByUser());
                        pt.put("customer_id", j.getCustomerId());
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
            result.put("message", "Πρόβλημα κατά την διαγραφή .");
            return ok(result);
        }
    }
}
