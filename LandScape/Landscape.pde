PImage landscape;
PImage face; 
PImage land;

void setup() {
  size(660, 468);
  smooth();
  land = loadImage("stars.jpeg");
  face = loadImage("jspacemanstencil.jpg");
  landscape = createImage(land.width, land.height,RGB);
  background(land);
//}

//void draw(){
  landscape.loadPixels();
  face.loadPixels();
  land.loadPixels();
  for (int x=0; x < land.width; x++){
      for (int y=0; y < land.height; y++){
         int loc = x + y*land.width;
         if (face.pixels[loc] > color(123)) {
           
        //    landscape.pixels[loc] = color(land.pixels[(land.pixels.length-loc-1)]);
           landscape.pixels[loc] = 0;
         }
//         else if (face.pixels[loc] < color(123) && face.pixels[loc+1] < (123)) {
//         
//             landscape.pixels[loc] = color(random(loc%255), random(0,loc%255), loc%255);
//           }
           
         else {
           
           landscape.pixels[loc] = color(red(land.pixels[land.pixels.length-loc-1]),red(land.pixels[land.pixels.length-loc-1]),120);
           //landscape.pixels[loc] = color(red(land.pixels[loc]),255,0);
         }
         
         
      }
  }
  landscape.format = ARGB;
  landscape.updatePixels();
  
  tint(200,150);
  image(landscape,0,0);
  
}


