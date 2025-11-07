--
-- PostgreSQL database dump
--

\restrict LEwqdeQragHzjhXfA5BopdqMprb6XfDVhaHZab6eDaSIm9yCSkitEzwpBjwU5Ff

-- Dumped from database version 15.14 (Debian 15.14-1.pgdg13+1)
-- Dumped by pg_dump version 15.14 (Debian 15.14-1.pgdg13+1)

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

ALTER TABLE ONLY public.book DROP CONSTRAINT fk9cv1tt952k857xoia51k1vj12;
DROP INDEX public.flyway_schema_history_s_idx;
ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
ALTER TABLE ONLY public.users DROP CONSTRAINT ukr43af9ap4edm43mmtq01oddj6;
ALTER TABLE ONLY public.book DROP CONSTRAINT ukg0286ag1dlt4473st1ugemd0m;
ALTER TABLE ONLY public.flyway_schema_history DROP CONSTRAINT flyway_schema_history_pk;
ALTER TABLE ONLY public.book DROP CONSTRAINT book_pkey;
DROP SEQUENCE public.users_seq;
DROP TABLE public.users;
DROP TABLE public.flyway_schema_history;
DROP SEQUENCE public.book_seq;
DROP TABLE public.book;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: book; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book (
    id bigint NOT NULL,
    author character varying(255),
    description character varying(20000),
    genre character varying(255),
    picturesrc character varying(255),
    title character varying(255) NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT book_genre_check CHECK (((genre)::text = ANY ((ARRAY['FANTASY'::character varying, 'THRILLER'::character varying, 'SCIFI'::character varying, 'ROMANCE'::character varying])::text[])))
);


ALTER TABLE public.book OWNER TO postgres;

--
-- Name: book_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.book_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.book_seq OWNER TO postgres;

