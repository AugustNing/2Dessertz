package engine.groovy;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class GroovyEngine {
	private static ScriptEngine eng = new ScriptEngineManager().getEngineByName("groovy");
	
	public static ScriptEngine returnScriptEngine() {
		return eng;
	}
}
