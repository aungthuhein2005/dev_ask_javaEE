create database askdev;
use askdev;
create table users(
	user_id int unique auto_increment primary key,
    username varchar(100) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    reputation int default 0,
    is_admin boolean default 0,
    about text(1000),
    dob DATE,
    created_at timestamp default current_timestamp
);
create table questions(
	question_id int unique auto_increment primary key,
    title text(300) not null,
    description text(1000) default "",
    body text(1500) not null,
    vote int default 0,
    user_id int,
    foreign key (user_id) references users(user_id),
    created_at timestamp default current_timestamp
);
create table answers(
	answer_id int unique auto_increment primary key,
    body text(1000) not null,
    user_id int,
    question_id int,
    vote int default 0,
    created_at timestamp default current_timestamp,
    foreign key (user_id) references users(user_id),
    foreign key (question_id) references questions(question_id)
);
create table tags(
	tag_id int unique auto_increment primary key,
    tag_name varchar(255) not null,
    description text(1000) not null,
        created_at timestamp default current_timestamp
);
create table questiontags(
	question_tag_id int unique auto_increment primary key,
    question_id int not null,
    tag_id int not null,
    foreign key (question_id) references questions(question_id),
    foreign key (tag_id) references tags(tag_id),
    created_at timestamp default current_timestamp
);
create table comments(
	comment_id int unique auto_increment primary key,
    body text(1000) not null,
    user_id int,
    question_id int,
    answer_id int,
    created_at timestamp default current_timestamp,
    foreign key (user_id) references users(user_id),
    foreign key (question_id) references questions(question_id),
    foreign key (answer_id) references answers(answer_id)
);

CREATE TABLE `askdev`.`votes` (
  `vote_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `question_id` INT DEFAULT NULL,
  `answer_id` INT DEFAULT NULL,
  `vote_type` ENUM('upvote', 'downvote') NOT NULL,
  PRIMARY KEY (`vote_id`),
  UNIQUE KEY `unique_vote_question` (`user_id`, `question_id`),
  UNIQUE KEY `unique_vote_answer` (`user_id`, `answer_id`),
  CONSTRAINT `votes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `askdev`.`users` (`user_id`),
  CONSTRAINT `votes_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `askdev`.`questions` (`question_id`),
  CONSTRAINT `votes_ibfk_3` FOREIGN KEY (`answer_id`) REFERENCES `askdev`.`answers` (`answer_id`)
);


create table social_links(
	social_link_id int auto_increment primary key,
    user_id int,
    platform varchar(255) not null,
    link varchar(2048) not null,
    foreign key (user_id) references users(user_id) on delete cascade,
    unique(user_id,platform)
);
CREATE TABLE user_interests (
    user_id INT NOT NULL,
    tag_id INT NOT NULL,
    PRIMARY KEY (user_id, tag_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);
