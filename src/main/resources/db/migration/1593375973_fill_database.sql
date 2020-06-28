INSERT INTO popcorn.`movies` (`id`, `name`, `rating`, `launch_date`) VALUES ('3314fc8d-872b-4751-ae34-7e34bbc8022f', 'Lion King', 'G', '2019-07-09 00:00:00');
INSERT INTO popcorn.`movies` (`id`, `name`, `rating`, `launch_date`) VALUES ('52bbc3a3-f640-4ade-831f-89ab661be668', 'Saw', 'R', '2004-10-29 00:00:00');

INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('f8bdd258-530f-4f2d-8b1e-6fd9393f02fe', 'Donald Glover');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('6761ce6d-e93d-4830-9e05-c6bd53d1ed32', 'Seth Rogen');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('76a7d400-ab41-4e1d-b397-06cd19ff59c4', 'Chiwetel Ejiofor');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('8111fd8d-682a-4954-bf9c-2f26f8a0ad05', 'Alfre Woodard');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('4aae0743-ac6f-4162-a224-a387e577970f', 'Billy Eichner');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('a36c4e14-8bd5-4354-aaaa-45a9d1c4f219', 'John Kani');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('d9fc6344-b307-4d4a-b0ba-d682eed532b8', 'John Oliver');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('0f2b0491-2c4d-404b-a4f0-c89eb8c06ebb', 'Beyoncé Knowles-Carter');

INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('cf9b01f0-2f58-4cc0-81aa-63381ad40e91', 'Leigh Whannell');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('c5157f90-119d-494f-a057-da0bd4fd05c5', 'Cary Elwes');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('4c8318fb-49c8-4ad5-8b75-75a2299b5de5', 'Danny Glover');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('ed03b469-4737-43a5-a50f-71a596c28daf', 'Ken Leung');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('b63abb51-2971-491c-bb22-96a9e826f717', 'Dina Meyer');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('60a14628-4217-47ec-b1ef-8c795e1a18a3', 'Mike Butters');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('eae3b083-3524-44e4-9f67-98963bd68dc6', 'Paul Gutrecht');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('510e87a6-101a-42d2-9770-4bc886ef4b9c', 'Michael Emerson');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('2135c451-5def-4c26-bfd4-c77771729d4b', 'Benito Martinez');
INSERT INTO popcorn.`actors` (`id`, `name`) VALUES ('472c5cbc-6e7e-4a2a-9aca-849a5e9bc80c', 'Shawnee Smith');

INSERT INTO popcorn.`directors` (`id`, `name`) VALUES ('df2d0cf2-e60e-4e12-a0b9-d70012a51454', 'Jon Favreau');

INSERT INTO popcorn.`directors` (`id`, `name`) VALUES ('024d05f1-bf11-4267-b1af-9ae363614e40', 'James Wan');

INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('3314fc8d-872b-4751-ae34-7e34bbc8022f', 'f8bdd258-530f-4f2d-8b1e-6fd9393f02fe');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('3314fc8d-872b-4751-ae34-7e34bbc8022f', '6761ce6d-e93d-4830-9e05-c6bd53d1ed32');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('3314fc8d-872b-4751-ae34-7e34bbc8022f', '76a7d400-ab41-4e1d-b397-06cd19ff59c4');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('3314fc8d-872b-4751-ae34-7e34bbc8022f', '8111fd8d-682a-4954-bf9c-2f26f8a0ad05');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('3314fc8d-872b-4751-ae34-7e34bbc8022f', '4aae0743-ac6f-4162-a224-a387e577970f');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('3314fc8d-872b-4751-ae34-7e34bbc8022f', 'a36c4e14-8bd5-4354-aaaa-45a9d1c4f219');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('3314fc8d-872b-4751-ae34-7e34bbc8022f', 'd9fc6344-b307-4d4a-b0ba-d682eed532b8');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('3314fc8d-872b-4751-ae34-7e34bbc8022f', '0f2b0491-2c4d-404b-a4f0-c89eb8c06ebb');

INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('52bbc3a3-f640-4ade-831f-89ab661be668', 'cf9b01f0-2f58-4cc0-81aa-63381ad40e91');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('52bbc3a3-f640-4ade-831f-89ab661be668', 'c5157f90-119d-494f-a057-da0bd4fd05c5');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('52bbc3a3-f640-4ade-831f-89ab661be668', '4c8318fb-49c8-4ad5-8b75-75a2299b5de5');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('52bbc3a3-f640-4ade-831f-89ab661be668', 'ed03b469-4737-43a5-a50f-71a596c28daf');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('52bbc3a3-f640-4ade-831f-89ab661be668', 'b63abb51-2971-491c-bb22-96a9e826f717');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('52bbc3a3-f640-4ade-831f-89ab661be668', '60a14628-4217-47ec-b1ef-8c795e1a18a3');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('52bbc3a3-f640-4ade-831f-89ab661be668', 'eae3b083-3524-44e4-9f67-98963bd68dc6');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('52bbc3a3-f640-4ade-831f-89ab661be668', '510e87a6-101a-42d2-9770-4bc886ef4b9c');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('52bbc3a3-f640-4ade-831f-89ab661be668', '2135c451-5def-4c26-bfd4-c77771729d4b');
INSERT INTO popcorn.`movies_actors` (`movie_id`, `actor_id`) VALUES ('52bbc3a3-f640-4ade-831f-89ab661be668', '472c5cbc-6e7e-4a2a-9aca-849a5e9bc80c');

INSERT INTO popcorn.`movies_directors` (`movie_id`, `director_id`) VALUES ('3314fc8d-872b-4751-ae34-7e34bbc8022f', 'df2d0cf2-e60e-4e12-a0b9-d70012a51454');

INSERT INTO popcorn.`movies_directors` (`movie_id`, `director_id`) VALUES ('52bbc3a3-f640-4ade-831f-89ab661be668', '024d05f1-bf11-4267-b1af-9ae363614e40');

