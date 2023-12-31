-- -------------------------------------------------------------
-- TablePlus 5.6.6(520)
--
-- https://tableplus.com/
--
-- Database: votingapp
-- Generation Time: 2023-12-22 20:48:43.6770
-- -------------------------------------------------------------


-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Sequence and defined type
CREATE SEQUENCE IF NOT EXISTS candidates_id_seq;

-- Table Definition
CREATE TABLE "public"."candidates" (
    "id" int4 NOT NULL DEFAULT nextval('candidates_id_seq'::regclass),
    "name" text,
    "phone" text,
    "image" text,
    PRIMARY KEY ("id")
);

-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Sequence and defined type
CREATE SEQUENCE IF NOT EXISTS events_id_seq;

-- Table Definition
CREATE TABLE "public"."events" (
    "id" int4 NOT NULL DEFAULT nextval('events_id_seq'::regclass),
    "name" varchar(255) NOT NULL,
    "candidates" _varchar NOT NULL,
    PRIMARY KEY ("id")
);

-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Sequence and defined type
CREATE SEQUENCE IF NOT EXISTS users_id_seq;

-- Table Definition
CREATE TABLE "public"."users" (
    "id" int4 NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    "name" text,
    "phone" text,
    "code" text,
    PRIMARY KEY ("id")
);

-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Sequence and defined type
CREATE SEQUENCE IF NOT EXISTS vote_id_seq;

-- Table Definition
CREATE TABLE "public"."votes" (
    "id" int4 NOT NULL DEFAULT nextval('vote_id_seq'::regclass),
    "candidate_id" text,
    "code" text,
    "event_id" text,
    PRIMARY KEY ("id")
);

INSERT INTO "public"."candidates" ("id", "name", "phone", "image") VALUES
(1, 'Candidate 1', '015111', '1'),
(2, 'Candidate 2', '015112', '2'),
(3, 'Candidate 3', '015113', '3'),
(4, 'Candidate 4', '015114', '4'),
(5, 'Candidate 5', '015115', '5'),
(9, 'Candidate 6', '015116', '6');

INSERT INTO "public"."events" ("id", "name", "candidates") VALUES
(1, 'Event 1', '{{3,5}}'),
(2, 'Event 2', '{{2,3,5,4}}'),
(3, 'Event 3', '{{5,4,1}}'),
(4, 'Event 4', '{{1,3,5}}'),
(5, 'Event 5', '{{1,4,5,9}}'),
(6, 'Event 6', '{{4,5,9}}'),
(7, '', '{}'),
(8, '', '{}');

INSERT INTO "public"."users" ("id", "name", "phone", "code") VALUES
(7, 'User 1', '017111', '5034'),
(8, 'User 2', '017112', '6092'),
(9, 'User 3', '017113', '1931'),
(10, 'User 4', '017114', '6111'),
(11, 'User 5', '017115', '8173');

INSERT INTO "public"."votes" ("id", "candidate_id", "code", "event_id") VALUES
(1, '3', '284925', '1'),
(2, '5', '950375', '1'),
(3, '5', '124345', '1'),
(4, '5', '836346', '1'),
(5, '5', '8173', '4'),
(6, '4', '8173', '5'),
(7, '9', '8173', '6');

