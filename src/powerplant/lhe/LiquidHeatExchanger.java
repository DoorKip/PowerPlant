/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package powerplant.lhe;

import powerplant.WorkingFluidObject;
import powerplant.fluid.Fluid;

/**
 *
 * @author DoorKip
 */
public abstract class LiquidHeatExchanger extends WorkingFluidObject {
	/**
	 * Sets the fluid that will be used as the input exchange fluid.
	 * @param fluid The fluid to be set as the input exchange fluid.
	 * @return Returns this object
	 */
	public abstract WorkingFluidObject setExchageFluidIn(Fluid fluid);
	/**
	 * Sets the fluid that will be used as the output exchange fluid.
	 * WARNING: IF YOU ARE USING THIS METHOD, YOU ARE PROBABLY DOING SOMETHING WRONG. Any object should "own" its output fluid.
	 * @param fluid The fluid to be set as the output exchange fluid.
	 * @return 
	 */
	public abstract WorkingFluidObject setExchageFluidOut(Fluid fluid);
	/**
	 * Returns the fluid being used as the input exchange fluid.
	 * @return The fluid
	 */
	public abstract Fluid getExchangeFluidIn();
	/**
	 * Returns the fluid being used as the output exchange fluid.
	 * @return The fluid
	 */
	public abstract Fluid getExchangeFluidOut();
}
