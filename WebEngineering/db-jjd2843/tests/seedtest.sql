INSERT INTO users(username,phone_number)  VALUES ('Abbott','123'),('Costello','234'),('Moe','345'),('Larry','456');
INSERT INTO users(username,phone_number,username_last_changed) VALUES('Curly','567','2024-01-01 00:00:00');
INSERT INTO users(username,phone_number,date_created) VALUES('DrMarvin','1234','1961-05-16 09:00:00');
INSERT INTO users(username,phone_number) VALUES('spicelover','7067'),('Paul','1609');


INSERT INTO direct_messages(sender_id,receiver_id,text_sent,time_sent,is_read)
VALUES(1, 2, 'I just put all my money in the stock market!', '1929-10-28 10:00:00', FALSE),

--I asked chatgpt to write these all out so I didn't have to because i don't want arthritus
--still messed up though with the strings using '' instead of "" (words such as "Can't" mess up '') smh
    (1, 2, 'Just finished reading that new novel everyone is talking about.', '1922-05-12 10:00:00', FALSE),
    (2, 1, 'Really? I have been meaning to get my hands on it.', '1922-06-15 14:30:00', TRUE),
    (1, 3, 'Have you heard about the latest inventions? They are quite fascinating.', '1930-07-22 09:45:00', FALSE),
    (3, 1, 'Yes, the advancements are amazing. Cannot wait to see what comes next.', '1931-08-25 11:00:00', TRUE),
    (2, 4, 'I saw the new film release last night. It was quite a spectacle!', '1940-09-10 19:30:00', FALSE),
    (4, 2, 'I heard the film was great. I will try to catch it this weekend.', '1940-10-05 20:00:00', TRUE),
    (3, 5, 'The latest jazz album is out. It has been on repeat in my house.', '1950-11-18 21:15:00', FALSE),
    (5, 3, 'I will have to listen to it. I am always looking for new music to enjoy.', '1950-12-02 16:00:00', TRUE),
    (4, 1, 'I just got back from a wonderful trip abroad. The sights were incredible.', '1960-01-30 13:00:00', FALSE),
    (1, 4, 'Sounds amazing! I have always wanted to travel. Maybe I will plan a trip soon.', '1960-02-15 15:45:00', TRUE),
    (1, 2, 'Did you catch the news about the new scientific discovery?', '1922-08-15 12:00:00', TRUE),
    (2, 1, 'No, but I am eager to learn about it. What is the big news?', '1922-09-20 14:00:00', FALSE),
    (3, 4, 'The latest fashion trends are really unique this year.', '1935-03-10 10:30:00', TRUE),
    (4, 3, 'I agree! I am considering updating my wardrobe soon.', '1935-04-05 11:00:00', FALSE),
    (5, 2, 'Just tried a new recipe for dinner. It was delicious!', '1945-06-15 18:00:00', TRUE),
    (2, 5, 'I would love to try it. Can you share the recipe with me?', '1945-07-20 19:00:00', FALSE),
    (1, 3, 'The latest sports match was intense! What a game!', '1955-08-25 21:30:00', FALSE),
    (3, 1, 'Absolutely! I am still buzzing from the excitement.', '1955-09-05 22:00:00', TRUE),
    (4, 3, 'I am planning a garden for the spring. Any tips?', '1965-03-10 09:30:00', TRUE),
    (3, 4, 'Sure! Start with good soil and do not forget to water regularly.', '1965-03-20 11:00:00', FALSE),
    (8, 3, 'How was your day today?', '2024-09-20 08:45:00', TRUE),
    (3, 8, 'It was pretty good, just finished a workout.', '2024-09-20 09:00:00', TRUE),
    (8, 3, 'Nice! What workout did you do?', '2024-09-20 09:15:00', TRUE),
    (3, 8, 'A full body routine, trying to stay consistent.', '2024-09-20 09:30:00', TRUE),
    (8, 3, 'That is awesome, consistency is key.', '2024-09-20 09:45:00', TRUE),
    (3, 8, 'Absolutely, we should work out together sometime.', '2024-09-20 10:00:00', FALSE),
    (7,8,'Please reply!','2024-09-21',TRUE),
    (8,7,'I replied already!','2024-09-22',TRUE);

    INSERT INTO suspensions(user_id,suspension_starts,suspension_ends)
    VALUES (4,'2000-01-01 00:00:00','2060-01-01 00:00:00'),(5,'1990-01-01 00:00:00','2000-01-01 00:00:00');

    INSERT INTO communities(community_name)
    VALUES('Arrakis'),('Comedy');

    INSERT INTO channels(channel_name,community_id)
    VALUES('#Worms',1),('#Random',1),('#ArgumentClinic',2),('#Dialogs',2);

    INSERT INTO users_communities(user_id,community_id) VALUES(7,1);

    INSERT INTO users_channels(user_id,channel_id) VALUES(7,1),(7,2);

    --chatGPT generated
    INSERT INTO channel_messages(sender_id, channel_id, text_sent, time_sent) 
    VALUES 
    (7, 1, 'Please reply!', '2024-08-01 10:15:00'),
    (8, 1, 'I replied already!', '2024-08-05 14:20:00'),
    (1, 2, 'Hi!', '2024-08-10 09:30:00'),
    (2, 3, 'Hello!', '2024-08-15 11:45:00'),
    (3, 4, 'OK', '2024-08-20 12:00:00'),
    (4, 2, 'Yes', '2024-08-25 15:10:00'),
    (5, 3, 'Sure!', '2024-08-30 08:25:00'),
    (6, 4, 'No', '2024-09-02 17:35:00'),
    (7, 1, 'Got it!', '2024-09-05 13:15:00'),
    (8, 2, 'Done', '2024-09-10 19:20:00'),
    (5, 3, 'See you!', '2024-09-15 10:00:00'),
    (1, 4, 'Bye', '2024-09-20 16:45:00'),
    (2, 2, 'Check this!', '2024-09-22 11:00:00'),
    (3, 3, 'Good!', '2024-09-23 09:00:00'),
    (4, 4, 'Nice!', '2024-09-24 12:30:00'),
    (5, 1, 'Maybe', '2024-09-25 14:50:00'),
    (6, 2, 'Agreed!', '2024-09-26 13:55:00'),
    (7, 3, 'What?', '2024-09-27 11:10:00'),
    (8, 4, 'Later!', '2024-09-27 15:20:00'),
    (7, 1, 'On it!', '2024-09-28 08:00:00'),
    (1, 2, 'Perfect!', '2024-09-28 12:00:00'),
    (2, 3, 'Amazing!', '2024-09-28 18:30:00'),
    (3, 4, 'Thanks!', '2024-09-28 19:00:00'),
    (4, 1, 'Sure thing!', '2024-09-28 20:15:00'),
    (5, 2, 'Noted!', '2024-09-28 21:30:00'),
    (6, 3, 'Will do!', '2024-09-28 22:45:00'),
    (7, 4, 'In progress', '2024-09-28 23:00:00'),
    (8, 1, 'Let me know', '2024-09-28 23:30:00'),
    (3, 2, 'All set!', '2024-09-28 23:59:00'),
    (1, 4, 'Can do!', '2024-09-28 15:10:00'),
    (2, 3, 'Absolutely!', '2024-09-28 16:25:00'),
    (3, 1, 'Confirmed!', '2024-09-28 17:35:00');  

    INSERT INTO suspensions(user_id,suspension_starts,suspension_ends,community_id)
    VALUES (1,'1990-01-01 00:00:00','2000-01-01 00:00:00',1);