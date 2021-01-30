package controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import models.CustomersEntity;
import models.InstallersEntity;
import models.UsersEntity;
import org.apache.commons.mail.DefaultAuthenticator;
import play.Configuration;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.mail.internet.InternetAddress;
import javax.persistence.Query;
import java.io.IOException;
import java.util.*;

import static play.mvc.Controller.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
/**
 * created by mpapaspyropoulos
 */
@SuppressWarnings("Duplicates")
public class UsersController {

    @Inject
    protected Configuration configuration;
    private static final String ALGO = "AES";
    private static final byte[] keyValue =
            new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't',
                    'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)

    public Result signUp() { //GIA EGGRAFH TOU EGKATASTATH KAI PARALHLH ENHMERWSH TOU PINAKA USER
        try {
            ObjectMapper ow = new ObjectMapper();
            ObjectNode result = Json.newObject();
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {



                //doy
                //companyName


                System.out.println(json);

                Boolean detachedData = false;
                String afm = json.findPath("installerAfm").asText();
                String email = json.findPath("email").asText();
                String name = json.findPath("name").asText();
                String lastName = json.findPath("lastName").asText();
                String installerInsuredAreaCity = json.findPath("installerInsuredAreaCity").asText();
                //-------elegxos gia to ean o xrhsths edwse afm email name kai installerInsuredAreaCity ----
                if (afm == null || afm.equalsIgnoreCase("")) {
                    detachedData = true;
                    result.put("status", "error");
                    result.put("afm not found", "Παρακαλώ καταχωρήστε ΑΦΜ.");
                }
                if (email == null || email.equalsIgnoreCase("")) {
                    detachedData = true;

                    result.put("email not found", "Παρακαλώ καταχωρήστε email.");
                }
                if (name == null || name.equalsIgnoreCase("")) {
                    detachedData = true;

                    result.put("name not found", "Παρακαλώ καταχωρήστε Όνομα.");
                }

                if (lastName == null || lastName.equalsIgnoreCase("")) {
                    detachedData = true;

                    result.put("lastName not found", "Παρακαλώ καταχωρήστε Eπώνυμο.");
                }


                if (installerInsuredAreaCity == null || installerInsuredAreaCity.equalsIgnoreCase("")) {
                    detachedData = true;

                    result.put("installerInsuredAreaCity not found", "Παρακαλώ καταχωρήστε Πόλη.");
                }
                if (detachedData == true) {
                    result.put("status", "error");
                    return ok(result);
                }
                //--------------------------------------------telos elegxou---------------------
                //ελεγχοσ μοναδικοτητας email kai afm-----------------------------------
                int em_count = 0;
                int afm_count = 0;
                Query qi = JPA.em().createQuery("SELECT i FROM InstallersEntity i ");
                List<InstallersEntity> instList1 = qi.getResultList();
                for (InstallersEntity ins : instList1) {
                    if ((afm.equalsIgnoreCase(ins.getInstallerAfm()))) {
                        afm_count++;
                    }
                }
                for (InstallersEntity ins : instList1) {
                    if ((email.equalsIgnoreCase(ins.getEmail()))) {
                        em_count++;
                    }
                }
                if (em_count > 0) {
                    result.put("incorect email", "Το email που δώσατε υπάρχει ήδη.");
                }
                if (afm_count > 0) {
                    result.put("incorect afm_count", "Το ΑΦΜ που δώσατε υπάρχει ήδη.");
                }
                if (em_count > 0 || afm_count > 0) {
                    result.put("status", "error");
                    return ok(result);
                }
                InstallersEntity inst = Json.fromJson(json.findPath("data"), InstallersEntity.class);
                //dhmiourgia kwdidou pwd 6pshfia
                String pwd = getSaltString();
                Query qPwd = JPA.em().createQuery("SELECT i FROM InstallersEntity i ");
                //elegxos ean o kwdikos uparxei hdh
                List<InstallersEntity> instList = qPwd.getResultList();
                //dhmiourgia userId 6pshfia
                int userId = generateRandomDigits(4);
                for (int i = 0; i < instList.size(); i++) {
                    if (userId == instList.get(i).getUserId()) {
                        i = 0;
                        userId = generateRandomDigits(4);
                    }
                }
                if (userId != 0) {
                    inst.setUserId(userId);
                }
                //kane ton inactive
                inst.setActive((byte) 0);
                //User
                UsersEntity u = new UsersEntity();
                u.setPassword("null");
                u.setUserId(inst.getUserId());
                u.setRole("installer");
                u.setCreateDate(new Date());
                u.setActive((byte) 0);
                u.setEmail(inst.getEmail());
                JPA.em().persist(inst);
                JPA.em().persist(u);
                result.put("status", "ok");
                result.put("message", "Η καταχώρηση νέου Χρήστη έγινε με επιτυχία."+inst.getUserId() +"/"+u.getId());
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
    public Result checkEmailIfExist() {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                String email = json.findPath("email").asText();
                Query qUsers = JPA.em().createQuery("SELECT u FROM UsersEntity u");
                List<UsersEntity> usList = qUsers.getResultList();
                for (UsersEntity u : usList) {
                    if (email.equalsIgnoreCase(u.getEmail())) {
                        if (u.getActive() != null) {
                            if (u.getActive() == 1) {
                                Query qPwd = JPA.em().createQuery("SELECT i FROM InstallersEntity i ");
                                //elegxos ean o kwdikos uparxei hdh
                                String pwd = getSaltString();
                                u.setPassword(encrypt(pwd));
                                int userId = u.getUserId();
                                //TODO:APOSTOLH EMAIL ME TA ENHMERWMENA STOIXEIA STON XRHSTH

                                String subject = "Αλλαγή κωδικού πρόσβασης";
                                String message = "Τα στοιχεία εισόδου για το userId " + u.getUserId() + " έχουν αλλάξει επιτυχώς. " +
                                        "Ο νεός κωδικός σας είναι: " + pwd;
                                if (u.getRole().equals("installer")) {
                                    InstallersEntity installer = JPA.em().find(InstallersEntity.class, userId);
                                    sendMail(installer.getEmail(), subject, message);
                                } else {
                                    sendMail(u.getEmail(), subject, message);
                                }
                                result.put("status", "ok");
                                result.put("message", "Eπιτυχής επαλήθευση στοιχείων");
                                return ok(result);
                            } else {
                                result.put("status", "inactive");
                                result.put("message", "O χρήστης πλέον είναι ανενεργός");
                                return ok(result);
                            }
                        } else {
                            result.put("status", "error");
                            result.put("active missing from DATABASE", "δεν γνωρίζουμε εάν ο χρήστης είναι ενεργός ή ανενεργός");
                        }
                        result.put("status", "ok");
                        result.put("message", "ο Χρήστης που εκχωρήσατε βρέθηκε!");
                        return ok(result);
                    }
                }
                result.put("status", "error");
                result.put("message", "ο Χρήστης που εκχωρήσατε δεν βρέθηκε!!");
                return ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την αναζήτηση.");
            return ok(result);
        }
    }


    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    //ayth h methodos ekteleitai ka8e fora pou eisagetai mia parametros
    public Result getUsers() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = Controller.request().body().asJson();
            int id = json.findPath("userId").asInt();
            ObjectMapper ow = new ObjectMapper();
            Query q = JPA.em().createQuery("SELECT b FROM UsersEntity b ");
            HashMap<String, Object> returnList = new HashMap<String, Object>();
            List<UsersEntity> iList = q.getResultList();
            List<HashMap<String, Object>> instFlist = new ArrayList<HashMap<String, Object>>();
            Integer total = q.getResultList().size();
            String jsonResult = "";
            for (UsersEntity i : iList) {
                HashMap<String, Object> pt = new HashMap<String, Object>();
                pt.put("userId", i.getUserId());
                pt.put("pwd", i.getPassword());
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
    public Result getPasswords() throws IOException {
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = Controller.request().body().asJson();
            int id = json.findPath("userId").asInt();
            ObjectMapper ow = new ObjectMapper();
            Query q = JPA.em().createQuery("SELECT b FROM UsersEntity b ");
            HashMap<String, Object> returnList = new HashMap<String, Object>();
            List<UsersEntity> iList = q.getResultList();
            List<HashMap<String, Object>> instFlist = new ArrayList<HashMap<String, Object>>();
            Integer total = q.getResultList().size();
            String jsonResult = "";
            for (UsersEntity i : iList) {
                HashMap<String, Object> pt = new HashMap<String, Object>();
                pt.put("userId", i.getUserId());
                pt.put("pwd",  decrypt(i.getPassword()));
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




    public Result sendMail(String recipient, String subject, String message) {
        ObjectNode result = Json.newObject();
        String hostName = configuration.getString("host_name_mail");
        String hostUsername = configuration.getString("username_mail_host");
        String hostPassword = configuration.getString("password_mail_host");
        Integer smtpPort = configuration.getInt("port_mail_host");
        Boolean sslOn = configuration.getBoolean("email_ssl");
        Boolean emailAuthentication = configuration.getBoolean("email_authentication");
        //Boolean trustSSLServer = configuration.getBoolean("ssl_trust");
        try {
            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", hostName);
            props.put("mail.smtp.port", smtpPort);
            try {
                Email email = new SimpleEmail();
                email.setBounceAddress(hostUsername);
                email.setHostName(hostName);
                if (emailAuthentication) {
                    email.setAuthenticator(new DefaultAuthenticator(hostUsername, hostPassword));
                }
                email.setSmtpPort(smtpPort);
                email.setFrom(String.valueOf(new InternetAddress(hostUsername, "HELLAS DMS")));
                email.addTo(recipient);
                email.setSSLOnConnect(sslOn);
                email.setSubject(subject);
                email.setMsg(message);
                email.send();
                result.put("status", "ok");
                result.put("message", "Email sent");
            } catch (Exception e) {
                result.put("status", "error");
                result.put("message", e.getMessage());
                e.printStackTrace();
            }
        } catch (Throwable t) {
            result.put("status", "error");
            result.put("message", t.getMessage());
            t.printStackTrace();
        }
        return ok(result);
    }

    public Result sendMailTest(String recipient) {
        ObjectNode result            =     Json.newObject();
        String  hostName             =     configuration.getString("host_name_mail");
        String  hostUsername         =     configuration.getString("username_mail_host");
        String  hostPassword         =     configuration.getString("password_mail_host");
        Integer smtpPort             =     configuration.getInt(   "port_mail_host");
        Boolean sslOn                =     configuration.getBoolean("email_ssl");
        Boolean emailAuthentication  =     configuration.getBoolean("email_authentication");
        //Boolean trustSSLServer = configuration.getBoolean("ssl_trust");
        try {
            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", hostName);
            props.put("mail.smtp.port", smtpPort);
            try {
                Email email = new SimpleEmail();
                email.setBounceAddress(hostUsername);
                email.setHostName(hostName);
                if (emailAuthentication) {
                    email.setAuthenticator(new DefaultAuthenticator(hostUsername, hostPassword));
                }
                email.setSmtpPort(smtpPort);
                email.setFrom(hostUsername);
                email.addTo(recipient);
                email.setSSLOnConnect(sslOn);
                email.setSubject("testt");
                email.setMsg("test2");
                email.send();
                result.put("status", "ok");
                result.put("message", "Email sent");
            } catch (Exception e) {
                result.put("status", "error");
                result.put("message", e.getMessage());
                e.printStackTrace();
            }
        } catch (Throwable t) {
            result.put("status", "error");
            result.put("message", t.getMessage());
            t.printStackTrace();
        }
        return ok(result);
    }


    @SuppressWarnings("Duplicates")
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updatePwd() {//tha pernei san parametro se ena json to id tou egkatastath kai to pwd pros ananewsh
        ObjectNode result = Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                String pwd = encrypt(json.findPath("pwd").asText());
                Integer userId = json.findPath("userId").asInt();
                InstallersEntity ins = Json.fromJson(json, InstallersEntity.class);
                ins.setUserId(userId);
                //ins.setPwd(pwd);
                ins.setLastUpdatePwd(new Date());
                JPA.em().merge(ins);
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


    @SuppressWarnings("Duplicates")
    //
    @play.db.jpa.Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result login() {//login apo ton pinaka me tous users
        Query qUserstest = JPA.em().createQuery("SELECT u FROM UsersEntity u ");
        List<UsersEntity> usListtest = qUserstest.getResultList();
        for (UsersEntity ue : usListtest) {
            System.out.println("userId:" + ue.getUserId());
            try {
                System.out.println(" /pwd:" + decrypt(ue.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            ObjectMapper ow = new ObjectMapper();
            ObjectNode result = Json.newObject();
            JsonNode json = request().body().asJson();
            if (json == null) {
                return badRequest("Expecting Json data");
            } else {
                Integer userId = json.findPath("userId").asInt();
                String pwd = json.findPath("pwd").asText();
                if (userId == null || pwd == null || pwd.equalsIgnoreCase("") || userId == 0) {
                    result.put("status", "invalidCredentials");
                    result.put("message", "Παρακαλώ καταχωρήστε ορθά στοιχεία.");
                    return ok(result);
                }
                int us_count = 0;
                int pwd_count = 0;
                Query qUsers = JPA.em().createQuery("SELECT u FROM UsersEntity u where u.userId = " + userId);
                List<UsersEntity> usList = qUsers.getResultList();
                for (UsersEntity us : usList) {
                    // ayto  gia apokruptografhsh apo ton encrypt algorithmo
                    String encryptPassword = encrypt(pwd);
                    if (userId.equals(us.getUserId()) && encryptPassword.equalsIgnoreCase(us.getPassword())) {
                        //ta stoixeia tairiazoun,,epomenws elegxo an einai active h inactive
                        if (us.getActive() != null) {
                            if (us.getActive() == 1) {
                                result.put("status", "ok");
                                result.put("role", us.getRole());
                                result.put("message", "Eπιτυχής επαλήθευση στοιχείων");
                                return ok(result);
                            } else {
                                result.put("status", "inactive");
                                result.put("message", "O χρήστης πλέον είναι ανενεργός");
                                return ok(result);
                            }
                        } else {
                            result.put("status", "error");
                            result.put("active missing from DATABASE", "δεν γνωρίζουμε εάν ο χρήστης είναι ενεργός ή ανενεργός");
                        }
                    }
                }
                result.put("status", "invalidCredentials");
                result.put("message", "Πρόβλημα κατά την επαλήθευση των στοιχείων.");
                return ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά ττην επαλήθευση των στοιχείων.");
            return ok(result);
        }
    }


    @SuppressWarnings("Duplicates")

    //todo:set enabled
    @Transactional
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @BodyParser.Of(BodyParser.Json.class)
    public Result setActive() {
        try {
            ObjectNode result = Json.newObject();
            JsonNode json = request().body().asJson(); //
            int active = json.findPath("data").findPath("active").asInt();
            int userId = json.findPath("data").findPath("userId").asInt();
            Query qUsers = JPA.em().createQuery("SELECT u FROM UsersEntity u where userId= " + userId);
            List<UsersEntity> usList = qUsers.getResultList();
            UsersEntity u = usList.get(0);
            u.setActive((byte) active);
            JPA.em().merge(u);
            InstallersEntity installer = JPA.em().find(InstallersEntity.class, userId);
            installer.setActive((byte) active);
            JPA.em().merge(installer);
            //TODO: EDW THA MPEI KWDIKOS POU 8A STELNEI EMAIL STON XRHSTH ME TA KAINOURIA TOU STOIXEIA
            if (active == 1) {
                String pwd = getSaltString();
                u.setPassword(encrypt(pwd));
                String subject = "Ενεργοποίηση Λογαριασμού";
                String message = "Ο λογαριασμός σας ενεργοποιήθηκε επιτυχώς. Τα στοιχεία εισόδου σας είναι τα ακόλουθα: userId: " + u.getUserId() + " και password: " + decrypt(u.getPassword());
                sendMail(installer.getEmail(), subject, message);
            }
            result.put("status", "ok");
            result.put("message", "Η ενημέρωση έγινε με επιτυχία ,ο χρήστης είναι πλέον ενεργός.");
            return ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την ενημέρωση.");
            return ok(result);
        }
    }


    @SuppressWarnings("Duplicates")
    protected String getSaltString() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
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


    @Transactional
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @BodyParser.Of(BodyParser.Json.class)
    public Result sendMessage() {
        try {
            ObjectNode result = Json.newObject();
            JsonNode json = request().body().asJson();
            String messageForm = json.findPath("message").asText();
            int userId = json.findPath("userId").asInt();
            String contactEmail = configuration.getString("contactEmail");

            Query qUsers = JPA.em().createQuery("SELECT u FROM UsersEntity u where u.userId = " + userId);
            List<UsersEntity> usList = qUsers.getResultList();
            if (usList.size() == 1) {
                String subject = "Μήνυμα από εγκαταστάτη με userId: " + userId + " & email: " + usList.get(0).getEmail();
                String message = "Μήνυμα: " + messageForm;
                sendMail(contactEmail, subject, message);
                result.put("status", "ok");
                result.put("message", "Η ενημέρωση έγινε με επιτυχία.");
            } else {
                result.put("status", "error");
            }
            // UsersEntity user  = JPA.em().find(UsersEntity.class, userId);
            return ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode result = Json.newObject();
            result.put("status", "error");
            result.put("message", "Πρόβλημα κατά την ενημέρωση.");
            return ok(result);
        }
    }

}
