package data;

/**
 * @author Edward Zhuang
 * Interface implemented by ObjectInfoPanel allowing the GameObjectManager to notify it. Used since ObjectInfoPanel observes multiple observables.
 */
public interface LibraryObserver {
	void notifyObserver();
}
