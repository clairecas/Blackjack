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

import com.example.blackjack.R.id;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;


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
	
	//image slots for betting chips
	private ImageView oChip1;
	private ImageView oChip2;
	private ImageView oChip3;
	private ImageView oChip4;
	private ImageView oChip5;
	private ImageView oChip6;
	private ImageView oChip7;
	private ImageView oChip8;
	private ImageView oChip9;
	private ImageView oChip10;
	private ImageView pChip1;
	private ImageView pChip2;
	private ImageView pChip3;
	private ImageView pChip4;
	private ImageView pChip5;
	private ImageView pChip6;
	private ImageView pChip7;
	private ImageView pChip8;
	private ImageView pChip9;
	private ImageView pChip10;
	
	private TextView textViewGameOver;
	private TextView oMoneyView;
	private TextView pMoneyView;
	
	//to randomize deck of cards
	private final static Random random = new Random();
	
	//holds values of players and opponents hand
	int oHand = 0;
	int pHand = 0;
	
	public int cardsDealt = 0;//keeps track of how deckOfCards index
	public boolean gameOver = false;
	public boolean playersTurn = false;
	//public boolean opponentsTurn = false;
	public boolean stand = false;
	int pCards = 0; //number of cards dealt to player
	int oCards = 0;//number of cards dealt to opponent
	int pBet = 1000;//players initial betting amount	
	int oBet = 1000;//opponents initial betting amount
	public boolean outOfMoney = false;
	public boolean canDoubleDown = false;//player can only double down after first two cards are dealt
	public int playerAces = 0; 
	public int opponentAces = 0;
	
	
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
        
        //find image slots for chips
        oChip1 = (ImageView) findViewById(R.id.oChip1);
        oChip2 = (ImageView) findViewById(R.id.oChip2);
        oChip3 = (ImageView) findViewById(R.id.oChip3);
        oChip4 = (ImageView) findViewById(R.id.oChip4);
        oChip5 = (ImageView) findViewById(R.id.oChip5);
        oChip6 = (ImageView) findViewById(R.id.oChip6);
        oChip7 = (ImageView) findViewById(R.id.oChip7);
        oChip8 = (ImageView) findViewById(R.id.oChip8);
        oChip9 = (ImageView) findViewById(R.id.oChip9);
        oChip10 = (ImageView) findViewById(R.id.oChip10);
        pChip1 = (ImageView) findViewById(R.id.pChip1);
        pChip2 = (ImageView) findViewById(R.id.pChip2);
        pChip3 = (ImageView) findViewById(R.id.pChip3);
        pChip4 = (ImageView) findViewById(R.id.pChip4);
        pChip5 = (ImageView) findViewById(R.id.pChip5);
        pChip6 = (ImageView) findViewById(R.id.pChip6);
        pChip7 = (ImageView) findViewById(R.id.pChip7);
        pChip8 = (ImageView) findViewById(R.id.pChip8);
        pChip9 = (ImageView) findViewById(R.id.pChip9);
        pChip10 = (ImageView) findViewById(R.id.pChip10);
        
        //hides cards that haven't been dealt yet
        imageViewO3.setVisibility(View.INVISIBLE);
        imageViewO4.setVisibility(View.INVISIBLE);
        imageViewO5.setVisibility(View.INVISIBLE);
        imageViewP3.setVisibility(View.INVISIBLE);
        imageViewP4.setVisibility(View.INVISIBLE);
        imageViewP5.setVisibility(View.INVISIBLE);
        
        //hide chips that haven't been bet on yet
        oChip2.setVisibility(View.INVISIBLE);
        oChip3.setVisibility(View.INVISIBLE);
        oChip4.setVisibility(View.INVISIBLE);
        oChip5.setVisibility(View.INVISIBLE);
        oChip6.setVisibility(View.INVISIBLE);
        oChip7.setVisibility(View.INVISIBLE);
        oChip8.setVisibility(View.INVISIBLE);
        oChip9.setVisibility(View.INVISIBLE);
        oChip10.setVisibility(View.INVISIBLE);
        pChip2.setVisibility(View.INVISIBLE);
        pChip3.setVisibility(View.INVISIBLE);
        pChip4.setVisibility(View.INVISIBLE);
        pChip5.setVisibility(View.INVISIBLE);
        pChip6.setVisibility(View.INVISIBLE);
        pChip7.setVisibility(View.INVISIBLE);
        pChip8.setVisibility(View.INVISIBLE);
        pChip9.setVisibility(View.INVISIBLE);
        pChip10.setVisibility(View.INVISIBLE);
        
        //buttons
        Button hitButton = (Button) findViewById(R.id.hitButton);
        Button standButton = (Button) findViewById(R.id.standButton);
        Button newGameButton = (Button) findViewById(R.id.newGameButton);
        Button doubleDownButton = (Button) findViewById(R.id.doubleDownButton);
        Button surrenderButton = (Button) findViewById(R.id.surrenderButton);
        Button saveGameButton = (Button) findViewById(R.id.saveGameButton);
        Button loadGameButton = (Button) findViewById(R.id.loadGameButton);
        
        hitButton.setOnClickListener(hitButtonListener);
        standButton.setOnClickListener(standButtonListener);
        newGameButton.setOnClickListener(newGameButtonListener);
        doubleDownButton.setOnClickListener(doubleDownButtonListener);
        surrenderButton.setOnClickListener(surrenderButtonListener);
        saveGameButton.setOnClickListener(saveGameButtonListener);
        loadGameButton.setOnClickListener(loadGameButtonListener);
        
        //textView
        textViewGameOver = (TextView) findViewById(R.id.textViewGameOver);
        oMoneyView = (TextView) findViewById(R.id.oMoneyView);
        pMoneyView = (TextView) findViewById(R.id.pMoneyView);
        
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
    	imageViewO2.setImageResource(deckOfCards[1]); //2nd card in deckOfCards
    	imageViewP1.setImageResource(deckOfCards[2]); //3rd card in deckOfCards
    	imageViewP2.setImageResource(deckOfCards[3]); //4th card in deckOfCards
    	
    	//hides cards that haven't been dealt yet
        imageViewO3.setVisibility(View.INVISIBLE);
        imageViewO4.setVisibility(View.INVISIBLE);
        imageViewO5.setVisibility(View.INVISIBLE);
        imageViewP3.setVisibility(View.INVISIBLE);
        imageViewP4.setVisibility(View.INVISIBLE);
        imageViewP5.setVisibility(View.INVISIBLE);
    	
        //holds values of players and opponents hand
    	oHand = 0;
    	pHand = 0;
    	
    	cardsDealt = 4;//keeps track of how deckOfCards index
    	gameOver = false;
    	playersTurn = false;
    	canDoubleDown = true;
    	stand = false;
    	pCards = 2; //number of cards dealt to player
    	oCards = 2;//number of cards dealt to opponent
    	playerAces = 0;
    	opponentAces = 0;
    	
    	//clears the betting amount
    	if (outOfMoney){
    		pBet = 1000;
    		oBet = 1000;
    		outOfMoney = false;
    		
    		//hide chips that haven't been bet on yet
            oChip2.setVisibility(View.INVISIBLE);
            oChip3.setVisibility(View.INVISIBLE);
            oChip4.setVisibility(View.INVISIBLE);
            oChip5.setVisibility(View.INVISIBLE);
            oChip6.setVisibility(View.INVISIBLE);
            oChip7.setVisibility(View.INVISIBLE);
            oChip8.setVisibility(View.INVISIBLE);
            oChip9.setVisibility(View.INVISIBLE);
            oChip10.setVisibility(View.INVISIBLE);
            pChip2.setVisibility(View.INVISIBLE);
            pChip3.setVisibility(View.INVISIBLE);
            pChip4.setVisibility(View.INVISIBLE);
            pChip5.setVisibility(View.INVISIBLE);
            pChip6.setVisibility(View.INVISIBLE);
            pChip7.setVisibility(View.INVISIBLE);
            pChip8.setVisibility(View.INVISIBLE);
            pChip9.setVisibility(View.INVISIBLE);
            pChip10.setVisibility(View.INVISIBLE);
    	}
    	
    	textViewGameOver.setText("New Game Started");
    	
    	//calculates opponents hand
    	for (int i=0; i < 2; i++){
    		int temp = getCardInfo(deckOfCards[i]);
    		oHand = oHand + temp;
    	}	
    	//calculates players hand
    	for (int i=2; i < 4; i++){
    		playersTurn = true;
    		int temp = getCardInfo(deckOfCards[i]);
    		pHand = pHand + temp;
    	}
    	
    	//checks game score
    	gameCheck();
    }
       
    
    //gets the value of each card that has been dealt
    public int getCardInfo(int i) {
    	//deckOfCards indexes for jack, queens, and kings
    	if (i==R.drawable.c9 || i==R.drawable.c10 || 
    			i==R.drawable.c11 || i==R.drawable.c22 || 
    			i==R.drawable.c23 || i==R.drawable.c24 || 
    			i==R.drawable.c35 || i==R.drawable.c36 || 
    			i==R.drawable.c37 || i==R.drawable.c48 || 
    			i==R.drawable.c49 || i==R.drawable.c50){
    		return 10;
    	}    	
    	//number cards
    	else if(i==R.drawable.c0 || i==R.drawable.c13 || 
    			i==R.drawable.c26 || i==R.drawable.c39 ){
    		return 2;
    	}
    	else if(i==R.drawable.c1 || i==R.drawable.c14 || 
    			i==R.drawable.c27 || i==R.drawable.c40 ){
    		return 3;
    	}
    	else if(i==R.drawable.c2 || i==R.drawable.c15 || 
    			i==R.drawable.c28 || i==R.drawable.c41 ){
    		return 4;
    	}
    	else if(i==R.drawable.c3 || i==R.drawable.c16 || 
    			i==R.drawable.c29 || i==R.drawable.c42 ){
    		return 5;
    	}
    	else if(i==R.drawable.c4 || i==R.drawable.c17 || 
    			i==R.drawable.c30 || i==R.drawable.c43 ){
    		return 6;
    	}
    	else if(i==R.drawable.c5 || i==R.drawable.c18 || 
    			i==R.drawable.c31 || i==R.drawable.c44 ){
    		return 7;
    	}
    	else if(i==R.drawable.c6 || i==R.drawable.c19 || 
    			i==R.drawable.c32 || i==R.drawable.c45 ){
    		return 8;
    	}
    	else if(i==R.drawable.c7 || i==R.drawable.c20 || 
    			i==R.drawable.c33 || i==R.drawable.c46 ){
    		return 9;
    	}
    	else if(i==R.drawable.c8 || i==R.drawable.c21 || 
    			i==R.drawable.c34 || i==R.drawable.c47 ){
    		return 10;
    	}    	 
    	//checks ace
    	else if (i==R.drawable.c12 || i==R.drawable.c25 || 
    			i==R.drawable.c38 || i==R.drawable.c51){
    		//keeps track of how many aces each player has in hand
    		if (playersTurn)
    			playerAces =+ 1;
    		else
    			opponentAces =+ 1;
    		
    		//default to return 11 for aces
    		return 11;
    	}
	
    	return 0; //invalid index
    }
    
    //display when player wins/lose
    public void gameCheck(){
    	//change aces to 1 if the hand is over 21
    	if (playerAces > 0 && pHand > 21)
    		pHand = pHand - 10;
    	else if (opponentAces > 0 && oHand > 21)
    		oHand = oHand - 10;
    	
    	//opponent wins
    	if (oHand == 21 || pHand > 21){
    		imageViewO1.setImageResource(deckOfCards[0]); //display opponents down-facing card
        	textViewGameOver.setText(R.string.lose);
    		gameOver = true;
    		canDoubleDown = false;
    		pBet = pBet - 1000;
    		oBet = oBet - 1000;
    	}
    	
    	//player wins
    	else if (pHand == 21 || oHand > 21){
    		imageViewO1.setImageResource(deckOfCards[0]); //display opponents down-facing card
    		textViewGameOver.setText(R.string.win);
    		gameOver = true;
    		canDoubleDown = false;
    		pBet = pBet + 1000;
    		oBet = oBet - 1000;
    	}
    	
    	//opponents turn
    	else if(!playersTurn){ 
    		while (oHand < 15){ //opponent risks drawing another card
    			oCards++;
    			dealerLogic();
    		}
    	}
    	
    	chipDisplay(); //updates chips that are shown
    	
    	//display bet amount
    	oMoneyView.setText(String.valueOf(oBet));
    	pMoneyView.setText(String.valueOf(pBet));
    	
    	//if player or opponents runs out of money
    	if (pBet < 0){
    		outOfMoney = true;
    		textViewGameOver.setText("You are out of money!");
    	}
    	else if (oBet < 0){
    		outOfMoney = true;
    		textViewGameOver.setText("Opponent is out of money!");
    	}
    	
    	//max number of wins
    	if (oBet >= 10000 || pBet >= 10000){
    		outOfMoney = true;
    		textViewGameOver.setText("Max wins have been reached");
    	}
    	  	
    }
    
    public OnClickListener hitButtonListener = new OnClickListener(){
    	@Override
    	public void onClick(View v){  
    		if (gameOver){
    			//if the game is over this button cannot 
    			//do anything until a new game is started
    		}
    		else {
	    		playersTurn = true;
	    		pCards++;
	    		canDoubleDown = false;
	    		dealerLogic();
    		}
    	}

    };
    
    public OnClickListener standButtonListener = new OnClickListener(){
    	@Override
    	public void onClick(View v){
    		if (gameOver){
    			//if the game is over this button cannot 
    			//do anything until a new game is started
    		}
    		else {
	    		stand = true;  
	    		canDoubleDown = false;
	    		//gameCheck();
	        	//opponent continues to draw cards with a 15 point max risk
	        	while (oCards <= 5 && oHand < 15){
	        		playersTurn = false;
	        		oCards++;
	        		dealerLogic();
	        	} 		
	        	imageViewO1.setImageResource(deckOfCards[0]);//display opponents down-facing card
	       		gameOver = true;
	       		if (oHand > pHand){
	       			textViewGameOver.setText(R.string.lose);
	       			oBet = oBet + 1000;
	       			pBet = pBet - 1000;
	       		}
	       		else if (pHand > oHand){
	       			textViewGameOver.setText(R.string.win);
	        		pBet = pBet + 1000;
	       			oBet = oBet - 1000;
	       		}
	        	else
	       			textViewGameOver.setText(R.string.tie);
	       		
	       		//display bet amount
	        	oMoneyView.setText(String.valueOf(oBet));
	        	pMoneyView.setText(String.valueOf(pBet));
	       	}
    	}

    };
    
    public OnClickListener newGameButtonListener = new OnClickListener(){
    	@Override
    	public void onClick(View v){
    		gameOver = true;
    		newGame();
    	}

    };
    
    public OnClickListener doubleDownButtonListener = new OnClickListener(){
    	@Override
    	public void onClick(View v){
    		if (gameOver){
    			//if the game is over this button cannot 
    			//do anything until a new game is started
    		}
    		else if (canDoubleDown){
    			//doubles bet after first 2 cards and is dealt 1 more card	
    			
    			//deals one more card to player
    			int temp = 0;
    			imageViewP3.setImageResource(deckOfCards[cardsDealt]);
    			imageViewP3.setVisibility(View.VISIBLE);
    			temp = getCardInfo(deckOfCards[cardsDealt]);
    			pHand = pHand + temp;
    			
    			if (pHand > oHand && pHand <= 21){
    				imageViewO1.setImageResource(deckOfCards[0]); //display opponents down-facing card
    	    		textViewGameOver.setText(R.string.win);
    				oBet = oBet - 2000;
    				pBet = pBet + 2000;
    			}
    			else {
    				imageViewO1.setImageResource(deckOfCards[0]); //display opponents down-facing card
    	    		textViewGameOver.setText(R.string.lose);
    				oBet = oBet + 2000;
    				pBet = pBet - 2000;
    			}
    			chipDisplay();
    			
    			//display bet amount
    	    	oMoneyView.setText(String.valueOf(oBet));
    	    	pMoneyView.setText(String.valueOf(pBet));
    			gameOver = true;
    		}
    		else {
    			//the player cannot double down because 
    			//a card has already been drawn
    		}
    	}
    };
    
    public OnClickListener surrenderButtonListener = new OnClickListener(){
    	@Override
    	public void onClick(View v){
    		if (gameOver){
    			//if the game is over this button cannot 
    			//do anything until a new game is started
    		}
    		else {
    			//surrender and lose $500 instead of $1000
    			pBet = pBet - 500;
    			oBet = oBet + 500;
    			textViewGameOver.setText("Player surrendered $500");
    			chipDisplay();
    			
    			//display bet amount
    	    	String o = String.valueOf(oBet);
    	    	String p = String.valueOf(pBet);
    	    	oMoneyView.setText(o);
    	    	pMoneyView.setText(p);
    		}
    	}
    };
    
    public OnClickListener saveGameButtonListener = new OnClickListener(){
    	@Override
    	public void onClick(View v){
    		//sharedPreference file for saving bet amounts in game
    		SharedPreferences pref = getApplicationContext().getSharedPreferences("PREF", 0);
    		SharedPreferences.Editor editor = pref.edit();
    		editor.putInt("oPref", oBet); //push the bet amount of oBet to be saved
    		editor.putInt("pPref", pBet); // push the bet amount of pBet to be saved
    		editor.apply(); //apply the save
    		
    		textViewGameOver.setText("Game Saved");
    	}

    };
    
    public OnClickListener loadGameButtonListener = new OnClickListener(){
    	@Override
    	public void onClick(View v){
    		//sharedPreference file for saving bet amounts in game
    		SharedPreferences pref = getApplicationContext().getSharedPreferences("PREF", 0);
    		//load data
    		oBet = pref.getInt("oPref", 1000); //oPref is key that holds stored value, 1000 is default if nothing is stored there
    		pBet = pref.getInt("pPref", 1000);
    		SharedPreferences.Editor editor = pref.edit();
    		editor.clear(); //clear the data
    		editor.commit();
    		
        	oMoneyView.setText(String.valueOf(oBet));
        	pMoneyView.setText(String.valueOf(pBet));
        	textViewGameOver.setText("Game Loaded");
        	
        	chipDisplay();
    	}
    };
    
    public void dealerLogic(){
    	//if the player draws a card
    	if (playersTurn && pCards <= 5){
    		int temp = 0;
    		if (pCards==3){ //players 3rd card
    			imageViewP3.setImageResource(deckOfCards[cardsDealt]);
    			imageViewP3.setVisibility(View.VISIBLE);
    			temp = getCardInfo(deckOfCards[cardsDealt]);
    		}
    		else if (pCards==4){ //players 4th card
    			imageViewP4.setImageResource(deckOfCards[cardsDealt]);
    			imageViewP4.setVisibility(View.VISIBLE);
    			temp = getCardInfo(deckOfCards[cardsDealt]);
    		}
    		else if (pCards==5){ //players 5th card
    			imageViewP5.setImageResource(deckOfCards[cardsDealt]);
    			imageViewP5.setVisibility(View.VISIBLE);
    			temp = getCardInfo(deckOfCards[cardsDealt]);		
    		}
    		pHand = pHand + temp;
	    	cardsDealt++;
	    	playersTurn = false;
			gameCheck(); 
    	}
    	//opponents turn
    	else if(!playersTurn && oCards <= 5){
    		int temp = 0;
    		if (oCards==3){ //opponents 3rd card
    			imageViewO3.setImageResource(deckOfCards[cardsDealt]);
    			imageViewO3.setVisibility(View.VISIBLE);
    			temp = getCardInfo(deckOfCards[cardsDealt]);
    		}
    		else if (oCards==4){ //opponents 4th card
    			imageViewO4.setImageResource(deckOfCards[cardsDealt]);
    			imageViewO4.setVisibility(View.VISIBLE);
    			temp = getCardInfo(deckOfCards[cardsDealt]);
    		}
    		else if (oCards==5){ //opponents 5th card
    			imageViewO5.setImageResource(deckOfCards[cardsDealt]);
    			imageViewO5.setVisibility(View.VISIBLE);
    			temp = getCardInfo(deckOfCards[cardsDealt]);			
    		}
    		oHand = oHand + temp;
			cardsDealt++;
			playersTurn = true;
			gameCheck();
    	}
    	if (pCards > 5 || oCards > 5)
    		gameOver = true;
    		gameCheck();
    }
    
    public void chipDisplay(){
    	if (pBet >= 10000){
    		pChip1.setVisibility(View.VISIBLE);
    		pChip2.setVisibility(View.VISIBLE);
            pChip3.setVisibility(View.VISIBLE);
            pChip4.setVisibility(View.VISIBLE);
            pChip5.setVisibility(View.VISIBLE);
            pChip6.setVisibility(View.VISIBLE);
            pChip7.setVisibility(View.VISIBLE);
            pChip8.setVisibility(View.VISIBLE);
            pChip9.setVisibility(View.VISIBLE);
            pChip10.setVisibility(View.VISIBLE);
    	}
    	else if(pBet <= 9000 && pBet > 8000){
    		pChip1.setVisibility(View.VISIBLE);
    		pChip2.setVisibility(View.VISIBLE);
            pChip3.setVisibility(View.VISIBLE);
            pChip4.setVisibility(View.VISIBLE);
            pChip5.setVisibility(View.VISIBLE);
            pChip6.setVisibility(View.VISIBLE);
            pChip7.setVisibility(View.VISIBLE);
            pChip8.setVisibility(View.VISIBLE);
            pChip9.setVisibility(View.VISIBLE);
            pChip10.setVisibility(View.INVISIBLE);
    	}
    	else if(pBet <= 8000 && pBet > 7000){
    		pChip1.setVisibility(View.VISIBLE);
    		pChip2.setVisibility(View.VISIBLE);
            pChip3.setVisibility(View.VISIBLE);
            pChip4.setVisibility(View.VISIBLE);
            pChip5.setVisibility(View.VISIBLE);
            pChip6.setVisibility(View.VISIBLE);
            pChip7.setVisibility(View.VISIBLE);
            pChip8.setVisibility(View.VISIBLE);
            pChip9.setVisibility(View.INVISIBLE);
            pChip10.setVisibility(View.INVISIBLE);
    	}
    	else if(pBet <= 7000 && pBet > 6000){
    		pChip1.setVisibility(View.VISIBLE);
    		pChip2.setVisibility(View.VISIBLE);
            pChip3.setVisibility(View.VISIBLE);
            pChip4.setVisibility(View.VISIBLE);
            pChip5.setVisibility(View.VISIBLE);
            pChip6.setVisibility(View.VISIBLE);
            pChip7.setVisibility(View.VISIBLE);
            pChip8.setVisibility(View.INVISIBLE);
            pChip9.setVisibility(View.INVISIBLE);
            pChip10.setVisibility(View.INVISIBLE);
    	}
    	else if(pBet <= 6000 && pBet > 5000){
    		pChip1.setVisibility(View.VISIBLE);
    		pChip2.setVisibility(View.VISIBLE);
            pChip3.setVisibility(View.VISIBLE);
            pChip4.setVisibility(View.VISIBLE);
            pChip5.setVisibility(View.VISIBLE);
            pChip6.setVisibility(View.VISIBLE);
            pChip7.setVisibility(View.INVISIBLE);
            pChip8.setVisibility(View.INVISIBLE);
            pChip9.setVisibility(View.INVISIBLE);
            pChip10.setVisibility(View.INVISIBLE);
    	}
    	else if(pBet <= 5000 && pBet > 4000){
    		pChip1.setVisibility(View.VISIBLE);
    		pChip2.setVisibility(View.VISIBLE);
            pChip3.setVisibility(View.VISIBLE);
            pChip4.setVisibility(View.VISIBLE);
            pChip5.setVisibility(View.VISIBLE);
            pChip6.setVisibility(View.INVISIBLE);
            pChip7.setVisibility(View.INVISIBLE);
            pChip8.setVisibility(View.INVISIBLE);
            pChip9.setVisibility(View.INVISIBLE);
            pChip10.setVisibility(View.INVISIBLE);
    	}
    	else if(pBet <= 4000 && pBet > 3000){
    		pChip1.setVisibility(View.VISIBLE);
    		pChip2.setVisibility(View.VISIBLE);
            pChip3.setVisibility(View.VISIBLE);
            pChip4.setVisibility(View.VISIBLE);
            pChip5.setVisibility(View.INVISIBLE);
            pChip6.setVisibility(View.INVISIBLE);
            pChip7.setVisibility(View.INVISIBLE);
            pChip8.setVisibility(View.INVISIBLE);
            pChip9.setVisibility(View.INVISIBLE);
            pChip10.setVisibility(View.INVISIBLE);
    	}
    	else if(pBet <= 3000 && pBet > 2000){
    		pChip1.setVisibility(View.VISIBLE);
    		pChip2.setVisibility(View.VISIBLE);
            pChip3.setVisibility(View.VISIBLE);
            pChip4.setVisibility(View.INVISIBLE);
            pChip5.setVisibility(View.INVISIBLE);
            pChip6.setVisibility(View.INVISIBLE);
            pChip7.setVisibility(View.INVISIBLE);
            pChip8.setVisibility(View.INVISIBLE);
            pChip9.setVisibility(View.INVISIBLE);
            pChip10.setVisibility(View.INVISIBLE);
    	}
    	else if(pBet <= 2000 && pBet > 1000){
    		pChip1.setVisibility(View.VISIBLE);
    		pChip2.setVisibility(View.VISIBLE);
            pChip3.setVisibility(View.INVISIBLE);
            pChip4.setVisibility(View.INVISIBLE);
            pChip5.setVisibility(View.INVISIBLE);
            pChip6.setVisibility(View.INVISIBLE);
            pChip7.setVisibility(View.INVISIBLE);
            pChip8.setVisibility(View.INVISIBLE);
            pChip9.setVisibility(View.INVISIBLE);
            pChip10.setVisibility(View.INVISIBLE);
    	}
    	else if(pBet == 1000 && pBet > 0){
    		pChip1.setVisibility(View.VISIBLE);
    		pChip2.setVisibility(View.INVISIBLE);
            pChip3.setVisibility(View.INVISIBLE);
            pChip4.setVisibility(View.INVISIBLE);
            pChip5.setVisibility(View.INVISIBLE);
            pChip6.setVisibility(View.INVISIBLE);
            pChip7.setVisibility(View.INVISIBLE);
            pChip8.setVisibility(View.INVISIBLE);
            pChip9.setVisibility(View.INVISIBLE);
            pChip10.setVisibility(View.INVISIBLE);
    	}
    	else if(pBet <= 0){
    		pChip1.setVisibility(View.INVISIBLE);
    		pChip2.setVisibility(View.INVISIBLE);
            pChip3.setVisibility(View.INVISIBLE);
            pChip4.setVisibility(View.INVISIBLE);
            pChip5.setVisibility(View.INVISIBLE);
            pChip6.setVisibility(View.INVISIBLE);
            pChip7.setVisibility(View.INVISIBLE);
            pChip8.setVisibility(View.INVISIBLE);
            pChip9.setVisibility(View.INVISIBLE);
            pChip10.setVisibility(View.INVISIBLE);
    	}
    	else{
    		//pWBet error
    	}
    		
    	if (oBet >= 10000){
        	oChip1.setVisibility(View.VISIBLE);
        	oChip2.setVisibility(View.VISIBLE);
            oChip3.setVisibility(View.VISIBLE);
            oChip4.setVisibility(View.VISIBLE);
            oChip5.setVisibility(View.VISIBLE);
            oChip6.setVisibility(View.VISIBLE);
            oChip7.setVisibility(View.VISIBLE);
            oChip8.setVisibility(View.VISIBLE);
            oChip9.setVisibility(View.VISIBLE);
            oChip10.setVisibility(View.VISIBLE);
        }	
        else if(oBet <= 9000 && oBet > 8000){
        	oChip1.setVisibility(View.VISIBLE);
        	oChip2.setVisibility(View.VISIBLE);
            oChip3.setVisibility(View.VISIBLE);
            oChip4.setVisibility(View.VISIBLE);
            oChip5.setVisibility(View.VISIBLE);
            oChip6.setVisibility(View.VISIBLE);
            oChip7.setVisibility(View.VISIBLE);
            oChip8.setVisibility(View.VISIBLE);
            oChip9.setVisibility(View.VISIBLE);
            oChip10.setVisibility(View.INVISIBLE);
        }
        else if(oBet <= 8000 && oBet > 7000){
        	oChip1.setVisibility(View.VISIBLE);
        	oChip2.setVisibility(View.VISIBLE);
            oChip3.setVisibility(View.VISIBLE);
            oChip4.setVisibility(View.VISIBLE);
            oChip5.setVisibility(View.VISIBLE);
            oChip6.setVisibility(View.VISIBLE);
            oChip7.setVisibility(View.VISIBLE);
            oChip8.setVisibility(View.VISIBLE);
            oChip9.setVisibility(View.INVISIBLE);
            oChip10.setVisibility(View.INVISIBLE);
        }
        else if(oBet <= 7000 && oBet > 6000){
        	oChip1.setVisibility(View.VISIBLE);
        	oChip2.setVisibility(View.VISIBLE);
            oChip3.setVisibility(View.VISIBLE);
            oChip4.setVisibility(View.VISIBLE);
            oChip5.setVisibility(View.VISIBLE);
            oChip6.setVisibility(View.VISIBLE);
            oChip7.setVisibility(View.VISIBLE);
            oChip8.setVisibility(View.INVISIBLE);
            oChip9.setVisibility(View.INVISIBLE);
            oChip10.setVisibility(View.INVISIBLE);
        }
        else if(oBet <= 6000 && oBet > 5000){
        	oChip1.setVisibility(View.VISIBLE);
        	oChip2.setVisibility(View.VISIBLE);
            oChip3.setVisibility(View.VISIBLE);
            oChip4.setVisibility(View.VISIBLE);
            oChip5.setVisibility(View.VISIBLE);
            oChip6.setVisibility(View.VISIBLE);
            oChip7.setVisibility(View.INVISIBLE);
            oChip8.setVisibility(View.INVISIBLE);
            oChip9.setVisibility(View.INVISIBLE);
            oChip10.setVisibility(View.INVISIBLE);
        }
        else if(oBet <= 5000 && oBet > 4000){
        	oChip1.setVisibility(View.VISIBLE);
        	oChip2.setVisibility(View.VISIBLE);
            oChip3.setVisibility(View.VISIBLE);
            oChip4.setVisibility(View.VISIBLE);
            oChip5.setVisibility(View.VISIBLE);
            oChip6.setVisibility(View.INVISIBLE);
            oChip7.setVisibility(View.INVISIBLE);
            oChip8.setVisibility(View.INVISIBLE);
            oChip9.setVisibility(View.INVISIBLE);
            oChip10.setVisibility(View.INVISIBLE);
        }
        else if(oBet <= 4000 && oBet > 3000){
        	oChip1.setVisibility(View.VISIBLE);
        	oChip2.setVisibility(View.VISIBLE);
            oChip3.setVisibility(View.VISIBLE);
            oChip4.setVisibility(View.VISIBLE);
            oChip5.setVisibility(View.INVISIBLE);
            oChip6.setVisibility(View.INVISIBLE);
            oChip7.setVisibility(View.INVISIBLE);
            oChip8.setVisibility(View.INVISIBLE);
            oChip9.setVisibility(View.INVISIBLE);
            oChip10.setVisibility(View.INVISIBLE);
        }
        else if(oBet <= 3000 && oBet > 2000){
        	oChip1.setVisibility(View.VISIBLE);
        	oChip2.setVisibility(View.VISIBLE);
            oChip3.setVisibility(View.VISIBLE);
            oChip4.setVisibility(View.INVISIBLE);
            oChip5.setVisibility(View.INVISIBLE);
            oChip6.setVisibility(View.INVISIBLE);
            oChip7.setVisibility(View.INVISIBLE);
            oChip8.setVisibility(View.INVISIBLE);
            oChip9.setVisibility(View.INVISIBLE);
            oChip10.setVisibility(View.INVISIBLE);
        }
        else if(oBet <= 2000 && oBet > 1000){
        	oChip1.setVisibility(View.VISIBLE);
        	oChip2.setVisibility(View.VISIBLE);
            oChip3.setVisibility(View.INVISIBLE);
            oChip4.setVisibility(View.INVISIBLE);
            oChip5.setVisibility(View.INVISIBLE);
            oChip6.setVisibility(View.INVISIBLE);
            oChip7.setVisibility(View.INVISIBLE);
            oChip8.setVisibility(View.INVISIBLE);
            oChip9.setVisibility(View.INVISIBLE);
            oChip10.setVisibility(View.INVISIBLE);
        }
        else if(oBet <= 1000 && oBet > 0){
        	oChip1.setVisibility(View.VISIBLE);
        	oChip2.setVisibility(View.INVISIBLE);
            oChip3.setVisibility(View.INVISIBLE);
            oChip4.setVisibility(View.INVISIBLE);
            oChip5.setVisibility(View.INVISIBLE);
            oChip6.setVisibility(View.INVISIBLE);
            oChip7.setVisibility(View.INVISIBLE);
            oChip8.setVisibility(View.INVISIBLE);
            oChip9.setVisibility(View.INVISIBLE);
            oChip10.setVisibility(View.INVISIBLE);
        }
        else if(oBet <= 0){
        	oChip1.setVisibility(View.INVISIBLE);
        	oChip2.setVisibility(View.INVISIBLE);
            oChip3.setVisibility(View.INVISIBLE);
            oChip4.setVisibility(View.INVISIBLE);
            oChip5.setVisibility(View.INVISIBLE);
            oChip6.setVisibility(View.INVISIBLE);
            oChip7.setVisibility(View.INVISIBLE);
            oChip8.setVisibility(View.INVISIBLE);
            oChip9.setVisibility(View.INVISIBLE);
            oChip10.setVisibility(View.INVISIBLE);
        }
        else{
        	//oBet error   		
        }
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
