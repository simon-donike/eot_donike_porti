package eot_donike_porti;

import java.io.*;

public class CSVtoKML {
        public static void read_convert_save(String csvFile) {
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

                    /* check if temp array is != id, therefore excluding the csv header line */
                    if (!tempArr[0].equals("id")) {
                        /* fill temp string w/ kml placemark syntax + info from iterated array incl. indentation */
                        String temp_placemark = "<Placemark>\n"                                                             // start with opening placemark tag
                                //+ "\t<name>" + tempArr[0] + "</name>\n"                                                     // defining name of PM as ID of tweet

                                + "\t<ExtendedData>\n"                                                                      // open extended data tag
                                + "\t\t<Data name=\"TweetID\"> " + "<value>" + tempArr[0] + "</value> </Data>\n"            // sub-tag w/TweetID
                                + "\t\t<Data name=\"Tweet\"> " + "<value>" + tempArr[5] + "</value> </Data>\n"              // sub-tag w/Tweet
                                + "\t\t<Data name=\"Hashtags\"> " + "<value>" + tempArr[3] + "</value> </Data>\n"           // sub-tag w/Hashtags, only shows up in GE if hastags in tweet
                                + "\t\t<Data name=\"TimeStamp\" >" + "<value>" + tempArr[6] + "</value> </Data>\n"          // sub-tag w/CreatedAt
                                + "\t\t<Data name=\"UserID\"> " + "<value>" + tempArr[7] + "</value> </Data>\n"             // sub-tag w/userID
                                + "\t</ExtendedData>\n"                                                                     // close extended data tag

                                /* magic to turn timestamp into dateTime format: (YYYY-MM-DDThh:mm:sszzzzzz) */
                                + "\t<TimeStamp id=\"" + tempArr[0] + "\"> <when>" + tempArr[6].replace(' ', 'T') + ":00" + "</when>  </TimeStamp>\n"

                                + "\t<Point> <coordinates>" + tempArr[1] + "," + tempArr[2] + "</coordinates> </Point>\n"   // Setting coordinates
                                + "</Placemark>\n";                                                                         // closing placemark tag

                        /* append kml string with Placemark info */
                        kml = kml + temp_placemark;
                    } // close if statement

                } // end while loop

                /* close buffered Reader */
                br.close();

                /* append kml with document/kml closing tags after iteration */
                kml = kml + "</Document>\n"+"</kml>";

                /* Write KML */
                FileWriter fw = new FileWriter("output.kml");
                fw.write(kml);
                fw.close();



            /* end try block (reading csv), defining and closing catch block */
            } catch(IOException ioe) { ioe.printStackTrace(); }
        } // end reader
    } // end CSVtoKML class

