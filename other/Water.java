/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package powerplant.fluid;

import com.hummeling.if97.IF97;
import com.hummeling.if97.OutOfRangeException;
import net.sf.jsteam.SteamState;
import powerplant.InsufficientDataException;

/**
 *
 * @author DoorKip
 */
public class Water extends Fluid{
	
	@Override
	public Fluid initializeValues(){
		massFlow = 0;
		prandtl = 0;
		density = 0;
		pressure = 0;
		specificEntropy = 0;
		specificEnthalpy = 0;
		specificHeatCapacity = 0;
		temperature = 0;
		return this;
	}
	
	@Override
	public void solve(){
		boolean fail;
		boolean changed;
		int loops = 0;
		do {
			fail = false;
			changed = false;
			if (prandtl == 0){try{prandtl = calcPrandtl(); changed = true;}catch(InsufficientDataException e){fail=true;}}
			if (specificVolume == 0){try{specificVolume = calcSpecificVolume(); changed = true;}catch(InsufficientDataException e){fail=true;}}
			if (density == 0){try{density = calcDensity(); changed = true;}catch(InsufficientDataException e){fail=true;}}
			if (pressure == 0){try{pressure = calcPressure(); changed = true;}catch(InsufficientDataException e){fail=true;}}
			if (specificEntropy == 0){try{specificEntropy = calcSpecificEntropy(); changed = true;}catch(InsufficientDataException e){fail=true;}}
			if (specificEnthalpy == 0){try{specificEnthalpy = calcSpecificEnthalpy(); changed = true;}catch(InsufficientDataException e){fail=true;}}
			if (specificInternalEnergy == 0){try{specificInternalEnergy = calcSpecificInternalEnergy(); changed = true;}catch(InsufficientDataException e){fail = true;}}
			if (specificHeatCapacity == 0){try{specificHeatCapacity = calcSpecificHeatCapacity(); changed = true;}catch(InsufficientDataException e){fail = true;}}
			if (temperature == 0){try{temperature = calcTemperature(); changed = true;}catch(InsufficientDataException e){fail=true;}}
			loops++;
		} while (fail && changed);
		solved = true;
		java.lang.System.out.println(loops);
		printProperties();
	}
	
