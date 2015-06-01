//this now exists on github.


JSONArray countries;
int res = 34; //this is the "resolution", really just a scalar for the size of each flag
int dimX = 3;
int dimY = 2;

void setup() {
  colorMode(HSB,360,100,100);
  size(1220,810);
  background(255,255,255);

  countries = loadJSONArray("test.json");
  String tempRegion = "";
  int counter = 0;
  //gonna add regionID to JSON objects
  for (int i = 0; i<countries.size(); i++){
    JSONObject country = countries.getJSONObject(i);
    if(country.getString("Region") == tempRegion){
    country.setInt("RegionID", counter);
    }
    else{
      counter = counter+1;
      country.setInt("RegionID", counter);
      tempRegion = country.getString("Region");
    }
    println(tempRegion);
  }

  //highest = best  
  float minCorruption = getLowestValue(countries,"Freedom_from_Corruption"); 
  float minPropertyRights = getLowestValue(countries,"Property_Rights"); 
  float minLabor = getLowestValue(countries,"Labor_Freedom");
  float minMoney = getLowestValue(countries,"Monetary_Freedom"); 
  
  float maxCorruption = getHighestValue(countries,"Freedom_from_Corruption"); 
  float maxPropertyRights = getHighestValue(countries,"Property_Rights"); 
  float maxLabor = getHighestValue(countries,"Labor_Freedom");
  float maxMoney = getHighestValue(countries,"Monetary_Freedom"); 
  
  
  //println(countries.size());

  
  float y = 0;
  int count = 0;

    while(y<height){
    
    float x = 0;
        
        while(x<width){
          
          if(count<countries.size()){
            JSONObject country = countries.getJSONObject(count);

            int id = country.getInt("CountryID");
            String name = country.getString("Country_Name");
            int region = country.getInt("RegionID");
            
            //println(id + ", " + region + ", " + name);
            
            makeFlag(country, x, y, maxCorruption, maxPropertyRights, maxLabor, maxMoney, minCorruption, minPropertyRights, minLabor, minMoney); //make a flag at x and y
            
            stroke(1);
            strokeJoin(ROUND);
            noFill();
            rect(x,y,res*dimX, res*dimY);
            fill(216,104,104);
            text(region, x+3,y+20);
            
          }
        
        else {
            fill(123,73,222);
            rect(x,y,res*dimX, res*dimY);
          }
          
          x = x + res*dimX;

          count = count + 1;
        }
      y = y + res*dimY;
    }
  };



void makeFlag(JSONObject country, float x, float y, float maxCorr, float maxPro, float maxLab, float maxMon, float minCorr,float minPro,float minLab,float minMon){
        
    noStroke();
    
    float regionMap = map(country.getInt("RegionID"),1,6,0,100);

    float corrMap = map(country.getFloat("Freedom_from_Corruption"),minCorr, maxCorr, 0, 120);
    fill(corrMap, 100 ,regionMap);
    rect(x,y,res*dimX/2, res*dimY/2);
    
    float propMap = map(country.getFloat("Property_Rights"),minPro, maxPro, 0, 120);
    fill(propMap, 100 ,regionMap);
    rect(x+(res*dimX/2),y,res*dimX/2, res*dimY/2);
    
    float labMap = map(country.getFloat("Labor_Freedom"),minLab, maxLab, 0, 120);
    fill(labMap, 100, regionMap);
    rect(x,y +(res*dimY/2),res*dimX/2, res*dimY/2);
    
    float monMap = map(country.getFloat("Monetary_Freedom"),minMon, maxMon, 0, 120);
    fill(monMap, 100, regionMap);
    rect(x +(res*dimX/2),y +(res*dimY/2),res*dimX/2, res*dimY/2);
    
    if(country.getFloat("Population_(Millions)") > 10)
      {
         strokeWeight(3);
         stroke(#FFFAFA);
         line(x+2,y+2,x+res*dimX,y+res*dimY);
         strokeWeight(1);
      }
    
    if(country.getFloat("5_Year_GDP_Growth_Rate_(%)") < 0){
         strokeWeight(3);
         stroke(#CC0000);
         line(x+2,y+dimY*res,x+res*dimX,y+2);
         strokeWeight(1);
    }
}


//This functiona will return largest value for specific key in JSONArray
float getHighestValue(JSONArray objects, String property){ 
  float highestValue = 0;
  for(int i = 0; i < objects.size(); i++){
      JSONObject object = objects.getJSONObject(i);
      float value = object.getFloat(property);

    if(value > highestValue){
      highestValue = value;
    }
  }
  return highestValue; 
}

float getLowestValue(JSONArray objects, String property){ 
  float lowestValue = 100;
  for(int i = 0; i < objects.size(); i++){
      JSONObject object = objects.getJSONObject(i);
      float value = object.getFloat(property);

    if(value < lowestValue){
      lowestValue = value;
    }
  }
  return lowestValue; 
}

