package eot_donike_porti;

import java.io.*;

public class CSVtoKML_polygon {

        public static String create_polygon_string(String lon_string, String lat_string) {
            double lon_double = Double.parseDouble(lon_string);
            double lat_double = Double.parseDouble(lat_string);
            double shift_by = 0.0012;

            String polygon_string;
            polygon_string = "\t<Polygon>\n"
                    + "\t\t<extrude>1</extrude>\n"
                    + "\t\t<altitudeMode>relativeToGround</altitudeMode>\n"
                    + "\t\t<outerBoundaryIs>\n"
                    + "\t\t<LinearRing>\n"
                    + "\t\t\t<coordinates>\n"

                    + "\t\t\t\t" + Double.toString(lon_double+shift_by) + "," + Double.toString(lat_double) + ",100" + "\n"
                    + "\t\t\t\t" + Double.toString(lon_double) + "," + Double.toString(lat_double + shift_by) + ",100" + "\n"
                    + "\t\t\t\t" + Double.toString(lon_double - shift_by) + "," + Double.toString(lat_double) + ",100" + "\n"
                    + "\t\t\t\t" + Double.toString(lon_double) + "," + Double.toString(lat_double - shift_by) + ",100" + "\n"
                    + "\t\t\t\t" + Double.toString(lon_double+shift_by) + "," + Double.toString(lat_double) + ",100" + "\n" // repeat 1st coordinate to close polygon

                    + "\t\t\t</coordinates>\n"
                    + "\t\t</LinearRing>\n"
                    + "\t\t</outerBoundaryIs>\n"
                    + "\t</Polygon>\n"
                    + "\t</Placemark>\n";

            return polygon_string;
        }

        public static String create_color_string(String timestamp) {
            /* get minute from timstamp */
            String timestamp_clean = timestamp.substring(14, 16);
            /* calculate percentage of hour from timestamp */
            double perc = 100*  (Double.parseDouble(timestamp_clean)/60);

            /* return kml style command depending on the percentage */
            if ( (perc >= 0) & (perc < 10) ) {
                return "<styleUrl>#perc_10</styleUrl>\n"; }
            if ( (perc >= 10) & (perc < 20) ) {
                return "<styleUrl>#perc_20</styleUrl>\n"; }
            if ( (perc >= 20) & (perc < 30) ) {
                return "<styleUrl>#perc_30</styleUrl>\n"; }
            if ( (perc >= 30) & (perc < 40) ) {
                return "<styleUrl>#perc_40</styleUrl>\n"; }
            if ( (perc >= 40) & (perc < 50) ) {
                return "<styleUrl>#perc_50</styleUrl>\n"; }
            if ( (perc >= 50) & (perc < 60) ) {
                return "<styleUrl>#perc_60</styleUrl>\n"; }
            if ( (perc >= 60) & (perc < 70) ) {
                return "<styleUrl>#perc_70</styleUrl>\n"; }
            if ( (perc >= 70) & (perc < 80) ) {
                return "<styleUrl>#perc_80</styleUrl>\n"; }
            if ( (perc >= 80) & (perc < 90) ) {
                return "<styleUrl>#perc_90</styleUrl>\n"; }
            if ( (perc >= 90) & (perc < 100) ) {
                return "<styleUrl>#perc_100</styleUrl>\n"; }
            /* if no condition before is met, return other style (all black) */
            return "<styleUrl>#other</styleUrl>\n";
        }

        public static String create_styles() {
            String style_section;
            style_section =  "<Style id=\"perc_10\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF14F0FF</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n"

                    + "<Style id=\"perc_20\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF14F0AA</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n"

                    + "<Style id=\"perc_30\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF14B4FF</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n"

                    + "<Style id=\"perc_40\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF14B4BE</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n"

                    + "<Style id=\"perc_50\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF1478FF</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n"

                    + "<Style id=\"perc_60\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF1478B4</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n"

                    + "<Style id=\"perc_70\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF143CFF</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n"

                    + "<Style id=\"perc_80\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF143CA0</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n"

                    + "<Style id=\"perc_90\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF1400AA</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n"

                    + "<Style id=\"perc_100\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF140078</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n"

                    + "<Style id=\"other\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF00000</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n";

            return style_section;
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
                        + "<Document>\n\n"
                        + create_styles(); // insert style section of predefined styles



                /* iterate over csv file by lines until line is empty */
                while((line = br.readLine()) != null) {
                    /* create Array per line, separate based on semicolon */
                    tempArr = line.split(";");

                    /* check if temp array is != id, therefore excluding the csv header line */
                    if (!tempArr[0].equals("id")) {
                        /* fill temp string w/ kml placemark syntax + info from iterated array incl. indentation */
                        String temp_placemark = "<Placemark>\n"                                                             // start with opening placemark tag

                                + create_color_string(tempArr[6])
                                + "\t<ExtendedData>\n"                                                                      // open extended data tag
                                + "\t\t<Data name=\"TweetID\"> " + "<value>" + tempArr[0] + "</value> </Data>\n"            // sub-tag w/TweetID
                                + "\t\t<Data name=\"Tweet\"> " + "<value>" + tempArr[5] + "</value> </Data>\n"              // sub-tag w/Tweet
                                + "\t\t<Data name=\"Hashtags\"> " + "<value>" + tempArr[3] + "</value> </Data>\n"           // sub-tag w/Hashtags, only shows up in GE if hashtags in tweet
                                + "\t\t<Data name=\"TimeStamp\" >" + "<value>" + tempArr[6].replace(' ', 'T') + ":00" + "</value> </Data>\n"          // sub-tag w/CreatedAt in corect Format
                                + "\t\t<Data name=\"UserID\"> " + "<value>" + tempArr[7] + "</value> </Data>\n"             // sub-tag w/userID
                                + "\t</ExtendedData>\n"                                                                     // close extended data tag

                                /* magic to turn timestamp into dateTime format: (YYYY-MM-DDThh:mm:sszzzzzz) */
                                + "\t<TimeStamp id=\"" + tempArr[0] + "\"> <when>" + tempArr[6].replace(' ', 'T') + ":00" + "</when>  </TimeStamp>\n"

                                /* Add polygon string */
                                + create_polygon_string(tempArr[1],tempArr[2]);


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

