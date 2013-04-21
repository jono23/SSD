package app;

public class Feature extends Bookable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -736031871320357213L;
	String name;

	public Feature(Accommodation accommodation, String name) {
		super(accommodation);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return " Feature -- " + name;
	}
	
	
	
}
