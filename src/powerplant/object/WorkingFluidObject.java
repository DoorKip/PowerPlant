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

package powerplant.object;
import powerplant.fluid.Fluid;
/**
 *
 * @author DoorKip
 */
public abstract class WorkingFluidObject{ 
	public abstract void solve();
	/**
	 * Sets the fluid object that the WFO will use as the input working fluid.
	 * @param fluid 
	 * @return  
	 */
	public abstract WorkingFluidObject setWorkingFluidInput(Fluid fluid);
	/**
	 * IF YOU NEED TO USE THIS, YOU ARE DOING SOMETHING WRONG. Any object should "own" its output objects.
	 * @param fluid The fluid object that will be set as the output working fluid.
	 * @return 
	 */
	public abstract WorkingFluidObject setWorkingFluidOutput(Fluid fluid);
	/**
	 * Retrieves the fluid object being used as the input working fluid.
	 * @return The input fluid object
	 */
	public abstract Fluid getWorkingFluidInput();
	/**
	 * Retrieves the fluid object being used as the output working fluid.
	 * @return The output fluid object
	 */
	public abstract Fluid getWorkingFluidOutput();
	/**
	 * Returns the solution state of the object.
	 * @return boolean solution state.
	 */
	public abstract boolean isSolved();
	
}
