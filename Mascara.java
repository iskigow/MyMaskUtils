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
 * @eamil iskigow #at# gmail #dot# com
 * @version 1.0
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
		String er = "([^\\p{Alnum}])";
		return dado.replaceAll(er, "");
	}
}
