package zappos.product.common;

/**
 * @author chaitanya
 *This is the Product class that is mapped to the products coming in as JSON array in the Zappos API
 *response. For Simplicity I have considered that the products are uniquely identified by their 
 *productID thus using it as a key(HashMap) wherever necessary. 
 */
public class Product 
{
	String price;
	String productName;
	String productId;        // considering products have unique product Id 
	
	public Product(String price, String productName, String productId) 
	{
		this.price = price;
		this.productName = productName;
		this.productId = productId;
	}
	public Double getDoublePrice() {
		return Double.parseDouble(price.substring(1));
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
