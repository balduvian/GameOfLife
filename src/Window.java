import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JFrame{
	
	Canvas canvas;
	boolean run=false;
	JButton r;
	JButton n;
	JButton c;
	int[][] block = new int[32][32];
	double size = 16;
	int ay,ax;
	Color[] colors = {Color.WHITE,Color.BLACK};
	int cl;
	
	public int[][] generation(int[][] a){
		int[][] temp = new int[a.length][a[1].length];
		for(int y=0;y<a.length;y++){
			for(int x=0;x<a[y].length;x++){
				if(a[y][x]==1){
					if(getNeighbors(a,y,x)>3 || getNeighbors(a,y,x)<2){
						temp[y][x]=1;
					}
				}
				else{
					if(getNeighbors(a,y,x)==3){
						temp[y][x]=2;
					}
				}
			}
		}
		for(int y=0;y<a.length;y++){
			for(int x=0;x<a[y].length;x++){
				if(temp[y][x]==1){
					a[y][x]=0;
				}
				if(temp[y][x]==2){
					a[y][x]=1;
				}
			}
		}
		return a;
	}
		
	public int getNeighbors(int[][] a, int py, int px){
		int n=0;
		for(int y=-1;y<=1;y++){
			for(int x=-1;x<=1;x++){
				try{
					if(a[py+y][px+x]==1){
						n++;
					}
				}catch(Exception e){}
			}
		}
		if(a[py][px]==1){
			return n-1;
		}else{
			return n;
		}
	}
	
	public class Canvas extends JPanel{
		public void paintComponent(Graphics g){
			//t.setText(Boolean.toString(c));
			super.paintComponent(g);
			setBackground(Color.GRAY);
			for(int y=0;y<block.length;y++){
				for(int x=0;x<block[y].length;x++){
					g.setColor(colors[block[y][x]]);
					int offy = (int)((double)getHeight()/2-((double)block.length*(size/2)));
					int offx = (int)((double)getWidth()/2-((double)block[0].length*(size/2)));
					int py = (int)((double)y*size+offy);
					int px = (int)((double)x*size+offx);
					g.fill3DRect(px, py, (int)size, (int)size,true);
					//g.setColor(Color.RED);
					//g.drawString(String.valueOf(getNeighbors(block,y,x)),px+10,py+10);
					//System.out.println(((double)x*size+((double)getWidth()/2-((double)block.length*(size/2)))));
					//g.fillRect(x*size, y*size, size, size);
				}
			}
		}
	}
	
	public Window(){
		setTitle("Game of Life");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 640);
		canvas = new Canvas();
		canvas.addMouseListener(new MListen());
		canvas.addMouseMotionListener(new MListen2());
		r = new JButton("Start");
		n = new JButton("Next");
		c = new JButton("Clear");
		r.addActionListener(new SS());
		n.addActionListener(new SK());
		c.addActionListener(new SC());
		JPanel bc = new JPanel();
		bc.setLayout(new GridLayout());
		bc.add(r);
		bc.add(n);
		bc.add(c);
		//bc.add(t);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
	    cp.add(canvas,BorderLayout.CENTER);
	    cp.add(bc, BorderLayout.SOUTH);
	    //setResizable(false);
		setVisible(true);
	}
	
	public class SS implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			if(run){
				r.setText("Start");
				run=false;
				Imager.setGoal();
			}else{
				r.setText("Stop");
				run=true;
			}
		}
	}
	
	public class SK implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			if(run==false){
				generation(block);
			}
		}
	}
	
	public class SC implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			run=false;
			r.setText("Start");
			for(int y=0;y<block.length;y++){
				for(int x=0;x<block[y].length;x++){
					block[y][x]=0;
				}
			}
		}
	}
	
	public void drawin(MouseEvent e,int m){
			try{
				double yy= (((double)block.length*((double)size/2)/(double)size)-((double)getHeight()/2/(double)size)+((double)e.getY()/(double)size));
				double xx= (((double)block[0].length*((double)size/2)/(double)size)-((double)getWidth()/2/(double)size)+((double)e.getX()/(double)size));
				int y= (int)(yy);
				int x= (int)(xx);
				//System.out.println("X: "+xx+" Y: "+yy);
				if(ay!=y || ax!=x){
					ay=y;
					ax=x;
					block[y][x]=m;
					//block[y][x] %= colors.length;
				}
			}catch(Exception ex){
			}
	}

	public class MListen2 implements MouseMotionListener{

		public void mouseDragged(MouseEvent e) {
			drawin(e,cl);
		}
		public void mouseMoved(MouseEvent e) {
		}
	}
	
	public class MListen implements MouseListener{
		public void mouseClicked(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
		public void mousePressed(MouseEvent e) {
			if(e.getButton()==1){
				cl=1;
				drawin(e,cl);
			}else if(e.getButton()==3){
				cl=0;
				drawin(e,cl);
			}
		}
		public void mouseReleased(MouseEvent e) {
		
		}

	}
	
}
