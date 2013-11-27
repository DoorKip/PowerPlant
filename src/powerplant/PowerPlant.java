/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package powerplant;

import powerplant.fluid.Fluid;
import powerplant.fluid.FluidH2O;
//import JProde.Prode;
/**
 *
 * @author DoorKip
 */
public class PowerPlant {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		//new FluidH2O().setQuality(0).setTemperature(373.15).printProperties();
		//testTurbine();
		testCycle();
	}
	
	static void testSteam(){
		Fluid water = new FluidH2O().setTemperature(373.15).setQuality(0.75);
		System.out.println(water.getSpecificEntropy());
	}
	
	static void testTurbine(){
		FluidH2O fluid = new FluidH2O();
		Turbine turbine = new Turbine(fluid);
		fluid.setPressure(12000000).setTemperature(650).setMassFlow(25);
		turbine.getWorkingFluidOutput().setPressure(8000);
		turbine.setThermalEfficiency(0.91).solve();
		System.out.println(turbine.getWorkingFluidOutput().getPrandtl());
	}
	
	static void testCycle(){
		Turbine turbine = new Turbine(new FluidH2O().setMassFlow(25).setTemperature(650).setPressure(12000000));
		powerplant.lhe.Condenser condenser = new powerplant.lhe.Condenser(turbine.getWorkingFluidOutput());
		turbine.setThermalEfficiency(0.91).solve();
		condenser.getWorkingFluidInput().setPressure(8000);
		condenser.getExchangeFluidIn().setMassFlow(1).setTemperature(298.15).setPressure(101000).solve();
		turbine.solve();
		condenser.solve();
		condenser.getWorkingFluidOutput().printProperties();
	}
}
