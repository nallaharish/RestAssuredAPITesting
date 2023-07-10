package com.testautomation.apitesting.tests;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import com.testautomation.apitesting.utils.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;


public class POSTAPIResquest extends BaseTest{	
	
	@Test
	public void createBooking() {	
				
		JSONObject booking  = new JSONObject();
		JSONObject bookingDates  = new JSONObject();
		
		booking.put("firstname", "Harish");
		booking.put("lastname", "Kumar");
		booking.put("totalprice", 1000);
		booking.put("depositpaid", true);
		booking.put("additionalneeds", "travel");
		booking.put("bookingdates", bookingDates);
		 
		bookingDates.put("checkin", "2023-01-01");
		bookingDates.put("checkout", "2023-02-02");
		
		Response response =
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(booking.toString())
			.baseUri("https://restful-booker.herokuapp.com/booking")
		//.log().body()
		//.log().all()
		
		.when()
			.post()
		
		.then()
			.assertThat()
			//.log().all()
			//.log().ifValidationFails()
			.statusCode(200)
			.body("booking.firstname", Matchers.equalTo("Harish"))
			.body("booking.totalprice", Matchers.equalTo(1000))
			.body("booking.bookingdates.checkin", Matchers.equalTo("2023-01-01"))
		.extract()
			.response();
		
		int bookingId = response.path("bookingid");
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.pathParam("bookingID", bookingId)		
			.baseUri("https://restful-booker.herokuapp.com/booking")
		.when()
			.get("{bookingID}")
		.then()
			.assertThat()
			.statusCode(200)
			.body("firstname", Matchers.equalTo("Harish"))
			.body("lastname", Matchers.equalTo("Kumar"));
		
		
	}

}
