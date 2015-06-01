import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

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
//import processing.pdf.*;

PFont mono;

JSONArray countries;

int res = 75; //this is the "resolution", really just a scalar for the size of each flag
int dimX = 3;
int dimY = 2;

public void setup() {
  colorMode(HSB,360,100,100);
  size(1320,962);//, PDF, "world_flags.pdf");
  mono = createFont("AndaleMono", 12);
  background(0,0,100);
  smooth();

  countries = loadJSONArray("fixedish.json");

  //highest = best  
  float minPopulation = getLowestValue(countries,"Population_(Millions)"); 
  float minUnemployment = getLowestValue(countries,"Unemployment_(%)"); 
  float minGrowth = getLowestValue(countries,"Public_Debt_(%_of_GDP)");
  float minPerCapita = getLowestValue(countries,"GDP_per_Capita_(PPP)"); 
  
  float maxPopulation = getHighestValue(countries,"Population_(Millions)"); 
  float maxUnemployment = getHighestValue(countries,"Unemployment_(%)"); 
  float maxGrowth = getHighestValue(countries,"Public_Debt_(%_of_GDP)");
  float maxPerCapita = getHighestValue(countries,"GDP_per_Capita_(PPP)"); 
  
  
  //println(countries.size());

  
  float y = 2;
  int count = 0;

    while(y<height){
    
    float x = 2;
        
        while(x<width){
          
          if(count<10){
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
    
  //THIS IS THE TOP LEFT QUADRANT
    //float regionMap = map(country.getInt("RegionID"),1,6,0,100);
    if(country.getFloat("Population_(Millions)") == 9999.0f){
        fill(0xffC0C0C0);
        rect(x,y,res*dimX/2, res*dimY/2);
    }
    else{
        float popMap = map(log(country.getFloat("Population_(Millions)")),minPop, log(maxPop), 5, (res*dimY/2)-5);
        float regionMap = map(country.getInt("RegionID"),1,6,140,360);
        fill(regionMap, 80 , 65);
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

  //THIS IS THE TOP RIGHT QUADRANT
    if(country.getFloat("Unemployment_(%)") == 9999.0f){
        fill(0xffC0C0C0);
        rect(x+(res*dimX/2),y,res*dimX/2, res*dimY/2);
    }

    else if(country.getFloat("Unemployment_(%)") > 15 && country.getFloat("Unemployment_(%)") != 9999.0f){
        fill(17,82,92);
        rect(x+(res*dimX/2),y,res*dimX/2, res*dimY/2);
    }
    else if (country.getFloat("Unemployment_(%)") <= 15 && country.getFloat("Unemployment_(%)") != 9999.0f){
        //lowestValueer unemployment is better, so reverse map
        float unempMap = map(country.getFloat("Unemployment_(%)"),minUnemp, 15, 92, 17);
        fill(unempMap, 82 ,92);
        rect(x+(res*dimX/2),y,res*dimX/2, res*dimY/2);
        println(country.getString("Country_Name") + unempMap);
        println(minUnemp);
    }

  //THIS IS THE LOWER right QUADRANT
    if(country.getFloat("Public_Debt_(%_of_GDP)") == 9999.0f){
        fill(0xffC0C0C0);
        rect(x,y +(res*dimY/2),res*dimX/2, res*dimY/2);
    }

    else if (country.getFloat("Public_Debt_(%_of_GDP)") >= 90 && country.getFloat("Public_Debt_(%_of_GDP)") != 9999.0f){
        fill(0, 95, 90);
        rect(x,y +(res*dimY/2),res*dimX/2, res*dimY/2);
    }
     else if (country.getFloat("Public_Debt_(%_of_GDP)") < 90){
        float groMap = map(country.getFloat("Public_Debt_(%_of_GDP)"),minGro, 90, 0, 95);
        fill(0, 95, groMap);
        rect(x,y +(res*dimY/2),res*dimX/2, res*dimY/2);
    }

  //THIS IS THE LOWER left QUADRANT
    if(country.getFloat("GDP_per_Capita_(PPP)") == 9999.0f){
        fill(0xffC0C0C0);
        rect(x +(res*dimX/2),y +(res*dimY/2),res*dimX/2, res*dimY/2);
    }

    else if (country.getFloat("GDP_per_Capita_(PPP)") > 9999.0f){
        fill(211,92,91);
        rect(x +(res*dimX/2),y +(res*dimY/2),res*dimX/2, res*dimY/2);
    }

    else{
        float perMap = map(log(country.getFloat("GDP_per_Capita_(PPP)")),log(minPerCap), log(9999), 0, 91);
        fill(211,92,round(perMap));
        rect(x +(res*dimX/2),y +(res*dimY/2),res*dimX/2, res*dimY/2);
        println(perMap);
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
         stroke(0,0,0);
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
      continue;
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
        continue;
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
