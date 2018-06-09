
public interface Serializer {

	/**
	 * Converts game to XML. Used by game authoring environment.
	 * 
	 * @param fileName
	 *            desired name of XML game file to be created
	 * @param gameObjects
	 *            list of GameObjects which the game is comprised of
	 */
	void gameAuthorToXML(String fileName, List<GameObject> gameObjects);

	/**
	 * Converts game to XML. Used by game engine.
	 * 
	 * @param fileName
	 *            desired name of XML game file to be created
	 * @param gameObjects
	 *            list of GameObjects which the game is comprised of
	 */
	void gameEngineToXML(String fileName, List<GameObject> gameObjects);

	/**
	 * Retrieves game information from an XML file specified by an inputted string
	 * 
	 * @param fileName
	 *            name of game file
	 * @return XML file of specified game
	 */
	File getXMLFile(String fileName);

}
