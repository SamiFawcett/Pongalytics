import java.io.BufferedReader;
import java.lang.reflect.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Keyword {
	private static String KEYWORD_FILE = "/Users/cocop/eclipse-workspace/Pongalytics/src/data/keywords.txt";
	public static String REGISTER = "register";
	public static String NEW = "new";
	public static String START = "start";
	public Map<String, Method> keyword_map;
	public Scanner sc = new Scanner(System.in);
	
	public Keyword() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		keyword_map = new HashMap<String, Method>();
        keyword_map.put(Keyword.REGISTER, Database.class.getMethod("write_new_player"));
        //keyword_map.put(Keyword.NEW, Keyword.class.getMethod("event_add"));
        //keyword_map.put(Keyword.START, Main.class.getMethod("main", String[]{}));
	}
	
	public Map<String, Method> get_keyword_map(){
		return this.keyword_map;
	}
	
	public String read() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String line = sc.nextLine();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(Keyword.KEYWORD_FILE)));
			String reader_line;
			if(br.ready()) {
				while((reader_line = br.readLine()) != null) {
					if(line.contains(reader_line)) {
						System.out.println("resp: " + reader_line + ".");
						this.get_keyword_map().get(reader_line).invoke(null);
					}
				}
			}
			
			
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return line;
	}
	
	public void event_add() {
		
	}
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Keyword k = new Keyword();
		System.out.println("First Read");
		k.read();
		System.out.println("Second Read");
		k.read();
	}
}
