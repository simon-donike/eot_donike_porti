package eot_donike_porti;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVtoKML_polygon {
    /* declare public String array by reading txt file via read_profanity_list funtion */


        public static String[] read_profanity_list() throws IOException {

            BufferedReader reader = new BufferedReader(new FileReader("/Users/simondonike/Documents/GitHub/eot_donike_porti/out/production/final_assignment/eot_donike_porti/profanity_list.txt"));
            List<String> data_list = new ArrayList<String>();
            String line_string;
            while((line_string = reader.readLine())!=null) {
                data_list.add(line_string);
            }
            reader.close();
            return data_list.toArray(new String[]{});
        }



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


        public static String create_profanity_color_string(String tweet) throws IOException {
            /* get list of profanity from text reader function */
            List<String> profanity_list = Arrays.asList(read_profanity_list());
            /* iterate over profanity list */
            for (int i = 0; i < profanity_list.size(); i++) {
                /* ckeck if tweet contains each word */
                if (tweet.contains(profanity_list.get(i))) {
                    /* if tweet contains word, return kml string for according style */
                    System.out.println(profanity_list.get(i));
                    return "<styleUrl>#contains_profanity</styleUrl>";
                }

            }


            return "<styleUrl>#no_profanity</styleUrl>";
            /* gets tweet, checks if profanity in tweet, returns according style
            if (tweet.contains(read_profanity_list())) {
                return "<styleUrl>contains_profanity</styleUrl>\n";
            } else {
                return "<styleUrl>_no_profanity</styleUrl>\n";
            }*/
        }

        public static String create_styles() {
            String style_section;
            style_section =  "<Style id=\"no_profanity\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF14F000</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n"

                    + "<Style id=\"contains_profanity\">\n"
                    + "<LineStyle>\n"
                    + "<width>1.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF1400FF</color>\n"
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

                                + create_profanity_color_string(tempArr[5])
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

