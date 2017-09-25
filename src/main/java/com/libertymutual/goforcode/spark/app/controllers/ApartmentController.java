package com.libertymutual.goforcode.spark.app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Model;
import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.spark.app.models.Apartment;
import com.libertymutual.goforcode.spark.app.models.ApartmentsUsers;
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
			User currentUser = req.session().attribute("currentUser");
					
			long userId = apartment.getUserId();
								
			boolean isOwner;
			boolean isLikeable = true;
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("apartment", apartment);
			model.put("currentUser", currentUser);
			model.put("noUser", req.session().attribute("currentUser") == null);
			
			
			if(currentUser != null && userId == (long) currentUser.getId()) {
				isOwner = true;
			}
			else {
				isOwner = false;
			}
			model.put("isOwner", isOwner);
			
			List<ApartmentsUsers> likesList = ApartmentsUsers.where("apartment_id = ?", (long) apartment.getId());
			model.put("likesCount", likesList.size());
			
			List<Long> listUserIds = new ArrayList<Long>();
			List<User> users = new ArrayList<User>();
			
			if(likesList.size() > 0) {
				for(ApartmentsUsers au : likesList) {	
					listUserIds.add(au.getUserId());
				}
				
				for(Long id : listUserIds){
					User user = User.findById(id);
					users.add(user);					
				}
			}
			model.put("users", users);
			
			if(currentUser != null) {
				for(ApartmentsUsers au : likesList) {
					if(au.getUserId() == (long) currentUser.getId()) {
						isLikeable = false; 
					}
				}
			}
			
			model.put("isLikeable", isLikeable);
			
//			List<ApartmentsUsers> userLikedList = ApartmentsUsers.findBy
//			
//			if(likesList.where("user_id = ?", id)) {
//					hasLiked = true;
//				}
//				else {
//					hasLiked = false;
//				}
//			}
			
			
//			if(req.session().attribute(""))
			
			return MustacheRenderer.getInstance().render("apartment/details.html", model);
		}
	};

	public static final Route newForm = (Request req, Response res) -> {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("currentUser", req.session().attribute("currentUser"));
		model.put("noUser", req.session().attribute("currentUser") == null);
		
		return MustacheRenderer.getInstance().render("apartment/newForm.html", model);

	};

	public static final Route create = (Request req, Response res) -> {

		try (AutocloseableDb db = new AutocloseableDb()) {
			
			User currentUser = req.session().attribute("currentUser");
			long id = (long) currentUser.getId();
			
			
			Apartment apartment = new Apartment(

					Integer.parseInt(req.queryParams("rent")), Integer.parseInt(req.queryParams("number_of_bedrooms")),
					Double.parseDouble(req.queryParams("number_of_bathrooms")),
					Integer.parseInt(req.queryParams("square_footage")), req.queryParams("address"),
					req.queryParams("city"), req.queryParams("state"), req.queryParams("zip_code"), 
					Boolean.parseBoolean(req.queryParams("is_active")));

			currentUser.add(apartment);
			
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
			
//			List<Apartment> activeApartments = Apartment.where("is_active", true);
//			List<Apartment> inactiveApartments = Apartment.where("is_active", false);			
//			
//			model.put("activeApartments", activeApartments);
//			model.put("inactiveApartments", inactiveApartments);
			
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
			
			return MustacheRenderer.getInstance().render("apartment/index.html", model);
		}
		
		
	};

	public static final Route deactivations = (Request req, Response res) -> {

		try (AutocloseableDb db = new AutocloseableDb()) {
			
			User currentUser = req.session().attribute("currentUser");
			long currentUserId = (long) currentUser.getId();
			
			Apartment apartment = Apartment.findById(Integer.parseInt(req.params("id")));
	
			apartment.setIsActive(false);
			apartment.saveIt();
			
			long id = (long) apartment.getId();
			
			res.redirect("/apartments/" + id);
					
			return "/";
		}
	};

	public static Route activations = (Request req, Response res) -> {

		try (AutocloseableDb db = new AutocloseableDb()) {
			
			Apartment apartment = Apartment.findById(Integer.parseInt(req.params("id")));
						
			apartment.setIsActive(true);
			apartment.saveIt();
			
			long id = (long) apartment.getId();
			
			res.redirect("/apartments/" + id);
					
			return "/";
		}
	};

	public static final Route like = (Request req, Response res) -> {
		
		try (AutocloseableDb db = new AutocloseableDb()) {
			
			Apartment apartment = Apartment.findById(Integer.parseInt(req.params("id")));
			long id = (long) apartment.getId();
			
			if(req.session().attribute("currentUser") != null) {
				User currentUser = req.session().attribute("currentUser");
				apartment.add(currentUser);
				}
			
				res.redirect("/apartments/" + id);
			}
			return "/";
		
	};
}