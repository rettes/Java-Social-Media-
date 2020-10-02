
create database if not exists magnet;
use magnet;

drop table if exists Member;

CREATE TABLE Member (
    username VARCHAR(128) NOT NULL,
    name VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL,
    experience int NOT NULL,
    gold INT NOT NULL,
    PRIMARY KEY (username)
);

drop table if exists Friends;

CREATE TABLE Friends (
    username VARCHAR(128) NOT NULL,
    friend_username VARCHAR(128) NOT NULL,
    status VARCHAR(128) NOT NULL,
    PRIMARY KEY (username,friend_username),
    Constraint friends_fk FOREIGN KEY (username) REFERENCES Member(username)
);

drop table if exists Post;

CREATE TABLE Post (
    postID INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(128) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    posted_on VARCHAR(128) NOT NULL,
    date TIMESTAMP NOT NULL,

    PRIMARY KEY (postid),
    Constraint post_fk FOREIGN KEY (username) REFERENCES Member(username)
);

drop table if exists Tagged_People;

CREATE TABLE Tagged_People (
    postID INT NOT NULL,
    tagged_username VARCHAR(128) NOT NULL,
    PRIMARY KEY (postID,tagged_username),
    Constraint tagged_people_fk1 FOREIGN KEY (postID) REFERENCES Post (postID),
    Constraint tagged_people_fk2 FOREIGN KEY (tagged_username) REFERENCES Member (username)
);
drop table if exists Reply;

CREATE TABLE Reply (
    replyID INT NOT NULL,
    postID INT NOT NULL,
    username VARCHAR(128) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    date TIMESTAMP NOT NULL,
    PRIMARY KEY (ReplyId, PostId),
    Constraint reply_fk1 FOREIGN KEY (postID) REFERENCES Post (postID),
    Constraint reply_fk2 FOREIGN KEY (username) REFERENCES Member (username)
);

drop table if exists Like_Dislike;

CREATE TABLE Like_Dislike (
    postID INT NOT NULL,
    username VARCHAR(128) NOT NULL,
    likes INT NOT NULL,
    dislikes INT NOT NULL,
    PRIMARY KEY (PostId, Username),
    Constraint like_dislike_fk1 FOREIGN KEY (postID) REFERENCES Post (postID),
    Constraint like_dislike_fk2 FOREIGN KEY (username) REFERENCES Member (username)
);

drop table if exists plot;

CREATE TABLE plot (
  username varchar(128) NOT NULL,
  plot_number int(11) NOT NULL,
  crop varchar(128) DEFAULT NULL,
  time_planted timestamp NULL DEFAULT NULL,
  PRIMARY KEY (username,plot_number)
);

drop table if exists seeds;

CREATE TABLE seeds (
  username varchar(128) NOT NULL,
  crop varchar(128) NOT NULL,
  quantity int(11) NOT NULL,
  PRIMARY KEY (username,crop)
);

drop table if exists gifts;

CREATE TABLE gifts (
  sender_username varchar(128) NOT NULL,
  receiver_username varchar(128) NOT NULL,
  gift varchar(128) NOT NULL,
  time_given timestamp NOT NULL,
  PRIMARY KEY (sender_username, receiver_username, time_given)
);

INSERT INTO member (username, name, password, experience, gold)
VALUES
('apple', 'appleheng', 'secret', 0, 50),
('orange', 'orangetan', 'secret', 0, 50),
('pear', 'pearlee', 'secret', 0, 50),
('durian', 'duriantoh', 'secret', 0, 50);

INSERT INTO friends (username, friend_username, status)
VALUES
('apple', 'orange', 'pending'),
('apple', 'pear', 'accept'),
('orange', 'pear', 'accept'),
('orange', 'durian', 'accept'),
('durian', 'apple', 'accept'),
('durian', 'orange', 'pending');

INSERT INTO post (postID, username, message, posted_on, date)
VALUES
(1, 'apple', 'Hello! I’m new to magnet!! @orange @durian', 'apple','2020-02-01 10:10:10'),
(2, 'orange', 'Hi Friends @pear@durian! My first post in magnet', 'orange','2020-02-01 10:10:11'),
(3, 'durian','IMMA DURIAN!', 'durian','2020-02-02 11:11:11'),
(4, 'pear', 'Testing 123 @strawberry', 'pear', '2020-02-02 11:11:11'),
(5, 'orange', 'Hi durian!', 'durian', '2020-02-05 11:11:11'),
(6, 'orange', 'Hi durian! Hi @pear !', 'durian', '2020-02-07 11:11:11');

INSERT INTO tagged_people (postID, tagged_username)
VALUES
(1, 'orange'),
(1, 'durian'),
(2, 'pear'),
(2, 'durian');

INSERT INTO reply (replyID, postID, username, message, date)
VALUES
(1, 1, 'orange', 'Toh man...', '2020-02-01 12:12:12'),
(1, 2, 'pear', 'helluuuuu!', '2020-02-01 10:10:50'),
(2, 1, 'durian', 'YAHOO!', '2020-02-01 12:12:12'),
(3, 1, 'pear', 'Bojio????', '2020-02-01 13:00:00'),
(4, 1, 'durian', 'toh', CURRENT_TIME());

INSERT INTO like_dislike (postID, username, likes, dislikes)
VALUES
(1, 'orange', 1, 0),
(1, 'durian', 1, 0),
(1, 'pear', 0, 1),
(2, 'pear', 1, 0);
