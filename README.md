# FlightPlanner
#### Nat's Proposed method to convert lat-lon values to map cords. ####
1. Find the highest and lowest lattitudes, initalize them as seperate varables.
2. Subtract the lowest variable from all entries so the lowest will be zero, and the rest will be measured in offset from zero.
2. Also subtract the lowest variable from the highest variable.
3. Divide all values by the highest value so the highest will be 1, the lowest will be zero, and the rest will be between zero and one.
3. Multiply all values by 100 OR the number of pixels wide we want the map to be.
4. Convert all values from doubles to ints to round them off.
4. Repeat this process for the longitude values.  
