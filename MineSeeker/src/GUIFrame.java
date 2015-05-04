import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class GUIFrame extends JFrame implements ActionListener{
	//Components
	private GameProcess gp = new GameProcess();
	private Container con = null;
	private GridLayout mineArea = new GridLayout(10,10);
	private JButton[][] minePos = new JButton[10][10];
	private static final int NORTH = 1;
	private static final int SOUTH = 2;
	private static final int WEST = 3;
	private static final int EAST = 4;
	
	public GUIFrame(String title){
		super(title);
		super.setSize(700,750);
			//create window at the center of screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xInitP = (int)(screen.getWidth()/2-super.getWidth()/2);
		int yInitP = (int)(screen.getHeight()/2-super.getHeight()/2);
		super.setLocation(xInitP, yInitP);

		this.setInterior();
		this.setAction();
		
		super.setResizable(false);
		super.setVisible(true);
	}
	void setInterior(){
		//setting GUI
		con = this.getContentPane();
		this.setLayout(mineArea);
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				minePos[i][j] = new JButton();
				this.add(minePos[i][j]);
			}
		}
		
	}
	void setAction(){
		//setting action
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				minePos[i][j].addActionListener(this);
			}
		}
	}
	public void actionPerformed(ActionEvent ae){
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				if(ae.getSource()== minePos[i][j]){
					updateMineArea(i,j);
					break;
				}
			}
		}
	}
	void updateMineArea(int i, int j){
		int result = gp.isMine(i,j);
		if(result==-1){
			showMineArea();
			return;
		}else{
			minePos[i][j].setEnabled(false);
			if(result>0){
				minePos[i][j].setText(Integer.toString(result));
			}else{
				openAreaAuto(i, j, 0);
			}
		}
	}
	//if player select mine=>show all and block the buttons
	void showMineArea(){
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				minePos[i][j].setText("");
				minePos[i][j].setEnabled(false);
				if(gp.isMine(i,j)==-1)
					minePos[i][j].setBackground(Color.RED);
			}
		}
	}
	//if player select the area no mine is near by
	//=> open areas until close to mine
	void openAreaAuto(int i, int j, int direc){
		if(i<0||i>9||j<0||j>9) return;
		//out of area
		else if(!minePos[i][j].isEnabled()&&direc!=0) return;
		//if already selected point
		//except point that event initiate
		else{
			int result = gp.isMine(i,j);
			minePos[i][j].setEnabled(false);
			if(result>0){
				minePos[i][j].setText(Integer.toString(result));
			}
			else{
				if(direc!=SOUTH)openAreaAuto(i-1, j, NORTH);
				if(direc!=NORTH)openAreaAuto(i+1, j, SOUTH);
				if(direc!=EAST)openAreaAuto(i, j-1, WEST);
				if(direc!=WEST)openAreaAuto(i, j+1, EAST);
			}
		}
	}
}
