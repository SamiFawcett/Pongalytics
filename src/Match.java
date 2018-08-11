import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

public class Match {
	private String id;
	private boolean in_progress;
	public static boolean players_confirmed;
	private Player winner;
	private Player loser;
	private int winning_score;
	private int losing_score;
	public static HashMap<String, String[]> ID_MAP;
	
	
	public Match(Player p1, Player p2) {
		id = generate_id();
		in_progress = true;
		this.winner = null;
		this.loser = null;
		Match.players_confirmed = true;
		this.winning_score = -1;
		this.losing_score = -1;
		Match.ID_MAP = null;
		
	}
	
	public Match(String match_id) {
		this.id = match_id;
		this.in_progress = false;
		this.players_confirmed = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(Database.MATCH_BASE_ALL_LOC)));
			String[] tokens = null;
			if(br.ready()) {
				String line;
				while((line = br.readLine()) != null) {
					if(line.contains(this.id)) {
						tokens = line.split(" ");
					}
				}
			}
			br.close();
			if(!Match.players_confirmed) {
				this.winner = new Player("/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registration_data/" + tokens[0] + ".txt");
				this.loser = new Player("/Users/cocop/eclipse-workspace/Pongalytics/src/data/player_data/registration_data/" + tokens[1] + ".txt");
			}
			this.players_confirmed = true;
			String[] scores = tokens[2].split("-");
			Match.ID_MAP = new HashMap<String, String[]>();
		    ID_MAP.put(this.id, scores);
			this.winning_score = Integer.parseInt(scores[0]);
			this.losing_score = Integer.parseInt(scores[1]);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public String toString() {
		return "WINNER: " + this.winner.toString() + ":: W_S:" + this.winning_score + "\n" + "LOSER: " + this.loser.toString() + ":: L_S:" + this.losing_score + "\n";
	}
	
	public Player get_winner() {
		return this.winner;
	}
	
	public Player get_loser() {
		return this.loser;
	}
	
	private String generate_id() {
		int id_length = 8;
		String id = "";
		Calendar cal = Calendar.getInstance();
		for(int i = 0; i < id_length; i++) {
			int rand = (int)(Math.random() * 10); 
			id+=rand;
		}
		return "MAT" + id + cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public String get_id() {
		return id;
	}
	
	
	
	public static void main(String[] args) {
		String deserialized =  "MAT866563501";
		Match m2 = new Match(deserialized);
		System.out.println(m2.toString());
	}
}
