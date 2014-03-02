package zappos.output.module;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import zappos.products.module.Products;

public class OutputModule 
{
	public void displayList(List<ArrayList<Products>> list)
	{
		DecimalFormat myformat = new DecimalFormat("#0.00");
		System.out.println("in output module");
		System.out.println(list.size()+" Product Combinations Found ");
		for(ArrayList<Products> prodList : list)
		{
			double total=0;
			for(Products prod:prodList)
			{
				System.out.println("Product Name: "+prod.getProductName());
				System.out.println("Product Price: "+prod.getPrice());
				System.out.println("Product Id: "+prod.getProductId());
				total=total+prod.getDoublePrice();
			}
			System.out.println("Total cost of combination: "+myformat.format(total));
			System.out.println("--------------------------------------------------");
		}
		System.out.println(list.size()+" Product Combinations Found ");
	}
}
