import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Player {
	private int wins;
	private int losses;
	private String win_rate;
	private String first_name;
	private String last_name;
	private String player_handle;
	private Player last_opponent;
	private Match last_match;
	private List<Match> matches;
	
	public Player(String first_name, String last_name, String player_handle) {
		this.wins = 0;
		this.losses = 0;
		this.win_rate = "0.00";
		this.first_name = first_name;
		this.last_name = last_name;
		this.player_handle = player_handle;
		this.last_opponent = null;
		this.last_match = null; 
		this.matches = new ArrayList<Match>();
	}
	
	public Player(String file_name) {
		String[] player_data = null;
		List<String> match_data = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(file_name)));
			String line;
			if(br.ready()) {
				player_data = br.readLine().split(",");;
				while((line = br.readLine()) != null) {
					match_data.add(line);
					line = br.readLine();
				}
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		this.first_name = player_data[0];
		this.last_name = player_data[1];
		this.player_handle = player_data[2];
		this.wins = Integer.parseInt(player_data[3]);
		this.losses = Integer.parseInt(player_data[4]);
		DecimalFormat df = new DecimalFormat("#.00");
		this.win_rate = df.format((double)wins / ((double)wins + (double)losses) * 100);
		this.last_opponent = null;
		this.last_match = null;
	}
	
	
	public String toString() {
		return this.last_name + ", " + this.first_name + ": W:" + this.wins + " L:" + this.losses + " WR:" + this.win_rate + " L_O:" +
			   this.last_opponent + " L_M:" + this.last_match;
	}
	
	
	public static void main(String[] args){
		Player p = new Player("/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registration_data/regist_player_0.txt");
		System.out.println(p.toString());
	}
	
}
