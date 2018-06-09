package engine.groovy;

import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/** 
 * 
 * @author: Summer
 **/
public class GroovyExecutor {

	private static ScriptEngine engine;
	
	public GroovyExecutor() {
		engine = new ScriptEngineManager().getEngineByName("groovy");
	}
	
	public Object execute(String script) {
		try {
			return engine.eval(script);
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new GroovyException("cannot evaluate groovy expression");
		}
	}
	
	public void addToMap(String name, Object toAdd) {
		engine.put(name, toAdd);
	}
	
	public static ScriptEngine getScriptEngine() {
		return engine;
	}
	
	public void addMaptoEngine(Map<String, Object> toAdd) {
		for(Entry<String, Object> e : toAdd.entrySet()) {
			addToMap(e.getKey(), e.getValue());
		}
	}
	
}
