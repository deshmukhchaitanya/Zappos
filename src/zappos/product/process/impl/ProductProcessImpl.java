package zappos.product.process.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zappos.product.api.ProductProcess;
import zappos.product.common.Product;
import zappos.product.common.ProductHelper;

public class ProductProcessImpl implements ProductProcess
{
	public HashMap<String,Product> getProductList(double priceRange) 
	{
		HashMap<String,Product> unProcessedProductList;
		ProductHelper helper=new ProductHelper();
		unProcessedProductList=helper.getProductList(priceRange);
		return unProcessedProductList;
	}

	public List<ArrayList<Product>> processProductList(HashMap<String, Product> unProcessedProductList,int numberOfProducts, double dollarAmount) 
	{
		ProductHelper helper=new ProductHelper();
		List<ArrayList<Product>> listOfCombinationOfProducts;
		listOfCombinationOfProducts=helper.processProductList(unProcessedProductList,numberOfProducts,dollarAmount);
		return listOfCombinationOfProducts;
	}
}