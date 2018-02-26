package module3;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

public class Earthquake extends PApplet {
	private static final boolean offline = true;
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	public String  earthquakesURL = "2.5_week.atom"; 	
	private UnfoldingMap map;
	
	public void setup() {
		size(950,600,OPENGL);
		//take size:700,500
		//coordinates of the map in the canvas 200,50 
		//map provider
		
		AbstractMapProvider provider = new Google.GoogleTerrainProvider();
		AbstractMapProvider provider2 = new Google.GoogleTerrainProvider();
		// Set a zoom level
		int zoomLevel = 10;

		if (offline) {
			// If you are working offline, you need to use this provider
			// to work with the maps that are local on your computer.
			provider = new MBTilesMapProvider(mbTilesString);
			provider2= new  MBTilesMapProvider(mbTilesString);
			// 3 is the maximum zoom level for working offline
			zoomLevel = 3;
		}
		map = new UnfoldingMap(this,200,50, 700, 500, provider );
		
		//default zoom level
		map.zoomToLevel(2);
		//makes app come to life
		MapUtils.createDefaultEventDispatcher(this, map);
	//	Location loc= new Location(-38.14f,-73.03f);

		    //Use provided parser to collect properties for each earthquake
		    //PointFeatures have a getLocation method
		    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
		    
		    // These print statements show you (1) all of the relevant properties 
		    // in the features, and (2) how to get one property and use it
		   
		    
		    // Here is an example of how to use Processing's color method to generate 
		    // an int that represents the color yellow.  
		    
		    int red= color(255,0,0);
			  int yellow= color(255,255,0);
			  int green= color(0,255,0);
			  
		  List<Marker> markers1= new ArrayList<Marker>();
		  for(PointFeature eq: earthquakes) {
			  SimplePointMarker simplePointMarker = createMarker(eq);
			  
			    	PointFeature f = earthquakes.get(0);
			    	System.out.println(f.getProperties());
			    	Object magObj = f.getProperty("magnitude");
			    	float mag = Float.parseFloat(magObj.toString());
			    	if ( mag < 4.0 ){
						simplePointMarker.setColor(green);
						simplePointMarker.setRadius(5);
					}
					else if( mag < 5.0 ){
						simplePointMarker.setColor(yellow);
						simplePointMarker.setRadius(8);
					}
					else {
						simplePointMarker.setColor(red);
						simplePointMarker.setRadius(10);
					}
			    	
			    	markers1.add(simplePointMarker);
			    	// PointFeatures also have a getLocation method
			    }
		  map.addMarkers(markers1);;
		  }
		    //TODO: Add code here as appropriate
		  
		  
		
		
		
		
	
   
private SimplePointMarker createMarker(PointFeature feature)
{
	// finish implementing and use this method, if it helps.
	return new SimplePointMarker(feature.getLocation());
}

	
	public void draw() {
		background(10);
		//take the map object and anything that is associated with it and refresh the
		//map object
		map.draw();
		addKey();
	}
	
	public void addKey() {
		fill(200,200,200);
		rect(20,50,150,380);
	}
}