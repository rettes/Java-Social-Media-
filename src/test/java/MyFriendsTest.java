// package test.java;

// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertFalse;

// import java.util.ArrayList;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;

// import main.java.socialmagnet.*;
// import main.java.dao.*;

// public class MyFriendsTest {
//     private MemberDAO memDAO = new MemberDAO();
//     private FriendsDAO fDAO = new FriendsDAO();
//     private Member superman = memDAO.getMember("superman");
//     private Member batman = memDAO.getMember("batman");

//     //test the friend list size of superman
//     @Test
//     public void testGetFriends() {
//         Member superman = memDAO.getMember("superman");
//         Member batman = memDAO.getMember("batman");
//         ArrayList<String> friends = fDAO.getFriends(superman.getUsername());
//         assertEquals(0, friends.size());
//     }

//     //test the sending of a friend request from batman to superman
  

//     //test to see batman in superman's request list
//     @Test
//     public void testGetRequest() {
//         ArrayList<String> friendRequests = fDAO.getFriendRequests(superman.getUsername());
//         boolean hasRequest = friendRequests.contains(batman.getUsername());
//         assertTrue(hasRequest);
//     }

//     //superman will accept a friend request from batman
//     @Test
//     public void testAcceptRequest() {
//         fDAO.acceptRequest(superman.getUsername(), batman.getUsername());
//         ArrayList<String> friends = fDAO.getFriends(superman.getUsername());

//         boolean isAccepted = friends.contains(batman.getUsername());
//         assertTrue(isAccepted);
//     }

//     //superman will remove batman from his friend list
//     @Test
//     public void testRemoveFriend() {
//         fDAO.removeFriend(superman.getUsername(), batman.getUsername());

//         ArrayList<String> friends = fDAO.getFriends(superman.getUsername());
//         boolean isRemoved = friends.contains(batman.getUsername());
//         assertFalse(isRemoved);
//     }
// }
