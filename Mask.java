/*-
 * Copyright (c) 2007-2010 Rodrigo G. M. Catto.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Rodrigo G. M. Catto nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY RODRIGO G. M. CATTO. AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL RODRIGO G. M. CATTO OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY 
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
 * @email iskigow #at# gmail #dot# com
 * @version 1.2
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
		if ( data == null || mask == null ) {
			return data;
		}
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
		if ( data == null ) {
			return data;
		}
		String er = "([^\\p{Alnum}])";
		return data.replaceAll(er, "");
	}
}
