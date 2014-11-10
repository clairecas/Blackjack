package com.example.blackjack;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.Random;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;


public class MainActivity extends ActionBarActivity {
	
	//image slots for opponent's and player's cards
	private ImageView imageViewO1;
	private ImageView imageViewO2;
	private ImageView imageViewO3;
	private ImageView imageViewO4;
	private ImageView imageViewO5;
	private ImageView imageViewP1;
	private ImageView imageViewP2;
	private ImageView imageViewP3;
	private ImageView imageViewP4;
	private ImageView imageViewP5;
	
	private TextView textViewGameOver;
	private TextView textView3;
	private TextView textView4;
	private TextView textView5;
	
	//to randomize deck of cards
	private final static Random random = new Random();
	
	//holds values of players and opponents hand
	int oHand = 0;
	int pHand = 0;
	
	public int cardsDealt = 0;//keeps track of how deckOfCards index
	public final static int maxCardsDealt = 10;//each player gets dealt 5 cards - max
	public boolean gameOver = false;
	public boolean playersTurn = false;
	public boolean opponentsTurn = false;
	public boolean stand = false;
	int pCards = 0; //number of cards dealt to player
	int oCards = 0;//number of cards dealt to opponent
	
	//cards are ordered clubs, diamonds, hearts, spades
	//from 2 - ace 
	public final static int[] deckOfCards = new int[] { R.drawable.c0, 
		R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4,
		R.drawable.c5, R.drawable.c6, R.drawable.c7, R.drawable.c8,
		R.drawable.c9, R.drawable.c10, R.drawable.c11, R.drawable.c12,
		R.drawable.c13, R.drawable.c14, R.drawable.c15, R.drawable.c16,
		R.drawable.c17, R.drawable.c18, R.drawable.c19, R.drawable.c20,
		R.drawable.c21, R.drawable.c22, R.drawable.c23, R.drawable.c24,
		R.drawable.c25, R.drawable.c26, R.drawable.c27, R.drawable.c28, 
		R.drawable.c29, R.drawable.c30, R.drawable.c31, R.drawable.c32,
		R.drawable.c33, R.drawable.c34, R.drawable.c35, R.drawable.c36,
		R.drawable.c37, R.drawable.c38, R.drawable.c39, R.drawable.c40, 
		R.drawable.c41, R.drawable.c42, R.drawable.c43, R.drawable.c44,
		R.drawable.c45, R.drawable.c46, R.drawable.c47, R.drawable.c48,
		R.drawable.c49, R.drawable.c50, R.drawable.c51 	 
		};  


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
      //find card slots for opponent's and players hand
        imageViewO1 = (ImageView) findViewById(R.id.imageViewO1);
        imageViewO2 = (ImageView) findViewById(R.id.imageViewO2);
        imageViewO3 = (ImageView) findViewById(R.id.imageViewO3);
        imageViewO4 = (ImageView) findViewById(R.id.imageViewO4);
        imageViewO5 = (ImageView) findViewById(R.id.imageViewO5);
        imageViewP1 = (ImageView) findViewById(R.id.imageViewP1);
        imageViewP2 = (ImageView) findViewById(R.id.imageViewP2);
        imageViewP3 = (ImageView) findViewById(R.id.imageViewP3);
        imageViewP4 = (ImageView) findViewById(R.id.imageViewP4);
        imageViewP5 = (ImageView) findViewById(R.id.imageViewP5);
        
        //hides cards that haven't been dealt yet
        imageViewO3.setVisibility(View.INVISIBLE);
        imageViewO4.setVisibility(View.INVISIBLE);
        imageViewO5.setVisibility(View.INVISIBLE);
        imageViewP3.setVisibility(View.INVISIBLE);
        imageViewP4.setVisibility(View.INVISIBLE);
        imageViewP5.setVisibility(View.INVISIBLE);
        
        //buttons
        Button hitButton = (Button) findViewById(R.id.hitButton);
        Button standButton = (Button) findViewById(R.id.standButton);
        Button newGameButton = (Button) findViewById(R.id.newGameButton);
        
