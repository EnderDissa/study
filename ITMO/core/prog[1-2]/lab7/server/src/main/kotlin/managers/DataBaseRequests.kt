package managers

object DataBaseRequests {
    const val CREATE = """CREATE TYPE MOVIE_GENRE AS ENUM(
                    'ACTION',
                    'COMEDY',
                    'TRAGEDY',
                    'FANTASY'
                );
                CREATE TYPE COLOR AS ENUM (
                    'GREEN',
                    'BLACK',
                    'WHITE',
                    'BROWN'
                );
                CREATE TYPE COUNTRY AS ENUM(
                    'RUSSIA',
                    'SPAIN',
                    'CHINA',
                    'VATICAN',
                    'INDIA'
                );
                CREATE TYPE MPAA_RATING AS ENUM(
                    'G',
                    'PG',
                    'PG_13',
                    'R',
                    'NC_17'
                );
                CREATE TABLE IF NOT EXISTS MOVIE (
                    id SERIAL PRIMARY KEY,
                    name TEXT NOT NULL,
                    coord_x NUMERIC NOT NULL,
                    coord_y NUMERIC NOT NULL,
                    oscarsCount BIGINT,
                    length INT NOT NULL,
                    genre MOVIE_GENRE NOT NULL,
                    rating MPAA_RATING,
                    person_name TEXT NOT NULL,
                    person_height INT NOT NULL,
                    person_hair_color COLOR NOT NULL,
                    person_nationality COUNTRY,
                    creation_date DATE NOT NULL,
                    owner_login TEXT NOT NULL
                );
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    login TEXT,
                    password TEXT,
                    salt TEXT
                );"""
    const val ADD_USER = """
            INSERT INTO users(login, password, salt) VALUES (?, ?, ?);"""
    const val GET_USER = """
            SELECT * FROM users WHERE (login = ?);"""
    const val ADD_MOVIE = """
            INSERT INTO movie(name, coord_x, coord_y, oscarsCount, length, genre, rating, person_name, person_height, person_hair_color, person_nationality, creation_date, owner_login)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id;
            """
    const val UPDATE_MOVIE = """
            UPDATE movie
            SET(name, coord_x, coord_y, oscarsCount, length, genre, rating, person_name, person_height, person_hair_color, person_nationality, creation_date)
            = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            WHERE (id = ?) AND (owner_login = ?)
            RETURNING id;
            """
    const val DELETE_MOVIE = """
            DELETE FROM movie WHERE (owner_login = ?) AND (id = ?) RETURNING id;
            """
    const val GET_MOVIES = """
        SELECT * FROM movie;
    """
}