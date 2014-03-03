package zappos.product.process.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zappos.product.api.ProductProcess;
import zappos.product.common.Product;
import zappos.product.common.ProductHelper;

/**
 * @author chaitanya
 * This class provides the implementation of getProductList and processProductList methods
 */
public class ProductProcessImpl implements ProductProcess
{
	/* (non-Javadoc)
	 * @see zappos.product.api.ProductProcess#getProductList(double)
	 * This method is the implementation of ProductProcess interface getProductList method
	 * It calls the getProductList of ProductHelper method and return a HashMap of product
	 *  with productId as the Key and Product as the value
	 */ 
	public HashMap<String,Product> getProductList(double priceRange) 
	{
		HashMap<String,Product> unProcessedProductList;
		ProductHelper helper=new ProductHelper();
		unProcessedProductList=helper.getProductList(priceRange);
		return unProcessedProductList;
	}
	
	/**
	 * @param unProcessedProductList is the raw unprocessed list of all the products 
	 * @param numberOfProducts is the number of products in a combination that the user entered 
	 * @param dollarAmount is the price limit that the user entered
	 * @return A list of combination of products with each combination containing specified number of products 
	 * with total cost of the combination close to price limit
	 */
	public List<ArrayList<Product>> processProductList(HashMap<String, Product> unProcessedProductList,int numberOfProducts, double dollarAmount) 
	{
		ProductHelper helper=new ProductHelper();
		List<ArrayList<Product>> listOfCombinationOfProducts;
		listOfCombinationOfProducts=helper.processProductList(unProcessedProductList,numberOfProducts,dollarAmount);
		return listOfCombinationOfProducts;
	}
}