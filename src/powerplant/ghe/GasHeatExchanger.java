/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package powerplant.ghe;
import powerplant.WorkingFluidObject;
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
