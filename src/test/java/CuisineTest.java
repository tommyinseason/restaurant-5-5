import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class CuisineTest {

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
  public void Cuisine_instantiatesCorrectly_true() {
    Cuisine myCuisine = new Cuisine("Mexican");
    assertEquals(true, myCuisine instanceof Cuisine);
  }

  @Test
  public void getName_cuisineInstantiatesWithName_String() {
    Cuisine myCuisine = new Cuisine("Mexican");
    assertEquals("Mexican", myCuisine.getType());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Cuisine.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Cuisine firstCuisine = new Cuisine("Mexican");
    Cuisine secondCuisine = new Cuisine("Mexican");
    assertTrue(firstCuisine.equals(secondCuisine));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Cuisine myCuisine = new Cuisine("Mexican");
    myCuisine.save();
    assertTrue(Cuisine.all().get(0).equals(myCuisine));
  }
  @Test
  public void save_assignsIdToObject() {
    Cuisine myCuisine = new Cuisine("Mexican");
    myCuisine.save();
    Cuisine savedCuisine = Cuisine.all().get(0);
    assertEquals(myCuisine.getId(), savedCuisine.getId());
  }
  @Test
  public void find_findCuisineInDatabase_true() {
    Cuisine myCuisine = new Cuisine("Mexican");
    myCuisine.save();
    Cuisine savedCuisine = Cuisine.find(myCuisine.getId());
    assertTrue(myCuisine.equals(savedCuisine));
  }
  @Test
  public void getRestaurants_retrievesAllRestaurantsFromDataBase_restaurants() {
    Cuisine myCuisine = new Cuisine("Mexican");
    myCuisine.save();

    System.out.println("mycuisineid " + myCuisine.getId());
    Restaurant firstRestaurant = new Restaurant("Super Taco", myCuisine.getId(), "SW", 2);
    firstRestaurant.save(); //name, c id, location, price
    Restaurant secondRestaurant = new Restaurant("Taqueria El Rodeo", myCuisine.getId(), "SW", 1);
    secondRestaurant.save();

    System.out.println("mycuisine restaurants " + myCuisine.getRestaurants());
    Restaurant[] restaurants = new Restaurant[] { firstRestaurant, secondRestaurant };
    assertTrue(myCuisine.getRestaurants().containsAll(Arrays.asList(restaurants)));
  }
}
