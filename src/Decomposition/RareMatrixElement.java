package Decomposition;

public class RareMatrixElement {
	private double value;
	private long columnIndex;
	
	public RareMatrixElement(double initialValue, long initialColumnIndex) {
		this.value = initialValue;
		this.columnIndex = initialColumnIndex;
	}
	
	/**
	 * Default constructor.
	 */
	public RareMatrixElement() {}
	
	public double getValue() {
		return this.value;
	}
	
	public long getColumnIndex() {
		return this.columnIndex;
	}
	
	/**
	 * Special method for setting the value, that takes into consideration that you 
	 * sometimes have to add the new value to the old one.
	 * @param newValue
	 * @param add Specifies whether you add the new value to the old one or not.
	 */
	public void setValue(double newValue) {
		this.value = newValue;
	}
	
	public void setColumnIndex(long newColumnIndex) {
		this.columnIndex = newColumnIndex;
	}
}
