package af.handball.helper;

import java.text.DecimalFormat;

public class MathRoundHelper {

	public static String round(double val, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    String formattedVal = "error";
	    if (val >= 10) {
	    DecimalFormat df = new DecimalFormat("####0.0");
	    formattedVal = df.format(val);
	    } else {
	    	DecimalFormat df = new DecimalFormat("####0.00");	
	    	formattedVal = df.format(val);
	    }
	    return formattedVal;
      
	    /*return Math.round(val*1e2)/1e2;*/
        
	    /*BigDecimal bigDec = new BigDecimal(val);
	    bigDec = bigDec.setScale(places, RoundingMode.HALF_UP);
	    return bigDec.doubleValue();*/
	}
	
}
