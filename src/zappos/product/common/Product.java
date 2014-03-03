package zappos.product.common;

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
