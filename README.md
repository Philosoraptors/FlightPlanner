# FlightPlanner #

##Features We'd Like To Implement##
* Draw airport labels (DONE)
* Draw flights between airports
* Select airport
 * Display flights to/from selected airport
* Animate flight

#### Nat's Proposed method to convert lat-lon values to map cords. ####
1. Find the highest and lowest longatudes, initalize them as seperate varables.
2. Subtract the lowest variable from all entries so the lowest will be zero, and the rest will be measured in offset from zero.
2. Also subtract the lowest variable from the highest variable.
3. Divide all values by the highest value so the highest will be 1, the lowest will be zero, and the rest will be between zero and one.
4. Multiply all values by 100 OR the number of pixels wide we want the map to be.
5. Convert all values from doubles to ints to round them off.
6. latatude values will be more complicated because they are negitive, but a simmilar process will be used.
