/*
 * Copyright (C) 2013 DoorKip
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
