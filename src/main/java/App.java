import java.util.Map;
import java.util.List;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
         HashMap<String, Object> model = new HashMap<String, Object>();
         model.put("template", "templates/index.vtl");
         return new ModelAndView(model, layout);
       }, new VelocityTemplateEngine());

    post("/cuisines", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        String type = request.queryParams("type");
        Cuisine newCuisine = new Cuisine(type);
        newCuisine.save(); // *** ADDED FOR DB VERSION ***
        model.put("template", "templates/cuisine-success.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    get("/cuisines", (request, response) -> {
        HashMap<String, Object>model = new HashMap<String, Object>();
        model.put("cuisines", Cuisine.all());
        model.put("template", "templates/cuisines.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    get("/cuisines/new", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/cuisine-form.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    get("/cuisines/:id", (request, response) -> {
       HashMap<String, Object> model = new HashMap<String, Object>();
       Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));
       model.put("cuisine", cuisine);
       model.put("template", "templates/cuisine.vtl");
       return new ModelAndView(model, layout);
     }, new VelocityTemplateEngine());

     get("/cuisines/:id/restaurants/new", (request, response) -> {
         HashMap<String, Object> model = new HashMap<String, Object>();
         Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));
         model.put("cuisine", cuisine);
         model.put("template", "templates/restaurant-form.vtl");
         return new ModelAndView(model, layout);
       }, new VelocityTemplateEngine());

    //  get("/restaurants", (request, response) -> {
    //      HashMap<String, Object> model = new HashMap<String, Object>();
    //      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.queryParams("cuisine_id")));
    //      List<Restaurant> restaurants = cuisine.getRestaurants();
    //      model.put("restaurants", Restaurant.all());
    //      model.put("template", "templates/restaurants.vtl");
    //      return new ModelAndView(model, layout);
    //    }, new VelocityTemplateEngine());



    post("/restaurants", (request, response) -> {
       HashMap<String, Object> model = new HashMap<String, Object>();
       Cuisine cuisine = Cuisine.find(Integer.parseInt(request.queryParams("cuisine_id")));
       String name = request.queryParams("name");
       String location =  request.queryParams("location");
       int price = Integer.parseInt(request.queryParams("price"));
       Restaurant newRestaurant = new Restaurant(name, cuisine.getId(), location, price);
       newRestaurant.save();
       model.put("cuisine", cuisine);
       model.put("template", "templates/cuisine-restaurants-success.vtl");
       return new ModelAndView(model, layout);
     }, new VelocityTemplateEngine());




   }
 }
