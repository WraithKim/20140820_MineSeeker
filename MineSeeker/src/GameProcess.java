
class GameProcess {
	//save the status of each area in integer
	private int[][] mineArr = new int[10][10];
	private static final int MINE = 1;
	public GameProcess(){
		setMine();
	}
	void setMine(){
		//count = the number of mine
		int count=0;
		while(true){
			for(int i=0; i<10; i++){
				for(int j=0; j<10; j++){
					mineArr[i][j] = (int)(Math.random()*10);
					//The rate of setting mine = 1/10
					//(mine=1, default=other)
					if(mineArr[i][j]==MINE)count++;
				}
			}
			if(count==10) return;
			else count=0;
		}
	}
	int isMine(int i, int j){
		if(mineArr[i][j]==MINE) return -1;
		//count = the number of mine near by.
		int count = 0;
		for(int row=i-1; row<i+2; row++){
			for(int col=j-1; col<j+2; col++){
				//if the target for search is
				//out of area => skip
				if(row<0||row>9||col<0||col>9) continue;
				if(mineArr[row][col]==MINE) count++;
			}
		}
		return count;
	}
}
