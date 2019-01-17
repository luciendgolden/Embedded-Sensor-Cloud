package at.technikum.swe.DAO;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import at.technikum.swe.domain.Temperature;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureDAOTest {

  @Mock
  private Connection con;
  @Mock
  private PreparedStatement statement;
  @Mock
  private ResultSet resultSet;
  private Temperature temperature;

  public void setUp() throws Exception {
    Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true)
        .thenReturn(false);
    Mockito.when(resultSet.getString(1)).thenReturn("table_r3").thenReturn("table_r1")
        .thenReturn("table_r2");

    Mockito.when(statement.executeQuery("SELECT * FROM TEMPERATURE")).thenReturn(resultSet);

    Connection jdbcConnection = Mockito.mock(Connection.class);
    Mockito.when(jdbcConnection.createStatement()).thenReturn(statement);
  }

  public void testInsert() {
  }

  public void testFindByDate() {
  }

  public void testFindDateBetween() {
  }

  public void testCountDateBetween() {
  }

  public void testListAll() {
  }
}