	private double calcPrandtl() throws InsufficientDataException {
		if (specificEnthalpy != 0 && specificEntropy != 0){
			try{
				return tables.PrandtlHS(specificEnthalpy, specificEntropy);
			} catch(OutOfRangeException e){}
		}
		if (pressure != 0 && specificEnthalpy !=0){
			try{
				return tables.PrandtlPH(pressure, specificEnthalpy);
			} catch(OutOfRangeException e){}
		}
		if (pressure != 0 && temperature != 0){
			try{
				return tables.PrandtlPT(pressure, temperature);
			} catch(OutOfRangeException e){}
		}
		throw new InsufficientDataException();
	}
	private double calcSpecificVolume() throws InsufficientDataException {
		if (density != 0){
			return 1/density;
		}
		if (specificEnthalpy != 0 && specificInternalEnergy != 0 && pressure != 0){
			return (specificEnthalpy - specificInternalEnergy) / pressure;
		}
		if (pressure != 0 && specificEnthalpy != 0){
			try{
				return tables.specificVolumePH(pressure, specificEnthalpy);
			} catch (OutOfRangeException e){}
		}
		if (pressure != 0 && temperature != 0){
			try{
				return tables.specificVolumePT(pressure, temperature);
			} catch (OutOfRangeException e){}
		}
		throw new InsufficientDataException();
	}
	private double calcDensity() throws InsufficientDataException{
		if (specificVolume != 0){
			return 1/specificVolume;
		}
		throw new InsufficientDataException();
	}
	private double calcPressure() throws InsufficientDataException {
		if (specificEnthalpy != 0 && specificInternalEnergy != 0 && specificVolume != 0){
			return (specificEnthalpy - specificInternalEnergy) / specificVolume;
		}
		if (specificEnthalpy != 0 && specificEntropy != 0){
			try{
				return tables.pressureHS(specificEnthalpy , specificEntropy);
			} catch (OutOfRangeException e){}
		}
		throw new InsufficientDataException();
	}
	private double calcSpecificEntropy() throws InsufficientDataException{
		if (pressure != 0 && temperature != 0){
			try{
				return tables.specificEntropyPT(pressure, temperature);
			} catch(OutOfRangeException e) {}
		}
		if (pressure != 0 && density != 0){
			try{
				return tables.specificEntropyPH(pressure, density);
			} catch(OutOfRangeException e) {}
		}
		throw new InsufficientDataException();
	}
	private double calcSpecificEnthalpy() throws InsufficientDataException{
		if (pressure != 0 && specificVolume != 0 && specificInternalEnergy != 0){
			return specificInternalEnergy + (pressure * specificVolume);
		}
		if (pressure != 0 && temperature != 0) {
			try{
				return tables.specificEnthalpyPT(pressure, temperature);
			} catch(OutOfRangeException e){}
		}
		throw new InsufficientDataException();
	}
	private double calcSpecificInternalEnergy() throws InsufficientDataException{
		if(pressure != 0 && temperature != 0){
			try{
				return tables.specificInternalEnergyPT(pressure, temperature);
			} catch(OutOfRangeException e){}
		}
		if(pressure != 0 && specificEnthalpy != 0){
			try{
				return tables.specificInternalEnergyPH(pressure, specificEnthalpy);
			} catch(OutOfRangeException e){}
		}
		if(specificEnthalpy != 0 && specificEntropy != 0){
			try{
				return tables.specificInternalEnergyHS(specificEnthalpy, specificEntropy);
			} catch(OutOfRangeException e){}
		}
		throw new InsufficientDataException();
	}
	private double calcSpecificHeatCapacity() throws InsufficientDataException{
		if (pressure != 0 && temperature != 0){
			try{
				return tables.isobaricHeatCapacityPT(pressure, temperature);
			} catch(OutOfRangeException e){}
		}
		if (pressure != 0 && specificEnthalpy != 0){
			try{
				return tables.isobaricHeatCapacityPH(pressure, specificEnthalpy);
			} catch (OutOfRangeException e){}
		}
		throw new InsufficientDataException();
	}
	private double calcTemperature() throws InsufficientDataException{
		if (pressure != 0 && specificEnthalpy != 0){
			try{
				double temp = tables.temperaturePH(pressure, specificEnthalpy);
				if (temp < 0 || temp > 10000000) {throw new RuntimeException("Unreasonable Temperature");}
				return temp;
			} catch(OutOfRangeException e) {}
		}
		if (specificEnthalpy != 0 && specificEntropy != 0) {
			try{
				double temp = tables.temperatureHS(specificEnthalpy, specificEntropy);
				if (temp < 0 || temp > 1000000) {throw new RuntimeException("Unreasonable Temperature");}
				return temp;
			} catch (OutOfRangeException e) {}
		}
		throw new InsufficientDataException();
	}
	
	@Override
	public double getMassFlow() {if(!solved){solve();} return massFlow;}
	
	@Override
	public double getPrandtl() {if(!solved){solve();} return prandtl;}
	
	@Override
	public double getDensity() {if(!solved){solve();} return density;}

	@Override
	public double getPressure() {if(!solved){solve();} return pressure;}

	@Override
	public double getSpecificEntropy() {if(!solved){solve();} return specificEntropy;}

	@Override
	public double getSpecificEnthalpy() {if(!solved){solve();} return specificEnthalpy;}
	
	@Override
	public double getSpecificInternalEnergy() {if(!solved){solve();} return specificInternalEnergy;}
	
	@Override
	public double getSpecificIsobaricHeatCapacity(){if(!solved){solve();} return specificHeatCapacity;}

	@Override
	public double getSpecificIsochoricHeatCapacity(){if(!solved){solve();} return specificHeatCapacity;}

	@Override
	public double getTemperature() {if(!solved){solve();} return temperature;}
	
	@Override
	public double getSpecificVolume() {if(!solved){solve();} return specificVolume;}

