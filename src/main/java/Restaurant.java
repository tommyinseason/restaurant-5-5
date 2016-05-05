import org.sql2o.*;
import java.util.List;

public class Restaurant {
  private int id;
  private String name;
  private String location;
  private int price;
  private int cuisine_id;


  public Restaurant (String name, int cuisine_id, String location, int price) {
    this.name = name;
    this.cuisine_id = cuisine_id;
    this.location = location;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  public int getPrice() {
    return price;
  }

  public int getCuisineId() {
    return cuisine_id;
  }

  public static List<Restaurant> all() {
    String sql = "SELECT id, name, cuisine_id FROM restaurants";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }

  @Override
  public boolean equals(Object otherRestaurant){
    if (!(otherRestaurant instanceof Restaurant)) {
      return false;
    } else {
      Restaurant newRestaurant = (Restaurant) otherRestaurant;
      return this.getName().equals(newRestaurant.getName()) &&
        this.getId() == newRestaurant.getId();
    }
  }
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO restaurants (name, cuisine_id, location, price) VALUES (:name, :cuisine_id, :location, :price);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("cuisine_id", this.cuisine_id)
        .addParameter("location", this.location)
        .addParameter("price", this.price)
        .executeUpdate()
        .getKey();
    }
  }

  public static Restaurant find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants where id=:id";
      Restaurant restaurant = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Restaurant.class);
      return restaurant;
    }
  }
}
