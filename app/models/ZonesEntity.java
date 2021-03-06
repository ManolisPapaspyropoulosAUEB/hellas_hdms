package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "zones", schema = "hdms_ibo_db_pr", catalog = "")
public class ZonesEntity {
    private int zoneId;
    private Byte editable;
    private String name;
    private long customerId;
    private String idByUser;
    private Byte visible;
    private String lastAction;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zone_id")

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
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
    @Column(name = "id_by_user")
    public String getIdByUser() {
        return idByUser;
    }

    public void setIdByUser(String idByUser) {
        this.idByUser = idByUser;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZonesEntity that = (ZonesEntity) o;
        return zoneId == that.zoneId &&
                customerId == that.customerId &&
                Objects.equals(editable, that.editable) &&
                Objects.equals(name, that.name) &&
                Objects.equals(idByUser, that.idByUser) &&
                Objects.equals(visible, that.visible) &&
                Objects.equals(lastAction, that.lastAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, editable, name, customerId, idByUser, visible, lastAction);
    }
}
