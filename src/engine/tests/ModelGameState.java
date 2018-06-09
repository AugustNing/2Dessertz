package engine.tests;

import java.util.ArrayList;
import java.util.List;

import engine.DisplayState;
import engine.GameElement;
import engine.GamePart;
import engine.actions.CollisionKillable;
import engine.actions.CollisionStopXMotion;
import engine.actions.CollisionStopYMotion;
import engine.actions.IncrementTimeTracker;
import engine.actions.MoveIfMoving;
import engine.actions.TimeCreateGameElement;
import engine.actions.TimeGravity;
import engine.actions.TimeMovable;
import engine.actions.TimeSwitchXMotion;
import engine.actions.TimeSwitchYMotion;
import engine.behaviors.AddsGameElement;
import engine.behaviors.Gravity;
import engine.behaviors.Killable;
import engine.behaviors.MainCharacter;
import engine.behaviors.MandatoryBehavior;
import engine.behaviors.Movable;
import engine.behaviors.MovableCharacter;
import engine.behaviors.TimeRoutine2;
import engine.behaviors.TimeTracker;
import engine.behaviors.TrackMainCharacter;
import engine.events.elementevents.CollisionEvent;
import engine.events.elementevents.KeyInputEvent;
import engine.events.elementevents.TimeEvent;
import javafx.scene.input.KeyCode;

public class ModelGameState {
	private GamePart part;
	private DisplayState display;
	
	public ModelGameState() {
		display = new DisplayState(part);
		
		addMainCharacter();
		ArrayList<GameElement> elements = new ArrayList<GameElement>();
		getCreatedMario();
		for (double i = 0; i < 900; i+=40) {
			elements.add(getBlock(i, 500.0));
		}
//		for (double i = 20; i < 500 ; i+=40) {
//			elements.add(getMovableBlock(0.0, i));
//		}
		
		
		
		for (double i = 500; i < 3000; i+=400) {
			elements.add(getKoopa(i, 100.0));
		}

		
		for (GameElement el : elements) {
			part.addGameElement(el);
			//display.addNewElement(el);
		}
		display = new DisplayState(part);
		//display.update(state);
	}
	
	private void addMainCharacter() {
		GameElement mainCharacter = getMario();
		part.addGameElement(mainCharacter);
		//display.addNewElement(mainCharacter);
	}
	
	private GameElement getMovableBlock(double xpos, double ypos) {
		List<Double> direction = new ArrayList<>(); direction.add(1.0); direction.add(1.0);
		GameElement block = new GameElement();
		//block.addBehavior(new MandatoryBehavior(block, "Block", xpos, ypos, new RectangleShape(40.0, 40.0), "mario_block.png"));
		block.addBehavior(new MandatoryBehavior(block, "Block", xpos, ypos, "rectangle", 40.0, 40.0, 40.0, 40.0, "mario_block.png"));
		block.addBehavior(new TimeTracker(block));
		block.addBehavior(new TimeRoutine2(block, 5, true));
		block.addBehavior(new TrackMainCharacter(block, getCreatedMario()));
		block.addBehavior(new Movable(block, 20.0, direction));
		block.addEventResponse(new TimeEvent(0.0), new IncrementTimeTracker());
		block.addEventResponse(new TimeEvent(0.0), new TimeMovable());
		block.addEventResponse(new TimeEvent(0.0), new TimeSwitchXMotion());
		block.addEventResponse(new TimeEvent(0.0), new TimeSwitchYMotion());
		block.addEventResponse(new TimeEvent(0.0), new MoveIfMoving());
		return block;
	}

	private GameElement getCreatedMario() {
		return part.getElements().get(0);
	}
	
	public DisplayState getDisplay() {
		return display;
	}
	
	public GamePart getPart() {
		return part;
	}
	
