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
	
	public static Player find(String handle) {
		Player found = null;
		String[] player_data = null;
		try {
			BufferedReader br_base = new BufferedReader(new FileReader(new File(Database.PLAYER_BASE_LOC)));
			String line;
			if(br_base.ready()) {
				while((line = br_base.readLine()) != null) {
					if(line.equals(handle)){
						return new Player("/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registration_data/" + handle + ".txt");
					}
				}
			}
			br_base.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	

}