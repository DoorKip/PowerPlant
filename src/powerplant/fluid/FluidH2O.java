/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package powerplant.fluid;

import com.hummeling.if97.IF97;
import com.hummeling.if97.OutOfRangeException;
import net.sf.jsteam.SteamState;

/**
 * Internal wrapper for jSteam
 * @author DoorKip
 */
public class FluidH2O extends Fluid {

	@Override
	public Fluid initializeValues() {
		solved = false;
		massFlow = 0;
		prandtl = 0;
		density = 0;
		pressure = 0;
		specificEntropy = 0;
		specificEnthalpy = 0;
		specificInternalEnergy = 0;
		specificIsobaricHeatCapacity = 0;
		specificIsochoricHeatCapacity = 0;
		specificVolume = 0;
		temperature = 0;
		return this;
	}

	@Override
	public void solve() {
		if(calcRegion()){
			boolean changed;
			do {
				changed = false;
				if (temperature == 0){if(calcTemp()){changed = true;}}
				if (pressure == 0){if(calcPressure()){changed = true;}}
				if (density == 0){if(calcDensity()){changed = true;}}
				if (specificVolume == 0){if(calcSpecificVolume()){changed = true;}}
				if (specificEntropy == 0){if(calcSpecificEntropy()){changed = true;}}
				if (specificEnthalpy == 0){if(calcSpecificEnthalpy()){changed = true;}}
				if (specificInternalEnergy == 0){if(calcSpecificInternalEnergy()){changed = true;}}
				if (specificIsobaricHeatCapacity == 0){if(calcIsobaricSpecificHeatCapacity()){changed = true;}}
				if (specificIsochoricHeatCapacity == 0){if(calcIsochoricSpecificHeatCapacity()){changed = true;}}
				if (quality == -1){if(calcQuality()){changed = true;}}
				if (dynamicViscosity == 0){if(calcDynamicViscosity()){changed = true;}}
				if (thermalConductivity == 0){if(calcThermalConductivity()){{changed = true;}}}
				if (prandtl == 0){if(calcPrandtl()){changed = true;}}
			} while(changed);
		} else {
			boolean changed;
			do {
				changed = false;
				if (specificVolume == 0){if(calcSpecificVolume()){changed = true;}}
			} while(changed);
		}
		solved = true;
	}
	
	//<editor-fold desc="CALC Methods">
	private boolean calcRegion(){
		if(region != 0){return true;}
		else if (temperature != 0 && quality != -1){sstate = SteamState.newTx(temperature, quality);}
		else if (temperature != 0 && specificEnthalpy != 0){sstate = SteamState.newTs(temperature, specificEnthalpy);}
		else if (pressure != 0 && specificVolume != 0){sstate = SteamState.newPv(pressure, specificVolume);}
		else if (pressure != 0 && specificEntropy != 0){sstate = SteamState.newPs(pressure, specificEntropy);}
		else if (pressure != 0 && specificEnthalpy != 0){sstate = SteamState.newPh(pressure, specificEnthalpy);}
		else if (pressure != 0 && temperature != 0){sstate = SteamState.newPT(pressure, temperature);}
		else if (specificEnthalpy != 0 && specificEntropy != 0){
			try{pressure = tempcalc.pressureHS(specificEnthalpy/1000, specificEntropy/1000)*1000000;} catch (OutOfRangeException e) {}
			sstate = SteamState.newPh(pressure, specificEnthalpy);
		} else return false;
		switch(sstate.getRegion()){
			case R1: region = 1; break;
			case R2: region = 2; break;
			case R3: region = 3; break;
			case R4: region = 4; break;
			default: break;
		}
		return true;
	}
	private boolean calcTemp(){
		if(region != 0){
			temperature = sstate.getTemperature();
			return true;
		} else return false;
	}
	
