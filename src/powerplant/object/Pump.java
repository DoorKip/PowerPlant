

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
		if(workingFluidInput.getPressure() != 0 && workingFluidOutput.getPressure() != 0 && specificWork ==0){
			specificWork = (workingFluidInput.getPressure() - workingFluidOutput.getPressure())/workingFluidInput.nominalLiquidDensity;
			return true;
		} else if(workingFluidInput.getPressure() != 0 && workingFluidOutput.getPressure() == 0 && specificWork != 0){
			workingFluidOutput.setPressure(specificWork * workingFluidInput.nominalLiquidDensity - workingFluidInput.getPressure());
			return true;
		} else if(workingFluidInput.getPressure() == 0 && workingFluidOutput.getPressure() != 0 && specificWork != 0){
			workingFluidInput.setPressure(specificWork * workingFluidInput.nominalLiquidDensity + workingFluidOutput.getPressure());
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
	private Fluid workingFluidInput;
	private Fluid workingFluidOutput;
	private boolean solved;
	private double specificWork;
	private double isentropicEfficiency;
	private double massFlow;
}
