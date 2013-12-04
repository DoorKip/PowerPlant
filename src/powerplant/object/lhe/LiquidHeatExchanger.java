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

package powerplant.object.lhe;

import powerplant.object.WorkingFluidObject;
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
