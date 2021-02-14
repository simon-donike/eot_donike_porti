package eot_donike_porti;

import java.io.*;
import java.util.Arrays;

public class CSVtoKML_polygon {

        public static String create_polygon_string(String lon_string, String lat_string) {
            double lon_double = Double.parseDouble(lon_string);
            double lat_double = Double.parseDouble(lat_string);
            double shift_by = 0.0012;

            String polygon;
            polygon = "\t\t\t\t" + Double.toString(lon_double+shift_by) + "," + Double.toString(lat_double) + ",100" + "\n"
                    + "\t\t\t\t" + Double.toString(lon_double) + "," + Double.toString(lat_double + shift_by) + ",100" + "\n"
                    + "\t\t\t\t" +Double.toString(lon_double - shift_by) + "," + Double.toString(lat_double) + ",100" + "\n"
                    + "\t\t\t\t" +Double.toString(lon_double) + "," + Double.toString(lat_double - shift_by) + ",100" + "\n";
            return polygon;
        }



        public static void read_convert_save_polygon(String csvFile) {
            try {
                File file = new File(csvFile);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                String[] tempArr;

                /* Create KML string with document header */
                String kml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n"
                        + "<Document>\n\n";

                /* Adding png from WMS as overlay */
                kml = kml + "<GroundOverlay>\n"
                        + "\t<name>WMS Overlay</name>\n"                    // setting name
                        + "\t<color>64ffffff</color>\n"                     // set opacity with hex
                        + "\t<Icon> <href>output_WMS.png</href> </Icon>\n"  // file path of png
                        + "\t<LatLonBox>\n"                                 // opening bbox tag
                        + "\t\t<north>42.32</north>\n"                      // setting bb
                        + "\t\t<south>42.42</south>\n"                      // setting bb
                        + "\t\t<east>-71.03</east>\n"                       // setting bb
                        + "\t\t<west>-71.13</west>\n"                       // setting bb
                        + "\t</LatLonBox>\n"                                // closing bbox tag
                        + "</GroundOverlay>\n";                             // closing ground overlay tag




                /* iterate over csv file by lines until line is empty */
                while((line = br.readLine()) != null) {
                    /* create Array per line, separate based on semicolon */
                    tempArr = line.split(";");

                    /* From here: create polygon fro central coordinate, append to array as string */


                    /*
                    String tempString = new String(tempArr[]);
                    System.out.println(tempString);
                    Double tempDouble = Double.parseDouble(tempString);
                    System.out.println(tempDouble);


                    double tempLon = Double.parseDouble(tempArr[1]);
                    double tempLat = Double.parseDouble(tempArr[2]);
                    double shift_by = 0.0012;

                    polygon = "\n" + Double.toString(tempLon+0.0012) + "," + Double.toString(tempLat) + "\n"
                            + Double.toString(tempLon) + "," + Double.toString(tempLat+0.0012) + "\n"
                            + Double.toString(tempLon-0.0012) + "," + Double.toString(tempLat) + "\n"
                            + Double.toString(tempLon) + "," + Double.toString(tempLat-0.0012) + "\n";

                    // append polygon by copying arr & defining 1 longer, then adding polygon at end
                    tempArr = Arrays.copyOf(tempArr, tempArr.length + 1);
                    tempArr[tempArr.length - 1] = polygon;

                    System.out.println(polygon);
                    */



                    /* check if temp array is != id, therefore excluding the csv header line */
                    if (!tempArr[0].equals("id")) {
                        /* fill temp string w/ kml placemark syntax + info from iterated array incl. indentation */
                        String temp_placemark = "<Placemark>\n"                                                             // start with opening placemark tag
                                //+ "\t<name>" + tempArr[0] + "</name>\n"                                                     // defining name of PM as ID of tweet

                                + "\t<ExtendedData>\n"                                                                      // open extended data tag
                                + "\t\t<Data name=\"TweetID\"> " + "<value>" + tempArr[0] + "</value> </Data>\n"            // sub-tag w/TweetID
                                + "\t\t<Data name=\"Tweet\"> " + "<value>" + tempArr[5] + "</value> </Data>\n"              // sub-tag w/Tweet
                                + "\t\t<Data name=\"Hashtags\"> " + "<value>" + tempArr[3] + "</value> </Data>\n"           // sub-tag w/Hashtags, only shows up in GE if hastags in tweet
                                + "\t\t<Data name=\"TimeStamp\" >" + "<value>" + tempArr[6].replace(' ', 'T') + ":00" + "</value> </Data>\n"          // sub-tag w/CreatedAt in corect Format
                                + "\t\t<Data name=\"UserID\"> " + "<value>" + tempArr[7] + "</value> </Data>\n"             // sub-tag w/userID
                                + "\t</ExtendedData>\n"                                                                     // close extended data tag

                                /* magic to turn timestamp into dateTime format: (YYYY-MM-DDThh:mm:sszzzzzz) */
                                + "\t<TimeStamp id=\"" + tempArr[0] + "\"> <when>" + tempArr[6].replace(' ', 'T') + ":00" + "</when>  </TimeStamp>\n"

                                //+ "\t<Point> <coordinates>" + create_polygon_string(tempArr[1],tempArr[2])  + "</coordinates> </Point>\n"   // Setting coordinates


                                + "\t<Polygon>\n"
                                + "\t\t<extrude>1</extrude>\n"
                                + "\t\t<altitudeMode>relativeToGround</altitudeMode>\n"
                                + "\t\t<outerBoundaryIs>\n"
                                + "\t\t<LinearRing>\n"
                                + "\t\t\t<coordinates>\n"
                                + create_polygon_string(tempArr[1],tempArr[2])
                                + "\t\t\t</coordinates>\n"
                                + "\t\t</LinearRing>\n"
                                + "\t\t</outerBoundaryIs>\n"
                                + "\t</Polygon>\n"
                                + "\t</Placemark>\n";

                        /* append kml string with Placemark info */
                        kml = kml + temp_placemark;
                    } // close if statement

                } // end while loop

                /* close buffered Reader */
                br.close();

                /* append kml with document/kml closing tags after iteration */
                kml = kml + "</Document>\n"+"</kml>";

                /* Write KML */
                FileWriter fw = new FileWriter("output_polygon.kml");
                fw.write(kml);
                fw.close();



            /* end try block (reading csv), defining and closing catch block */
            } catch(IOException ioe) { ioe.printStackTrace(); }
        } // end reader
    } // end CSVtoKML class

