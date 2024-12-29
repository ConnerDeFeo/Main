CREATE TABLE users(
    user_id SERIAL PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    phone_number CHAR(10),
    date_created TIMESTAMP(0) DEFAULT NOW(),
    username_last_changed TIMESTAMP(0) DEFAULT NULL
    
);

CREATE TABLE direct_messages(
    message_id SERIAL PRIMARY KEY,
    sender_id INT REFERENCES users(user_id),
    receiver_id INT REFERENCES users(user_id),
    text_sent TEXT NOT NULL DEFAULT '',
    time_sent TIMESTAMP(0) DEFAULT NOW(),
    is_read BOOLEAN DEFAULT FALSE
);

CREATE INDEX direct_messages_idx ON direct_messages
USING GIN(to_tsvector('english',text_sent));

CREATE TABLE communities(
    community_id SERIAL PRIMARY KEY,
    community_name TEXT UNIQUE NOT NULL
);

CREATE TABLE users_communities(
    user_id INT REFERENCES users(user_id),
    community_id INT REFERENCES communities(community_id),
    unread_messages INT DEFAULT 0
);

CREATE TABLE suspensions(
    user_id INT REFERENCES users(user_id),
    community_id INT REFERENCES communities(community_id) DEFAULT NULL,
    suspension_starts TIMESTAMP(0) DEFAULT NOW(),
    suspension_ends TIMESTAMP(0) DEFAULT NOW()
);

CREATE TABLE channels(
    channel_id SERIAL PRIMARY KEY,
    community_id INT REFERENCES communities(community_id),
    channel_name TEXT NOT NULL DEFAULT ''
);

CREATE TABLE users_channels(
    channel_id INT REFERENCES channels(channel_id),
    user_id INT REFERENCES users(user_id),
    unread_messages INT DEFAULT 0
);

CREATE TABLE channel_messages(
    message_id SERIAL PRIMARY KEY,
    channel_id INT REFERENCES channels(channel_id),
    sender_id INT REFERENCES users(user_id),
    text_sent TEXT NOT NULL DEFAULT '',
    time_sent TIMESTAMP(0) DEFAULT NOW()
);

CREATE INDEX channel_messages_idx ON channel_messages
USING GIN(to_tsvector('english',text_sent));