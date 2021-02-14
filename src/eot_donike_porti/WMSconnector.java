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
        URL wmsURL = null;

        try {
            wmsURL = new URL("http://maps.heigit.org/osm-wms/wms");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            //Creating a new WebMapServer
            wms = new WebMapServer(wmsURL);
            GetMapRequest request = wms.createGetMapRequest();

            /* setting parameters of request, hardcoding request parameters :( */
            request.setVersion("1.3.0");
            request.setFormat("image/png");
            request.setDimensions(2500, 2500);
            request.setTransparent(true);
            request.setSRS("EPSG:4326");
            request.setBBox("42.32,-71.13,42.42,-71.03");
            request.addLayer("osm_auto:all", "default");
            /* send request */
            GetMapResponse response = (GetMapResponse) wms.issueRequest(request);
            /* reading response via input stream, saving as BI */
            BufferedImage return_image = ImageIO.read(response.getInputStream()); // why error?
            return return_image;

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
            ImageIO.write(img,"png",output_file); //save file, file type png
            return true; // return true boolean
        } catch (IOException e) {
            e.printStackTrace(); // print error
            return false; // return false boolean
        } // close catch block

        } // close function

} // close class
