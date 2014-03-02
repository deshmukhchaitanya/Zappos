package zappos.driver.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zappos.input.module.InputModule;
import zappos.output.module.OutputModule;
import zappos.processing.module.ProcessingModule;
import zappos.products.module.Products;

public class ZapposMain {

	public static void main(String[] args) 
	{
		try 
		{
			InputModule in=new InputModule();
			ProcessingModule process=new ProcessingModule();
			
			OutputModule out=new OutputModule();
			
			List<ArrayList<Products>> listOfSetOfProducts=null;
			
			in.getInputFromUser();
			//System.out.println("here1");
			HashMap<String,Products> prodList=in.getProductList();
			//System.out.println("here2");
			if(prodList!=null)
			{
				listOfSetOfProducts=process.processProductList(prodList, in.getNumberOfProducts(), in.getDollarAmount());
				//System.out.println("here3");
			}
			else {
				System.out.println("Product List was empty");
			}
			if(listOfSetOfProducts!=null)
			{
				out.displayList(listOfSetOfProducts);
			}
			else
			{
				System.out.println("Processed Product List was empty");
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Some Exception occurred");
			e.printStackTrace();
		}
		System.out.println("main done");
	}
}
