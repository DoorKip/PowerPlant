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
	
	public Boiler(Fluid inputFluid){
		workingFluidInput = inputFluid;
		try{workingFluidOutput = inputFluid.getClass().newInstance();}
		catch(InstantiationException | IllegalAccessException e){System.out.println("FUCK");}
	}

	@Override
	public void solve() {
		if(massFlow == 0){calcMassFlow();}
		calcPressure();
		calcToSatVapor();
	}
	
	private boolean calcPressure(){
		//Ideal case for now
		if(workingFluidInput.getPressure() != 0 && workingFluidOutput.getPressure() == 0){
			workingFluidOutput.setPressure(workingFluidInput.getPressure());
			return true;
		} else if( workingFluidInput.getPressure() == 0 && workingFluidOutput.getPressure() != 0){
			workingFluidInput.setPressure(workingFluidOutput.getPressure());
			return true;
		}
		return false;
	}
	
	private boolean calcToSatVapor(){
		if(workingFluidInput.getSpecificEnthalpy() != 0 && workingFluidOutput.getSpecificEnthalpy() != 0){
			specificEnthalpyFlow = workingFluidOutput.getSpecificEnthalpy() - workingFluidInput.getSpecificEnthalpy();
		} else if(gasInput.getTemperature() != 0 && workingFluidOutput.getPressure() != 0 && workingFluidInput.getSpecificEnthalpy() != 0 && gasOutput.getTemperature() == 0){
			workingFluidOutput.setQuality(1);
		}
		return false;
	}
	
	private boolean designPipes(){
		if(specificEnthalpyFlow != 0 && massFlow != 0){
			
		}
		return false;
	}
	
	private void calcMassFlow(){
		if(workingFluidInput.getMassFlow()!=0 && workingFluidOutput.getMassFlow()==0){
			massFlow = workingFluidInput.getMassFlow();
			workingFluidOutput.setMassFlow(massFlow);
		} else if(workingFluidInput.getMassFlow()==0 && workingFluidOutput.getMassFlow()!=0){
			massFlow = workingFluidOutput.getMassFlow();
			workingFluidInput.setMassFlow(massFlow);
		}
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
	private double specificEnthalpyFlow;

}
