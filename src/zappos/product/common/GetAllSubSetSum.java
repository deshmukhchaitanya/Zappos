package zappos.product.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetAllSubSetSum 
{
	//I am not passing these variables with recursive function because these will create memory issue with large values of input set and sum.
	private static List<ArrayList<Product>> ListOfCombinationOfProducts = new ArrayList<ArrayList<Product>>();
	private static ArrayList<Product> unProcessedProductList = null;

	public List<ArrayList<Product>> processProductList(HashMap<String, Product> prodList, int numberOfProducts, double targetPrice)
	{
		System.out.println("Processing the data....");
		ArrayList<Product> productList = new ArrayList<Product>(prodList.values());
		List<ArrayList<Product>> listOfCombinationOfProducts=getListOfCombinationsOfProducts(productList,targetPrice, numberOfProducts);
		return listOfCombinationOfProducts;
	}
	private void solveSubSetSum(Double targetPrice, ArrayList<Product> checkedProductList, int index, int desiredcheckedProductListSize) 
	{
		if (0 > targetPrice) {
			return;
		}
		if (targetPrice>=0 && targetPrice<=2) // considers all products combinations with sum within $ 4 of given price range 
		{
			//If visitor's count is given than we will consider only those sub-sets whose number of elements is equal to visitor's count.
			if( checkedProductList.size() == desiredcheckedProductListSize)
			{
				ListOfCombinationOfProducts.add(checkedProductList);
			}
				//Else consider all sub-sets
			else 
			{
				ListOfCombinationOfProducts.add(checkedProductList);
			}
			return;
		}
		for(int i=index;i<unProcessedProductList.size();i++) {
			//We pick one number in one iteration and leave rest of all for next iteration.
			Product product=unProcessedProductList.get(i); 
			double productPrice =product.getDoublePrice();
			
			//System.out.println("Traget: "+target);
			//System.out.println("Product price: "+p.getDoublePrice());
			if(productPrice>targetPrice)
			{
				//System.out.println("---------------------continued");
				continue;
			}
			//System.out.println("product being considered: "+p.getProductId());
			ArrayList<Product> beingCheckeProductList = new ArrayList<Product>(checkedProductList);
			beingCheckeProductList.add(product);
			//System.out.println("Current list size: "+newVisiters.size());
			//System.out.println("Allowed list size: "+visitersCount);
			if(beingCheckeProductList.size()>desiredcheckedProductListSize)
			{
				//System.out.println("####################################");
				break;
			}
			solveSubSetSum(targetPrice-productPrice,beingCheckeProductList, ++index, desiredcheckedProductListSize);
		}
	}

	public List<ArrayList<Product>> getListOfCombinationsOfProducts(ArrayList<Product> productList, Double targetPrice, int desiredcheckedProductListSize) 
	{
		unProcessedProductList =  productList;
		solveSubSetSum(targetPrice, new ArrayList<Product>(), 0, desiredcheckedProductListSize);
		return ListOfCombinationOfProducts;
	}

}