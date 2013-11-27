/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package powerplant.ghe;

import powerplant.WorkingFluidObject;
import powerplant.fluid.Fluid;
import powerplant.gas.FlueGas;

/**
 *
 * @author DoorKip
 */
public class SuperHeater extends GasHeatExchanger{

	@Override
	public void setGasInput(FlueGas flueIn) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public FlueGas getGasInput() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public FlueGas getGasOutput() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public WorkingFluidObject setWorkingFluidInput(Fluid fluid) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public WorkingFluidObject setWorkingFluidOutput(Fluid fluid) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Fluid getWorkingFluidInput() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Fluid getWorkingFluidOutput() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean isSolved() {
		//TODO Implement isSolved in powerplant.ghe.SuperHeater
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