	private boolean calcPressure(){
		if(region != 0){
			pressure = sstate.getPressure();
			return true;
		} else return false;
	}
	private boolean calcSpecificEntropy(){
		if(region != 0){
			specificEntropy = sstate.getEntropy();
			return true;
		} else return false;
	}
	private boolean calcSpecificEnthalpy(){
		if(region != 0){
			specificEnthalpy = sstate.getEnthalpy();
			return true;
		} else return false;
	}
	private boolean calcSpecificInternalEnergy(){
		if(region != 0){
			specificInternalEnergy = sstate.getIntEnergy();
			return true;
		} else return false;
	}
	private boolean calcIsobaricSpecificHeatCapacity(){
		if(region != 0){
			specificIsobaricHeatCapacity = sstate.getIsobaricHC();
			return true;
		} else return false;
	}
	private boolean calcIsochoricSpecificHeatCapacity(){
		if(region != 0){
			specificIsochoricHeatCapacity = sstate.getIsochoricHC();
			return true;
		} else return false;
	}
	private boolean calcSpecificVolume(){
		if(density != 0){
			specificVolume = 1/density;
			return true;
		} else if(region != 0){
			specificVolume = sstate.getSpVolume();
			return true;
		} else return false;
	}
	private boolean calcDensity(){
		if(specificVolume != 0){
			density = 1/specificVolume;
			return true;
		} else if (region != 0){
			density = sstate.getDensity();
			return true;
		} else return false;
	}
	private boolean calcQuality(){
		if(region == 4){
			quality = sstate.getQuality();
			return true;
		}else return false;
	}
	private boolean calcPrandtl(){
		if(dynamicViscosity != 0 && specificIsobaricHeatCapacity != 0 && thermalConductivity != 0){
			prandtl = (dynamicViscosity * specificIsobaricHeatCapacity) / thermalConductivity;
			return true;
		}
		return false;
	}
	private boolean calcDynamicViscosity(){
		if(region != 0){
			dynamicViscosity = sstate.getDynViscosity();
			return true;
		}
		return false;
	}
	private boolean calcThermalConductivity(){
		if(region != 0){
			thermalConductivity = sstate.getThermCond();
			return true;
		}
		return false;
	}
	//</editor-fold>
	
	//<editor-fold desc="GET Methods">
	@Override
	public double getMassFlow() {
		if(!solved && massFlow == 0){solve();}
		return massFlow;
	}

	@Override
	public double getPrandtl() {
		if(!solved && prandtl == 0){solve();}
		return prandtl;
	}

	@Override
	public double getDensity() {
		if(!solved && density == 0){solve();}
		return density;
	}

	@Override
	public double getSpecificVolume() {
		if(!solved && specificVolume == 0){solve();}
		return specificVolume;
	}

	@Override
	public double getPressure() {
		if(!solved && pressure == 0){solve();}
		return pressure;
	}

	@Override
	public double getSpecificEntropy() {
		if(!solved && specificEntropy == 0){solve();}
		return specificEntropy;
	}

	@Override
	public double getSpecificEnthalpy() {
		if(!solved && specificEnthalpy == 0){solve();}
		return specificEnthalpy;
	}

	@Override
	public double getSpecificInternalEnergy() {
		if(!solved && specificInternalEnergy == 0){solve();}
		return specificInternalEnergy;
	}

	@Override
	public double getSpecificIsobaricHeatCapacity() {
		if(!solved && specificIsobaricHeatCapacity == 0){solve();}
		return specificIsobaricHeatCapacity;
	}
	
	@Override
	public double getSpecificIsochoricHeatCapacity() {
		if(!solved && specificIsochoricHeatCapacity == 0){solve();}
		return specificIsochoricHeatCapacity;
	}

	@Override
	public double getTemperature() {
		if(!solved && temperature == 0){solve();}
		return temperature;
	}
	
	@Override
	public double getQuality(){
		if(!solved && quality == -1){solve();}
		return quality;
	}
	
	@Override
	public double getDynamicViscosity(){
		if(!solved && dynamicViscosity == 0){solve();}
		return dynamicViscosity;
	}
	
	@Override
	public double getThermalConductivity(){
		if(!solved && thermalConductivity == 0){solve();}
		return thermalConductivity;
	}
	
	@Override
	public double getRegion(){
		if(!solved && region == 0){solve();}
		return region;
	}
	//</editor-fold>
	
