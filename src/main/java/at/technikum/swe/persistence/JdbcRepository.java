package at.technikum.swe.persistence;

import at.technikum.swe.domain.BaseModel;
import at.technikum.swe.exception.PersistenceException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface JdbcRepository<E extends BaseModel<E,K>, K extends Number>{
  int save(Connection con, E entity) throws PersistenceException;

  int delete(Connection con, K id) throws PersistenceException;

  E findById(Connection con, K key) throws PersistenceException;

  List<E> listAll(Connection con) throws SQLException;
}
