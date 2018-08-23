import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class Client {
	public final static File PLAYER_BASE_LOC = new File("./src/data/player_data/registered_players.txt");
	public final static File MATCH_BASE_ALL_LOC = new File("./src/data/match_data/all_pong_data.txt");
	public final static File PLAYER_SPEC = new File("./src/data/player_data/registration_data/");
	public static Player LOGGED_IN = null;
	
	public static Player find(String handle, boolean recursive) {
		Player found = null;
		try {
			BufferedReader br_base = new BufferedReader(new FileReader(Client.PLAYER_BASE_LOC));
			String line;
			if(br_base.ready()) {
				while((line = br_base.readLine()) != null) {
					if(line.equals(handle)){
						br_base.close();
						found = new Player(Client.PLAYER_SPEC.toString() + "/" + handle + ".txt");
						return found;
					}
				}
			}
			br_base.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		if(recursive) {
			if(!Keyword.is_keyword(handle)) {
				System.out.println(handle + " was not found.");
			}
			return find(Main.GLOBAL_SCANNER.nextLine(), recursive);
		} else { return null; }
		
		
	}
	
	public static boolean log_in() throws Exception {
		Keyword k = new Keyword();
		System.out.println("Pongalytics Handle: ");
		String handle = k.read();
		Player p = Client.find(handle, true);
		if(p != null) {
			LOGGED_IN = p;
			System.out.println(p.get_first() + " logged in.");
			return true;
		}
		return false;
	}
	
	public static boolean log_out() throws Exception {
		if(LOGGED_IN != null) {
			Player temp_p = LOGGED_IN;
			LOGGED_IN = null;
			System.out.println(temp_p.get_first() + " logged out.");
			return true;
		}
		return false;
	}
	
	
	public static void write_match(String winner_handle, int winner_score, String loser_handle, int loser_score, String m_id) {
		try {
			//write to pong files first
			BufferedWriter bw_pong = new BufferedWriter(new FileWriter(Client.MATCH_BASE_ALL_LOC, true));
			PrintWriter out_pong = new PrintWriter(bw_pong);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate localDate = LocalDate.now();
			out_pong.println();
			out_pong.print(dtf.format(localDate) + " " + winner_handle + " " + loser_handle + " " + winner_score + "-" + loser_score + " " + m_id);
			out_pong.close();	
			
			//update player files

			
			
			BufferedWriter bw_p_one = new BufferedWriter(new FileWriter(Client.PLAYER_SPEC + "/" + winner_handle + ".txt", true));
			BufferedWriter bw_p_two = new BufferedWriter(new FileWriter(Client.PLAYER_SPEC + "/" + loser_handle + ".txt", true));
			PrintWriter out_p_one = new PrintWriter(bw_p_one);
			PrintWriter out_p_two = new PrintWriter(bw_p_two);
			out_p_one.println();
			out_p_two.println();
			out_p_one.print(m_id);
			out_p_two.print(m_id);
			out_p_one.close();
			out_p_two.close();
			
			
			update_winner_score(winner_handle);
			update_loser_score(loser_handle);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		

	}
	
	private static void update_winner_score(String winner_handle) {
		try {
			
			BufferedReader br_score = new BufferedReader(new FileReader(Client.PLAYER_SPEC + "/" + winner_handle + ".txt"));
			List<String> match_data = new ArrayList<String>();
			String new_player_data = "";
			if(br_score.ready()) {
				String line;
				String player_data = br_score.readLine();
				new_player_data = player_data;
				String[] new_pd_tokens = new_player_data.split(",");
				int new_value = Integer.parseInt(new_pd_tokens[3]);
				System.out.println(new_value + 1);
				new_pd_tokens[3] = ""+ (new_value+1); 
				new_player_data = "";
				for(int i = 0; i < new_pd_tokens.length; i++) {
					if(i < new_pd_tokens.length - 1) {
						new_player_data += new_pd_tokens[i] + ",";
					} else {
						new_player_data += new_pd_tokens[i];
					}
				}
				int iter = 0;
				while((line = br_score.readLine()) != null) {
					match_data.add(iter, line);;
					iter++;
				}
				
				
			}
			
			BufferedWriter bw_score = new BufferedWriter(new FileWriter(Client.PLAYER_SPEC + "/" + winner_handle + ".txt",false));
			PrintWriter out_score = new PrintWriter(bw_score);
			out_score.print(new_player_data + "\n");
			for(int j = 0; j < match_data.size(); j++) {
				if(j < match_data.size() - 1) {
					out_score.print(match_data.get(j) + "\n");
				} else {
					out_score.print(match_data.get(j));
				}
			}
			out_score.close();
			bw_score.close();
			br_score.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void update_loser_score(String loser_handle) {
		try {
			
			BufferedReader br_score = new BufferedReader(new FileReader(Client.PLAYER_SPEC + "/" + loser_handle + ".txt"));
			List<String> match_data = new ArrayList<String>();
			String new_player_data = "";
			if(br_score.ready()) {
				String line;
				String player_data = br_score.readLine();
				new_player_data = player_data;
				String[] new_pd_tokens = new_player_data.split(",");
				int new_value = Integer.parseInt(new_pd_tokens[3]);
				System.out.println(new_value - 1);
				new_pd_tokens[3] = ""+ (new_value+1); 
				new_player_data = "";
				for(int i = 0; i < new_pd_tokens.length; i++) {
					if(i < new_pd_tokens.length - 1) {
						new_player_data += new_pd_tokens[i] + ",";
					} else {
						new_player_data += new_pd_tokens[i];
					}
				}
				int iter = 0;
				while((line = br_score.readLine()) != null) {
					match_data.add(iter, line);;
					iter++;
				}
				
				
			}
			
			BufferedWriter bw_score = new BufferedWriter(new FileWriter(Client.PLAYER_SPEC + "/" + loser_handle + ".txt",false));
			PrintWriter out_score = new PrintWriter(bw_score);
			out_score.print(new_player_data + "\n");
			for(int j = 0; j < match_data.size(); j++) {
				if(j < match_data.size() - 1) {
					out_score.print(match_data.get(j) + "\n");
				} else {
					out_score.print(match_data.get(j));
				}
			}
			out_score.close();
			bw_score.close();
			br_score.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void write_new_player() {
		System.out.println("REGISTRATION_INTIATIED");
		try {

			System.out.println("First Name: ");
			String first_name = Main.GLOBAL_SCANNER.nextLine();
			System.out.println("Last Name: ");
			String last_name = Main.GLOBAL_SCANNER.nextLine();
			System.out.println("Pongalytics Handle: ");
			String handle = Main.GLOBAL_SCANNER.nextLine();
			
			Player p = new Player(first_name, last_name, handle);
			
			BufferedWriter bw_p_one = new BufferedWriter(new FileWriter("./src/data/player_data/registration_data/" + p.get_handle() + ".txt", false));
			PrintWriter out_p_one = new PrintWriter(bw_p_one);
			
			out_p_one.print(p.get_first() + "," + p.get_last() + "," + p.get_handle() + ",0,0");
			
			out_p_one.close();
			
			BufferedWriter bw_reg_pla = new BufferedWriter(new FileWriter("./src/data/player_data/registered_players.txt", true));
			PrintWriter out_reg_pla = new PrintWriter(bw_reg_pla);
			
			out_reg_pla.println();
			out_reg_pla.print(p.get_handle());
			
			out_reg_pla.close();
			System.out.println("REGISTRATION_COMPLETED");
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) throws IOException {
		
	}
	
	
	
	
	

}