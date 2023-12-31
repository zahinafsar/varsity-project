-- -------------------------------------------------------------
-- TablePlus 5.6.6(520)
--
-- https://tableplus.com/
--
-- Database: votingapp
-- Generation Time: 2023-12-22 20:47:24.8080
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

INSERT INTO "public"."candidates" ("id", "name", "phone", "image") VALUES
(1, 'Candidate 1', '015111', '1'),
(2, 'Candidate 2', '015112', '2'),
(3, 'Candidate 3', '015113', '3'),
(4, 'Candidate 4', '015114', '4'),
(5, 'Candidate 5', '015115', '5'),
(9, 'Candidate 6', '015116', '6');
