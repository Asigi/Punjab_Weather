


#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <math.h>





//This method checks if the value is an end of line character or a period, or not.
int doCheck(char value) {
    if (value == '\n' || value == '.') {
        return 0;
    } else {
        return 1;
    }
}



//This method is used when the user enters a number.
int doMath(int orig, int toAdd) {
    int final = orig * 10;
    final = final + toAdd;
    
    return final;
}


//Convert a char to an int.
int characterTodecimal(char ch) {
    int decimalNum;
    decimalNum = (int) ch;
    
    if (decimalNum < 58)
        decimalNum = decimalNum - '0'; //if char is < 10
    else
        decimalNum = decimalNum - 55;
    return decimalNum;
}


//Check if the character is a period
int periodCheck(char ch) {
    if (ch == '.') {
        return 1;
    } else {
        return 0;
    }
    
}


//this method is called to help deal with the numbers after the decimal point.
int figureOut(int count, int value) {
    
    return ((float) value / (10*count));
}


//returns the rest of the user-entered-value (after the decimal point) into a float.
float toFloat(int value) {
    printf("making a float\n");
    //figure out size of value
    int size = 0;
    int copy = value;
    while(copy > 0) {
        size++;
        copy = copy / 10;
        printf("copy is now %d\n", copy);
    }
    if (value > 0) {
        printf("in toFloat, returning %f\n", ((float)value / (pow (10, size))));
        return (float) value / (pow (10, size));
    } else {
        printf("in toFloat, returning %f\n", (value / 1.0));
        return value / 1.0 ;
    }
    
}

//counts and returns the amount of numbers that appear after the decimal.
int postDecCounter(int value) {
    int size = 0;
    int copy = value;
    while(copy > 0) {
        size++;
        copy = copy / 10;
    }
    printf("returning %d\n", size);
    return size;
}



int main (void) {

    int ino;
    float flo;
    char cho;
    
    printf("type something \n");
    cho = getchar();
    
    if (isalpha(cho)) {
        
        putchar(cho);
        printf("\n");
        //TODO print binary version
        printf("\n");
        
    } else {
        
        ino = characterTodecimal(cho);
        printf("ino is : %d\n", ino);
        
        //TODO handle negative cases.
        
        
        
        char next = getchar();
        printf("next character is %c\n", next);
        //while (doCheck(next)) {
        while (next != '\n') {
            
            printf("in while loop\n");
            
            if (periodCheck(next)) {
                
                printf("found period\n");
                
                //take the rest in as an int then convert to float.
                int rest;
                scanf("%d", &rest);
                printf("rest is %d\n", rest);
                
                float after = toFloat(rest);
                printf("after is %f\n", after);
                
                flo = ino + after;
                printf("flo is %f\n", flo);
                
                int top = roundf(flo * pow(10, postDecCounter(rest)));
                printf("top is %d\n", top);
                int bottom = pow(10, postDecCounter(rest));
                printf("bottom is %d\n", bottom);
                
                flo = (float) top / bottom;
                printf("flo is %f\n", flo);
                
            } else {
                printf("not found period\n");
                ino = doMath(ino, characterTodecimal(next));
                printf("after doMath, ino is : %d\n", ino);
                //maybe get next from here
            }
            
            next = getchar();
            printf("new next is: %c\n", next);
        
        }
        
  
        
        
    }
    
    //while (getchar() != '\n') {
        
    //}
    
    
    
    return 0;
}









        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    
    /*
     float flo;
     printf("type something \n");
     scanf("%f" , &flo);
     printf("okay thank you\n");
     printf("your float is: %f\n", flo);
     */
    //NOTE THAT THIS WORKS
    //When input was 5, output was 5.000000
    //when input was 5.5, output was 5.500000
    //when input was g, output was 264435#########.000000 (replace # signs with numbers)
    //CONCLUSION: float will only hold something as a float value with the decimal.
    //... ints will be given a zeros after the decimal. letters stored as giant numbers.
    //... special chacters stored as giant numbers.
    
    
    /*
     char flo;
     printf("type 1 character \n");
     scanf("%c" , &flo);
     printf("okay thank you\n");
     printf("your character is: %c\n", flo);
     */
    //when input is 5, output is 5
    //when input is 88, output is 8. When input is 47, output is 4 (the first number only)
    //when input is a, outout is a. When input is B, output is B
    //when input is ' ' (spacebar), output is also ' ' (spacebar)
    //when input is 34.99, output is 3
    //CONCLUSION: char can hold integers, letters, special characters.
    //Can not hold floats (>int). Will only hold '-' when a negative number is entered.
    
    
    
    /*
     int flo;
     printf("type 1 number \n");
     scanf("%d" , &flo);
     printf("okay thank you\n");
     printf("your int is: %d\n", flo);
     */
    //when input is 5, output is 5
    //when input is 77, output is 77
    //when input is f, output is some really large integer
    //when input is 4.5, output is 4
    //when input is *, output is some really large integer
    //space can not be an input.
    //CONCLUSION: ints can hold integers.
    //...Cannot hold floats (>int), letters(>giant #), or special characters (>giant #).
    
    /*
     float flo;
     char cho;
     
     printf("type something \n");
     scanf("%c%f" , &cho, &flo);
     //printf("okay, converting to int\n");
     printf("cho is: %c\n", cho);
     printf("flo is: %f\n", flo);
     */
    //get a char, then right after that get a float.
    //...because char will get the first value and stop, float will pic up remainder applicable values.
    //A: when user enters a single letter, console wants one more input.
    
    
    
    
    //use f for float intake
    
    
    
































