package com.libertymutual.goforcode.spark.app;

import static spark.Spark.*;

import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.spark.app.controllers.ApartmentApiController;
import com.libertymutual.goforcode.spark.app.controllers.ApartmentController;
import com.libertymutual.goforcode.spark.app.controllers.UserController;
import com.libertymutual.goforcode.spark.app.controllers.HomeController;
import com.libertymutual.goforcode.spark.app.controllers.SessionController;
import com.libertymutual.goforcode.spark.app.models.Apartment;
import com.libertymutual.goforcode.spark.app.models.User;
import com.libertymutual.goforcode.spark.app.utilities.AutocloseableDb;

public class Application {

	public static void main(String[] args) {

		String encryptedPassword = BCrypt.hashpw("password", BCrypt.gensalt());

		try (AutocloseableDb db = new AutocloseableDb()) {
			User.deleteAll();
			new User("a@a.com", encryptedPassword, "Anya", "Marie").saveIt();
			
			Apartment.deleteAll();
			new Apartment(6200, 1, 0, 350, "123 Main St", "San Francisco", 
					"CA", "95125").saveIt();
			new Apartment(1400, 5, 6, 4000, "123 Cowboy Way", "Houston", 
					"CA", "77096").saveIt();
		}
		
		

		get("/", 				HomeController.index);

		get("/apartments/:id", 	ApartmentController.details);

		get("/login", 			SessionController.newForm);

		post("/login", 			SessionController.create);
		
		get("/signup", 			UserController.newForm);
		
//		post("/users", 			UserController.create);
		
		path("/api", () -> {
			
			get("/apartments/:id", 	ApartmentApiController.details);
			
			post("/apartments", 	ApartmentApiController.create);			
			
		});
	}

}
