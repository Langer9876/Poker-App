package com.bscofyp.pokerapp.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.Html;
import android.text.Spanned;

public class HelpContent {
	private static String about = 
			"&#8226;This poker app was designed and developed by Padraig O'Regan, Kalvin Stuart & Hamza Ardo, for the purposes of "
			+ "taking a classic game such as poker, and to alter the gameplay slighly in a way which takes the focus off the "
			+ "gambling aspect, and still remain as a fun game. <br />&#8226;Poker is a game where players compete against each other "
			+ "using the two cards which they are assigned in conjunction with the five community cards on the table. "
			+ "While knowing that most of the cards available are out in full view to all players, players are encouraged to "
			+ "weigh their own odds of winning in order best their opponent.",
			rules = "&#8226;At the fist stage of play, each player is assigned two cards each.<br />&#8226;Following this is the flop, where "
					+ "three community cards are dealt on the table.<br />&#8226;After this is the turn, where another card is dealt to "
					+ "the table.<br />&#8226;Finally the river, which is the fifth & last card drawn to the table.<br />&#8226;These table cards "
					+ "(also known as community cards) can be used by all players, in conjunction with their own starting two cards "
					+ "in order to forum a 5 card set, which has it's own culminative strength, and the winner will be the "
					+ "player with the best hand.<br />&#8226;Typically, between each stage of play is a session where players "
					+ "bet against each other in order to achieve higher gains or bluff the other players into folding out of the "
					+ "round. In our game however, points are awarded based on the decision of the player, taking into account "
					+ "whether they continue to play or fold correctly.",
			handTypesSrc = "pokerhands",
			pointDesc = "";
	public static List<HelpItem> ITEMS = new ArrayList<HelpItem>();
	public static Map<String, HelpItem> ITEM_MAP = new HashMap<String, HelpItem>();
	static {
		addItem(new HelpItem("1", "About",about,""));
		addItem(new HelpItem("2", "Poker Rules",rules,""));
		addItem(new HelpItem("3", "Hand Types","",handTypesSrc));
		addItem(new HelpItem("4","Point Scoring",pointDesc,""));
	}

	private static void addItem(HelpItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}
	
	public static class HelpItem {
		public String id;
		public String description;
		public Spanned content;
		public String backgroundSrc;

		public HelpItem(String id, String desc, String content,String img) {
			this.id = id;
			this.description = desc;
			this.content = Html.fromHtml(content);
			this.backgroundSrc = img;
		}

		@Override
		public String toString() {
			return description;
		}
	}
}
