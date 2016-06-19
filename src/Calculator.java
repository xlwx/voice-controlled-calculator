package edu.cmu.sphinx.helloworld;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class Calculator extends JFrame {
	JPanel p1 = new JPanel();// textField
	JPanel p2 = new JPanel();// radioButton
	// p3,p4 are all for keyBoard
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();
	//p5 is for result
	JScrollPane p5 = new JScrollPane(); 

	Calculator() {
		textFieldInitial();
		radioButtonInitial();
		keyBoardInitial();
		resultBoardInitial();
		setLayout(new FlowLayout());
		setSize(300, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		
	}
	/*
	 * TextField
	 * */
	public JTextField textField = new JTextField();
	private void textFieldInitial() {
		textField.setEditable(false);
		textField.setPreferredSize(new Dimension(280, 40));
		p1.add(textField);
	}

	/*
	 * RadioButton
	 * */
	JRadioButton jrb1 = new JRadioButton("keybord",true);
	JRadioButton jrb2 = new JRadioButton("result");
	private void radioButtonInitial() {
		p2.setLayout(new GridLayout(1, 2));
		p2.add(jrb1);
		p2.add(jrb2);
		ButtonGroup bp = new ButtonGroup();
		bp.add(jrb1);
		bp.add(jrb2);
	}
	/*
	 * Button
	 * */
	public JButton[] jb = new JButton[18];
	private void keyBoardInitial() {
		p3.setLayout(new GridLayout(4, 4));
		p4.setLayout(new GridLayout(1, 2));
		p3.setPreferredSize(new Dimension(300,200));
		p4.setPreferredSize(new Dimension(300,50));
	
		
		jb[0] = new JButton("C");
		jb[1] = new JButton("+/-");
		jb[2] = new JButton("%");
		jb[3] = new JButton("¡Â");

		jb[4] = new JButton("7");
		jb[5] = new JButton("8");
		jb[6] = new JButton("9");
		jb[7] = new JButton("X");

		jb[8] = new JButton("4");
		jb[9] = new JButton("5");
		jb[10] = new JButton("6");
		jb[11] = new JButton("-");

		jb[12] = new JButton("1");
		jb[13] = new JButton("2");
		jb[14] = new JButton("3");
		jb[15] = new JButton("+");

		jb[16] = new JButton("0");
		jb[17] = new JButton("=");

		for (int i = 0; i < 16; i++) {
			p3.add(jb[i]);
		}
		p4.add(jb[16]);
		p4.add(jb[17]);

	}
	/*
	 * Result
	 * */
	public JTextArea text = new JTextArea(300,200);

	private void resultBoardInitial(){
		p5.add(text);
		p5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		p5.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		text.setLineWrap(true);
		text.append("Hello");
	} 
	
	public void initialButtonCollor(){
		for(int i=0;i<18;i++){
			jb[i].setForeground(java.awt.Color.BLACK);
			jb[i].setBackground(UIManager.getColor("Button.background"));
		}
	}
	
	public void hitButton(int i){
		jb[i].setForeground(java.awt.Color.WHITE);
		jb[i].setBackground(java.awt.Color.RED);
	}
	
	public String letterToSign(String s){
			initialButtonCollor();
	    	String temp="";
	    	if(s.equals("one")){
	    		temp="1";
	    		hitButton(12);
	    	}else if(s.equals("two")){
	    		temp="2";
	    		hitButton(13);
	    	}else if(s.equals("three")){
	    		temp="3";
	    		hitButton(14);
	    	}else if(s.equals("four")){
	    		temp="4";
	    		hitButton(8);
	    	}else if(s.equals("five")){
	    		temp="5";
	    		hitButton(9);
	    	}else if(s.equals("six")){
	    		temp="6";
	    		hitButton(10);
	    	}else if(s.equals("seven")){
	    		temp="7";
	    		hitButton(4);
	    	}else if(s.equals("eight")){
	    		temp="8";
	    		hitButton(5);
	    	}else if(s.equals("nine")){
	    		temp="9";
	    		hitButton(6);
	    	}else if(s.equals("zero")){
	    		temp="0";
	    		hitButton(16);
	    	}else{
	    		temp="";
	    	}
	    	return temp;
	    }

	public void changePanel(){
		if(jrb1.isSelected()){
			add(p3);
			add(p4);
			remove(p5);
			repaint();
		}else{
			add(p5);
			remove(p3);
			remove(p4);
			repaint();
		}
	}
	
}
