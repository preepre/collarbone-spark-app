package com.libertymutual.goforcode.spark.app;

import static spark.Spark.*;

import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.spark.app.controllers.ApartmentApiController;
import com.libertymutual.goforcode.spark.app.controllers.ApartmentController;
import com.libertymutual.goforcode.spark.app.controllers.UserController;
import com.libertymutual.goforcode.spark.app.filters.SecurityFilters;
import com.libertymutual.goforcode.spark.app.controllers.HomeController;
import com.libertymutual.goforcode.spark.app.controllers.SessionController;
import com.libertymutual.goforcode.spark.app.models.Apartment;
import com.libertymutual.goforcode.spark.app.models.User;
import com.libertymutual.goforcode.spark.app.utilities.AutocloseableDb;

import spark.Request;
import spark.Response;

public class Application {

	public static void main(String[] args) {

		String encryptedPassword = BCrypt.hashpw("password", BCrypt.gensalt());

		try (AutocloseableDb db = new AutocloseableDb()) {
			User.deleteAll();
			User anya = new User("a@a.com", encryptedPassword, "Anya", "Marie");
			anya.saveIt();
			
			
			Apartment.deleteAll();
			Apartment a = new Apartment(6200, 1, 0, 350,	 "123 Main St", "San Francisco", "CA", "95125", true);
			a.saveIt();
			anya.add(a);
			
			Apartment b = new Apartment(1400, 5, 6, 4000, "123 Cowboy Way", "Houston", "CA", "77096", true);
			b.saveIt();
			anya.add(b);
			
		}

		path("/apartments", () -> {

			before("/new", SecurityFilters.isAuthenticated);
			get("/new", ApartmentController.newForm);
			
			before("/mine", SecurityFilters.isAuthenticated);
			get("/mine", ApartmentController.index);
			
			get("/:id", ApartmentController.details);
			
			
			before("", SecurityFilters.isAuthenticated);
			post("", 	ApartmentController.create);
			
			before("/:id/deactivations", SecurityFilters.isAuthenticated);
			post("/:id/deactivations", ApartmentController.deactivations);
			
			before("/:id/activations", SecurityFilters.isAuthenticated);
			post("/:id/activations", ApartmentController.activations);
			

		});

		get("/", HomeController.index);

		get("/login", SessionController.newForm);

		post("/login", SessionController.create);

		post("/logout", SessionController.logout);

		get("/signup", UserController.newForm);

		post("/create", UserController.create);

		path("/api", () -> {

			get("/apartments/:id", ApartmentApiController.details);

			post("/apartments", ApartmentApiController.create);

		});
	}

}
