// Be able to detect a collision between two game elements.

public static void main(String[] args) {
	LinkedList<ElementEvent> events = new LinkedList<ElementEvent>();
	//GameState is given
	GameState state = new GameState
	for (GameElement e1 : state) {
		for (GameElement e2 : state) {
			if (e1 != e2) {
				Shape intersect = Shape.intersect(e1.getImageView(), e2.getImageView());
				if (intersect.getBoundsInLocal().getWidth != -1) {
					ElementEvent collision = new CollisionEvent(e1, e2)
					events.add(collision);
				}
			}
		}
	}
}
