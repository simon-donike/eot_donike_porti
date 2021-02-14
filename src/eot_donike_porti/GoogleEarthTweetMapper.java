package eot_donike_porti;

import java.awt.image.BufferedImage;

public class GoogleEarthTweetMapper {
    public static void main(String[] args) {


        /* connect to WMSconnector class as wms_connection */
        WMSconnector wms_connection = new WMSconnector();

        /* get image as return from function getWMSimage in WMSconnector class */
        BufferedImage buffered_image = wms_connection.getWMSimage();

        /* pass image to saveImage function from WMSconnector class */
        wms_connection.saveImage(buffered_image);




        /* defining path of input files (twitter CSV, profanity TXT) */
        String working_dir = "/Users/simondonike/Documents/GitHub/eot_donike_porti/out/production/final_assignment/eot_donike_porti/";

        /* calling CSV to KML class & funtion to write point kml */
        CSVtoKML.read_convert_save(working_dir, "twitter.csv");

        /* calling CSV to KML class & funtion to write and style polygon kml  */
        CSVtoKML_polygon.read_convert_save_polygon(working_dir, "twitter.csv", "profanity_list.txt");
    }
}