        hitButton.setOnClickListener(hitButtonListener);
        standButton.setOnClickListener(standButtonListener);
        newGameButton.setOnClickListener(newGameButtonListener);
        
        //textView
        textViewGameOver = (TextView) findViewById(R.id.textViewGameOver);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
            
        newGame();
    }
    
    public void shuffle(){
    	for (int i=0; i<52; i++){
    		int j = random.nextInt(52);
    		int temp = deckOfCards[i];
    		deckOfCards[i] = deckOfCards[j];
    		deckOfCards[j] = temp;
    	}
    }
    
    public void newGame() {
    	shuffle(); //shuffle the deck
    	
    	//deal cards to players and display card image
    	imageViewO1.setImageResource(R.drawable.back_blue);
    	imageViewO2.setImageResource(deckOfCards[1]);
    	imageViewP1.setImageResource(deckOfCards[2]);
    	imageViewP2.setImageResource(deckOfCards[3]);
    	
    	//hides cards that haven't been dealt yet
        imageViewO3.setVisibility(View.INVISIBLE);
        imageViewO4.setVisibility(View.INVISIBLE);
        imageViewO5.setVisibility(View.INVISIBLE);
        imageViewP3.setVisibility(View.INVISIBLE);
        imageViewP4.setVisibility(View.INVISIBLE);
        imageViewP5.setVisibility(View.INVISIBLE);
    	
    	//updates cards that have been dealt
    	cardsDealt = 4; 
    	pCards = 2;
    	oCards = 2;
    	pHand = 0;
    	oHand = 0;
    	
    	//calculates opponents hand
    	for (int i=0; i < 2; i++){
    		int temp = getCardInfo(i);
    		oHand = oHand + temp;
    	}	
    	//calculates players hand
    	for (int i=2; i < 4; i++){
    		int temp = getCardInfo(i);
    		pHand = pHand + temp;
    	}
    	
    	//checks game score
    	gameCheck();
    }
       
    
    //gets the value of each card that has been dealt
    public int getCardInfo(int i) {
    	//deckOfCards indexes for jack, queens, and kings
    	if (i==9 || i==10 || i==11 || i==22 || i==23 || i==24 
    			|| i>=35 || i<=36 || i==37 || 
    			i==48 || i==49 || i==50){
    		return 10;
    	}    	
    	//deckOfCards indexes for aces
    	else if (i==12 || i==25 || i==38 || i==51){
    		if((pHand+11) > 21 || (oHand+11) > 21){
    			return 1;
    		}
    		else
    			return 11;
    	} 	
    	
    	//number cards
    	else if(i==0 || i==13 || i==26 || i==39 ){
    		return 2;
    	}
    	else if(i==1 || i==14 || i==27 || i==40 ){
    		return 3;
    	}
    	else if(i==2 || i==15 || i==28 || i==41 ){
    		return 4;
    	}
    	else if(i==3 || i==16 || i==29 || i==42 ){
    		return 5;
    	}
    	else if(i==4 || i==17 || i==30 || i==43 ){
    		return 6;
    	}
    	else if(i==5 || i==18 || i==31 || i==44 ){
    		return 7;
    	}
    	else if(i==6 || i==19 || i==32 || i==45 ){
    		return 8;
    	}
    	else if(i==7 || i==20 || i==33 || i==46 ){
    		return 9;
    	}
    	else if(i==8 || i==21 || i==34 || i==47 ){
    		return 10;
    	}
    	
    	//value for number cards
    	/*else {
    		int temp = deckOfCards[i];
    		value = (temp%13) + 2;
    	}*/
    	return 0;
    }
    
    //display when player wins/lose
    public void gameCheck(){
    	//opponent wins
    	if (oHand == 21 || pHand > 21){
    		imageViewO1.setImageResource(deckOfCards[0]); //display opponents down-facing card
        	textViewGameOver.setText(R.string.lose);
    		gameOver = true;
    	}
    	//player wins
    	else if (pHand == 21 || oHand > 21){
    		imageViewO1.setImageResource(deckOfCards[0]); //display opponents down-facing card
    		textViewGameOver.setText(R.string.win);
    		gameOver = true;
    	}
    	//player chooses to stand
    	else if(stand){
    		//opponent continues to draw cards with a 15 point max risk
    		while (oCards <= 5 && oHand < 15){
    			dealerLogic();
    		} 
    		
    		imageViewO1.setImageResource(deckOfCards[0]);//display opponents down-facing card
    		if (oHand > pHand){
    			textViewGameOver.setText(R.string.lose);
    		}
    		else if (pHand > oHand){
    			textViewGameOver.setText(R.string.win);
    		}
    		else
    			textViewGameOver.setText(R.string.tie);
    	}
    	else if(opponentsTurn){ //opponents turn
    		if (oHand < 15) //opponent risks drawing another card
    			dealerLogic();
    	}
    	
    	//This code is to troubleshoot values ******************************
    	String text = String.valueOf(pHand);
    	textView5.setText("pHand= " + text);//value of the players 1st card + 2nd card
    	String text2 = String.valueOf(getCardInfo(2));
    	textView3.setText("pcard 1=" + text2 + " ");//value of the players 1st card
    	String text3 = String.valueOf(getCardInfo(3));
    	textView4.setText("pcard 2=" + text3 + " ");//value of the players 2nd card
    	
    }
    
    public OnClickListener hitButtonListener = new OnClickListener(){
    	@Override
    	public void onClick(View v){  	
    		playersTurn = true;
    		pCards++;
    		dealerLogic();
    	}

    };
    
    public OnClickListener standButtonListener = new OnClickListener(){
    	@Override
    	public void onClick(View v){
    		stand = true;  
    		gameCheck();
    	}

    };
    
    public OnClickListener newGameButtonListener = new OnClickListener(){
    	@Override
    	public void onClick(View v){
    		newGame();
    	}

    };
    
    public void dealerLogic(){
    	//if the player draws a card
    	if (playersTurn){
    		if (pCards==3){ //players 3rd card
    			imageViewP3.setImageResource(deckOfCards[cardsDealt]);
    			imageViewP3.setVisibility(View.VISIBLE);
    			getCardInfo(cardsDealt);
    			//pCards++;
    	    	cardsDealt++;
    			gameCheck();
    			playersTurn = false;
    	    	opponentsTurn = true;
    		}
    		else if (pCards==4){ //players 4th card
    			imageViewP4.setImageResource(deckOfCards[cardsDealt]);
    			imageViewP4.setVisibility(View.VISIBLE);
    			getCardInfo(cardsDealt);
    			//pCards++;
    	    	cardsDealt++;
    			gameCheck();
    			playersTurn = false;
    	    	opponentsTurn = true;
    		}
    		else if (pCards==5){ //players 5th card
    			imageViewP5.setImageResource(deckOfCards[cardsDealt]);
    			imageViewP5.setVisibility(View.VISIBLE);
    			getCardInfo(cardsDealt);
    			//pCards++;
    	    	cardsDealt++;
    			gameCheck(); 
    			playersTurn = false;
    	    	opponentsTurn = true;
    		}
    	}
    	//opponents turn
    	else if(opponentsTurn){
    		if (oCards==3){ //opponents 3rd card
    			imageViewO3.setImageResource(deckOfCards[cardsDealt]);
    			imageViewO3.setVisibility(View.VISIBLE);
    			getCardInfo(cardsDealt);
    			cardsDealt++;
    			oCards++;
    			gameCheck();
    		}
    		else if (oCards==4){ //opponents 4th card
    			imageViewO4.setImageResource(deckOfCards[cardsDealt]);
    			imageViewO4.setVisibility(View.VISIBLE);
    			getCardInfo(cardsDealt);
    			cardsDealt++;
    			oCards++;
    			gameCheck();
    		}
    		else if (oCards==5){ //opponents 5th card
    			imageViewO5.setImageResource(deckOfCards[cardsDealt]);
    			imageViewO5.setVisibility(View.VISIBLE);
    			getCardInfo(cardsDealt);
    			cardsDealt++;
    			oCards++;
    			gameCheck();
    		}
    	}
    	if (pCards == 5 || oCards == 5)
    		gameCheck();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
