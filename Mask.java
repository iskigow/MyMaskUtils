import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for placing and removing the mask of a given data. <br>
 * <br>
 * The class has some standards themselves, but we can create additional masks.<br>
 * <br>
 * <em>Future improvements</em> - Fix the class so that it can mask as part of data,<br>
 * this is partly mask.<br>
 * <br>
 * Ex.:<br>
 * <br>
 * PHONE can be or <code>###-####</code>, or <code>####-####</code>, or <code>(##)####-####</code>. And the class must do that.
 * So the mask's call is simply:<br>
 * <br>
 * <code>Mask.mascararToString( dataPhone, Mask.PHONE );</code>  
 *  
 * @author Rodrigo Gilberto Marin Catto
 * @eamil iskigow@gmail.com
 * @version 1.0
 * @category Util Class
 * @since Friday, 24 oct 2008 02:54:48 PM
 */

public class Mask {

	private static final Pattern ER;
	public static final String MASK_CPF_BRAZIL;
	public static final String MASK_CNPJ_BRAZIL;
	public static final String MASK_ZIP_BRAZIL;
	public static final String PHONE_BRAZIL;
	public static final String DDD_PHONE_BRAZIL;
	public static final String DATE_BRAZIL;
	public static final String HOUR_BRAZIL;

	static {
		ER = Pattern.compile("([#]+)");
		MASK_CPF_BRAZIL = "###.###.###-##";
		MASK_CNPJ_BRAZIL = "##.###.###/####-##";
		MASK_ZIP_BRAZIL = "##.###-##";
		PHONE_BRAZIL = "####-####";
		DDD_PHONE_BRAZIL = "(##)####-####";
		DATE_BRAZIL = "##/##/####";
		HOUR_BRAZIL = "##:##:##";
	}

	Mask() {
	}

	public static String maskToString(String data, String mask) {
		String maskAux = mask;
		Matcher result = ER.matcher(maskAux);
		StringBuffer sb = new StringBuffer();
		String er = "^";
		int i = 1;
		while (result.find()) {
			er = er.concat("(\\p{Alnum}{"
					+ (result.end() - result.start()) + "})");
			result.appendReplacement(sb, "\\$" + i);
			++i;
		}
		er = er.concat("$");
		result.appendTail(sb);

		return data.replaceAll(er, sb.toString());
	}

	public static String unmaskToString(String data) {
		String er = "([^\\p{Alnum}])";
		return data.replaceAll(er, "");
	}
}
