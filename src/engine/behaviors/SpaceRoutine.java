package engine.behaviors;

import java.util.ArrayList;
import java.util.List;

import authoring.groovy.GroovyMethod;
import engine.GameElement;

public class SpaceRoutine extends Movable {
	
	private List<List<Double>> points2Visit;
	private Integer currentIndex;
	
	public SpaceRoutine(GameElement ge) {
		this (ge, new ArrayList<>(), 20.0);
	}
	
	public SpaceRoutine(GameElement ge, List<List<Double>> elements, Double v) {
		super(ge);
		points2Visit = elements;	
		currentIndex = 0;
		setVelocity(v);
	}
	
	@GroovyMethod
	@Override
	public void move(Double time) {
		setDirection(points2Visit.get(currentIndex));
		super.move(time);
	}
	
}
