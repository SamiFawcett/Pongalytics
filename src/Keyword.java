import java.io.BufferedReader;
import java.lang.reflect.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Keyword {
	private static String KEYWORD_FILE = "/Users/cocop/eclipse-workspace/Pongalytics/src/data/keywords.txt";
	public static String REGISTER = "register";
	public static String NEW = "new";
	public static String START = "start";
	public static String STATS = "stats";
	public static String LOGIN = "login";
	public Map<String, Commands> keyword_method_map;

	public Keyword() throws Exception {
		keyword_method_map = new HashMap<String, Commands>();
		
		keyword_method_map.put(Keyword.LOGIN, new Commands() {
			public void invoke() throws Exception{
				Database.class.getMethod("log_in").invoke(null);
			}
			});
		
		keyword_method_map.put(Keyword.STATS, new Commands() {
			public void invoke() throws Exception{
				System.out.println(Player.class.getMethod("toString").invoke(Database.LOGGED_IN));
			}
			});
		
        keyword_method_map.put(Keyword.REGISTER,new Commands() {
			public void invoke() throws Exception{
				Database.class.getMethod("write_new_player").invoke(null);
			}
			});
        
        keyword_method_map.put(Keyword.START,new Commands() {
			public void invoke() throws Exception{
				Match.class.getMethod("start").invoke(new Main());
			}
			});
        
        //keyword_method_map.put(Keyword.NEW, Keyword.class.getMethod("event_add"));
        //keyword_method_map.put(Keyword.START, Main.class.getMethod("main", String[]{}));

        		
		}
	
	public Map<String, Commands> get_keyword_map(){
		return this.keyword_method_map;
	}
	
	public static boolean is_keyword(String token) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(Keyword.KEYWORD_FILE)));
			if(br.ready()) {
				
				String line;
				while((line = br.readLine()) != null) {
					if(line.equals(token)) {
						return true;
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String read() throws Exception {
		
			String line = Main.GLOBAL_SCANNER.nextLine();
			String[] line_tokens = line.split(" ");
		try {
			BufferedReader br_read = new BufferedReader(new FileReader(new File(Keyword.KEYWORD_FILE)));
			String reader_line;
			if(br_read.ready()) {
				while((reader_line = br_read.readLine()) != null) {
					if(line.contains(reader_line)) {
						System.out.println("COMMAND_ACTIVATED: " + reader_line + ".");
						this.get_keyword_map().get(line_tokens[0]).invoke();
					}
				}
			}
			br_read.close();
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return line;
	}
	
	public void event_add() {
		
	}
	
	public static void main(String[] args) {
		
	}
}


