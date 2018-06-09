public class GameEngineBehaviorUseCase {

	/*
	 * This class represents a behavior for a game element that coudld lose lives
	 */
	
	private numLives; // The main variable required for this behavior
	private parentGE; // The parent GameElement this behavior is tied too
	
	public KillableBehavior(GameElement parent, int lives) {
		numLives = lives;
		parentGE = parent;
	}
	
	
	/*
	 * This is a method that allows other behaviors to decrement the life of the object that contains this game
	 */
	public void decrementlife() {
		numLives -= 1;
		if (numLives == 0) {
			// 
			/*
			 * GameEvent objects are passed upto the Event Manager which will process this Event and 
			 * remove the parent Game Element from the GameState
			 */
			GameEvent destroyThisObject = new KillElement(parentGE);  
			parentGE.addToGameEvents(destroyThisObject);
		}
	}
	
	/*
	 * Publicly available method that allows other behaviors to increment the lives of this game element.
	 */
	public void incrementLife() {
		numLives += 1;
	}
	

}
