package at.technikum.swe.persistence;

import at.technikum.swe.DAO.BaseModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractJdbcRepository<E extends BaseModel<E, K>, K extends Number>
    implements JdbcRepository<E, K> {

  private final Class<E> clazz;

  private final static Logger logger = Logger.getLogger(AbstractJdbcRepository.class.getName());

  protected List<E> entities = new ArrayList<>();

  public AbstractJdbcRepository(Class<E> clazz) {
    this.clazz = clazz;
  }

  public boolean tableExist(Connection con) throws SQLException {
    boolean tExists = false;
    final String tableName = this.clazz.getSimpleName().toUpperCase();

    try (ResultSet rs = con.getMetaData()
        .getTables(null, null, tableName, null)) {
      while (rs.next()) {
        String tName = rs.getString("TABLE_NAME");
        if (tName != null && tName.equals(tableName)) {
          tExists = true;
          break;
        }
      }
    }
    return tExists;
  }

  public abstract int createTable(Connection con) throws SQLException;

  public abstract int insert(Connection con, E entity) throws SQLException;
}