	@Override
	public Fluid setMassFlow(double massFlow) {
		this.massFlow = massFlow;
		solved = false;
		return this;
	}
	@Override
	public Fluid setPrandtl(double prandtl){
		this.prandtl = prandtl;
		solved = false;
		return this;
	}
	@Override
	public Fluid setDensity(double density){
		this.density = density;
		solved = false;
		return this;
	}
	@Override
	public Fluid setSpecificVolume(double specificVolume){
		this.specificVolume = specificVolume;
		solved = false;
		return this;
	}
	@Override
	public Fluid setPressure(double pressure){
		this.pressure = pressure;
		solved = false;
		return this;
	}
	@Override
	public Fluid setSpecificEntropy(double specificEntropy){
		this.specificEntropy = specificEntropy;
		solved = false;
		return this;
	}
	@Override
	public Fluid setSpecificEnthalpy(double specificEnthalpy){
		this.specificEnthalpy = specificEnthalpy;
		solved = false;
		return this;
	}
	@Override
	public Fluid setSpecificInternalEnergy(double specificInternalEnergy){
		this.specificInternalEnergy = specificInternalEnergy;
		solved = false;
		return this;
	}
	@Override
	public Fluid setSpecificIsobaricHeatCapacity(double specificHeatCapacity){
		this.specificHeatCapacity = specificHeatCapacity;
		solved = false;
		return this;
	}
	@Override
	public Fluid setSpecificIsochoricHeatCapacity(double specificHeatCapacity){
		this.specificHeatCapacity = specificHeatCapacity;
		solved = false;
		return this;
	}
	@Override
	public Fluid setTemperature(double temperature){
		this.temperature = temperature;
		solved = false;
		return this;
	}
	
	public void printProperties(){
		java.lang.System.out.println("Mass Flow Rate           : " + Double.toString(massFlow) + " kg/s");
		java.lang.System.out.println("Specific Enthalpy        : " + Double.toString(specificEnthalpy) + " kJ/kg");
		java.lang.System.out.println("Specific Entropy         : " + Double.toString(specificEntropy) + " kJ/kg·K");
		java.lang.System.out.println("Prandtl Number           : " + Double.toString(prandtl));
		java.lang.System.out.println("Pressure                 : " + Double.toString(pressure) + " MPa");
		java.lang.System.out.println("Temperature              : " + Double.toString(temperature) + " K");
		java.lang.System.out.println("Density                  : " + Double.toString(density) + " kg/m^3");
		java.lang.System.out.println("Specific Volume          : " + Double.toString(specificVolume) + " m^3/kg");
		java.lang.System.out.println("Specific Heat Capacity   : " + Double.toString(specificHeatCapacity) + " kg/m^3");
		java.lang.System.out.println("Specific Internal Energy : " + Double.toString(specificInternalEnergy) + " kg/m^3");
	}
	
	private double massFlow;
	private double prandtl;
	private double density;
	private double pressure;
	private double specificEntropy;
	private double specificEnthalpy;
	private double specificInternalEnergy;
	private double specificHeatCapacity;
	private double specificVolume;
	private double temperature;
	private boolean solved = false;
	private IF97 tables = new IF97();
	private SteamState sstate;

	@Override
	public double getQuality() {
		//TODO Implement getQuality in powerplant.fluid.Water
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Fluid setQuality(double temperature) {
		//TODO Implement setQuality in powerplant.fluid.Water
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public double getDynamicViscosity() {
		//TODO Implement getDynamicViscosity in powerplant.fluid.Water
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public double getThermalConductivity() {
		//TODO Implement getThermalConductivity in powerplant.fluid.Water
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Fluid setDynamicViscosity(double dynamicViscosity) {
		//TODO Implement setDynamicViscosity in powerplant.fluid.Water
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Fluid setThermalConductivity(double thermalConductivity) {
		//TODO Implement setThermalConductivity in powerplant.fluid.Water
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public double getRegion() {
		//TODO Implement getRegion in powerplant.fluid.Water
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
