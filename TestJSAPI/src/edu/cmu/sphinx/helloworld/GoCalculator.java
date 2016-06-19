/*
 * Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */

package edu.cmu.sphinx.helloworld;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

/**
 * A simple HelloWorld demo showing a simple speech application built using
 * Sphinx-4. This application uses the Sphinx-4 endpointer, which automatically
 * segments incoming audio into utterances and silences.
 */
public class GoCalculator {

	private static boolean extraSign(String n1,String n2){
		return !n1.isEmpty() && !n2.isEmpty();
	}
	public static void main(String[] args) {

		Calculator ca = new Calculator();

		ConfigurationManager cm;

		if (args.length > 0) {
			cm = new ConfigurationManager(args[0]);
		} else {
			cm = new ConfigurationManager(
					GoCalculator.class.getResource("helloworld.config.xml"));
		}

		Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
		recognizer.allocate();

		// start the microphone or exit if the programm if this is not possible
		Microphone microphone = (Microphone) cm.lookup("microphone");
		if (!microphone.startRecording()) {
			System.out.println("Cannot start microphone.");
			recognizer.deallocate();
			System.exit(1);
		}

		System.out.println("Say:   One | Two | Three | Four | Five | Six ");

		// loop the recognition until the programm exits.
		String num1 = "";
		String num2 = "";
		int sign = 0; // 1__+, 2__-, 3__*, 4__/
		int n1 = 0;
		int n2 = 0;
		boolean twoNum = false;
		String temp="";
		
		while (true) {
			ca.changePanel();
			Result result = recognizer.recognize();
			String resultText = result.getBestFinalResultNoFiller();
			// ********************** + - * /
			// ***********************************
			
			if (resultText.equals("plus")) {
				ca.initialButtonCollor();
				ca.hitButton(15);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ca.initialButtonCollor();
				
					sign = 1;
					if (!num1.isEmpty()) {
						twoNum = true;
						temp = num1+"+";
						if(!temp.endsWith("+") ){
							temp = temp.substring(0, temp.length()-1)+"+";
						}
					}
			}
			if (resultText.equals("minus")) {
				ca.initialButtonCollor();
				ca.hitButton(11);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ca.initialButtonCollor();
				
				sign = 2;
				if (!num1.isEmpty()) {
					twoNum = true;
					temp = num1+"-";
					if(!temp.endsWith("-") ){
						temp = temp.substring(0, temp.length()-1)+"-";
					}
				}
			}
			if (resultText.equals("times")) {
				ca.initialButtonCollor();
				ca.hitButton(7);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ca.initialButtonCollor();
				sign = 3;
				if (!num1.isEmpty()) {
					twoNum = true;
					temp = num1+"*";
					if(!temp.endsWith("*") ){
						temp = temp.substring(0, temp.length()-1)+"*";
					}
				}
			}
			if (resultText.equals("divided by")) {
				ca.initialButtonCollor();
				ca.hitButton(3);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ca.initialButtonCollor();
				
				sign = 4;
				if (!num1.isEmpty()) {
					twoNum = true;
					temp = num1+"/";
					if(!temp.endsWith("/") ){
						temp = temp.substring(0, temp.length()-1)+"/";
					}
				}
			}
			// *****************************clear**********************************
			if (resultText.equals("clear")) {
				ca.initialButtonCollor();
				ca.hitButton(0);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ca.initialButtonCollor();
				
				num1 = "";
				num2 = "";
				sign = 0;
				n1 = 0;
				n2 = 0;
				twoNum = false;
				ca.textField.setText("");
				temp = "";
			}
			// **************************numbers**********************************
			if (!twoNum) {
				// if(input is a number)
				num1 = num1 + ca.letterToSign(resultText);
				ca.textField.setText(num1);
			} else {
				// if input is a number
				num2 = num2 + ca.letterToSign(resultText);
				ca.textField.setText(temp+num2);
			}

			// **************************euqals**********************************
			if (resultText.equals("equals")) {
				ca.initialButtonCollor();
				ca.hitButton(17);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ca.initialButtonCollor();
				if (twoNum && !num1.isEmpty() && !num2.isEmpty()) {
					n1 = Integer.parseInt(num1);
					n2 = Integer.parseInt(num2);

					switch (sign) {
					case 1:
						ca.textField.setText(num1 + "+" + num2 + "="
								+ (n1 + n2));
						break;
					case 2:
						ca.textField.setText(num1 + "-" + num2 + "="
								+ (n1 - n2));
						break;
					case 3:
						ca.textField.setText(num1 + "*" + num2 + "="
								+ (n1 * n2));
						break;
					case 4:
						if (n2 != 0) {
							ca.textField.setText(num1 + "/" + num2 + "="
									+ (float)((float)n1 /(float) n2));
							break;
						} else {
							ca.textField.setText("Math Error");
							break;
						}

					}
					num1 = "";
					num2 = "";
					sign = 0;
					n1 = 0;
					n2 = 0;
					twoNum = false;
					temp = "";
				} else {
					if(!num1.isEmpty() && sign==0){
						//just input one number but not a sign yet
					}else{
						ca.textField.setText("Syntax Error");
					}
					
				}
			}

			System.out.println("Start speaking. Press Ctrl-C to quit.\n");
			if (result != null) {
				resultText = result.getBestFinalResultNoFiller();
				System.out.println("You said: " + resultText + '\n');
			} else {
				System.out.println("I can't hear what you said.\n");
			}
		}
	}

}
