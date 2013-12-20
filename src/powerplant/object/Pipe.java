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

import powerplant.EngineeringMath;
import powerplant.fluid.Fluid;

/**
 *
 * @author DoorKip
 */
public class Pipe extends WorkingFluidObject {

	public Pipe(Fluid inputFluid){
		workingFluidInput = inputFluid;
		try{workingFluidOutput = inputFluid.getClass().newInstance();}
		catch(InstantiationException | IllegalAccessException e){}
	}
	
	@Override
	public void solve() {
		calcMassFlow();
		calcPressure();
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
	
	private void calcPressure(){
		if(workingFluidInput.getPressure() != 0 && workingFluidOutput.getPressure() == 0){
			if(calcPipeLoss()){
				workingFluidOutput.setPressure(workingFluidInput.getPressure() - pressureLoss);
				solved = true;
			}
		}
	}
	
	private boolean calcPipeLoss(){
		if(length != 0 && interiorDiameter != 0 && surfaceRoughness != 0 && pressureLoss == 0){
			if(workingFluidInput.getPressure() != 0 && workingFluidInput.getDensity() != 0 && workingFluidOutput.getPressure() == 0){
				double area = Math.PI * Math.pow(interiorDiameter/2, 2);
				double velocity = massFlow / ( area * workingFluidInput.getDensity() );
				double reynoldsNumber = workingFluidInput.getDensity() * velocity * interiorDiameter / workingFluidInput.getDynamicViscosity();
				double reynoldsNumber2 = (4*massFlow) / (Math.PI * workingFluidInput.getDynamicViscosity() * interiorDiameter);
				System.out.println(reynoldsNumber2);
				System.out.println(workingFluidInput.getDynamicViscosity());
				double frictionFactor = 0;
				if( reynoldsNumber < 2300 ){
					System.out.println("Laminar");
					frictionFactor = 64/reynoldsNumber2;
				} else if( reynoldsNumber > 4000 ){
					frictionFactor = EngineeringMath.haalandEquation(reynoldsNumber, surfaceRoughness/interiorDiameter);
				} //TODO Implement an else case here to handle transient flow.
				pressureLoss = frictionFactor*(length/interiorDiameter)*(workingFluidInput.getDensity()*Math.pow(velocity, 2)/2);
				return true;
			}
		}
		return false;
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
	
	public WorkingFluidObject setLength(double length){
		this.length = length;
		return this;
	}
	
	public WorkingFluidObject setInteriorDiameter(double interiorDiameter){
		this.interiorDiameter = interiorDiameter;
		return this;
	}
	
	public WorkingFluidObject setSurfaceRoughness(double surfaceRoughness){
		this.surfaceRoughness = surfaceRoughness;
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
	
	public double getLength(){
		return length;
	}
	
	public double getInteriorDiameter(){
		return interiorDiameter;
	}
	
	public double getSurfaceRoughness(){
		return surfaceRoughness;
	}
	
	public double getPressureLoss(){
		return pressureLoss;
	}

	@Override
	public boolean isSolved() {
		return solved;
	}
	
	private boolean solved = false;
	private Fluid workingFluidInput;
	private Fluid workingFluidOutput;
	private double length;
	private double interiorDiameter;
	private double surfaceRoughness;
	private double pressureLoss;
	private double massFlow;

}
