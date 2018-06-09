package authoring;

/**
 * @author Edward Zhuang
 * Serializable form of SceneBackgroundImage. Does not contain FX components.
 */
public class SceneBackgroundImageSerializable {

	private Double xPos;
	private Double yPos;
	private Double xSize;
	private Double ySize;
	private String imagePath;
	
	public SceneBackgroundImageSerializable(Double xPos, Double yPos, Double xSize, Double ySize, String imagePath) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		this.imagePath = imagePath;
	}
	
	public Double getxPos() {
		return xPos;
	}


	public void setxPos(Double xPos) {
		this.xPos = xPos;
	}


	public Double getyPos() {
		return yPos;
	}


	public void setyPos(Double yPos) {
		this.yPos = yPos;
	}


	public Double getxSize() {
		return xSize;
	}


	public void setxSize(Double xSize) {
		this.xSize = xSize;
	}


	public Double getySize() {
		return ySize;
	}


	public void setySize(Double ySize) {
		this.ySize = ySize;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
