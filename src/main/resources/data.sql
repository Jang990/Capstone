-- 사용자
INSERT INTO capstonemanagement.users
(user_id, email, name, password)
VALUES(33, 'hong@gmail.com', '홍길동', 'OAuthUser');

-- 태그 목록
INSERT INTO capstonemanagement.tag
(name, tagged_count, `type`)
VALUES
('java', 4, 'TECH'),
('c', 22, 'TECH'),
('csharp', 5555, 'TECH'),
('codepen', 1, 'TECH'),
('git', 1, 'TECH');

-- 생성한 컨텐츠
INSERT INTO capstonemanagement.content
(content_id, date_created, last_updated, content, heart_count, is_recruit, title, used_language, view_count, user_id)
VALUES(11111, '2022-12-14 09:27:03', '2022-12-14 09:37:03', '컨텐츠 내용1', 33, NULL, 'Spring에 관하여', 'Java', 0, 33);
INSERT INTO capstonemanagement.content
(content_id, date_created, last_updated, content, heart_count, is_recruit, title, used_language, view_count, user_id)
VALUES(22222, '2022-12-14 09:37:03', '2022-12-14 09:37:03', '내용 내용 내용Chsrap 내용11112222', 22, NULL, 'CSharp에 관하여', 'csharp', 0, 33);
INSERT INTO capstonemanagement.content
(content_id, date_created, last_updated, content, heart_count, is_recruit, title, used_language, view_count, user_id)
VALUES(33333, '2022-12-14 09:47:03', '2022-12-14 09:37:03', 'Spring 태그는 없음', 2, NULL, 'Spring 태그는 없는 글', 'Java', 0, 33);
INSERT INTO capstonemanagement.content
(content_id, date_created, last_updated, content, heart_count, is_recruit, title, used_language, view_count, user_id)
VALUES(44444, '2022-12-14 09:47:05', '2022-12-14 09:47:05', '태그는 아예 없음', 1, NULL, '태그가 아예 없는 글입니다', '', 0, 33);

-- 컨텐츠 태그 목록
INSERT INTO capstonemanagement.content_tag
(content_tag_id, content_id, tag_id)
VALUES(1, 2904, 8);
INSERT INTO capstonemanagement.content_tag
(content_tag_id, content_id, tag_id)
VALUES(2, 2904, 13);
INSERT INTO capstonemanagement.content_tag
(content_tag_id, content_id, tag_id)
VALUES(8, 2905, 10);
INSERT INTO capstonemanagement.content_tag
(content_tag_id, content_id, tag_id)
VALUES(9, 2905, 12);
INSERT INTO capstonemanagement.content_tag
(content_tag_id, content_id, tag_id)
VALUES(11, 2905, 11);
INSERT INTO capstonemanagement.content_tag
(content_tag_id, content_id, tag_id)
VALUES(12, 2905, 8);
INSERT INTO capstonemanagement.content_tag
(content_tag_id, content_id, tag_id)
VALUES(13, 2905, 9);
INSERT INTO capstonemanagement.content_tag
(content_tag_id, content_id, tag_id)
VALUES(10, 3006, 12);
