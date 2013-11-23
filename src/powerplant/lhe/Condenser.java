/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package powerplant.lhe;

import powerplant.WorkingFluidObject;
import powerplant.fluid.Fluid;

/**
 * Woo, more shit that does stuff.
 * Isobaric heat exchanger. Input pressure = Output pressure.
 * @author DoorKip
 */
public class Condenser extends LiquidHeatExchanger{
	
	public Condenser(Fluid inputFluid){
		workingFluidInput = inputFluid;
		try{workingFluidOutput = inputFluid.getClass().newInstance();}
		catch(InstantiationException | IllegalAccessException e){}
	}
	
	public void solve(){
		calcMassFlow();
		calcPressure();
	}
	
	private void calcMassFlow(){
		if(workingFluidInput.getMassFlow() != 0 && workingFluidOutput.getMassFlow() == 0){
			massFlow = workingFluidOutput.getMassFlow();
			workingFluidInput.setMassFlow(massFlow);
		} else if( workingFluidInput.getMassFlow() == 0 && workingFluidOutput.getMassFlow() != 0){
			massFlow = workingFluidInput.getMassFlow();
			workingFluidOutput.setMassFlow(massFlow);
		}
	}
	
	private void calcPressure(){
		if(workingFluidInput.getPressure() != 0 && workingFluidOutput.getPressure() == 0){
			workingFluidInput.setPressure(workingFluidOutput.getPressure());
		} else if( workingFluidInput.getPressure() == 0 && workingFluidOutput.getPressure() != 0){
			workingFluidOutput.setPressure(workingFluidInput.getPressure());
		}
	}
	
	private void calcEnthalpy(){
		double hEFIn = exchangeFluidInput.getSpecificEnthalpy();
		double hEFOut = exchangeFluidOutput.getSpecificEnthalpy();
		double hWFIn = workingFluidInput.getSpecificEnthalpy();
		double hWFOut = workingFluidOutput.getSpecificEnthalpy();
		if (hEFIn != 0 && hEFOut == 0 && hWFIn != 0 && hWFOut == 0){
			if(workingFluidInput.getRegion() == 4){
				try{
					workingFluidInput.setSpecificEnthalpy(workingFluidInput.getClass().newInstance().setQuality(0).setPressure(workingFluidOutput.getPressure()).getSpecificEnthalpy());
				} catch (IllegalAccessException | InstantiationException e){throw new Error("YOU FUCKING BROKE IT, DOG.");}
			}else{throw new Error("I WARNED YOU ABOUT THOSE REGIONS, BRO");}
		}
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
	
	private Fluid workingFluidInput;
	private Fluid workingFluidOutput;
	private Fluid exchangeFluidInput;
	private Fluid exchangeFluidOutput;
	private double massFlow;
}
