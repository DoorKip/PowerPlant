/*
 * Copyright (C) 2014 DoorKip
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

package powerplant.units;

/**
 *
 * @author DoorKip
 */
public class Pressure {
	public static enum Units{
		/**
		 * Pa is the base unit. All other units are representations of how many base units there are in that unit.
		 */
		Pa(1),
		kPa(1000)
		;
		Units(double conversionFactor){
			this.conversionFactor = conversionFactor;
		}
		private double conversionFactor;
	}
	public Pressure(double pressure){
		value = pressure;
	}
	private double value;
}
