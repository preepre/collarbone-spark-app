package com.libertymutual.goforcode.spark.app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.libertymutual.goforcode.spark.app.models.Apartment;
import com.libertymutual.goforcode.spark.app.utilities.AutocloseableDb;
import com.libertymutual.goforcode.spark.app.utilities.MustacheRenderer;
import com.libertymutual.goforcode.spark.app.utilities.VelocityTemplateEngine;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

public class HomeController {

	public static final Route index = (Request req, Response res) -> {

		try (AutocloseableDb db = new AutocloseableDb()) {
			List<Apartment> apartments = Apartment.findAll();

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("noUser", req.session().attribute("currentUser") == null);
			model.put("apartments", apartments);
			model.put("currentUser", req.session().attribute("currentUser"));

			List<Apartment> activeApartments = new ArrayList<>();
			List<Apartment> inactiveApartments = new ArrayList<>();
			
			for(Apartment a : apartments) {
				if(a.getIsActive() ) {
					activeApartments.add(a);
				}
				else {
					inactiveApartments.add(a);
				}
			}

			model.put("activeApartments", activeApartments);
			model.put("inactiveApartments", inactiveApartments);
			
			
			// delete below line and create your own index

//			return new VelocityTemplateEngine().render(new ModelAndView(model, "/templates/home/indexVelocity.html"));
		
			 return MustacheRenderer.getInstance()
			 .render("home/index.html", model);
		}
	};

	// return MustacheRenderer.getInstance()
	// .render("home/index.html", model);

}
