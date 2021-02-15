# PLUS CDE  
## Software Development End of Term Assignment  
 S.Donike & A. Porti (2021)  
  
This repository contains Java classes completing the following tasks:  
- GoogleEarthTweetMapper.java: main class  
- WMSconnector.java: create WMS request and save it to PNG  
- CSVtoKML.java: read twitter.csv and parse into KML  
-	CSVtoKML_polygon.java: read twitter.csv, create polygons, visualize weather tweets contain profanity or not  
  
## Final Result  
The final result of turning the tweet coordinates into polygons and coloring them by checking for profanity can be seen here. the created KML file is shown via Google Earth Pro.  
![final_result](https://github.com/simon-donike/eot_donike_porti/blob/main/images/final_result.png?raw=true) 
  
  
  
  

## Flowcharts:  
GoogleEarthTweetMapper.java - main  
![main](https://github.com/simon-donike/eot_donike_porti/blob/main/images/chart_main.png?raw=true)  
  
  
  
  
  
 
WMSconnector.java  
![wms](https://github.com/simon-donike/eot_donike_porti/blob/main/images/chart_WMSconnector.png?raw=true)  
  
  
  
  
  
 
CSVtoKML.java  
![point](https://github.com/simon-donike/eot_donike_porti/blob/main/images/chart_point.png?raw=true)  
  
  
  
  
  

CSVtoKML_polygon.java  
![poly](https://github.com/simon-donike/eot_donike_porti/blob/main/images/chart_polygon.png?raw=true)  
  
The polygons are created by adding/substracting to the coordinates according to the following schema:  
![shift](https://github.com/simon-donike/eot_donike_porti/blob/main/images/shift_by.png?raw=true)  
