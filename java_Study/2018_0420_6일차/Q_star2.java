class Q_star2{
	public static void main(String [] args){
		for(int i =0;i<5;i++){//가로로 별 출력
			
			for(int k =0;k<i+1;k++)//줄 바꾸기 
			{
				System.out.print("*");
			}
			System.out.println();
		}
	}
}
			