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
public class Units {
	public static double ft_meter(double ft){
		return ft*0.3048;
	}
	public static double inch_meter(double inch){
		return (inch/12.0)*0.3048;
	}
	public static double kPa_Pa(double pressure){
		return (pressure/1000);
	}
}
