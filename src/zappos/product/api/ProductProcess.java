package zappos.product.api;

import java.util.HashMap;

import zappos.product.common.Product;

public interface ProductProcess 
{
	public HashMap<String,Product> getProductList(double priceRange);
}
