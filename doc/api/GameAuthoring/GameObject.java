public interface GameObject {

	public void addProperty(Property propToAdd); //each game object will have properties that describe how it behaves. 
	
	public void removeProperty(Property propToRemove); //the user can remove a property after assigning it
	
	public String getName(); //each object is known by its name
	
	public void setName(String newName); //method sets or resets the name of the object
	
	public List<Property> getProperties(); //returns the list of all properties associated with the object
	
	public void setImage(Image image); //sets the image that represents the object
	
	public Image getImage(); //returns the objects image
	
	public void setImageSize(double x, double y); //sets the size of the image
	
	public double getImageWidth(); //returns the width of the image representing the object
	
	public double getImageHeight(); //returns the height of the image representing the object
	
}
