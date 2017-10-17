INSERT INTO `user` (`id`, `username`, `password`, `first_name`, `last_name`, `profile_image`, `description`, `date_created`, `last_updated`, `active`) VALUES (1, 'admin', '$2a$10$Ce2HJja0Trha0ee3.rMqQewIzJMVe87.jNi5zF5gDdsyvHjJsnwOm', 'Admin', 'Admin', NULL, NULL, now(), NULL, 1);
INSERT INTO `user` (`id`, `username`, `password`, `first_name`, `last_name`, `profile_image`, `description`, `date_created`, `last_updated`, `active`) VALUES (2, 'user', '$2a$10$Ce2HJja0Trha0ee3.rMqQewIzJMVe87.jNi5zF5gDdsyvHjJsnwOm', 'User', 'User', NULL, NULL, now(), NULL, 1);

INSERT INTO `role` (`id`, `name`, `description`, `active`) VALUES (1, 'ROLE_ADMIN', 'System Admin', 1);
INSERT INTO `role` (`id`, `name`, `description`, `active`) VALUES (2, 'ROLE_USER', 'Normal User', 1);

INSERT INTO `menu_category` (`id`, `name`, `index`) VALUES (1, 'userManagement', 1);

INSERT INTO `menu_item` (`id`, `name`, `index`, `target`, `menu_category_id`) VALUES (1, 'manageUser', 11, 'user/index', 1);

INSERT INTO `permission` (`id`, `name`, `description`, `active`, `menu_item_id`) VALUES (1, 'manageUser', NULL, 1, 1);

INSERT INTO `role_permission_mapping` (`role_id`, `permission_id`) VALUES (1, 1);

INSERT INTO `user_role_mapping` (`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO `user_role_mapping` (`user_id`, `role_id`) VALUES (2, 2);
