package com.company;

import java.util.*;


public class Main {



    public static void main(String[] args) {
      
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("1. Add matrices\n2. Multiply Matrix by a constant\n"
                    +"3. Multiply matrices\n4. Transpose matrix\n5. Calculate a determinant\n" +
                    "6. Inverse matrix\n0. Exit ");



            switch(sc.nextLine()){
                case "1":
                    double[][] A = readMatrix();
                    double[][] B = readMatrix();
                    if(A.length == B.length && A[0].length == B[0].length){
                        printMatrixes(sumOfMatrixes(A,B));
                    }else{
                        System.out.println("ERROR");
                    }

                    break;
                case "2":
                    printMatrixes(multiplicationByConstant(readMatrix(),sc.nextInt()));
                    break;
                case "3":
                    double[][] C = readMatrix();
                    double[][] D = readMatrix();
                    if(C[0].length == D.length){
                        printMatrixes(multiplyMatrices(C,D));
                    }else{
                        System.out.println("ERROR");
                    }

                    break;
                case "4":
                    System.out.println("1. Main Diagonal\n2. Side Diagonal\n" +
                            "3. Vertical line\n4. Horizontal Line");

                    switch (sc.nextLine()){
                        case "1":
                            printMatrixes(transposeMatrix(readMatrix(),1));
                            break;
                        case "2":
                            printMatrixes(transposeMatrix(readMatrix(),2));
                            break;
                        case "3":
                            printMatrixes(transposeMatrix(readMatrix(),3));
                            break;
                        case "4":
                            printMatrixes(transposeMatrix(readMatrix(),4));
                            break;
                        default:
                            System.out.println("Wrong key");
                    }

                    break;
                case "5":
                    double[][] matrix = readMatrix();
                    if(matrix.length == matrix[0].length){
                        System.out.println("The result is: ");
                        double vysl = calculateDetermin(matrix,matrix.length);
                        if(vysl == (int)vysl){
                            System.out.println((int)vysl);
                        }else{
                            System.out.println(vysl);
                        }
                    }else{
                        System.out.println("No square matrix!!");
                    }

                    break;
                case "6":
                    double[][] matrixToInverse = readMatrix();
                    if(matrixToInverse.length == matrixToInverse[0].length){
                    double deter = calculateDetermin(matrixToInverse,matrixToInverse.length);
                    if(deter != 0){
                        printMatrixes(computeInverseMatrix(matrixToInverse,deter));
                    }else{
                        System.out.println("This matrix doesn't have an inverse.");
                    }
                    }else {
                        System.out.println("It is not square matrix");
                    }

                    break;
                case "0":
                    return;
                default:
                    System.out.println("No Valid key");
            }


        }

    }

    public  static double[][] readMatrix (){
        System.out.print("Matrix size: ");
        Scanner sc = new Scanner(System.in);
        int[] size = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        double[][] readedMatrix = new double[size[0]][size[1]];
        for(int i = 0; i<size[0];i++){
            readedMatrix[i] = Arrays.stream(sc.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
        }

        return  readedMatrix;

    }

    public static double[][] computeInverseMatrix(double[][] A,double deter){
        return multiplicationByConstant(adjoint(A),1/deter);
    }

    public static double[][] adjoint(double[][] A){
        double[][] adjMatrix = new double[A.length][A.length];
        if(A.length == 1){
            adjMatrix[0][0] = 1;
            return adjMatrix;
        }
        int sign = 1;


        for(int i = 0; i< A.length;i++){
            for(int j =0; j<A.length;j++){
                sign = ((i+j)%2==0)? 1:-1 ;
                adjMatrix[j][i] = sign*calculateDetermin(cofractor(A,A.length,i,j),A.length-1);
            }
        }
        return adjMatrix;
    }
    public static double calculateDetermin(double[][] A,int n){
        if (n == 1){
            return A[0][0];
        }
        if(n == 2){
            return (A[0][0]*A[1][1] - A[0][1]*A[1][0]);
        }
        int sign = 1;
        double sum = 0;
        for(int i = 0 ; i < n;i++){
            sum += sign * A[0][i]*calculateDetermin(cofractor(A,n,0,i),n-1);
            sign = -sign;
        }

        return sum;

    }

    public static double[][] cofractor(double[][] matr,int n,int row,int column){
        double[][] cofractorMatrix = new double[n-1][n-1];
        int coRow = 0;
        int coColumn = 0;
        for(int i = 0; i<n;i++){
            for(int j = 0; j<n;j++){
                if(i != row && j != column){
                    cofractorMatrix[coRow][coColumn++] = matr[i][j];

                    if(coColumn == n-1){
                        coColumn = 0;
                        coRow++;
                    }
                }

            }


        }

        return cofractorMatrix;

    }

    public static double[][] transposeMatrix(double[][] A,int decision){
        double[][] transposedMatrix = null;
        System.out.println("The result is: ");
        switch(decision){
            case 1:
                transposedMatrix = new double[A[0].length][A.length];

                for(int i = 0; i<A[0].length;i++){
                    for(int j = 0;j<A.length;j++){
                        transposedMatrix[i][j] = A[j][i];
                    }
                }
                break;
            case 2:
                transposedMatrix = new double[A[0].length][A.length];
                for(int i = 0; i<A.length;i++){
                    for(int j = 0;j<A[0].length;j++){
                        transposedMatrix[j][i] = A[A.length-1-i][A[0].length-1-j];
                    }
                }
                break;
            case 3:
                transposedMatrix = new double[A.length][A[0].length];
                for(int i = 0; i<A.length;i++){
                    for(int j = 0; j<A[0].length;j++){
                        transposedMatrix[i][j] = A[i][A[0].length-1-j];
                    }
                }
                break;
            case 4:
                transposedMatrix = new double[A.length][A[0].length];
                for(int i = 0; i<A.length;i++){
                    for(int j = 0; j<A[0].length;j++){
                        transposedMatrix[i][j] = A[A.length-1-i][j];
                    }
                }
                break;
        }
        return transposedMatrix;
    }
    public static double[][] multiplyMatrices(double[][] A,double[][] B){
        double[][] multipliedMatrix = new double[A.length][B[0].length];
        double sum = 0;
        for(int i = 0; i<A.length;i++){
            for(int j = 0; j<B[0].length;j++){
                for(int k = 0; k<A[0].length;k++){
                    multipliedMatrix[i][j] += A[i][k] * B[k][j];
                }

            }
        }
        return multipliedMatrix;
    }

    public static double[][] multiplicationByConstant(double[][] A,double constant){
        double [][] multiplicatedMatrix = new double[A.length][A[0].length];

        for(int i = 0; i<A.length;i++){
            for(int j = 0; j<A[0].length;j++){
                multiplicatedMatrix[i][j] = A[i][j] * constant;
            }
        }

        return  multiplicatedMatrix;

    }

    public static double[][] sumOfMatrixes(double[][] A,double[][] B){

        double[][] sumMatrix = new double[A.length][A[0].length];
        for(int i = 0; i<A.length;i++){
            for(int j = 0; j<A[0].length;j++){
                sumMatrix[i][j] = A[i][j] + B[i][j];
            }
        }

        return sumMatrix;
    }

    public  static  void printMatrixes(double[][] matrix){
        System.out.println("The result is: ");
        for(int i = 0; i<matrix.length;i++){
            for(int j = 0; j<matrix[0].length;j++){
                if((int)matrix[i][j] == matrix[i][j]){
                    System.out.print((int)matrix[i][j]+" ");
                }else{
                    System.out.print(matrix[i][j]+" ");
                }

            }
            System.out.println();
        }
    }
}