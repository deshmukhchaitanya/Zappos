package zappos.products.module;

public class Products 
{
	String price;
	String productName;
	String productId;        // considering products have unique product Id 
	
//	@Override
//	public boolean equals(Object obj) 
//	{
//		Products p=(Products)obj;
//		return (this.getProductId()==p.getProductId());
//	}
//	@Override
//	public int hashCode() {
//	
//		return Integer.parseInt(getProductId());
//	}
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
