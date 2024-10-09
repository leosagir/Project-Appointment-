--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Drop databases (except postgres and template1)
--

DROP DATABASE appointment_db_doc;




--
-- Drop roles
--

DROP ROLE postgres;


--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'md5f661f148cb0c0b9202886f1053c0bbac';






--
-- Databases
--

--
-- Database "template1" dump
--

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

UPDATE pg_catalog.pg_database SET datistemplate = false WHERE datname = 'template1';
DROP DATABASE template1;
--
-- Name: template1; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE template1 OWNER TO postgres;

\connect template1

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
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: template1; Type: DATABASE PROPERTIES; Schema: -; Owner: postgres
--

ALTER DATABASE template1 IS_TEMPLATE = true;


\connect template1

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
-- Name: DATABASE template1; Type: ACL; Schema: -; Owner: postgres
--

REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


--
-- PostgreSQL database dump complete
--

--
-- Database "appointment_db_doc" dump
--

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
-- Name: appointment_db_doc; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE appointment_db_doc WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE appointment_db_doc OWNER TO postgres;

\connect appointment_db_doc

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: refresh_token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.refresh_token (
    id bigint NOT NULL,
    expiry_date timestamp(6) with time zone NOT NULL,
    token character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE public.refresh_token OWNER TO postgres;

--
-- Name: refresh_token_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.refresh_token_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.refresh_token_seq OWNER TO postgres;

--
-- Name: specialist_service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.specialist_service (
    specialist_id bigint NOT NULL,
    service_id bigint NOT NULL
);


ALTER TABLE public.specialist_service OWNER TO postgres;

--
-- Name: specialist_specialization; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.specialist_specialization (
    specialist_id bigint NOT NULL,
    specialization_id bigint NOT NULL
);


ALTER TABLE public.specialist_specialization OWNER TO postgres;

--
-- Name: t_admin; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_admin (
    id bigint NOT NULL,
    address character varying(255) NOT NULL,
    created_at timestamp(6) without time zone,
    date_of_birth date NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(20) NOT NULL,
    last_name character varying(20) NOT NULL,
    password character varying(100) NOT NULL,
    phone character varying(15) NOT NULL,
    role character varying(255),
    status character varying(255),
    updated_at timestamp(6) without time zone,
    CONSTRAINT t_admin_role_check CHECK (((role)::text = ANY ((ARRAY['CLIENT'::character varying, 'SPECIALIST'::character varying, 'ADMINISTRATOR'::character varying])::text[]))),
    CONSTRAINT t_admin_status_check CHECK (((status)::text = ANY ((ARRAY['ACTIVE'::character varying, 'INACTIVE'::character varying, 'BANNED'::character varying])::text[])))
);


ALTER TABLE public.t_admin OWNER TO postgres;

--
-- Name: t_admin_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.t_admin ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.t_admin_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: t_appointment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_appointment (
    id bigint NOT NULL,
    status character varying(255) NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    end_time timestamp(6) without time zone NOT NULL,
    start_time timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    client_id bigint,
    service_id bigint,
    specialist_id bigint NOT NULL,
    CONSTRAINT t_appointment_status_check CHECK (((status)::text = ANY ((ARRAY['AVAILABLE'::character varying, 'BOOKED'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying])::text[])))
);


ALTER TABLE public.t_appointment OWNER TO postgres;

--
-- Name: t_appointment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.t_appointment ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.t_appointment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: t_client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_client (
    id bigint NOT NULL,
    address character varying(255) NOT NULL,
    created_at timestamp(6) without time zone,
    date_of_birth date NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(20) NOT NULL,
    last_name character varying(20) NOT NULL,
    password character varying(100) NOT NULL,
    phone character varying(15) NOT NULL,
    role character varying(255),
    status character varying(255),
    updated_at timestamp(6) without time zone,
    CONSTRAINT t_client_role_check CHECK (((role)::text = ANY ((ARRAY['CLIENT'::character varying, 'SPECIALIST'::character varying, 'ADMINISTRATOR'::character varying])::text[]))),
    CONSTRAINT t_client_status_check CHECK (((status)::text = ANY ((ARRAY['ACTIVE'::character varying, 'INACTIVE'::character varying, 'BANNED'::character varying])::text[])))
);


ALTER TABLE public.t_client OWNER TO postgres;

--
-- Name: t_client_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.t_client ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.t_client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: t_notification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_notification (
    id bigint NOT NULL,
    sent_at timestamp(6) without time zone NOT NULL,
    status character varying(255) NOT NULL,
    appointment_id bigint,
    client_id bigint,
    CONSTRAINT t_notification_status_check CHECK (((status)::text = ANY ((ARRAY['SENT'::character varying, 'FAILED'::character varying])::text[])))
);


ALTER TABLE public.t_notification OWNER TO postgres;

--
-- Name: t_notification_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.t_notification ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.t_notification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: t_review; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_review (
    id bigint NOT NULL,
    comment character varying(510),
    created_at timestamp(6) without time zone NOT NULL,
    rating integer NOT NULL,
    appointment_id bigint NOT NULL,
    client_id bigint NOT NULL,
    specialist_id bigint NOT NULL,
    CONSTRAINT t_review_rating_check CHECK (((rating <= 5) AND (rating >= 1)))
);


ALTER TABLE public.t_review OWNER TO postgres;

--
-- Name: t_review_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.t_review ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.t_review_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: t_service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_service (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    description character varying(1000) NOT NULL,
    duration integer NOT NULL,
    price character varying(255) NOT NULL,
    title character varying(100) NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    specialization_id bigint,
    CONSTRAINT t_service_duration_check CHECK (((duration >= 15) AND (duration <= 60)))
);


ALTER TABLE public.t_service OWNER TO postgres;

--
-- Name: t_service_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.t_service ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.t_service_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: t_specialist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_specialist (
    id bigint NOT NULL,
    address character varying(255) NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    date_of_birth date NOT NULL,
    description character varying(510) NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(20) NOT NULL,
    last_name character varying(20) NOT NULL,
    password character varying(100) NOT NULL,
    phone character varying(15) NOT NULL,
    role character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    CONSTRAINT t_specialist_role_check CHECK (((role)::text = ANY ((ARRAY['CLIENT'::character varying, 'SPECIALIST'::character varying, 'ADMINISTRATOR'::character varying])::text[]))),
    CONSTRAINT t_specialist_status_check CHECK (((status)::text = ANY ((ARRAY['ACTIVE'::character varying, 'INACTIVE'::character varying, 'BANNED'::character varying])::text[])))
);


ALTER TABLE public.t_specialist OWNER TO postgres;

--
-- Name: t_specialist_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.t_specialist ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.t_specialist_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: t_specialization; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_specialization (
    id bigint NOT NULL,
    title character varying(100) NOT NULL
);


ALTER TABLE public.t_specialization OWNER TO postgres;

--
-- Name: t_specialization_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.t_specialization ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.t_specialization_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: refresh_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.refresh_token (id, expiry_date, token, username) FROM stdin;
1	2024-10-29 07:30:42.541878+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc1OTUwNDIsImV4cCI6MTczMDE4NzA0Mn0.0V02hXf7JdsmVwJU9C3mL8uC2yDnECQvYvgzNt6HAsg	leosagir@gmail.com
2	2024-10-29 11:10:18.092677+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2MDgyMTgsImV4cCI6MTczMDIwMDIxOH0.7UKG-eHh-WaEzc8yf95v8kzrV90Wzz82gLWpx2Dt4RA	leosagir@gmail.com
3	2024-10-29 11:10:48.407017+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2MDgyNDgsImV4cCI6MTczMDIwMDI0OH0.VcLo1Uy3s92WB6FwaJB3zvieiRWnrgcfhoCuFwY5-gk	leosagir@gmail.com
4	2024-10-29 11:11:26.161272+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2MDgyODYsImV4cCI6MTczMDIwMDI4Nn0.yYcRmiCZtd10MihNb2ir8EAlJcuDn3xah7f1yqmSDcw	leosagir@gmail.com
5	2024-10-29 11:48:36.346501+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2MTA1MTYsImV4cCI6MTczMDIwMjUxNn0.Pcb3DfuDwoJmYMz0brbw7mH7EK-PuN74__s2W5464wU	leosagir@gmail.com
6	2024-10-29 11:48:50.699787+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2MTA1MzAsImV4cCI6MTczMDIwMjUzMH0.2kaI3bJ8E2pSO6TDrmkOwP0b0q6ZfVcmMODQYZadfVA	leosagir@gmail.com
7	2024-10-29 11:51:06.56361+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2MTA2NjYsImV4cCI6MTczMDIwMjY2Nn0.BWcOw6gKrc6MgKMZICNbx8GLSYDPVd2uZOdw9YejqxE	leosagir@gmail.com
8	2024-10-29 11:52:45.314089+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2MTA3NjUsImV4cCI6MTczMDIwMjc2NX0.Ir4FZ4PDV6qrG4D4mcVqDlDXNYH4ABmvr-pdOUBlMjY	leosagir@gmail.com
52	2024-10-29 12:26:13.260779+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJob2ZtYW5uQGdtYWlsLmNvbSIsImlhdCI6MTcyNzYxMjc3MywiZXhwIjoxNzMwMjA0NzczfQ.8NAwdUp_GS-c1YXy71XovYp36ov2AD4Vie0JvXm39jg	hofmann@gmail.com
53	2024-10-29 12:26:40.699604+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2MTI4MDAsImV4cCI6MTczMDIwNDgwMH0.jSd22m5N_JOOTQnW2NjPMon7xOIiba7Zp4t5Igsqkc0	leosagir@gmail.com
102	2024-10-29 12:29:49.252241+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2MTI5ODksImV4cCI6MTczMDIwNDk4OX0.zK0wa07V3KSnlxhCjsdD8OHFn30XExJnL8vpzNtvslo	leosagir@gmail.com
152	2024-10-29 12:40:43.403306+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2MTM2NDMsImV4cCI6MTczMDIwNTY0M30.upr3X7f0DA1KvwlpD7utOClH30O69TJ34ShoahwKGFg	leosagir@gmail.com
202	2024-10-29 19:09:32.025427+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2MzY5NzIsImV4cCI6MTczMDIyODk3Mn0.-W0po0WujjAgM_OB1zkcYxefscmesQh4NiQjM7MTF4k	leosagir@gmail.com
203	2024-10-29 19:11:10.560544+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2MzcwNzAsImV4cCI6MTczMDIyOTA3MH0.I7ejhykKUAWtkVLxHwWZpJzYuIMSJLFpZ_BkS7ajLfQ	leosagir@gmail.com
204	2024-10-29 19:24:18.002337+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2Mzc4NTgsImV4cCI6MTczMDIyOTg1OH0.UtY_sZPmtjWCu3sa4Yze14DEta0mmvxqHA5Zcl0PSLI	leosagir@gmail.com
205	2024-10-29 19:25:12.473287+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2Mzc5MTIsImV4cCI6MTczMDIyOTkxMn0.iUa50DZFYYt6vDfebI7k9JiIOFHcPvhDYdVP8t9Q6Oo	leosagir@gmail.com
206	2024-10-29 19:34:57.447428+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2Mzg0OTcsImV4cCI6MTczMDIzMDQ5N30.o73Lhy1rh4E7noaX3WcJ1mQwiMrwPfWC5Ty5xoTRGdM	leosagir@gmail.com
207	2024-10-29 19:38:09.140527+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2Mzg2ODksImV4cCI6MTczMDIzMDY4OX0.n38UDKL74vIuQei55TH4tUpptlBRs3JAiRSuWrflpKc	leosagir@gmail.com
208	2024-10-29 19:50:52.221995+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2Mzk0NTIsImV4cCI6MTczMDIzMTQ1Mn0.5dMYgnoDp-wMa4Q93Mi1CqSVbDQsoG534Gvone-qH2g	leosagir@gmail.com
209	2024-10-29 19:57:33.45538+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2Mzk4NTMsImV4cCI6MTczMDIzMTg1M30.pnMI2oNc_znQQI0jji_0YIDzXAKMIi-c9FRchz4KgB8	leosagir@gmail.com
252	2024-10-30 06:44:17.121043+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2Nzg2NTcsImV4cCI6MTczMDI3MDY1N30.TI08zXhH063Wop0KMJFMSFifrozgr0MJXVa6u_hbnig	leosagir@gmail.com
253	2024-10-30 06:56:38.240352+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2NzkzOTgsImV4cCI6MTczMDI3MTM5OH0.t0goxul-E_LxvlhBDEM1OmlccKheRmd8hTtZF0ZLlQk	leosagir@gmail.com
254	2024-10-30 06:58:18.080677+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI3Njc5NDk4LCJleHAiOjE3MzAyNzE0OTh9.FClNocqophiZaRqyWEgaYW5K4ngguGXpT4R2sYs-WjE	leo@gmail.com
255	2024-10-30 06:58:56.078322+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI3Njc5NTM2LCJleHAiOjE3MzAyNzE1MzZ9.7kIxpho_sMVLnMzRlEib8474xUm774Fqsz6Iy2fI2c4	leo@gmail.com
256	2024-10-30 06:59:00.863314+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI3Njc5NTQwLCJleHAiOjE3MzAyNzE1NDB9.BxTKYQwcLI1vyJ_YQ7wh5JlxtQ3ftjRB_PcR5EzE-eA	leo@gmail.com
257	2024-10-30 06:59:05.309728+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI3Njc5NTQ1LCJleHAiOjE3MzAyNzE1NDV9.AYaFANouwIFR7ytGXhmd72nbJr-3XUsyXEkQGgsgVKQ	leo@gmail.com
258	2024-10-30 07:24:00.984665+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2ODEwNDAsImV4cCI6MTczMDI3MzA0MH0.eZxmmi7vI-qvKWFJ_Gzhp13fek3b2k9qwVBFOpPxl4k	leosagir@gmail.com
259	2024-10-30 07:32:59.108046+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2ODE1NzksImV4cCI6MTczMDI3MzU3OX0.r5p8jEkK4l541Mu0jzKGJMIxrOAZ-TByGSW2hOSvmvU	leosagir@gmail.com
260	2024-10-30 10:30:43.850218+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2OTIyNDMsImV4cCI6MTczMDI4NDI0M30.rw_rQw_W_gDcbOTJfV_-JBCaP03dahXaK06MpQE-3wM	leosagir@gmail.com
261	2024-10-30 10:34:53.436988+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyQGdtYWlsLmNvbSIsImlhdCI6MTcyNzY5MjQ5MywiZXhwIjoxNzMwMjg0NDkzfQ.6IGt7Gz7ilqib0Gj32mqooxctmd4ByUxFOMhWgFLF2s	petr@gmail.com
262	2024-10-30 10:37:52.400637+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2OTI2NzIsImV4cCI6MTczMDI4NDY3Mn0.4b2NNr94QSO8nO3XMwvCsWrB0QmDYTAWnG5gr3C5Wro	leosagir@gmail.com
263	2024-10-30 10:38:05.410611+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2OTI2ODUsImV4cCI6MTczMDI4NDY4NX0.CEAgGXG4qHVn6UY9FpNIQsJFHK5c2SHk9sE-Ld12fsE	leosagir@gmail.com
264	2024-10-30 10:42:05.639868+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGlhbkBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2OTI5MjUsImV4cCI6MTczMDI4NDkyNX0.nTj9iYvXATFHQHLJAX01d3RLRRPyPZAKwNvjI0S9azA	cristian@gmail.com
265	2024-10-30 11:46:15.303928+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2OTY3NzUsImV4cCI6MTczMDI4ODc3NX0.hAXg96xypH2Nv7CvtEEzRh_glrV0Y_PXJXmHy8DGha0	leosagir@gmail.com
266	2024-10-30 11:46:37.964204+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyQGdtYWlsLmNvbSIsImlhdCI6MTcyNzY5Njc5NywiZXhwIjoxNzMwMjg4Nzk3fQ.1o4RRoYW6UrT7e7wFWIEsHifxtYv631Q4536LF2RV-I	petr@gmail.com
267	2024-10-30 11:48:05.363447+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyQGdtYWlsLmNvbSIsImlhdCI6MTcyNzY5Njg4NSwiZXhwIjoxNzMwMjg4ODg1fQ.hvsu-QpVBtHRfRq2MSYWB1aEogZLpEVf_HZeY2RGZgE	petr@gmail.com
268	2024-10-30 12:08:55.038808+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2OTgxMzUsImV4cCI6MTczMDI5MDEzNX0.HUUKnRdmToJyFsqVNLzGq5uVTcf-mRGKzDeL2v2rMhQ	leosagir@gmail.com
269	2024-10-30 12:09:15.534365+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2OTgxNTUsImV4cCI6MTczMDI5MDE1NX0.ZbO0YTXX2zRtwEwaqxBz-_lo3sNOj0IQNapSeulBHyo	weber@gmail.com
270	2024-10-30 12:09:28.61344+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2OTgxNjgsImV4cCI6MTczMDI5MDE2OH0.MNlrefd2IcZuBLSAW07QGgkqnkw7iQ8F_ZWsArTMgcA	weber@gmail.com
271	2024-10-30 12:09:36.779618+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc2OTgxNzYsImV4cCI6MTczMDI5MDE3Nn0.GB77JLbW-52D1A3GXNGut7s0FH_Mkx901GoClGAtOUU	weber@gmail.com
302	2024-10-30 15:12:55.871288+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3MDkxNzUsImV4cCI6MTczMDMwMTE3NX0.m4byeW1XHfPgk8Uwkqm8g34U-wVKO84n48VVrCoQUGg	weber@gmail.com
303	2024-10-30 15:14:04.090497+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3MDkyNDQsImV4cCI6MTczMDMwMTI0NH0.wn9ztyTuC5jHQgHLJJuioz7oFi2NLOsv4KhDD0hUcVA	weber@gmail.com
304	2024-10-30 15:22:04.012003+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3MDk3MjQsImV4cCI6MTczMDMwMTcyNH0.2Fcr2vF0JI7TARvmsP1qRTZrdbHTYPAFzWCh5uOLCtA	weber@gmail.com
352	2024-10-31 07:37:07.83011+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3NjgyMjcsImV4cCI6MTczMDM2MDIyN30.-03oDsQkw5Uxnm66JDQyriki4yI41KMdO_q9zj6Qawo	leosagir@gmail.com
353	2024-10-31 07:38:43.082762+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzY2huZWlkZXJAZ21haWwuY29tIiwiaWF0IjoxNzI3NzY4MzIzLCJleHAiOjE3MzAzNjAzMjN9.LBGMaCGFutTu23KrHb4D5BVcOQ5-VyUzfrP4URmRjdA	schneider@gmail.com
402	2024-10-31 07:45:36.447588+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3Njg3MzYsImV4cCI6MTczMDM2MDczNn0.l2Ng-2v-zQQB1XEGWyeEsSVQHtt1YBY7E5v2G0g-oD8	leosagir@gmail.com
403	2024-10-31 07:49:00.881324+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3Njg5NDAsImV4cCI6MTczMDM2MDk0MH0.kFdGhCO2sFLLiUbzdFqJnz1Ir34DASclI_c0UK79x54	weber@gmail.com
452	2024-10-31 08:23:04.241871+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3NzA5ODQsImV4cCI6MTczMDM2Mjk4NH0.3lP12pBkceka96jItGGtquLm3-VWHTXdef6l5jWYNtE	weber@gmail.com
453	2024-10-31 08:24:08.748376+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3NzEwNDgsImV4cCI6MTczMDM2MzA0OH0.v1SuBglhgslYD1wa-FNP_B22FT9_L7byC2K18RsZexo	weber@gmail.com
454	2024-10-31 08:24:23.80359+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3NzEwNjMsImV4cCI6MTczMDM2MzA2M30.8IH5JzyR89Qbf-_jGKnuup0sjGzF53sRLl49zRhaaGA	leosagir@gmail.com
455	2024-10-31 08:35:17.077885+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3NzE3MTcsImV4cCI6MTczMDM2MzcxN30.SowyCoDQ6O3JvQNqQLJSz0K69Nyz5Aghfhi3uLukCxk	leosagir@gmail.com
456	2024-10-31 08:36:50.85861+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3NzE4MTAsImV4cCI6MTczMDM2MzgxMH0.SXqMNxz9GFXeoO4AB1V81ZjzBuVOBrthyFldLnGtXVg	weber@gmail.com
457	2024-10-31 08:37:26.032827+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3NzE4NDYsImV4cCI6MTczMDM2Mzg0Nn0.DslSIP1sJdyNmV1V4GwJH39_FUJesIqIjDWyWdVQxGg	weber@gmail.com
458	2024-10-31 09:53:25.78627+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3NzY0MDUsImV4cCI6MTczMDM2ODQwNX0.s0Ic-j4TILjP5ottORKOFnAK0j3LcjINHOsDuRVL3tg	weber@gmail.com
459	2024-10-31 09:54:07.054913+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3NzY0NDcsImV4cCI6MTczMDM2ODQ0N30.gwpyR1w631h-0-VI1ew2hwZt14XdmbhRGTrEjsupURc	weber@gmail.com
460	2024-10-31 09:58:20.924457+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3NzY3MDAsImV4cCI6MTczMDM2ODcwMH0.ka9oj5iQRLaCfTJzdRJlvZjk2pmNguuHWm_o_sWwuOc	leosagir@gmail.com
461	2024-10-31 10:12:19.924374+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3Nzc1MzksImV4cCI6MTczMDM2OTUzOX0.yciGe63juib0VS6gyFXbKuB-9fPUjCydRsK_UOFwlVM	weber@gmail.com
502	2024-10-31 10:23:20.141425+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3NzgyMDAsImV4cCI6MTczMDM3MDIwMH0.Hny3Fe5sYGb9nUr9gQdG8idiHsU_8elJ4jeYusswRZw	weber@gmail.com
503	2024-10-31 11:32:27.781684+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3ODIzNDcsImV4cCI6MTczMDM3NDM0N30.njcOYOnKgR70ZReBxBq8W1dgH4Oir5bHrH9UkZ2xyO4	weber@gmail.com
504	2024-10-31 11:39:05.063794+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3ODI3NDUsImV4cCI6MTczMDM3NDc0NX0.QmknXI-up8W6Xq71SSt5q44Rim0LQ6So28iZndYfNwI	weber@gmail.com
505	2024-10-31 11:41:42.198161+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3ODI5MDIsImV4cCI6MTczMDM3NDkwMn0.pFPF4426vAMVGMvXSfar8rruMATiXyNphbVdWaGSfVA	weber@gmail.com
506	2024-10-31 11:57:19.050059+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3ODM4MzksImV4cCI6MTczMDM3NTgzOX0.Y0NKtiSNVNsotih64we7y6ZJyxsf9xEeONxwvdrxBxA	leosagir@gmail.com
507	2024-10-31 12:37:44.696828+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3ODYyNjQsImV4cCI6MTczMDM3ODI2NH0.q4MPjK9HaJlFTu6P31wYkSXktEdi3ag4opNTy8bSjgg	leosagir@gmail.com
508	2024-10-31 12:47:35.969541+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3ODY4NTUsImV4cCI6MTczMDM3ODg1NX0.dMDZfhmP8oLpwM-GHdZSPm1C1DpW7vjGq46ZhQVRqqA	leosagir@gmail.com
509	2024-10-31 12:48:27.862833+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3ODY5MDcsImV4cCI6MTczMDM3ODkwN30.6RrsSv3WoOHSWWX0Ie_hdioFSbyZRtOV_6ul6Swcs-Y	leosagir@gmail.com
510	2024-10-31 13:07:24.969035+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc3ODgwNDQsImV4cCI6MTczMDM4MDA0NH0.vDHlOZq5baiYrSy2afe47uVU1owQvp5waCG3VuRe8U4	leosagir@gmail.com
552	2024-10-31 17:26:11.384391+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4MDM1NzEsImV4cCI6MTczMDM5NTU3MX0.iO2z26HBMqVH6G5c-D3fT39STD1xrMFBx8N5w-pnWgQ	leosagir@gmail.com
553	2024-10-31 17:28:46.975267+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4MDM3MjYsImV4cCI6MTczMDM5NTcyNn0.4Q1AuXgw2yNHT1wdSHykvCR1C12bwxIb7zg4lP0IhEY	leosagir@gmail.com
554	2024-10-31 17:45:48.142164+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4MDQ3NDgsImV4cCI6MTczMDM5Njc0OH0.GQSKX9o9E9FyNnG7k1H3g3Sv0rtA6-SRZyrwXJxIhrE	leosagir@gmail.com
555	2024-10-31 17:48:37.354152+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyQGdtYWlsLmNvbSIsImlhdCI6MTcyNzgwNDkxNywiZXhwIjoxNzMwMzk2OTE3fQ.q4I2Hk3IiMpTH1iWNjrmD1HzE8x14c8-_9MQdchlraU	petr@gmail.com
556	2024-10-31 17:49:24.618679+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzY2huZWlkZXJAZ21haWwuY29tIiwiaWF0IjoxNzI3ODA0OTY0LCJleHAiOjE3MzAzOTY5NjR9.TiSgjaql2Zs7cfTA1RetIcKkkaQOG66Qedw2RPcSZqU	schneider@gmail.com
557	2024-10-31 18:24:51.423463+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4MDcwOTEsImV4cCI6MTczMDM5OTA5MX0.FSdp6T2v1kdIoJqBqCMBbfusX3lkHgQpHif-pKUdhMw	leosagir@gmail.com
602	2024-10-31 18:50:23.898431+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4MDg2MjMsImV4cCI6MTczMDQwMDYyM30.-HJZTjG54b_YyalZq6VfhESftSgLsKpuQaOlofJB6oo	weber@gmail.com
603	2024-10-31 18:50:50.494942+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4MDg2NTAsImV4cCI6MTczMDQwMDY1MH0.zlTEcqX8hdLZfPjNCCVab5a0WJigVizSSSKTNnm7ksI	leosagir@gmail.com
2252	2024-11-01 06:37:10.282679+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4NTEwMzAsImV4cCI6MTczMDQ0MzAzMH0.GHeoZKIWGpfB2Es-To4Hinih4DYYMg6hBenehKSWoKs	leosagir@gmail.com
2302	2024-11-01 09:52:49.258236+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4NjI3NjksImV4cCI6MTczMDQ1NDc2OX0.UkjvKXFOahrQkO7SqUeEWZzwOt--kKX2uUk39fPCrcA	leosagir@gmail.com
2303	2024-11-01 10:09:46.227946+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4NjM3ODYsImV4cCI6MTczMDQ1NTc4Nn0.fV5W09ukj6PHUdZGxaWSHxaQUlABsw4C2fdq94r8QkM	leosagir@gmail.com
2304	2024-11-01 10:49:18.539626+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4NjYxNTgsImV4cCI6MTczMDQ1ODE1OH0.j4pU2t0aTaxctqn-6pHoyxG3Jocek9pflHLTKRxYfwk	leosagir@gmail.com
2305	2024-11-01 11:31:18.005957+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4Njg2NzgsImV4cCI6MTczMDQ2MDY3OH0.SuJ99p0cT9cjLs-ECacD3cdNP-6I8uKdsCXTBPviktY	leosagir@gmail.com
2306	2024-11-01 11:46:16.530051+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4Njk1NzYsImV4cCI6MTczMDQ2MTU3Nn0.XT-0zyKnL2JIsx7XvCcSwu3J-bBWcDt5TstXEJfW9U8	leosagir@gmail.com
2307	2024-11-01 12:07:47.578152+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc4NzA4NjcsImV4cCI6MTczMDQ2Mjg2N30.GwcY35tylyc4KLgaAqyOE2W6oPMM9C2IOR4659dpFaU	leosagir@gmail.com
2352	2024-11-02 13:11:51.805978+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc5NjExMTEsImV4cCI6MTczMDU1MzExMX0.Vudc2Fj-O6Dx8WmJWVk6wLrTJXUOn1nl8CQxqehOtlc	leosagir@gmail.com
2402	2024-11-02 14:12:18.072954+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjc5NjQ3MzgsImV4cCI6MTczMDU1NjczOH0.0N9ZlqEoo7uytiWwSVyTo5uZqXGibIMPy2yi8PiOuyo	leosagir@gmail.com
2452	2024-11-03 08:02:50.615749+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgwMjg5NzAsImV4cCI6MTczMDYyMDk3MH0.7q5tjGgJeqYQpkpoYXH2m4H6rUy8Ec4aYITkKDj24ks	leosagir@gmail.com
2453	2024-11-03 08:33:17.937488+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgwMzA3OTcsImV4cCI6MTczMDYyMjc5N30.oCAYO6WKY4NpYen4LZ0SjEkWMC4RWR7vehELsdrTPH8	leosagir@gmail.com
2454	2024-11-03 08:35:45.772704+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgwMzA5NDUsImV4cCI6MTczMDYyMjk0NX0.Q6A19nDrA7ysmyC_T9c5ScA26qmKJobiGaDN_JD7k2Q	leosagir@gmail.com
2502	2024-11-03 08:40:45.903714+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgwMzEyNDUsImV4cCI6MTczMDYyMzI0NX0.VL6_afrtN6Ow6bInh4sBNBuaAydJOLU5FEBu8J2kF4o	leosagir@gmail.com
2503	2024-11-03 08:53:51.311901+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgwMzIwMzEsImV4cCI6MTczMDYyNDAzMX0.xq7yxPbMuvjPF_KdiIbQJh9h7rPV-nkwsTdEQbEr45o	leosagir@gmail.com
2504	2024-11-03 11:07:04.234467+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgwNDAwMjQsImV4cCI6MTczMDYzMjAyNH0.U_Wj57bBlJGkA5bpli8xsjvW-WpukHxmKmEbmJKqI0s	leosagir@gmail.com
2505	2024-11-03 11:26:12.518802+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzY2huZWlkZXJAZ21haWwuY29tIiwiaWF0IjoxNzI4MDQxMTcyLCJleHAiOjE3MzA2MzMxNzJ9.fpEtaKUxyLwu3cNA2uY6EpXPwEpMO-OloAFeDIqfc-U	schneider@gmail.com
2506	2024-11-03 11:27:26.358249+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3MjgwNDEyNDYsImV4cCI6MTczMDYzMzI0Nn0.2RATUM2WXMQ8hLfKMf4AMsH6_NjS6H1vkQkZCf9VK_E	weber@gmail.com
2507	2024-11-03 11:27:50.383736+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3MjgwNDEyNzAsImV4cCI6MTczMDYzMzI3MH0.z2q0lfnAhXGnkR2mTKOogC0NgUyV1rGz97tt6FEgEGw	weber@gmail.com
2508	2024-11-03 11:29:19.911846+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3MjgwNDEzNTksImV4cCI6MTczMDYzMzM1OX0.y--KPv4l3Sn5xGrTPO4vLtiNlYu1u3N_jkiJc9klDqY	weber@gmail.com
2552	2024-11-03 11:43:13.511811+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3MjgwNDIxOTMsImV4cCI6MTczMDYzNDE5M30.xfYnXS16WQaGi99Ezb3xosmuin7Hv2jK1GEAAVlkhSY	weber@gmail.com
2602	2024-11-04 07:12:11.428243+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3MjgxMTIzMzEsImV4cCI6MTczMDcwNDMzMX0.8ltyX8-wl2Ew0m-Bvw5KoqhAGs-Cz0jX9uXZmQrfK0I	weber@gmail.com
2603	2024-11-04 07:24:48.516845+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3MjgxMTMwODgsImV4cCI6MTczMDcwNTA4OH0.FZj3RcklHW-XjaUnV4flyLOUhIoYNs-SegfR2y2bE6k	weber@gmail.com
2604	2024-11-04 07:29:20.093586+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgxMTMzNjAsImV4cCI6MTczMDcwNTM2MH0.nbeC4OuqCUCTJi0FD7wyz9Q0uZtjYcyLYH_5H64NG04	leosagir@gmail.com
2605	2024-11-04 07:42:56.042844+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTE0MTc2LCJleHAiOjE3MzA3MDYxNzZ9.ywd9aNGbOP-SZ0z0flds5qe2MPqgQhPkwYbKkEBCtCQ	leo@gmail.com
2606	2024-11-04 07:43:33.59152+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTE0MjEzLCJleHAiOjE3MzA3MDYyMTN9.th4pgUdX7_d9B2uohZDxhsWHdFPxAFCWqK0pSd8fgto	leo@gmail.com
2607	2024-11-04 08:04:16.01385+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTE1NDU2LCJleHAiOjE3MzA3MDc0NTZ9.dQ1q3InryLfdI9wxeSs2S4RuWQ7LNREMrwjC6u7TMJ0	leo@gmail.com
2608	2024-11-04 08:09:48.37639+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTE1Nzg4LCJleHAiOjE3MzA3MDc3ODh9.xAqkFGcWvPz9ly6r4qRjppbPDMWsy_Hzvo_C4s6NWHs	leo@gmail.com
2609	2024-11-04 08:12:44.645456+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTE1OTY0LCJleHAiOjE3MzA3MDc5NjR9.G9B_66F5Ba109_rRQh6URBUnG1MRfXJa4iBilbn4dPE	leo@gmail.com
2610	2024-11-04 08:14:51.843367+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTE2MDkxLCJleHAiOjE3MzA3MDgwOTF9.tU6gnFlqWdUi8iT6ItTLVxybHzwCQgFLb3_J6hew6Rg	leo@gmail.com
2611	2024-11-04 08:20:22.616673+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTE2NDIyLCJleHAiOjE3MzA3MDg0MjJ9.hoclqcvqcCg99icWYVmB315S_nKkZeREy-H1s9erCTM	leo@gmail.com
2612	2024-11-04 08:30:04.667226+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTE3MDA0LCJleHAiOjE3MzA3MDkwMDR9.H2RNvR-tNG2tC49yc3a4IVTGLhl0PvBVOw74DahunZA	leo@gmail.com
2652	2024-11-04 08:57:41.440994+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTE4NjYxLCJleHAiOjE3MzA3MTA2NjF9.8bceP26F2-WoSfme25s7xeD1CuWB5prPI46mzjP87Xo	leo@gmail.com
2653	2024-11-04 09:42:16.926682+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTIxMzM2LCJleHAiOjE3MzA3MTMzMzZ9.ZfMSCW7gu0qs7y_xQgBEqGJhRO9yeNqXsB171hUZlAw	leo@gmail.com
2654	2024-11-04 09:56:22.778543+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTIyMTgyLCJleHAiOjE3MzA3MTQxODJ9.4QcSAzogjG6jua4rgUmJzSVM8E7bZLNavk3xbUDQG9o	leo@gmail.com
2655	2024-11-04 10:00:44.592959+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgxMjI0NDQsImV4cCI6MTczMDcxNDQ0NH0.A23DtHgfqkdOvbVtg0G1To88-XWOkpHrdbD01-ZgQXQ	leosagir@gmail.com
2656	2024-11-04 10:02:52.407461+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTIyNTcyLCJleHAiOjE3MzA3MTQ1NzJ9.D-CiIgoRfiDb-EtCdfzBruNFOJWKheV7lnkSxY3vXm4	leo@gmail.com
2702	2024-11-04 16:17:36.531302+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTQ1MDU2LCJleHAiOjE3MzA3MzcwNTZ9.jFSuMe2F337X6nzwgb_DVyV_fiLkUzci053hyWaYquI	leo@gmail.com
2703	2024-11-04 16:24:53.583354+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTQ1NDkzLCJleHAiOjE3MzA3Mzc0OTN9.bq5t8URI-kaTuX9fdoe7sZWpBfBAJyRaAa67u9jm1Ek	leo@gmail.com
2752	2024-11-04 17:07:08.490149+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTQ4MDI4LCJleHAiOjE3MzA3NDAwMjh9.d1ECeh2VCERXKdKebtgn44Gb7S2H1hdtEX9dOf1sKCs	leo@gmail.com
2753	2024-11-04 17:09:10.130924+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTQ4MTUwLCJleHAiOjE3MzA3NDAxNTB9.YYDGNhldCjrlE7j5pYRB9eLmP7j21iyaiuFu1bo9bzI	leo@gmail.com
2754	2024-11-04 17:09:45.383068+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTQ4MTg1LCJleHAiOjE3MzA3NDAxODV9.mpbGjLcz97Nc41Cx_6zySjsUu3g6MQLCEsYij8NA2I0	leo@gmail.com
2802	2024-11-04 17:25:50.480049+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTQ5MTUwLCJleHAiOjE3MzA3NDExNTB9.NCDSs_6-WEYMDx4MAt_ZR4bFssMaC5Rfwxq5DQ7r-fY	leo@gmail.com
2852	2024-11-04 17:41:15.366237+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTUwMDc1LCJleHAiOjE3MzA3NDIwNzV9.aLglsWuK1mKb6VCKRQRseI_oPPPAtH73HQeWGjINj3o	leo@gmail.com
2902	2024-11-04 18:02:17.102872+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTUxMzM3LCJleHAiOjE3MzA3NDMzMzd9.C6pOKXNyIYctmCWMt49n2QjHMRTe1qgPwFFVdhzegkc	leo@gmail.com
2903	2024-11-04 18:05:09.919539+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzY2huZWlkZXJAZ21haWwuY29tIiwiaWF0IjoxNzI4MTUxNTA5LCJleHAiOjE3MzA3NDM1MDl9.ckU2aNXYApgcc29W2YrbpcFXD2lXK0Mw8Qn9KR1__0c	schneider@gmail.com
2904	2024-11-04 18:06:05.030132+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTUxNTY1LCJleHAiOjE3MzA3NDM1NjV9.nJ8L0f_94ByWLzenW8D7zUg4r0cOoLZuxrfdh-hAgJg	leo@gmail.com
2905	2024-11-04 18:06:44.104708+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTUxNjA0LCJleHAiOjE3MzA3NDM2MDR9.WqF25lBhNaoVmP0EAt_421xd5R6DCeKvMEA7dapk17M	leo@gmail.com
2952	2024-11-04 18:07:34.339485+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTUxNjU0LCJleHAiOjE3MzA3NDM2NTR9.rM0k11LpLk1UvgQXehkCW1_BQPt2MCo7AubAoKAkvQ8	leo@gmail.com
2953	2024-11-04 18:08:16.669199+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTUxNjk2LCJleHAiOjE3MzA3NDM2OTZ9.w_gODVaoQpl4LgF7OUxlfGqK3OxZucbl_ZFgsKzQu_E	leo@gmail.com
2954	2024-11-04 18:08:26.430532+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTUxNzA2LCJleHAiOjE3MzA3NDM3MDZ9.ChOiA4ukNjDtFmDJ4jY9E6ivvmMIKzn0I0kg944M_bk	leo@gmail.com
2955	2024-11-04 18:08:58.720146+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgxNTE3MzgsImV4cCI6MTczMDc0MzczOH0.gWfUJ_BA_PcytFQHLKN4LlImvLJB8ebE2KH-7fDTAlw	leosagir@gmail.com
2956	2024-11-04 18:09:48.641154+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MTUxNzg4LCJleHAiOjE3MzA3NDM3ODh9.F8vxyk5KDtgvNVQ56xRsaMYv2-GZd6hcwNqrh4hHDDU	leo@gmail.com
3002	2024-11-05 08:43:25.701091+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MjA0MjA1LCJleHAiOjE3MzA3OTYyMDV9.XqVvzmeeywhdKPzDW-1cPgl7kBKkRLRKzh-ggE203K0	leo@gmail.com
3003	2024-11-05 08:44:00.686379+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgyMDQyNDAsImV4cCI6MTczMDc5NjI0MH0.TGeMON33EF0nnFGgEu3PXxZvLK7KJwNVkXsFvMkii84	leosagir@gmail.com
3004	2024-11-05 08:44:53.6011+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzY2huZWlkZXJAZ21haWwuY29tIiwiaWF0IjoxNzI4MjA0MjkzLCJleHAiOjE3MzA3OTYyOTN9.YcgbUnmt_1T_-mwX4nrrwzHp3525vnJqAWq2OVK-9rk	schneider@gmail.com
3005	2024-11-05 08:46:25.8469+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgyMDQzODUsImV4cCI6MTczMDc5NjM4NX0.xBREjyyDiBjMInIc76PTHrZjJJBMVDx4edq4WT7USBA	leosagir@gmail.com
3006	2024-11-05 09:09:55.474047+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgyMDU3OTUsImV4cCI6MTczMDc5Nzc5NX0.tNB6iVdaeLBRlpIsl90wOPoljVp_AdcZo1wMbx4kTFw	leosagir@gmail.com
3007	2024-11-05 10:33:19.027814+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgyMTA3OTksImV4cCI6MTczMDgwMjc5OX0.absBUUFqrVZ4ymd-Uv4BggWzJEG5TM-v0e1dVPiFhIo	leosagir@gmail.com
3008	2024-11-05 10:36:51.917012+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgyMTEwMTEsImV4cCI6MTczMDgwMzAxMX0.b5jh31-hl5bmhtVbCcs1RXC2yrGvdV64QgBkMGOi1zw	leosagir@gmail.com
3009	2024-11-05 10:41:27.85636+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MjExMjg3LCJleHAiOjE3MzA4MDMyODd9.IbjaaHVaytyFB0PfQEXwq2apoyZyyGy_ZeffLGQ8vk8	leo@gmail.com
3010	2024-11-05 10:45:50.038501+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YWduZXJAZ21haWwuY29tIiwiaWF0IjoxNzI4MjExNTUwLCJleHAiOjE3MzA4MDM1NTB9.RIhdUfqAN-oUj5YWZLEQjv6V7aZinCevLj_xBJvbSlw	wagner@gmail.com
3011	2024-11-05 10:46:37.33899+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgyMTE1OTcsImV4cCI6MTczMDgwMzU5N30.R_Cgz46iREXPXg0dBdzok3SWUERkQyvnAJZBeGikwdw	leosagir@gmail.com
3012	2024-11-05 10:47:33.883386+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzY2huZWlkZXJAZ21haWwuY29tIiwiaWF0IjoxNzI4MjExNjUzLCJleHAiOjE3MzA4MDM2NTN9.RlkwAi2m3ar7JXNkUFYvRZJbj4V89_NHRozbzO_qLSU	schneider@gmail.com
3013	2024-11-05 10:48:06.889904+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzY2huZWlkZXJAZ21haWwuY29tIiwiaWF0IjoxNzI4MjExNjg2LCJleHAiOjE3MzA4MDM2ODZ9.8xVFYb--alA5zn3o3kcoZbTtj5fY7GnmLlWmFY3nec8	schneider@gmail.com
3014	2024-11-05 10:48:40.986574+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJob2ZtYW5uQGdtYWlsLmNvbSIsImlhdCI6MTcyODIxMTcyMCwiZXhwIjoxNzMwODAzNzIwfQ.9x0bUAS3SAKxNqMdDSswis2j8u8HrSiikLNGOHWAH38	hofmann@gmail.com
3015	2024-11-05 11:23:59.549582+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgyMTM4MzksImV4cCI6MTczMDgwNTgzOX0.lmnDlLI0sV9z-NdqpNmnqdI4g1rTEKVl_lmonGYYsdQ	leosagir@gmail.com
3016	2024-11-05 12:06:58.457633+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MjE2NDE4LCJleHAiOjE3MzA4MDg0MTh9.-tGbyoIPfyOsmUFn-LDY_7MUkhXQ1DwbaDbMx8qFYgw	leo@gmail.com
3052	2024-11-05 15:41:42.857401+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXZpZEBnbWFpbC5jb20iLCJpYXQiOjE3MjgyMjkzMDIsImV4cCI6MTczMDgyMTMwMn0.jUxvJukKkbuIelnlLRE-Hff8Ep30vR7saTdUMr8Qnmk	david@gmail.com
3053	2024-11-05 15:57:35.650661+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXZpZEBnbWFpbC5jb20iLCJpYXQiOjE3MjgyMzAyNTUsImV4cCI6MTczMDgyMjI1NX0.9nWedULfmnRegWbUkNyBR19jCi5kYYXvR7g7jwMJIxk	david@gmail.com
3054	2024-11-05 16:06:28.328848+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXZpZEBnbWFpbC5jb20iLCJpYXQiOjE3MjgyMzA3ODgsImV4cCI6MTczMDgyMjc4OH0.9QIzAi6vCHkythR5HDTBVHnhIRXQ7rE-ZjrWMsUvgXk	david@gmail.com
3055	2024-11-05 16:07:20.18327+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MjMwODQwLCJleHAiOjE3MzA4MjI4NDB9.nn_I7MASw0Y8YK20WW5EbY3OzMFawwTlEHUE2SoRf24	leo@gmail.com
3056	2024-11-05 16:08:46.398042+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MjMwOTI2LCJleHAiOjE3MzA4MjI5MjZ9.MMqNyWBDBowuqbJE0BGWqfcauejOSRMGNkqRPZZXkGo	leo@gmail.com
3057	2024-11-05 16:16:50.024679+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MjMxNDEwLCJleHAiOjE3MzA4MjM0MTB9.7H4sa7GsxpsbtTnv4YjbfUePM8D5rJip8mcDQ0oPD9s	leo@gmail.com
3058	2024-11-05 16:18:05.469013+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MjMxNDg1LCJleHAiOjE3MzA4MjM0ODV9.2zHwmKM5wObpQTRQgVFR0B2amQsKkn37-pfhl0LZgFs	leo@gmail.com
3102	2024-11-06 07:09:33.768588+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4Mjg0OTczLCJleHAiOjE3MzA4NzY5NzN9.9fotKKvEVae3IRJSGvxf_RuSz3c9_WQ3KXTmRKITaDk	leo@gmail.com
3103	2024-11-06 07:13:42.648847+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4Mjg1MjIyLCJleHAiOjE3MzA4NzcyMjJ9.nkgKIv7rSXUQ7fjFrRqY5t_aNP91mlDlskWNowsnU3g	leo@gmail.com
3104	2024-11-06 07:15:24.968418+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4Mjg1MzI0LCJleHAiOjE3MzA4NzczMjR9.f1qjsx7sLibdGQQLC4YIAOTRoIPBNZ7Tmq5rC9gr7EA	leo@gmail.com
3152	2024-11-06 07:45:40.232757+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4Mjg3MTQwLCJleHAiOjE3MzA4NzkxNDB9.ZhBiGYdFngLOQoYM6xcvWnNtpG7FEo08dN5xdJWwupQ	leo@gmail.com
3202	2024-11-06 14:34:25.761057+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MzExNjY1LCJleHAiOjE3MzA5MDM2NjV9.i_WRvbfeeqYfCm-33Gg5WF65oi1cE1zS7eJBEQ-PgMQ	leo@gmail.com
3203	2024-11-06 14:35:02.163882+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YWduZXJAZ21haWwuY29tIiwiaWF0IjoxNzI4MzExNzAyLCJleHAiOjE3MzA5MDM3MDJ9.OcKl7UbYH6n5MXhJn-fRFNCdp3Vh-IaHvY82jW7VJcE	wagner@gmail.com
3204	2024-11-06 14:35:27.92575+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzY2huZWlkZXJAZ21haWwuY29tIiwiaWF0IjoxNzI4MzExNzI3LCJleHAiOjE3MzA5MDM3Mjd9.sxeoo6SW_fyj56yukgPAeP6_SFQ6y9uuDzcU_3L3Krs	schneider@gmail.com
3205	2024-11-06 14:35:44.672559+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzY2huZWlkZXJAZ21haWwuY29tIiwiaWF0IjoxNzI4MzExNzQ0LCJleHAiOjE3MzA5MDM3NDR9.8-TNUQWairuQyktin5teOf_gKiUC4dj6ia0KVZI3HgA	schneider@gmail.com
3206	2024-11-06 14:36:25.695935+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgzMTE3ODUsImV4cCI6MTczMDkwMzc4NX0.-lbH7KpwgpCwqbGpjsR5rsfE8QHj4BPQi-HpQLWXsHY	leosagir@gmail.com
3252	2024-11-06 19:08:27.655094+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgzMjgxMDcsImV4cCI6MTczMDkyMDEwN30.lj8QzUHVlw_LHtcyoDldgia7nX2F6TaF6WV6KkgMQao	leosagir@gmail.com
3253	2024-11-06 19:20:31.568206+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3MjgzMjg4MzEsImV4cCI6MTczMDkyMDgzMX0.cHNZ43QAhjT89zqxz5BtD5G4bCMhesna8s6Kb7_MYXc	weber@gmail.com
3254	2024-11-06 19:23:25.676679+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MzI5MDA1LCJleHAiOjE3MzA5MjEwMDV9.dSqcw7CGQbJ3moBdHJjE982v6n5WmOmqfvmCnnXAQRY	leo@gmail.com
3255	2024-11-06 19:24:33.167866+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AZ21haWwuY29tIiwiaWF0IjoxNzI4MzI5MDczLCJleHAiOjE3MzA5MjEwNzN9.tY4Ik8g_3GkMC_JnnxOPL7VHpBZLStGW2L33YoLHI0k	leo@gmail.com
3256	2024-11-06 19:29:41.771982+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyQGdtYWlsLmNvbSIsImlhdCI6MTcyODMyOTM4MSwiZXhwIjoxNzMwOTIxMzgxfQ.n4MEjkIWkOqQaIQUjYkkIfcLFx-Z5ppq6n4j-k6JXRc	petr@gmail.com
3257	2024-11-06 19:42:01.404156+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyQGdtYWlsLmNvbSIsImlhdCI6MTcyODMzMDEyMSwiZXhwIjoxNzMwOTIyMTIxfQ.-0Hn1XAmTW8fThMPB3kZHcyr8LftSmh1-N3E_FT4V0c	petr@gmail.com
3258	2024-11-06 19:45:24.950486+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgzMzAzMjQsImV4cCI6MTczMDkyMjMyNH0.5TMd3KGGGVdoe3_d4GXNJ3qIKRdFjuTtvA5xmnhOlA4	leosagir@gmail.com
3259	2024-11-06 19:50:16.573011+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyQGdtYWlsLmNvbSIsImlhdCI6MTcyODMzMDYxNiwiZXhwIjoxNzMwOTIyNjE2fQ.DICvXX8Skx8khHmoJvLa8ue0y3yEXsurwcgQrxgbRzQ	petr@gmail.com
3260	2024-11-06 19:54:23.64147+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyQGdtYWlsLmNvbSIsImlhdCI6MTcyODMzMDg2MywiZXhwIjoxNzMwOTIyODYzfQ.gechlCo7Aqimnccx2q9UENO8T3EOQeEiUvzSRPmYdLE	petr@gmail.com
3261	2024-11-06 20:04:44.890951+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YWduZXJAZ21haWwuY29tIiwiaWF0IjoxNzI4MzMxNDg0LCJleHAiOjE3MzA5MjM0ODR9.AcGjz4IMn4uPnwOZ0CnRkEid7J_wlcH8g3o1vp86ZuA	wagner@gmail.com
3302	2024-11-07 07:51:22.725982+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgzNzM4ODIsImV4cCI6MTczMDk2NTg4Mn0.iAh8JMbz6IgNIgxlgy8srmBofel8z5FA1i6BJZdDodM	leosagir@gmail.com
3303	2024-11-07 08:33:04.887732+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyQGdtYWlsLmNvbSIsImlhdCI6MTcyODM3NjM4NCwiZXhwIjoxNzMwOTY4Mzg0fQ.YSy0PAkfzV3aD4_YZujV4d2Omd8zt6OE-aQ1L6MO9uk	petr@gmail.com
3304	2024-11-07 08:34:48.666192+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3MjgzNzY0ODgsImV4cCI6MTczMDk2ODQ4OH0.immjALMUxU9mF-ydm6KWPq5IuDtnHOJxrulnkbuHStE	weber@gmail.com
3305	2024-11-07 08:35:57.241634+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3MjgzNzY1NTcsImV4cCI6MTczMDk2ODU1N30.732-0ma-g1d3uGllxniVTMo3qzzwpigTTdfWQ5v279c	leosagir@gmail.com
3306	2024-11-07 15:57:54.29298+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyQGdtYWlsLmNvbSIsImlhdCI6MTcyODQwMzA3NCwiZXhwIjoxNzMwOTk1MDc0fQ._Axmi4FcvUu9AP5YwIX_p0mrqgexNfZjThEiSrX09G8	petr@gmail.com
3307	2024-11-07 15:59:02.567466+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZWJlckBnbWFpbC5jb20iLCJpYXQiOjE3Mjg0MDMxNDIsImV4cCI6MTczMDk5NTE0Mn0.7KvYev1ndkAncSFKOLHBYoUPv7xs3LSTeFuLinZPMYg	weber@gmail.com
3308	2024-11-07 15:59:54.581674+00	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9zYWdpckBnbWFpbC5jb20iLCJpYXQiOjE3Mjg0MDMxOTQsImV4cCI6MTczMDk5NTE5NH0.fWLJyrobmyGXrwp6bUya2JTTMLf8ntwfWeOV6CNtInc	leosagir@gmail.com
\.


--
-- Data for Name: specialist_service; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.specialist_service (specialist_id, service_id) FROM stdin;
1	16
1	21
1	11
1	22
1	20
1	1
1	4
1	13
1	17
1	19
1	26
1	15
1	23
1	25
1	5
1	18
1	2
1	3
1	24
1	14
1	12
2	4
2	29
2	2
2	28
2	3
2	30
2	1
2	5
2	27
2	31
3	8
3	9
3	10
3	17
3	28
3	20
3	19
3	27
3	7
3	16
3	18
3	30
3	21
3	6
3	29
3	31
4	26
4	15
4	14
4	25
4	13
4	24
4	22
4	23
4	11
4	12
5	31
5	3
5	8
5	2
5	16
5	1
5	7
5	21
5	9
5	17
5	29
5	27
5	6
5	5
5	20
5	10
5	28
5	30
5	4
5	18
5	19
6	14
6	12
6	22
6	15
6	24
6	25
6	11
6	23
6	13
6	26
7	5
7	17
7	20
7	28
7	6
7	1
7	31
7	9
7	10
7	7
7	4
7	29
7	3
7	8
7	19
7	18
7	16
7	27
7	2
7	30
7	21
\.


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
-- Data for Name: t_admin; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_admin (id, address, created_at, date_of_birth, email, first_name, last_name, password, phone, role, status, updated_at) FROM stdin;
1	Administrator street 1	2024-09-29 09:30:16	1983-06-09	leosagir@gmail.com	Leonid	Sahirov	$2a$12$eaQQlkbBE/ngI7G2f44Glel/vV9WeglxICMw63ymfpa6VPS3ZclDO	+4917812309865	ADMINISTRATOR	ACTIVE	2024-09-29 09:30:08
2	Adminstraße 1	2024-10-04 13:12:00.987514	2000-01-01	admin@gmail.com	Admin	Admin	$2a$10$Zj5JNCF7F0NQ1uTWUNjxjOwmVlC570MFWdRet5fJb7GF8SKhr9M6e	+4919097846523	ADMINISTRATOR	ACTIVE	2024-10-04 13:12:26.074446
\.


--
-- Data for Name: t_appointment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_appointment (id, status, created_at, end_time, start_time, updated_at, client_id, service_id, specialist_id) FROM stdin;
646	AVAILABLE	2024-10-08 16:12:44.565917	2024-10-23 14:00:41	2024-10-23 13:00:41	2024-10-08 16:12:44.565917	\N	\N	4
7	AVAILABLE	2024-09-29 15:01:06.859863	2024-09-30 10:00:53	2024-09-30 09:00:53	2024-09-29 15:01:06.859863	\N	\N	2
8	AVAILABLE	2024-09-29 15:01:06.950603	2024-09-30 11:00:53	2024-09-30 10:00:53	2024-09-29 15:01:06.950603	\N	\N	2
9	AVAILABLE	2024-09-29 15:01:07.197749	2024-09-30 12:00:53	2024-09-30 11:00:53	2024-09-29 15:01:07.197749	\N	\N	2
10	AVAILABLE	2024-09-29 15:01:07.28992	2024-09-30 16:00:53	2024-09-30 15:00:53	2024-09-29 15:01:07.28992	\N	\N	2
11	AVAILABLE	2024-09-29 15:01:07.543432	2024-09-30 17:00:53	2024-09-30 16:00:53	2024-09-29 15:01:07.543432	\N	\N	2
652	AVAILABLE	2024-10-08 16:12:45.588564	2024-10-23 20:00:41	2024-10-23 19:00:41	2024-10-08 16:12:45.588564	\N	\N	4
655	AVAILABLE	2024-10-08 16:12:46.203012	2024-10-24 11:00:41	2024-10-24 10:00:41	2024-10-08 16:12:46.203012	\N	\N	4
14	AVAILABLE	2024-09-29 15:01:07.982165	2024-10-01 11:00:53	2024-10-01 10:00:53	2024-09-29 15:01:07.982165	\N	\N	2
15	AVAILABLE	2024-09-29 15:01:08.243128	2024-10-01 12:00:53	2024-10-01 11:00:53	2024-09-29 15:01:08.243128	\N	\N	2
16	AVAILABLE	2024-09-29 15:01:08.319728	2024-10-01 16:00:53	2024-10-01 15:00:53	2024-09-29 15:01:08.320255	\N	\N	2
17	AVAILABLE	2024-09-29 15:01:08.585655	2024-10-01 17:00:53	2024-10-01 16:00:53	2024-09-29 15:01:08.585655	\N	\N	2
18	AVAILABLE	2024-09-29 15:01:08.665561	2024-10-02 09:00:53	2024-10-02 08:00:53	2024-09-29 15:01:08.665561	\N	\N	2
21	AVAILABLE	2024-09-29 15:01:09.271815	2024-10-02 12:00:53	2024-10-02 11:00:53	2024-09-29 15:01:09.271815	\N	\N	2
22	AVAILABLE	2024-09-29 15:01:09.348631	2024-10-02 16:00:53	2024-10-02 15:00:53	2024-09-29 15:01:09.348631	\N	\N	2
23	AVAILABLE	2024-09-29 15:01:09.618136	2024-10-02 17:00:53	2024-10-02 16:00:53	2024-09-29 15:01:09.618136	\N	\N	2
24	AVAILABLE	2024-09-29 15:01:09.6961	2024-10-03 09:00:53	2024-10-03 08:00:53	2024-09-29 15:01:09.6961	\N	\N	2
25	AVAILABLE	2024-09-29 15:01:09.960075	2024-10-03 10:00:53	2024-10-03 09:00:53	2024-09-29 15:01:09.960075	\N	\N	2
26	AVAILABLE	2024-09-29 15:01:10.038449	2024-10-03 11:00:53	2024-10-03 10:00:53	2024-09-29 15:01:10.038449	\N	\N	2
27	AVAILABLE	2024-09-29 15:01:10.302523	2024-10-03 12:00:53	2024-10-03 11:00:53	2024-09-29 15:01:10.302523	\N	\N	2
28	AVAILABLE	2024-09-29 15:01:10.381481	2024-10-03 16:00:53	2024-10-03 15:00:53	2024-09-29 15:01:10.381481	\N	\N	2
29	AVAILABLE	2024-09-29 15:01:10.646031	2024-10-03 17:00:53	2024-10-03 16:00:53	2024-09-29 15:01:10.646031	\N	\N	2
30	AVAILABLE	2024-10-01 09:47:59.071569	2024-10-02 09:00:53	2024-10-02 08:00:53	2024-10-01 09:47:59.071569	\N	\N	3
31	AVAILABLE	2024-10-01 09:47:59.321859	2024-10-02 10:00:53	2024-10-02 09:00:53	2024-10-01 09:47:59.321859	\N	\N	3
32	AVAILABLE	2024-10-01 09:47:59.444462	2024-10-02 11:00:53	2024-10-02 10:00:53	2024-10-01 09:47:59.444462	\N	\N	3
33	AVAILABLE	2024-10-01 09:47:59.678795	2024-10-02 12:00:53	2024-10-02 11:00:53	2024-10-01 09:47:59.678795	\N	\N	3
34	AVAILABLE	2024-10-01 09:47:59.79925	2024-10-02 14:00:53	2024-10-02 13:00:53	2024-10-01 09:47:59.79925	\N	\N	3
35	AVAILABLE	2024-10-01 09:48:00.032284	2024-10-02 15:00:53	2024-10-02 14:00:53	2024-10-01 09:48:00.032284	\N	\N	3
656	AVAILABLE	2024-10-08 16:12:46.254313	2024-10-24 12:00:41	2024-10-24 11:00:41	2024-10-08 16:12:46.254313	\N	\N	4
662	AVAILABLE	2024-10-08 16:12:47.282331	2024-10-24 19:00:41	2024-10-24 18:00:41	2024-10-08 16:12:47.282331	\N	\N	4
665	AVAILABLE	2024-10-08 16:12:47.89072	2024-10-25 10:00:41	2024-10-25 09:00:41	2024-10-08 16:12:47.89072	\N	\N	4
666	AVAILABLE	2024-10-08 16:12:47.955286	2024-10-25 11:00:41	2024-10-25 10:00:41	2024-10-08 16:12:47.955286	\N	\N	4
672	AVAILABLE	2024-10-08 16:12:48.988499	2024-10-25 18:00:41	2024-10-25 17:00:41	2024-10-08 16:12:48.988499	\N	\N	4
19	AVAILABLE	2024-09-29 15:01:08.929414	2024-10-02 10:00:53	2024-10-02 09:00:53	2024-10-01 20:52:14.379677	\N	\N	2
38	AVAILABLE	2024-10-02 11:57:10.295627	2024-10-02 15:57:00	2024-10-02 13:56:00	2024-10-02 11:57:10.295627	\N	\N	1
675	AVAILABLE	2024-10-08 16:12:49.604164	2024-10-28 09:00:41	2024-10-28 08:00:41	2024-10-08 16:12:49.604164	\N	\N	4
676	AVAILABLE	2024-10-08 16:12:49.685344	2024-10-28 10:00:41	2024-10-28 09:00:41	2024-10-08 16:12:49.685344	\N	\N	4
682	AVAILABLE	2024-10-08 16:12:50.723492	2024-10-28 17:00:41	2024-10-28 16:00:41	2024-10-08 16:12:50.723492	\N	\N	4
5	COMPLETED	2024-09-29 14:44:58.45669	2024-09-30 15:41:00	2024-09-30 14:41:00	2024-10-05 20:00:47.953141	1	\N	2
37	COMPLETED	2024-10-01 09:48:00.388747	2024-10-02 17:00:53	2024-10-02 16:00:53	2024-10-05 20:00:47.969975	2	\N	3
41	AVAILABLE	2024-10-04 11:15:47.20922	2024-10-04 14:00:37	2024-10-04 13:00:37	2024-10-04 11:15:47.20922	\N	\N	6
42	AVAILABLE	2024-10-04 11:15:47.300587	2024-10-04 16:00:37	2024-10-04 15:00:37	2024-10-04 11:15:47.300587	\N	\N	6
43	AVAILABLE	2024-10-04 11:15:47.552394	2024-10-04 17:00:37	2024-10-04 16:00:37	2024-10-04 11:15:47.552394	\N	\N	6
36	COMPLETED	2024-10-01 09:48:00.160687	2024-10-02 16:00:53	2024-10-02 15:00:53	2024-10-05 20:00:47.974656	2	\N	3
20	COMPLETED	2024-09-29 15:01:09.006193	2024-10-02 11:00:53	2024-10-02 10:00:53	2024-10-05 20:00:47.979561	2	\N	2
44	COMPLETED	2024-10-05 12:02:05.953525	2024-10-07 09:00:47	2024-10-07 08:00:47	2024-10-07 09:01:40.824206	1	\N	2
40	AVAILABLE	2024-10-04 11:15:46.957642	2024-10-04 13:00:37	2024-10-04 12:00:37	2024-10-04 13:09:44.268378	\N	\N	6
45	AVAILABLE	2024-10-05 12:02:06.185695	2024-10-07 10:00:47	2024-10-07 09:00:47	2024-10-05 12:02:06.185695	\N	\N	2
46	AVAILABLE	2024-10-05 12:02:06.329897	2024-10-07 11:00:47	2024-10-07 10:00:47	2024-10-05 12:02:06.329897	\N	\N	2
47	AVAILABLE	2024-10-05 12:02:06.52965	2024-10-07 12:00:47	2024-10-07 11:00:47	2024-10-05 12:02:06.52965	\N	\N	2
48	AVAILABLE	2024-10-05 12:02:06.683613	2024-10-07 14:00:47	2024-10-07 13:00:47	2024-10-05 12:02:06.683613	\N	\N	2
49	AVAILABLE	2024-10-05 12:02:06.880381	2024-10-07 15:00:47	2024-10-07 14:00:47	2024-10-05 12:02:06.880381	\N	\N	2
50	AVAILABLE	2024-10-05 12:02:07.032874	2024-10-07 16:00:47	2024-10-07 15:00:47	2024-10-05 12:02:07.032874	\N	\N	2
51	AVAILABLE	2024-10-05 12:02:07.230874	2024-10-07 17:00:47	2024-10-07 16:00:47	2024-10-05 12:02:07.230874	\N	\N	2
52	AVAILABLE	2024-10-05 12:02:07.38241	2024-10-08 09:00:47	2024-10-08 08:00:47	2024-10-05 12:02:07.38241	\N	\N	2
53	AVAILABLE	2024-10-05 12:02:07.583661	2024-10-08 10:00:47	2024-10-08 09:00:47	2024-10-05 12:02:07.583661	\N	\N	2
54	AVAILABLE	2024-10-05 12:02:07.725971	2024-10-08 11:00:47	2024-10-08 10:00:47	2024-10-05 12:02:07.725971	\N	\N	2
55	AVAILABLE	2024-10-05 12:02:07.943841	2024-10-08 12:00:47	2024-10-08 11:00:47	2024-10-05 12:02:07.943841	\N	\N	2
56	AVAILABLE	2024-10-05 12:02:08.082997	2024-10-08 14:00:47	2024-10-08 13:00:47	2024-10-05 12:02:08.082997	\N	\N	2
57	AVAILABLE	2024-10-05 12:02:08.29734	2024-10-08 15:00:47	2024-10-08 14:00:47	2024-10-05 12:02:08.29734	\N	\N	2
58	AVAILABLE	2024-10-05 12:02:08.429612	2024-10-08 16:00:47	2024-10-08 15:00:47	2024-10-05 12:02:08.429612	\N	\N	2
59	AVAILABLE	2024-10-05 12:02:08.644936	2024-10-08 17:00:47	2024-10-08 16:00:47	2024-10-05 12:02:08.644936	\N	\N	2
60	AVAILABLE	2024-10-05 12:02:08.780666	2024-10-09 09:00:47	2024-10-09 08:00:47	2024-10-05 12:02:08.780666	\N	\N	2
61	AVAILABLE	2024-10-05 12:02:08.993716	2024-10-09 10:00:47	2024-10-09 09:00:47	2024-10-05 12:02:08.993716	\N	\N	2
62	AVAILABLE	2024-10-05 12:02:09.132649	2024-10-09 11:00:47	2024-10-09 10:00:47	2024-10-05 12:02:09.132649	\N	\N	2
63	AVAILABLE	2024-10-05 12:02:09.34574	2024-10-09 12:00:47	2024-10-09 11:00:47	2024-10-05 12:02:09.34574	\N	\N	2
64	AVAILABLE	2024-10-05 12:02:09.480201	2024-10-09 14:00:47	2024-10-09 13:00:47	2024-10-05 12:02:09.480729	\N	\N	2
65	AVAILABLE	2024-10-05 12:02:09.696016	2024-10-09 15:00:47	2024-10-09 14:00:47	2024-10-05 12:02:09.696016	\N	\N	2
66	AVAILABLE	2024-10-05 12:02:09.832339	2024-10-09 16:00:47	2024-10-09 15:00:47	2024-10-05 12:02:09.832339	\N	\N	2
67	AVAILABLE	2024-10-05 12:02:10.047335	2024-10-09 17:00:47	2024-10-09 16:00:47	2024-10-05 12:02:10.047335	\N	\N	2
69	AVAILABLE	2024-10-05 12:02:10.396447	2024-10-10 10:00:47	2024-10-10 09:00:47	2024-10-05 12:02:10.396447	\N	\N	2
70	AVAILABLE	2024-10-05 12:02:10.528993	2024-10-10 11:00:47	2024-10-10 10:00:47	2024-10-05 12:02:10.528993	\N	\N	2
71	AVAILABLE	2024-10-05 12:02:10.742489	2024-10-10 12:00:47	2024-10-10 11:00:47	2024-10-05 12:02:10.742489	\N	\N	2
68	BOOKED	2024-10-05 12:02:10.180488	2024-10-10 09:00:47	2024-10-10 08:00:47	2024-10-07 21:43:23.785702	2	\N	2
685	AVAILABLE	2024-10-08 16:12:51.333106	2024-10-28 20:00:41	2024-10-28 19:00:41	2024-10-08 16:12:51.333106	\N	\N	4
686	AVAILABLE	2024-10-08 16:12:51.422259	2024-10-29 09:00:41	2024-10-29 08:00:41	2024-10-08 16:12:51.422259	\N	\N	4
692	AVAILABLE	2024-10-08 16:12:52.456188	2024-10-29 16:00:41	2024-10-29 15:00:41	2024-10-08 16:12:52.456188	\N	\N	4
695	AVAILABLE	2024-10-08 16:12:53.067809	2024-10-29 19:00:41	2024-10-29 18:00:41	2024-10-08 16:12:53.067809	\N	\N	4
696	AVAILABLE	2024-10-08 16:12:53.15616	2024-10-29 20:00:41	2024-10-29 19:00:41	2024-10-08 16:12:53.15616	\N	\N	4
702	AVAILABLE	2024-10-08 16:12:54.204047	2024-10-30 15:00:41	2024-10-30 14:00:41	2024-10-08 16:12:54.204047	\N	\N	4
72	AVAILABLE	2024-10-05 12:02:10.880635	2024-10-10 14:00:47	2024-10-10 13:00:47	2024-10-05 12:02:10.880635	\N	\N	2
74	AVAILABLE	2024-10-05 12:02:11.230672	2024-10-10 16:00:47	2024-10-10 15:00:47	2024-10-05 12:02:11.230672	\N	\N	2
75	AVAILABLE	2024-10-05 12:02:11.445896	2024-10-10 17:00:47	2024-10-10 16:00:47	2024-10-05 12:02:11.445896	\N	\N	2
76	AVAILABLE	2024-10-05 12:02:11.579134	2024-10-11 09:00:47	2024-10-11 08:00:47	2024-10-05 12:02:11.579134	\N	\N	2
77	AVAILABLE	2024-10-05 12:02:11.800026	2024-10-11 10:00:47	2024-10-11 09:00:47	2024-10-05 12:02:11.800026	\N	\N	2
82	AVAILABLE	2024-10-05 12:02:12.63039	2024-10-11 16:00:47	2024-10-11 15:00:47	2024-10-05 12:02:12.63039	\N	\N	2
647	AVAILABLE	2024-10-08 16:12:44.834887	2024-10-23 15:00:41	2024-10-23 14:00:41	2024-10-08 16:12:44.834887	\N	\N	4
648	AVAILABLE	2024-10-08 16:12:44.90349	2024-10-23 16:00:41	2024-10-23 15:00:41	2024-10-08 16:12:44.90349	\N	\N	4
650	AVAILABLE	2024-10-08 16:12:45.238066	2024-10-23 18:00:41	2024-10-23 17:00:41	2024-10-08 16:12:45.238066	\N	\N	4
654	AVAILABLE	2024-10-08 16:12:45.921496	2024-10-24 10:00:41	2024-10-24 09:00:41	2024-10-08 16:12:45.921496	\N	\N	4
657	AVAILABLE	2024-10-08 16:12:46.548452	2024-10-24 14:00:41	2024-10-24 13:00:41	2024-10-08 16:12:46.548452	\N	\N	4
658	AVAILABLE	2024-10-08 16:12:46.597826	2024-10-24 15:00:41	2024-10-24 14:00:41	2024-10-08 16:12:46.597826	\N	\N	4
660	AVAILABLE	2024-10-08 16:12:46.937265	2024-10-24 17:00:41	2024-10-24 16:00:41	2024-10-08 16:12:46.937265	\N	\N	4
664	AVAILABLE	2024-10-08 16:12:47.620961	2024-10-25 09:00:41	2024-10-25 08:00:41	2024-10-08 16:12:47.620961	\N	\N	4
667	AVAILABLE	2024-10-08 16:12:48.225983	2024-10-25 12:00:41	2024-10-25 11:00:41	2024-10-08 16:12:48.225983	\N	\N	4
668	AVAILABLE	2024-10-08 16:12:48.290931	2024-10-25 14:00:41	2024-10-25 13:00:41	2024-10-08 16:12:48.290931	\N	\N	4
670	AVAILABLE	2024-10-08 16:12:48.638659	2024-10-25 16:00:41	2024-10-25 15:00:41	2024-10-08 16:12:48.638659	\N	\N	4
674	AVAILABLE	2024-10-08 16:12:49.338685	2024-10-25 20:00:41	2024-10-25 19:00:41	2024-10-08 16:12:49.338685	\N	\N	4
677	AVAILABLE	2024-10-08 16:12:49.951707	2024-10-28 11:00:41	2024-10-28 10:00:41	2024-10-08 16:12:49.951707	\N	\N	4
678	AVAILABLE	2024-10-08 16:12:50.039087	2024-10-28 12:00:41	2024-10-28 11:00:41	2024-10-08 16:12:50.039087	\N	\N	4
680	AVAILABLE	2024-10-08 16:12:50.387677	2024-10-28 15:00:41	2024-10-28 14:00:41	2024-10-08 16:12:50.387677	\N	\N	4
684	AVAILABLE	2024-10-08 16:12:51.072877	2024-10-28 19:00:41	2024-10-28 18:00:41	2024-10-08 16:12:51.072877	\N	\N	4
687	AVAILABLE	2024-10-08 16:12:51.670335	2024-10-29 10:00:41	2024-10-29 09:00:41	2024-10-08 16:12:51.670335	\N	\N	4
688	AVAILABLE	2024-10-08 16:12:51.769434	2024-10-29 11:00:41	2024-10-29 10:00:41	2024-10-08 16:12:51.769434	\N	\N	4
690	AVAILABLE	2024-10-08 16:12:52.103932	2024-10-29 14:00:41	2024-10-29 13:00:41	2024-10-08 16:12:52.103932	\N	\N	4
694	AVAILABLE	2024-10-08 16:12:52.804358	2024-10-29 18:00:41	2024-10-29 17:00:41	2024-10-08 16:12:52.804358	\N	\N	4
697	AVAILABLE	2024-10-08 16:12:53.417859	2024-10-30 09:00:41	2024-10-30 08:00:41	2024-10-08 16:12:53.417859	\N	\N	4
698	AVAILABLE	2024-10-08 16:12:53.505671	2024-10-30 10:00:41	2024-10-30 09:00:41	2024-10-08 16:12:53.505671	\N	\N	4
700	AVAILABLE	2024-10-08 16:12:53.856661	2024-10-30 12:00:41	2024-10-30 11:00:41	2024-10-08 16:12:53.856661	\N	\N	4
704	AVAILABLE	2024-10-08 16:12:54.554409	2024-10-30 17:00:41	2024-10-30 16:00:41	2024-10-08 16:12:54.554409	\N	\N	4
707	AVAILABLE	2024-10-08 16:12:55.167746	2024-10-30 20:00:41	2024-10-30 19:00:41	2024-10-08 16:12:55.167746	\N	\N	4
793	AVAILABLE	2024-10-08 18:02:51.770001	2024-10-22 14:00:00	2024-10-22 13:00:00	2024-10-08 18:02:51.770001	\N	\N	7
796	AVAILABLE	2024-10-08 18:02:52.185407	2024-10-22 17:00:00	2024-10-22 16:00:00	2024-10-08 18:02:52.185407	\N	\N	7
803	AVAILABLE	2024-10-08 18:02:53.472713	2024-10-23 15:00:00	2024-10-23 14:00:00	2024-10-08 18:02:53.472713	\N	\N	7
806	AVAILABLE	2024-10-08 18:02:53.903839	2024-10-23 18:00:00	2024-10-23 17:00:00	2024-10-08 18:02:53.903839	\N	\N	7
813	AVAILABLE	2024-10-08 18:02:55.175458	2024-10-24 16:00:00	2024-10-24 15:00:00	2024-10-08 18:02:55.175458	\N	\N	7
816	AVAILABLE	2024-10-08 18:02:55.608624	2024-10-25 09:00:00	2024-10-25 08:00:00	2024-10-08 18:02:55.608624	\N	\N	7
823	AVAILABLE	2024-10-08 18:02:56.883419	2024-10-25 17:00:00	2024-10-25 16:00:00	2024-10-08 18:02:56.883419	\N	\N	7
826	AVAILABLE	2024-10-08 18:02:57.311362	2024-10-28 10:00:00	2024-10-28 09:00:00	2024-10-08 18:02:57.311362	\N	\N	7
833	AVAILABLE	2024-10-08 18:02:58.580954	2024-10-28 18:00:00	2024-10-28 17:00:00	2024-10-08 18:02:58.580954	\N	\N	7
836	AVAILABLE	2024-10-08 18:02:59.01461	2024-10-29 11:00:00	2024-10-29 10:00:00	2024-10-08 18:02:59.01461	\N	\N	7
843	AVAILABLE	2024-10-08 18:03:00.288456	2024-10-30 09:00:00	2024-10-30 08:00:00	2024-10-08 18:03:00.288456	\N	\N	7
846	AVAILABLE	2024-10-08 18:03:00.73311	2024-10-30 12:00:00	2024-10-30 11:00:00	2024-10-08 18:03:00.73311	\N	\N	7
853	AVAILABLE	2024-10-08 18:03:02.021984	2024-10-31 10:00:00	2024-10-31 09:00:00	2024-10-08 18:03:02.021984	\N	\N	7
856	AVAILABLE	2024-10-08 18:03:02.471212	2024-10-31 14:00:00	2024-10-31 13:00:00	2024-10-08 18:03:02.471212	\N	\N	7
78	AVAILABLE	2024-10-05 12:02:11.932348	2024-10-11 11:00:47	2024-10-11 10:00:47	2024-10-05 12:02:11.932348	\N	\N	2
80	AVAILABLE	2024-10-05 12:02:12.277598	2024-10-11 14:00:47	2024-10-11 13:00:47	2024-10-05 12:02:12.277598	\N	\N	2
81	AVAILABLE	2024-10-05 12:02:12.495135	2024-10-11 15:00:47	2024-10-11 14:00:47	2024-10-05 12:02:12.495135	\N	\N	2
83	AVAILABLE	2024-10-05 12:02:12.847243	2024-10-11 17:00:47	2024-10-11 16:00:47	2024-10-05 12:02:12.847243	\N	\N	2
73	BOOKED	2024-10-05 12:02:11.093973	2024-10-10 15:00:47	2024-10-10 14:00:47	2024-10-07 09:45:58.856617	1	\N	2
649	AVAILABLE	2024-10-08 16:12:45.170803	2024-10-23 17:00:41	2024-10-23 16:00:41	2024-10-08 16:12:45.170803	\N	\N	4
653	AVAILABLE	2024-10-08 16:12:45.864108	2024-10-24 09:00:41	2024-10-24 08:00:41	2024-10-08 16:12:45.864108	\N	\N	4
659	AVAILABLE	2024-10-08 16:12:46.882329	2024-10-24 16:00:41	2024-10-24 15:00:41	2024-10-08 16:12:46.882329	\N	\N	4
663	AVAILABLE	2024-10-08 16:12:47.553084	2024-10-24 20:00:41	2024-10-24 19:00:41	2024-10-08 16:12:47.553084	\N	\N	4
669	AVAILABLE	2024-10-08 16:12:48.568125	2024-10-25 15:00:41	2024-10-25 14:00:41	2024-10-08 16:12:48.568125	\N	\N	4
673	AVAILABLE	2024-10-08 16:12:49.256668	2024-10-25 19:00:41	2024-10-25 18:00:41	2024-10-08 16:12:49.256668	\N	\N	4
679	AVAILABLE	2024-10-08 16:12:50.287279	2024-10-28 14:00:41	2024-10-28 13:00:41	2024-10-08 16:12:50.287279	\N	\N	4
683	AVAILABLE	2024-10-08 16:12:50.983724	2024-10-28 18:00:41	2024-10-28 17:00:41	2024-10-08 16:12:50.983724	\N	\N	4
689	AVAILABLE	2024-10-08 16:12:52.015127	2024-10-29 12:00:41	2024-10-29 11:00:41	2024-10-08 16:12:52.015127	\N	\N	4
693	AVAILABLE	2024-10-08 16:12:52.71781	2024-10-29 17:00:41	2024-10-29 16:00:41	2024-10-08 16:12:52.71781	\N	\N	4
699	AVAILABLE	2024-10-08 16:12:53.767167	2024-10-30 11:00:41	2024-10-30 10:00:41	2024-10-08 16:12:53.767167	\N	\N	4
703	AVAILABLE	2024-10-08 16:12:54.467401	2024-10-30 16:00:41	2024-10-30 15:00:41	2024-10-08 16:12:54.467401	\N	\N	4
79	AVAILABLE	2024-10-05 12:02:12.147416	2024-10-11 12:00:47	2024-10-11 11:00:47	2024-10-05 12:02:12.147416	\N	\N	2
110	AVAILABLE	2024-10-08 14:46:07.343236	2024-10-07 14:00:56	2024-10-07 13:00:56	2024-10-08 14:46:07.343236	\N	\N	3
111	AVAILABLE	2024-10-08 14:46:07.601891	2024-10-07 15:00:56	2024-10-07 14:00:56	2024-10-08 14:46:07.601891	\N	\N	3
112	AVAILABLE	2024-10-08 14:46:07.733233	2024-10-07 16:00:56	2024-10-07 15:00:56	2024-10-08 14:46:07.733233	\N	\N	3
113	AVAILABLE	2024-10-08 14:46:07.946654	2024-10-07 17:00:56	2024-10-07 16:00:56	2024-10-08 14:46:07.946654	\N	\N	3
114	AVAILABLE	2024-10-08 14:46:08.084714	2024-10-07 18:00:56	2024-10-07 17:00:56	2024-10-08 14:46:08.084714	\N	\N	3
651	AVAILABLE	2024-10-08 16:12:45.514276	2024-10-23 19:00:41	2024-10-23 18:00:41	2024-10-08 16:12:45.514276	\N	\N	4
116	AVAILABLE	2024-10-08 14:46:08.431093	2024-10-08 15:00:56	2024-10-08 14:00:56	2024-10-08 14:46:08.431093	\N	\N	3
117	AVAILABLE	2024-10-08 14:46:08.635596	2024-10-08 16:00:56	2024-10-08 15:00:56	2024-10-08 14:46:08.635596	\N	\N	3
118	AVAILABLE	2024-10-08 14:46:08.780631	2024-10-08 17:00:56	2024-10-08 16:00:56	2024-10-08 14:46:08.780631	\N	\N	3
119	AVAILABLE	2024-10-08 14:46:08.980132	2024-10-08 18:00:56	2024-10-08 17:00:56	2024-10-08 14:46:08.980132	\N	\N	3
120	AVAILABLE	2024-10-08 14:46:09.134232	2024-10-09 14:00:56	2024-10-09 13:00:56	2024-10-08 14:46:09.134232	\N	\N	3
121	AVAILABLE	2024-10-08 14:46:09.328468	2024-10-09 15:00:56	2024-10-09 14:00:56	2024-10-08 14:46:09.328468	\N	\N	3
122	AVAILABLE	2024-10-08 14:46:09.483876	2024-10-09 16:00:56	2024-10-09 15:00:56	2024-10-08 14:46:09.483876	\N	\N	3
123	AVAILABLE	2024-10-08 14:46:09.682971	2024-10-09 17:00:56	2024-10-09 16:00:56	2024-10-08 14:46:09.682971	\N	\N	3
124	AVAILABLE	2024-10-08 14:46:09.834746	2024-10-09 18:00:56	2024-10-09 17:00:56	2024-10-08 14:46:09.834746	\N	\N	3
125	AVAILABLE	2024-10-08 14:46:10.033234	2024-10-10 14:00:56	2024-10-10 13:00:56	2024-10-08 14:46:10.033234	\N	\N	3
126	AVAILABLE	2024-10-08 14:46:10.184407	2024-10-10 15:00:56	2024-10-10 14:00:56	2024-10-08 14:46:10.184407	\N	\N	3
127	AVAILABLE	2024-10-08 14:46:10.384283	2024-10-10 16:00:56	2024-10-10 15:00:56	2024-10-08 14:46:10.384283	\N	\N	3
128	AVAILABLE	2024-10-08 14:46:10.527928	2024-10-10 17:00:56	2024-10-10 16:00:56	2024-10-08 14:46:10.527928	\N	\N	3
129	AVAILABLE	2024-10-08 14:46:10.733505	2024-10-10 18:00:56	2024-10-10 17:00:56	2024-10-08 14:46:10.733505	\N	\N	3
130	AVAILABLE	2024-10-08 14:46:10.880325	2024-10-11 14:00:56	2024-10-11 13:00:56	2024-10-08 14:46:10.880325	\N	\N	3
131	AVAILABLE	2024-10-08 14:46:11.079475	2024-10-11 15:00:56	2024-10-11 14:00:56	2024-10-08 14:46:11.079475	\N	\N	3
132	AVAILABLE	2024-10-08 14:46:11.234204	2024-10-11 16:00:56	2024-10-11 15:00:56	2024-10-08 14:46:11.234204	\N	\N	3
133	AVAILABLE	2024-10-08 14:46:11.433842	2024-10-11 17:00:56	2024-10-11 16:00:56	2024-10-08 14:46:11.433842	\N	\N	3
134	AVAILABLE	2024-10-08 14:46:11.584926	2024-10-11 18:00:56	2024-10-11 17:00:56	2024-10-08 14:46:11.584926	\N	\N	3
135	AVAILABLE	2024-10-08 14:46:48.966937	2024-10-07 09:00:56	2024-10-07 08:00:56	2024-10-08 14:46:48.966937	\N	\N	3
136	AVAILABLE	2024-10-08 14:46:49.304686	2024-10-07 10:00:56	2024-10-07 09:00:56	2024-10-08 14:46:49.304686	\N	\N	3
137	AVAILABLE	2024-10-08 14:46:49.571084	2024-10-07 11:00:56	2024-10-07 10:00:56	2024-10-08 14:46:49.571084	\N	\N	3
138	AVAILABLE	2024-10-08 14:46:49.6509	2024-10-07 12:00:56	2024-10-07 11:00:56	2024-10-08 14:46:49.6509	\N	\N	3
661	AVAILABLE	2024-10-08 16:12:47.218745	2024-10-24 18:00:41	2024-10-24 17:00:41	2024-10-08 16:12:47.218745	\N	\N	4
671	AVAILABLE	2024-10-08 16:12:48.906177	2024-10-25 17:00:41	2024-10-25 16:00:41	2024-10-08 16:12:48.906177	\N	\N	4
681	AVAILABLE	2024-10-08 16:12:50.635917	2024-10-28 16:00:41	2024-10-28 15:00:41	2024-10-08 16:12:50.635917	\N	\N	4
691	AVAILABLE	2024-10-08 16:12:52.36623	2024-10-29 15:00:41	2024-10-29 14:00:41	2024-10-08 16:12:52.36623	\N	\N	4
701	AVAILABLE	2024-10-08 16:12:54.116873	2024-10-30 14:00:41	2024-10-30 13:00:41	2024-10-08 16:12:54.116873	\N	\N	4
144	AVAILABLE	2024-10-08 14:46:50.681898	2024-10-08 09:00:56	2024-10-08 08:00:56	2024-10-08 14:46:50.681898	\N	\N	3
145	AVAILABLE	2024-10-08 14:46:50.951239	2024-10-08 10:00:56	2024-10-08 09:00:56	2024-10-08 14:46:50.951239	\N	\N	3
146	AVAILABLE	2024-10-08 14:46:51.030055	2024-10-08 11:00:56	2024-10-08 10:00:56	2024-10-08 14:46:51.030055	\N	\N	3
147	AVAILABLE	2024-10-08 14:46:51.287953	2024-10-08 12:00:56	2024-10-08 11:00:56	2024-10-08 14:46:51.287953	\N	\N	3
153	AVAILABLE	2024-10-08 14:46:52.294966	2024-10-09 09:00:56	2024-10-09 08:00:56	2024-10-08 14:46:52.294966	\N	\N	3
154	AVAILABLE	2024-10-08 14:46:52.405735	2024-10-09 10:00:56	2024-10-09 09:00:56	2024-10-08 14:46:52.405735	\N	\N	3
155	AVAILABLE	2024-10-08 14:46:52.641719	2024-10-09 11:00:56	2024-10-09 10:00:56	2024-10-08 14:46:52.641719	\N	\N	3
156	AVAILABLE	2024-10-08 14:46:52.755175	2024-10-09 12:00:56	2024-10-09 11:00:56	2024-10-08 14:46:52.755175	\N	\N	3
162	AVAILABLE	2024-10-08 14:46:53.78118	2024-10-10 09:00:56	2024-10-10 08:00:56	2024-10-08 14:46:53.78118	\N	\N	3
163	AVAILABLE	2024-10-08 14:46:54.019266	2024-10-10 10:00:56	2024-10-10 09:00:56	2024-10-08 14:46:54.019266	\N	\N	3
164	AVAILABLE	2024-10-08 14:46:54.122838	2024-10-10 11:00:56	2024-10-10 10:00:56	2024-10-08 14:46:54.122838	\N	\N	3
165	AVAILABLE	2024-10-08 14:46:54.369844	2024-10-10 12:00:56	2024-10-10 11:00:56	2024-10-08 14:46:54.369844	\N	\N	3
171	AVAILABLE	2024-10-08 14:46:55.402214	2024-10-11 09:00:56	2024-10-11 08:00:56	2024-10-08 14:46:55.402214	\N	\N	3
172	AVAILABLE	2024-10-08 14:46:55.516636	2024-10-11 10:00:56	2024-10-11 09:00:56	2024-10-08 14:46:55.516636	\N	\N	3
173	AVAILABLE	2024-10-08 14:46:55.754052	2024-10-11 11:00:56	2024-10-11 10:00:56	2024-10-08 14:46:55.754052	\N	\N	3
174	AVAILABLE	2024-10-08 14:46:55.865267	2024-10-11 12:00:56	2024-10-11 11:00:56	2024-10-08 14:46:55.865267	\N	\N	3
705	AVAILABLE	2024-10-08 16:12:54.81861	2024-10-30 18:00:41	2024-10-30 17:00:41	2024-10-08 16:12:54.81861	\N	\N	4
706	AVAILABLE	2024-10-08 16:12:54.903057	2024-10-30 19:00:41	2024-10-30 18:00:41	2024-10-08 16:12:54.903057	\N	\N	4
398	AVAILABLE	2024-10-08 16:12:20.919394	2024-10-10 10:00:41	2024-10-10 09:00:41	2024-10-08 16:16:43.260562	\N	\N	4
368	AVAILABLE	2024-10-08 16:12:18.429807	2024-10-09 11:00:41	2024-10-09 10:00:41	2024-10-08 16:12:18.429807	\N	\N	4
374	AVAILABLE	2024-10-08 16:12:18.955524	2024-10-09 14:00:41	2024-10-09 13:00:41	2024-10-08 16:12:18.955524	\N	\N	4
376	AVAILABLE	2024-10-08 16:12:19.096886	2024-10-09 15:00:41	2024-10-09 14:00:41	2024-10-08 16:12:19.096886	\N	\N	4
404	AVAILABLE	2024-10-08 16:12:21.393565	2024-10-10 12:00:41	2024-10-10 11:00:41	2024-10-08 16:12:21.393565	\N	\N	4
410	AVAILABLE	2024-10-08 16:12:21.905385	2024-10-10 15:00:41	2024-10-10 14:00:41	2024-10-08 16:12:21.905385	\N	\N	4
416	AVAILABLE	2024-10-08 16:12:22.369443	2024-10-10 17:00:41	2024-10-10 16:00:41	2024-10-08 16:12:22.369443	\N	\N	4
428	AVAILABLE	2024-10-08 16:12:23.339563	2024-10-11 09:00:41	2024-10-11 08:00:41	2024-10-08 16:12:23.339563	\N	\N	4
430	AVAILABLE	2024-10-08 16:12:23.54216	2024-10-11 10:00:41	2024-10-11 09:00:41	2024-10-08 16:12:23.54216	\N	\N	4
434	AVAILABLE	2024-10-08 16:12:23.858535	2024-10-11 11:00:41	2024-10-11 10:00:41	2024-10-08 16:12:23.858535	\N	\N	4
446	AVAILABLE	2024-10-08 16:12:24.855081	2024-10-11 16:00:41	2024-10-11 15:00:41	2024-10-08 16:12:24.855081	\N	\N	4
449	AVAILABLE	2024-10-08 16:12:24.985997	2024-10-11 17:00:41	2024-10-11 16:00:41	2024-10-08 16:12:24.985997	\N	\N	4
464	AVAILABLE	2024-10-08 16:12:26.28857	2024-10-14 10:00:41	2024-10-14 09:00:41	2024-10-08 16:12:26.28857	\N	\N	4
466	AVAILABLE	2024-10-08 16:12:26.503111	2024-10-14 11:00:41	2024-10-14 10:00:41	2024-10-08 16:12:26.503111	\N	\N	4
470	AVAILABLE	2024-10-08 16:12:26.828833	2024-10-14 12:00:41	2024-10-14 11:00:41	2024-10-08 16:12:26.828833	\N	\N	4
478	AVAILABLE	2024-10-08 16:12:27.501106	2024-10-14 16:00:41	2024-10-14 15:00:41	2024-10-08 16:12:27.501106	\N	\N	4
488	AVAILABLE	2024-10-08 16:12:28.283774	2024-10-14 19:00:41	2024-10-14 18:00:41	2024-10-08 16:12:28.284305	\N	\N	4
491	AVAILABLE	2024-10-08 16:12:28.498005	2024-10-14 20:00:41	2024-10-14 19:00:41	2024-10-08 16:12:28.498005	\N	\N	4
708	AVAILABLE	2024-10-08 18:02:36.528379	2024-10-09 09:00:00	2024-10-09 08:00:00	2024-10-08 18:02:36.528379	\N	\N	7
709	AVAILABLE	2024-10-08 18:02:36.791568	2024-10-09 10:00:00	2024-10-09 09:00:00	2024-10-08 18:02:36.791568	\N	\N	7
711	AVAILABLE	2024-10-08 18:02:37.146313	2024-10-09 12:00:00	2024-10-09 11:00:00	2024-10-08 18:02:37.146313	\N	\N	7
714	AVAILABLE	2024-10-08 18:02:37.598782	2024-10-09 16:00:00	2024-10-09 15:00:00	2024-10-08 18:02:37.598782	\N	\N	7
715	AVAILABLE	2024-10-08 18:02:37.862453	2024-10-09 17:00:00	2024-10-09 16:00:00	2024-10-08 18:02:37.862453	\N	\N	7
718	AVAILABLE	2024-10-08 18:02:38.307939	2024-10-10 10:00:00	2024-10-10 09:00:00	2024-10-08 18:02:38.307939	\N	\N	7
719	AVAILABLE	2024-10-08 18:02:38.57263	2024-10-10 11:00:00	2024-10-10 10:00:00	2024-10-08 18:02:38.57263	\N	\N	7
721	AVAILABLE	2024-10-08 18:02:38.92958	2024-10-10 14:00:00	2024-10-10 13:00:00	2024-10-08 18:02:38.92958	\N	\N	7
724	AVAILABLE	2024-10-08 18:02:39.381881	2024-10-10 17:00:00	2024-10-10 16:00:00	2024-10-08 18:02:39.381881	\N	\N	7
725	AVAILABLE	2024-10-08 18:02:39.646484	2024-10-10 18:00:00	2024-10-10 17:00:00	2024-10-08 18:02:39.646484	\N	\N	7
727	AVAILABLE	2024-10-08 18:02:40.005363	2024-10-11 10:00:00	2024-10-11 09:00:00	2024-10-08 18:02:40.005891	\N	\N	7
728	AVAILABLE	2024-10-08 18:02:40.093297	2024-10-11 11:00:00	2024-10-11 10:00:00	2024-10-08 18:02:40.093297	\N	\N	7
729	AVAILABLE	2024-10-08 18:02:40.356175	2024-10-11 12:00:00	2024-10-11 11:00:00	2024-10-08 18:02:40.356175	\N	\N	7
731	AVAILABLE	2024-10-08 18:02:40.711896	2024-10-11 15:00:00	2024-10-11 14:00:00	2024-10-08 18:02:40.711896	\N	\N	7
734	AVAILABLE	2024-10-08 18:02:41.165944	2024-10-11 18:00:00	2024-10-11 17:00:00	2024-10-08 18:02:41.165944	\N	\N	7
735	AVAILABLE	2024-10-08 18:02:41.42705	2024-10-14 09:00:00	2024-10-14 08:00:00	2024-10-08 18:02:41.42705	\N	\N	7
737	AVAILABLE	2024-10-08 18:02:41.785594	2024-10-14 11:00:00	2024-10-14 10:00:00	2024-10-08 18:02:41.785594	\N	\N	7
738	AVAILABLE	2024-10-08 18:02:41.881729	2024-10-14 12:00:00	2024-10-14 11:00:00	2024-10-08 18:02:41.881729	\N	\N	7
739	AVAILABLE	2024-10-08 18:02:42.144112	2024-10-14 14:00:00	2024-10-14 13:00:00	2024-10-08 18:02:42.144112	\N	\N	7
741	AVAILABLE	2024-10-08 18:02:42.499937	2024-10-14 16:00:00	2024-10-14 15:00:00	2024-10-08 18:02:42.499937	\N	\N	7
744	AVAILABLE	2024-10-08 18:02:42.948992	2024-10-15 09:00:00	2024-10-15 08:00:00	2024-10-08 18:02:42.948992	\N	\N	7
745	AVAILABLE	2024-10-08 18:02:43.211466	2024-10-15 10:00:00	2024-10-15 09:00:00	2024-10-08 18:02:43.211466	\N	\N	7
747	AVAILABLE	2024-10-08 18:02:43.571871	2024-10-15 12:00:00	2024-10-15 11:00:00	2024-10-08 18:02:43.571871	\N	\N	7
748	AVAILABLE	2024-10-08 18:02:43.661599	2024-10-15 14:00:00	2024-10-15 13:00:00	2024-10-08 18:02:43.661599	\N	\N	7
749	AVAILABLE	2024-10-08 18:02:43.924818	2024-10-15 15:00:00	2024-10-15 14:00:00	2024-10-08 18:02:43.924818	\N	\N	7
751	AVAILABLE	2024-10-08 18:02:44.279806	2024-10-15 17:00:00	2024-10-15 16:00:00	2024-10-08 18:02:44.279806	\N	\N	7
754	AVAILABLE	2024-10-08 18:02:44.725937	2024-10-16 10:00:00	2024-10-16 09:00:00	2024-10-08 18:02:44.725937	\N	\N	7
755	AVAILABLE	2024-10-08 18:02:45.006437	2024-10-16 11:00:00	2024-10-16 10:00:00	2024-10-08 18:02:45.006437	\N	\N	7
757	AVAILABLE	2024-10-08 18:02:45.361165	2024-10-16 14:00:00	2024-10-16 13:00:00	2024-10-08 18:02:45.361165	\N	\N	7
758	AVAILABLE	2024-10-08 18:02:45.438823	2024-10-16 15:00:00	2024-10-16 14:00:00	2024-10-08 18:02:45.438823	\N	\N	7
759	AVAILABLE	2024-10-08 18:02:45.717869	2024-10-16 16:00:00	2024-10-16 15:00:00	2024-10-08 18:02:45.717869	\N	\N	7
761	AVAILABLE	2024-10-08 18:02:46.076329	2024-10-16 18:00:00	2024-10-16 17:00:00	2024-10-08 18:02:46.076329	\N	\N	7
764	AVAILABLE	2024-10-08 18:02:46.508668	2024-10-17 11:00:00	2024-10-17 10:00:00	2024-10-08 18:02:46.508668	\N	\N	7
765	AVAILABLE	2024-10-08 18:02:46.785744	2024-10-17 12:00:00	2024-10-17 11:00:00	2024-10-08 18:02:46.785744	\N	\N	7
767	AVAILABLE	2024-10-08 18:02:47.144654	2024-10-17 15:00:00	2024-10-17 14:00:00	2024-10-08 18:02:47.144654	\N	\N	7
768	AVAILABLE	2024-10-08 18:02:47.219597	2024-10-17 16:00:00	2024-10-17 15:00:00	2024-10-08 18:02:47.219597	\N	\N	7
383	AVAILABLE	2024-10-08 16:12:19.661893	2024-10-09 17:00:41	2024-10-09 16:00:41	2024-10-08 16:12:19.661893	\N	\N	4
389	AVAILABLE	2024-10-08 16:12:20.09132	2024-10-09 19:00:41	2024-10-09 18:00:41	2024-10-08 16:12:20.09132	\N	\N	4
769	AVAILABLE	2024-10-08 18:02:47.499818	2024-10-17 17:00:00	2024-10-17 16:00:00	2024-10-08 18:02:47.499818	\N	\N	7
771	AVAILABLE	2024-10-08 18:02:47.857138	2024-10-18 09:00:00	2024-10-18 08:00:00	2024-10-08 18:02:47.857138	\N	\N	7
774	AVAILABLE	2024-10-08 18:02:48.290856	2024-10-18 12:00:00	2024-10-18 11:00:00	2024-10-08 18:02:48.290856	\N	\N	7
775	AVAILABLE	2024-10-08 18:02:48.572256	2024-10-18 14:00:00	2024-10-18 13:00:00	2024-10-08 18:02:48.572256	\N	\N	7
412	AVAILABLE	2024-10-08 16:12:22.050531	2024-10-10 16:00:41	2024-10-10 15:00:41	2024-10-08 16:12:22.050531	\N	\N	4
419	AVAILABLE	2024-10-08 16:12:22.573256	2024-10-10 18:00:41	2024-10-10 17:00:41	2024-10-08 16:12:22.573256	\N	\N	4
777	AVAILABLE	2024-10-08 18:02:48.928251	2024-10-18 16:00:00	2024-10-18 15:00:00	2024-10-08 18:02:48.928251	\N	\N	7
778	AVAILABLE	2024-10-08 18:02:49.007654	2024-10-18 17:00:00	2024-10-18 16:00:00	2024-10-08 18:02:49.007654	\N	\N	7
779	AVAILABLE	2024-10-08 18:02:49.288849	2024-10-18 18:00:00	2024-10-18 17:00:00	2024-10-08 18:02:49.288849	\N	\N	7
781	AVAILABLE	2024-10-08 18:02:49.644502	2024-10-21 10:00:00	2024-10-21 09:00:00	2024-10-08 18:02:49.644502	\N	\N	7
442	AVAILABLE	2024-10-08 16:12:24.537528	2024-10-11 15:00:41	2024-10-11 14:00:41	2024-10-08 16:12:24.537528	\N	\N	4
784	AVAILABLE	2024-10-08 18:02:50.080751	2024-10-21 14:00:00	2024-10-21 13:00:00	2024-10-08 18:02:50.080751	\N	\N	7
785	AVAILABLE	2024-10-08 18:02:50.355719	2024-10-21 15:00:00	2024-10-21 14:00:00	2024-10-08 18:02:50.355719	\N	\N	7
458	AVAILABLE	2024-10-08 16:12:25.839841	2024-10-11 20:00:41	2024-10-11 19:00:41	2024-10-08 16:12:25.839841	\N	\N	4
717	AVAILABLE	2024-10-08 18:02:38.214896	2024-10-10 09:00:00	2024-10-10 08:00:00	2024-10-08 18:04:14.035083	\N	\N	7
482	AVAILABLE	2024-10-08 16:12:27.819648	2024-10-14 17:00:41	2024-10-14 16:00:41	2024-10-08 16:12:27.819648	\N	\N	4
503	AVAILABLE	2024-10-08 16:12:29.488439	2024-10-15 12:00:41	2024-10-15 11:00:41	2024-10-08 16:12:29.488439	\N	\N	4
509	AVAILABLE	2024-10-08 16:12:29.948687	2024-10-15 15:00:41	2024-10-15 14:00:41	2024-10-08 16:12:29.948687	\N	\N	4
542	AVAILABLE	2024-10-08 16:12:33.098145	2024-10-16 17:00:41	2024-10-16 16:00:41	2024-10-08 16:12:33.098145	\N	\N	4
553	AVAILABLE	2024-10-08 16:12:34.175795	2024-10-17 10:00:41	2024-10-17 09:00:41	2024-10-08 16:12:34.175795	\N	\N	4
563	AVAILABLE	2024-10-08 16:12:35.370119	2024-10-17 16:00:41	2024-10-17 15:00:41	2024-10-08 16:12:35.370119	\N	\N	4
572	AVAILABLE	2024-10-08 16:12:36.351827	2024-10-18 09:00:41	2024-10-18 08:00:41	2024-10-08 16:12:36.351827	\N	\N	4
583	AVAILABLE	2024-10-08 16:12:37.402811	2024-10-18 15:00:41	2024-10-18 14:00:41	2024-10-08 16:12:37.402811	\N	\N	4
593	AVAILABLE	2024-10-08 16:12:38.602507	2024-10-18 20:00:41	2024-10-18 19:00:41	2024-10-08 16:12:38.602507	\N	\N	4
710	AVAILABLE	2024-10-08 18:02:36.88875	2024-10-09 11:00:00	2024-10-09 10:00:00	2024-10-08 18:02:36.88875	\N	\N	7
720	AVAILABLE	2024-10-08 18:02:38.666196	2024-10-10 12:00:00	2024-10-10 11:00:00	2024-10-08 18:02:38.666196	\N	\N	7
730	AVAILABLE	2024-10-08 18:02:40.450355	2024-10-11 14:00:00	2024-10-11 13:00:00	2024-10-08 18:02:40.450355	\N	\N	7
740	AVAILABLE	2024-10-08 18:02:42.240285	2024-10-14 15:00:00	2024-10-14 14:00:00	2024-10-08 18:02:42.240285	\N	\N	7
750	AVAILABLE	2024-10-08 18:02:44.01996	2024-10-15 16:00:00	2024-10-15 15:00:00	2024-10-08 18:02:44.01996	\N	\N	7
760	AVAILABLE	2024-10-08 18:02:45.800298	2024-10-16 17:00:00	2024-10-16 16:00:00	2024-10-08 18:02:45.800298	\N	\N	7
770	AVAILABLE	2024-10-08 18:02:47.578683	2024-10-17 18:00:00	2024-10-17 17:00:00	2024-10-08 18:02:47.578683	\N	\N	7
780	AVAILABLE	2024-10-08 18:02:49.365882	2024-10-21 09:00:00	2024-10-21 08:00:00	2024-10-08 18:02:49.365882	\N	\N	7
712	AVAILABLE	2024-10-08 18:02:37.243252	2024-10-09 14:00:00	2024-10-09 13:00:00	2024-10-08 18:02:37.243252	\N	\N	7
722	AVAILABLE	2024-10-08 18:02:39.023213	2024-10-10 15:00:00	2024-10-10 14:00:00	2024-10-08 18:02:39.023213	\N	\N	7
732	AVAILABLE	2024-10-08 18:02:40.808029	2024-10-11 16:00:00	2024-10-11 15:00:00	2024-10-08 18:02:40.808029	\N	\N	7
742	AVAILABLE	2024-10-08 18:02:42.594743	2024-10-14 17:00:00	2024-10-14 16:00:00	2024-10-08 18:02:42.594743	\N	\N	7
752	AVAILABLE	2024-10-08 18:02:44.371217	2024-10-15 18:00:00	2024-10-15 17:00:00	2024-10-08 18:02:44.371217	\N	\N	7
762	AVAILABLE	2024-10-08 18:02:46.150937	2024-10-17 09:00:00	2024-10-17 08:00:00	2024-10-08 18:02:46.150937	\N	\N	7
772	AVAILABLE	2024-10-08 18:02:47.935489	2024-10-18 10:00:00	2024-10-18 09:00:00	2024-10-08 18:02:47.935489	\N	\N	7
782	AVAILABLE	2024-10-08 18:02:49.723287	2024-10-21 11:00:00	2024-10-21 10:00:00	2024-10-08 18:02:49.723287	\N	\N	7
362	AVAILABLE	2024-10-08 16:12:17.969756	2024-10-09 09:00:41	2024-10-09 08:00:41	2024-10-08 16:12:17.969756	\N	\N	4
392	AVAILABLE	2024-10-08 16:12:20.411233	2024-10-09 20:00:41	2024-10-09 19:00:41	2024-10-08 16:12:20.411233	\N	\N	4
422	AVAILABLE	2024-10-08 16:12:22.877014	2024-10-10 19:00:41	2024-10-10 18:00:41	2024-10-08 16:12:22.877014	\N	\N	4
452	AVAILABLE	2024-10-08 16:12:25.31326	2024-10-11 18:00:41	2024-10-11 17:00:41	2024-10-08 16:12:25.31326	\N	\N	4
472	AVAILABLE	2024-10-08 16:12:26.959602	2024-10-14 14:00:41	2024-10-14 13:00:41	2024-10-08 16:12:26.959602	\N	\N	4
512	AVAILABLE	2024-10-08 16:12:30.266976	2024-10-15 16:00:41	2024-10-15 15:00:41	2024-10-08 16:12:30.266976	\N	\N	4
532	AVAILABLE	2024-10-08 16:12:31.903497	2024-10-16 11:00:41	2024-10-16 10:00:41	2024-10-08 16:12:31.903497	\N	\N	4
713	AVAILABLE	2024-10-08 18:02:37.502901	2024-10-09 15:00:00	2024-10-09 14:00:00	2024-10-08 18:02:37.502901	\N	\N	7
365	AVAILABLE	2024-10-08 16:12:18.115483	2024-10-09 10:00:41	2024-10-09 09:00:41	2024-10-08 16:12:18.115483	\N	\N	4
716	AVAILABLE	2024-10-08 18:02:37.952249	2024-10-09 18:00:00	2024-10-09 17:00:00	2024-10-08 18:02:37.952249	\N	\N	7
723	AVAILABLE	2024-10-08 18:02:39.289301	2024-10-10 16:00:00	2024-10-10 15:00:00	2024-10-08 18:02:39.289301	\N	\N	7
726	AVAILABLE	2024-10-08 18:02:39.737229	2024-10-11 09:00:00	2024-10-11 08:00:00	2024-10-08 18:02:39.737229	\N	\N	7
371	AVAILABLE	2024-10-08 16:12:18.668189	2024-10-09 12:00:41	2024-10-09 11:00:41	2024-10-08 16:12:18.668189	\N	\N	4
733	AVAILABLE	2024-10-08 18:02:41.073276	2024-10-11 17:00:00	2024-10-11 16:00:00	2024-10-08 18:02:41.073276	\N	\N	7
736	AVAILABLE	2024-10-08 18:02:41.52219	2024-10-14 10:00:00	2024-10-14 09:00:00	2024-10-08 18:02:41.52219	\N	\N	7
380	AVAILABLE	2024-10-08 16:12:19.424808	2024-10-09 16:00:41	2024-10-09 15:00:41	2024-10-08 16:12:19.424808	\N	\N	4
743	AVAILABLE	2024-10-08 18:02:42.854415	2024-10-14 18:00:00	2024-10-14 17:00:00	2024-10-08 18:02:42.854415	\N	\N	7
746	AVAILABLE	2024-10-08 18:02:43.306212	2024-10-15 11:00:00	2024-10-15 10:00:00	2024-10-08 18:02:43.306212	\N	\N	7
386	AVAILABLE	2024-10-08 16:12:19.933731	2024-10-09 18:00:41	2024-10-09 17:00:41	2024-10-08 16:12:19.933731	\N	\N	4
753	AVAILABLE	2024-10-08 18:02:44.632942	2024-10-16 09:00:00	2024-10-16 08:00:00	2024-10-08 18:02:44.632942	\N	\N	7
756	AVAILABLE	2024-10-08 18:02:45.080415	2024-10-16 12:00:00	2024-10-16 11:00:00	2024-10-08 18:02:45.080415	\N	\N	7
395	AVAILABLE	2024-10-08 16:12:20.630862	2024-10-10 09:00:41	2024-10-10 08:00:41	2024-10-08 16:12:20.630862	\N	\N	4
763	AVAILABLE	2024-10-08 18:02:46.431924	2024-10-17 10:00:00	2024-10-17 09:00:00	2024-10-08 18:02:46.431924	\N	\N	7
401	AVAILABLE	2024-10-08 16:12:21.064438	2024-10-10 11:00:41	2024-10-10 10:00:41	2024-10-08 16:12:21.064438	\N	\N	4
766	AVAILABLE	2024-10-08 18:02:46.861089	2024-10-17 14:00:00	2024-10-17 13:00:00	2024-10-08 18:02:46.861089	\N	\N	7
407	AVAILABLE	2024-10-08 16:12:21.596675	2024-10-10 14:00:41	2024-10-10 13:00:41	2024-10-08 16:12:21.596675	\N	\N	4
773	AVAILABLE	2024-10-08 18:02:48.215979	2024-10-18 11:00:00	2024-10-18 10:00:00	2024-10-08 18:02:48.215979	\N	\N	7
776	AVAILABLE	2024-10-08 18:02:48.650712	2024-10-18 15:00:00	2024-10-18 14:00:00	2024-10-08 18:02:48.650712	\N	\N	7
783	AVAILABLE	2024-10-08 18:02:50.001084	2024-10-21 12:00:00	2024-10-21 11:00:00	2024-10-08 18:02:50.001084	\N	\N	7
786	AVAILABLE	2024-10-08 18:02:50.436701	2024-10-21 16:00:00	2024-10-21 15:00:00	2024-10-08 18:02:50.436701	\N	\N	7
424	AVAILABLE	2024-10-08 16:12:23.022954	2024-10-10 20:00:41	2024-10-10 19:00:41	2024-10-08 16:12:23.022954	\N	\N	4
437	AVAILABLE	2024-10-08 16:12:24.011866	2024-10-11 12:00:41	2024-10-11 11:00:41	2024-10-08 16:12:24.011866	\N	\N	4
440	AVAILABLE	2024-10-08 16:12:24.317517	2024-10-11 14:00:41	2024-10-11 13:00:41	2024-10-08 16:12:24.317517	\N	\N	4
454	AVAILABLE	2024-10-08 16:12:25.520521	2024-10-11 19:00:41	2024-10-11 18:00:41	2024-10-08 16:12:25.520521	\N	\N	4
460	AVAILABLE	2024-10-08 16:12:25.967568	2024-10-14 09:00:41	2024-10-14 08:00:41	2024-10-08 16:12:25.967568	\N	\N	4
476	AVAILABLE	2024-10-08 16:12:27.280959	2024-10-14 15:00:41	2024-10-14 14:00:41	2024-10-08 16:12:27.280959	\N	\N	4
484	AVAILABLE	2024-10-08 16:12:27.962217	2024-10-14 18:00:41	2024-10-14 17:00:41	2024-10-08 16:12:27.962217	\N	\N	4
497	AVAILABLE	2024-10-08 16:12:28.951642	2024-10-15 10:00:41	2024-10-15 09:00:41	2024-10-08 16:12:28.951642	\N	\N	4
500	AVAILABLE	2024-10-08 16:12:29.281491	2024-10-15 11:00:41	2024-10-15 10:00:41	2024-10-08 16:12:29.281491	\N	\N	4
515	AVAILABLE	2024-10-08 16:12:30.474284	2024-10-15 17:00:41	2024-10-15 16:00:41	2024-10-08 16:12:30.474284	\N	\N	4
521	AVAILABLE	2024-10-08 16:12:30.929001	2024-10-15 19:00:41	2024-10-15 18:00:41	2024-10-08 16:12:30.929001	\N	\N	4
526	AVAILABLE	2024-10-08 16:12:31.454865	2024-10-16 09:00:41	2024-10-16 08:00:41	2024-10-08 16:12:31.454865	\N	\N	4
541	AVAILABLE	2024-10-08 16:12:32.871368	2024-10-16 16:00:41	2024-10-16 15:00:41	2024-10-08 16:12:32.871368	\N	\N	4
545	AVAILABLE	2024-10-08 16:12:33.416675	2024-10-16 18:00:41	2024-10-16 17:00:41	2024-10-08 16:12:33.416675	\N	\N	4
551	AVAILABLE	2024-10-08 16:12:34.065899	2024-10-17 09:00:41	2024-10-17 08:00:41	2024-10-08 16:12:34.065899	\N	\N	4
554	AVAILABLE	2024-10-08 16:12:34.400777	2024-10-17 11:00:41	2024-10-17 10:00:41	2024-10-08 16:12:34.400777	\N	\N	4
560	AVAILABLE	2024-10-08 16:12:35.050987	2024-10-17 15:00:41	2024-10-17 14:00:41	2024-10-08 16:12:35.050987	\N	\N	4
565	AVAILABLE	2024-10-08 16:12:35.465157	2024-10-17 17:00:41	2024-10-17 16:00:41	2024-10-08 16:12:35.465157	\N	\N	4
571	AVAILABLE	2024-10-08 16:12:36.103377	2024-10-17 20:00:41	2024-10-17 19:00:41	2024-10-08 16:12:36.103377	\N	\N	4
575	AVAILABLE	2024-10-08 16:12:36.667696	2024-10-18 10:00:41	2024-10-18 09:00:41	2024-10-08 16:12:36.667696	\N	\N	4
581	AVAILABLE	2024-10-08 16:12:37.313415	2024-10-18 14:00:41	2024-10-18 13:00:41	2024-10-08 16:12:37.313415	\N	\N	4
585	AVAILABLE	2024-10-08 16:12:37.649972	2024-10-18 16:00:41	2024-10-18 15:00:41	2024-10-08 16:12:37.649972	\N	\N	4
591	AVAILABLE	2024-10-08 16:12:38.297573	2024-10-18 19:00:41	2024-10-18 18:00:41	2024-10-08 16:12:38.297573	\N	\N	4
595	AVAILABLE	2024-10-08 16:12:38.707212	2024-10-21 09:00:41	2024-10-21 08:00:41	2024-10-08 16:12:38.707212	\N	\N	4
601	AVAILABLE	2024-10-08 16:12:39.364248	2024-10-21 12:00:41	2024-10-21 11:00:41	2024-10-08 16:12:39.364248	\N	\N	4
605	AVAILABLE	2024-10-08 16:12:39.917699	2024-10-21 15:00:41	2024-10-21 14:00:41	2024-10-08 16:12:39.917699	\N	\N	4
611	AVAILABLE	2024-10-08 16:12:40.609842	2024-10-21 18:00:41	2024-10-21 17:00:41	2024-10-08 16:12:40.609842	\N	\N	4
494	AVAILABLE	2024-10-08 16:12:28.828952	2024-10-15 09:00:41	2024-10-15 08:00:41	2024-10-08 16:12:28.828952	\N	\N	4
787	AVAILABLE	2024-10-08 18:02:50.71552	2024-10-21 17:00:00	2024-10-21 16:00:00	2024-10-08 18:02:50.71552	\N	\N	7
788	AVAILABLE	2024-10-08 18:02:50.789488	2024-10-21 18:00:00	2024-10-21 17:00:00	2024-10-08 18:02:50.789488	\N	\N	7
789	AVAILABLE	2024-10-08 18:02:51.068247	2024-10-22 09:00:00	2024-10-22 08:00:00	2024-10-08 18:02:51.068247	\N	\N	7
791	AVAILABLE	2024-10-08 18:02:51.427576	2024-10-22 11:00:00	2024-10-22 10:00:00	2024-10-08 18:02:51.427576	\N	\N	7
506	AVAILABLE	2024-10-08 16:12:29.805461	2024-10-15 14:00:41	2024-10-15 13:00:41	2024-10-08 16:12:29.805461	\N	\N	4
794	AVAILABLE	2024-10-08 18:02:51.84556	2024-10-22 15:00:00	2024-10-22 14:00:00	2024-10-08 18:02:51.84556	\N	\N	7
795	AVAILABLE	2024-10-08 18:02:52.107979	2024-10-22 16:00:00	2024-10-22 15:00:00	2024-10-08 18:02:52.107979	\N	\N	7
797	AVAILABLE	2024-10-08 18:02:52.44997	2024-10-22 18:00:00	2024-10-22 17:00:00	2024-10-08 18:02:52.44997	\N	\N	7
798	AVAILABLE	2024-10-08 18:02:52.527808	2024-10-23 09:00:00	2024-10-23 08:00:00	2024-10-08 18:02:52.527808	\N	\N	7
518	AVAILABLE	2024-10-08 16:12:30.792379	2024-10-15 18:00:41	2024-10-15 17:00:41	2024-10-08 16:12:30.792379	\N	\N	4
799	AVAILABLE	2024-10-08 18:02:52.787853	2024-10-23 10:00:00	2024-10-23 09:00:00	2024-10-08 18:02:52.787853	\N	\N	7
524	AVAILABLE	2024-10-08 16:12:31.246346	2024-10-15 20:00:41	2024-10-15 19:00:41	2024-10-08 16:12:31.246346	\N	\N	4
801	AVAILABLE	2024-10-08 18:02:53.12999	2024-10-23 12:00:00	2024-10-23 11:00:00	2024-10-08 18:02:53.12999	\N	\N	7
804	AVAILABLE	2024-10-08 18:02:53.566179	2024-10-23 16:00:00	2024-10-23 15:00:00	2024-10-08 18:02:53.566179	\N	\N	7
530	AVAILABLE	2024-10-08 16:12:31.769734	2024-10-16 10:00:41	2024-10-16 09:00:41	2024-10-08 16:12:31.769734	\N	\N	4
533	AVAILABLE	2024-10-08 16:12:32.110057	2024-10-16 12:00:41	2024-10-16 11:00:41	2024-10-08 16:12:32.110057	\N	\N	4
537	AVAILABLE	2024-10-08 16:12:32.438445	2024-10-16 14:00:41	2024-10-16 13:00:41	2024-10-08 16:12:32.438445	\N	\N	4
539	AVAILABLE	2024-10-08 16:12:32.758205	2024-10-16 15:00:41	2024-10-16 14:00:41	2024-10-08 16:12:32.758205	\N	\N	4
805	AVAILABLE	2024-10-08 18:02:53.81111	2024-10-23 17:00:00	2024-10-23 16:00:00	2024-10-08 18:02:53.81111	\N	\N	7
807	AVAILABLE	2024-10-08 18:02:54.149404	2024-10-24 09:00:00	2024-10-24 08:00:00	2024-10-08 18:02:54.149404	\N	\N	7
547	AVAILABLE	2024-10-08 16:12:33.527235	2024-10-16 19:00:41	2024-10-16 18:00:41	2024-10-08 16:12:33.527235	\N	\N	4
548	AVAILABLE	2024-10-08 16:12:33.748642	2024-10-16 20:00:41	2024-10-16 19:00:41	2024-10-08 16:12:33.748642	\N	\N	4
808	AVAILABLE	2024-10-08 18:02:54.243611	2024-10-24 10:00:00	2024-10-24 09:00:00	2024-10-08 18:02:54.243611	\N	\N	7
809	AVAILABLE	2024-10-08 18:02:54.492266	2024-10-24 11:00:00	2024-10-24 10:00:00	2024-10-08 18:02:54.492266	\N	\N	7
557	AVAILABLE	2024-10-08 16:12:34.719171	2024-10-17 12:00:41	2024-10-17 11:00:41	2024-10-08 16:12:34.719171	\N	\N	4
559	AVAILABLE	2024-10-08 16:12:34.817024	2024-10-17 14:00:41	2024-10-17 13:00:41	2024-10-08 16:12:34.817024	\N	\N	4
811	AVAILABLE	2024-10-08 18:02:54.833888	2024-10-24 14:00:00	2024-10-24 13:00:00	2024-10-08 18:02:54.833888	\N	\N	7
814	AVAILABLE	2024-10-08 18:02:55.269177	2024-10-24 17:00:00	2024-10-24 16:00:00	2024-10-08 18:02:55.269177	\N	\N	7
566	AVAILABLE	2024-10-08 16:12:35.70465	2024-10-17 18:00:41	2024-10-17 17:00:41	2024-10-08 16:12:35.70465	\N	\N	4
569	AVAILABLE	2024-10-08 16:12:36.020349	2024-10-17 19:00:41	2024-10-17 18:00:41	2024-10-08 16:12:36.020349	\N	\N	4
815	AVAILABLE	2024-10-08 18:02:55.517623	2024-10-24 18:00:00	2024-10-24 17:00:00	2024-10-08 18:02:55.517623	\N	\N	7
817	AVAILABLE	2024-10-08 18:02:55.857409	2024-10-25 10:00:00	2024-10-25 09:00:00	2024-10-08 18:02:55.857409	\N	\N	7
577	AVAILABLE	2024-10-08 16:12:36.759548	2024-10-18 11:00:41	2024-10-18 10:00:41	2024-10-08 16:12:36.759548	\N	\N	4
579	AVAILABLE	2024-10-08 16:12:36.996717	2024-10-18 12:00:41	2024-10-18 11:00:41	2024-10-08 16:12:36.996717	\N	\N	4
818	AVAILABLE	2024-10-08 18:02:55.951073	2024-10-25 11:00:00	2024-10-25 10:00:00	2024-10-08 18:02:55.951073	\N	\N	7
819	AVAILABLE	2024-10-08 18:02:56.200374	2024-10-25 12:00:00	2024-10-25 11:00:00	2024-10-08 18:02:56.200374	\N	\N	7
587	AVAILABLE	2024-10-08 16:12:37.951482	2024-10-18 17:00:41	2024-10-18 16:00:41	2024-10-08 16:12:37.951482	\N	\N	4
589	AVAILABLE	2024-10-08 16:12:38.053371	2024-10-18 18:00:41	2024-10-18 17:00:41	2024-10-08 16:12:38.053371	\N	\N	4
821	AVAILABLE	2024-10-08 18:02:56.540168	2024-10-25 15:00:00	2024-10-25 14:00:00	2024-10-08 18:02:56.540168	\N	\N	7
824	AVAILABLE	2024-10-08 18:02:56.970597	2024-10-25 18:00:00	2024-10-25 17:00:00	2024-10-08 18:02:56.970597	\N	\N	7
597	AVAILABLE	2024-10-08 16:12:38.950515	2024-10-21 10:00:41	2024-10-21 09:00:41	2024-10-08 16:12:38.950515	\N	\N	4
599	AVAILABLE	2024-10-08 16:12:39.253545	2024-10-21 11:00:41	2024-10-21 10:00:41	2024-10-08 16:12:39.253545	\N	\N	4
825	AVAILABLE	2024-10-08 18:02:57.218455	2024-10-28 09:00:00	2024-10-28 08:00:00	2024-10-08 18:02:57.218455	\N	\N	7
827	AVAILABLE	2024-10-08 18:02:57.560311	2024-10-28 11:00:00	2024-10-28 10:00:00	2024-10-08 18:02:57.560311	\N	\N	7
607	AVAILABLE	2024-10-08 16:12:40.002971	2024-10-21 16:00:41	2024-10-21 15:00:41	2024-10-08 16:12:40.002971	\N	\N	4
828	AVAILABLE	2024-10-08 18:02:57.65179	2024-10-28 12:00:00	2024-10-28 11:00:00	2024-10-08 18:02:57.65179	\N	\N	7
829	AVAILABLE	2024-10-08 18:02:57.89863	2024-10-28 14:00:00	2024-10-28 13:00:00	2024-10-08 18:02:57.89863	\N	\N	7
614	AVAILABLE	2024-10-08 16:12:40.92792	2024-10-21 19:00:41	2024-10-21 18:00:41	2024-10-08 16:12:40.92792	\N	\N	4
831	AVAILABLE	2024-10-08 18:02:58.243444	2024-10-28 16:00:00	2024-10-28 15:00:00	2024-10-08 18:02:58.243444	\N	\N	7
618	AVAILABLE	2024-10-08 16:12:41.278065	2024-10-22 09:00:41	2024-10-22 08:00:41	2024-10-08 16:12:41.278065	\N	\N	4
620	AVAILABLE	2024-10-08 16:12:41.591991	2024-10-22 10:00:41	2024-10-22 09:00:41	2024-10-08 16:12:41.591991	\N	\N	4
624	AVAILABLE	2024-10-08 16:12:41.940393	2024-10-22 12:00:41	2024-10-22 11:00:41	2024-10-08 16:12:41.940393	\N	\N	4
834	AVAILABLE	2024-10-08 18:02:58.671148	2024-10-29 09:00:00	2024-10-29 08:00:00	2024-10-08 18:02:58.671148	\N	\N	7
628	AVAILABLE	2024-10-08 16:12:42.321492	2024-10-22 15:00:41	2024-10-22 14:00:41	2024-10-08 16:12:42.321492	\N	\N	4
630	AVAILABLE	2024-10-08 16:12:42.593645	2024-10-22 16:00:41	2024-10-22 15:00:41	2024-10-08 16:12:42.593645	\N	\N	4
634	AVAILABLE	2024-10-08 16:12:42.985771	2024-10-22 18:00:41	2024-10-22 17:00:41	2024-10-08 16:12:42.985771	\N	\N	4
835	AVAILABLE	2024-10-08 18:02:58.922495	2024-10-29 10:00:00	2024-10-29 09:00:00	2024-10-08 18:02:58.922495	\N	\N	7
638	AVAILABLE	2024-10-08 16:12:43.546825	2024-10-22 20:00:41	2024-10-22 19:00:41	2024-10-08 16:12:43.546825	\N	\N	4
640	AVAILABLE	2024-10-08 16:12:43.637486	2024-10-23 09:00:41	2024-10-23 08:00:41	2024-10-08 16:12:43.637486	\N	\N	4
644	AVAILABLE	2024-10-08 16:12:44.19592	2024-10-23 11:00:41	2024-10-23 10:00:41	2024-10-08 16:12:44.19592	\N	\N	4
837	AVAILABLE	2024-10-08 18:02:59.262667	2024-10-29 12:00:00	2024-10-29 11:00:00	2024-10-08 18:02:59.262667	\N	\N	7
838	AVAILABLE	2024-10-08 18:02:59.357031	2024-10-29 14:00:00	2024-10-29 13:00:00	2024-10-08 18:02:59.357031	\N	\N	7
839	AVAILABLE	2024-10-08 18:02:59.608568	2024-10-29 15:00:00	2024-10-29 14:00:00	2024-10-08 18:02:59.608568	\N	\N	7
841	AVAILABLE	2024-10-08 18:02:59.947429	2024-10-29 17:00:00	2024-10-29 16:00:00	2024-10-08 18:02:59.947429	\N	\N	7
844	AVAILABLE	2024-10-08 18:03:00.380463	2024-10-30 10:00:00	2024-10-30 09:00:00	2024-10-08 18:03:00.380463	\N	\N	7
845	AVAILABLE	2024-10-08 18:03:00.641987	2024-10-30 11:00:00	2024-10-30 10:00:00	2024-10-08 18:03:00.641987	\N	\N	7
847	AVAILABLE	2024-10-08 18:03:00.979561	2024-10-30 14:00:00	2024-10-30 13:00:00	2024-10-08 18:03:00.979561	\N	\N	7
848	AVAILABLE	2024-10-08 18:03:01.076307	2024-10-30 15:00:00	2024-10-30 14:00:00	2024-10-08 18:03:01.076307	\N	\N	7
849	AVAILABLE	2024-10-08 18:03:01.32524	2024-10-30 16:00:00	2024-10-30 15:00:00	2024-10-08 18:03:01.32524	\N	\N	7
851	AVAILABLE	2024-10-08 18:03:01.6665	2024-10-30 18:00:00	2024-10-30 17:00:00	2024-10-08 18:03:01.6665	\N	\N	7
854	AVAILABLE	2024-10-08 18:03:02.115027	2024-10-31 11:00:00	2024-10-31 10:00:00	2024-10-08 18:03:02.115027	\N	\N	7
855	AVAILABLE	2024-10-08 18:03:02.37721	2024-10-31 12:00:00	2024-10-31 11:00:00	2024-10-08 18:03:02.37721	\N	\N	7
857	AVAILABLE	2024-10-08 18:03:02.730473	2024-10-31 15:00:00	2024-10-31 14:00:00	2024-10-08 18:03:02.730473	\N	\N	7
858	AVAILABLE	2024-10-08 18:03:02.809454	2024-10-31 16:00:00	2024-10-31 15:00:00	2024-10-08 18:03:02.809454	\N	\N	7
859	AVAILABLE	2024-10-08 18:03:03.075081	2024-10-31 17:00:00	2024-10-31 16:00:00	2024-10-08 18:03:03.075081	\N	\N	7
603	AVAILABLE	2024-10-08 16:12:39.590807	2024-10-21 14:00:41	2024-10-21 13:00:41	2024-10-08 16:12:39.590807	\N	\N	4
609	AVAILABLE	2024-10-08 16:12:40.264224	2024-10-21 17:00:41	2024-10-21 16:00:41	2024-10-08 16:12:40.264224	\N	\N	4
790	AVAILABLE	2024-10-08 18:02:51.149688	2024-10-22 10:00:00	2024-10-22 09:00:00	2024-10-08 18:02:51.149688	\N	\N	7
800	AVAILABLE	2024-10-08 18:02:52.883889	2024-10-23 11:00:00	2024-10-23 10:00:00	2024-10-08 18:02:52.883889	\N	\N	7
810	AVAILABLE	2024-10-08 18:02:54.583727	2024-10-24 12:00:00	2024-10-24 11:00:00	2024-10-08 18:02:54.583727	\N	\N	7
820	AVAILABLE	2024-10-08 18:02:56.293258	2024-10-25 14:00:00	2024-10-25 13:00:00	2024-10-08 18:02:56.293258	\N	\N	7
830	AVAILABLE	2024-10-08 18:02:57.993516	2024-10-28 15:00:00	2024-10-28 14:00:00	2024-10-08 18:02:57.993516	\N	\N	7
840	AVAILABLE	2024-10-08 18:02:59.700006	2024-10-29 16:00:00	2024-10-29 15:00:00	2024-10-08 18:02:59.700006	\N	\N	7
850	AVAILABLE	2024-10-08 18:03:01.417824	2024-10-30 17:00:00	2024-10-30 16:00:00	2024-10-08 18:03:01.417824	\N	\N	7
860	AVAILABLE	2024-10-08 18:03:03.150903	2024-10-31 18:00:00	2024-10-31 17:00:00	2024-10-08 18:03:03.150903	\N	\N	7
616	AVAILABLE	2024-10-08 16:12:41.02024	2024-10-21 20:00:41	2024-10-21 19:00:41	2024-10-08 16:12:41.02024	\N	\N	4
622	AVAILABLE	2024-10-08 16:12:41.669617	2024-10-22 11:00:41	2024-10-22 10:00:41	2024-10-08 16:12:41.669617	\N	\N	4
792	AVAILABLE	2024-10-08 18:02:51.505402	2024-10-22 12:00:00	2024-10-22 11:00:00	2024-10-08 18:02:51.505402	\N	\N	7
626	AVAILABLE	2024-10-08 16:12:42.244788	2024-10-22 14:00:41	2024-10-22 13:00:41	2024-10-08 16:12:42.244788	\N	\N	4
632	AVAILABLE	2024-10-08 16:12:42.899854	2024-10-22 17:00:41	2024-10-22 16:00:41	2024-10-08 16:12:42.899854	\N	\N	4
802	AVAILABLE	2024-10-08 18:02:53.222218	2024-10-23 14:00:00	2024-10-23 13:00:00	2024-10-08 18:02:53.222218	\N	\N	7
636	AVAILABLE	2024-10-08 16:12:43.243836	2024-10-22 19:00:41	2024-10-22 18:00:41	2024-10-08 16:12:43.243836	\N	\N	4
642	AVAILABLE	2024-10-08 16:12:43.891685	2024-10-23 10:00:41	2024-10-23 09:00:41	2024-10-08 16:12:43.891685	\N	\N	4
645	AVAILABLE	2024-10-08 16:12:44.232447	2024-10-23 12:00:41	2024-10-23 11:00:41	2024-10-08 16:12:44.232447	\N	\N	4
812	AVAILABLE	2024-10-08 18:02:54.929566	2024-10-24 15:00:00	2024-10-24 14:00:00	2024-10-08 18:02:54.929566	\N	\N	7
822	AVAILABLE	2024-10-08 18:02:56.631816	2024-10-25 16:00:00	2024-10-25 15:00:00	2024-10-08 18:02:56.631816	\N	\N	7
832	AVAILABLE	2024-10-08 18:02:58.331091	2024-10-28 17:00:00	2024-10-28 16:00:00	2024-10-08 18:02:58.331091	\N	\N	7
842	AVAILABLE	2024-10-08 18:03:00.041262	2024-10-29 18:00:00	2024-10-29 17:00:00	2024-10-08 18:03:00.041262	\N	\N	7
852	AVAILABLE	2024-10-08 18:03:01.773254	2024-10-31 09:00:00	2024-10-31 08:00:00	2024-10-08 18:03:01.773254	\N	\N	7
\.


--
-- Data for Name: t_client; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_client (id, address, created_at, date_of_birth, email, first_name, last_name, password, phone, role, status, updated_at) FROM stdin;
4	Cristianstraße 1	2024-09-30 12:41:43.279492	1999-03-02	cristian@gmail.com	Cristian	Stoff	$2a$10$In/pLNv4Q5557GKGTfG8j.bl4bjqy/W.9xL5woBHRtFjI1szDYPYi	+499023456789	CLIENT	ACTIVE	2024-09-30 12:41:43.279492
2	Petersenstraße 1	2024-09-30 12:30:35.806083	1969-11-23	petr@gmail.com	Petr	Petersen	$2a$10$mzqIAKvnk.FsLx7Eg0BL3uLA//DBJdT6P8wdxTN4mA2ytVIHjPv8C	+4919097846523	CLIENT	ACTIVE	2024-10-04 11:17:50.505133
5	Olegovstarße 1	2024-10-04 12:59:50.947086	1973-06-25	oleg@gmail.com	Oleg	Olegov	$2a$10$IesuKSQJmrTbNHPZOzuVY.svDF4iqChIAYoM8zoxGjdeSNa9PLIQu	+499468765432	CLIENT	ACTIVE	2024-10-04 13:08:27.119437
1	Leonistraße 1	2024-09-30 08:58:10.952828	2003-01-05	leo@gmail.com	Leo	Leoni	$2a$10$BajDIoQ/jiOUygZHcjT1zOSaD3qMqjSfQBYVv0HlaQYyOIs2XnW8a	+4916200844923	CLIENT	ACTIVE	2024-10-05 18:19:14.297311
3	Ivanovstraße 1	2024-09-30 12:39:11.35171	1987-12-22	ivan@gmail.com	Ivan	Ivanov	$2a$10$q.ekAGU8Cz6LCi5QYAjTj.CeI4tVs/KEKeApwPi4hVHNfomUye4hm	+495087654321	CLIENT	ACTIVE	2024-10-08 14:42:14.495691
6	Davidstrße 1	2024-10-06 17:41:37.04124	1989-11-05	david@gmail.com	David	Damber	$2a$10$4UiutER1nkdydO.qy4ifzOC.ZWUMgZQHYwUDyVEfqReMCOqsFxnRS	+4914657846523	CLIENT	ACTIVE	2024-10-08 14:42:44.806076
\.


--
-- Data for Name: t_notification; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_notification (id, sent_at, status, appointment_id, client_id) FROM stdin;
\.


--
-- Data for Name: t_review; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_review (id, comment, created_at, rating, appointment_id, client_id, specialist_id) FROM stdin;
6	Super!!!	2024-10-05 20:07:48.442568	5	5	1	2
7	Ausgezeichneter Specialist, ich bin mit dem Empfang zufrieden	2024-10-07 21:41:20.91518	5	36	2	3
\.


--
-- Data for Name: t_service; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_service (id, created_at, description, duration, price, title, updated_at, specialization_id) FROM stdin;
1	2024-09-29 09:54:46.849909	Zahnspangen werden verwendet, um Zahnfehlstellungen und Fehlbisse zu korrigieren. Sie bestehen aus Metall, Keramik oder Kunststoff und werden an den Zähnen befestigt. Durch regelmäßige Anpassungen wird die Zahnstellung über einen Zeitraum von mehreren Monaten bis Jahren allmählich verändert. Es gibt auch ästhetisch ansprechende Varianten wie durchsichtige Brackets oder Lingualspangen, die hinter den Zähnen angebracht werden.	30	5000	Zahnspangen	2024-09-29 09:54:46.849909	5
2	2024-09-29 09:55:43.439654	Die Kieferkorrektur wird bei Fehlstellungen des Kiefers durchgeführt, um die Funktion und das ästhetische Erscheinungsbild zu verbessern. Diese kann sowohl durch chirurgische als auch durch nicht-invasive Methoden erfolgen, abhängig vom Schweregrad der Fehlstellung. Die Korrektur kann notwendig sein, um das Kauen zu verbessern und langfristige Kiefergelenksprobleme zu vermeiden.	60	7000	Kieferkorrekturen	2024-09-29 09:55:43.439654	5
3	2024-09-29 09:56:21.559414	Retentionsgeräte werden nach einer kieferorthopädischen Behandlung verwendet, um die Zähne in ihrer neuen Position zu halten. Es gibt herausnehmbare Retentionsplatten und feste Retainer, die auf der Zahninnenseite befestigt werden. Sie verhindern, dass die Zähne in ihre ursprüngliche Position zurückkehren.	30	450	Retentionsgeräte	2024-09-29 09:56:21.559414	5
4	2024-09-29 09:57:16.470309	Invisalign ist eine unsichtbare Alternative zu herkömmlichen Zahnspangen. Diese transparenten Schienen werden individuell für den Patienten gefertigt und regelmäßig ausgetauscht, um die Zahnstellung allmählich zu korrigieren. Sie sind besonders bei Erwachsenen beliebt, da sie diskret und ästhetisch ansprechender sind.	30	6000	Unsichtbare Zahnspangen	2024-09-29 09:57:16.470309	5
5	2024-09-29 09:57:55.486387	Eine frühzeitige kieferorthopädische Behandlung bei Kindern hilft, schwerwiegendere Fehlstellungen im Erwachsenenalter zu verhindern. Sie kann auch das Kieferwachstum positiv beeinflussen und die Notwendigkeit von späteren, umfangreicheren Eingriffen reduzieren. Häufig verwendete Geräte sind herausnehmbare Platten oder funktionskieferorthopädische Apparaturen.	60	2500	Frühbehandlung	2024-09-29 09:57:55.486387	5
6	2024-09-29 09:58:44.135786	Eine Zahnfleischbehandlung dient der Bekämpfung von Zahnfleischerkrankungen wie Gingivitis und Parodontitis. Dabei werden Zahnbelag und Zahnstein entfernt und die Zahnfleischtaschen gründlich gereinigt. Eine regelmäßige Behandlung kann dazu beitragen, dass sich das Zahnfleisch erholt und das Fortschreiten der Krankheit gestoppt wird.	60	140	Zahnfleischbehandlung	2024-09-29 09:58:44.135786	4
7	2024-09-29 09:59:24.833011	 Die parodontale Chirurgie wird bei fortgeschrittenen Parodontalerkrankungen angewendet, um geschädigtes Zahnfleisch und Kieferknochen wiederherzustellen. Es kann erforderlich sein, um tiefe Zahnfleischtaschen zu reduzieren, die durch Infektionen entstanden sind, oder um verlorenes Zahnfleischgewebe zu rekonstruieren.	60	1000	Parodontalchirurgie	2024-09-29 09:59:24.833011	4
8	2024-09-29 10:00:16.568032	 Die Tiefenreinigung ist ein nicht-chirurgischer Eingriff zur Behandlung von Parodontitis. Dabei werden harte Ablagerungen (Zahnstein) von den Zahnwurzeln entfernt und die Zahnoberflächen geglättet, um die Heilung des Zahnfleischs zu fördern. Die Tiefenreinigung kann mehrere Sitzungen erfordern.	60	350	Tiefenreinigung	2024-09-29 10:00:16.568032	4
9	2024-09-29 10:00:55.090438	Lasertherapie wird verwendet, um infiziertes Gewebe schonend zu entfernen und das Zahnfleisch zur Heilung anzuregen. Der Laser minimiert Blutungen und fördert die Regeneration des Gewebes, was die Behandlungsdauer und das Risiko von Komplikationen reduziert.	45	200	Lasertherapie	2024-09-29 10:00:55.090438	4
10	2024-09-29 10:02:29.385167	Bei starkem Knochenverlust wird eine Knochentransplantation durchgeführt, um ausreichend Knochen für Implantate oder die Stabilität von Zähnen wiederherzustellen. Dabei kann körpereigener oder synthetischer Knochen verwendet werden, der in die betroffenen Bereiche eingebracht wird. Der Heilungsprozess kann mehrere Monate dauern.	60	1500	Knochentransplantation	2024-09-29 10:02:29.385167	4
11	2024-09-29 10:03:31.009948	Zahnimplantate sind künstliche Zahnwurzeln aus Titan, die im Kieferknochen verankert werden. Sie dienen als stabile Basis für Kronen, Brücken oder Prothesen. Nach der Einheilzeit, in der das Implantat mit dem Knochen verwächst, wird der Zahnersatz befestigt. Implantate sind eine langlebige Lösung bei Zahnverlust.	60	2500	Zahnimplantate	2024-09-29 10:03:31.009948	6
12	2024-09-29 10:04:10.283588	Knochenaufbau wird durchgeführt, wenn der Kieferknochen nicht ausreichend stark ist, um ein Implantat zu tragen. Der Eingriff erfolgt oft zusammen mit einer Implantation oder als vorbereitende Maßnahme. Dabei wird Knochengewebe aus dem eigenen Körper oder synthetisches Material verwendet, um den Kieferknochen zu verstärken.	60	1300	Knochenaufbau	2024-09-29 10:04:10.283588	6
13	2024-09-29 10:04:46.856989	Eine regelmäßige professionelle Reinigung der Implantate ist entscheidend für deren Langlebigkeit. Die Pflege beinhaltet die Entfernung von Ablagerungen und die Kontrolle des Gewebes um das Implantat herum, um Entzündungen zu verhindern. Eine gute Mundhygiene und regelmäßige Nachsorge sind unerlässlich.	60	120	Implantatpflege	2024-09-29 10:04:46.856989	6
14	2024-09-29 10:06:22.748981	Der Sinuslift ist ein chirurgischer Eingriff, bei dem der Kieferhöhlenboden angehoben wird, um Platz für das Einbringen von Knochenmaterial zu schaffen. Diese Technik wird häufig im Oberkiefer angewendet, wenn nicht genügend Knochenmaterial für die Verankerung eines Implantats vorhanden ist. Der Heilungsprozess kann mehrere Monate dauern.\n\n	60	1800	Sinuslift	2024-09-29 10:06:22.748981	6
15	2024-09-29 10:07:34.615925	Nach der Implantation folgt die prothetische Versorgung, bei der Kronen, Brücken oder Prothesen auf den Implantaten befestigt werden. Dieser Zahnersatz wird individuell angepasst, um die natürliche Zahnfunktion wiederherzustellen und ein ästhetisch ansprechendes Ergebnis zu erzielen.	60	2000	Prothetische Versorgung	2024-09-29 10:07:34.615925	6
16	2024-09-29 10:08:26.368886	Die Wurzelkanalbehandlung wird durchgeführt, wenn das Zahnmark (Pulpa) durch Karies oder Trauma infiziert ist. Der betroffene Bereich wird gereinigt, desinfiziert und der Kanal wird mit einer Füllung verschlossen. Diese Behandlung kann den Zahn vor einer Extraktion bewahren und langfristig erhalten.	60	600	Wurzelkanalbehandlung	2024-09-29 10:08:26.368886	7
17	2024-09-29 10:09:41.325454	Eine Wurzelspitzenresektion ist ein chirurgischer Eingriff zur Entfernung der entzündeten Wurzelspitze, wenn eine herkömmliche Wurzelkanalbehandlung nicht ausreicht. Dabei wird die betroffene Spitze des Zahns abgetragen, um die Entzündung zu stoppen und den Zahn zu erhalten.	60	600	Wurzelspitzenresektion	2024-09-29 10:09:41.325454	7
18	2024-09-29 10:10:13.187981	Mikroskopische Endodontie ist eine präzisere Form der Wurzelkanalbehandlung, bei der ein Dentalmikroskop verwendet wird, um die feinen Strukturen der Zahnwurzel besser zu sehen. Dadurch können selbst schwer erreichbare oder versteckte Kanäle gereinigt und gefüllt werden. Diese Technik erhöht die Erfolgsrate der Behandlung erheblich und trägt dazu bei, den Zahn langfristig zu erhalten.	60	1000	Mikroskopische Endodontie	2024-09-29 10:10:13.187981	7
19	2024-09-29 10:10:49.353424	Eine Revisionsbehandlung wird durchgeführt, wenn eine frühere Wurzelkanalbehandlung nicht erfolgreich war und eine erneute Reinigung und Füllung des Wurzelkanals erforderlich ist. Diese Behandlung ist komplexer als die ursprüngliche Behandlung, da das bereits behandelte Kanalsystem erneut geöffnet und bearbeitet werden muss.	60	800	Revisionsbehandlung	2024-09-29 10:10:49.353424	7
20	2024-09-29 10:12:21.701716	Internes Bleaching wird an wurzelbehandelten Zähnen durchgeführt, die aufgrund der Behandlung dunkel verfärbt sind. Dabei wird ein Bleaching-Mittel in das Zahninnere eingebracht, um den Zahn aufzuhellen. Das Verfahren erfordert mehrere Sitzungen und ist besonders nützlich bei Verfärbungen, die durch Blutungen oder alte Füllmaterialien verursacht wurden.	60	350	Internes Bleaching	2024-09-29 10:12:21.701716	7
21	2024-09-29 10:12:55.404124	Nach einer Wurzelkanalbehandlung ist der Zahn oft geschwächt und benötigt eine postendodontische Versorgung, um die Stabilität wiederherzustellen. Dies kann eine Krone oder ein Stiftaufbau sein, der den Zahn stärkt und vor weiteren Schäden schützt. Die Versorgung stellt sicher, dass der Zahn seine Funktion behält und nicht bricht.	60	400	Postendodontische Versorgung	2024-09-29 10:12:55.404124	7
22	2024-09-29 10:13:40.799594	Kronen werden verwendet, um stark beschädigte oder abgenutzte Zähne zu rekonstruieren. Sie umhüllen den gesamten Zahn und bieten Schutz und Stabilität. Kronen bestehen aus verschiedenen Materialien wie Keramik, Metall oder einer Kombination aus beidem. Diese prothetische Lösung stellt die natürliche Zahnfunktion wieder her und verbessert die Ästhetik.	60	1000	Kronen	2024-09-29 10:13:40.799594	8
23	2024-09-29 10:14:11.843413	Zahnbrücken ersetzen einen oder mehrere fehlende Zähne, indem sie an den benachbarten Zähnen befestigt werden. Sie können aus Metall, Keramik oder einer Kombination hergestellt werden. Die Brücke wird fest verankert und sorgt für eine stabile und ästhetische Lösung bei Zahnverlust. Sie verbessert sowohl die Kaufunktion als auch das Aussehen des Gebisses.	60	2000	Brücken	2024-09-29 10:14:11.843413	8
24	2024-09-29 10:14:49.283482	Vollprothesen ersetzen alle Zähne im Ober- oder Unterkiefer. Sie bestehen aus einer Kunststoffbasis und künstlichen Zähnen, die dem natürlichen Zahnfleisch und den Zähnen sehr ähnlich sind. Prothesen werden angepasst, um einen bequemen Sitz zu gewährleisten, und erfordern regelmäßige Anpassungen, um eine optimale Passform zu gewährleisten.	60	2200	Vollprothesen 	2024-09-29 10:14:49.283482	8
25	2024-09-29 10:15:31.258315	Teilprothesen werden verwendet, wenn mehrere, aber nicht alle Zähne fehlen. Sie bestehen aus einer Metall- oder Kunststoffbasis, an der künstliche Zähne befestigt sind. Die Teilprothese wird an den natürlichen Zähnen fixiert und kann herausgenommen werden. Sie verbessert die Kaufunktion und stabilisiert den Zahnbogen.	60	1500	Teilprothesen	2024-09-29 10:15:31.258315	8
26	2024-09-29 10:16:11.597132	Implantatgetragene Prothesen bieten eine festere und stabilere Alternative zu herkömmlichen herausnehmbaren Prothesen. Sie werden auf Implantaten befestigt, die im Kieferknochen verankert sind, was ihnen zusätzlichen Halt und Stabilität verleiht. Diese Lösung ist besonders vorteilhaft für Patienten mit schlechtem Kieferknochenvolumen oder instabilen herkömmlichen Prothesen.\n\n	60	5500	Implantatgetragene Prothesen	2024-09-29 10:16:11.597132	8
27	2024-09-29 10:17:04.490742	Zahnaufhellung ist eine kosmetische Behandlung zur Aufhellung der Zähne. Sie kann entweder in der Zahnarztpraxis mit hochkonzentrierten Aufhellungsmitteln oder zu Hause mit speziell angefertigten Schienen durchgeführt werden. Diese Behandlung entfernt Verfärbungen, die durch Kaffee, Tabak oder Alterung verursacht werden, und verbessert das ästhetische Erscheinungsbild des Lächelns.\n\n	60	550	Zahnaufhellung	2024-09-29 10:17:04.490742	9
28	2024-09-29 10:18:08.890816	 Veneers sind dünne Keramik- oder Kompositschalen, die auf die Vorderseite der Zähne geklebt werden, um ästhetische Mängel zu korrigieren, wie z. B. Verfärbungen, Absplitterungen oder Lücken. Veneers bieten eine langfristige, natürliche Lösung für kosmetische Probleme und sind besonders beliebt, um das Aussehen der Frontzähne zu verbessern.	60	1000	Veneers	2024-09-29 10:18:08.890816	9
29	2024-09-29 10:18:44.021863	Zahnästhetische Füllungen aus Kompositmaterial werden verwendet, um Karies oder kleine Defekte an den Zähnen zu reparieren. Sie sind zahnfarben und passen sich dem natürlichen Zahnmaterial an, was sie nahezu unsichtbar macht. Diese Füllungen sind langlebig und bieten eine ästhetisch ansprechende Lösung für kleinere Zahnschäden.	30	150	Zahnästhetische Füllungen	2024-09-29 10:18:44.021863	9
30	2024-09-29 10:19:15.423883	Die Zahnkonturierung ist ein kosmetischer Eingriff, bei dem kleine Mengen Zahnschmelz entfernt werden, um die Form und Größe der Zähne zu verändern. Diese Technik wird häufig eingesetzt, um ungleichmäßige Zähne zu korrigieren, Lücken zu schließen oder abgebrochene Stellen zu glätten. Die Behandlung ist minimal-invasiv und liefert sofort sichtbare Ergebnisse.	30	200	Zahnkonturierung	2024-09-29 10:19:15.423883	9
31	2024-09-29 10:19:55.218813	Die Zahnfleischkonturierung ist ein kosmetischer Eingriff, der zur Korrektur von ungleichmäßigem oder überschüssigem Zahnfleischgewebe durchgeführt wird. Häufig wird sie bei einem "gummy smile" angewendet, um das Verhältnis von Zahn zu Zahnfleisch zu verbessern. Dies verleiht dem Lächeln ein symmetrischeres und ästhetischeres Erscheinungsbild.	60	500	Zahnfleischkonturierung	2024-09-29 10:19:55.218813	9
\.


--
-- Data for Name: t_specialist; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_specialist (id, address, created_at, date_of_birth, description, email, first_name, last_name, password, phone, role, status, updated_at) FROM stdin;
2	Friedrichstraße 20, 10117 Berlin	2024-09-29 14:14:48.416253	1985-08-22	Dr. Julia Schneider ist eine Kieferorthopädin mit Schwerpunkt auf der Behandlung von Kindern und Jugendlichen. Sie legt besonderen Wert auf eine ganzheitliche und sanfte Therapie. Ihr Fachwissen umfasst moderne Zahnspangensysteme wie Invisalign und Lingualtechnik.	schneider@gmail.com	Julia	Schneider	$2a$10$G2EguatYARjPjfdc..hVLOcM/pgDeLoULIfJRpJ5Wxk9mqPZDAWQW	+493087654321	SPECIALIST	ACTIVE	2024-09-29 14:14:48.416253
3	Potsdamer Platz 5, 10785 Berlin	2024-09-29 14:17:35.173679	1975-02-10	 Dr. Stefan Weber ist Experte für Parodontologie und Parodontalchirurgie. Er behandelt komplexe Zahnfleischerkrankungen und ist bekannt für seine sorgfältige und schmerzfreie Herangehensweise. Seine Patienten schätzen seine Kompetenz und sein Einfühlungsvermögen.	weber@gmail.com	Stefan	Weber	$2a$10$6ljhau6H0Udw8uVJzmUlSOW9n0s9aFoeRwNTmHf94n3vRwngThcPe	+493023456789	SPECIALIST	ACTIVE	2024-09-29 14:17:35.173679
1	Kurfürstendamm 50, 10707 Berlin	2024-09-29 14:11:54.902412	1980-05-15	Dr. Thomas Müller ist ein erfahrener Implantologe mit über 15 Jahren Berufserfahrung. Er ist spezialisiert auf Zahnimplantate und Knochenaufbau. Seine Patienten schätzen seine ruhige und präzise Arbeitsweise. Neben der Implantologie bietet er auch ästhetische Zahnbehandlungen an.	mueller@gmail.com	Thomas	Müller	$2a$10$dUWRv7tOqa7BCG0ZdBJtIuq8clAXDaN4OjRTQLmMkadVOIlVXqoyW	+4913012345678	SPECIALIST	ACTIVE	2024-09-29 14:17:57.941221
4	Adresse: Unter den Linden 16, 10117 Berlin	2024-09-29 14:19:47.799601	1990-07-30	Dr. Anna Meier ist Spezialistin für ästhetische Zahnmedizin und Veneers. Sie hat ein besonderes Auge für Details und sorgt dafür, dass jeder Patient ein strahlendes und natürliches Lächeln erhält. Ihre Behandlungen zeichnen sich durch höchste Präzision und moderne Technik aus.	meier@gmail.com	Anna	Meier	$2a$10$KNU2qb0i8OtotPueixD1teEf.lLtXAeUX2dAR.XpblqQHjGUEViSW	+493098765432	SPECIALIST	ACTIVE	2024-09-29 14:19:47.799601
5	Gendarmenmarkt 10, 10117 Berlin	2024-09-29 14:21:30.894081	1982-04-12	Dr. Markus Wagner ist ein erfahrener Endodontologe mit Schwerpunkt auf Wurzelkanalbehandlungen. Mit modernsten Techniken und Mikroskopie sorgt er für den Erhalt natürlicher Zähne, selbst in schwierigen Fällen. Er legt großen Wert auf schmerzfreie Behandlungen.	wagner@gmail.com	Markus	Wagner	$2a$10$xORm.jpgX5uop0sScAcYqONv7ctki4LOhesjGHDdppvuIbE.kAuVW	+493054321678	SPECIALIST	ACTIVE	2024-09-29 14:21:30.894081
6	Kantstraße 8, 10623 Berlin	2024-09-29 14:23:07.565983	1987-03-08	Dr. Sabrina Keller ist auf Prothetik und Zahnersatz spezialisiert. Sie bietet individuelle Lösungen für Zahnkronen, Brücken und Prothesen. Ihre Patienten schätzen die persönliche Betreuung und die hohe Qualität der prothetischen Arbeiten.	keller@gmail.com	Sabrina	Keller	$2a$10$86KG3dvsWaFLnYKBc0dY9e5HI7oIQvG8Y2BgFL8xJJFEGnQ35KKWu	+493065432109	SPECIALIST	ACTIVE	2024-09-29 14:23:07.565983
7	Alexanderplatz 1, 10178 Berlin	2024-09-29 14:24:45.49616	1978-10-25	Dr. Christoph Hofmann ist Experte für Zahnchirurgie und Implantologie. Mit über 20 Jahren Erfahrung führt er komplexe chirurgische Eingriffe durch, darunter Sinuslift und Knochenaufbau. Sein Team setzt auf modernste Technologien und patientenorientierte Behandlung.	hofmann@gmail.com	Christoph	Hofmann	$2a$10$oo1ePkXwUYgC5U2NFsEXWO891KUJuEnVxN6AXYDNlUKyvS1E4deX.	+493076543210	SPECIALIST	ACTIVE	2024-10-03 15:12:26.617198
\.


--
-- Data for Name: t_specialization; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_specialization (id, title) FROM stdin;
5	Kieferorthopädie
6	Implantologie
7	Endodontologie
8	Prothetik 
9	Ästhetische Zahnheilkunde
4	Parodontologie 
\.


--
-- Name: refresh_token_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.refresh_token_seq', 3351, true);


--
-- Name: t_admin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_admin_id_seq', 2, true);


--
-- Name: t_appointment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_appointment_id_seq', 860, true);


--
-- Name: t_client_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_client_id_seq', 6, true);


--
-- Name: t_notification_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_notification_id_seq', 1, false);


--
-- Name: t_review_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_review_id_seq', 7, true);


--
-- Name: t_service_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_service_id_seq', 31, true);


--
-- Name: t_specialist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_specialist_id_seq', 7, true);


--
-- Name: t_specialization_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_specialization_id_seq', 9, true);


--
-- Name: refresh_token refresh_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT refresh_token_pkey PRIMARY KEY (id);


--
-- Name: specialist_service specialist_service_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.specialist_service
    ADD CONSTRAINT specialist_service_pkey PRIMARY KEY (specialist_id, service_id);


--
-- Name: specialist_specialization specialist_specialization_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.specialist_specialization
    ADD CONSTRAINT specialist_specialization_pkey PRIMARY KEY (specialist_id, specialization_id);


--
-- Name: t_admin t_admin_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_admin
    ADD CONSTRAINT t_admin_pkey PRIMARY KEY (id);


--
-- Name: t_appointment t_appointment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_appointment
    ADD CONSTRAINT t_appointment_pkey PRIMARY KEY (id);


--
-- Name: t_client t_client_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_client
    ADD CONSTRAINT t_client_pkey PRIMARY KEY (id);


--
-- Name: t_notification t_notification_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_notification
    ADD CONSTRAINT t_notification_pkey PRIMARY KEY (id);


--
-- Name: t_review t_review_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_review
    ADD CONSTRAINT t_review_pkey PRIMARY KEY (id);


--
-- Name: t_service t_service_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_service
    ADD CONSTRAINT t_service_pkey PRIMARY KEY (id);


--
-- Name: t_specialist t_specialist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_specialist
    ADD CONSTRAINT t_specialist_pkey PRIMARY KEY (id);


--
-- Name: t_specialization t_specialization_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_specialization
    ADD CONSTRAINT t_specialization_pkey PRIMARY KEY (id);


--
-- Name: t_client uk18cdmora4bda55r3j036lxi7p; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_client
    ADD CONSTRAINT uk18cdmora4bda55r3j036lxi7p UNIQUE (email);


--
-- Name: t_review uk3u5jr24ewotjxmc5a16mlyauf; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_review
    ADD CONSTRAINT uk3u5jr24ewotjxmc5a16mlyauf UNIQUE (appointment_id);


--
-- Name: t_specialist uknjcp9ndtw5snf2sgp4niacgn5; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_specialist
    ADD CONSTRAINT uknjcp9ndtw5snf2sgp4niacgn5 UNIQUE (email);


--
-- Name: refresh_token ukr4k4edos30bx9neoq81mdvwph; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT ukr4k4edos30bx9neoq81mdvwph UNIQUE (token);


--
-- Name: t_admin uks28x9l6rak8n743gdjygiklol; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_admin
    ADD CONSTRAINT uks28x9l6rak8n743gdjygiklol UNIQUE (email);


--
-- Name: t_specialization uktnid0q9wl77dcr0jg9fb650l4; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_specialization
    ADD CONSTRAINT uktnid0q9wl77dcr0jg9fb650l4 UNIQUE (title);


--
-- Name: specialist_service fk35eqjmwpxsox8a40ic6egb9r6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.specialist_service
    ADD CONSTRAINT fk35eqjmwpxsox8a40ic6egb9r6 FOREIGN KEY (service_id) REFERENCES public.t_service(id);


--
-- Name: t_appointment fk6e9hpnydcdn2dd5judtr81in2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_appointment
    ADD CONSTRAINT fk6e9hpnydcdn2dd5judtr81in2 FOREIGN KEY (specialist_id) REFERENCES public.t_specialist(id);


--
-- Name: specialist_specialization fk9sty57js62wyfj24a2tgd137l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.specialist_specialization
    ADD CONSTRAINT fk9sty57js62wyfj24a2tgd137l FOREIGN KEY (specialist_id) REFERENCES public.t_specialist(id);


--
-- Name: t_notification fkb5bjkmk6gh6i9lv945svc9794; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_notification
    ADD CONSTRAINT fkb5bjkmk6gh6i9lv945svc9794 FOREIGN KEY (appointment_id) REFERENCES public.t_appointment(id);


--
-- Name: t_appointment fkbsutm4x15burlhadu6q3ger58; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_appointment
    ADD CONSTRAINT fkbsutm4x15burlhadu6q3ger58 FOREIGN KEY (service_id) REFERENCES public.t_service(id);


--
-- Name: specialist_specialization fkduvtqje6oo21w4r0jaseu5qmq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.specialist_specialization
    ADD CONSTRAINT fkduvtqje6oo21w4r0jaseu5qmq FOREIGN KEY (specialization_id) REFERENCES public.t_specialization(id);


--
-- Name: specialist_service fkgo9x1ugfwsyk2d8ig82slwuou; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.specialist_service
    ADD CONSTRAINT fkgo9x1ugfwsyk2d8ig82slwuou FOREIGN KEY (specialist_id) REFERENCES public.t_specialist(id);


--
-- Name: t_service fkhvac6tsauabljgjmio9vre9cb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_service
    ADD CONSTRAINT fkhvac6tsauabljgjmio9vre9cb FOREIGN KEY (specialization_id) REFERENCES public.t_specialization(id);


--
-- Name: t_review fkl7rx3b1yeaam2uhvm9eqyg81; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_review
    ADD CONSTRAINT fkl7rx3b1yeaam2uhvm9eqyg81 FOREIGN KEY (client_id) REFERENCES public.t_client(id);


--
-- Name: t_review fkmajfbhcim6vev8l4qslio1gjd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_review
    ADD CONSTRAINT fkmajfbhcim6vev8l4qslio1gjd FOREIGN KEY (specialist_id) REFERENCES public.t_specialist(id);


--
-- Name: t_review fkr9sbwuvj4bpa2lh2yj0t1co6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_review
    ADD CONSTRAINT fkr9sbwuvj4bpa2lh2yj0t1co6 FOREIGN KEY (appointment_id) REFERENCES public.t_appointment(id);


--
-- Name: t_appointment fkrk1rs96hasxcks2er1c1n96w5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_appointment
    ADD CONSTRAINT fkrk1rs96hasxcks2er1c1n96w5 FOREIGN KEY (client_id) REFERENCES public.t_client(id);


--
-- Name: t_notification fksdewnrwv68a4a0th1dw2rhd0b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_notification
    ADD CONSTRAINT fksdewnrwv68a4a0th1dw2rhd0b FOREIGN KEY (client_id) REFERENCES public.t_client(id);


--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

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

DROP DATABASE postgres;
--
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

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
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

