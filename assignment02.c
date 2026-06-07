#include <stdio.h>

#include <stdlib.h>

void constantSpace(int n){

int a=10;

int b=20;

int sum=a+b;

}

void linearSpace(int n){

int *arr=(int*)malloc(n*sizeof(int));

for(int i=0;i<n;i++){

arr[i]=i;

}

free(arr);

}

void quadraticSpace(int n){

int **matrix=(int**)malloc(n*sizeof(int*));

for(int i=0;i<n;i++){

matrix[i]=(int*)malloc(n*sizeof(int));

}

for(int i=0;i<n;i++){

free(matrix[i]);

}

free(matrix);

}

int main(){

int sizes[]={100,200,400,800,1600};

int numSizes=5;

printf("Input Size\tO(1) Space\tO(n) Space\tO(n^2) Space\n");

for(int i=0;i<numSizes;i++){

int n=sizes[i];

constantSpace(n);

linearSpace(n);

quadraticSpace(n);

int constantMemory=3*sizeof(int);

int linearMemory=n*sizeof(int);

int quadraticMemory=n*n*sizeof(int);

printf("%d\t\t%d bytes\t%d bytes\t%d
bytes\n",n,constantMemory,linearMemory,quadraticMemory);

}

return 0;

}
