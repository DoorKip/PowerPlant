

package powerplant.object.lhe;

import powerplant.object.WorkingFluidObject;
import powerplant.fluid.Fluid;

/**
 * Woo, more shit that does stuff.
 * Isobaric heat exchanger. Input pressure = Output pressure.
 * @author DoorKip
 */
public class Condenser extends LiquidHeatExchanger{
	
	public Condenser(Fluid inputFluid){
		workingFluidInput = inputFluid;
		try{
			workingFluidOutput = inputFluid.getClass().newInstance();
			exchangeFluidInput = inputFluid.getClass().newInstance();
			exchangeFluidOutput = inputFluid.getClass().newInstance();
		}
		catch(InstantiationException | IllegalAccessException e){}
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
		if (hEFIn != 0 && hEFOut == 0 && hWFIn != 0 && hWFOut == 0 && workingFluidInput.getRegion() == 4){
			try{
				Fluid enthalpyTest = workingFluidInput.getClass().newInstance().setQuality(0).setTemperature(workingFluidInput.getTemperature());
				workingFluidOutput.setSpecificEnthalpy(enthalpyTest.getSpecificEnthalpy()).setPressure(workingFluidInput.getPressure());
				exchangeFluidOutput.setSpecificEnthalpy(hEFIn + (hWFIn - enthalpyTest.getSpecificEnthalpy()));
				return true;
			} catch (IllegalAccessException | InstantiationException e){throw new Error("YOU FUCKING BROKE IT, DOG.");}
		} else {System.out.println("SHIT BROKE"); return false;}
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
	
	private Fluid workingFluidInput;
	private Fluid workingFluidOutput;
	private Fluid exchangeFluidInput;
	private Fluid exchangeFluidOutput;
	private double massFlow;
	private boolean solved = false;
}
