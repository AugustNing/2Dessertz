package engine.behaviors;
import java.util.List;

import authoring.groovy.GroovyMethod;
import engine.GameElement;

public class MovableCharacter extends Movable {
//	private String jumpAudioFilePath = "";
	private Double jumpSpeed;
	private Double rightSpeed;
	private Double leftSpeed;

	public MovableCharacter(GameElement ge, Double vel, List<Double> dir) {
		super(ge, vel, dir);
		
	}
	
	public MovableCharacter(GameElement ge, Double vel, List<Double> dir, String jumpAudioFilePath) {
		super(ge, vel, dir);
//		this.jumpAudioFilePath = jumpAudioFilePath;
	}
	
	public MovableCharacter(GameElement ge, Double xVel, Double yVel) {
		super(ge, xVel, yVel);
	}
	
	public MovableCharacter(GameElement ge) {
		super(ge);
		jumpSpeed = 50.0;
		rightSpeed = 50.0;
		leftSpeed = 50.0;
	}
	
	@Override
	public void move(Double time) {
		super.move(time);
		this.setXVelocity(this.getXVelocity() * .98);
	}
	
	@GroovyMethod
	public void jump() {
//		System.out.println("Jumping");
		setYVelocity(getYVelocity() - jumpSpeed);
//		System.out.println(getParent());
//		System.out.println(getParent().hashCode());
//		new Printer().printGameElement(getParent());
//		if (!jumpAudioFilePath.equals("")) {
//			getParent().addGameEvent(new PlayAudioEvent(jumpAudioFilePath));
//		}
	}
	
	@GroovyMethod
	public void moveLeft() {
		System.out.println("Moing Left");
		setXVelocity(-leftSpeed);
	}
	
	@GroovyMethod
	public void moveRight() {
		System.out.println("Moing Right");
		setXVelocity(leftSpeed);
	}

}
