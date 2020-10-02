package main.java.socialmagnet;

import main.java.dao.*;
import java.sql.*;
import java.util.*;

public class Wall {
	private Member member;
	private String name;
	private String username;
	private int experience;
	public int gold;
	private PostDAO postDAO = new PostDAO();
	private FriendsDAO friendsDAO = new FriendsDAO();
	private MemberDAO memberDAO = new MemberDAO();

	// for example (Member durianMember, durian)
	// if i access a friend's wall (Member ME, friend->orange)
	public Wall(Member member, String wallUsername) {
		this.member = member;
		this.username = wallUsername;
	}

	/**
	 * To display the bottom menu for My Wall
	 */
	public void start() {
		MemberDAO memberDAO = new MemberDAO();
		FriendsDAO friendsDAO = new FriendsDAO();

		// Get My details
		String myrank = memberDAO.getRank(member);
		String myUsername = this.member.getUsername();
		String myFullname = this.member.getName();
		ArrayList<String> myFriends = friendsDAO.getFriends(myUsername);
		HashMap<Integer, Integer> postIDMap = null;

		// Check if username given is ME OR MY FRIEND or stranger
		if (myUsername.equals(this.username)) { // MY WALL
			displayAbout(myUsername);
			postIDMap = displayPosts(myUsername);
			menu();
		} else {
			// Check if it is my friend
			if (myFriends.contains(this.username)) {
				displayFriendAbout(myFullname, this.username);
				postIDMap = displayPosts(username);
				displayFriends(myUsername, this.username);
				friendMenu();
			} else { // if stranger
				displayFriendAbout(myFullname, this.username);
				friendMenu();
			}
		}

		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		char key = input.charAt(0);

		switch (key) {
			case 'M':
				MainMenu mainmenu = new MainMenu(this.member);
				mainmenu.start();
			case 'T':
				int post = Integer.parseInt("" + input.charAt(1));
				ViewThread thread = new ViewThread(postIDMap.get(post), username);

				thread.start();
				break;
			case 'A':
				acceptGifts(this.username);
				break;
			case 'P':
				createPost(myUsername, this.username);
				break;
		}
	}

	// Display bottom menu for my wall
	public void menu() {
		System.out.print("[M]ain | [T]hread | [A]ccept Gift | [P]ost > ");
	}

	// Display bottom menu for friends/strangers wall
	public void friendMenu() {
		System.out.print("[M]ain | [T]hread | [P]ost > ");
	}

	/*
	 * Display MY wall His full name His rank (e.g. novice) His wealth ranking among
	 * his friends. This is the amount of gold the member has amassed in the City
	 * Farmers game.
	 */
	public void displayAbout(String username) {
		MemberDAO memberDAO = new MemberDAO();

		// GET MY details
		String myrank = memberDAO.getRank(member);

		// TODO: find out the wealth ranking among friends
		System.out.println();
		System.out.println("== Social Magnet :: My Wall ==");
		System.out.println("About " + username);
		System.out.println("Full Name: " + member.getName());
		System.out.println(myrank + " Farmer, " + member.getGold());
		System.out.println();
	}

	/*
	 * Display FRIEND's Wall His full name His rank (e.g. novice) His wealth ranking
	 * among his friends. This is the amount of gold the member has amassed in the
	 * City Farmers game.
	 */
	public void displayFriendAbout(String myFullname, String username) {
		MemberDAO memberDAO = new MemberDAO();

		// GET friend's details
		Member friend = memberDAO.getMember(username);
		String friendRank = memberDAO.getRank(friend);

		System.out.println();
		System.out.println("== Social Magnet :: " + username + "'s Wall ==");
		System.out.println("Welcome, " + myFullname + "!");
		System.out.println();
		System.out.println("About " + username);
		System.out.println("Full Name: " + friend.getName());
		System.out.println(friendRank + " Farmer, " + friend.getGold());
		System.out.println();
	}

	/**
	 * Displays 5 most-recent posts on my wall - Retrieve lists of POSTS (i'm the
	 * author AND people tag me in post AND people who write on my wall) - Retrieve
	 * lists of REPLIES for each POST
	 */
	public HashMap<Integer, Integer> displayPosts(String username) {

		ArrayList<Post> posts = new ArrayList<Post>();
		HashMap<Integer, Integer> postIDs = new HashMap<Integer, Integer>();
		ArrayList<String> taggedUsernames = new ArrayList<String>();
		ArrayList<ArrayList<String>> gifts = new ArrayList<ArrayList<String>>();
		PostDAO postDAO = new PostDAO();
		GiftDAO giftDAO = new GiftDAO();

		// get latest 5 posts
		posts = postDAO.get5Posts(username);

		// Print out Message , Likes & Dislikes and Replies for each Post
		int count = 1;
		for (Post post : posts) {
			int postID = post.getPostID();

			taggedUsernames = postDAO.getTaggedPeople(postID);
			String postMessage = post.getMessage();

			// check if POST is a gift post
			if (postID == 0) {
				System.out.println(count + ". " + post.getAuthor() + ": Here is a bag " + postMessage.toLowerCase()
						+ " seeds for you. - City Farmers");
			} else {
				// check if this wall's username exist in this post's tagged people
				// if yes -> replace "@"
				if (taggedUsernames.size() > 0 && taggedUsernames.contains(this.username)) {
					String newMessage = postMessage.replace("@", "");
					System.out.println(count + ". " + post.getAuthor() + ": " + newMessage);
				} else {
					System.out.println(count + ". " + post.getAuthor() + ": " + postMessage);

				}
				postIDs.put(count, postID);

				// if there are no likes and dislikes == show 0 likes and 0 dislikes
				ArrayList<Integer> likeDislikes = postDAO.numberOfLikesDislikes(postID);

				if (likeDislikes.get(0) == 0 && likeDislikes.get(1) == 0) {
					System.out.println("[ 0 likes, 0 dislikes ]");
				} else {
					System.out.println("[ " + likeDislikes.get(0) + " likes, " + likeDislikes.get(1) + " dislikes ]");
				}
			}
			// Get 3 replies for each post
			ArrayList<String> replies = postDAO.get3Replies(postID);
			for (String reply : replies) {
				System.out.println("  " + count + reply);
			}
			count++;
			System.out.println();
		}

		return postIDs;
	}

