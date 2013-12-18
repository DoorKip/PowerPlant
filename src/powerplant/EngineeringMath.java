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

/**
 *
 * @author DoorKip
 */
public class EngineeringMath {
	/**
	 * An implementation of the Haaland Friction Factor Equation to solve for the Darcy-Weisbach friction factor.
	 * @param reynoldsNumber
	 * @param relativeRoughness The mean surface roughness for the pipe.
	 * @return The friction factor
	 */
	public static double haalandEquation(double reynoldsNumber, double relativeRoughness){
		double frictionFactor = Math.pow(1/(-1.8 * Math.log10( Math.pow(relativeRoughness/3.7 , 1.11) + 6.9/reynoldsNumber)), 2);
		return frictionFactor;
	}
	
	//TODO Implement Serghide's Equation to test it against Haaland's
	
}
