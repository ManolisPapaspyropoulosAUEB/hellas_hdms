package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.CdataEntity;
import models.ContactInfoEntity;
import models.CoredataEntity;
import play.db.jpa.JPA;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
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

public class ContactInformationsController {




  @SuppressWarnings("Duplicates")
  @BodyParser.Of(BodyParser.Json.class)
  @play.db.jpa.Transactional

  public Result addContactInformation() {
    try {
      JsonNode json = request().body().asJson(); //
      ObjectNode result = Json.newObject();
      if (json == null) {
        return badRequest("Expecting Json data");
      } else {


        System.out.println(json);

        String title = json.findPath("title").asText();
        String primary = json.findPath("primary").asText();
        String portPrimary = json.findPath("portPrimary").asText();
        String secondary = json.findPath("secondary").asText();
        String portSecondary = json.findPath("portSecondary").asText();
        String type = json.findPath("type").asText();


        /*ContactInfoEntity cinf =  new ContactInfoEntity();
        String title = json.findPath("title").asText();
        String primary = json.findPath("primary").asText();
        String portPrimary = json.findPath("portPrimary").asText();
        String secondary = json.findPath("secondary").asText();
        String portSecondary = json.findPath("portSecondary").asText();
        String type = json.findPath("type").asText();


        cinf.setTitle(title);
        cinf.setPrimary(primary);
        cinf.setPortPrimary(portPrimary);
        cinf.setSecondary(secondary);
        cinf.setPortSecondary(portSecondary);
        cinf.setType(type);

        System.out.println(cinf.getTitle());
        System.out.println(cinf.getPrimary());
        System.out.println(cinf.getPortPrimary());
        System.out.println(cinf.getSecondary());
        System.out.println(cinf.getPortSecondary());
        System.out.println(cinf.getType());*/



//        ContactInfoEntity cc = Json.fromJson(json, ContactInfoEntity.class);
//
//
//        InfoCoredataEntity cinf = Json.fromJson(json, InfoCoredataEntity.class);
//        System.out.println(cinf.getPortPrimary());
//        System.out.println(cinf.getPrimary());
//        System.out.println(cinf.getPortSecondary());
//        System.out.println(cinf.getType());
//        System.out.println(cinf.getSecondary());
//        System.out.println(cinf.toString());
//        JPA.em().persist(cinf);


        CdataEntity cd = new CdataEntity();

        cd.setCol1(title);
        cd.setCol2(primary);
        cd.setCol3(portPrimary);
        cd.setCol4(secondary);
        cd.setCol5(portSecondary);
        cd.setCol6(type);



       JPA.em().persist(cd);



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
  public Result deleteContactInformation() {
    ObjectNode result = Json.newObject();
    try {
      JsonNode json = Controller.request().body().asJson();
      if (json == null) {
        return badRequest("Expecting Json data");
      } else {
        Integer id = json.findPath("id").asInt();
        if (id == null || id == 0) {
          result.put("status", "error");
          result.put("message", "Ανεπιτυχής Διαγραφή.");
          return ok(result);
        } else {


          CdataEntity s = JPA.em().find(CdataEntity.class, id);
          JPA.em().remove(s);
          result.put("status", "ok");
          result.put("message", "success");
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
  public Result updateContactInformation() {
    try {
      JsonNode json = request().body().asJson(); //
      ObjectNode result = Json.newObject();
      if (json == null) {
        return badRequest("Expecting Json data");
      } else {

        String title = json.findPath("title").asText();
        String primary = json.findPath("primary").asText();
        String portPrimary = json.findPath("portPrimary").asText();
        String secondary = json.findPath("secondary").asText();
        String portSecondary = json.findPath("portSecondary").asText();
        String type = json.findPath("type").asText();
        Integer id = json.findPath("id").asInt();


        CdataEntity cinf =  JPA.em().find(CdataEntity.class,id);

        cinf.setCol1(title);
        cinf.setCol2(primary);
        cinf.setCol3(portPrimary);
        cinf.setCol4(secondary);
        cinf.setCol5(portSecondary);

        if(type.equalsIgnoreCase("Pots")){
          cinf.setCol6("p");
        }else{
          cinf.setCol6("n");
        }





        JPA.em().merge(cinf);

        result.put("status", "ok");
        result.put("message", "Η ενημέρωση έγινε με επιτυχία.");
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
  public Result getContactsInformationsByType() throws IOException {
    ObjectNode result = Json.newObject();
    try {
      JsonNode json = request().body().asJson();
      if (json == null) {
        return badRequest("Expecting Json data");
      } else {
        Query q = JPA.em().createQuery("SELECT t FROM CdataEntity t " );
        List<CdataEntity> sList = q.getResultList();
        ObjectMapper ow = new ObjectMapper();
        HashMap<String, Object> returnList = new HashMap<String, Object>();
        String jsonResult = "";
        Integer total = q.getResultList().size();
        List<HashMap<String, Object>> templatesListNetwork = new ArrayList<HashMap<String, Object>>();
        List<HashMap<String, Object>> templatesListPots = new ArrayList<HashMap<String, Object>>();



        for (CdataEntity j : sList) {

          HashMap<String, Object> pt = new HashMap<String, Object>();
          pt.put("id", j.getId());

          pt.put("title", j.getCol1());
          pt.put("portPrimary", j.getCol3());
          pt.put("portSecondary", j.getCol5());
          pt.put("primary", j.getCol2());
          pt.put("secondary", j.getCol4());

          if(j.getCol6().equalsIgnoreCase("n")){
            pt.put("type", "Net Work");
          }else if (j.getCol6().equalsIgnoreCase("p")){
            pt.put("type", "Pots");
          }



          if(j.getCol6().equalsIgnoreCase("n")){
            templatesListNetwork.add(pt);
          }else if (j.getCol6().equalsIgnoreCase("p")){
            templatesListPots.add(pt);
          }




        }

        returnList.put("status", "ok");

        returnList.put("templatesListNetwork", templatesListNetwork);
        returnList.put("templatesListPots", templatesListPots);

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
  public Result getContactsInformations() throws IOException {
    ObjectNode result = Json.newObject();
    try {
      JsonNode json = request().body().asJson();
      if (json == null) {
        return badRequest("Expecting Json data");
      } else {
        Query q = JPA.em().createQuery("SELECT t FROM CdataEntity t " );
        List<CdataEntity> sList = q.getResultList();
        ObjectMapper ow = new ObjectMapper();
        HashMap<String, Object> returnList = new HashMap<String, Object>();
        String jsonResult = "";
        Integer total = q.getResultList().size();
        List<HashMap<String, Object>> templatesList = new ArrayList<HashMap<String, Object>>();



        for (CdataEntity j : sList) {

          HashMap<String, Object> pt = new HashMap<String, Object>();
          pt.put("id", j.getId());

          pt.put("title", j.getCol1());
          pt.put("portPrimary", j.getCol3());
          pt.put("portSecondary", j.getCol5());
          pt.put("primary", j.getCol2());
          pt.put("secondary", j.getCol4());

          if(j.getCol6().equalsIgnoreCase("n")){
            pt.put("type", "Net Work");
          }else if (j.getCol6().equalsIgnoreCase("p")){
            pt.put("type", "Pots");
          }



          templatesList.add(pt);
        }

        returnList.put("status", "ok");
        returnList.put("data", templatesList);
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
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "error");
      result.put("message", "Πρόβλημα κατά την διαγραφή .");
      return ok(result);
    }
  }














}
