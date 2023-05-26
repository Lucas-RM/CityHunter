package extras;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FuncoesExtras {
	
	public static int sortear(Integer[] valoresDoSorteio, Integer[] probalidades) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int i = 0; i < valoresDoSorteio.length; i++) {
			map.put(probalidades[i], valoresDoSorteio[i]);
		}

		int sort = new Random().nextInt(100);

		for (Map.Entry<Integer, Integer> item : map.entrySet()) {
			sort = sort - item.getKey();
			if (sort <= 0) {
				return item.getValue();
			}
		}

		return 0;
	}

	
	public static String capitalizeWord(String word)
    {
        if (word == null || word.length() == 0) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
 
    public static String capitalize(String str)
    {
        if (str == null || str.length() == 0) {
            return str;
        }
 
        return Stream.of(str.trim().split("\\s+"))
                .map(FuncoesExtras::capitalizeWord)
                .collect(Collectors.joining(" "));
 
    }
	
	public static void desenhaSimbolo(String simbolo, int quantidade) {
		for (int i = 0; i < quantidade; i++) {
			System.out.print(simbolo);
		}
	}
	
	public static void desenhaSimbolo(String cor, String simbolo, int quantidade) {
		
		String ANSI_RESET = "\u001B[0m";
		for (int i = 0; i < quantidade; i++) {
			System.out.print(cor + simbolo + ANSI_RESET);
		}
	}
		
	public static void limparConsole() {
		
		try {
			if (System.getProperty("os.name").contains("Windows"))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        else
	        	
	            Runtime.getRuntime().exec("clear");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
