import java.awt.BorderLayout;
import java.awt.Button;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

class rogic implements Runnable, CommonConstant {
	ArrayList<serverThread> pl = new ArrayList<>();
	ArrayList<Card> mainCardDeck;
	ArrayList<Card> subCardDeck;

	int total;
	int turn;
	String nowShape;
	String nowNum;
	public boolean gameStart;
	public boolean endGame;
	int maxUser;
	Thread th;

	public rogic(int maxUser) {
		this.maxUser = maxUser;
		nowShape = null;
		gameStart = false;
		endGame = true;
		nowNum = null;
		mainCardDeck = new ArrayList<>();
		subCardDeck = new ArrayList<>();

		Thread th = new Thread(this);
		th.start();
	}
	
	public void cardDecADD() {
		Card cd1 = new Card();
		cd1.setNum("2");
		cd1.setShape("하트");
		cd1.setImgName("Heart2");

		Card cd2 = new Card();
		cd2.setNum("3");
		cd2.setShape("하트");
		cd2.setImgName("Heart3");

		Card cd3 = new Card();
		cd3.setNum("4");
		cd3.setShape("하트");
		cd3.setImgName("Heart4");

		Card cd4 = new Card();
		cd4.setNum("5");
		cd4.setShape("하트");
		cd4.setImgName("Heart5");

		Card cd5 = new Card();
		cd5.setNum("6");
		cd5.setShape("하트");
		cd5.setImgName("Heart6");

		Card cd6 = new Card();
		cd6.setNum("7");
		cd6.setShape("하트");
		cd6.setImgName("Heart7");

		Card cd7 = new Card();
		cd7.setNum("8");
		cd7.setShape("하트");
		cd7.setImgName("Heart8");

		Card cd8 = new Card();
		cd8.setNum("9");
		cd8.setShape("하트");
		cd8.setImgName("Heart9");

		Card cd9 = new Card();
		cd9.setNum("10");
		cd9.setShape("하트");
		cd9.setImgName("Heart10");

		Card cd10 = new Card();
		cd10.setNum("Q");
		cd10.setShape("하트");
		cd10.setImgName("HeartQ");

		Card cd11 = new Card();
		cd11.setNum("K");
		cd11.setShape("하트");
		cd11.setImgName("HeartK");

		Card cd12 = new Card();
		cd12.setNum("J");
		cd12.setShape("하트");
		cd12.setImgName("HeartJ");

		Card cd13 = new Card();
		cd13.setNum("A");
		cd13.setShape("하트");
		cd13.setImgName("HeartA");

		Card cd14 = new Card();
		cd14.setNum("2");
		cd14.setShape("클로버");
		cd14.setImgName("Clover2");

		Card cd15 = new Card();
		cd15.setNum("3");
		cd15.setShape("클로버");
		cd15.setImgName("Clover3");

		Card cd16 = new Card();
		cd16.setNum("4");
		cd16.setShape("클로버");
		cd16.setImgName("Clover4");

		Card cd17 = new Card();
		cd17.setNum("5");
		cd17.setShape("클로버");
		cd17.setImgName("Clover5");

		Card cd18 = new Card();
		cd18.setNum("6");
		cd18.setShape("클로버");
		cd18.setImgName("Clover6");

		Card cd19 = new Card();
		cd19.setNum("7");
		cd19.setShape("클로버");
		cd19.setImgName("Clover7");

		Card cd20 = new Card();
		cd20.setNum("8");
		cd20.setShape("클로버");
		cd20.setImgName("Clover8");

		Card cd21 = new Card();
		cd21.setNum("9");
		cd21.setShape("클로버");
		cd21.setImgName("Clover9");

		Card cd22 = new Card();
		cd22.setNum("10");
		cd22.setShape("클로버");
		cd22.setImgName("Clover10");

		Card cd23 = new Card();
		cd23.setNum("Q");
		cd23.setShape("클로버");
		cd23.setImgName("CloverQ");

		Card cd24 = new Card();
		cd24.setNum("K");
		cd24.setShape("클로버");
		cd24.setImgName("CloverK");

		Card cd25 = new Card();
		cd25.setNum("J");
		cd25.setShape("클로버");
		cd25.setImgName("CloverJ");

		Card cd26 = new Card();
		cd26.setNum("A");
		cd26.setShape("클로버");
		cd26.setImgName("CloverA");

		Card cd27 = new Card();
		cd27.setNum("2");
		cd27.setShape("다이아");
		cd27.setImgName("Diamond2");

		Card cd28 = new Card();
		cd28.setNum("3");
		cd28.setShape("다이아");
		cd28.setImgName("Diamond3");

		Card cd29 = new Card();
		cd29.setNum("4");
		cd29.setShape("다이아");
		cd29.setImgName("Diamond4");

		Card cd30 = new Card();
		cd30.setNum("5");
		cd30.setShape("다이아");
		cd30.setImgName("Diamond5");

		Card cd31 = new Card();
		cd31.setNum("6");
		cd31.setShape("다이아");
		cd31.setImgName("Diamond6");

		Card cd32 = new Card();
		cd32.setNum("7");
		cd32.setShape("다이아");
		cd32.setImgName("Diamond7");

		Card cd33 = new Card();
		cd33.setNum("8");
		cd33.setShape("다이아");
		cd33.setImgName("Diamond8");

		Card cd34 = new Card();
		cd34.setNum("9");
		cd34.setShape("다이아");
		cd34.setImgName("Diamond9");

		Card cd35 = new Card();
		cd35.setNum("10");
		cd35.setShape("다이아");
		cd35.setImgName("Diamond10");

		Card cd36 = new Card();
		cd36.setNum("Q");
		cd36.setShape("다이아");
		cd36.setImgName("DiamondQ");

		Card cd37 = new Card();
		cd37.setNum("K");
		cd37.setShape("다이아");
		cd37.setImgName("DiamondK");

		Card cd38 = new Card();
		cd38.setNum("J");
		cd38.setShape("다이아");
		cd38.setImgName("DiamondJ");

		Card cd39 = new Card();
		cd39.setNum("A");
		cd39.setShape("다이아");
		cd39.setImgName("DiamondA");

		Card cd40 = new Card();
		cd40.setNum("2");
		cd40.setShape("스페이드");
		cd40.setImgName("Spade2");

		Card cd41 = new Card();
		cd41.setNum("3");
		cd41.setShape("스페이드");
		cd41.setImgName("Spade3");

		Card cd42 = new Card();
		cd42.setNum("4");
		cd42.setShape("스페이드");
		cd42.setImgName("Spade4");

		Card cd43 = new Card();
		cd43.setNum("5");
		cd43.setShape("스페이드");
		cd43.setImgName("Spade5");

		Card cd44 = new Card();
		cd44.setNum("6");
		cd44.setShape("스페이드");
		cd44.setImgName("Spade6");

		Card cd45 = new Card();
		cd45.setNum("7");
		cd45.setShape("스페이드");
		cd45.setImgName("Spade7");

		Card cd46 = new Card();
		cd46.setNum("8");
		cd46.setShape("스페이드");
		cd46.setImgName("Spade8");

		Card cd47 = new Card();
		cd47.setNum("9");
		cd47.setShape("스페이드");
		cd47.setImgName("Spade9");

		Card cd48 = new Card();
		cd48.setNum("10");
		cd48.setShape("스페이드");
		cd48.setImgName("Spade10");

		Card cd49 = new Card();
		cd49.setNum("Q");
		cd49.setShape("스페이드");
		cd49.setImgName("SpadeQ");

		Card cd50 = new Card();
		cd50.setNum("K");
		cd50.setShape("스페이드");
		cd50.setImgName("SpadeK");

		Card cd51 = new Card();
		cd51.setNum("J");
		cd51.setShape("스페이드");
		cd51.setImgName("SpadeJ");

		Card cd52 = new Card();
		cd52.setNum("A");
		cd52.setShape("스페이드");
		cd52.setImgName("SpadeA");

		mainCardDeck.add(cd1);
		mainCardDeck.add(cd2);
		mainCardDeck.add(cd3);
		mainCardDeck.add(cd4);
		mainCardDeck.add(cd5);
		mainCardDeck.add(cd6);
		mainCardDeck.add(cd7);
		mainCardDeck.add(cd8);
		mainCardDeck.add(cd9);
		mainCardDeck.add(cd10);
		mainCardDeck.add(cd11);
		mainCardDeck.add(cd12);
		mainCardDeck.add(cd13);
		mainCardDeck.add(cd14);
		mainCardDeck.add(cd15);
		mainCardDeck.add(cd16);
		mainCardDeck.add(cd17);
		mainCardDeck.add(cd18);
		mainCardDeck.add(cd19);
		mainCardDeck.add(cd20);
		mainCardDeck.add(cd21);
		mainCardDeck.add(cd22);
		mainCardDeck.add(cd23);
		mainCardDeck.add(cd24);
		mainCardDeck.add(cd25);
		mainCardDeck.add(cd26);
		mainCardDeck.add(cd27);
		mainCardDeck.add(cd28);
		mainCardDeck.add(cd29);
		mainCardDeck.add(cd30);
		mainCardDeck.add(cd31);
		mainCardDeck.add(cd32);
		mainCardDeck.add(cd33);
		mainCardDeck.add(cd34);
		mainCardDeck.add(cd35);
		mainCardDeck.add(cd36);
		mainCardDeck.add(cd37);
		mainCardDeck.add(cd38);
		mainCardDeck.add(cd39);
		mainCardDeck.add(cd40);
		mainCardDeck.add(cd41);
		mainCardDeck.add(cd42);
		mainCardDeck.add(cd43);
		mainCardDeck.add(cd44);
		mainCardDeck.add(cd45);
		mainCardDeck.add(cd46);
		mainCardDeck.add(cd47);
		mainCardDeck.add(cd48);
		mainCardDeck.add(cd49);
		mainCardDeck.add(cd50);
		mainCardDeck.add(cd51);
		mainCardDeck.add(cd52);
	}

