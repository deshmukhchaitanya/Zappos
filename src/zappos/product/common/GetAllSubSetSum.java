package zappos.product.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author chaitanya
 * This class provides the functionality of processing a raw list of products into a list of
 * combination of products where each combination has total cost close to a user specified price
 * limit.
 */
public class GetAllSubSetSum 
{
	private static List<ArrayList<Product>> ListOfCombinationOfProducts = new ArrayList<ArrayList<Product>>();
	private static ArrayList<Product> unProcessedProductList = null;
	
	/**
	 * @param prodList is the list of products 
	 * @param numberOfProducts is the input form the user and denotes number of products in each combination
	 * @param targetPrice is the input price limit from the user
	 * @return A list of combination of products satisfying given price limit criteria
	 * 
	 * This method makes use of solveSubSetSum and getListOfCombinationsOfProducts methods for doing its task.
	 */
	public List<ArrayList<Product>> processProductList(HashMap<String, Product> prodList, int numberOfProducts, double targetPrice)
	{
		System.out.println("Processing the data....");
		ArrayList<Product> productList = new ArrayList<Product>(prodList.values());
		List<ArrayList<Product>> listOfCombinationOfProducts=getListOfCombinationsOfProducts(productList,targetPrice, numberOfProducts);
		return listOfCombinationOfProducts;
	}

	/**
	 *  Solves the Sum Of SubSet problem of finding all subsets with their sum of prices equal/close to a specified sum
	 *  NOTE:
	 *  Here priceMargin controls the closeness of the results to the desired target price, i.e. if target price is $20.00
	 *  and price margin is set to 1 then all the combinations with total cost of $19.00 to $20.00 are included in desired list of
	 *  product. This parameter could be modified as needed in the ZapposConstants class for narrowing or widening accepted
	 *  the deviation from target price.   
	 */
	private void solveSubSetSum(Double targetPrice, ArrayList<Product> checkedProductList, int index, int desiredcheckedProductListSize) 
	{
		try{
			if (0 > targetPrice) {
				return;
			}
			// ASSUMPTION: all products combinations with total sum within priceMargin($1.00) of given price limit
			if (targetPrice>=0 && targetPrice<=ZapposConstants.priceMargin)  
			{
				if( checkedProductList.size() == desiredcheckedProductListSize){
					ListOfCombinationOfProducts.add(checkedProductList);
				}
				return;
			}
			for(int i=index;i<unProcessedProductList.size();i++) {
				Product product=unProcessedProductList.get(i); 
				double productPrice =product.getDoublePrice();
				if(productPrice>targetPrice)  
					continue;		// no need to consider products with price greater than target price/price limit
				ArrayList<Product> beingCheckeProductList = new ArrayList<Product>(checkedProductList);
				beingCheckeProductList.add(product);
				if(beingCheckeProductList.size()>desiredcheckedProductListSize)
					break; 			// break if the size of the list get bigger than specified size for each combination
				solveSubSetSum(targetPrice-productPrice,beingCheckeProductList, ++index, desiredcheckedProductListSize);
				
			}
		}
		catch(Exception e){
			System.out.println("Could not generate sub sets");
		}
	}

	public List<ArrayList<Product>> getListOfCombinationsOfProducts(ArrayList<Product> productList, Double targetPrice, int desiredcheckedProductListSize) 
	{
		unProcessedProductList =  productList;
		solveSubSetSum(targetPrice, new ArrayList<Product>(), 0, desiredcheckedProductListSize);
		return ListOfCombinationOfProducts;
	}

}