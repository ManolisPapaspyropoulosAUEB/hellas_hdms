package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.db.jpa.JPA;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;

import javax.persistence.Query;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static play.mvc.Controller.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
/**
 * created by mpapaspyropoulos
 */
public class InstallersController {
    private static final String ALGO = "AES";
    private static final byte[] keyValue =
            new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't',
                    'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    @SuppressWarnings("Duplicates")
    //insert daneizomenou ulikou,,prose3e mhn ksanakaneis map gt den to kanei swsta logw palaiothtas
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result insertInstaller() {
        try {
            ObjectMapper ow = new ObjectMapper();
            ObjectNode result = Json.newObject();
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                String username = json.findPath("username").asText();
                String password = json.findPath("pwd").asText();
                String email = json.findPath("email").asText();
                String afm = json.findPath("installerAfm").asText();
                if (username == null || username.equalsIgnoreCase("")) {
                    result.put("status", "error");
                    result.put("message", "Παρακαλώ καταχωρήστε username.");
                    return ok(result);
                }
                if (password == null || password.equalsIgnoreCase("")) {
                    result.put("status", "error");
                    result.put("message", "Παρακαλώ καταχωρήστε password.");
                    return ok(result);
                }
                if (email == null || email.equalsIgnoreCase("")) {
                    result.put("status", "error");
                    result.put("message", "Παρακαλώ καταχωρήστε email.");
                    return ok(result);
                }
                int us_count = 0;
                int em_count = 0;
                Query qi = JPA.em().createQuery("SELECT i FROM InstallersEntity i ");
                List<InstallersEntity> instList = qi.getResultList();
                for (InstallersEntity ins : instList) {
                    if ((username.equalsIgnoreCase(ins.getUsername()))) {
                        us_count++;
                    }
                }
                for (InstallersEntity ins : instList) {
                    if ((afm.equalsIgnoreCase(ins.getInstallerAfm()))) {
                        result.put("status", "error");
                        result.put("message", "Το αφμ ειναι μοναδικό.");
                    }
                }
                for (InstallersEntity ins : instList) {
                    if ((email.equalsIgnoreCase(ins.getEmail()))) {
                        em_count++;
                    }
                }
                if (us_count > 0) {
                    result.put("incorect username", "Το username που δώσατε υπάρχει ήδη.");
                }
                if (em_count > 0) {
                    result.put("incorect email", "Το email που δώσατε υπάρχει ήδη.");
                }
                if (us_count > 0 || em_count > 0) {
                    result.put("status", "error");
                    return ok(result);
                }
                InstallersEntity inst = Json.fromJson(json, InstallersEntity.class);
                JPA.em().persist(inst);
                result.put("status", "ok");
                result.put("message", "Η καταχώρηση νέου εγκαταστάτη έγινε με επιτυχία.");
                return ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την καταχώρηση.");
            return ok(result);
        }
    }

    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    //ayth h methodos ekteleitai ka8e fora pou eisagetai mia parametros
    public Result getInstallers() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            System.out.println(json);
            String querySearch = json.findPath("searchField").asText();
            ObjectMapper ow = new ObjectMapper();
            String qw = "select * from installers   ";
            if (querySearch != "") {
                qw += " where installer_afm like '%" + querySearch + "%' " +
                        " or  name like    '%" + querySearch + "%' " +
                        " or  installer_insured_area_city like    '%" + querySearch + "%'" +
                        " or  email like    '%" + querySearch + "%'";
            }
            System.out.println(qw);
            //Query q = JPA.em().createQuery("SELECT b FROM InstallersEntity b ");
            Query q = JPA.em().createNativeQuery(qw, InstallersEntity.class);
            HashMap<String, Object> returnList = new HashMap<String, Object>();
            List<InstallersEntity> iList = q.getResultList();
            List<HashMap<String, Object>> instFlist = new ArrayList<HashMap<String, Object>>();
            Integer total = q.getResultList().size();
            String jsonResult = "";
            for (InstallersEntity i : iList) {
                HashMap<String, Object> pt = new HashMap<String, Object>();
                pt.put("id", i.getUserId());
                pt.put("name", i.getName());
                pt.put("installerAfm", i.getInstallerAfm());
                pt.put("billingAddressOnly", i.getBillingAddressOnly());
                pt.put("installerBillingAddressOnly", i.getInstallerBillingAddressOnly());
                pt.put("installerCollectionPolicy", i.getInstallerCollectionPolicy());
                pt.put("username", i.getUsername());
                //pt.put("pwd", decrypt(i.getPwd()));



              String qr = "select * from users u where u.userId="+i.getUserId();
              System.out.println(qr);
              Query q2 = JPA.em().createNativeQuery(qr,UsersEntity.class);


                List<UsersEntity> uList = q2.getResultList();


                if (uList.get(0).getPassword().equalsIgnoreCase("null")) {
                    pt.put("firstTime", true);
                } else {
                    pt.put("firstTime", false);
                }
                // pt.put("pwd",uList.get(0).getPassword());
                pt.put("email", i.getEmail());

                pt.put("lastName", i.getLastName());
                pt.put("doy", i.getDoy());
                pt.put("fax", i.getFax());
                pt.put("email", i.getEmail());




                pt.put("selfCollection", i.getSelfCollection());
                pt.put("emailInvoice", i.getEmailInvoice());
                pt.put("fax", i.getFax());
                pt.put("installerInsuredAreaAddress", i.getInstallerInsuredAreaAddress());
                pt.put("installerInsuredAreaCity", i.getInstallerInsuredAreaCity());
                pt.put("installerInsuredAreaFloor", i.getInstallerInsuredAreaFloor());
                pt.put("installerInsuredAreaPostCode", i.getInstallerInsuredAreaPostCode());
                pt.put("installerLandlinePhone", i.getInstallerLandlinePhone());
                pt.put("installerMobilePhone", i.getInstallerMobilePhone());
                pt.put("installerProffesionalDescription", i.getInstallerProffesionalDescription());
                pt.put("installerWebsite", i.getInstallerWebsite());
                pt.put("lastUpdatePwd", i.getLastUpdatePwd());
                pt.put("active", i.getActive());
                instFlist.add(pt);
            }
            returnList.put("data", instFlist);
            returnList.put("total", total.intValue());
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

        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την διαγραφή .");
            return ok(result);
        }
    }


    //
    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    //ayth h methodos ekteleitai ka8e fora pou eisagetai mia parametros
    public Result getConnectionsOfLastWeek() throws ParseException {
        ObjectMapper ow = new ObjectMapper();
        String jsonResult = "";
        JsonNode json = request().body().asJson();
        HashMap<String, Object> itemk = new HashMap<>();
        HashMap<String, Object> itemd = new HashMap<>();
        HashMap<String, Object> itemtr = new HashMap<>();
        HashMap<String, Object> itemtet = new HashMap<>();
        HashMap<String, Object> itempem = new HashMap<>();
        HashMap<String, Object> itempar = new HashMap<>();
        HashMap<String, Object> itemsab = new HashMap<>();
        Integer userId = json.findPath("userId").asInt();
        List returnList = new ArrayList<>();
        HashMap<String, Object> returnListData = new HashMap<String, Object>();
        List b_m = new ArrayList<>();
        String query3 = "SELECT count(DATE(CREATION_DATE)) " +
                "FROM customers " +
                "where user_id=" + "'" + userId + "'" +
                "and  date(creation_date) " +
                "  BETWEEN CURRENT_TIMESTAMP - INTERVAL 7 DAY " +
                "      AND CURRENT_TIMESTAMP " +
                " "; //nursing_category_id


        String query4 = "SELECT count(DATE(creation_date)),DAYOFWEEK(creation_date),DAY(creation_date),MONTH(creation_date) " +
                "FROM customers " +
                "where user_id=" + "'" + userId + "'" +
                "and  date(creation_date) " +
                " BETWEEN CURRENT_TIMESTAMP - INTERVAL 7 DAY " +
                " AND CURRENT_TIMESTAMP " +
                "group by day(creation_date)"; //count


        List<Integer> resultList_id = JPA.em().createNativeQuery(query3).getResultList();
        List<Integer> resultList_count = JPA.em().createNativeQuery(query4).getResultList();
        Iterator it = resultList_count.iterator();
        ArrayList<HashMap<String, Object>> myList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> myListFinal = new ArrayList<>();

        ArrayList<Integer> myListforSort = new ArrayList<>();

        int countk = 0;
        int countd = 0;
        int counttr = 0;
        int counttet = 0;
        int countpem = 0;
        int countpar = 0;
        int countsav = 0; //
        while (it.hasNext()) {
            JsonNode jn = Json.toJson(it.next());
            HashMap<String, Object> item = new HashMap<>();
            item.put("count", jn.get(0).asLong());
            if (jn.get(1).asLong() == 1) {
                myListforSort.add(1);
                item.put("day", "Κυρ " + jn.get(2) + "/" + jn.get(3));
                item.put("dayN", 1);
                countk++;
            } else if (jn.get(1).asLong() == 2) {
                myListforSort.add(2);
                item.put("day", "Δευτ " + jn.get(2) + "/" + jn.get(3));
                item.put("dayN", 2);
                countd++;
            } else if (jn.get(1).asLong() == 3) {
                myListforSort.add(3);
                item.put("day", "Τριτ " + jn.get(2) + "/" + jn.get(3));
                item.put("dayN", 3);
                counttr++;
            } else if (jn.get(1).asLong() == 4) {
                myListforSort.add(4);
                item.put("day", "Τετ " + jn.get(2) + "/" + jn.get(3));
                item.put("dayN", 4);
                counttet++;
            } else if (jn.get(1).asLong() == 5) {
                myListforSort.add(5);
                item.put("day", "Πεμ " + jn.get(2) + "/" + jn.get(3));
                item.put("dayN", 5);
                countpem++;

            } else if (jn.get(1).asLong() == 6) {
                myListforSort.add(6);
                item.put("day", "Παρ " + jn.get(2) + "/" + jn.get(3));
                item.put("dayN", 6);
                countpar++;
            } else if (jn.get(1).asLong() == 7) {
                myListforSort.add(7);
                item.put("day", "Σαβ " + jn.get(2) + "/" + jn.get(3));
                item.put("dayN", 7);
                countsav++;
            }
            myList.add(item);
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateToday = new Date();
        Date date1 = dateFormat.parse(dateFormat.format(dateToday));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);

        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int dayw = cal.get(Calendar.DAY_OF_MONTH);
        int dweek = cal.get(Calendar.DAY_OF_WEEK);
        int day = cal.get(Calendar.YEAR);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (countk == 0) {
            myListforSort.add(1);
            itemk.put("day", "Κυρ " + (dayw - dweek + 1) + "/" + month);
            itemk.put("count", 0);
            itemk.put("dayN", 1);
            myList.add(itemk);
        }
        if (countd == 0) {
            myListforSort.add(2);
            itemd.put("day", "Δευτ " + (dayw - dweek + 2) + "/" + month);
            itemd.put("count", 0);
            itemd.put("dayN", 2);
            myList.add(itemd);
        }
        if (counttr == 0) {
            myListforSort.add(3);
            itemtr.put("day", "Τριτ " + (dayw - dweek + 3) + "/" + month);
            itemtr.put("count", 0);
            itemtr.put("dayN", 3);
            myList.add(itemtr);
        }
        if (counttet == 0) {
            myListforSort.add(4);
            itemtet.put("day", "Τετ " + (dayw - dweek + 4) + "/" + month);
            itemtet.put("count", 0);
            itemtet.put("dayN", 4);
            myList.add(itemtet);
        }
        if (countpem == 0) {
            myListforSort.add(5);
            itempem.put("day", "Πεμ " + (dayw - dweek + 5) + "/" + month);
            itempem.put("count", 0);
            itempem.put("dayN", 5);
            myList.add(itempem);
        }
        if (countpar == 0) {
            myListforSort.add(6);
            itempar.put("day", "Παρ " + (dayw - dweek + 6) + "/" + month);
            itempar.put("count", 0);
            itempar.put("dayN", 6);
            myList.add(itempar);
        }
        if (countsav == 0) {
            myListforSort.add(7);
            itemsab.put("day", "Σαβ " + (dayw - dweek + 7) + "/" + month);
            itemsab.put("count", 0);
            itemsab.put("dayN", 7);
            myList.add(itemsab);
        }
//myListFinal
        List<HashMap<String, Object>> sortedunitCostExpenses = new ArrayList<HashMap<String, Object>>();
        //briskw to min ths listas ,to kanw instert se mia allh kai meta remove
        while (myList.size() > 0) {
            Integer min = (Integer) myList.get(0).get("dayN");
            int index = 0;
            for (int i = 0; i < myList.size(); i++) {
                if ((Integer) myList.get(i).get("dayN") < min) {
                    min = (Integer) myList.get(i).get("dayN");
                    index = i;
                }
            }
            myListFinal.add(myList.get(index));
            myList.remove(index);
        }

        returnListData.put("data", myListFinal);
        //returnList.put("status","ok");
        returnListData.put("total", resultList_id.get(0));
        try {
            jsonResult = ow.writeValueAsString(returnListData);
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την ανάγνωση .");
            return ok(result);
        }
        return ok(jsonResult);
    }


    @SuppressWarnings("Duplicates")
    public List<HashMap<String, Object>> getAllCustomersByUserId(Integer user_id) throws IOException {
        ObjectNode result = Json.newObject();
        Query q = JPA.em().createQuery("SELECT c FROM CustomersCcEntity c where c.userId=" + user_id);
        List<CustomersCcEntity> custList = q.getResultList();
        ObjectMapper ow = new ObjectMapper();
        HashMap<String, Object> returnList = new HashMap<String, Object>();
        String jsonResult = "";
        Integer total = q.getResultList().size();
        List<HashMap<String, Object>> cust = new ArrayList<HashMap<String, Object>>();
        int k = -1;
        //--------------------------MASTER ------------- (customer details) -----------------------------\\ \\ \\ \\ \\ \\ \\ \\ // // // // // // //
        for (CustomersCcEntity j : custList) {
            k++;
            HashMap<String, Object> pt = new HashMap<String, Object>();
            pt.put("id", j.getId()); //creationCcDate
            pt.put("creationCcDate", j.getCreationCcDate());
            pt.put("submittedCcDate", j.getSubmittedCcDate());
            pt.put("customerId", j.getCustomerId());
            pt.put("alarmUnitType", j.getAlarmUnitType());
            pt.put("areaPhone", j.getAreaPhone());
            pt.put("customerAuxiliaryPass", j.getCustomerAuxiliaryPass());
            pt.put("customerConnectionDate", j.getCustomerConnectionDate());
            pt.put("customerPass", j.getCustomerPass());
            pt.put("datePublished", j.getDatePublished());
            pt.put("directTransmissionPhones", j.getDirectTransmissionPhones());
            pt.put("draft", j.getDraft());
            if (j.getCancelation() != null) {
                if (j.getCancelation() == 0) {
                    pt.put("cancelation", false);
                } else {
                    pt.put("cancelation", true);
                }
            }
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
            pt.put("visible", j.getVisible());
            pt.put("status", j.getStatus());
            pt.put("revisionId", j.getRevisionId());
            pt.put("afm", j.getAfm());
            List<PhoneNoticesCcEntity> phoneNoticiesList = JPA.em()
                    .createQuery("SELECT d FROM PhoneNoticesCcEntity d WHERE d.customerId=" + j.getCustomerId() + " ORDER BY d.id")
                    .getResultList();
            HashMap<String, Object> p = new HashMap<String, Object>();
            List<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
            for (int l = 0; l < phoneNoticiesList.size(); l++) {
                p.put("id", phoneNoticiesList.get(l).getId());
                p.put("phoneId", phoneNoticiesList.get(l).getPhoneNoticesId());
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
            List<ZonesCcEntity> zonesList = JPA.em()
                    .createQuery("SELECT z FROM ZonesCcEntity z WHERE z.customerId=" + j.getCustomerId() + " ORDER BY z.id")
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
                z.put("id", zonesList.get(l).getId());
                z.put("name", zonesList.get(l).getName());
                z.put("customerId", zonesList.get(l).getCustomerId());
                z.put("zoneId", zonesList.get(l).getZoneId());
                z.put("idByUser", zonesList.get(l).getIdByUser());
                zList.add(z);
            }
            List<AlarmUsersCcEntity> AlarmUsersList = JPA.em()
                    .createQuery("SELECT a FROM AlarmUsersCcEntity a WHERE a.customerId=" + j.getCustomerId() + " ORDER BY a.id")
                    .getResultList();
            HashMap<String, Object> a = new HashMap<String, Object>();
            List<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();
            for (int l = 0; l < AlarmUsersList.size(); l++) {
                a.put("id", AlarmUsersList.get(l).getId());
                a.put("alarmUserId", AlarmUsersList.get(l).getAlarmUserId());
                a.put("customerId", AlarmUsersList.get(l).getCustomerId());
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
            pt.put("phoneNotices", pList);
            pt.put("zones", zList);
            pt.put("alarmUsers", aList);
            cust.add(pt);
        }

        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //TODO:DATE :AYTH H METHODOS EYTHINETE GIA TO PWS THA EPISTREPSOUN OI HMEROMHNIES
        ow.setDateFormat(myDateFormat);
        return cust;
    }

    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    //ayth h methodos ekteleitai ka8e fora pou eisagetai mia parametros
    public Result getInstallersDetails() throws IOException { //oxi gia CC
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            ObjectMapper ow = new ObjectMapper();
            Query q = JPA.em().createQuery("SELECT b FROM InstallersEntity b ");
            HashMap<String, Object> returnList = new HashMap<String, Object>();
            List<InstallersEntity> iList = q.getResultList();
            List<HashMap<String, Object>> instFlist = new ArrayList<HashMap<String, Object>>();
            Integer total = q.getResultList().size();
            String jsonResult = "";
            for (InstallersEntity i : iList) {
                HashMap<String, Object> pt = new HashMap<String, Object>();

                pt.put("id", i.getUserId());
                pt.put("name", i.getName());
                pt.put("installerAfm", i.getInstallerAfm());
                pt.put("billingAddressOnly", i.getBillingAddressOnly());
                pt.put("installerBillingAddressOnly", i.getInstallerBillingAddressOnly());
                pt.put("installerCollectionPolicy", i.getInstallerCollectionPolicy());
                pt.put("username", i.getUsername());
                //pt.put("pwd", decrypt(i.getPwd()));
                pt.put("email", i.getEmail());
                pt.put("selfCollection", i.getSelfCollection());
                pt.put("emailInvoice", i.getEmailInvoice());
                pt.put("fax", i.getFax());
                pt.put("installerInsuredAreaAddress", i.getInstallerInsuredAreaAddress());
                pt.put("installerInsuredAreaCity", i.getInstallerInsuredAreaCity());
                pt.put("installerInsuredAreaFloor", i.getInstallerInsuredAreaFloor());
                pt.put("installerInsuredAreaPostCode", i.getInstallerInsuredAreaPostCode());
                pt.put("installerLandlinePhone", i.getInstallerLandlinePhone());
                pt.put("installerMobilePhone", i.getInstallerMobilePhone());
                pt.put("installerProffesionalDescription", i.getInstallerProffesionalDescription());
                pt.put("installerWebsite", i.getInstallerWebsite());
                pt.put("lastUpdatePwd", i.getLastUpdatePwd());
                pt.put("active", i.getActive());
                instFlist.add(pt);
            }
            returnList.put("data", instFlist);
            returnList.put("total", total.intValue());
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
    //ayth h methodos ekteleitai ka8e fora pou eisagetai mia parametros
    //ayth h methodos fernei flat dedomena installers-customers ,gia oous tous isntallers fernei tous pelates pou tous antistoixoun
    //se flat morfh
    public Result getInstallersDataCc() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            System.out.println("json");
            System.out.println(json);
            ObjectMapper ow = new ObjectMapper();
            //Query q = JPA.em().createQuery("SELECT b FROM InstallersCustomersEntity b order by b.creationCcDate desc", InstallersCustomersEntity.class);
            //se ayto to view uparxoun ola ayta ta dedomena installer-customer
            String query = "select * from installers_customers where 1 ";
            String open = json.findPath("open").asText();
            String inProgress = json.findPath("inProgress").asText();
            String submitted = json.findPath("submitted").asText();
            String installer = json.findPath("installer").asText();
            String customer = json.findPath("customer").asText();
            String search = json.findPath("search").asText();

            if (open.equalsIgnoreCase("false") && inProgress.equalsIgnoreCase("false") && submitted.equalsIgnoreCase("false")) {
                query = "select * from installers_customers where 1 ";
            } else if (open.equalsIgnoreCase("false") && inProgress.equalsIgnoreCase("false") && submitted.equalsIgnoreCase("true")) {
                query += " and STATUS='SUBMITTED'";
            } else if (open.equalsIgnoreCase("false") && inProgress.equalsIgnoreCase("true") && submitted.equalsIgnoreCase("false")) {
                query += " and STATUS= 'IN PROGRESS' ";
            } else if (open.equalsIgnoreCase("false") && inProgress.equalsIgnoreCase("true") && submitted.equalsIgnoreCase("true")) {
                query += " and (STATUS= 'IN PROGRESS' OR STATUS= 'SUBMITTED') ";
            } else if (open.equalsIgnoreCase("true") && inProgress.equalsIgnoreCase("false") && submitted.equalsIgnoreCase("false")) {
                query += " and (STATUS= 'OPEN') ";
            } else if (open.equalsIgnoreCase("true") && inProgress.equalsIgnoreCase("false") && submitted.equalsIgnoreCase("true")) {
                query += " and (STATUS= 'OPEN' OR STATUS= 'SUBMITTED') ";
            } else if (open.equalsIgnoreCase("true") && inProgress.equalsIgnoreCase("true") && submitted.equalsIgnoreCase("false")) {
                query += " and (STATUS= 'OPEN' OR STATUS= 'IN PROGRESS') ";
            } else if (open.equalsIgnoreCase("true") && inProgress.equalsIgnoreCase("true") && submitted.equalsIgnoreCase("true")) {
                //query = "select * from installers_customers where 1 ";
            }
            if (installer.equalsIgnoreCase("true")) {
                if (!search.equalsIgnoreCase("")) {
                    // " AND (p.lname LIKE '%"+queryParam+"%' )";
                    query += " and (installers_customers.INSTALLER_AFM like'%" + search + "%'  or installers_customers.NAME like'%" + search + "%' )";
                }
                //poiase to search kai kane anazhthsh
            } else if (customer.equalsIgnoreCase("true")) {
                if (!search.equalsIgnoreCase("")) {
                    query += " and (installers_customers.AFM like'%" + search + "%'  or installers_customers.SUBSCRIBER_NAME like'%" + search + "%' )";
                    //query+=" and (installers_customers.AFM="+"'"+search+"'" +"  or installers_customers.SUBSCRIBER_NAME="+"'"+search+"'"+")"  ;
                }
                //poiase to search kai kane anazhthsh
            }
            query += " order by CREATION_CC_DATE desc ";

            System.out.println(query + "  --------- INSTALLERS ------------");

            Query q = JPA.em().createNativeQuery(query, InstallersCustomersEntity.class);
            // Query q = JPA.em().createQuery("SELECT b FROM InstallersCustomersEntity b order by b.creationCcDate desc", InstallersCustomersEntity.class);
            HashMap<String, Object> returnList = new HashMap<String, Object>();
            List<InstallersCustomersEntity> iList = q.getResultList();
            List<HashMap<String, Object>> instFlist = new ArrayList<HashMap<String, Object>>();
            Integer total = q.getResultList().size();
            String jsonResult = "";
            for (InstallersCustomersEntity i : iList) {
                if (i.getCustomerId() != null) {
                    HashMap<String, Object> pt = new HashMap<String, Object>();
                    HashMap<String, Object> ptCust = new HashMap<String, Object>();
                    //-------------++++++++++++++++++++++++++++*******----INSTALLER--INSTALLER--INSTALLER-
                    // -INSTALLER--INSTALLER--INSTALLER--INSTALLER-------++++++++++++++++++++++++++++*******-----------//
                    pt.put("installerId", i.getUserId());
                    pt.put("name", i.getName());
                    pt.put("lastNameins", i.getLastNameIns());
                    pt.put("emailins", i.getEmailIns());
                    pt.put("faxins", i.getFaxIns());
                    pt.put("companyName", i.getCompanyName());
                    pt.put("installerAfm", i.getInstallerAfm());
                    pt.put("billingAddressOnly", i.getBillingAddressOnly());
                    pt.put("installerBillingAddressOnly", i.getInstallerBillingAddressOnly());
                    pt.put("installerCollectionPolicy", i.getInstallerCollectionPolicy());
                    pt.put("username", i.getUsername());
                    //pt.put("pwd", decrypt(i.getPwd()));
                    pt.put("email", i.getEmailIns());
                    pt.put("selfCollection", i.getSelfCollection());
                    pt.put("emailInvoice", i.getEmailInvoice());
                    pt.put("fax", i.getFaxIns());
                    pt.put("installerInsuredAreaAddress", i.getInstallerInsuredAreaAddress());
                    pt.put("installerInsuredAreaCity", i.getInstallerInsuredAreaCity());
                    pt.put("installerInsuredAreaFloor", i.getInstallerInsuredAreaFloor());
                    pt.put("installerInsuredAreaPostCode", i.getInstallerInsuredAreaPostCode());
                    pt.put("installerLandlinePhone", i.getInstallerLandlinePhone());
                    pt.put("installerMobilePhone", i.getInstallerMobilePhone());
                    pt.put("installerProffesionalDescription", i.getInstallerProffesionalDescription());
                    pt.put("installerWebsite", i.getInstallerWebsite());
                    pt.put("lastUpdatePwd", i.getLastUpdatePwd());
                    pt.put("active", i.getActive());
                    pt.put("id", i.getId());
//-------------++++++++++++++++++++++++++++*******-----CUSTOMER-------++++++++++++++++++++++++++++*******-------------//
                    if (i.getCustomerId() != null) {
                        ptCust.put("creationCcDate", i.getCreationCcDate());
                        ptCust.put("submittedCcDate", i.getSubmittedCcDate());
                        ptCust.put("customerId", i.getCustomerId());
                        ptCust.put("alarmUnitType", i.getAlarmUnitType());
                        ptCust.put("areaPhone", i.getAreaPhone());
                        ptCust.put("customerAuxiliaryPass", i.getCustomerAuxiliaryPass());
                        ptCust.put("customerConnectionDate", i.getCustomerConnectionDate());
                        ptCust.put("customerPass", i.getCustomerPass());
                        ptCust.put("datePublished", i.getDatePublished());
                        ptCust.put("directTransmissionPhones", i.getDirectTransmissionPhones());
                        ptCust.put("faxcc", i.getFaxCc());
                        ptCust.put("emailcc", i.getEmailCc());
                        ptCust.put("service247Monitoring", i.getService247Monitoring());
                        ptCust.put("serviceAccess", i.getServiceAccess());
                        ptCust.put("serviceCctv", i.getServiceCctv());
                        ptCust.put("serviceCommunication", i.getServiceCommunication());
                        ptCust.put("serviceGlobalSim", i.getServiceGlobalSim());
                        ptCust.put("serviceReport", i.getServiceReport());
                        ptCust.put("serviceTest", i.getServiceTest());
                        ptCust.put("sevriceMonitoring", i.getSevriceMonitoring());
                        ptCust.put("draft", i.getDraft());
                        if (i.getCancelation() != null) {
                            if (i.getCancelation() == 0) {
                                ptCust.put("cancelation", false);
                            } else {
                                ptCust.put("cancelation", true);
                            }
                        }
                        ptCust.put("duressCode", i.getDuressCode());
                        ptCust.put("enabled", i.getEnabled());
                        ptCust.put("format", i.getFormat());
                        ptCust.put("frequency24HourTest", i.getFrequency24HourTest());
                        ptCust.put("insuredAreaAddress", i.getInsuredAreaAddress());
                        ptCust.put("insuredAreaCity", i.getInsuredAreaCity());
                        ptCust.put("insuredAreaDescription", i.getInsuredAreaDescription());
                        ptCust.put("insuredAreaFloor", i.getInsuredAreaFloor());
                        ptCust.put("insuredAreaPostCode", i.getInsuredAreaPostCode());
                        ptCust.put("insuredAreaType", i.getInsuredAreaType());
                        ptCust.put("insuredAreaTypeOther", i.getInsuredAreaTypeOther());
                        ptCust.put("monthlyAlarmList", i.getMonthlyAlarmList());
                        ptCust.put("operationControllHours", i.getOperationControllHours());
                        ptCust.put("otherRemarks", i.getOtherRemarks());
                        ptCust.put("policeStation", i.getPoliceStation());
                        ptCust.put("subscriberName", i.getSubscriberName());
                        ptCust.put("weeklyTimeMonitoring", i.getWeeklyTimeMonitoring());
                        ptCust.put("lastAction", i.getLastAction());
                        ptCust.put("device", i.getDevice());
                        ptCust.put("system", i.getDevice());
                        ptCust.put("updateDate", i.getUpdateDate());
                        ptCust.put("visible", i.getVisible());
                        ptCust.put("status", i.getStatus());
                        ptCust.put("revisionId", i.getRevisionId());
                        ptCust.put("afm", i.getAfm());
                        List<PhoneNoticesCcEntity> phoneNoticiesList = JPA.em()
                                .createQuery("SELECT d FROM PhoneNoticesCcEntity d WHERE d.customerId=" + i.getCustomerId() +
                                        " and d.revisionId=" + i.getId() + "  ORDER BY d.id")
                                .getResultList();
                        HashMap<String, Object> p = new HashMap<String, Object>();
                        List<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
                        for (int l = 0; l < phoneNoticiesList.size(); l++) {
                            p.put("id", phoneNoticiesList.get(l).getId());
                            p.put("phoneId", phoneNoticiesList.get(l).getPhoneNoticesId());
                            if (phoneNoticiesList.get(l).getEditable().equals(0)) {
                                p.put("editable", false);
                            } else {
                                p.put("editable", true);
                            }
                            p.put("phone", phoneNoticiesList.get(l).getPhone());
                            p.put("name", phoneNoticiesList.get(l).getName());
                            p.put("username", phoneNoticiesList.get(l).getUsername());
                            p.put("customerId", phoneNoticiesList.get(l).getCustomerId());
                            p.put("revisionId", phoneNoticiesList.get(l).getRevisionId());
                            if (phoneNoticiesList.get(l).getVisible().equals(0)) {
                                p.put("visible", false);
                            } else {
                                p.put("visible", true);
                            }
                            pList.add(p);
                        }
                        List<ZonesCcEntity> zonesList = JPA.em()
                                .createQuery("SELECT z FROM ZonesCcEntity z WHERE z.customerId=" + i.getCustomerId() +
                                        " and z.revisionId=" + i.getId() + " ORDER BY z.id")
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
                            z.put("id", zonesList.get(l).getId());
                            z.put("name", zonesList.get(l).getName());
                            z.put("customerId", zonesList.get(l).getCustomerId());
                            z.put("zoneId", zonesList.get(l).getZoneId());
                            z.put("revisionId", zonesList.get(l).getRevisionId());
                            z.put("idByUser", zonesList.get(l).getIdByUser());
                            zList.add(z);
                        }
                        List<AlarmUsersCcEntity> AlarmUsersList = JPA.em()
                                .createQuery("SELECT a FROM AlarmUsersCcEntity a WHERE a.customerId=" + i.getCustomerId() +
                                        " and a.revisionId=" + i.getId() + " ORDER BY a.id")
                                .getResultList();
                        HashMap<String, Object> a = new HashMap<String, Object>();
                        List<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();
                        for (int l = 0; l < AlarmUsersList.size(); l++) {
                            a.put("id", AlarmUsersList.get(l).getId());
                            a.put("alarmUserId", AlarmUsersList.get(l).getAlarmUserId());
                            a.put("customerId", AlarmUsersList.get(l).getCustomerId());
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
                            if (AlarmUsersList.get(l).getRevisionId() != null) {
                                a.put("revisionId", AlarmUsersList.get(l).getRevisionId());
                            }
                            a.put("customerId", AlarmUsersList.get(l).getCustomerId());
                            aList.add(a);
                        }
                        if (i.getVisible() != null) {
                            if (i.getVisible() == 0) {
                                pt.put("visible", false);
                            } else {
                                pt.put("visible", true);
                            }
                        }
                        if (i.getEnabled() != null) {
                            if (i.getEnabled() == 0) {
                                pt.put("enabled", false);
                            } else {
                                pt.put("enabled", true);
                            }
                        }
                        if (i.getDraft() != null) {
                            if (i.getDraft() == 0) {
                                pt.put("draft", false);
                            } else {
                                pt.put("draft", true);
                            }
                        }
                        ptCust.put("phoneNotices", pList);
                        ptCust.put("zones", zList);
                        ptCust.put("alarmUsers", aList);
                        pt.put("customers", ptCust);
                    }
                    //List<HashMap<String, Object>> m = getAllCustomersByUserId(i.getUserId());
                    // pt.put("customers", m);
                    instFlist.add(pt);
                }
            }
            DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //TODO:DATE :AYTH H METHODOS EYTHINETE GIA TO PWS THA EPISTREPSOUN OI HMEROMHNIES
            ow.setDateFormat(myDateFormat);
            returnList.put("data", instFlist);
            returnList.put("total", total.intValue());
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
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την ανακτηση .");
            return ok(result);
        }
    }

    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateInstaller() {
        try {
            ObjectMapper ow = new ObjectMapper();
            ObjectNode result = Json.newObject();
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                String email = json.findPath("email").asText();
                String afm = json.findPath("installerAfm").asText();
                if (email == null || email.equalsIgnoreCase("")) {
                    result.put("status", "error");
                    result.put("message", "Παρακαλώ καταχωρήστε email.");
                    return ok(result);
                }
                int us_count = 0;
                int em_count = 0;
                Query qi = JPA.em().createQuery("SELECT i FROM InstallersEntity i ");
                List<InstallersEntity> instList = qi.getResultList();
                for (InstallersEntity ins : instList) {
                    if ((afm.equalsIgnoreCase(ins.getInstallerAfm())) && (json.findPath("userId").asInt() != (ins.getUserId()))) {
                        result.put("status", "error");
                        result.put("message", "Το αφμ ειναι μοναδικό.");
                    }
                }
                for (InstallersEntity ins : instList) {
                    if ((email.equalsIgnoreCase(ins.getEmail())) && (json.findPath("userId").asInt() != (ins.getUserId()))) {
                        em_count++;
                    }
                }
                if (us_count > 0) {
                    result.put("incorect username", "Το username που δώσατε υπάρχει ήδη.");
                }
                if (em_count > 0) {
                    result.put("incorect email", "Το email που δώσατε υπάρχει ήδη.");
                }
                if (us_count > 0 || em_count > 0) {
                    result.put("status", "error");
                    return ok(result);
                }
                InstallersEntity inst = Json.fromJson(json, InstallersEntity.class);
                JPA.em().merge(inst);
                result.put("status", "ok");
                result.put("message", "Η καταχώρηση νέου εγκαταστάτη έγινε με επιτυχία.");
                return ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την καταχώρηση.");
            return ok(result);
        }
    }


    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateInstallerPwd() {
        try {
            ObjectMapper ow = new ObjectMapper();
            ObjectNode result = Json.newObject();
            JsonNode json = request().body().asJson();

            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                String pwd = json.findPath("pwd").asText();
                Integer userId = json.findPath("userId").asInt();
                Query qUsers = JPA.em().createQuery("SELECT u FROM UsersEntity u where u.userId=" + userId);
                List<UsersEntity> uList = qUsers.getResultList();
                UsersEntity u = uList.get(0);
                if (!pwd.equalsIgnoreCase("") && pwd != null) {
                    u.setPassword(encrypt(pwd));
                    JPA.em().merge(u);
                    result.put("status", "ok");
                    result.put("message", "Η ανανέωση κωδικού πραγματοποιήθηκε με επιτυχία.");
                    return ok(result);
                } else {
                    result.put("status", "error");
                    result.put("message", "Προβλημα κατα την ανανεωση κωδικου .");
                    return ok(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την καταχώρηση.");
            return ok(result);
        }
    }


    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    //ayth h methodos ekteleitai ka8e fora pou eisagetai mia parametros
    public Result getInstallersById() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            int id = json.findPath("userId").asInt();
            ObjectMapper ow = new ObjectMapper();
            Query q = JPA.em().createQuery("SELECT b FROM InstallersEntity b WHERE b.id= " + id);
            HashMap<String, Object> returnList = new HashMap<String, Object>();
            List<InstallersEntity> iList = q.getResultList();
            List<HashMap<String, Object>> instFlist = new ArrayList<HashMap<String, Object>>();
            Integer total = q.getResultList().size();
            String jsonResult = "";
            for (InstallersEntity i : iList) {
                HashMap<String, Object> pt = new HashMap<String, Object>();
                pt.put("userId", i.getUserId());
                pt.put("name", i.getName());
                pt.put("installerAfm", i.getInstallerAfm());
                pt.put("lastName", i.getLastName());
                pt.put("billingAddressOnly", i.getBillingAddressOnly());
                pt.put("installerBillingAddressOnly", i.getInstallerBillingAddressOnly());
                pt.put("installerCollectionPolicy", i.getInstallerCollectionPolicy());
                pt.put("username", i.getUsername());
                // pt.put("pwd", i.getPwd());
                if (i.getActive() != null) {
                    if (i.getActive() == 0) {
                        pt.put("active", false);
                    } else {
                        pt.put("active", true);
                    }
                }
                pt.put("email", i.getEmail());
                pt.put("selfCollection", i.getSelfCollection());
                pt.put("emailInvoice", i.getEmailInvoice());
                pt.put("fax", i.getFax());
                pt.put("doy", i.getDoy());
                pt.put("companyName", i.getCompanyName());
                pt.put("installerInsuredAreaAddress", i.getInstallerInsuredAreaAddress());
                pt.put("installerInsuredAreaCity", i.getInstallerInsuredAreaCity());
                pt.put("installerInsuredAreaFloor", i.getInstallerInsuredAreaFloor());
                pt.put("installerInsuredAreaPostCode", i.getInstallerInsuredAreaPostCode());
                pt.put("installerLandlinePhone", i.getInstallerLandlinePhone());
                pt.put("installerMobilePhone", i.getInstallerMobilePhone());
                pt.put("installerProffesionalDescription", i.getInstallerProffesionalDescription());
                pt.put("installerWebsite", i.getInstallerWebsite());
                pt.put("lastUpdatePwd", i.getLastUpdatePwd());
                instFlist.add(pt);
            }
            returnList.put("data", instFlist);
            returnList.put("total", total.intValue());
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

        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την διαγραφή .");
            return ok(result);
        }
    }

    @SuppressWarnings("Duplicates")
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @SuppressWarnings("Duplicates")
    // Generates a random int with n digits
    public static int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }


    @SuppressWarnings("Duplicates")
    //encryption password user
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }

    @SuppressWarnings("Duplicates")
    public static String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    @SuppressWarnings("Duplicates")
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

}
