

package powerplant.object.ghe;
import powerplant.object.WorkingFluidObject;
import powerplant.gas.FlueGas;
/**
 *
 * @author DoorKip
 */
public abstract class GasHeatExchanger extends WorkingFluidObject{
	public abstract void setGasInput(FlueGas flueIn);
	public abstract FlueGas getGasInput();
	public abstract FlueGas getGasOutput();
}
