package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phone_notices_cc", schema = "hdms_ibo_db_pr", catalog = "")
public class PhoneNoticesCcEntity {
    private int id;
    private int phoneNoticesId;
    private Byte editable;
    private String phone;
    private String name;
    private long customerId;
    private Byte visible;
    private String lastAction;
    private String username;
    private Integer revision;
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
    @Column(name = "phone_notices_id")
    public int getPhoneNoticesId() {
        return phoneNoticesId;
    }

    public void setPhoneNoticesId(int phoneNoticesId) {
        this.phoneNoticesId = phoneNoticesId;
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
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
    @Column(name = "revision_id")
    public Integer getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(Integer revisionId) {
        this.revisionId = revisionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNoticesCcEntity that = (PhoneNoticesCcEntity) o;
        return id == that.id &&
                phoneNoticesId == that.phoneNoticesId &&
                customerId == that.customerId &&
                Objects.equals(editable, that.editable) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(name, that.name) &&
                Objects.equals(visible, that.visible) &&
                Objects.equals(lastAction, that.lastAction) &&
                Objects.equals(username, that.username) &&
                Objects.equals(revision, that.revision) &&
                Objects.equals(revisionId, that.revisionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNoticesId, editable, phone, name, customerId, visible, lastAction, username, revision, revisionId);
    }
}
