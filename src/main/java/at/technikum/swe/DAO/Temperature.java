package at.technikum.swe.DAO;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

public class Temperature extends BaseModel<Temperature, Long>{

  private LocalDate date;
  private Float minTemperature;
  private Float maxTemperature;


  public Temperature() {
    super();
  }

  public Temperature(LocalDate date,
      Float minTemperature, Float maxTemperature) {
    super();
    this.date = date;
    this.minTemperature = minTemperature;
    this.maxTemperature = maxTemperature;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Float getMinTemperature() {
    return minTemperature;
  }

  public void setMinTemperature(Float minTemperature) {
    this.minTemperature = minTemperature;
  }

  public Float getMaxTemperature() {
    return maxTemperature;
  }

  public void setMaxTemperature(Float maxTemperature) {
    this.maxTemperature = maxTemperature;
  }

  @Override
  public String toString() {
    return super.toString()+
        "date=" + date +
        ", minTemperature=" + minTemperature +
        ", maxTemperature=" + maxTemperature +
        '}';
  }

  @Override
  public int compareTo(Temperature o) {
    return 0;
  }
}
