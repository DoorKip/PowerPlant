

package powerplant.gas;

/**
 *
 * @author DoorKip
 */
public class FlueGas {
	public double getTemperature(){
		return temperature;
	}
	
	public FlueGas setTemperature(double temperature){
		this.temperature = temperature;
		return this;
	}
	
	private double temperature;
}