	public void shuffleCard() {

		Random rd = new Random();
		for (int i = 0; i < 1000; i++) {
			int num1 = rd.nextInt(mainCardDeck.size());
			int num2 = rd.nextInt(mainCardDeck.size());

			Card ra = mainCardDeck.get(num1);
			mainCardDeck.set(num1, mainCardDeck.get(num2));
			mainCardDeck.set(num2, ra);
		}
	}

	public void subMoveMain() {

		for (int i = 1; i < subCardDeck.size() - 1; i++) {
			mainCardDeck.add(subCardDeck.get(i));
			subCardDeck.remove(i);
		}
	}

	
	public void gameStart() {
		cardDecADD();
		shuffleCard();
		turn = 0;
		for (int i = 0; i < pl.size(); i++) {
			pl.get(i).getMyCard().clear();
			
			for (int j = 0; j < 5; j++) {
				pl.get(i).setMyCard(mainCardDeck.get(0));
				mainCardDeck.remove(0);
			}
			String str = MY_TURN_NUM + SEPA + i;
			pl.get(i).send(str);
			pl.get(i).send(SOUND + SEPA + "shuffling.wav");
		}

		subCardDeck.add(mainCardDeck.get(0));
		mainCardDeck.remove(0);

		nowShape = subCardDeck.get(0).getShape();
		nowNum = subCardDeck.get(0).getNum();

		broadcast();
		pl.get(turn).setMyTurn(true);
	}

