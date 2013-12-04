

package powerplant.object.ghe;

import powerplant.gas.FlueGas;
import powerplant.fluid.Fluid;

/**
 *
 * @author DoorKip
 */
public class Boiler extends GasHeatExchanger{

	@Override
	public void solve() {
		//TODO Implement solve in powerplant.ghe.Boiler
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public void setGasInput(FlueGas flueIn) {
		gasInput = flueIn;
	}

	@Override
	public FlueGas getGasInput() {
		return gasInput;
	}

	@Override
	public FlueGas getGasOutput() {
		return gasOutput;
	}

	@Override
	public GasHeatExchanger setWorkingFluidInput(Fluid fluid) {
		workingFluidInput = fluid;
		return this;
	}

	@Override
	public GasHeatExchanger setWorkingFluidOutput(Fluid fluid) {
		workingFluidOutput = fluid;
		return this;
	}

	@Override
	public Fluid getWorkingFluidInput() {
		return workingFluidInput;
	}

	@Override
	public Fluid getWorkingFluidOutput() {
		return workingFluidOutput;
	}

	@Override
	public boolean isSolved() {
		return solved;
	}
	
	private boolean solved = false;
	private Fluid workingFluidInput;
	private Fluid workingFluidOutput;
	private FlueGas gasInput;
	private FlueGas gasOutput;
	private double massFlow;

}
