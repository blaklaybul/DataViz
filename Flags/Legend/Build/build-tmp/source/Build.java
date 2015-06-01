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



public void setup(){
	

size(375,100, PDF, "countries.pdf");

colorMode(HSB,360,100,100);
noStroke();

//gdp
for (int i = 0; i<=5; i++){
	fill(140+(i*44), 80, 65);
	rect(i*62.5f,0,63,100);
}

// //debt
// for (int i = 0; i<95; i++){
// 	fill(0,95,i);
// 	rect(i*5*(75.0/95.0),0,6,100);
// }

// for (int i = 0; i<75; i++){
// 	fill(i+17,82,92);
// 	rect(i*5,0,6,100);
// }
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
