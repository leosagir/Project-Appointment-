--
-- PostgreSQL database dump
--

-- Dumped from database version 13.5 (Debian 13.5-1.pgdg110+1)
-- Dumped by pg_dump version 13.5 (Debian 13.5-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: specialist_specialization; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.specialist_specialization (specialist_id, specialization_id) FROM stdin;
1	6
1	7
1	5
1	8
2	9
2	5
3	7
3	4
3	9
4	6
4	8
5	5
5	9
5	7
5	4
6	6
6	8
7	9
7	4
7	5
7	7
\.


--
-- PostgreSQL database dump complete
--

