import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.sql2o.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
   public WebDriver getDefaultDriver() {
     return webDriver;
   }

   @ClassRule
   public static ServerRule server = new ServerRule();


   @Before
   public void setUp(){
     DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/best_restaurants_test", null, null);

   }
   @After
     public void tearDown() {
     try(Connection con = DB.sql2o.open()) {
       String deleteRestaurantsQuery = "DELETE FROM restaurants *;";
       String deleteCuisinesQuery = "DELETE FROM cuisines *;";
       con.createQuery(deleteRestaurantsQuery).executeUpdate();
       con.createQuery(deleteCuisinesQuery).executeUpdate();
     }
   }
   @Test
   public void rootTest() {
     goTo("http://localhost:4567/");
     assertThat(pageSource()).contains("Restaurant Cuisines");
     assertThat(pageSource()).contains("View Cuisines List");
     assertThat(pageSource()).contains("Add a New Cuisine");
   }

   @Test
   public void cuisineIsCreatedTest() {
     goTo("http://localhost:4567/");
     click("a", withText("Add a New Cuisine"));
     fill("#type").with("Mexican");
     submit(".btn");
     assertThat(pageSource()).contains("Your cuisine has been saved.");
   }

   @Test
   public void cuisineIsDisplayedTest() {
     Cuisine myCuisine = new Cuisine("Mexican");
     myCuisine.save();
     String cuisinePath = String.format("http://localhost:4567/cuisines/%d", myCuisine.getId());
     goTo(cuisinePath);
     assertThat(pageSource()).contains("Mexican");
   }

   @Test
   public void cuisineShowPageDisplaysName() {
     goTo("http://localhost:4567/cuisines/new");
     fill("#type").with("Mexican");
     submit(".btn");
     click("a", withText("View Cuisines"));
     click("a", withText("Mexican"));
     assertThat(pageSource()).contains("Mexican");
   }

   @Test
   public void cuisineTasksFormIsDisplayed() {
     goTo("http://localhost:4567/cuisines/new");
     fill("#type").with("Thai");
     submit(".btn");
     click("a", withText("View Cuisines"));
     click("a", withText("Thai"));
     click("a", withText("Add a new restaurant"));
     assertThat(pageSource()).contains("Add a new restaurant:");
   }

   @Test
   public void restaurantsIsAddedAndDisplayed() {
     goTo("http://localhost:4567/cuisines/new");
     fill("#type").with("Mexican");
     submit(".btn");
     click("a", withText("View Cuisines"));
     click("a", withText("Mexican"));
     click("a", withText("Add a new restaurant"));
     fill("#name").with("Thai E San");
     fill("#location").with("SW");
     fill("#price").with("2");
     submit(".btn");
     click("a", withText("View cuisines"));
     click("a", withText("Mexican"));
     assertThat(pageSource()).contains("All cuisines");
   }
   @Test
   public void allRestaurantsDisplayNameOnCuisinePage() {
     Cuisine myCuisine = new Cuisine("Mexican");
     myCuisine.save();
     Restaurant firstRestaurant = new Restaurant("El Jefes", myCuisine.getId(), "SW", 2);
     firstRestaurant.save();
     Restaurant secondRestaurant = new Restaurant("Thai E San", myCuisine.getId(), "SW", 2);
     secondRestaurant.save();
     String cuisinePath = String.format("http://localhost:4567/cuisines/%d", myCuisine.getId());
     goTo(cuisinePath);
     assertThat(pageSource()).contains("El Jefes");
     assertThat(pageSource()).contains("Thai E San");
   }
 }
