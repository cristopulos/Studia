package aisd.sorts;

public class BubbleSort extends SortAlg {
	
	public void sort( int [ ] arr )
	{
	     boolean flagWasSwapped = true;   
	     int temp;  

	     while ( flagWasSwapped )
	     {
	            flagWasSwapped= false;    
	            for(int  j=0;  j < arr.length -1;  j++ )
	            {
	                   if ( arr[ j ] < arr[j+1] )   
	                   {
	                           temp = arr[ j ];              
	                           arr[ j ] = arr[ j+1 ];
	                           arr[ j+1 ] = temp;
	                          flagWasSwapped = true;             
	                  } 
	            } 
	      } 
	} 

}
