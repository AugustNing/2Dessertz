package authoring;

/**
 * Event response is an action that occurs in response to an event trigger
 * has a name and a string of content 
 * 
 * This class is not used
 * 
 * @author: Summer
 */
public class EventResponse {
	
	private String myName;
	private String myContent;

	public EventResponse() {
		
	}
	
	/**
	 * returns name of event response
	 */
	public String getName() {
		return myName;
	}
	
	/**
	 * sets the name of the event response to name
	 */
	public void setName(String name) {
		myName = name;
	}
	
	/**
	 * sets the content of the event response to content
	 */
	public void setMyContent(String content) {
		myContent = content;
	}
	
	/**
	 * returns the content of the event response
	 */
	public String getMyContent() {
		return myContent;
	}
	
	/**
	 * returns name of event response
	 */
	public String toString() {
		return getName();
	}
	
	/**
	 * method is used to clone the event when an object is cloned
	 */
	public EventResponse clone() {
		EventResponse er = new EventResponse();
		er.setName(this.getName());
		er.setMyContent(this.getMyContent());
		return er;
	}
		
}
