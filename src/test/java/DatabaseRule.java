// import org.junit.rules.ExternalResource;
// import org.sql2o.*;
//
// public class DatabaseRule extends ExternalResource {
//
//   protected void before() {
//     DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/best_restaurants_test", null, null);
//     /******************************************************
//       Students: TODO: Change url to link to actual database
//       and add necessary permissions
//     *******************************************************/
//    }
//
//   protected void after() {
//     try(Connection con = DB.sql2o.open()) {
//       String deleteRestaurantsQuery = "DELETE FROM restaurants *;";
//       String deleteCuisineQuery = "DELETE FROM cuisine *;";
//       con.createQuery(deleteRestaurantsQuery).executeUpdate();
//       con.createQuery(deleteCuisineQuery).executeUpdate();
//     }
//   }
// }
