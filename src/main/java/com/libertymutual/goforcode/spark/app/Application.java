package com.libertymutual.goforcode.spark.app;

import static spark.Spark.*;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.libertymutual.goforcode.spark.app.controllers.ApartmentController;
import com.libertymutual.goforcode.spark.app.controllers.HomeController;
import com.libertymutual.goforcode.spark.app.controllers.SessionController;
import com.libertymutual.goforcode.spark.app.utilities.MustacheRenderer;

public class Application {
	
	public static void main(String[] args) {

		
		get("/", 					HomeController.index);
		
		get("/apartments/:id", 		ApartmentController.details);
		
		get("/login", 				SessionController.newForm);
		
		post("/login", 				SessionController.create);
			}

}
