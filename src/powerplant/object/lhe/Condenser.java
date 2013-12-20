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

package powerplant.object.lhe;

import powerplant.object.WorkingFluidObject;
import powerplant.fluid.Fluid;

/**
 * Woo, more shit that does stuff.
 * Isobaric heat exchanger. Input pressure = Output pressure.
 * @author DoorKip
 */
public class Condenser extends LiquidHeatExchanger{
	private enum TubeLayout{
		SQUARE(1.0,90),
		STAGERED(1.0,45);
		TubeLayout(double cl, double angle){
			this.cl = cl;
			this.angle = angle;
		}
		double CL(){return cl;}
		private double cl;
		private double angle;
	}
	
	public Condenser(Fluid inputFluid){
		workingFluidInput = inputFluid;
		try{
			workingFluidOutput = inputFluid.getClass().newInstance();
			exchangeFluidInput = inputFluid.getClass().newInstance();
			exchangeFluidOutput = inputFluid.getClass().newInstance();
		}
		catch(InstantiationException | IllegalAccessException e){}
		//For now, assume square layout
		tubeLayout = TubeLayout.SQUARE;
	}
	
	public void solve(){
		if(massFlow == 0){calcMassFlow();}
		calcPressure();
		if(calcEnthalpy()){solved = true;}
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
			workingFluidOutput.setPressure(workingFluidInput.getPressure());
		} else if( workingFluidInput.getPressure() == 0 && workingFluidOutput.getPressure() != 0){
			workingFluidInput.setPressure(workingFluidOutput.getPressure());
		}
		if(exchangeFluidInput.getPressure() != 0 && exchangeFluidOutput.getPressure() == 0){
			exchangeFluidOutput.setPressure(exchangeFluidInput.getPressure());
		} else if(exchangeFluidInput.getPressure() == 0 && exchangeFluidOutput.getPressure() != 0){
			exchangeFluidInput.setPressure(exchangeFluidOutput.getPressure());
		}
	}
	
	private boolean calcEnthalpy(){
		double hEFIn = exchangeFluidInput.getSpecificEnthalpy();
		double hEFOut = exchangeFluidOutput.getSpecificEnthalpy();
		double hWFIn = workingFluidInput.getSpecificEnthalpy();
		double hWFOut = workingFluidOutput.getSpecificEnthalpy();
		if (hEFIn != 0 && hEFOut == 0 && hWFIn != 0 && hWFOut == 0 && workingFluidInput.getRegion() != 0){
			try{
				Fluid enthalpyTest = workingFluidInput.getClass().newInstance().setQuality(0).setPressure(workingFluidInput.getPressure());
				workingFluidOutput.setSpecificEnthalpy(enthalpyTest.getSpecificEnthalpy()).setPressure(workingFluidInput.getPressure());
				exchangeFluidOutput.setSpecificEnthalpy(hEFIn + (hWFIn - enthalpyTest.getSpecificEnthalpy()));
				return true;
			} catch (IllegalAccessException | InstantiationException e){throw new Error("YOU FUCKING BROKE IT, DOG.");}
		} else {System.out.println("SHIT BROKE"); return false;}
	}
	
	private boolean designCondenser(){
		if(numberPasses == 1){double CTP = 0.93;}
		else if(numberPasses == 2){double CTP = 0.9;}
		else{return false;}
		if(exchangeFluidInput.getTemperature() != 0 && exchangeFluidOutput.getTemperature() != 0 && workingFluidInput.getRegion() != 0){
			
		}
		return false;
	}
	@Override
	public WorkingFluidObject setExchageFluidIn(Fluid fluid) {
		exchangeFluidInput = fluid;
		return this;
	}

	@Override
	public WorkingFluidObject setExchageFluidOut(Fluid fluid) {
		exchangeFluidOutput = fluid;
		return this;
	}

	@Override
	public Fluid getExchangeFluidIn() {
		return exchangeFluidInput;
	}

	@Override
	public Fluid getExchangeFluidOut() {
		return exchangeFluidOutput;
	}

	@Override
	public WorkingFluidObject setWorkingFluidInput(Fluid fluid) {
		workingFluidInput = fluid;
		return this;
	}

	@Override
	public WorkingFluidObject setWorkingFluidOutput(Fluid fluid) {
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
	public boolean isSolved(){
		return solved;
	}
	
	private TubeLayout tubeLayout;
	private Fluid workingFluidInput;
	private Fluid workingFluidOutput;
	private Fluid exchangeFluidInput;
	private Fluid exchangeFluidOutput;
	private double massFlow;
	private boolean solved = false;
	private int transverseTubes;
	private int numberPasses;
	private double innerFoulingResistance;
	private double outerFoulingResistance;
}
