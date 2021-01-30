package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "info_coredata", schema = "hdms_ibo_db_pr", catalog = "")
public class InfoCoredataEntity {
  private int id;
  private String title;
  private String primary;
  private String portPrimary;
  private String secondary;
  private String portSecondary;
  private String type;

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
  @Column(name = "title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Basic
  @Column(name = "primary")
  public String getPrimary() {
    return primary;
  }

  public void setPrimary(String primary) {
    this.primary = primary;
  }

  @Basic
  @Column(name = "port_primary")
  public String getPortPrimary() {
    return portPrimary;
  }

  public void setPortPrimary(String portPrimary) {
    this.portPrimary = portPrimary;
  }

  @Basic
  @Column(name = "secondary")
  public String getSecondary() {
    return secondary;
  }

  public void setSecondary(String secondary) {
    this.secondary = secondary;
  }

  @Basic
  @Column(name = "port_secondary")
  public String getPortSecondary() {
    return portSecondary;
  }

  public void setPortSecondary(String portSecondary) {
    this.portSecondary = portSecondary;
  }

  @Basic
  @Column(name = "type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InfoCoredataEntity that = (InfoCoredataEntity) o;
    return id == that.id &&
      Objects.equals(title, that.title) &&
      Objects.equals(primary, that.primary) &&
      Objects.equals(portPrimary, that.portPrimary) &&
      Objects.equals(secondary, that.secondary) &&
      Objects.equals(portSecondary, that.portSecondary) &&
      Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, primary, portPrimary, secondary, portSecondary, type);
  }
}
