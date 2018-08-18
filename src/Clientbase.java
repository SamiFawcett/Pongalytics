import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class Clientbase {
	public final static String PLAYER_BASE_LOC = "/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registered_players.txt";
	public final static String MATCH_BASE_ALL_LOC = "/Users/cocop/eclipse-workspace/Pongalytics/src/data/match_data/all_pong_data.txt";
	public final static String MATCH_BASE_TEMP_LOC = "/Users/cocop/eclipse-workspace/Pongalytics/src/data/match_data/pong_data_summer_2018.txt";
	public static Player LOGGED_IN = null;
	
	public static Player find(String handle, boolean recursive) {
		Player found = null;
		try {
			BufferedReader br_base = new BufferedReader(new FileReader(new File(Clientbase.PLAYER_BASE_LOC)));
			String line;
			if(br_base.ready()) {
				while((line = br_base.readLine()) != null) {
					if(line.equals(handle)){
						br_base.close();
						found = new Player("/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registration_data/" + handle + ".txt");
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
		Player p = Clientbase.find(handle, false);
		if(p != null) {
			LOGGED_IN = p;
			System.out.println(p.get_first() + " logged in.");
			return true;
		}
		return false;
	}
	
	
	public static void write_match(String winner_handle, int winner_score, String loser_handle, int loser_score, String m_id) {
		try {
			//write to pong files first
			BufferedWriter bw_pong = new BufferedWriter(new FileWriter(Clientbase.MATCH_BASE_ALL_LOC, true));
			PrintWriter out_pong = new PrintWriter(bw_pong);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate localDate = LocalDate.now();
			out_pong.println();
			out_pong.print(dtf.format(localDate) + " " + winner_handle + " " + loser_handle + " " + winner_score + "-" + loser_score + " " + m_id);
			out_pong.close();			
			
			BufferedWriter bw_p_one = new BufferedWriter(new FileWriter("/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registration_data/" + winner_handle + ".txt", true));
			BufferedWriter bw_p_two = new BufferedWriter(new FileWriter("/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registration_data/" + loser_handle + ".txt", true));
			PrintWriter out_p_one = new PrintWriter(bw_p_one);
			PrintWriter out_p_two = new PrintWriter(bw_p_two);
			out_p_one.println();
			out_p_two.println();
			out_p_one.print(m_id);
			out_p_two.print(m_id);
			out_p_one.close();
			out_p_two.close();
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
			
			BufferedWriter bw_p_one = new BufferedWriter(new FileWriter("/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registration_data/" + p.get_handle() + ".txt", false));
			PrintWriter out_p_one = new PrintWriter(bw_p_one);
			
			out_p_one.print(p.get_first() + "," + p.get_last() + "," + p.get_handle() + ",0,0");
			
			out_p_one.close();
			
			BufferedWriter bw_reg_pla = new BufferedWriter(new FileWriter("/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registered_players.txt", true));
			PrintWriter out_reg_pla = new PrintWriter(bw_reg_pla);
			
			out_reg_pla.println();
			out_reg_pla.print(p.get_handle());
			
			out_reg_pla.close();
			System.out.println("REGISTRATION_COMPLETED");
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	

}