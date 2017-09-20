package com.libertymutual.goforcode.spark.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.javalite.activejdbc.Model;
import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.spark.app.models.User;
import com.libertymutual.goforcode.spark.app.utilities.AutocloseableDb;
import com.libertymutual.goforcode.spark.app.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class SessionController {

	public static final Route newForm = (Request req, Response res) -> {

		Map<String, Object> model = new HashMap<String, Object>();	
		model.put("returnPath", req.queryParams("returnPath"));
		return MustacheRenderer.getInstance().render("session/newForm.html", model);

	};

	public static final Route create = (Request req, Response res) -> {
		String email = req.queryParams("email");
		String password = req.queryParams("password");

		try (AutocloseableDb db = new AutocloseableDb()) {
			User user = User.findFirst("email = ?", email);
			if (user != null && BCrypt.checkpw(password, user.getPassword())) {
				req.session().attribute("currentUser", user);
			}
		}
		res.redirect(req.queryParamOrDefault("returnPath", "/"));

		return "";
	};

	public static Route logout= (Request req, Response res) -> {
		
		try (AutocloseableDb db = new AutocloseableDb()) {
			req.session().attribute("currentUser", null);
			res.redirect("/");
		}
		
		return "";
	};

}
