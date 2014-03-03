package zappos.product.common;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class ProductHelper 
{
	public void displayList(List<ArrayList<Product>> listOfCombinationOfProducts)
	{
		for(ArrayList<Product> oneCombinationOfProduct : listOfCombinationOfProducts)
		{
			double totalCostOfProductCombination=0;
			for(Product product:oneCombinationOfProduct)
			{
				System.out.println("Product Name: "+product.getProductName());
				System.out.println("Product Price: "+product.getPrice());
				System.out.println("Product Id: "+product.getProductId());
				totalCostOfProductCombination=totalCostOfProductCombination+product.getDoublePrice();
			}
			System.out.println("Total cost of combination: "+ZapposConstants.myformat.format(totalCostOfProductCombination));
			System.out.println("--------------------------------------------------");
		}
		System.out.println(listOfCombinationOfProducts.size()+" Product Combinations Found ");
	}

	public HashMap<String,Product> getProductList(double priceRange)
	{
		HashMap<String,Product> mapOfProducts=null;
		int pagenum=1;
		// Zappos supports 4 price categories
		double priceLimit=0;
		boolean nolimit=false;
		if(priceRange<=ZapposConstants.priceCategoryOne)
			priceLimit=ZapposConstants.priceCategoryOne;
		else if(priceRange>ZapposConstants.priceCategoryOne && priceRange<=ZapposConstants.priceCategoryTwo)
			priceLimit=ZapposConstants.priceCategoryTwo;
		else if(priceRange>ZapposConstants.priceCategoryTwo && priceRange<=ZapposConstants.priceCategoryThree)
			priceLimit=ZapposConstants.priceCategoryThree;
		else if(priceRange>ZapposConstants.priceCategoryThree)
			nolimit=true;

		mapOfProducts = new HashMap<String,Product>();
		while(true)
		{
			String apiURL;
			String plimit=ZapposConstants.myformat.format(priceLimit);
			if(nolimit)
				apiURL=ZapposConstants.urlpart+pagenum+ZapposConstants.urlExcludeAndKey;
			else
				apiURL=ZapposConstants.urlpart+pagenum+ZapposConstants.urlFilterPartOne+plimit+ZapposConstants.urlFilterPartTwo+ZapposConstants.urlExcludeAndKey;
			try	{
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
				if(Jarray.size()==0 || pagenum==6)
				{
					//System.out.println("No more products");
					break;
				}

				for (JsonElement obj : Jarray) 
				{
					Product r = gson.fromJson(obj, Product.class);
					mapOfProducts.put(r.getProductId(),r);
				}
				conn.disconnect();
				pagenum++;
			}
			catch (MalformedURLException e) 
			{
				System.out.println("Something went erong while fetching data from Zappos, Please check the URL");
			} catch (IOException e) {

				System.out.println("Something went erong while fetching data from Zappos, Please check the Input/Output Streams");
			}
			//			System.out.println("list size:"+mapOfProducts.size());
		} 

		return mapOfProducts;
	}

	public List<ArrayList<Product>> processProductList(	HashMap<String, Product> unProcessedProductList,int numberOfProducts, double dollarAmount) 
	{
		GetAllSubSetSum subSetSum=new GetAllSubSetSum();
		List<ArrayList<Product>> processedList=subSetSum.processProductList(unProcessedProductList, numberOfProducts, dollarAmount);
		return processedList;
	}
}
