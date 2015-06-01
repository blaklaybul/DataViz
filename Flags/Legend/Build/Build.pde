import processing.pdf.*;

void setup(){
	

size(375,100, PDF, "countries.pdf");

colorMode(HSB,360,100,100);
noStroke();

//gdp
for (int i = 0; i<=5; i++){
	fill(140+(i*44), 80, 65);
	rect(i*62.5,0,63,100);
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