--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255),
    username character varying(255) NOT NULL,
    CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['ADMIN'::character varying, 'USER'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_seq OWNER TO postgres;

--
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.book (id, author, description, genre, picturesrc, title, user_id) FROM stdin;
158	Robin Hobb	As Bingtown slides toward disaster, clan matriarch Ronica Vestrit, branded a traitor, searches for a way to bring the city's inhabitants together against a momentous threat. Meanwhile, Althea Vestrit, unaware of what has befallen Bingtown and her family, continues her perilous quest to track down and recover her liveship, the Vivacia, from the ruthless pirate Kennit.  Bold though it is, Althea's scheme may be in vain. For her beloved Vivacia will face the most terrible confrontation of all as the secret of the liveships is revealed. It is a truth so shattering, it may destroy the Vivacia and all who love her, including Althea's nephew, whose life already hangs in the balance.	FANTASY	https://moly.hu/system/covers/big/covers_447434.jpg	Ship of Destiny	1
153	Robin Hobb	In a faraway land where members of the royal family are named for the virtues they embody, one young boy will become a walking enigma.  Born on the wrong side of the sheets, Fitz, son of Chivalry Farseer, is a royal bastard, cast out into the world, friendless and lonely. Only his magical link with animals - the old art known as the Wit - gives him solace and companionship. But the Wit, if used too often, is a perilous magic, and one abhorred by the nobility.  So when Fitz is finally adopted into the royal household, he must give up his old ways and embrace a new life of weaponry, scribing, courtly manners; and how to kill a man secretly, as he trains to become a royal assassin.	FANTASY	https://moly.hu/system/covers/big/covers_447426.jpg?1499009739	Assassin's Apprentice	2
154	Robin Hobb	Fitz has survived his first hazardous mission as king’s assassin, but is left little more than a cripple. Battered and bitter, he vows to abandon his oath to King Shrewd, remaining in the distant mountains. But love and events of terrible urgency draw him back to the court at Buckkeep, and into the deadly intrigues of the royal family.	FANTASY	https://moly.hu/system/covers/big/covers_447429.jpg?1499010470	Royal Assassin	2
155	Robin Hobb	King Shrewd is dead at the hands of his son Regal. As is Fitz--or so his enemies and friends believe. But with the help of his allies and his beast magic, he emerges from the grave, deeply scarred in body and soul. The kingdom also teeters toward ruin: Regal has plundered and abandoned the capital, while the rightful heir, Prince Verity, is lost to his mad quest--perhaps to death. Only Verity's return--or the heir his princess carries--can save the Six Duchies.  But Fitz will not wait. Driven by loss and bitter memories, he undertakes a quest: to kill Regal. The journey casts him into deep waters, as he discovers wild currents of magic within him--currents that will either drown him or make him something more than he was.	FANTASY	https://moly.hu/system/covers/big/covers_447430.jpg?1499010700	Assassin's Quest	2
156	Robin Hobb	But how can one trade with the Rain Wilders, when only a liveship fashioned from wizardwood can negotiate the perilous waters of the Rain River? Rare and valuable a liveship will quicken only when three members, from successive generations, have died on board. The liveship Vivacia is about to undergo her quickening as Althea Vestrit's father is carried on deck in his death-throes. Althea waits for the ship that she loves more than anything else in the world to awaken. Only to discover that the Vivacia has been signed away in her father’s will to her brutal brother-in-law, Kyle Haven...  Others plot to win or steal a liveship. The Paragon, known by many as the Pariah, went mad, turned turtle, and drowned his crew. Now he lies blind, lonely, and broken on a deserted beach. But greedy men have designs to restore him, to sail the waters of the Rain Wild River once more.	FANTASY	https://moly.hu/system/covers/big/covers_440383.jpg	Ship of Magic	1
157	Robin Hobb	The Farseer trilogy continues the dramatic tale of piracy, serpents, love and magic. The Vestrit family's liveship, Vivacia, has been taken by the pirate king, Kennit. Held captive on board, Wintrow Vestrit finds himself competing with Kennit for Vivacia's love as the ship slowly acquires her own bloodlust. Leagues away, Althea Vestrit has found a new home aboard the liveship Ophelia, but she lives only to reclaim the Vivacia and with her friend, Brashen, she plans a dangerous rescue. Meanwhile in Bingtown, the fading fortunes of the Vestrit family lead Malta deeper into the magical secrets of the Rain Wild Traders. And just outside Bingtown, Amber dreams of relaunching Paragon, the mad liveship ...	FANTASY	https://m.media-amazon.com/images/I/91uwuJ-pTOL.jpg	The Mad Ship	1
159	Robin Hobb	Fifteen years have passed since the end of the Red Ship War with the terrifying Outislanders. Since then, Fitz has wandered the world accompanied only by his wolf and Wit-partner, Nighteyes, finally settling in a tiny cottage as remote from Buckkeep and the Farseers as possible.  But lately the world has come crashing in again. The Witted are being persecuted because of their magical bonds with animals; and young Prince Dutiful has gone missing just before his crucial diplomatic wedding to an Outislander princess. Fitz’s assignment to fetch Dutiful back in time for the ceremony seems very much like a fool’s errand, but the dangers ahead could signal the end of the Farseer reign.	FANTASY	https://moly.hu/system/covers/big/covers_878655.jpg?1722528034	Fool's Errand	2
160	Robin Hobb	Prince Dutiful has been rescued from his Piebald kidnappers and the court has resumed its normal rhythms. But for FitzChivalry Farseer, a return to isolation is impossible. Though gutted by the loss of his wolf bondmate, Nighteyes, Fitz must take up residence at Buckkeep and resume his tasks as Chade’s apprentice assassin. Posing as Tom Badgerlock, bodyguard to Lord Golden, FitzChivalry becomes the eyes and ears behind the walls. And with his old mentor failing visibly, Fitz is forced to take on more burdens as he attempts to guide a kingdom straying closer to civil strife each day.  The problems are legion. Prince Dutiful’s betrothal to the Narcheska Elliania of the Out Islands is fraught with tension, and the Narcheska herself appears to be hiding an array of secrets. Then, amid Piebald threats and the increasing persecution of the Witted, FitzChivalry must ensure that no one betrays the Prince’s secret—a secret that could topple the Farseer throne: that he, like Fitz, possesses the dread “beast magic.”  Meanwhile, FitzChivalry must impart to the Prince his limited knowledge of the Skill: the hereditary and addictive magic of the Farseers. In the process, they discover within Buckkeep one who has a wild and powerful talent for it, and whose enmity for Fitz may have disastrous consequences for all.  Only Fitz’s enduring friendship with the Fool brings him any solace. But even that is shattered when unexpected visitors from Bingtown reveal devastating secrets from the Fool’s past. Now, bereft of support and adrift in intrigue, Fitz’s biggest challenge may be simply to survive the inescapable and violent path that fate has laid out for him.	FANTASY	https://moly.hu/system/covers/big/covers_483576.jpg?1521051182	Golden Fool	2
161	Robin Hobb	he triumphant conclusion to the Tawny Man trilogy, from the author of the bestselling Farseer and Liveship Traders trilogies. The moving end to the tale of the Farseers, in which kingdoms must stand or fall on the beat of a dragon's wings, or a Fool's heart. A small and sadly untried coterie - the old assassin Chade, the serving-boy Thick, Prince Dutiful, and his reluctant Skillmaster, Fitz - sail towards the distant island of Aslevjal. There they must fulfil the Narcheska's challenge to her betrothed: to lay the head of the dragon Icefyre, whom legends tell is buried there deep beneath the ice, upon her hearth. Only with the completion of this quest can the marriage proceed, and the resulting alliance signal an end to war between the two kingdoms. It is not a happy ship: tensions between the folk of the Six Duchies and their traditional enemies, the Outislanders, lie just beneath the surface. Thick is constantly ill, and his random but powerful Skilling has taken on a dark and menacing tone, while Chade's fascination with the Skill is growing to the point of obsession. Having ensured that his beloved friend the Fool is safely left behind in Buckkeep, Fitz is guilt-stricken; but he is determined to keep his fate at bay, since prophecy foretells the Fool's death if he ever sets foot on the isle of the black dragon. But as their ship draws in towards Aslevjal a lone figure awaits them...	FANTASY	https://moly.hu/system/covers/big/covers_483578.jpg?1521051225	Fool's Fate	2
166	Robin Hobb	oo much time has passed since the powerful dragon Tintaglia helped the people of the Trader cities stave off an invasion of their enemies. The Traders have forgotten their promises, weary of the labor and expense of tending earthbound dragons who were hatched weak and deformed by a river turned toxic. If neglected, the creatures will rampage--or die--so it is decreed that they must move farther upriver toward Kelsingra, the mythical homeland whose location is locked deep within the dragons' uncertain ancestral memories.  Thymara, an unschooled forest girl, and Alise, wife of an unloving and wealthy Trader, are among the disparate group entrusted with escorting the dragons to their new home. And on an extraordinary odyssey with no promise of return, many lessons will be learned--as dragons and tenders alike experience hardships, betrayals . . . and joys beyond their wildest imaginings.	FANTASY	https://moly.hu/system/covers/big/covers_693653.jpg	Dragon Keeper	1
167	Robin Hobb	Return to the world of the Liveships Traders and journey along the Rain Wild River in the second instalment of high adventure from the author of the internationally acclaimed Farseer trilogy. The dragon keepers and the fledgling dragons are forging a passage up the treacherous Rain Wild River. They are in search of the mythical Elderling city of Kelsingra, and are accompanied by the liveship Tarman, its captain, Leftrin, and a group of hunters who must search the forests for game with which to keep the dragons fed. With them are Alise, who has escaped her cold marriage to the cruel libertine Hest Finbok in order to continue her study of dragons, and Hest's amanuensis, Bingtown dandy, Sedric. Rivalries and romances are already threatening to disrupt the band of explorers: but external forces may prove to be even more dangerous. Chalcedean merchants are keen to lay hands on dragon blood and organs to turn them to medicines and profit. Their traitor has infiltrated the expeditionand will stop at nothing to obtain the coveted body parts. And then there are the Rain Wilds themselves: mysterious, unstable and ever perilous, its mighty river running with acid, its jungle impenetrable and its waterways uncharted. Will the expedition reach their destination unscathed? Does the city of Kelsingra even exist? Only one thing is certain: the journey will leave none of the dragons nor their human companions unchanged by the experience.	FANTASY	https://m.media-amazon.com/images/I/8115sAXsQOL._AC_UF1000,1000_QL80_.jpg	Dragon Haven	1
168	Robin Hobb	New York Times bestselling author Robin Hobb returns to world of the Rain Wilds—called “one of the most gripping settings in modern fantasy” (Booklist)—in City of Dragons. Continuing the enthralling journey she began in her acclaimed Dragon Keeper and Dragon Haven, Hobb rejoins a small group of weak, half-formed and unwanted dragons and their displaced human companions as they search for a legendary sanctuary. Now, as the misfit band approaches its final destination, dragons and keepers alike face a challenge so insurmountable that it threatens to render their long, difficult odyssey utterly meaningless. Touching, powerful, and dazzlingly inventive, Hobb’s City of Dragons is not to be missed—further proof that this author belongs alongside Raymond E. Feist, Terry Brooks, and Lois McMaster Bujold in the pantheon of fantasy fiction’s true greats.	FANTASY	https://m.media-amazon.com/images/I/81+lCRWOblL._UF1000,1000_QL80_.jpg	City of Dragon	1
169	Robin Hobb	The dragons' survival hangs in the balance in the thrilling final volume in the acclaimed River Wilds Chronicles fantasy series  The dragons and their dedicated band of keepers have at last found the lost city of Kelsingra. The magical creatures have learned to use their wings and are growing into their regal inheritance. Their humans, too, are changing. As the mystical bonds with their dragons deepen, Thymara, Tats, Rapskal, and even Sedric, the unlikeliest of keepers, have begun transforming into beautiful Elderlings raked with exquisite features that complement and reflect the dragons they serve.  But while the humans have scoured the empty streets and enormous buildings of Kelsingra, they cannot find the mythical silver wells the dragons need to stay health and survive. With enemies encroaching, the keepers must risk "memory walking"- immersing themselves in the dangerously addictive memories of long-deceased Elderlings - to uncover clues necessary to their survival.  And time is of the essence, for the legendary Tintaglia, long feared dead, has returned, wounded in a battle with humans hunting dragon blood and scales. She is weakening and only the hidden silver can revive her. If Tintaglia dies, so, too, will the ancient memories she carries - a devastating loss that will ensure the dragons' extinction.	FANTASY	https://m.media-amazon.com/images/I/81hrl3o8p-L._UF1000,1000_QL80_.jpg	Blood of Dragons	1
170	Robin Hobb	Tom Badgerlock has been living peaceably in the manor house at Withywoods with his beloved wife Molly these many years, the estate a reward to his family for loyal service to the crown.  But behind the facade of respectable middle-age lies a turbulent and violent past. For Tom Badgerlock is actually FitzChivalry Farseer, bastard scion of the Farseer line, convicted user of Beast-magic, and assassin. A man who has risked much for his king and lost more…  On a shelf in his den sits a triptych carved in memory stone of a man, a wolf and a fool. Once, these three were inseparable friends: Fitz, Nighteyes and the Fool. But one is long dead, and one long-missing.  Then one Winterfest night a messenger arrives to seek out Fitz, but mysteriously disappears, leaving nothing but a blood-trail. What was the message? Who was the sender? And what has happened to the messenger?  Suddenly Fitz's violent old life erupts into the peace of his new world, and nothing and no one is safe.	FANTASY	 https://m.media-amazon.com/images/I/91VwHvun9SL.jpg	Fool's assassin	2
171	Robin Hobb	After nearly killing his oldest friend, the Fool, and finding his daughter stolen away by those who were once targeting the Fool, FitzChivarly Farseer is out for blood. And who better to wreak havoc than a highly trained and deadly former royal assassin? Fitz might have let his skills go fallow over his years of peace, but such things, once learned, are not so easily forgotten. And nothing is more dangerous than a man who has nothing left to lose…	FANTASY	https://m.media-amazon.com/images/I/91jmp2PbsUL._UF1000,1000_QL80_.jpg	Fool's Quest	2
172	Robin Hobb	More than twenty years ago, the first epic fantasy novel featuring FitzChivalry Farseer and his mysterious, often maddening friend the Fool struck like a bolt of brilliant lightning. Now New York Times bestselling author Robin Hobb brings to a momentous close the third trilogy featuring these beloved characters in a novel of unsurpassed artistry that is sure to endure as one of the great masterworks of the genre.  Fitz’s young daughter, Bee, has been kidnapped by the Servants, a secret society whose members not only dream of possible futures but use their prophecies to add to their wealth and influence. Bee plays a crucial part in these dreams—but just what part remains uncertain.  As Bee is dragged by her sadistic captors across half the world, Fitz and the Fool, believing her dead, embark on a mission of revenge that will take them to the distant island where the Servants reside—a place the Fool once called home and later called prison. It was a hell the Fool escaped, maimed and blinded, swearing never to return.  For all his injuries, however, the Fool is not as helpless as he seems. He is a dreamer too, able to shape the future. And though Fitz is no longer the peerless assassin of his youth, he remains a man to be reckoned with—deadly with blades and poison, and adept in Farseer magic. And their goal is simple: to make sure not a single Servant survives their scourge.	FANTASY	https://m.media-amazon.com/images/I/A1eWMs0DkhL._UF894,1000_QL80_.jpg	Assassin's Fate	2
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, password, role, username) FROM stdin;
2	$2a$10$Oto1U5Sm3.cQ1x5DpawAduEmliWDV.ObdwHRabKW/ObkmHg9nTrbm	USER	Fitz
1	$2a$10$/XLKH7GxxE.9XIbQsfx7ou4HDbudvkDDt0VD/R9Vw3Jt.lcbqa1lq	ADMIN	Bence
102	$2a$10$ljpLn.S5rnekY94XZFNbpuMze7lca4fzc3q6Z2vvBm3fwy2qzgsKG	USER	TestUser1
\.


--
-- Name: book_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.book_seq', 251, true);


--
-- Name: users_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_seq', 151, true);


--
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: book ukg0286ag1dlt4473st1ugemd0m; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT ukg0286ag1dlt4473st1ugemd0m UNIQUE (title);


--
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: book fk9cv1tt952k857xoia51k1vj12; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT fk9cv1tt952k857xoia51k1vj12 FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- PostgreSQL database dump complete
--

\unrestrict LEwqdeQragHzjhXfA5BopdqMprb6XfDVhaHZab6eDaSIm9yCSkitEzwpBjwU5Ff

