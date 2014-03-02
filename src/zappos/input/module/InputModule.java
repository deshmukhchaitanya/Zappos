package zappos.input.module;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import zappos.products.module.Products;

public class InputModule 
{
	double dollarAmount;
	int numberOfProducts;
	HashMap<String,Products> productList;

	public void getInputFromUser()
	{
		this.dollarAmount=70.00;
		this.numberOfProducts=3;
	}
	public HashMap<String,Products> getProductList()
	{
		this.productList=APIinterface.getProductList(this.getDollarAmount());
		return this.productList;
	}
	public double getDollarAmount() {
		return dollarAmount;
	}
	public void setDollarAmount(double dollarAmount) {
		this.dollarAmount = dollarAmount;
	}
	public int getNumberOfProducts() {
		return numberOfProducts;
	}
	public void setNumberOfProducts(int numberOfProducts) {
		this.numberOfProducts = numberOfProducts;
	}
	public void setProductList(HashMap<String, Products> productList) {
		this.productList = productList;
	}

}
class APIinterface 
{
	public static HashMap<String,Products> getProductList(double priceRange) 
	{
		HashMap<String,Products> mapOfProducts=null;
		try 
		{
			// Zappos supports 4 price categories
			double priceLimit=0;
			boolean nolimit=false;
			if(priceRange<=50.00)
				priceLimit=50;
			else if(priceRange>50.00 && priceRange<=100)
				priceLimit=100;
			else if(priceRange>100.00 && priceRange<=200.00)
				priceLimit=200;
			else if(priceRange>200.00)
				nolimit=true;

			mapOfProducts = new HashMap<String,Products>();
			int pagenum=1;

			DecimalFormat myformat = new DecimalFormat("#0.00");
			while(true)
			{
				String apiURL;
				String plimit=myformat.format(priceLimit);
				if(nolimit)
					apiURL="http://api.zappos.com/Search?term=&sort={%22price%22:%22asc%22}&limit=100&page="+pagenum+"&key=52ddafbe3ee659bad97fcce7c53592916a6bfd73&excludes=[%22originalTerm%22,%22term%22,%22originalPrice%22,%22percentOff%22,%22statusCode%22,%22limit%22,%22productUrl%22,%22brandName%22,%22styleId%22,%22colorId%22,%22thumbnailImageUrl%22,%22currentResultCount%22,%22totalResultCount%22]";
				else
					apiURL="http://api.zappos.com/Search?term=&sort={%22price%22:%22asc%22}&filters={%22priceFacet%22:[%22$"+plimit+"%20and%20Under%22]}&limit=100&page="+pagenum+"&key=52ddafbe3ee659bad97fcce7c53592916a6bfd73&excludes=[%22originalTerm%22,%22term%22,%22originalPrice%22,%22percentOff%22,%22statusCode%22,%22limit%22,%22productUrl%22,%22brandName%22,%22styleId%22,%22colorId%22,%22thumbnailImageUrl%22,%22currentResultCount%22,%22totalResultCount%22]";
				
				URL url = new URL(apiURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				if (conn.getResponseCode() != 200) 
				{
					throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
				}
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				JsonReader jsonReader = new JsonReader(br);
				Gson gson = new Gson();
				JsonParser parser = new JsonParser();
				JsonArray Jarray = (JsonArray) parser.parse(jsonReader).getAsJsonObject().get("results");
				if(Jarray.size()==0 || pagenum==30)
				{
					//System.out.println("No more products");
					break;
				}

				for (JsonElement obj : Jarray) 
				{
					Products r = gson.fromJson(obj, Products.class);
					mapOfProducts.put(r.getProductId(),r);
				}
				conn.disconnect();
				pagenum++;
			}
			System.out.println("list size:"+mapOfProducts.size());
//			for(Products p:mapOfProducts.values())
//			{
//				System.out.println(p.getProductId());
//				System.out.println(p.getPrice());
//			}
		} 
		catch (MalformedURLException e) 
		{
			System.out.println("Something went erong while fetching data from Zappos, Please check the URL");

		} catch (IOException e) {

			System.out.println("Something went erong while fetching data from Zappos, Please check the Input/Output Streams");
		}
		return mapOfProducts;
	}
}