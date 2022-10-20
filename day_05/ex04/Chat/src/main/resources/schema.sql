DROP SCHEMA IF EXISTS chat CASCADE;

CREATE SCHEMA IF NOT EXISTS chat;

CREATE TABLE IF NOT EXISTS chat.user (
	id SERIAL PRIMARY KEY,
	login VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL
	/*List of created rooms*/
	/*List of chatrooms where a user socializes*/
);


CREATE TABLE IF NOT EXISTS chat.chatroom (
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	owner INTEGER NOT NULL,
	FOREIGN KEY (owner) REFERENCES chat.user(id)
	/*List of messages in a chatroom*/
);

CREATE TABLE IF NOT EXISTS chat.message (
	id SERIAL PRIMARY KEY,
	text TEXT,
	time TIMESTAMP,
	author INTEGER NOT NULL,
	room INTEGER NOT NULL,
	FOREIGN KEY (author) REFERENCES chat.user(id),
	FOREIGN KEY (room) REFERENCES chat.chatroom(id)
);
