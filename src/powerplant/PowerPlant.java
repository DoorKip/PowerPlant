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

package powerplant;

import powerplant.object.Turbine;
import powerplant.object.Pump;
import powerplant.fluid.Fluid;
import powerplant.fluid.FluidH2O;
import net.sf.jsteam.*;

/**
 *
 * @author DoorKip
 */
public class PowerPlant {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		testSteam();
		//testTurbine();
		//testCycle();
	}
	
	static void testSteam(){
		Fluid water1 = new FluidH2O().setPressure(8000).setTemperature(region4.Tsat_p(8000));
		Fluid water2 = new FluidH2O().setPressure(12000000).setSpecificEntropy(water1.getSpecificEntropy());
		water1.printProperties();
		water2.printProperties();
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
		powerplant.object.lhe.Condenser condenser = new powerplant.object.lhe.Condenser(turbine.getWorkingFluidOutput());
		Pump pump = new Pump(condenser.getWorkingFluidOutput());
		turbine.setThermalEfficiency(0.91).solve();
		condenser.getWorkingFluidInput().setPressure(8000);
		condenser.getExchangeFluidIn().setMassFlow(1).setTemperature(298.15).setPressure(101000).solve();
		pump.getWorkingFluidOutput().setPressure(12000000);
		turbine.solve();
		condenser.solve();
		pump.solve();
		turbine.getWorkingFluidOutput().printProperties();
		condenser.getWorkingFluidOutput().printProperties();
		pump.getWorkingFluidOutput().printProperties();
	}
}
