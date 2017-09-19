package com.libertymutual.goforcode.spark.app.controllers;

import com.libertymutual.goforcode.spark.app.models.Apartment;
import com.libertymutual.goforcode.spark.app.utilities.AutocloseableDb;


import spark.*;
import static spark.Spark.*;

import java.util.Map;

import org.javalite.common.JsonHelper;

public class ApartmentApiController {
	
	public static final Route details = (Request req, Response res) -> {
		
		try(AutocloseableDb db = new AutocloseableDb()) {
			
			String idAsString = req.params("id");
			int id = Integer.parseInt(idAsString);
			
			Apartment apartment = Apartment.findById(id);
			
			if(apartment != null) {
				res.header("Content-Type", "application/json");
				return apartment.toJson(true);
				
			}
			
			notFound("Did not find that");
			return "";
			
		}
	};
	
	
	public static Route create = (Request req, Response res) -> {
		
		String json = req.body();
		Map map = JsonHelper.toMap(json);
		
		Apartment apartment = new Apartment();
		apartment.fromMap(map);
		
		try(AutocloseableDb db = new AutocloseableDb()){
			apartment.saveIt();
			res.status(201);
			return apartment.toJson(true);
		}
			
	};

}
