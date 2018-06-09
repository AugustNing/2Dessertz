package engine.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import engine.GameElement;
import engine.GamePart;
import engine.actions.CollisionDamageAllSides;
import engine.actions.CollisionKillable;
import engine.actions.CollisionStopXMotion;
import engine.actions.CollisionStopYMotion;
import engine.actions.TimeKillable;
import engine.behaviors.Gravity;
import engine.behaviors.Killable;
import engine.behaviors.Killer;
import engine.behaviors.MainCharacter;
import engine.behaviors.MandatoryBehavior;
import engine.behaviors.Movable;
import engine.behaviors.MovableCharacter;
import engine.behaviors.Shooter;
import engine.behaviors.TimeRoutine2;
import engine.behaviors.TimeTracker;
import engine.events.elementevents.CollisionEvent;
import engine.events.elementevents.KeyInputEvent;
import engine.events.elementevents.TimeEvent;
import javafx.scene.input.KeyCode;

public class ModelGameState2 {
	private GamePart part;
	
	public ModelGameState2() {
		part = new GamePart(null);		
	}
	
	private void addMainCharacter() {
		GameElement mainCharacter = getMario();
		part.addGameElement(mainCharacter);
		//display.addNewElement(mainCharacter);
	}

	private GameElement getCreatedMario() {
		return part.getElements().get(0);
	}
	
	public GamePart getPart() {
		addMainCharacter();
		List<GameElement> elements = new ArrayList<GameElement>();
		getCreatedMario();
		for (double i = 0; i < 900; i+=40) {
			elements.add(getBlock(i, 500.0));
		}	
		
		for (double i = 300; i < 900; i+=40) {
			elements.add(getBlock(i, 300.0));
		}
		
		for (double i = 500; i < 3000; i+=400) {
			elements.add(getKoopa(i, 100.0));
		}
		
		elements.add(getBullet(300.0, 400.0, 50.0));

		
		for (GameElement el : elements) {
			part.addGameElement(el);
		}
		return part;
	}
	
	public GameElement getMario() {
		GameElement mario = new GameElement();
		//Note: Image path untested
		mario.addBehavior(new MandatoryBehavior(mario, "Mario", 200.0, 20.0, "rectangle", 100.0, 100.0, 20.0, 20.0, "MarioSMR.png"));
		List<Double> direction = new ArrayList<>(); direction.add(1.0); direction.add(0.0);
		mario.addBehavior(new MovableCharacter(mario, 0.0, direction));
		mario.addBehavior(new MainCharacter(mario, 1, true, true));
		mario.addBehavior(new Gravity(mario));
		mario.addBehavior(new TimeTracker(mario));
		TimeRoutine2 marioRoutines = new TimeRoutine2(mario);
		
		marioRoutines.addRoutine(5.0, (e, ge) -> {
			MovableCharacter mc = (MovableCharacter) mario.getBehavior(MovableCharacter.class);
			mc.jump();
		});
		mario.addBehavior(new Shooter(mario));
		marioRoutines.addRoutine(2.0,  (e, ge) -> {
			Shooter s = (Shooter) mario.getBehavior(Shooter.class);
			s.shootRight();
		});
		
		marioRoutines.addRoutine(7.0, (e, ge) -> {
			MovableCharacter mc = (MovableCharacter) mario.getBehavior(MovableCharacter.class);
			mc.setXVelocity(100.0);
		});
		mario.addBehavior(new TimeRoutine2(mario));
		
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
		block.addEventResponse(new CollisionEvent(block, CollisionEvent.ALL_SIDES, getMario(), CollisionEvent.ALL_SIDES), new CollisionKillable());
		return block;
	}
	
	public GameElement getBullet(Double xpos, Double ypos, Double v, List<Double> direction) {
		GameElement bullet = new GameElement();
		bullet.addBehavior(new MandatoryBehavior(bullet, "Bullet", xpos, ypos, "rectangle", 20.0, 20.0, 20.0, 20.0, "bullet.png"));
		bullet.addBehavior(new Movable(bullet, v, direction));
		bullet.addBehavior(new Killer(bullet, 10.0));
		bullet.addEventResponse(
				new CollisionEvent(
						bullet, 
						CollisionEvent.ALL_SIDES,
						new GameElement(MandatoryBehavior.REFER_ALL_ELEMENTS),
						CollisionEvent.ALL_SIDES),
				new CollisionDamageAllSides());
		return bullet;
	}
	
	public GameElement getBullet(Double xpos, Double ypos, Double v) {
		GameElement bullet = new GameElement();
		bullet.addBehavior(new MandatoryBehavior(bullet, "Bullet", xpos, ypos, "rectangle", 20.0, 20.0, 20.0, 20.0, "bullet.png"));
		bullet.addBehavior(new Movable(bullet, v, Arrays.asList(1.0, 0.0)));
		bullet.addBehavior(new Killer(bullet, 10.0));
		return bullet;
	}
}
