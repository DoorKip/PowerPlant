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

import powerplant.EngineeringMath;
import powerplant.object.WorkingFluidObject;
import powerplant.fluid.Fluid;
import powerplant.gas.FlueGas;

/**
 *
 * @author DoorKip
 */
public class SuperHeater extends GasHeatExchanger{
	
	public SuperHeater(Fluid inputFluid){
		workingFluidInput = inputFluid;
		try{workingFluidOutput = inputFluid.getClass().newInstance();}
		catch(InstantiationException | IllegalAccessException e){}
	}

	@Override
	public void solve() {
		calcMassFlow();
	}
	
	private void calcMassFlow(){
		if(workingFluidInput.getMassFlow() != 0 && workingFluidOutput.getMassFlow() == 0){
			massFlow = workingFluidInput.getMassFlow();
			workingFluidOutput.setMassFlow(massFlow);
		} else if( workingFluidInput.getMassFlow() == 0 && workingFluidOutput.getMassFlow() != 0){
			massFlow = workingFluidOutput.getMassFlow();
			workingFluidInput.setMassFlow(massFlow);
		}
	}
	
	private boolean sizeSuperHeater(){
		if(minimumPitch != 0 && width != 0 && transverseTubes == 0){
			transverseTubes = (int) (width/minimumPitch);
			actualTransversePitch = width/(transverseTubes);
		}else{return false;}
		double massFlowPerBank = massFlow/transverseTubes;
		double reynoldsNumber = (4*massFlowPerBank) / (Math.PI * workingFluidInput.getDynamicViscosity() * innerDiameter);
		double frictionFactor = 0;
		if( reynoldsNumber < 2300 ){
			frictionFactor = 64/reynoldsNumber;
		} else if( reynoldsNumber > 4000 ){
			frictionFactor = Math.pow(1.58 * Math.log(reynoldsNumber) - 3.28, -2); //Smooth pipe, turbulent flow
		}
		double nusseltNumber;
		try{
			Fluid testFluid = workingFluidInput.getClass().newInstance();
			testFluid.setTemperature(EngineeringMath.averageAND(workingFluidInput.getTemperature(), workingFluidOutput.getTemperature())).setPressure(workingFluidInput.getPressure());
			nusseltNumber = ((frictionFactor/2)*reynoldsNumber*testFluid.getPrandtl())/(1.07+12.7*Math.sqrt(frictionFactor/2)*(Math.pow(testFluid.getPrandtl(),(2.0/3.0))-1));
		}
		catch(InstantiationException | IllegalAccessException e){return false;}
		
		return false;
	}
	
	@Override
	public void setGasInput(FlueGas flueIn) {
		//TODO Implement setGasInput in powerplant.ghe.SuperHeater
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public FlueGas getGasInput() {
		//TODO Implement getGasInput in powerplant.ghe.SuperHeater
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public FlueGas getGasOutput() {
		//TODO Implement getGasOutput in powerplant.ghe.SuperHeater
		throw new UnsupportedOperationException("Not supported yet.");
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
	
	public WorkingFluidObject setWidth(double width){this.width = width; return this;}
	public WorkingFluidObject setHeight(double height){this.height = height; return this;}
	public WorkingFluidObject setDepth(double depth){this.depth = depth; return this;}
	public WorkingFluidObject setMinimumPitch(double minimumPitch){this.minimumPitch = minimumPitch; return this;}

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
	private double massFlow;
	private double width;
	private double height;
	private double depth;
	private double minimumPitch;
	private double actualTransversePitch;
	private double outerDiameter;
	private double innerDiameter;
	private int transverseTubes;
	private int longitudinalTubes;
}
