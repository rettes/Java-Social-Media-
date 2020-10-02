// package test.java;

// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.sql.Timestamp;
// import java.util.ArrayList;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;

// import main.java.socialmagnet.*;
// import main.java.dao.*;

// public class PostFunctionTest {
//     private PostDAO postDAO = new PostDAO();
//     private MemberDAO memDAO = new MemberDAO();
//     private Post thePost = postDAO.getPost(10);

//     // @BeforeAll
//     // public void addUsers() {
//     //     memDAO.addMember("test", "Test1", "password", 0, 50);
//     //     memDAO.addMember("test2", "Test2", "password", 0, 50);
//     //     memDAO.addMember("test3", "Test3", "password", 0, 50);
//     // }

//     @Test
//     public void testAddPost() {
//         memDAO.addMember("test", "Test1", "password", 0, 50);
//         memDAO.addMember("test2", "Test2", "password", 0, 50);
//         memDAO.addMember("test3", "Test3", "password", 0, 50);
//         String username = "test";
//         String message = "This is a test post.";
//         String postedOn = "test2";
//         Timestamp datetime = new Timestamp(System.currentTimeMillis());
//         boolean added = postDAO.addPost(username, message, postedOn, datetime);

//         if (added) {
//             this.thePost = postDAO.getPost(postDAO.getLatestPostID("test"));
//             assertEquals("This is a test post.", thePost.getMessage());
//         }else {
//             assertEquals("Post correct", "Post wrong");
//         }
//     }

//     @Test
//     public void testAddTags(){
//         boolean added = postDAO.addTaggedUsers(thePost.getPostID(), "test2");
//         boolean added2 = postDAO.addTaggedUsers(thePost.getPostID(), "test3");

//         if (added && added2) {
//             ArrayList<String> expected = new ArrayList<>();
//             expected.add("test2");
//             expected.add("test3");

//             ArrayList<String> actual = postDAO.getTaggedPeople(thePost.getPostID());
//             assertEquals(expected, actual);
//         } else {
//             assertEquals("Tag correct", "Tag wrong");
//         }
//     }

//     @Test
//     public void testAddReply() {
//         Timestamp datetime = new Timestamp(System.currentTimeMillis());

//         boolean added1 = postDAO.addReply(1, thePost.getPostID(), "test2", "Reply 1 ok?", datetime);
//         boolean added2 = postDAO.addReply(2, thePost.getPostID(), "test2", "Reply 2 ok?", datetime);
//         boolean added3 = postDAO.addReply(3, thePost.getPostID(), "test2", "Reply 3 ok?", datetime);

//         if (added1 && added2 && added3) {
//             ArrayList<String> expected = new ArrayList<>();
//             expected.add(".1 test2: Reply 1 ok?");
//             expected.add(".2 test2: Reply 2 ok?");
//             expected.add(".3 test2: Reply 3 ok?");

//             ArrayList<String> actual = postDAO.get3Replies(thePost.getPostID());
//             assertEquals(expected, actual);
//         }else {
//             assertEquals("Reply correct", "Reply wrong");
//         }
//     }
//     @Test
//     public void testAddLikeDisLike() {
//         int postID = thePost.getPostID();
//         boolean added1 = postDAO.updateLikeStatus(postID, "test", 'L', true);
//         boolean added2 = postDAO.updateLikeStatus(postID, "test2", 'L', true);
//         boolean added3 = postDAO.updateLikeStatus(postID, "test3", 'D', true);

//         if (added1 && added2 && added3) {
//             ArrayList<String> expected1 = new ArrayList<>();
//             expected1.add("test");
//             expected1.add("test2");

//             ArrayList<String> expected2 = new ArrayList<>();
//             expected2.add("test3");

//             ArrayList<String> actual1 = postDAO.getLikes(postID);
//             assertEquals(expected1, actual1);

//             ArrayList<String> actual2 = postDAO.getDislikes(postID);
//             assertEquals(expected2, actual2);
//         }else {
//             assertEquals("LikesDislikes correct", "LikesDislikes wrong");
//         }
//     }

//     // @Test
//     // public void testDeletePost(){
//     //     int postID = thePost.getPostID();
//     //     boolean deletedTags = postDAO.deleteAllTags(postID);

//     //     if (deletedTags) {
//     //         boolean deletedReplies = postDAO.deleteAllReplies(postID);

//     //         if (deletedReplies) {
//     //             boolean deletedLikes = postDAO.deleteAllLikeDislike(postID);

//     //             if (deletedLikes) {
//     //                 boolean deletePost = postDAO.deletePost(postID);

//     //                 if (!deletePost) {
//     //                     assertEquals("Post deleted", "Post not deleted");
//     //                 }
//     //             } else {
//     //                 assertEquals("Likes deleted", "Likes not deleted");
//     //             }
//     //         } else {
//     //             assertEquals("Replies deleted", "Replies not deleted");
//     //         }
//     //     } else {
//     //         assertEquals("Tags deleted", "Tags not deleted");
//     //     }
//     // }
// }
