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

package powerplant.object.ghe;
import powerplant.object.WorkingFluidObject;
import powerplant.gas.FlueGas;
/**
 *
 * @author DoorKip
 */
public abstract class GasHeatExchanger extends WorkingFluidObject{
	public abstract void setGasInput(FlueGas flueIn);
	public abstract FlueGas getGasInput();
	public abstract FlueGas getGasOutput();
}