	public GameElement getMario() {
		GameElement mario = new GameElement();
		//Note: Image path untested
		mario.addBehavior(new MandatoryBehavior(mario, "Mario", 200.0, 20.0, "ellipse", 100.0, 100.0, 100.0, 100.0, "MarioSMR.png"));
		List<Double> direction = new ArrayList<>(); direction.add(1.0); direction.add(0.0);
		mario.addBehavior(new MovableCharacter(mario, 0.0, direction));
		mario.addBehavior(new MainCharacter(mario, 1, true, true));
		mario.addBehavior(new Gravity(mario));
		mario.addBehavior(new TimeTracker(mario));
		mario.addBehavior(new TimeRoutine2(mario, 7, true));
		mario.addBehavior(new AddsGameElement(mario, getCreatedBlock(0.0, 0.0)));
		
		mario.addEventResponse(new TimeEvent(0.0), new TimeCreateGameElement());
		
		
		//Adding Time Responses
		mario.addEventResponse(new TimeEvent(0.0), new IncrementTimeTracker());
		mario.addEventResponse(new TimeEvent(0.0), new TimeMovable());
		mario.addEventResponse(new TimeEvent(0.0), new TimeGravity());
		
		// Response to up arrow key is to jump
		mario.addEventResponse(new KeyInputEvent(KeyCode.W), (event, element) -> {
			MovableCharacter mov = (MovableCharacter) element.getBehavior(MovableCharacter.class);
			mov.jump();
		});
		
		
		// Response to Right arrow key is to move right
		mario.addEventResponse(new KeyInputEvent(KeyCode.D), (event, element) -> {
			Movable mov = (Movable) element.getBehavior(Movable.class);
			mov.setXVelocity(200.0);
		});
		
		// Response to Left arrow key is to move left
		mario.addEventResponse(new KeyInputEvent(KeyCode.A), (event, element) -> {
			Movable mov = (Movable) element.getBehavior(Movable.class);
			mov.setXVelocity(-200.0);
		});
		
		mario.addEventResponse(new CollisionEvent(mario, CollisionEvent.VERTICALS, getBlock(0.0, 0.0), CollisionEvent.VERTICALS), new CollisionStopYMotion());
		mario.addEventResponse(new CollisionEvent(mario, CollisionEvent.SIDES, getBlock(0.0, 0.0), CollisionEvent.SIDES), new CollisionStopXMotion());

		return mario;
	}
	
	public GameElement getBlock(Double xpos, Double ypos) {
		GameElement block = new GameElement();
		block.addBehavior(new MandatoryBehavior(block, "Block", xpos, ypos, "rectangle", 40.0, 40.0, 40.0, 40.0, "mario_block.png"));
		return block;
	}
	
	public GameElement getCreatedBlock(Double xpos, Double ypos) {
		List<Double> direction = new ArrayList<>(); direction.add(1.0); direction.add(0.0);
		GameElement block = new GameElement();
		block.addBehavior(new MandatoryBehavior(block, "Block", xpos, ypos, "rectangle", 40.0, 40.0, 40.0, 40.0, "mario_block.png"));
		block.addBehavior(new Movable(block, 5.0, direction));
		block.addEventResponse(new TimeEvent(0.0), new TimeMovable());
		return block;
	}
	
	public GameElement getBack(Double xpos, Double ypos) {
		GameElement block = new GameElement();
		block.addBehavior(new MandatoryBehavior(block, "Back", xpos, ypos, "rectangle", 900.0, 590.0, 900.0, 590.0, "prairie.jpg"));
		return block;
	}
	
	public GameElement getKoopa(Double xpos, Double ypos) {
		GameElement block = new GameElement();
		
		block.addBehavior(new MandatoryBehavior(block, "Koopa", xpos, ypos, "rectangle", 60.0, 80.0, 60.0, 80.0, "koopa.png"));
		List<Double> direction = new ArrayList<>(); direction.add(-1.0); direction.add(0.0);
		block.addBehavior(new Movable(block, 20.0, direction));
		block.addBehavior(new Killable(block, 100.0));
		block.addEventResponse(new TimeEvent(0.0), new TimeMovable());
		block.addEventResponse(new CollisionEvent(block, CollisionEvent.ALL_SIDES, getMario(), CollisionEvent.ALL_SIDES), new CollisionKillable());
		return block;
	}
}
