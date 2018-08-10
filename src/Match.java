import java.util.Calendar;

public class Match {
	private String id;
	
	public Match(Player p1, Player p2) {
		id = generate_id();
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
		Match m1 = new Match(new Player("S", "F", "SF"), new Player("S", "S", "SS"));
		System.out.println(m1.get_id());
	}
}
