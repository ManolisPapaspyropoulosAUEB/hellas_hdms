package models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "zones_revisions", schema = "hdms_ibo_db_pr", catalog = "")
public class ZonesRevisionsEntity {
    private int id;
    private Byte editable;
    private String name;
    private long customerId;
    private Integer revision;
    private Date revisionDate;
    private Byte visible;
    private String lastAction;
    private Integer zoneId;
    private Integer revisionId;
    private String idByUser;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "customer_id")
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
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
    @Column(name = "zone_id")
    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    @Basic
    @Column(name = "revision_id")
    public Integer getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(Integer revisionId) {
        this.revisionId = revisionId;
    }

    @Basic
    @Column(name = "id_by_user")
    public String getIdByUser() {
        return idByUser;
    }

    public void setIdByUser(String idByUser) {
        this.idByUser = idByUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZonesRevisionsEntity that = (ZonesRevisionsEntity) o;
        return id == that.id &&
                customerId == that.customerId &&
                Objects.equals(editable, that.editable) &&
                Objects.equals(name, that.name) &&
                Objects.equals(revision, that.revision) &&
                Objects.equals(revisionDate, that.revisionDate) &&
                Objects.equals(visible, that.visible) &&
                Objects.equals(lastAction, that.lastAction) &&
                Objects.equals(zoneId, that.zoneId) &&
                Objects.equals(revisionId, that.revisionId) &&
                Objects.equals(idByUser, that.idByUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, editable, name, customerId, revision, revisionDate, visible, lastAction, zoneId, revisionId, idByUser);
    }
}