	/**
	 * Send message to POST DAO
	 */
	public void createPost(String myUsername, String postedOn) {
		String message = "";
		ArrayList<String> taggedUsers = new ArrayList<>();
		ArrayList<String> myFriends = friendsDAO.getFriends(myUsername);
		boolean isEmpty = true;
		Scanner sc = null;

		while (isEmpty) {
			sc = new Scanner(System.in);
			System.out.print("Enter your message> ");
			message = sc.nextLine();

			if (message.isEmpty() || message == null) {
				isEmpty = true;
				System.out.println("Please enter a valid message!");
			} else {
				isEmpty = false;
			}
		}

		try {
			// Get current datetime
			Timestamp datetime = new Timestamp(System.currentTimeMillis());

			// Send username, message, posted_on, date, to Post DAO to create a post
			boolean hasAddedPost = postDAO.addPost(myUsername, message, postedOn, datetime);

			// Check if post has been inserted into database
			if (hasAddedPost) {
				System.out.println("Message successfully posted.");
			} else {
				System.out.println("There was an error, message was not posted.");
			}

		} catch (Exception e) {
			System.out.println("Error in posting message!");
		}

		taggedUsers = getTaggedUsers(message);

		// If there are @tagged users, send to AddTaggedUsers method in PostDAO.
		if (taggedUsers.size() > 0) {

			// Get latest postID from current user
			int latestPostID = postDAO.getLatestPostID(myUsername);
			// if there are tagged users in the message
			for (String user : taggedUsers) {
				// check if author is friends with tagged user
				if (myFriends.contains(user)) {
					boolean hasAddedTagged = postDAO.addTaggedUsers(latestPostID, user);
					if (hasAddedTagged) {
						System.out.println("Added " + user + " successfully");
					} else {
						System.out.println("Error in adding " + user + " tagged username!");
					}
				}
			}
		}
	}

	/*
	 * Get Tagged Users - to get tagged usernames from message
	 */
	public ArrayList<String> getTaggedUsers(String message) {
		String taggedUser = "";
		ArrayList<String> taggedUsersList = new ArrayList<>();

		/** GET tagged usernames out of the message **/
		String[] msgList = message.split(" ");

		for (String msg : msgList) {

			// if it's a username with @, then proceed
			if (msg.charAt(0) == '@') {
				msg = msg.substring(1, msg.length());

				// iterate each character in a string
				for (int i = 0; i < msg.length(); i++) {
					char ch = msg.charAt(i);
					// if character is not a alphabet, remove the special symbol till the end of the
					// message
					if (!(Character.isLetterOrDigit(ch))) {
						int currentIndex = i;
						taggedUser = msg.substring(0, currentIndex);
						break;
					} else {
						taggedUser = msg;
					}
				}
				taggedUsersList.add(taggedUser);
			}
		}
		return taggedUsersList;
	}

	/*
	 * Accept Gifts - to GET gifts from friends and display on wall and ACCEPT gifts
	 * -> bag of seeds will be added to inventory
	 */
	public void acceptGifts(String friendUsername) {
		GiftDAO giftDAO = new GiftDAO();
		SeedDAO seedDAO = new SeedDAO();
		ArrayList<ArrayList<String>> allGifts = new ArrayList<ArrayList<String>>();

		// get all gifts in an arraylist of arraylists
		allGifts = giftDAO.getGifts(friendUsername);

		if (allGifts.size() > 0) {
			for (int i = 0; i < allGifts.size(); i++) {
				ArrayList<String> row = new ArrayList<String>();
				row = allGifts.get(i);
				String receiverUsername = row.get(1);
				String cropName = row.get(2);

				// Update seeds of user's
				boolean hasUpdated = seedDAO.updateSeeds(receiverUsername, cropName, 1);

				// Check if updated seeds quantity successfully
				if (hasUpdated) {
					// remove gift(s) in gifts
					boolean hasRemoved = giftDAO.removeGifts(receiverUsername);
					if (hasRemoved == false) {
						System.out.println("Something went wrong in removing gifts!");
					}
				} else {
					System.out.println("Something went wrong in updating seeds!");
				}

			}
		} else {
			System.out.println("No availble gifts!");
		}
	}

	/*
	 * Display friends when user is looking at other people's wall
	 */
	public void displayFriends(String myUsername, String friendUsername) {
		HashMap<Integer, String> mapFriends = new HashMap<Integer, String>();
		FriendsDAO friendDAO = new FriendsDAO();
		ArrayList<String> myFriends = friendDAO.getFriends(myUsername);
		ArrayList<String> theirFriends = friendDAO.getFriends(friendUsername);
		int count = 1;

		for (String friend : theirFriends) {
			// Check if myuserName exists in friends's list of friends
			if (!(friend.equals(myUsername))) {
				mapFriends.put(count, friend);
				// Check for common friends
				if (myFriends.contains(friend)) {
					System.out.println(count + ". " + friend + " (Common Friend)");
				} else {
					System.out.println(count + ". " + friend);
				}
				count++;
			}
		}
		System.out.println();
	}

}