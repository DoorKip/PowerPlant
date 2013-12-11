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

package powerplant.object;

import powerplant.fluid.Fluid;

/**
 * Consumes power. Pressurizes fluids. 'Nuf said.
 * @author DoorKip
 */
public class Pump extends WorkingFluidObject{

	public Pump(Fluid inputFluid){
		workingFluidInput = inputFluid;
		try{workingFluidOutput = inputFluid.getClass().newInstance();}
		catch(InstantiationException | IllegalAccessException e){throw new Error("Fluid instance creation failed");}
	}
	
	@Override
	public void solve(){
		if(massFlow == 0){calcMassFlow();}
		calcPressure();
		calcEnthalpy();
		calcEntropy();
	}
	
	private boolean calcMassFlow(){
		if(workingFluidInput.getMassFlow() != 0 && workingFluidOutput.getMassFlow() == 0){
			massFlow = workingFluidInput.getMassFlow();
			workingFluidOutput.setMassFlow(massFlow);
			return true;
		} else if(workingFluidInput.getMassFlow() == 0 && workingFluidOutput.getMassFlow() != 0){
			massFlow = workingFluidOutput.getMassFlow();
			workingFluidInput.setMassFlow(massFlow);
			return true;
		} else if(workingFluidInput.getMassFlow() != 0 && workingFluidOutput.getMassFlow() != 0 && workingFluidInput.getMassFlow() != workingFluidOutput.getMassFlow()){
			throw new Error("Mass Flow Inequity");
		}
		return false;
	}
	
	public boolean calcPressure(){
		//TODO Figure out why referencing workingFluidInput.nominalLiquidDensity does not work here
		double nominalLiquidDensity = 995;
		if(workingFluidInput.getPressure() != 0 && workingFluidOutput.getPressure() != 0 && specificWork ==0){
			specificWork = (workingFluidInput.getPressure() - workingFluidOutput.getPressure()) / nominalLiquidDensity;
			return true;
		} else if(workingFluidInput.getPressure() != 0 && workingFluidOutput.getPressure() == 0 && specificWork != 0){
			workingFluidOutput.setPressure(specificWork * nominalLiquidDensity - workingFluidInput.getPressure());
			return true;
		} else if(workingFluidInput.getPressure() == 0 && workingFluidOutput.getPressure() != 0 && specificWork != 0){
			workingFluidInput.setPressure(specificWork * nominalLiquidDensity + workingFluidOutput.getPressure());
			return true;
		}
		return false;
	}
	
	public boolean calcEnthalpy(){
		if(workingFluidInput.getSpecificEnthalpy() != 0 && workingFluidOutput.getSpecificEnthalpy() != 0 && specificWork == 0){
			specificWork = workingFluidInput.getSpecificEnthalpy() - workingFluidOutput.getSpecificEnthalpy();
			return true;
		} else if(workingFluidInput.getSpecificEnthalpy() != 0 && workingFluidOutput.getSpecificEnthalpy() == 0 && specificWork != 0){
			workingFluidOutput.setSpecificEnthalpy(workingFluidInput.getSpecificEnthalpy() - specificWork);
			return true;
		} else if(workingFluidInput.getSpecificEnthalpy() == 0 && workingFluidOutput.getSpecificEnthalpy() != 0 && specificWork != 0){
			workingFluidInput.setSpecificEnthalpy(workingFluidOutput.getSpecificEnthalpy() + specificWork);
			return true;
		}
		return false;
	}
	
	public boolean calcEntropy(){
		if(workingFluidInput.getSpecificEntropy() != 0 && workingFluidInput.getSpecificEnthalpy() != 0 && specificWork != 0 && isentropicEfficiency != 0 && workingFluidOutput.getSpecificEntropy() == 0 && workingFluidOutput.getPressure() == 0){
			try{
				Fluid isentropicTest = workingFluidInput.getClass().newInstance();
				isentropicTest.setSpecificEntropy(workingFluidInput.getSpecificEntropy()).setSpecificEnthalpy(workingFluidInput.getSpecificEnthalpy() - specificWork/isentropicEfficiency);
				workingFluidOutput.setPressure(isentropicTest.getPressure());
			} catch(InstantiationException | IllegalAccessException e){return false;}
			return true;
		} else if(workingFluidOutput.getSpecificEntropy() != 0 && workingFluidOutput.getSpecificEnthalpy() != 0 && specificWork != 0 && isentropicEfficiency != 0 && workingFluidInput.getSpecificEntropy() == 0 && workingFluidInput.getPressure() == 0){
			try{
				Fluid isentropicTest = workingFluidInput.getClass().newInstance();
				isentropicTest.setSpecificEntropy(workingFluidOutput.getSpecificEntropy()).setSpecificEnthalpy(workingFluidOutput.getSpecificEnthalpy() + specificWork/isentropicEfficiency);
				workingFluidInput.setPressure(isentropicTest.getPressure());
			} catch(InstantiationException | IllegalAccessException e){return false;}
			return true;
		}
		return false;
	}
	
	public WorkingFluidObject setWork(double work){
		if(massFlow == 0){calcMassFlow();}
		if(massFlow != 0){specificWork = work / massFlow;}
		return this;
	}
	
	@Override
	public WorkingFluidObject setWorkingFluidInput(Fluid fluid) {
		this.workingFluidInput = fluid;
		return this;
	}

	@Override
	public WorkingFluidObject setWorkingFluidOutput(Fluid fluid) {
		this.workingFluidOutput = fluid;
		return this;
	}
	
	public WorkingFluidObject setIsentropicEfficiency(double isentropicEfficiency){
		this.isentropicEfficiency = isentropicEfficiency;
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
	
	public double getIsentropicEfficiency(){
		return isentropicEfficiency;
	}
	
	public double getWork(){
		return specificWork*massFlow;
	}

	@Override
	public boolean isSolved() {
		return solved;
	}
	private Fluid workingFluidInput;
	private Fluid workingFluidOutput;
	private boolean solved;
	private double specificWork;
	private double isentropicEfficiency;
	private double massFlow;
}
