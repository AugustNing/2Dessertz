//1.Be able to select between Authoring Environment and Game Playing Environment.

/*
 * What we plan to do is create two buttons on the splash screen, one that will take you to the game authoring,
 * and one that takes you to the game player/engine. These will be assigned using our GUI buttons and ButtonEventHandler classes
 */

public DisplayUseCase {
	
	public static void main(String args[]) {
		GUIButton authoringButton = new GUIButton("To Authoring", new GoToAuthoring(authoring));
		GUIButton playerButton = new GUIButton("To Player", new GoToPlayer(player));
		
	/*
	 * Add code to display the buttons on the splash screen
	 */
	}
	
	public GoToAuthoring(GameAuthoring authoring) {
		public void execute() {
			authoring.start();
		}
	}
	
	public GoToPlayer(GamePlayer player) {
		public void execute() {
			player.start();
		}
	}
	
}
