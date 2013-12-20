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

import powerplant.fluid.Fluid;
import powerplant.fluid.FluidH2O;
import net.sf.jsteam.*;
import powerplant.object.*;
import powerplant.object.ghe.*;
import powerplant.object.lhe.*;

/**
 *
 * @author DoorKip
 */
public class PowerPlant {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		//testSteam();
		//double res = testTurbine()*4 + testTurbine2()*4 - testPump(); System.out.println(res);
		//testCycle();
		//System.out.println(EngineeringMath.haalandEquation(320000, 0.00015/0.315));
		//TestProde();
		testPipe();
	}
	
	static void testSteam(){
		Fluid water2 = new FluidH2O().setPressure(500000).setTemperature(217+273.15);
		Fluid water4 = new FluidH2O().setPressure(500000).setTemperature(550+273.15);
		double enth = water4.getSpecificEnthalpy() - water2.getSpecificEnthalpy();
		Fluid water3 = new FluidH2O().setPressure(500000-121630).setSpecificEnthalpy(water2.getSpecificEnthalpy() + enth);
		water2.printProperties();
		water3.printProperties();
	}
	
	static void testPipe(){
		double fine = 4.572E-5; double medium = 9.7536E-4; double coarse = 1.95072E-3;
		double length = 115;
		//Pipe pipe = new Pipe(new FluidH2O().setTemperature(700+237.15).setPressure(12000000).setMassFlow(96.98));//medium,16,14.324 -> 36795
		//Pipe pipe = new Pipe(new FluidH2O().setTemperature(151.9+237.15).setPressure(12000000).setMassFlow(96.98));//coarse,10,9.564 -> 11724
		//Pipe pipe = new Pipe(new FluidH2O().setTemperature(217+237.15).setPressure(500000).setMassFlow(77.584));//fine,24,22.626 -> 12916
		Pipe pipe = new Pipe(new FluidH2O().setTemperature(550+237.15).setPressure(500000-121630).setMassFlow(77.584));//fine,24,22.626 -> 30876
		pipe.setLength(Units.ft_meter(length));
		pipe.setInteriorDiameter(Units.inch_meter(22.626));
		pipe.setSurfaceRoughness(fine);
		pipe.solve();
		//pipe.getWorkingFluidInput().printProperties();
		System.out.println(pipe.getPressureLoss());
	}
	
	static void testNusselt(){
		double frictionFactor = 0.00204;
		double reynoldsNumber = 9560243;
		Fluid testFluid = new FluidH2O().setTemperature(EngineeringMath.averageAND(264+273.15, 400+273.15)).setQuality(1);
		double nusseltNumber = ((frictionFactor/2)*reynoldsNumber*testFluid.getPrandtl())/(1.07+12.7*Math.sqrt(frictionFactor/2)*(Math.pow(testFluid.getPrandtl(),(2.0/3.0))-1));
		System.out.println(testFluid.getPrandtl());
		System.out.println(nusseltNumber);
	}
		
	static double testTurbine(){
		FluidH2O fluid = new FluidH2O();
		Turbine turbine = new Turbine(fluid);
		fluid.setPressure(12000000-109370-35429).setTemperature(699.8+273.15).setMassFlow(96.68/4);
		turbine.getWorkingFluidOutput().setPressure(500000);
		turbine.setThermalEfficiency(0.97).solve();
		turbine.getWorkingFluidOutput().printProperties();
		double result = turbine.getWork()*0.91;
		System.out.println(result);
		return result;
	}
		
	static double testTurbine2(){
		/*
		FluidH2O fluid = new FluidH2O();
		Turbine turbine = new Turbine(fluid);
		fluid.setPressure(500000).setTemperature(550+273.15).setMassFlow(77.344/4);
		turbine.getWorkingFluidOutput().setPressure(8000);
		turbine.setThermalEfficiency(0.97).solve();
		turbine.getWorkingFluidOutput().printProperties();
		System.out.println(turbine.getWork()*0.91);
		*/
		FluidH2O fluid2 = new FluidH2O();
		Turbine turbine2 = new Turbine(fluid2);
		fluid2.setPressure(500000-121630).setTemperature(548.85+273.15).setMassFlow(77.344/4);
		turbine2.getWorkingFluidOutput().setPressure(8000);
		turbine2.setThermalEfficiency(0.97).solve();
		turbine2.getWorkingFluidOutput().printProperties();
		double result = turbine2.getWork()*0.91;
		System.out.println(result);
		//System.out.println(turbine.getWork()*0.91 - turbine2.getWork()*0.91);
		return result;
	}
	
	static double testPump(){
		Pump pump = new Pump(new FluidH2O().setMassFlow(96.68).setPressure(8000).setQuality(0));
		pump.getWorkingFluidOutput().setPressure(12000000);
		pump.setIsentropicEfficiency(0.87);
		pump.solve();
		double result = pump.getWork();
		System.out.println(result);
		return result;
	}
	
	static void testCycle(){
		Turbine turbine2 = new Turbine(new FluidH2O().setMassFlow(25).setTemperature(700).setPressure(500000));
		powerplant.object.lhe.Condenser condenser = new powerplant.object.lhe.Condenser(turbine2.getWorkingFluidOutput());
		Pump pump = new Pump(condenser.getWorkingFluidOutput());
		Pipe pipe1 = new Pipe(condenser.getWorkingFluidOutput());
		Boiler boiler = new Boiler(pipe1.getWorkingFluidOutput());
		SuperHeater superHeater = new SuperHeater(boiler.getWorkingFluidOutput());
		Pipe pipe2 = new Pipe(superHeater.getWorkingFluidOutput());
		Turbine turbine1 = new Turbine(pipe2.getWorkingFluidOutput());
		
		
		turbine2.setThermalEfficiency(0.91).solve();
		condenser.getWorkingFluidInput().setPressure(8000);
		condenser.getExchangeFluidIn().setMassFlow(1).setTemperature(298.15).setPressure(101000).solve();
		condenser.getExchangeFluidOut().setTemperature(308.15);
		pump.getWorkingFluidOutput().setPressure(12000000);
		pump.setIsentropicEfficiency(0.85);
		pipe1.setLength(35.052);
		pipe1.setInteriorDiameter(0.3);
		pipe1.setSurfaceRoughness(0.000975);
		turbine2.solve();
		condenser.solve();
		pump.solve();
		pipe1.solve();
		turbine2.getWorkingFluidOutput().printProperties();
		condenser.getWorkingFluidOutput().printProperties();
		pump.getWorkingFluidOutput().printProperties();
		System.out.println(turbine2.getWork() - pump.getWork());
		System.out.println(pipe1.getPressureLoss());
	}
}

/* Things that will require guesses
Condenser
- Number of passes
- Transverse number of tubes
-
*/