	//<editor-fold desc="SET Methods">
	@Override
	public Fluid setMassFlow(double massFlow) {
		this.massFlow = massFlow;
		solved = false;
		return this;
	}

	@Override
	public Fluid setPrandtl(double prandtl) {
		this.prandtl = prandtl;
		solved = false;
		return this;
	}

	@Override
	public Fluid setDensity(double density) {
		this.density = density;
		solved = false;
		return this;
	}

	@Override
	public Fluid setSpecificVolume(double specificVolume) {
		this.specificVolume = specificVolume;
		solved = false;
		return this;
	}

	@Override
	public Fluid setPressure(double pressure) {
		this.pressure = pressure;
		solved = false;
		return this;
	}

	@Override
	public Fluid setSpecificEnthalpy(double specificEnthalpy) {
		this.specificEnthalpy = specificEnthalpy;
		solved = false;
		return this;
	}

	@Override
	public Fluid setSpecificEntropy(double specificEntropy) {
		this.specificEntropy = specificEntropy;
		solved = false;
		return this;
	}

	@Override
	public Fluid setSpecificInternalEnergy(double specificInternalEnergy) {
		this.specificInternalEnergy = specificInternalEnergy;
		solved = false;
		return this;
	}

	@Override
	public Fluid setSpecificIsobaricHeatCapacity(double specificHeatCapacity) {
		this.specificIsobaricHeatCapacity = specificHeatCapacity;
		solved = false;
		return this;
	}
	
	@Override
	public Fluid setSpecificIsochoricHeatCapacity(double specificHeatCapacity) {
		this.specificIsochoricHeatCapacity = specificHeatCapacity;
		solved = false;
		return this;
	}

	@Override
	public Fluid setTemperature(double temperature) {
		this.temperature = temperature;
		solved = false;
		return this;
	}
	
	@Override
	public Fluid setQuality(double quality){
		this.quality = quality;
		solved = false;
		return this;
	}
	
	@Override
	public Fluid setDynamicViscosity(double dynamicViscosity){
		this.dynamicViscosity = dynamicViscosity;
		solved = false;
		return this;
	}
	
	@Override
	public Fluid setThermalConductivity(double thermalConductivity){
		this.thermalConductivity = thermalConductivity;
		solved = false;
		return this;
	}
	//</editor-fold>
	
	@Override
	public void printProperties(){
		if(!solved){solve();}
		System.out.println("Region                   : " + Integer.toString(region));
		System.out.println("Quality                  : " + Double.toString(quality));
		System.out.println("Mass Flow Rate           : " + Double.toString(massFlow) + " kg/s");
		System.out.println("Specific Enthalpy        : " + Double.toString(specificEnthalpy) + " J/kg");
		System.out.println("Specific Entropy         : " + Double.toString(specificEntropy) + " J/kg·K");
		System.out.println("Prandtl Number           : " + Double.toString(prandtl));
		System.out.println("Pressure                 : " + Double.toString(pressure) + " Pa");
		System.out.println("Temperature              : " + Double.toString(temperature) + " K");
		System.out.println("Density                  : " + Double.toString(density) + " kg/m^3");
		System.out.println("Specific Volume          : " + Double.toString(specificVolume) + " m^3/kg");
		System.out.println("Specific Heat Capacity   : " + Double.toString(specificIsobaricHeatCapacity) + " J/kg*K");
		System.out.println("Specific Internal Energy : " + Double.toString(specificInternalEnergy) + " J/kg");
	}
	
	private boolean solved = false;
	private double massFlow;
	private double prandtl;
	private double density;
	private double pressure;
	private double specificEntropy;
	private double specificEnthalpy;
	private double specificInternalEnergy;
	private double specificIsobaricHeatCapacity;
	private double specificIsochoricHeatCapacity;
	private double specificVolume;
	private double temperature;
	private double quality = -1;
	private double dynamicViscosity;
	private double thermalConductivity;
	private SteamState sstate;
	private IF97 tempcalc = new IF97();
	private int region = 0;
}
