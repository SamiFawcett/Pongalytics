import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {
	private final static String PLAYER_BASE_LOC = "/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registered_players.txt";
	private final static String MATCH_BASE_ALL_LOC = "/Users/cocop/eclipse-workspace/Pongalytics/src/data/match_data/all_pong_data.txt";
	private final static String MATCH_BASE_TEMP_LOC = "/Users/cocop/eclipse-workspace/Pongalytics/src/data/match_data/pong_data_summer_2018.txt";
	
	private static Player find(Player p) {
		
		String[] player_data = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(Database.PLAYER_BASE_LOC)));
			String line;
			if(br.ready()) {
				player_data = br.readLine().split(",");;
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return p;
		
	}
}