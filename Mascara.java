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
 * Classe responsavel por colocar e retirar a máscara de um dado. <br>
 * <br>
 * A classe possui alguns padrões próprios, mas não impede de criar seu próprio padrão de mascara. <br>
 * <br>
 * <em>Futuras melhorias</em> - Arrumar a classe de modo que possa mascarar parte do dado,<br>
 * isto é mascara parcial.<br>
 * <br>
 * Ex.:<br>
 * <br>
 * TELEFONE pode ser ou <code>###-####</code>, ou <code>####-####</code>, ou <code>(##)####-####</code>. E a própria classe tratar isso.
 * de modo que a chamada para mascarar seja simplesmente:<br>
 * <br>
 * <code>Mascara.mascararToString( dadoTelefone, Mascara.TELEFONE );</code>  
 * 
 * 
 * @author Rodrigo Gilberto Marin Catto
 * @email iskigow #at# gmail #dot# com
 * @version 1.2
 * @category Classe Util 
 * @since terça-feira, 18 de dezembro de 2007, 17:52:46
 */

public class Mascara {

	private static final Pattern ER;
	public static final String MASCARA_CPF;
	public static final String MASCARA_CNPJ;
	public static final String MASCARA_CEP;
	public static final String TELEFONE;
	public static final String DDD_TELEFONE;
	public static final String DATA;
	public static final String HORA;

	static {
		ER = Pattern.compile("([#]+)");
		MASCARA_CPF = "###.###.###-##";
		MASCARA_CNPJ = "##.###.###/####-##";
		MASCARA_CEP = "##.###-##";
		TELEFONE = "####-####";
		DDD_TELEFONE = "(##)####-####";
		DATA = "##/##/####";
		HORA = "##:##:##";
	}

	Mascara() {
	}

	public static String mascararToString(String dado, String mascara) {
		if ( dado == null || mascara == null ) {
			return dado;
		}
		String mascara_aux = mascara;
		Matcher resultado = ER.matcher(mascara_aux);
		StringBuffer sb = new StringBuffer();
		String er = "^";
		int i = 1;
		while (resultado.find()) {
			er = er.concat("(\\p{Alnum}{"
					+ (resultado.end() - resultado.start()) + "})");
			resultado.appendReplacement(sb, "\\$" + i);
			++i;
		}
		er = er.concat("$");
		resultado.appendTail(sb);

		return dado.replaceAll(er, sb.toString());
	}

	public static String desmascararToString(String dado) {
		if ( dado == null ) {
			return dado;
		}
		String er = "([^\\p{Alnum}])";
		return dado.replaceAll(er, "");
	}
}
