import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
	public  HashMap<String, String[]> ID_MAP;
	
	
	public Match(Player p1, Player p2) {
		this.id = generate_id();
		this.in_progress = true;
		this.winner = null;
		this.loser = null;
		Match.players_confirmed = true;
		this.winning_score = -1;
		this.losing_score = -1;
		this.ID_MAP = null;
		
	}
	
	public Match(String match_id) {
		this.id = match_id;
		this.in_progress = false;
		Match.players_confirmed = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(Clientbase.MATCH_BASE_ALL_LOC)));
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
				this.winner = Clientbase.find(tokens[0], false);
				this.loser = Clientbase.find(tokens[1], false);
			}
			Match.players_confirmed = true;
			String[] scores = tokens[2].split("-");
			this.ID_MAP = new HashMap<String, String[]>();
		    ID_MAP.put(this.id, scores);
			this.winning_score = Integer.parseInt(scores[0]);
			this.losing_score = Integer.parseInt(scores[1]);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void start() throws Exception {
		Keyword k = new Keyword();
		System.out.println("Player One: ");
		Player player_one = Clientbase.find(k.read(), true);
		System.out.println("Player Two: ");
		Player player_two = Clientbase.find(k.read(), true);
		Match m = new Match(player_one, player_two);
		
		System.out.println("MATCH_INTIATED");
		
		System.out.println(player_one.get_first() + " vs " + player_two.get_first());
		
		System.out.println("Player One Score: ");
		String player_one_score = k.read();
			
		System.out.println("Player Two Score: ");
		String player_two_score = k.read();
		
		if(Integer.parseInt(player_one_score) > Integer.parseInt(player_two_score)) {
			m.set_score(Integer.parseInt(player_one_score), Integer.parseInt(player_two_score));
			Clientbase.write_match(player_one.get_handle(), Integer.parseInt(player_one_score), player_two.get_handle(), Integer.parseInt(player_two_score), m.get_id());
		} else {
			m.set_score(Integer.parseInt(player_two_score), Integer.parseInt(player_one_score));
			Clientbase.write_match(player_two.get_handle(), Integer.parseInt(player_two_score), player_one.get_handle(), Integer.parseInt(player_one_score), m.get_id());
		}
		
		
		System.out.println("MATCH_TERMINATED");
	}
	
	public String toString() {
		return "WINNER: " + this.winner.toString() + ":: W_S:" + this.winning_score + "\n" + "LOSER: " + this.loser.toString() + ":: L_S:" + this.losing_score + "\n";
	}
	
	public Player get_winner() {
		return this.winner;
	}
	
	private void set_score(int winning_score, int losing_score) {
		this.winning_score = winning_score;
		this.losing_score = losing_score;
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
	
	
	
	public static void main(String[] args) throws Exception {
	}
}
