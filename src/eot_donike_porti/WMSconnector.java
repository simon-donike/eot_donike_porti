package eot_donike_porti;

import org.geotools.ows.wms.WebMapServer;
import org.geotools.ows.wms.request.GetMapRequest;
import org.geotools.ows.wms.response.GetMapResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class WMSconnector {

    public BufferedImage getWMSimage() {

        WebMapServer wms = null;
        URL connectionURL = null;

        try {
            connectionURL = new URL("http://maps.heigit.org/osm-wms/wms");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            //Creating a new WebMapServer
            wms = new WebMapServer(connectionURL);
            //Creating a new request for the WebMapServer
            GetMapRequest request = wms.createGetMapRequest();
            //setting the version of the WMS
            request.setVersion("1.3.0");
            //Setting the format of the image to request
            request.setFormat("image/png");
            //setting the dimensions of the requested image
            request.setDimensions(250, 250);
            //setting the background of the image transparent
            request.setTransparent(true);
            //This sets CRS=EPSG:XXXX NOT SRS=EPSG:XXXX so it works only with Version=1.3.0
            //setting the SRS of the request
            request.setSRS("EPSG:4326");
            //setting the bounding-box for the request
            request.setBBox("42.32,-71.13,42.42,-71.03");
            //System.out.println(request.toString());
            //setting the layer to request
            request.addLayer("osm_auto:all", "default");
            //sending the request
            GetMapResponse response = (GetMapResponse) wms.issueRequest(request);
            //reading the requested image
            BufferedImage image = ImageIO.read(response.getInputStream());

            return image;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


        /* function to save image, returns boolean if it worked or not */
        public boolean saveImage(BufferedImage img) {
            /* save as File data type and set name */
            File output_file = new File("output_WMS.png");

            try {
                /* write file set file type */
                ImageIO.write(img,"png",output_file);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }



        } // close function

} // close class
