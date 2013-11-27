/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package powerplant;
import powerplant.fluid.Fluid;
/**
 *
 * @author DoorKip
 */
public abstract class WorkingFluidObject {
	/**
	 * Sets the fluid object that the WFO will use as the input working fluid.
	 * @param fluid 
	 */
	public abstract WorkingFluidObject setWorkingFluidInput(Fluid fluid);
	/**
	 * IF YOU NEED TO USE THIS, YOU ARE DOING SOMETHING WRONG. Any object should "own" its output objects.
	 * @param fluid The fluid object that will be set as the output working fluid.
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
