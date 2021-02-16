package eot_donike_porti;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVtoKML_polygon {


        public static String[] read_profanity_list(String working_dir, String profanity_fileName) throws IOException {
            /* reads list from file and returns as String List, passing down file & path */
            BufferedReader reader = new BufferedReader(new FileReader(working_dir+profanity_fileName));
            List<String> data_list = new ArrayList<String>();
            String line_string;
            while((line_string = reader.readLine())!=null) {
                data_list.add(line_string);
            }
            reader.close();
            return data_list.toArray(new String[]{});
        }



        public static String create_polygon_string(String lon_string, String lat_string) {
            /* receives lat/lon of tweet, creates polygon around point, returns poly string in kml format */

            double lon_double = Double.parseDouble(lon_string); // convert string from csv to double
            double lat_double = Double.parseDouble(lat_string); // convert string from csv to double
            double shift_by = 0.0015;                           // define size of addition/substraction to/from point

            String polygon_string;
            /* create kml string in polygon style */
            polygon_string =
                    /* opening polygon tags */
                    "\t<Polygon>\n"
                    + "\t\t<extrude>1</extrude>\n"                              // enabling extruding polygons
                    + "\t\t<altitudeMode>relativeToGround</altitudeMode>\n"     // define z value as relative to ground
                    + "\t\t<outerBoundaryIs>\n"                                 // opening outer boundary tag for polygon
                    + "\t\t<LinearRing>\n"                                      // defining boundary as linear ring
                    + "\t\t\t<coordinates>\n"                                   // opening coordinate tag

                    /* perform addition/substraction to point to create polygon around it */
                    + "\t\t\t\t" + Double.toString(lon_double+shift_by) + "," + Double.toString(lat_double) + ",100" + "\n"
                    + "\t\t\t\t" + Double.toString(lon_double) + "," + Double.toString(lat_double + shift_by) + ",100" + "\n"
                    + "\t\t\t\t" + Double.toString(lon_double - shift_by) + "," + Double.toString(lat_double) + ",100" + "\n"
                    + "\t\t\t\t" + Double.toString(lon_double) + "," + Double.toString(lat_double - shift_by) + ",100" + "\n"
                    + "\t\t\t\t" + Double.toString(lon_double+shift_by) + "," + Double.toString(lat_double) + ",100" + "\n" // repeat 1st coordinate to close polygon

                    /* closing all polygon tags */
                    + "\t\t\t</coordinates>\n"
                    + "\t\t</LinearRing>\n"
                    + "\t\t</outerBoundaryIs>\n"
                    + "\t</Polygon>\n"
                    + "\t</Placemark>\n";

            return polygon_string; // return finished poly kml string
        }


        public static String create_profanity_color_string(String tweet, String[] profanity_list) throws IOException {
            /* gets tweet string, checks for profanity, returns according kml template style string */

            /* get list of profanity from text reader function, passing down file & path of list */
            //List<String> profanity_list = Arrays.asList(read_profanity_list(working_dir,profanity_fileName));
            /* iterate over profanity list */
            for (int i = 0; i < profanity_list.length; i++) {
                /* ckeck if tweet contains each word
                   putting spaces around each word, so that
                   'passed' is not flagged for containing 'ass' */
                if (tweet.contains(" "+ profanity_list[i])) {
                    /* if tweet contains word, return kml string for according style */
                    //System.out.println(profanity_list.get(i)); // print out found words
                    return "<styleUrl>#contains_profanity</styleUrl>";
                } // close if statement
            } // close for loop
            /* if no return from profanity check, return kml template string for no profanity */
            return "<styleUrl>#no_profanity</styleUrl>";
        } // close method

        public static String create_styles() {
            /* creating kml style templates for polygons */
            String style_section =  "<Style id=\"no_profanity\">\n"
                    + "<LineStyle>\n"
                    + "<width>0.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF14F000</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n"

                    + "<Style id=\"contains_profanity\">\n"
                    + "<LineStyle>\n"
                    + "<width>0.5</width>\n"
                    + "</LineStyle>\n"
                    + "<PolyStyle>\n"
                    + "<color>FF1400FF</color>\n"
                    + "</PolyStyle>\n"
                    + "</Style>\n\n";
            return style_section;
            }



        public static void read_convert_save_polygon(String working_dir, String csv_fileName, String profanity_fileName) {
            /* opens csv file, writes kml file and cals functions to create polygon and check for profanity,
            writes finished kml file */

            try {
                /* open file csv file via file reader + buffered reader */
                File file = new File(working_dir,csv_fileName);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                String[] tempArr;

                /* Create KML string with document header */
                String kml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n"
                        + "<Document>\n\n"
                        + create_styles(); // insert style section of predefined styles

                String[] profanity_list = read_profanity_list(working_dir,profanity_fileName);

                /* iterate over csv file by lines until line is empty */
                while((line = br.readLine()) != null) {
                    /* create Array per line, separated based on semicolon */
                    tempArr = line.split(";");

                    /* check if temp array is != id, therefore excluding the csv header line */
                    if (!tempArr[0].equals("id")) {
                        /* fill temp string w/ kml placemark syntax + info from iterated array incl. indentation */
                        String temp_placemark = "<Placemark>\n"                                                             // start with opening placemark tag

                                + create_profanity_color_string(tempArr[5],profanity_list)                                                 // call for profanity check method, returns kml style tag


                                + "\t<ExtendedData>\n"                                                                      // open extended data tag
                                + "\t\t<Data name=\"TweetID\"> " + "<value>" + tempArr[0] + "</value> </Data>\n"            // sub-tag w/TweetID
                                + "\t\t<Data name=\"Tweet\"> " + "<value>" + tempArr[5] + "</value> </Data>\n"              // sub-tag w/Tweet
                                + "\t\t<Data name=\"Hashtags\"> " + "<value>" + tempArr[3] + "</value> </Data>\n"           // sub-tag w/Hashtags, only shows up in GE if hashtags in tweet
                                + "\t\t<Data name=\"TimeStamp\" >" + "<value>" + tempArr[6].replace(' ', 'T') + ":00" + "</value> </Data>\n"          // sub-tag w/CreatedAt in correct Format
                                + "\t\t<Data name=\"UserID\"> " + "<value>" + tempArr[7] + "</value> </Data>\n"             // sub-tag w/userID
                                + "\t</ExtendedData>\n"                                                                     // close extended data tag

                                /* turn timestamp into dateTime format: (YYYY-MM-DDThh:mm:sszzzzzz), same before, then write to correct kml timestamp tags */
                                + "\t<TimeStamp id=\"" + tempArr[0] + "\"> <when>" + tempArr[6].replace(' ', 'T') + ":00" + "</when>  </TimeStamp>\n"

                                /* Add polygon string by calling function, returns complete polygon string incl. profanity color stlye */
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



            /* end try block (reading csv), printing error and closing catch block */
            } catch(IOException ioe) { ioe.printStackTrace(); }
        } // end reader
    } // end CSVtoKML class
