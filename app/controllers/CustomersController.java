package controllers;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import scala.util.parsing.json.JSONArray;
import scala.util.parsing.json.JSONObject;
import javax.persistence.Query;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static play.mvc.Http.Context.Implicit.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

@SuppressWarnings("Duplicates")

public class CustomersController {
    /**
     * created by mpapaspyropoulos
     */
    private java.util.Date modifiedTimestamp;

    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result insertCustomer() {
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
                String customerConnectionDate = json.findPath("customerConnectionDate").asText();
                String creationDate = json.findPath("creationDate").asText();
                String updateDate = json.findPath("updateDate").asText();
                Long userId = json.findPath("userId").asLong();
                //sbhnw apo to json to datePublished kai to customerConnectionDate
                ((ObjectNode) json).remove("datePublished");
                ((ObjectNode) json).remove("customerConnectionDate");
                ((ObjectNode) json).remove("updateDate");
                ((ObjectNode) json).remove("creationDate");
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
                CustomersEntity pat = Json.fromJson(json, CustomersEntity.class);    //ftoiaxnw antikeimeno pelath xwris thn hmeromhnia
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                DateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                DateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa

                if (datePublished != "" && datePublished != null && (!datePublished.equalsIgnoreCase(""))) {
                    Date datepubl = formatter.parse(datePublished.trim());                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setDatePublished(datepubl);                      //kanw set ston pelath thn nea hmeromhnia
                }
                if (customerConnectionDate != "" && customerConnectionDate != null && (!customerConnectionDate.equalsIgnoreCase(""))) {
                    Date dateconn = formatter2.parse(customerConnectionDate.trim());                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setCustomerConnectionDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                }
                if (creationDate != "" && creationDate != null && (!creationDate.equalsIgnoreCase(""))) {
                    Date dateconn = formatter3.parse(creationDate.trim());                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setCreationDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                }
                if (updateDate != "" && updateDate != null && (!updateDate.equalsIgnoreCase(""))) {
                    Date dateconn = formatter4.parse(updateDate.trim());                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setUpdateDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                }
                JPA.em().persist(pat);                             //persist sthn bash
                Query q = JPA.em().createQuery("SELECT c FROM CustomersEntity c order by id desc");
                List<CustomersEntity> custList = q.getResultList();
                id = custList.get(0).getId();

                Query q2 = JPA.em().createQuery("SELECT c FROM CustomersEntity c WHERE c.userId=" + userId);
                List<CustomersEntity> custList2 = q.getResultList();

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










    //todo:set enabled
    @Transactional
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @BodyParser.Of(BodyParser.Json.class)
    public Result setEnabled() {
        try {
            ObjectNode result = Json.newObject();
            JsonNode json = request().body().asJson(); //
            String enabled = json.findPath("enabled").asText();
            ((ObjectNode) json).remove("enabled");//sbhsto
            CustomersEntity c = Json.fromJson(json, CustomersEntity.class);
            if (enabled.equalsIgnoreCase("true")) {
                c.setEnabled((byte) 1);
            } else {
                c.setEnabled((byte) 0);
            }
            JPA.em().merge(c);
            result.put("status", "ok");
            result.put("message", "Η ενημέρωση έγινε με επιτυχία .");
            return ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την ενημέρωση.");
            return ok(result);
        }
    }


    @Transactional
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @BodyParser.Of(BodyParser.Json.class)
    ////PAIrnei ena json se morfh data:[ { stoixeia customer1 },{stoixeia customer2},{stoixeia customer3}  ]
    //    //kanei loop sayto to array kai gia ka8e object analoga me to lastAction enhmerwnei db
    public Result setData() {
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
                        long id = Long.valueOf(0);
                        //krataw thn hmeromhnia opws erxetai apo to json
                        String datePublished = pjson.findPath("datePublished").asText();
                        String lastAction = pjson.findPath("lastAction").asText();
                        String insuredAreaAddress = pjson.findPath("insuredAreaAddress").asText();
                        System.out.println(pjson.findPath("id").longValue());
                        // long customerId = pjson.findPath("id").asLong();
                        long customerId = pjson.findPath("id").asLong();
                        System.out.println("customer id " + customerId);
                        long cid = pjson.findPath("id").asLong();

                        String customerConnectionDate = pjson.findPath("customerConnectionDate").asText();
                        String updateDate = pjson.findPath("updateDate").asText();
                        String companyName = pjson.findPath("companyName").asText();
                        String fax = pjson.findPath("fax").asText();
                        String email = pjson.findPath("email").asText();
                        String creationDate = pjson.findPath("creationDate").asText();
                        Integer userId = pjson.findPath("userId").asInt();
                        //sbhnw apo to json to datePublished kai to customerConnectionDate
                        ((ObjectNode) pjson).remove("datePublished");
                        ((ObjectNode) pjson).remove("customerConnectionDate");
                        ((ObjectNode) pjson).remove("updateDate");
                        ((ObjectNode) pjson).remove("creationDate");
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
                        CustomersEntity pat = Json.fromJson(pjson, CustomersEntity.class);    //ftoiaxnw antikeimeno pelath xwris thn hmeromhnia
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa

                        if ((!datePublished.equalsIgnoreCase("null")) && (!datePublished.equalsIgnoreCase(""))) {
                            Date datepubl = formatter.parse(datePublished);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                            pat.setDatePublished(datepubl);                      //kanw set ston pelath thn nea hmeromhnia
                        }
                        if ((!customerConnectionDate.equalsIgnoreCase("null")) && (!customerConnectionDate.equalsIgnoreCase(""))) {
                            Date dateconn = formatter2.parse(customerConnectionDate);                                   //thn kanw Date ,,,String ->java util date ->timestamp
                            pat.setCustomerConnectionDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                        }
                        if ((!updateDate.equalsIgnoreCase("null")) && (!updateDate.equalsIgnoreCase(" "))) {
                            Date dateconn = formatter.parse(updateDate);
                            pat.setUpdateDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                        }
                        if (lastAction.equalsIgnoreCase("I")) {
                            pat.setCreationDate(new Date());
                        }else{
                            //creationDate
                           // pat.setCreationDate(new Date());
                            Date dateconn = formatter.parse(creationDate);
                            pat.setCreationDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                        }
                        if ((!creationDate.equalsIgnoreCase("null")) && (!creationDate.equalsIgnoreCase(""))) {
                            Date dateconn = formatter.parse(creationDate);
                            //pat.setCreationDate(dateconn);
                            if (lastAction.equalsIgnoreCase("I")) {
                                pat.setCreationDate(new Date());
                            }
                        }
                        RevisionsEntity rev = new RevisionsEntity();
                        CustomersCcEntity cc = new CustomersCcEntity();
                        if (lastAction.equalsIgnoreCase("I")) {
                            Query qCust = JPA.em().createQuery("SELECT c FROM CustomersEntity c ");
                            List<CustomersEntity> cList = qCust.getResultList();
                            for (CustomersEntity it : cList) {
                                if (it.getId() == pjson.findPath("id").longValue()) {
                                    return badRequest("Ο πελάτης που προσπαθείτε να καταχωρήσετε υπάρχει είδη");
                                }
                            }
                            pat.setCreationDate(new Date());
                            JPA.em().persist(pat);                             //persist sthn bash
                            if (pat.getDraft() == 0) {  //an den einai pleon draft tote upobalete kai epomenws prow8eitai kai sto kentro elegxou
                                //CustomersEntity
                                JsonNode pjsonRevision = pjson;
                                ((ObjectNode) pjson).remove("id"); //sbhse to id einai auto increment
                                ((ObjectNode) pjson).put("customerId", customerId);//bale sto customer id to id tou customer
                                ((ObjectNode) pjson).remove("revisionDate"); //sbhsto
                                ((ObjectNode) pjson).remove("creationDate");//sbhsto
                                ((ObjectNode) pjson).remove("updateDate");//sbhsto
                                ((ObjectNode) pjson).remove("customerConnectionDate");//sbhsto
                                ((ObjectNode) pjson).remove("datePublished");//sbhsto
                                ((ObjectNode) pjson).remove("revisions");//sbhsto
                                rev = Json.fromJson(pjson, RevisionsEntity.class);
                                if (insuredAreaAddress != null) {
                                    rev.setInsuredAreaAdress(insuredAreaAddress);
                                }
                                if (pat.getCreationDate() != null) {
                                    rev.setRevisionDate(pat.getCreationDate());
                                }
                                if (pat.getCreationDate() != null) {
                                    rev.setCreationDate(pat.getCreationDate());
                                }
                                if (pat.getUpdateDate() != null) {
                                    rev.setUpdateDate(pat.getUpdateDate());
                                }
                                if (pat.getCustomerConnectionDate() != null) {
                                    rev.setCustomerConnectionDate(pat.getCustomerConnectionDate());
                                }
                                if (pat.getDatePublished() != null) {
                                    rev.setDatePublished(pat.getDatePublished());
                                }
                                if (companyName != null) {
                                    rev.setCompanyName(companyName);
                                }
                                if (fax != null) {
                                    rev.setFax(fax);
                                }
                                if (email != null) {
                                    rev.setFax(email);
                                }
                                rev.setRevisions(1);
                                rev.setAfm(pat.getAfm());
                                JPA.em().persist(rev);
                                //-----------*******************************************************************************************************************
                                CustomersCcEntity ccc = new CustomersCcEntity();
                                cc = assignmentCustomerToCustomerCc(pat);
                                ObjectMapper mapper = new ObjectMapper();
                                JsonNode nodecc = mapper.convertValue(cc, JsonNode.class);
                                ((ObjectNode) nodecc).remove("id");
                                ccc = cc;
                                ccc.setCompanyName(companyName);

                                ccc.setFax(fax);
                                ccc.setCompanyName(companyName);
                                ccc.setCompanyName(companyName);
                                ccc.setRevision(rev.getRevisions());
                                ccc.setStatus("OPEN");
                                ccc.setRevisionId(rev.getId());
                                JPA.em().persist(ccc);
                                //-----------*******************************************************************************************************************
                            }
                            result.put("status", "ok");
                            result.put("message", "H εκχώρηση νέου πελάτη έγινε με επιτιχία.");
                        } else if (lastAction.equalsIgnoreCase("U")) {
                            Query qCust = JPA.em().createQuery("SELECT c FROM CustomersEntity c ");
                            List<CustomersEntity> cList = qCust.getResultList();
                            int count = 0;
                            for (CustomersEntity it : cList) {
                                if (it.getId() == pjson.findPath("id").longValue()) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                return badRequest("Προσπαθείτε να ενημερώσετε έναν πελάτη ο οποίος δεν είναι καταχωρημένος");
                            }
                            JPA.em().merge(pat);
                            //TODO:INSERT STON PINAKA REVISION ME NEA EKDOSH REVISIONS +1 kai draft==true
                            if (pat.getDraft() == 0) {
                                JsonNode pjsonRevision = pjson;
                                ((ObjectNode) pjson).remove("id"); //sbhse to id einai auto increment
                                ((ObjectNode) pjson).put("customerId", customerId);//bale sto customer id to id tou customer
                                ((ObjectNode) pjson).remove("revisionDate"); //sbhsto
                                ((ObjectNode) pjson).remove("creationDate");//sbhsto
                                ((ObjectNode) pjson).remove("updateDate");//sbhsto
                                ((ObjectNode) pjson).remove("customerConnectionDate");//sbhsto
                                ((ObjectNode) pjson).remove("datePublished");//sbhsto
                                ((ObjectNode) pjson).remove("revisions");//sbhsto
                                rev = Json.fromJson(pjson, RevisionsEntity.class);
                                if (pat.getUpdateDate() != null) {
                                    rev.setRevisionDate(new Date());
                                }
                                if (pat.getCreationDate() != null) {
                                    rev.setCreationDate(pat.getCreationDate());
                                }
                                if (pat.getUpdateDate() != null) {
                                    rev.setUpdateDate(pat.getUpdateDate());
                                }
                                if (pat.getCustomerConnectionDate() != null) {
                                    rev.setCustomerConnectionDate(pat.getCustomerConnectionDate());
                                }
                                if (pat.getDatePublished() != null) {
                                    rev.setDatePublished(pat.getDatePublished());
                                }
                                Integer maxRevC = (Integer) JPA.em()
                                        .createNativeQuery("SELECT MAX(revisions.revisions) FROM revisions  WHERE revisions.customer_id='" + customerId + "'").getSingleResult();
                                if (maxRevC == null) {
                                    maxRevC = 0;
                                }
                                rev.setRevisions(maxRevC + 1);
                                if (insuredAreaAddress != null) {
                                    rev.setInsuredAreaAdress(insuredAreaAddress);
                                }
                                if (companyName != null) {
                                    rev.setCompanyName(companyName);
                                }

                                if (fax != null) {
                                    rev.setFax(fax);
                                }


                                rev.setAfm(pat.getAfm());

                                JPA.em().persist(rev);
                                //todo:: fix
                                //afou upoblh8hke kai perase sta revision me ekdosh ,tote to pername kai ston cc
                                // CustomersCcEntity cc = new CustomersCcEntity();
                                cc = assignmentCustomerToCustomerCc(pat);
                                cc.setRevisionId(rev.getId());
                                //hmeromhnia opou upoblh8hke
                                cc.setCreationCcDate(new Date());
//-----------***************************************************************************************************************************************************************************
                                //apo thn stigmh pou upoblh8hke tha parei status OPEN
                                ObjectMapper mapper = new ObjectMapper();
                                JsonNode nodecc = mapper.convertValue(cc, JsonNode.class);
                                ((ObjectNode) nodecc).remove("id");
                                CustomersCcEntity ccc = Json.fromJson(nodecc, CustomersCcEntity.class);
                                ccc = cc;
                                ccc.setRevision(rev.getRevisions());
                                ccc.setFax(rev.getFax());
                                ccc.setStatus("OPEN");
                                ccc.setRevisionId(rev.getId());
                                JPA.em().persist(ccc);
                                //JPA.em().persist(cc);
                            }
//-----------*********************************************************************************************************************************************************
                            result.put("status", "ok");
                            result.put("message", "H ενημέρωση του πελάτη έγινε με επιτιχία.");
                        } else if (lastAction.equalsIgnoreCase("D")) {
                            if (pat.getDraft() == 1) {
                                //an einai last action delete kai DRAFT tote:
                                //antikatesthse thn eggrafh me to teleutaio revision
                                //fernw to teleutaio revision
                                String qR="SELECT MAX(revisions.revisions) FROM revisions  WHERE revisions.customer_id='" + customerId + "'";
                                Integer maxR = (Integer) JPA.em()
                                        .createNativeQuery(qR).getSingleResult();
                                if (maxR == null) {
                                    maxR = 0;
                                }
                                Query q = JPA.em().createQuery("SELECT b FROM RevisionsEntity b WHERE customerId= " + customerId + "AND b.revisions=" + maxR);
                                List<RevisionsEntity> lastVersionRevList = q.getResultList();
                                RevisionsEntity r = new RevisionsEntity();
                                CustomersEntity c = new CustomersEntity();
                                emptyCustomerTables(customerId);
                                if (lastVersionRevList.size() > 0) {
                                    emptyCustomerTables(customerId);
                                    r = lastVersionRevList.get(0);
                                    c = assignment(r);
                                    c.setUpdateDate(new Date());
                                    c.setVisible((byte) 1);
                                    JPA.em().persist(c);
                                }

                            } else {
                                Query qCust = JPA.em().createQuery("SELECT c FROM CustomersEntity c ");
                                List<CustomersEntity> cList = qCust.getResultList();
                                int count = 0;
                                for (CustomersEntity it : cList) {
                                    if (it.getId() == pjson.findPath("id").longValue()) {
                                        count++;
                                    }
                                }
                                if (count == 0) {
                                    return badRequest("Προσπαθείτε να ενημερώσετε έναν πελάτη ο οποίος δεν είναι καταχωρημένος");
                                }
                                pat.setVisible((byte) 0);
                                JPA.em().merge(pat);
                            }
                            result.put("status", "ok");
                            result.put("message", "Η ενημέρωση της διαγραφής του πελάτη έγινε με επιτυχία.");
                        } else {
                            return badRequest("Παρακαλώ καταχωρήστε ενέργεια (I για insert,U για update,D για delete)-->lastAction()");
                        }
                        Query q = JPA.em().createQuery("SELECT c FROM CustomersEntity c order by id desc");
                        List<CustomersEntity> custList = q.getResultList();
                        if (custList.size() > 0) {
                            id = custList.get(0).getId();
                        }
                        //   result.put("id", id);
                        Query q2 = JPA.em().createQuery("SELECT c FROM CustomersEntity c WHERE c.userId=" + userId);
                        List<CustomersEntity> custList2 = q.getResultList();
                        if (jsonAlarmUsers != null) {
                            //an einai last action delete kai draft tote kai monon tote antikatesthse ta details me to teleytaio tous revision(max revision)
                            if (lastAction.equalsIgnoreCase("D")) {
                                if (pat.getDraft() == 1) {
                                    //sthn grammh 537 kalesame thn emptyCustomerTables opote
                                    //o pinakas pleon twn alarm users einai adeios!
                                    //hrthe h wra na ton antikatasthsw me to teleutaio revision
                                    //3ekinaw mia loop sta revision details tou (alarmUsersRevisions) gia ton skopo ayto
                                    Integer maxR = (Integer) JPA.em()
                                            .createNativeQuery("SELECT MAX(alarm_users_revisions.revision) FROM alarm_users_revisions  WHERE alarm_users_revisions.customer_id='" + customerId + "'").getSingleResult();
                                    if (maxR == null) {
                                        maxR = 0;
                                    }
                                    Query qAL = JPA.em().createQuery("SELECT b FROM AlarmUsersRevisionsEntity b WHERE customerId= " + customerId + "AND b.revision=" + maxR);
                                    List<AlarmUsersRevisionsEntity> lastVersionAlRevList = qAL.getResultList();
                                    for (int i = 0; i < lastVersionAlRevList.size(); i++) {
                                        AlarmUsersRevisionsEntity ar = new AlarmUsersRevisionsEntity();
                                        AlarmUsersEntity a = new AlarmUsersEntity();
                                        if (lastVersionAlRevList.size() > 0) {
                                            ar = lastVersionAlRevList.get(i);
                                            a = assignmentToAlarmUser(ar);
                                            JPA.em().persist(a);
                                        }
                                    }
                                }
                            } else {
                                // if((la.equalsIgnoreCase("I"))||(la.equalsIgnoreCase("U")) || (la.equalsIgnoreCase("D"))){
                                //adeiasma pinaka AlarmsUsers kai AlarmsUsersCc{
                                //adeiasma pinaka AlarmsUsers
                                Query allAlusrs = JPA.em().createQuery("SELECT a FROM AlarmUsersEntity a where a.customerId=" + customerId);
                                List<AlarmUsersEntity> allAlusrslist = allAlusrs.getResultList();
                                for (int p = 0; p < allAlusrslist.size(); p++) {
                                    JPA.em().remove(allAlusrslist.get(p));
                                }
                                //ok afou tous adeiasa hrthe h wra na ta 3anaperasw me ta nea ananeomena dedomena kai stous Alarms kai stous AlarmsCc
                                //fernw to prohgoumeno revision kai to ay3anw +1 (nea ekdosh)
                                Integer maxRev = (Integer) JPA.em().
                                        createNativeQuery("SELECT  MAX(alarm_users_revisions.revision) " +
                                                "FROM alarm_users_revisions " +
                                                " WHERE alarm_users_revisions.customer_id='" + customerId + "'").getSingleResult();
                                if (maxRev == null) {
                                    maxRev = 1;
                                } else {
                                    maxRev++;
                                }
                                //kanw loop mesa sto json pou afora tous alarmUsers kai ta pernaw Stous pinakes Alarms kai AlarmsCc(stous cc efoson einai draft==0 kai oxi delete)
                                for (int i = 0; i < jsonAlarmUsers.size(); i++) {
                                    JsonNode ajson = jsonAlarmUsers.get(i);
                                    ((ObjectNode) ajson).remove("id");
                                    String editable = ajson.findPath("editable").asText();
                                    String vis = ajson.findPath("visible").asText();
                                    ((ObjectNode) ajson).remove("editable");
                                    ((ObjectNode) ajson).remove("visible");
                                    AlarmUsersEntity au = Json.fromJson(ajson, AlarmUsersEntity.class);
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
                                    //TODO:FIX THIS -- AN EINAI lastAction.equalsIgnoreCase("D") tote tha to pername ston cc kai sta revision ?
                                    if (pat.getDraft() == 0) {  //&& !lastAction.equalsIgnoreCase("D") ???
                                        //MPES EDW ,KRATA REVISION (ARA EINAI UPOBEBLHMENO) KAI ARA ENHMERWSE KAI TON CC
                                        ((ObjectNode) ajson).put("customerId", customerId);//bale sto customer id to id tou customer
                                        AlarmUsersRevisionsEntity aurevE = Json.fromJson(ajson, AlarmUsersRevisionsEntity.class);
                                        aurevE.setCustomerId(customerId);
                                        aurevE.setVisible(au.getVisible());
                                        aurevE.setRevisionDate(pat.getUpdateDate());
                                        aurevE.setAlarmUserId(au.getId());
                                        aurevE.setRevisionId(rev.getId());
                                        aurevE.setRevision(maxRev);
                                        aurevE.setEditable((byte) 0);
                                        JPA.em().persist(aurevE);
                                        //an den einai draft (tote o pinakas einai adeios afou mphke sto condition row:560) tote perna thn eggrafh ston AlarmCc
                                        AlarmUsersCcEntity acc = new AlarmUsersCcEntity();
                                        acc = assignmentToAlarmUserCc(au);
                                        //---------------------------------*********************************************************************--------------------------------
                                        ObjectMapper mapper = new ObjectMapper();
                                        JsonNode node_pcc = mapper.convertValue(acc, JsonNode.class);
                                        ((ObjectNode) node_pcc).remove("id");
                                        acc.setRevision(aurevE.getRevision());
                                        // acc.setRevisionId(aurevE.getRevisionId());
                                        acc.setRevisionId(cc.getId()); //bale sto revision id to id ths grammhs tou cc gia na ta deseis meta3u tous
//----------------------------------**********************************************************************----------------------------------
                                        JPA.em().persist(acc);
                                    }
                                }
                            }
                        }
                        if (jsonZones != null) {
                            if (lastAction.equalsIgnoreCase("D")) {
                                if (pat.getDraft() == 1) {
                                    Integer maxR = (Integer) JPA.em()
                                            .createNativeQuery("SELECT MAX(zones_revisions.revision) FROM zones_revisions  WHERE zones_revisions.customer_id='" + customerId + "'").getSingleResult();
                                    if (maxR == null) {
                                        maxR = 0;
                                    }
                                    Query qAL = JPA.em().createQuery("SELECT b FROM ZonesRevisionsEntity b WHERE customerId= " + customerId + "AND b.revision=" + maxR);
                                    List<ZonesRevisionsEntity> lastVersionZRevList = qAL.getResultList();
                                    for (int i = 0; i < lastVersionZRevList.size(); i++) {
                                        ZonesRevisionsEntity zr = new ZonesRevisionsEntity();
                                        ZonesEntity z = new ZonesEntity();
                                        if (lastVersionZRevList.size() > 0) {
                                            zr = lastVersionZRevList.get(i);
                                            z = assignmentToZones(zr);
                                            JPA.em().persist(z);
                                        }
                                    }
                                }
                            } else {
                                String la = jsonZones.findPath("lastAction").asText();
                                //adeiasma pinaka
                                Query qzone = JPA.em().createQuery("SELECT a FROM ZonesEntity a where a.customerId=" + customerId);
                                List<ZonesEntity> zolist = qzone.getResultList();
                                for (int p = 0; p < zolist.size(); p++) {
                                    JPA.em().remove(zolist.get(p));
                                }
                                Integer maxRev = (Integer) JPA.em().createNativeQuery("SELECT  MAX(zones_revisions.revision)" +
                                        " FROM zones_revisions " +
                                        " WHERE zones_revisions.customer_id='" + customerId + "'").getSingleResult();
                                if (maxRev == null) {
                                    maxRev = 1;
                                } else {
                                    maxRev++;
                                }
                                for (int i = 0; i < jsonZones.size(); i++) {
                                    JsonNode ajson = jsonZones.get(i);

                                    String editable = ajson.findPath("editable").asText();
                                    String vsi = ajson.findPath("visible").asText();
                                    ((ObjectNode) ajson).remove("editable");
                                    ((ObjectNode) ajson).remove("customerId");
                                    ((ObjectNode) ajson).remove("zoneId");

                                    ((ObjectNode) ajson).remove("visible");
                                    ZonesEntity au = Json.fromJson(ajson, ZonesEntity.class);
                                    au.setCustomerId(customerId);
                                    if (editable != null && !editable.equalsIgnoreCase("")) {
                                        if (editable.equalsIgnoreCase("true")) {
                                            au.setEditable((byte) 1);
                                        } else {
                                            au.setEditable((byte) 0);
                                        }
                                    }
                                    if (visible != null && !visible.equalsIgnoreCase("")) {
                                        if (vsi.equalsIgnoreCase("true")) {
                                            au.setVisible((byte) 1);
                                        } else {
                                            au.setVisible((byte) 0);
                                        }
                                    }
                                    JPA.em().persist(au);
                                    if (pat.getDraft() == 0) {
                                        ((ObjectNode) ajson).put("customerId", customerId);//bale sto customer id to id tou customer
                                        ZonesRevisionsEntity aurevE = Json.fromJson(ajson, ZonesRevisionsEntity.class);
                                        aurevE.setCustomerId(customerId);
                                        aurevE.setZoneId(au.getZoneId());
                                        aurevE.setIdByUser(au.getIdByUser());
                                        aurevE.setRevisionId(rev.getId());
                                        aurevE.setRevisionDate(pat.getUpdateDate());
                                        aurevE.setRevision(maxRev);
                                        aurevE.setEditable((byte) 0);
                                        aurevE.setVisible(au.getVisible());
                                        JPA.em().persist(aurevE);
                                        ZonesCcEntity zcc = new ZonesCcEntity();
                                        zcc = assignmentToZonesCc(au);
//----------**********************************************************************************************************************************************
                                        ObjectMapper mapper = new ObjectMapper();
                                        JsonNode node_pcc = mapper.convertValue(zcc, JsonNode.class);
                                        ((ObjectNode) node_pcc).remove("id");
                                        zcc.setRevision(aurevE.getRevision());
                                        zcc.setRevisionId(cc.getId());
//----------**********************************************************************************************************************************************
                                        JPA.em().persist(zcc);
                                    }
                                }
                            }
                        }
                        if (jsonPhoneNotices != null) {
                            System.out.println("       jsonPhoneNotices      ");
                            System.out.println(jsonPhoneNotices);
                            if (lastAction.equalsIgnoreCase("D")) {
                                if (pat.getDraft() == 1) {
                                    Integer maxR = (Integer) JPA.em()
                                            .createNativeQuery("SELECT MAX(phone_notices_revisions.revision) FROM phone_notices_revisions  WHERE phone_notices_revisions.customer_id='" + customerId + "'").getSingleResult();
                                    if (maxR == null) {
                                        maxR = 0;
                                    }
                                    Query qAL = JPA.em().createQuery("SELECT b FROM PhoneNoticesRevisionsEntity b WHERE customerId= " + customerId + "AND b.revision=" + maxR);
                                    List<PhoneNoticesRevisionsEntity> lastVersionPRevList = qAL.getResultList();
                                    for (int i = 0; i < lastVersionPRevList.size(); i++) {
                                        PhoneNoticesRevisionsEntity pr = new PhoneNoticesRevisionsEntity();
                                        PhoneNoticesEntity p = new PhoneNoticesEntity();
                                        if (lastVersionPRevList.size() > 0) {
                                            pr = lastVersionPRevList.get(i);
                                            p = assignmentToPhoneNotices(pr);
                                            JPA.em().persist(p);
                                        }
                                    }
                                }
                            } else {
                                Query allAlusrs = JPA.em().createQuery("SELECT a FROM PhoneNoticesEntity a where a.customerId=" + customerId);
                                List<PhoneNoticesEntity> allAlusrslist = allAlusrs.getResultList();
                                for (int p = 0; p < allAlusrslist.size(); p++) {
                                    JPA.em().remove(allAlusrslist.get(p));
                                }
                                Integer maxRev = (Integer) JPA.em().
                                        createNativeQuery("SELECT  MAX(phone_notices_revisions.revision) " +
                                                "FROM phone_notices_revisions " +
                                                " WHERE phone_notices_revisions.customer_id='" + customerId + "'")
                                        .getSingleResult();
                                if (maxRev == null) {
                                    maxRev = 1;
                                } else {
                                    maxRev++;
                                }
                                for (int i = 0; i < jsonPhoneNotices.size(); i++) {
                                    JsonNode ajson = jsonPhoneNotices.get(i);
                                    ((ObjectNode) ajson).remove("id");
                                    String editable = ajson.findPath("editable").asText();
                                    String vis = ajson.findPath("visible").asText();
                                    ((ObjectNode) ajson).remove("editable");
                                    ((ObjectNode) ajson).remove("visible");
                                    PhoneNoticesEntity au = Json.fromJson(ajson, PhoneNoticesEntity.class);
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
                                    if (pat.getDraft() == 0) {
                                        ((ObjectNode) ajson).put("customerId", customerId);//bale sto customer id to id tou customer
                                        ((ObjectNode) ajson).remove("visible");
                                        PhoneNoticesRevisionsEntity aurevE = Json.fromJson(ajson, PhoneNoticesRevisionsEntity.class);
                                        aurevE.setEditable(au.getEditable());
                                        aurevE.setVisible(au.getVisible());
                                        aurevE.setCustomerId(customerId);
                                        aurevE.setCustomerId(customerId);
                                        aurevE.setPhoneId(au.getId());
                                        aurevE.setRevisionId(rev.getId());
                                        aurevE.setRevisionDate(pat.getUpdateDate());
                                        aurevE.setRevision(maxRev);
                                        aurevE.setEditable((byte) 0);
                                        JPA.em().persist(aurevE);
                                        PhoneNoticesCcEntity pcc = new PhoneNoticesCcEntity();
                                        pcc = assignmentToPhoneNoticesCc(au);
                                        //---------------------------------*********************************************************************--------------------------------
                                        ObjectMapper mapper = new ObjectMapper();
                                        JsonNode node_pcc = mapper.convertValue(pcc, JsonNode.class);
                                        ((ObjectNode) node_pcc).remove("id");
                                        pcc.setRevision(aurevE.getRevision());
                                        pcc.setRevisionId(cc.getId());

                                        //---------------------------------*********************************************************************--------------------------------
                                        JPA.em().persist(pcc);
                                    }
                                }//
                            }
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


    private AlarmUsersCcEntity assignmentToAlarmUserCc(AlarmUsersEntity a) {
        AlarmUsersCcEntity acc = new AlarmUsersCcEntity();
        acc.setAlarmUserId(a.getId());
        acc.setCustomerId(a.getCustomerId());
        acc.setEditable(a.getEditable());
        acc.setName(a.getName());
        acc.setUsername(a.getUsername());
        acc.setVisible(a.getVisible());
        return acc;
    }

    private ZonesCcEntity assignmentToZonesCc(ZonesEntity z) {
        ZonesCcEntity zcc = new ZonesCcEntity();
        zcc.setZoneId(z.getZoneId());
        zcc.setCustomerId(z.getCustomerId());
        zcc.setEditable(z.getEditable());
        zcc.setName(z.getName());
        zcc.setVisible(z.getVisible());
        zcc.setIdByUser(z.getIdByUser());
        return zcc;
    }

    private PhoneNoticesCcEntity assignmentToPhoneNoticesCc(PhoneNoticesEntity p) {
        PhoneNoticesCcEntity pcc = new PhoneNoticesCcEntity();
        pcc.setPhoneNoticesId(p.getId());
        pcc.setCustomerId(p.getCustomerId());
        pcc.setEditable(p.getEditable());
        pcc.setName(p.getName());
        pcc.setUsername(p.getUsername());
        pcc.setVisible(p.getVisible());
        pcc.setPhone(p.getPhone());
        return pcc;

    }

    private AlarmUsersEntity assignmentToAlarmUser(AlarmUsersRevisionsEntity ar) {
        AlarmUsersEntity a = new AlarmUsersEntity();
        a.setCustomerId(ar.getCustomerId());
        a.setEditable(ar.getEditable());
        a.setName(ar.getName());
        a.setUsername(ar.getUsername());
        a.setVisible(ar.getVisible());
        return a;
    }

    private ZonesEntity assignmentToZones(ZonesRevisionsEntity zr) {
        ZonesEntity z = new ZonesEntity();
        z.setEditable(zr.getEditable());
        z.setCustomerId(zr.getCustomerId());
        z.setName(zr.getName());
        z.setIdByUser(zr.getIdByUser());
        z.setVisible(zr.getVisible());
        return z;
    }

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

    //TODO FIX: ADD AFM COLUMN TO REVISION TABLE (closed)
    private CustomersCcEntity assignmentCustomerToCustomerCc(CustomersEntity c) {
        CustomersCcEntity cc = new CustomersCcEntity();


        cc.setService247Monitoring(c.getService247Monitoring());
        cc.setServiceAccess(c.getServiceAccess());
        cc.setServiceCctv(c.getServiceCctv());
        cc.setServiceCommunication(c.getServiceCommunication());
        cc.setServiceGlobalSim(c.getServiceGlobalSim());
        cc.setServiceReport(c.getServiceReport());
        cc.setServiceTest(c.getServiceTest());
        cc.setSevriceMonitoring(c.getSevriceMonitoring());


        cc.setCustomerId(c.getId());
        cc.setCompanyName(c.getCompanyName());
        cc.setAfm(c.getAfm());
        cc.setAfm(c.getFax());
        cc.setAfm(c.getEmail());
        cc.setUserId(c.getUserId());
        cc.setAlarmUnitType(c.getAlarmUnitType());
        cc.setAreaPhone(c.getAreaPhone());
        cc.setCustomerAuxiliaryPass(c.getCustomerAuxiliaryPass());
        cc.setCustomerConnectionDate(c.getCustomerConnectionDate());
        cc.setCustomerPass(c.getCustomerPass());
        cc.setDatePublished(c.getDatePublished());
        cc.setDirectTransmissionPhones(c.getDirectTransmissionPhones());
        cc.setDraft(c.getDraft());
        cc.setDuressCode(c.getDuressCode());
        cc.setEnabled(c.getEnabled());
        cc.setFormat(c.getFormat());
        cc.setFrequency24HourTest(c.getFrequency24HourTest());
        cc.setInsuredAreaAddress(c.getInsuredAreaAddress());
        cc.setInsuredAreaCity(c.getInsuredAreaCity());
        cc.setInsuredAreaDescription(c.getInsuredAreaDescription());
        cc.setInsuredAreaFloor(c.getInsuredAreaFloor());
        cc.setInsuredAreaPostCode(c.getInsuredAreaPostCode());
        cc.setInsuredAreaType(c.getInsuredAreaType());
        cc.setInsuredAreaTypeOther(c.getInsuredAreaTypeOther());
        cc.setMonthlyAlarmList(c.getMonthlyAlarmList());
        cc.setOperationControllHours(c.getOperationControllHours());
        cc.setOtherRemarks(c.getOtherRemarks());
        cc.setPoliceStation(c.getPoliceStation());
        cc.setSubscriberName(c.getSubscriberName());
        cc.setWeeklyTimeMonitoring(c.getWeeklyTimeMonitoring());
        cc.setDevice(c.getDevice());
        cc.setCreationDate(c.getCreationDate());
        cc.setUpdateDate(c.getUpdateDate());
        cc.setLastAction(c.getLastAction());
        cc.setAfm(c.getAfm());
        cc.setVisible(c.getVisible());
        return cc;
    }

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



    @SuppressWarnings("Duplicates")

    @play.db.jpa.Transactional

    @BodyParser.Of(BodyParser.Json.class)
    public Result deleteCustomers() {
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
                    CustomersEntity c = JPA.em().find(CustomersEntity.class, id);
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
    public Result updateCustomer() {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                long id = Long.valueOf(0);
                //krataw thn hmeromhnia opws erxetai apo to json
                String datePublished = json.findPath("datePublished").asText();
                String customerConnectionDate = json.findPath("customerConnectionDate").asText();
                //sbhnw apo to json to datePublished kai to customerConnectionDate
                ((ObjectNode) json).remove("datePublished");
                ((ObjectNode) json).remove("customerConnectionDate");
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
                CustomersEntity pat = Json.fromJson(json, CustomersEntity.class);    //ftoiaxnw antikeimeno pelath xwris thn hmeromhnia
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");   //tropooiw katalhla thn hmeromhnia afou prohgoumenws thn eswsa
                if (datePublished != "" && datePublished != null && (!datePublished.equalsIgnoreCase(""))) {
                    Date datepubl = formatter.parse(datePublished.trim());                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setDatePublished(datepubl);                      //kanw set ston pelath thn nea hmeromhnia
                }
                if (customerConnectionDate != "" && customerConnectionDate != null && (!customerConnectionDate.equalsIgnoreCase(""))) {
                    Date dateconn = formatter2.parse(customerConnectionDate.trim());                                   //thn kanw Date ,,,String ->java util date ->timestamp
                    pat.setCustomerConnectionDate(dateconn);                      //kanw set ston pelath thn nea hmeromhnia
                }
                JPA.em().merge(pat);
                Query q = JPA.em().createQuery("SELECT c FROM CustomersEntity c order by id desc");
                List<CustomersEntity> custList = q.getResultList();
                id = custList.get(0).getId();
                result.put("id", id);
                result.put("status", "ok");
                result.put("message", "Η καταχώρηση έγινε με επιτυχία.");
                return ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την ενημέρωση .");
            return ok(result);
        }
    }

    @SuppressWarnings("Duplicates")

    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result getCustomers() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Long userId = json.findPath("userId").asLong();

                if (userId == null || userId == 0) {
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής.");
                    return ok(result);
                } else {
                    Query q = JPA.em().createQuery("SELECT c FROM CustomersEntity c WHERE c.userId=" + userId);
                    List<CustomersEntity> custList = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    List<HashMap<String, Object>> cust = new ArrayList<HashMap<String, Object>>();
                    for (CustomersEntity j : custList) {
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("id", j.getId());
                        pt.put("userId", j.getUserId());
                        pt.put("alarmUnitType", j.getAlarmUnitType());
                        pt.put("areaPhone", j.getAreaPhone());
                        pt.put("customerAuxiliaryPass", j.getCustomerAuxiliaryPass());
                        pt.put("customerConnectionDate", j.getCustomerConnectionDate());
                        pt.put("customerPass", j.getCustomerPass());//
                        // pt.put("datePublished", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(j.getDatePublished()))); //
                        pt.put("directTransmissionPhones", j.getDirectTransmissionPhones()); //
                        if (j.getDraft() != null) {
                            if (j.getDraft() == 0) {
                                pt.put("draft", false);

                            } else {
                                pt.put("draft", true);
                            }
                        }
                        if (j.getEnabled() != null) {
                            if (j.getEnabled() == 0) {
                                pt.put("enabled", true);

                            } else {
                                pt.put("enabled", false);
                            }
                        }
                        // java.util.Date temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse("2012-07-10 14:58:00.000000");

                        pt.put("custuomerDuressCode", j.getDuressCode());
                        pt.put("format", j.getFormat());
                        pt.put("frequency24HourTest", j.getFrequency24HourTest().intValue());
                        pt.put("insuredAreaAddress", j.getInsuredAreaAddress());
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
                        pt.put("device", j.getDevice());
                        // pt.put("updateDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(j.getUpdateDate()))); //todo:fix dates like this
                        // pt.put("creationDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(j.getCreationDate())));
                        pt.put("lastAction", j.getLastAction());
                        pt.put("visible", j.getVisible());
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

    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    //ayth h methodos ekteleitai ka8e fora pou eisagetai mia parametros
    public Result getCustomersById() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Long id = json.findPath("id").asLong();
                if (id == null || id == 0) {
                    result.put("status", "error");
                    result.put("message", "Ανεπιτυχής.");
                    return ok(result);
                } else {
                    Query q = JPA.em().createQuery("SELECT c FROM CustomersEntity c WHERE c.id=" + id);
                    List<CustomersEntity> custList = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    boolean draftFlag;
                    boolean customerEnabledFlag;
                    List<HashMap<String, Object>> cust = new ArrayList<HashMap<String, Object>>();
                    for (CustomersEntity j : custList) {
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("id", j.getId());
                        pt.put("userId", j.getUserId());
                        pt.put("alarmUnitType", j.getAlarmUnitType());
                        pt.put("areaPhone", j.getAreaPhone());
                        pt.put("customerAuxiliaryPass", j.getCustomerAuxiliaryPass());
                        pt.put("customerConnectionDate", j.getCustomerConnectionDate());
                        pt.put("customerPass", j.getCustomerPass());
                        pt.put("datePublished", j.getDatePublished());
                        pt.put("directTransmissionPhones", j.getDirectTransmissionPhones());
                        pt.put("draft", j.getDraft());
                        pt.put("duressCode", j.getDuressCode());
                        pt.put("enabled", j.getEnabled());
                        pt.put("format", j.getFormat());
                        pt.put("frequency24HourTest", j.getFrequency24HourTest());
                        pt.put("insuredAreaAddress", j.getInsuredAreaAddress());
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
                        pt.put("lastAction", j.getLastAction());
                        pt.put("device", j.getDevice());
                        pt.put("system", j.getDevice());
                        pt.put("updateDate", j.getUpdateDate());
                        pt.put("afm", j.getAfm());
                        List<PhoneNoticesEntity> phoneNoticiesList = JPA.em()
                                .createQuery("SELECT d FROM PhoneNoticesEntity d WHERE d.customerId=" + id + " ORDER BY d.id")
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
                            p.put("customerId", phoneNoticiesList.get(l).getCustomerId());
                            if (phoneNoticiesList.get(l).getVisible().equals(0)) {
                                p.put("visible", false);
                            } else {
                                p.put("visible", true);
                            }
                            pList.add(p);
                        }
                        List<ZonesEntity> zonesList = JPA.em()
                                .createQuery("SELECT z FROM ZonesEntity z WHERE z.customerId=" + id + " ORDER BY z.id")
                                .getResultList();
                        HashMap<String, Object> z = new HashMap<String, Object>();
                        List<HashMap<String, Object>> zList = new ArrayList<HashMap<String, Object>>();
                        for (int l = 0; l < zonesList.size(); l++) {

                            if (zonesList.get(l).getEditable() != null) {
                                if (zonesList.get(l).getEditable() == 0) {
                                    z.put("editable", false);
                                } else {
                                    z.put("editable", true);
                                }
                            }
                            if (zonesList.get(l).getVisible() != null) {
                                if (zonesList.get(l).getVisible() == 0) {
                                    z.put("visible", false);
                                } else {
                                    z.put("visible", true);
                                }
                            }
                            z.put("name", zonesList.get(l).getName());
                            z.put("customerId", zonesList.get(l).getCustomerId());
                            z.put("zoneId", zonesList.get(l).getZoneId());
                            z.put("idByUser", zonesList.get(l).getIdByUser());
                            zList.add(z);
                        }
                        List<AlarmUsersEntity> AlarmUsersList = JPA.em()
                                .createQuery("SELECT a FROM AlarmUsersEntity a WHERE a.customerId=" + id + " ORDER BY a.id")
                                .getResultList();
                        HashMap<String, Object> a = new HashMap<String, Object>();
                        List<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();
                        for (int l = 0; l < AlarmUsersList.size(); l++) {
                            a.put("id", AlarmUsersList.get(l).getId());
                            a.put("customerId", AlarmUsersList.get(l).getCustomerId());
                            //  a.put("editable",AlarmUsersList.get(l).getEditable());
                            a.put("name", AlarmUsersList.get(l).getName());
                            a.put("username", AlarmUsersList.get(l).getUsername());
                            if (AlarmUsersList.get(l).getEditable() == 0) {  //TODO SUNEXEIA EDW:::::::::::::::::::::::::
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
                        cust.add(pt);
                    }
                    returnList.put("data", cust);
                    returnList.put("status", "ok");

                    returnList.put("total", total.intValue());
                    DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //TODO:DATE :AYTH H METHODOS EYTHINETE GIA TO PWS THA EPISTREPSOUN OI HMEROMHNIES
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
            result.put("message", "Πρόβλημα κατά την διαγραφή .");
            return ok(result);
        }
    }

    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    public Result getData() throws IOException {
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
                    String querySearch = json.findPath("searchField").asText();
                    //Query q = JPA.em().createQuery("SELECT c FROM CustomersEntity c WHERE c.userId=" + user_id);
                    String query = "select * from customers where user_id=" + user_id;
                    if (querySearch != null && !querySearch.equalsIgnoreCase("")) {
                        query += " and (AFM like '%" + querySearch + "%' " +
                                " or  subscriber_name like    '%" + querySearch + "%' " +
                                " or  insured_area_city like    '%" + querySearch + "%'" +
                                " or  area_phone like    '%" + querySearch + "%'"  +")";
                    }


                    //select * from customers where user_id=9861 and   (AFM like '%t%'  or  subscriber_name like    '%t%'  or  insured_area_city like    '%t%' or  area_phone like    '%t%');
                    Query qUsers = JPA.em().createQuery("SELECT u FROM UsersEntity u where u.userId = " + user_id);
                    List<UsersEntity> usList = qUsers.getResultList();
                    if (usList.size() == 1) {
                        if (usList.get(0).getRole().equals("admin")) {
//                            q = JPA.em().createQuery("SELECT c FROM CustomersEntity c");
                            query = "select * from customers where 1";
                            if (querySearch != null && !querySearch.equalsIgnoreCase("")) {
                                query += " and afm like '%" + querySearch + "%' " +
                                        " or  subscriber_name like    '%" + querySearch + "%' " +
                                        " or  insured_area_city like    '%" + querySearch + "%'" +
                                        " or  area_phone like    '%" + querySearch + "%'";
                            }
                        }
                    }

                    System.out.println(query);
                    System.out.println(usList.get(0).getRole());
                    Query q = JPA.em().createNativeQuery(query, CustomersEntity.class);
                    List<CustomersEntity> custList = q.getResultList();
                    ObjectMapper ow = new ObjectMapper();
                    HashMap<String, Object> returnList = new HashMap<String, Object>();
                    String jsonResult = "";
                    Integer total = q.getResultList().size();
                    List<HashMap<String, Object>> cust = new ArrayList<HashMap<String, Object>>();
                    int k = -1;
                    //--------------------------MASTER ------------- (customer details)
                    for (CustomersEntity j : custList) {
                        k++;
                        HashMap<String, Object> pt = new HashMap<String, Object>();
                        pt.put("id", j.getId());
                        pt.put("userId", j.getUserId());
                        pt.put("alarmUnitType", j.getAlarmUnitType());
                        pt.put("areaPhone", j.getAreaPhone());
                        pt.put("customerAuxiliaryPass", j.getCustomerAuxiliaryPass());
                        pt.put("customerConnectionDate", j.getCustomerConnectionDate());
                        pt.put("customerPass", j.getCustomerPass());

                        pt.put("datePublished", j.getDatePublished());
                        pt.put("directTransmissionPhones", j.getDirectTransmissionPhones());
                        pt.put("draft", j.getDraft());
                        pt.put("duressCode", j.getDuressCode());


                        pt.put("companyName", j.getCompanyName());
                        pt.put("fax", j.getFax());
                        pt.put("email", j.getEmail());
                        pt.put("service247Monitoring", j.getService247Monitoring());
                        pt.put("serviceAccess", j.getServiceAccess());
                        pt.put("serviceCctv", j.getServiceCctv());
                        pt.put("serviceCommunication", j.getServiceCommunication());
                        pt.put("serviceGlobalSim", j.getServiceGlobalSim());
                        pt.put("serviceReport", j.getServiceReport());
                        pt.put("serviceTest", j.getServiceTest());
                        pt.put("sevriceMonitoring", j.getSevriceMonitoring());




                        pt.put("enabled", j.getEnabled());
                        pt.put("format", j.getFormat());
                        pt.put("creationDate",j.getCreationDate());
                        pt.put("frequency24HourTest", j.getFrequency24HourTest());
                        pt.put("insuredAreaAddress", j.getInsuredAreaAddress());
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
                        pt.put("lastAction", j.getLastAction());
                        pt.put("device", j.getDevice());
                        pt.put("system", j.getDevice());
                        pt.put("updateDate", j.getUpdateDate());
                        //  pt.put("visible",j.getVisible());
                        pt.put("afm", j.getAfm());
                        List<PhoneNoticesEntity> phoneNoticiesList = JPA.em()
                                .createQuery("SELECT d FROM PhoneNoticesEntity d WHERE d.customerId=" + j.getId() + " ORDER BY d.id")
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
                            p.put("customerId", phoneNoticiesList.get(l).getCustomerId());
                            if (phoneNoticiesList.get(l).getVisible().equals(0)) {
                                p.put("visible", false);
                            } else {
                                p.put("visible", true);
                            }
                            pList.add(p);
                        }
                        List<ZonesEntity> zonesList = JPA.em()
                                .createQuery("SELECT z FROM ZonesEntity z WHERE z.customerId=" + j.getId() + " ORDER BY z.id")
                                .getResultList();
                        HashMap<String, Object> z = new HashMap<String, Object>();
                        List<HashMap<String, Object>> zList = new ArrayList<HashMap<String, Object>>();
                        for (int l = 0; l < zonesList.size(); l++) {

                            if (zonesList.get(l).getEditable() != null) {
                                if (zonesList.get(l).getEditable() == 0) {
                                    z.put("editable", false);
                                } else {
                                    z.put("editable", true);
                                }
                            }
                            if (zonesList.get(l).getVisible() != null) {
                                if (zonesList.get(l).getVisible() == 0) {
                                    z.put("visible", false);
                                } else {
                                    z.put("visible", true);
                                }
                            }
                            z.put("name", zonesList.get(l).getName());
                            z.put("customerId", zonesList.get(l).getCustomerId());
                            z.put("zoneId", zonesList.get(l).getZoneId());
                            z.put("idByUser", zonesList.get(l).getIdByUser());
                            zList.add(z);
                        }
                        List<AlarmUsersEntity> AlarmUsersList = JPA.em().createQuery("SELECT a FROM AlarmUsersEntity a WHERE a.customerId=" + j.getId() + " ORDER BY a.id").getResultList();
                        HashMap<String, Object> a = new HashMap<String, Object>();
                        List<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();
                        for (int l = 0; l < AlarmUsersList.size(); l++) {
                            a.put("id", AlarmUsersList.get(l).getId());
                            a.put("customerId", AlarmUsersList.get(l).getCustomerId());
                            //  a.put("editable",AlarmUsersList.get(l).getEditable());
                            a.put("name", AlarmUsersList.get(l).getName());
                            a.put("username", AlarmUsersList.get(l).getUsername());
                            if (AlarmUsersList.get(l).getEditable() == 0) {  //TODO SUNEXEIA EDW:::::::::::::::::::::::::
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
                        cust.add(pt);
                    }
                    returnList.put("status", "ok");
                    returnList.put("customers", cust);
                    // returnList.put("revisions",revListmap);
                    returnList.put("total", total.intValue());
                    result.put("status", "ok");
                    result.put("message", "SUCCESS");
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
}
