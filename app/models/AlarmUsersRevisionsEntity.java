package models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "alarm_users_revisions", schema = "hdms_ibo_db_pr", catalog = "")
public class AlarmUsersRevisionsEntity {
    private int id;
    private long customerId;
    private Byte editable;
    private String name;
    private String username;
    private Integer revision;
    private Date revisionDate;
    private Byte visible;
    private String lastAction;
    private Integer alarmUserId;
    private Integer revisionId;

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "customer_id")
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "editable")
    public Byte getEditable() {
        return editable;
    }

    public void setEditable(Byte editable) {
        this.editable = editable;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "revision")
    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    @Basic
    @Column(name = "revision_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }



    @Basic
    @Column(name = "visible")
    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "last_action")
    public String getLastAction() {
        return lastAction;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    @Basic
    @Column(name = "alarm_user_id")
    public Integer getAlarmUserId() {
        return alarmUserId;
    }

    public void setAlarmUserId(Integer alarmUserId) {
        this.alarmUserId = alarmUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmUsersRevisionsEntity that = (AlarmUsersRevisionsEntity) o;
        return id == that.id &&
                customerId == that.customerId &&
                Objects.equals(editable, that.editable) &&
                Objects.equals(name, that.name) &&
                Objects.equals(username, that.username) &&
                Objects.equals(revision, that.revision) &&
                Objects.equals(revisionDate, that.revisionDate) &&
                Objects.equals(visible, that.visible) &&
                Objects.equals(lastAction, that.lastAction) &&
                Objects.equals(alarmUserId, that.alarmUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, editable, name, username, revision, revisionDate, visible, lastAction, alarmUserId);
    }

    @Basic
    @Column(name = "revision_id")
    public Integer getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(Integer revisionId) {
        this.revisionId = revisionId;
    }
}
