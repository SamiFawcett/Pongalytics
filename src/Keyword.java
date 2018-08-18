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
				if(Clientbase.LOGGED_IN == null) {
					Clientbase.class.getMethod("log_in").invoke(null);
				} else {
					System.out.println("Already logged in as " + Clientbase.LOGGED_IN.get_handle());
				}
			}
			});
		
		keyword_method_map.put(Keyword.STATS, new Commands() {
			public void invoke() throws Exception{
				if(Clientbase.LOGGED_IN != null) {
					System.out.println(Player.class.getMethod("toString").invoke(Clientbase.LOGGED_IN));
				} else {
					System.out.println("You need to login before checking stats.");
				}
			}
			});
		
        keyword_method_map.put(Keyword.REGISTER,new Commands() {
			public void invoke() throws Exception{
				Clientbase.class.getMethod("write_new_player").invoke(null);
			}
			});
        
        keyword_method_map.put(Keyword.START,new Commands() {
			public void invoke() throws Exception{
				if(Clientbase.LOGGED_IN != null) {
				Match.class.getMethod("start").invoke(new Main());
				} else {
					System.out.println("You need to login before starting a match.");
				}
			}
			});
        

        		
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


