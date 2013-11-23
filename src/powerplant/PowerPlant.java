/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package powerplant;

import net.sf.jsteam.SteamState;
import powerplant.fluid.Fluid;
import powerplant.fluid.FluidH2O;
import powerplant.fluid.Water;
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
		//new Water().setSpecificEnthalpy(1500).setSpecificEntropy(4.18).;
		//System.out.println(new Water().setPressure(0.101).setSpecificEntropy(4).getTemperature());
		testTurbine();
	}
	
	static void testSteam(){
		Fluid water = new FluidH2O().setTemperature(373.15).setQuality(0.75);
		System.out.println(water.getSpecificEntropy());
	}
	
	static void testTurbine(){
		Turbine turbine = new Turbine(new FluidH2O().setPressure(101000).setTemperature(400).setMassFlow(100));
		turbine.setThermalEfficiency(0.91).setWork(22400000).solve();
		turbine.getWorkingFluidOutput().solve();
		turbine.getWorkingFluidOutput().printProperties();
	}
	
	static void testCycle(){
		Turbine turbine = new Turbine(Water.class);
		turbine.getWorkingFluidInput().setTemperature(400).setPressure(6000000);
		powerplant.lhe.Condenser condenser = new powerplant.lhe.Condenser(turbine.getWorkingFluidOutput());
		condenser.getExchangeFluidIn().setTemperature(298.15).setPressure(101000);
	}
}
