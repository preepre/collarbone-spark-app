package com.libertymutual.goforcode.spark.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.spark.app.models.Apartment;
import com.libertymutual.goforcode.spark.app.models.User;
import com.libertymutual.goforcode.spark.app.utilities.AutocloseableDb;
import com.libertymutual.goforcode.spark.app.utilities.MustacheRenderer;

import static java.lang.Math.toIntExact;

import spark.Request;
import spark.Response;
import spark.Route;

public class ApartmentController {

	public static final Route details = (Request req, Response res) -> {

		try (AutocloseableDb db = new AutocloseableDb()) {
			Apartment apartment = Apartment.findById(Integer.parseInt(req.params("id")));

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("apartment", apartment);
			model.put("currentUser", req.session().attribute("currentUser"));
			model.put("noUser", req.session().attribute("currentUser") == null);
			
			return MustacheRenderer.getInstance().render("apartment/details.html", model);
		}
	};

	public static final Route newForm = (Request req, Response res) -> {
		return MustacheRenderer.getInstance().render("apartment/newForm.html", null);

	};

	public static final Route create = (Request req, Response res) -> {

		try (AutocloseableDb db = new AutocloseableDb()) {

			Apartment apartment = new Apartment(

					Integer.parseInt(req.queryParams("rent")), Integer.parseInt(req.queryParams("number_of_bedrooms")),
					Double.parseDouble(req.queryParams("number_of_bathrooms")),
					Integer.parseInt(req.queryParams("square_footage")), req.queryParams("address"),
					req.queryParams("city"), req.queryParams("state"), req.queryParams("zip_code"));

			apartment.saveIt();
			res.redirect("/");
			return "";
		}
	};

	public static final Route index = (Request req, Response res) -> {
		User currentUser = req.session().attribute("currentUser");
		long id = (long) currentUser.getId();

		try (AutocloseableDb db = new AutocloseableDb()) {

			List<Apartment> apartments = Apartment.where("user_id = ?", id);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("apartments", apartments);
			model.put("currentUser", req.session().attribute("currentUser"));
			return MustacheRenderer.getInstance().render("apartment/index.html", model);
		}
	};

	public static Route deactivate = (Request req, Response res) -> {

//		Apartment apartment = req.session().attribute("apartment");
		Apartment apartment = Apartment.findById(Integer.parseInt(req.params("id")));
		
//		long id = (long) apartment.getId();

		try (AutocloseableDb db = new AutocloseableDb()) {

			// get the apartment at the current id and set boolean to false
			apartment.setIsActive(false);
			apartment.saveIt();

			res.redirect("/apartments/:id");
			return "";
		}
	};
}