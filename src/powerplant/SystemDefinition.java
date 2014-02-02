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

package powerplant;

import java.util.ArrayList;
import powerplant.object.WorkingFluidObject;

/**
 *
 * @author DoorKip
 */
public class SystemDefinition {
	public static ArrayList<WorkingFluidObject> getDefinition(){
		if (!isDefined){
			buildDefinition();
		}
		return (ArrayList<WorkingFluidObject>) objectArray.clone();
	}
	public static void buildDefinition(){
		//This is a placeholder for now. Eventually there will be a GUI or something to do this.
		//objectArray.add();
	}
	
	
	
	private static boolean isDefined;
	private static int variableAmmount;
	private static ArrayList<WorkingFluidObject> objectArray;
}
