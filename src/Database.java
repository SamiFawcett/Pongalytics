import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Database {
	public final static String PLAYER_BASE_LOC = "/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registered_players.txt";
	public final static String MATCH_BASE_ALL_LOC = "/Users/cocop/eclipse-workspace/Pongalytics/src/data/match_data/all_pong_data.txt";
	public final static String MATCH_BASE_TEMP_LOC = "/Users/cocop/eclipse-workspace/Pongalytics/src/data/match_data/pong_data_summer_2018.txt";
	
	public static Player find(String handle, boolean recursive) {
		Player found = null;
		try {
			BufferedReader br_base = new BufferedReader(new FileReader(new File(Database.PLAYER_BASE_LOC)));
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
			System.out.println(handle + " was not found.");
			return find(new Scanner(System.in).nextLine(), recursive);
			
		}
		else return null;
		
		
	}
	
	
	public static void write_match(String player_one_handle, String player_two_handle, String m_id) {
		try {
			BufferedWriter bw_p_one = new BufferedWriter(new FileWriter("/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registration_data/" + player_one_handle + ".txt", true));
			BufferedWriter bw_p_two = new BufferedWriter(new FileWriter("/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registration_data/" + player_two_handle + ".txt", true));
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
	
	

}