	public void endCheck() {
		if(pl.get(turn).getMyCard().size() <= 0) {
			for(int i = 0 ; i < pl.size() ; i ++) {
				if(pl.get(i).equals(pl.get(turn))) {
					pl.get(i).send(GAME_WIN + SEPA);
				}else {
					pl.get(i).send(GAME_LOSE + SEPA);
				}
			}
			endGame();
		}else {
			if (pl.get(turn).getMyCard().size() > 10) {
				pl.get(turn).gameLose = true;
				int num = pl.get(turn).getMyCard().size();
				for (int i = 0; i < num; i++) {
					mainCardDeck.add(pl.get(turn).getMyCard().get(0));
					pl.get(turn).getMyCard().remove(0);
				}
				System.out.println(pl.get(turn).getMyCard().size());
				shuffleCard();	
			}
			
			
			int count = 0 ;
			for(int i = 0 ; i < pl.size() ; i ++) {
				if(pl.get(i).gameLose == true) {
					count++;
				}
			}
			
			System.out.println("count : " +count);
			if(count == maxUser - 1) {
				for(int i = 0 ; i < pl.size() ; i ++) {
					if(pl.get(i).gameLose == true) {
						pl.get(i).send(GAME_LOSE + SEPA);
					}else {
						pl.get(i).send(GAME_WIN + SEPA);
					}
				}
				endGame();
			}else {
				endTurn();
			}
		}
	}

	public void endTurn() {

		pl.get(turn).setMyTurn(false);
		nextTurn();
		pl.get(turn).setMyTurn(true);
	}

	public void nextTurn() {
		System.out.println("7");

		turn++;
		if (turn >= total) {
			turn = 0;
		}

		if (pl.get(turn).gameLose == true) {
			nextTurn();
		}
	}

