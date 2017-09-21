package com.libertymutual.goforcode.spark.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.javalite.common.JsonHelper;
import org.mindrot.jbcrypt.BCrypt;

import com.github.mustachejava.MustacheResolver;
import com.libertymutual.goforcode.spark.app.models.Apartment;
import com.libertymutual.goforcode.spark.app.models.User;
import com.libertymutual.goforcode.spark.app.utilities.AutocloseableDb;
import com.libertymutual.goforcode.spark.app.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {

	
	public static final Route newForm = (Request req, Response res) -> {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("currentUser", req.session().attribute("currentUser"));
		model.put("noUser", req.session().attribute("currentUser") == null);

		return MustacheRenderer.getInstance().render("users/signup.html", model);

	};
	
	public static final Route create = (Request req, Response res) -> {
		String encryptedPassword = BCrypt.hashpw(req.queryParams("password"), BCrypt.gensalt());
		
		User user = new User(
				req.queryParams("email"),
				encryptedPassword,
				req.queryParams("firstName"),
				req.queryParams("lastName")
				
				);
		
		try(AutocloseableDb db = new AutocloseableDb()){
			user.saveIt();
			req.session().attribute("currentUser", user);
			res.redirect("/");
			return "";
		}

	};

}
