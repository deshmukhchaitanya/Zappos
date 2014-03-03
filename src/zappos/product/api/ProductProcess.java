package zappos.product.api;
import java.util.HashMap;

import zappos.product.common.Product;

/**
 * @author chaitanya
 * Adhering to the OOP and design pattern, placing methods in a common interface from where
 * all other classes could provide their own implementation of the methods after implementing 
 * the interface. 
 */
public interface ProductProcess 
{
	public HashMap<String,Product> getProductList(double priceRange);
}
