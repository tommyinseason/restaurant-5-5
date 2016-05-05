import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class RestaurantTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/best_restaurants_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteRestaurantQuery = "DELETE FROM restaurants *;";
      String deleteCuisineQuery = "DELETE FROM cuisines *;";
      con.createQuery(deleteRestaurantQuery).executeUpdate();
      con.createQuery(deleteCuisineQuery).executeUpdate();
    }
  }

  @Test
  public void Restaurant_instantiatesCorrectly_true() {
    Restaurant myRestaurant = new Restaurant("Thai E San", 1, "SW", 2);
    assertEquals(true, myRestaurant instanceof Restaurant);
  }

  @Test
  public void getName_instantiatesWithName_String() {
    Restaurant myRestaurant = new Restaurant("Thai E San", 1, "SW", 2);
    assertEquals("Thai E San", myRestaurant.getName());
  }

  @Test
  public void getLocation_instantiatesWithLocation_String() {
    Restaurant myRestaurant = new Restaurant("Thai E San", 1, "SW", 2);
    assertEquals("SW", myRestaurant.getLocation());
  }

  @Test
  public void getPrice_instantiatesWithPrice_int() {
    Restaurant myRestaurant = new Restaurant("Thai E San", 1, "SW", 2);
    assertEquals(2, myRestaurant.getPrice());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Restaurant.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame() {
    Restaurant firstRestaurant = new Restaurant("Thai E San", 1, "SW", 2);
    Restaurant secondRestaurant = new Restaurant("Thai E San", 1, "SW", 2);
    assertTrue(firstRestaurant.equals(secondRestaurant));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAreTheSame() {
    Restaurant myRestaurant = new Restaurant("Thai E San", 1, "SW", 2);
    myRestaurant.save();
    assertTrue(Restaurant.all().get(0).equals(myRestaurant));
  }

  @Test
  public void save_assignsIdToObject() {
    Restaurant myRestaurant = new Restaurant("Thai E San", 1, "SW", 2);
    myRestaurant.save();
    Restaurant savedRestaurant = Restaurant.all().get(0);
    assertEquals(myRestaurant.getId(), savedRestaurant.getId());
  }

  @Test
  public void find_findsRestaurantInDatabase_true() {
    Restaurant myRestaurant = new Restaurant ("Thai E San", 1, "SW", 2);
    myRestaurant.save();
    Restaurant savedRestaurant = Restaurant.find(myRestaurant.getId());
    assertTrue(myRestaurant.equals(savedRestaurant));
  }
}