	synchronized public void cardCheck(String str) {

		int num = Integer.parseInt(str);

		if (num == 10) {
			if(mainCardDeck.size() <= 0) {
				subMoveMain();
				shuffleCard();
			}
			System.out.println("1. shape : " + nowShape);
			System.out.println("1. num : " + nowNum);

			pl.get(turn).getMyCard().add(mainCardDeck.get(0));
			mainCardDeck.remove(0);

			System.out.println("2. shape : " + nowShape);
			System.out.println("2. num : " + nowNum);

			endCheck();
		} else if (nowShape.equals(pl.get(turn).getMyCard().get(num).getShape())
				|| nowNum.equals(pl.get(turn).getMyCard().get(num).getNum())) {
			System.out.println("1. shape : " + nowShape);
			System.out.println("1. num : " + nowNum);

			nowShape = pl.get(turn).getMyCard().get(num).getShape();
			nowNum = pl.get(turn).getMyCard().get(num).getNum();

			subCardDeck.add(0, pl.get(turn).getMyCard().get(num));
			pl.get(turn).getMyCard().remove(num);

			attackCheck(subCardDeck.get(0));

			System.out.println("2. shape : " + nowShape);
			System.out.println("2. num : " + nowNum);
		}

		broadcast();

	}

	public void endGame() {
		System.out.println("end Game");
		for (int i = 0; i < pl.size(); i++) {
			pl.get(i).setMyTurn(false);
			pl.get(i).gameLose = false;
		}
		reset();
		gameStart = false;
	}

	public void attackCheck(Card cd) {
		if (cd.getNum().equals("A")) {
			attackCard(2);
			endCheck();
		} else if (cd.getNum().equals("2")) {
			attackCard(3);

			endCheck();
		} else if (cd.getNum().equals("J")) {
			endCheck();

			endCheck();
			return;
		} else if (cd.getNum().equals("7")) {
			String str = pl.get(turn).selectShape();
			nowShape = str;
			endCheck();

		} else if (cd.getNum().equals("K")) {
			return;
		}else {
			endCheck();
		}
	}

	public void attackCard(int add) {
		for (int i = 0; i < pl.size(); i++) 
			pl.get(i).send(SOUND + SEPA + "도저히 막을 수 없습니다.wav");

		int i;
		if (turn + 1 >= total) {
			i = 0;
		} else {
			i = turn + 1;
		}
		for (int j = 0; j < add; j++) {
			pl.get(i).setMyCard(mainCardDeck.get(0));
			mainCardDeck.remove(0);
			if(mainCardDeck.size() <= 0) {
				subMoveMain();
				shuffleCard();
			}
		}
	}

	synchronized public void broadcast() {
		String str2;
		String str3;
		
		if (subCardDeck.size() <= 0) {
			str2 = SUBCARD_INFORMATION + SEPA;
		} else {
			str2 = SUBCARD_INFORMATION + SEPA + subCardDeck.get(0).getImgName();
		}

		str3 = ENEMYCARD_INFORMATION + SEPA;
		for (int j = 0; j < pl.size(); j++) {
			for (int i = 0; i < pl.size(); i++) {
				if (!pl.get(j).equals(pl.get(i))) {
					str3 += pl.get(i).getMyCard().size() + SEPA;
					str3 += i;
					pl.get(j).send(str3);
					System.out.println("str3 : " + str3);

					str3 = ENEMYCARD_INFORMATION + SEPA;
				}
			}
		}
		for (int i = 0; i < pl.size(); i++) {
			String str = MYCARD_INFORMATION + SEPA;

			for (int j = 0; j < pl.get(i).getMyCard().size(); j++) {
				str += pl.get(i).getMyCard().get(j).getImgName() + SEPA;
			}
			System.out.println(str);
			System.out.println(str2);

			pl.get(i).send(str);
			pl.get(i).send(str2);
			System.out.println("turn : " + turn);
			pl.get(i).send(NOW_TURN + SEPA + turn);
		}
		
		
	}

	public void run() {
		while (endGame) {
			try {
				th.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (gameStart == true) {
				if (pl.size() < maxUser) {
					endGame();
				}
			} else if (gameStart == false && pl.size() >= maxUser) {
				// System.out.println(pl.size());
				System.out.println("gameStart");
				total = pl.size();
				gameStart = true;
				gameStart();
			}
		}
	}

	public void reset() {
		mainCardDeck.clear();
		subCardDeck.clear();
		for (int i = 0; i < pl.size(); i++)
			pl.get(i).getMyCard().clear();

		total = 0;
		broadcast();
	}
}