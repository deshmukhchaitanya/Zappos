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

/**
 * @author chaitanya
 * This is a helper class providing functionality to various other classes such as ProductProcessImpl
 * This class consists of the method responsible for fetching data from the Zappos API and displaying the
 * desired list of combination of products. 
 */
public class ProductHelper 
{
	/** This method displays the desired list of combination of products to the user.
	 * @param listOfCombinationOfProducts is the desired list of products
	 */
	
	// ASSUPTION: here PAGE_NUM limits the number of pages to be accessed. Please keep in mind all the products with high
	// costs are in page number: 200 and above and fetching these many pages could be done but the time latency increases.
	// Thus for simplicity this parameter is being set to 21 which leads to fetching 20 pages(with 100 products in each page).
	// This parameter is configurable and can be set to any large value depending on the page fetching latency.
	private static int PAGE_NUM = 21;
	
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
			System.out.println("Total cost of combination: "+ZapposConstants.doubleFormat.format(totalCostOfProductCombination));
			System.out.println("--------------------------------------------------");
		}
		System.out.println(listOfCombinationOfProducts.size()+" Product Combinations Found ");
	}

	/**This method fetches the list of products from the Zappos.com using Zappos API and converting them
	 * into Product objects using 'gson framework' for processing JSON and extracting objects. 
	 * @param priceRange is used in URL for applying a upper bound on price if user demands so.
	 * @return is a HashMap of products with unique productIDs. 
	 */
	public HashMap<String,Product> getProductList(double priceRange)
	{
		HashMap<String,Product> mapOfProducts=null;
		// this variable sets which page to access on the Zappos API response 
		int pageNum=1;
		// Zappos supports grouping of products in 4 categories according to price
		double priceLimit=0;
		boolean nolimit=false;
		if(priceRange<=ZapposConstants.priceCategoryOne)
		{
			priceLimit=ZapposConstants.priceCategoryOne;
		}
		else if(priceRange>ZapposConstants.priceCategoryOne && priceRange<=ZapposConstants.priceCategoryTwo)
		{
			priceLimit=ZapposConstants.priceCategoryTwo;
		}
		else if(priceRange>ZapposConstants.priceCategoryTwo && priceRange<=ZapposConstants.priceCategoryThree)
		{
			priceLimit=ZapposConstants.priceCategoryThree;
		}
		else if(priceRange>ZapposConstants.priceCategoryThree)
		{
			nolimit=true;
		}

		mapOfProducts = new HashMap<String,Product>();
		while(true)
		{
			// creating the URL with proper parameters for using Zappos API
			String apiURL;
			String plimit=ZapposConstants.doubleFormat.format(priceLimit);
			if(nolimit)
				apiURL=ZapposConstants.urlpart+pageNum+ZapposConstants.urlExcludeAndKey;
			else
				apiURL=ZapposConstants.urlpart+pageNum+ZapposConstants.urlFilterPartOne+plimit+ZapposConstants.urlFilterPartTwo+ZapposConstants.urlExcludeAndKey;
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
				// break if the JSON response has no products OR PAGE_NUM limit is reached
				if(Jarray.size()==0 || pageNum==PAGE_NUM)
				{
					break;
				}
				for (JsonElement obj : Jarray) 
				{
					// extracting product object from the JSON response and populating the Map based on productId
					Product r = gson.fromJson(obj, Product.class);
					mapOfProducts.put(r.getProductId(),r);
				}
				conn.disconnect();
				pageNum++;
			}
			catch (MalformedURLException e) 
			{
				System.out.println("Something went wrong while fetching data from Zappos, Please check the URL");
			} catch (IOException e) {

				System.out.println("Something went wrong while fetching data from Zappos, Please check the Input/Output Streams");
			}
		} 
		System.out.println("Size of unprocessed list:"+mapOfProducts.size());
		return mapOfProducts;
	}

	/**
	 * @param unProcessedProductList is the raw fetched list of all the products
	 * @param numberOfProducts is the user input
	 * @param dollarAmount is the user input
	 * @return list of combination of specified number of products satisfying price limit criteria  
	 */
	public List<ArrayList<Product>> processProductList(	HashMap<String, Product> unProcessedProductList,int numberOfProducts, double dollarAmount) 
	{
		GetAllSubSetSum subSetSum=new GetAllSubSetSum();
		List<ArrayList<Product>> processedList=subSetSum.processProductList(unProcessedProductList, numberOfProducts, dollarAmount);
		return processedList;
	}
}
