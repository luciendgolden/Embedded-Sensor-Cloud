package at.technikum.swe.DAO;

import at.technikum.swe.foundation.Ensurer;
import java.time.LocalDateTime;

public abstract class BaseModel<DOMAIN_TYPE extends BaseModel, PK_TYPE extends Number>
    implements Comparable<DOMAIN_TYPE> {

  private PK_TYPE id;

  private LocalDateTime createdAt;
  private LocalDateTime lastModifiedAt;
  private LocalDateTime deletedAt;

  protected BaseModel() {}

  protected BaseModel(final PK_TYPE id) {
    this.id = Ensurer.ensureNotNull(id, "id");
  }

  public final void setId(PK_TYPE id) {
    this.id = Ensurer.ensureNotNull(id, "id");
  }

  public final PK_TYPE getId() {
    return id;
  }

  public final Boolean isNew() {
    return id == null;
  }

  public String toString() {
    return String.format("%s: {id:'%d', ", getClass().getSimpleName(),
        id);
  }
}


