-- create_user:
INSERT INTO user (student_id, full_name, password, avatar) VALUES (?, ?, ?, ?);

-- login_user:
SELECT * FROM user WHERE student_id = ?;

-- get_all_notes:
SELECT 
    note.*, 
    group_concat(image.image_url) AS images,
    user.full_name AS author_name, 
    user.avatar AS author_image,
    COUNT(DISTINCT liked_note.id) AS like_count,
    CASE WHEN MAX(liked_note.user_id = ?) THEN 1 ELSE 0 END AS is_liked,
    CASE WHEN MAX(saved_note.user_id = ?) THEN 1 ELSE 0 END AS is_saved
FROM note
LEFT JOIN image ON note.id = image.note_id
INNER JOIN user ON note.user_id = user.id
LEFT JOIN liked_note ON note.id = liked_note.note_id
LEFT JOIN saved_note ON note.id = saved_note.note_id
GROUP BY note.id
ORDER BY 
    note.created_at DESC,
    note.id DESC,
    user.id DESC;

-- create_note:
INSERT INTO note (user_id, title, content) VALUES (?, ?, ?);
INSERT INTO image (note_id, image_url)
SELECT LAST_INSERT_ID(), jt.value FROM JSON_TABLE(?, '$[*]' COLUMNS(value VARCHAR(255) PATH '$')) AS jt;

-- like_note:
INSERT INTO liked_note (user_id, note_id) VALUES (?, ?);

-- save_note:
INSERT INTO saved_note (user_id, note_id) VALUES (?, ?);

-- dislike_note:
DELETE FROM liked_note WHERE user_id = ? AND note_id = ?;

-- unsave_note:
DELETE FROM saved_note WHERE user_id = ? AND note_id = ?;