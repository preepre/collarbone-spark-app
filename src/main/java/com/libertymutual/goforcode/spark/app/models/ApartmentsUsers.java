package com.libertymutual.goforcode.spark.app.models;

import org.javalite.activejdbc.Model;

public class ApartmentsUsers extends Model{
	
	public Long getApartmentId() {
		return getLong("apartment_id");
	}
	
	public Long getUserId() {
		return getLong("user_id");
	}	


}
