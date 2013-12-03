

package powerplant.object;

import powerplant.fluid.Fluid;

/**
 *
 * @author DoorKip
 */
public class Turbine extends WorkingFluidObject {
	public Turbine(Class fluidSubclass){
		try{workingFluidOut = (Fluid) fluidSubclass.newInstance();}
		catch(InstantiationException | IllegalAccessException e){System.out.println("SHIT");}
	}
	public Turbine(Fluid inputFluid){
		workingFluidIn = inputFluid;
		try{workingFluidOut = inputFluid.getClass().newInstance();}
		catch(InstantiationException | IllegalAccessException e){System.out.println("FUCK");}
	}
	public void solve(){
		if(massFlow == 0){calcMassFlow();}
		if(workingFluidIn.getRegion() == 2){if(calcWorkEnthalpy()){solved = true;}}
	}
	private void calcMassFlow(){
		if(workingFluidIn.getMassFlow()!=0 && workingFluidOut.getMassFlow()==0){
			massFlow = workingFluidIn.getMassFlow();
			workingFluidOut.setMassFlow(massFlow);
		} else if(workingFluidIn.getMassFlow()==0 && workingFluidOut.getMassFlow()!=0){
			massFlow = workingFluidOut.getMassFlow();
			workingFluidIn.setMassFlow(massFlow);
		}
	}
	private boolean calcWorkEnthalpy(){
		double enthalpyIn = workingFluidIn.getSpecificEnthalpy();
		double enthalpyOut = workingFluidOut.getSpecificEnthalpy();
		if(specificWork != 0 && isentropicEfficiency != 0 && enthalpyIn != 0 && enthalpyOut == 0){
			workingFluidOut.setSpecificEnthalpy( enthalpyIn - specificWork );
			try{
				Fluid isentropicExitState = workingFluidIn.getClass().newInstance().setSpecificEnthalpy(enthalpyIn - specificWork/isentropicEfficiency).setSpecificEntropy(workingFluidIn.getSpecificEntropy());
				workingFluidOut.setPressure(isentropicExitState.getPressure());
			} catch(InstantiationException | IllegalAccessException e){}
			return true;
		} else if(specificWork != 0 && isentropicEfficiency != 0 && enthalpyIn == 0 && enthalpyOut != 0){
			workingFluidIn.setSpecificEnthalpy( enthalpyOut + specificWork );
			return true;
		} else if(specificWork != 0 && isentropicEfficiency == 0 && enthalpyIn != 0 && enthalpyOut != 0){
			return false;
		} else if(specificWork == 0 && isentropicEfficiency != 0 && enthalpyIn != 0 && enthalpyOut != 0){
			specificWork = enthalpyIn - enthalpyOut;
			return true;
		} else if(specificWork == 0 && isentropicEfficiency != 0 && enthalpyIn != 0 && enthalpyOut == 0 && workingFluidOut.getPressure() != 0){
			try{
				Fluid isentropicExitState = workingFluidIn.getClass().newInstance().setPressure(workingFluidOut.getPressure()).setSpecificEntropy(workingFluidIn.getSpecificEntropy());
				specificWork = (enthalpyIn - isentropicExitState.getSpecificEnthalpy())*isentropicEfficiency;
				workingFluidOut.setSpecificEnthalpy(enthalpyIn - specificWork);
			} catch(IllegalAccessException | InstantiationException e){}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public WorkingFluidObject setWorkingFluidInput(Fluid fluid) {
		this.workingFluidIn = fluid;
		return this;
	}

	@Override
	public WorkingFluidObject setWorkingFluidOutput(Fluid fluid) {
		this.workingFluidOut = fluid;
		return this;
	}
	
	public Turbine setWork(double work){
		if(massFlow == 0){
			calcMassFlow();
			if(massFlow != 0){this.specificWork = work/massFlow;}
		}
		return this;
	}
	
	public Turbine setThermalEfficiency(double isentropicEfficiency){
		this.isentropicEfficiency = isentropicEfficiency;
		return this;
	}

	@Override
	public Fluid getWorkingFluidInput() {return workingFluidIn;}

	@Override
	public Fluid getWorkingFluidOutput() {return workingFluidOut;}
	
	public double getWork(){return specificWork*massFlow;}
	
	public double getIsentropicEfficiency(){return isentropicEfficiency;}
	
	@Override
	public boolean isSolved(){
		return solved;
	}
	
	private Fluid workingFluidIn;
	private Fluid workingFluidOut;
	private double isentropicEfficiency;
	private double specificWork;
	private double massFlow;
	private boolean solved = false;
}
