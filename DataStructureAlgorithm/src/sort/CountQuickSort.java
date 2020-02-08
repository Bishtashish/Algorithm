package sort;

public class CountQuickSort {
	int count =0;
public void countQuickSort(int[] arr, int low, int high)
{
	if(low< high){
		int pi=partition(arr, low,high);
		countQuickSort(arr,low,pi-1);
		countQuickSort(arr,pi+1,high);
	}	
		
	}

public int partition(int[] arr, int low, int high){
	int pivot=arr[high-1];
	int i=low-1;
	for(int j= low-1;j<high;j++){
		if(arr[j]>pivot){
			i++;
			count++;
			
		}
	}
//	count++;
	return(i+1);
	
}

//public void swap(int[] arr,int a, int b){
//	arr[a]=arr[a]+arr[b];
//	arr[b]=arr[a]-arr[b];
//	arr[a]=arr[a]-arr[b];
//}


public static void main(String args[]){
	CountQuickSort q = new CountQuickSort();
	int[] objs = { 5, 4, 3, 2, 1};
	q.countQuickSort(objs, 1, 5);
	System.out.print(q.count);
}
}
