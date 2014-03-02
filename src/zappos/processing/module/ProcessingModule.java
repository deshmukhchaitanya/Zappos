package zappos.processing.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zappos.products.module.Products;

public class ProcessingModule
{
	public List<ArrayList<Products>> processProductList(HashMap<String, Products> prodList, int numberOfProducts, double target)
	{
		System.out.println("Processing the data....");
		ArrayList<Products> productList = new ArrayList<Products>(prodList.values());
		List<ArrayList<Products>> listOfProductSets=SubsetSum.sum(productList,target, numberOfProducts);
		return listOfProductSets;
	}
}
class SubsetSum 
{
	//I am not passing these variables with recursive function because these will create memory issue with large values of input set and sum.
	private static List<ArrayList<Products>> RESULT_SUBSETS = new ArrayList<ArrayList<Products>>();
	private static ArrayList<Products> NUMBERS_INPUT = null;

	private static void subSetSum(Double target, ArrayList<Products> visiters, int index, int visitersCount) 
	{
		
		if (0 > target) {
			return;
		}
		if (target>=0 && target<=3) // considers all products combinations with sum within $ 4 of given price range 
		{
			//If visitor's count is given than we will consider only those sub-sets whose number of elements is equal to visitor's count.
			if(visitersCount != 0 )
			{
				if( visiters.size() == visitersCount)
				{
					RESULT_SUBSETS.add(visiters);
				}
				//Else consider all sub-sets
			} 
			else 
			{
				RESULT_SUBSETS.add(visiters);
			}
			return;
		}
		for(int i=index;i<NUMBERS_INPUT.size();i++) {
			//We pick one number in one iteration and leave rest of all for next iteration.
			Products p=NUMBERS_INPUT.get(i); 
			double visit =p.getDoublePrice();
			
			//System.out.println("Traget: "+target);
			//System.out.println("Product price: "+p.getDoublePrice());
			if(visit>target)
			{
				//System.out.println("---------------------continued");
				continue;
			}
			//System.out.println("product being considered: "+p.getProductId());
			ArrayList<Products> newVisiters = new ArrayList<Products>(visiters);
			newVisiters.add(p);
			//System.out.println("Current list size: "+newVisiters.size());
			//System.out.println("Allowed list size: "+visitersCount);
			if(newVisiters.size()>visitersCount)
			{
				//System.out.println("####################################");
				break;
			}
			subSetSum(target-visit,newVisiters, ++index, visitersCount);
		}
	}

	static List<ArrayList<Products>> sum(ArrayList<Products> productList, Double target, int visitersCount) 
	{
		NUMBERS_INPUT =  productList;
		subSetSum(target, new ArrayList<Products>(), 0, visitersCount);
		return RESULT_SUBSETS;
	}

}
