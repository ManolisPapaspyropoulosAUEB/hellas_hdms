package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cdata", schema = "hdms_ibo_db_pr", catalog = "")
public class CdataEntity {
  private int id;
  private String col1;
  private String col2;
  private String col3;
  private String col4;
  private String col5;
  private String col6;

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
  @Column(name = "col1")
  public String getCol1() {
    return col1;
  }

  public void setCol1(String col1) {
    this.col1 = col1;
  }

  @Basic
  @Column(name = "col2")
  public String getCol2() {
    return col2;
  }

  public void setCol2(String col2) {
    this.col2 = col2;
  }

  @Basic
  @Column(name = "col3")
  public String getCol3() {
    return col3;
  }

  public void setCol3(String col3) {
    this.col3 = col3;
  }

  @Basic
  @Column(name = "col4")
  public String getCol4() {
    return col4;
  }

  public void setCol4(String col4) {
    this.col4 = col4;
  }

  @Basic
  @Column(name = "col5")
  public String getCol5() {
    return col5;
  }

  public void setCol5(String col5) {
    this.col5 = col5;
  }

  @Basic
  @Column(name = "col6")
  public String getCol6() {
    return col6;
  }

  public void setCol6(String col6) {
    this.col6 = col6;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CdataEntity that = (CdataEntity) o;
    return id == that.id &&
      Objects.equals(col1, that.col1) &&
      Objects.equals(col2, that.col2) &&
      Objects.equals(col3, that.col3) &&
      Objects.equals(col4, that.col4) &&
      Objects.equals(col5, that.col5) &&
      Objects.equals(col6, that.col6);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, col1, col2, col3, col4, col5, col6);
  }
}
