DROP TABLE IF EXISTS clubs CASCADE;

CREATE TABLE clubs(
        id SERIAL PRIMARY KEY NOT NULL,
        name TEXT NOT NULL DEFAULT '',
        location TEXT NOT NULL DEFAULT '',
        genre TEXT NOT NULL DEFAULT '',
        yellowThreshold INT DEFAULT 0,
        maxCap INT DEFAULT 0,
        counter INT DEFAULT 0,
        isHidden boolean DEFAULT FALSE
);

INSERT INTO clubs (name,location,genre,yellowThreshold,maxCap)
VALUES('Club Arcane','San Diego','Rock',70,100),
('Club UnderGround','NYC','Pop',30,50),
('Club Soda','Salt Lake City','Eletric',12,20),
('Studio 52','Chicago','Ethereal',32,52);