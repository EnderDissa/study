public class lab0{
	public static void main(String[] args){
		long[] a={16,14,12,10,8,6,4};
		float[] x=new float[11]; //массив из 11 чисел типа float
		int MIN=-12;
		int MAX=10; //диапазон значений 
		for (int i=0;i<11;i++){
			float j=(float)Math.random()*(MAX-MIN)+MIN; 
			x[i]=j;}
		double[][]d=new double[7][11]; //двумерный массив типа double 7x11
            for (int i=0;i<7;i++){
                for (int j=0;j<11;j++){
                    double xj=x[j]; //запоминаем x[j] для дальнейших вычислений
                        if (a[i]==12){ 
                            d[i][j]=Math.atan(Math.pow(Math.cos(xj),2)); //проверяем первое условие
                        }
                        else if (a[i]==6 || a[i]==8 || a[i]==16){
                            d[i][j]=Math.asin(Math.pow(Math.E,Math.cbrt(-Math.sqrt(Math.abs(xj))))); //проверяем второе условие
                        }
                        else{
                            d[i][j]=Math.pow(Math.asin(Math.sin(Math.cos(Math.pow((2-xj)/Math.PI,2)))),(Math.pow(Math.pow(0.25/Math.sin(xj),xj/0.25),0.25/(Math.pow(xj,(1/4)/(xj-2/3))/(Math.pow(Math.E,xj)-1)+0.5))/2)); //проверяем третье условие
	        }
                }
            }
        for (double[]doubles:d){
                for (int j=0;j<d[0].length;j++){
                        System.out.printf("%.2f ",doubles[j]); //перебираем все элементы массива
                }
            System.out.print("\n");
            }
	}
}