package eot_donike_porti;

import java.awt.image.BufferedImage;

public class GoogleEarthTweetMapper {
    public static void main(String[] args) {


        // connect to WMSconnector class as wms_connection
        WMSconnector wms_connection = new WMSconnector();
        // get image as return from function getWMSimage in WMSconnector class
        BufferedImage buffered_image = wms_connection.getWMSimage();
        // pass image to saveImage function from WMSconnector class
        wms_connection.saveImage(buffered_image);




        /* call method, pass csv path to csv reader & kml creator */
        CSVtoKML.read_convert_save("/Users/simondonike/Documents/GitHub/eot_donike_porti/out/production/final_assignment/eot_donike_porti/twitter.csv");
        CSVtoKML_polygon.read_convert_save_polygon("/Users/simondonike/Documents/GitHub/eot_donike_porti/out/production/final_assignment/eot_donike_porti/twitter.csv");
    }
}