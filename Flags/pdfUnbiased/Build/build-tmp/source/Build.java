import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.pdf.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Build extends PApplet {

//this now exists on github.


PFont mono;

JSONArray countries;

int res = 34; //this is the "resolution", really just a scalar for the size of each flag
int dimX = 3;
int dimY = 2;

public void setup() {
  colorMode(HSB,360,100,100);
  size(1320,962, PDF, "flags.pdf");
  mono = createFont("AndaleMono", 12);
  background(0,0,100);
  smooth();

  countries = loadJSONArray("fixedish.json");

  //highest = best  
  float minPopulation = getLowestValue(countries,"Population_(Millions)"); 
  float minUnemployment = getLowestValue(countries,"Unemployment_(%)"); 
  float minGrowth = getLowestValue(countries,"GDP_Growth_Rate_(%)");
  float minPerCapita = getLowestValue(countries,"GDP_per_Capita_(PPP)"); 
  
  float maxPopulation = getHighestValue(countries,"Population_(Millions)"); 
  float maxUnemployment = getHighestValue(countries,"Unemployment_(%)"); 
  float maxGrowth = getHighestValue(countries,"GDP_Growth_Rate_(%)");
  float maxPerCapita = getHighestValue(countries,"GDP_per_Capita_(PPP)"); 
  
  
  //println(countries.size());

  
  float y = 2;
  int count = 0;

    while(y<height){
    
    float x = 2;
        
        while(x<width){
          
          if(count<countries.size()){
            JSONObject country = countries.getJSONObject(count);

            int id = country.getInt("CountryID");
            String name = country.getString("Country_Name");
            int region = country.getInt("RegionID");
            
            //println(id + ", " + region + ", " + name);
            
            makeFlag(country, x, y, maxPopulation, maxUnemployment, maxGrowth, maxPerCapita, minPopulation, minUnemployment, minGrowth, minPerCapita); //make a flag at x and y
            
            stroke(1);
            strokeJoin(ROUND);
            noFill();
            rect(x,y,res*dimX, res*dimY);
            fill(0,0,0);
            textFont(mono);
             text(name, x,y+res*dimY+10);
            
          }
        
        else {
            fill(123,73,222);
            rect(x,y,res*dimX, res*dimY);
          }
          
          x = x + res*dimX + 8;

          count = count + 1;
        }
      y = y + res*dimY + 12;
    }
  };



public void makeFlag(JSONObject country, float x, float y, float maxPop, float maxUnemp, float maxGro, float maxPerCap, float minPop, float minUnemp, float minGro,float minPerCap){
        
    noStroke();
    
    //float regionMap = map(country.getInt("RegionID"),1,6,0,100);
    if(country.getFloat("Population_(Millions)") == 9999.0f){
        fill(0xffC0C0C0);
        rect(x,y,res*dimX/2, res*dimY/2);
    }
    else{
        float popMap = map(log(country.getFloat("Population_(Millions)")),minPop, log(maxPop), 5, (res*dimY/2)-5);
        float regionMap = map(country.getInt("RegionID"),1,6,160,310);
        fill(regionMap, 100 , 85);
        rect(x,y,res*dimX/2, res*dimY/2);
          if(country.getFloat("Population_(Millions)") > 100){
            strokeWeight(2);
            stroke(58,80,100);
            line(x+1,y+1,x+(res*dimX/2),y+(res*dimY)/2);
            line(x+(res*dimX/2)-2,y+1,x,y+(res*dimY/2));
            strokeWeight(1);
          }
        fill(0xffFFFFFF);
        ellipse((x+(res*dimX/2)/2),(y+(res*dimY/2)/2),popMap, popMap);
        noStroke();
    }

    if(country.getFloat("Unemployment_(%)") == 9999.0f){
        fill(0xffC0C0C0);
        rect(x+(res*dimX/2),y,res*dimX/2, res*dimY/2);
    }
    else{
        //lowestValueer unemployment is better, so reverse map
        float unempMap = map(country.getFloat("Unemployment_(%)"),minUnemp, maxUnemp, 120, 0);
        fill(unempMap, 100 ,85);
        rect(x+(res*dimX/2),y,res*dimX/2, res*dimY/2);
    }

    if(country.getFloat("GDP_Growth_Rate_(%)") == 9999.0f){
        fill(0xffC0C0C0);
        rect(x,y +(res*dimY/2),res*dimX/2, res*dimY/2);
    }
    else{
        float groMap = map(log(country.getFloat("GDP_Growth_Rate_(%)")),minGro, log(maxGro), 0, 120);
        fill(groMap, 100, 85);
        rect(x,y +(res*dimY/2),res*dimX/2, res*dimY/2);
    }

    if(country.getFloat("GDP_per_Capita_(PPP)") == 9999.0f){
        fill(0xffC0C0C0);
        rect(x +(res*dimX/2),y +(res*dimY/2),res*dimX/2, res*dimY/2);
    }
    else{
        float perMap = map(country.getFloat("GDP_per_Capita_(PPP)"),minPerCap, maxPerCap, 0, 120);
        fill(perMap, 100, 85);
        rect(x +(res*dimX/2),y +(res*dimY/2),res*dimX/2, res*dimY/2);
    }

    // if(country.getFloat("Population_(Millions)") > 100)
    //   {
         
    //      strokeWeight(1);
    //      stroke(#0000FF);
    //      noFill();
    //      ellipse((x+(res*dimX/2)/2),(y+(res*dimY/2)/2), 5, 5);
    //      strokeWeight(1);
    //   }
    
    if(country.getFloat("5_Year_GDP_Growth_Rate_(%)") < 0){
         strokeWeight(3);
         stroke(0xffCC0000);
         line(x+2,y+dimY*res-2,x+res*dimX-2,y+2);
         strokeWeight(1);
    }
}


//This functiona will return largest value for specific key in JSONArray
public float getHighestValue(JSONArray objects, String property){ 
  float highestValue = 0;
  for(int i = 0; i < objects.size(); i++){
      JSONObject object = objects.getJSONObject(i);
      float value = object.getFloat(property);
    if(value == 9999.0f){
      break;
    }
    else{
    if(value > highestValue){
      highestValue = value;
      }
    }
  }
  return highestValue; 
}

public float getLowestValue(JSONArray objects, String property){ 
  float lowestValue = 100;
  for(int i = 0; i < objects.size(); i++){
      JSONObject object = objects.getJSONObject(i);
      float value = object.getFloat(property);
      if(value == 9999.0f){
        break;
      }
      else{
    if(value < lowestValue){
      lowestValue = value;
    }
  }
  }
  return lowestValue; 
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Build" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
