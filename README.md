# FlightPlanner
##Proposed method to convert lat-lon values to map cords.
1. Find the lowest lattitude and subtract it from all entries.
2. Find the highest lattitude and divide all enties by it
3. Multiply all values by 100 OR the number of pixels wide we want the map to be.
4. Repeat this process for the longitude values.  
  - Nat
