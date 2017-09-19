package com.libertymutual.goforcode.spark.app.controllers;

import com.libertymutual.goforcode.spark.app.models.User;
import com.libertymutual.goforcode.spark.app.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;
	
public class SessionController {
	
	public static final Route newForm = (Request req, Response res) -> {
		
		return MustacheRenderer.getInstance().render("session/newForm.html", null);

	};
	
	
	public static final Route create = (Request req, Response res) -> {
		String email = req.queryParams("email");
		String password = req.queryParams("password");
		
		User user = new User(email, password, "Ernest", "Hemingway");
		req.session().attribute("currentUser", user);
		
		res.redirect("/");
		
		return "";
	};

}
