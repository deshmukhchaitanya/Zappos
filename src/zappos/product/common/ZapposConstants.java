package zappos.product.common;

import java.text.DecimalFormat;

public class ZapposConstants 
{
	public final static double priceCategoryOne=50.00;
	public final static double priceCategoryTwo=100.00;
	public final static double priceCategoryThree=200.00;
	public final static DecimalFormat myformat = new DecimalFormat("#0.00");
	public final static String urlpart="http://api.zappos.com/Search?term=&sort={%22price%22:%22asc%22}&limit=100&page=";
	public final static String urlExcludeAndKey="&key=52ddafbe3ee659bad97fcce7c53592916a6bfd73&excludes=[%22originalTerm%22,%22term%22,%22originalPrice%22,%22percentOff%22,%22statusCode%22,%22limit%22,%22productUrl%22,%22brandName%22,%22styleId%22,%22colorId%22,%22thumbnailImageUrl%22,%22currentResultCount%22,%22totalResultCount%22]";
	public final static String urlFilterPartOne="&filters={%22priceFacet%22:[%22$";
	public final static String urlFilterPartTwo="%20and%20Under%22]}";
}
