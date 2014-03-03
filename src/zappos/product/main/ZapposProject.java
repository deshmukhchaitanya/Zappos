package zappos.product.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zappos.product.common.Product;
import zappos.product.common.ProductHelper;
import zappos.product.process.impl.ProductProcessImpl;

public class ZapposProject 
{
	double dollarAmount;
	int numberOfProducts;
	
	public double getDollarAmount() {
		return dollarAmount;
	}

	public void setDollarAmount(double dollarAmount) {
		this.dollarAmount = dollarAmount;
	}

	public int getNumberOfProducts() {
		return numberOfProducts;
	}

	public void setNumberOfProducts(int numberOfProducts) {
		this.numberOfProducts = numberOfProducts;
	}

	
	public boolean getInputFromUser(String args1, String args2)
	{
		boolean validInput=false;
		try 
		{
			int numberOfProducts=Integer.parseInt(args1);
			double dollarAmount=Double.parseDouble(args2);
			if(!(dollarAmount>0) || !(numberOfProducts>0))
			{
				System.out.println("Negative numbers not allowed");
				return false;
			}
			this.dollarAmount=dollarAmount;
			this.numberOfProducts=numberOfProducts;
			validInput=true;
		} catch (NumberFormatException e) {
			System.out.println("improper input format");
		}
		return validInput; 
	}
	
	public static void main(String[] args) 
	{
		try 
		{
			boolean validInput=false;
			ZapposProject project=new ZapposProject();
			ProductProcessImpl process=new ProductProcessImpl();
			ProductHelper helper=new ProductHelper();
			System.out.println("Eneter two positive numbers (first for the desired number of products and second for the price limit) with a space as separator then press return");
			if(args.length==2)
				validInput=project.getInputFromUser(args[0],args[1]);
			if(!validInput)
				System.exit(0);
			HashMap<String,Product> unProcessedProductList;
			List<ArrayList<Product>> listOfCombinationOfProducts=null;
			
			unProcessedProductList=process.getProductList(project.getDollarAmount());
			
			if(unProcessedProductList!=null)
			{
				listOfCombinationOfProducts=process.processProductList(unProcessedProductList, project.getNumberOfProducts(), project.getDollarAmount());
			
			}
			else {
				System.out.println("Could not get Product List");
			}
			if(listOfCombinationOfProducts!=null)
			{
				helper.displayList(listOfCombinationOfProducts);
			}
			else
			{
				System.out.println("Could not get Processed Product List");
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
