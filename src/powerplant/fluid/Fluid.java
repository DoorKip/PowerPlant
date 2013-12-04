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

package powerplant.fluid;

/**
 *
 * @author DoorKip
 */
public abstract class Fluid {
	public abstract Fluid initializeValues();
	public abstract void solve();
	public abstract double getMassFlow();
	public abstract double getPrandtl();
	public abstract double getDensity();
	public abstract double getSpecificVolume();
	public abstract double getPressure();
	public abstract double getSpecificEntropy();
	public abstract double getSpecificEnthalpy();
	public abstract double getSpecificInternalEnergy();
	public abstract double getSpecificIsobaricHeatCapacity();
	public abstract double getSpecificIsochoricHeatCapacity();
	public abstract double getTemperature();
	public abstract double getQuality();
	public abstract double getDynamicViscosity();
	public abstract double getThermalConductivity();
	public abstract double getRegion();
	public abstract Fluid setMassFlow(double massFlow);
	public abstract Fluid setPrandtl(double prandtl);
	public abstract Fluid setDensity(double rho);
	public abstract Fluid setSpecificVolume(double specificVolume);
	public abstract Fluid setPressure(double pressure);
	public abstract Fluid setSpecificEnthalpy(double specificEnthalpy);
	public abstract Fluid setSpecificEntropy(double specificEntropy);
	public abstract Fluid setSpecificInternalEnergy(double specificInternalEnergy);
	public abstract Fluid setSpecificIsobaricHeatCapacity(double specificHeatCapacity);
	public abstract Fluid setSpecificIsochoricHeatCapacity(double specificHeatCapacity);
	public abstract Fluid setTemperature(double temperature);
	public abstract Fluid setQuality(double quality);
	public abstract Fluid setDynamicViscosity(double dynamicViscosity);
	public abstract Fluid setThermalConductivity(double thermalConductivity);
	public abstract void printProperties();
	public double nominalLiquidDensity;
}
