package tsbreuer.GameGen;

public enum Direction {
	
	UP("UP"), DOWN("DOWN"), LEFT("LEFT"), RIGHT("RIGHT");
	
	private String text;
	
	Direction(String text) {
		      this.text = text;
		   }

	public String getText() {
		      return this.text;
			}
}
