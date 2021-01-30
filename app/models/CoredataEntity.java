package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "coredata", schema = "hdms_ibo_db_pr", catalog = "")
public class CoredataEntity {
  private int id;
  private String title;
  private String primary;
  private String portprimary;
  private String secondary;
  private String portsecondary;
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
  @Column(name = "portprimary")
  public String getPortprimary() {
    return portprimary;
  }

  public void setPortprimary(String portprimary) {
    this.portprimary = portprimary;
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
  @Column(name = "portsecondary")
  public String getPortsecondary() {
    return portsecondary;
  }

  public void setPortsecondary(String portsecondary) {
    this.portsecondary = portsecondary;
  }

  @Basic
  @Column(name = "type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }








}
