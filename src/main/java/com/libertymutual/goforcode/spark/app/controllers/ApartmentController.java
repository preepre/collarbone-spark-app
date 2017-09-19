package com.libertymutual.goforcode.spark.app.controllers;

import java.util.HashMap;
import java.util.Map;

import com.libertymutual.goforcode.spark.app.models.Apartment;
import com.libertymutual.goforcode.spark.app.utilities.AutocloseableDb;
import com.libertymutual.goforcode.spark.app.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class ApartmentController {

	public static final Route details = (Request req, Response res) -> {

		try (AutocloseableDb db = new AutocloseableDb()) {
			Apartment apartment = Apartment.findById(Integer.parseInt(req.params("id")));

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("apartment", apartment);

			return MustacheRenderer.getInstance().render("apartment/details.html", model);
		}
	};

}
