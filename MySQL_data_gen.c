#include <stdio.h>
#include <stdlib.h>  
#include <math.h>


int main()
{
   char sentence[1000];
   FILE *fptr;

   fptr = fopen("MySQL_Demo_Data.txt", "w");
   if(fptr == NULL)
   {
      printf("Error!");
      exit(1);
   }
   fprintf(fptr,"DROP TABLE IF EXISTS PowerTest;\n");
   fprintf(fptr,"CREATE TABLE PowerTest ( id int auto_increment primary key, Voltage DECIMAL(4, 1), Current DECIMAL(3,1), RealPower DECIMAL(5,1), ReactivePower DECIMAL(5, 1));\n");
   for ( double i = 1; i <= 100; i++){
	   double V, I, PF;
	   V = 120 + (5 * sin(i/10));
	   I = 7.5 + (7.5 * sin(i/8));
	   PF = 0.5 + (0.25 * sin(i/100));
      fprintf(fptr,"INSERT INTO PowerTest (Voltage, Current, RealPower, ReactivePower)\n");
      fprintf(fptr,"VALUES ( %3.2f, %3.2f, %3.2f, %3.2f);\n", V, I, V*I*PF, sqrt(V*V*I*I - V*I*PF*V*I*PF));
   }
   fclose(fptr);

   return 0;
}
