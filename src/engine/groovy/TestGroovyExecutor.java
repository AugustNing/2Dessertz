package engine.groovy;

import engine.GameElement;
import engine.behaviors.MandatoryBehavior;

public class TestGroovyExecutor {

    public static void main(String[] args){
    		GameElement ge = new GameElement();
//    		List<Double> doubles= new ArrayList<>();
//    		doubles.add(4.1);
//    		doubles.add(2.5);
//    		Gravity g = new Gravity(ge, 3.2, doubles);
    		ge.addBehavior(new MandatoryBehavior(ge, "bl", 0.0, 0.0));
    		GroovyExecutor executor = new GroovyExecutor();
    		executor.addToMap("myObject", ge);
    		executor.execute("import engine.GameElement");
    		executor.execute("println myObject");
    		executor.execute("println myObject.getAllBehaviors()[0]");
    		
    		System.out.println(executor.execute("myObject.behaviors.size()"));
//    		executor.execute()
    		
//    		ee.getResponses().stream().forEach(response -> executor.execute(response));
    }
	
}
